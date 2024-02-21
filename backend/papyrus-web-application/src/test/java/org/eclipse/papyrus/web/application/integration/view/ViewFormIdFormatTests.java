/*******************************************************************************
 * Copyright (c) 2023 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.web.application.integration.view;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.papyrus.web.application.utils.AbstractWebUMLTest;
import org.eclipse.sirius.components.collaborative.forms.services.PropertiesDescriptionRegistry;
import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistryConfigurer;
import org.eclipse.sirius.components.core.URLParser;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextRepresentationDescriptionProvider;
import org.eclipse.sirius.components.forms.description.AbstractControlDescription;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.view.emf.form.IFormIdProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Integration test that checks that all registered forms generated from a View model used as representation description
 * or in the Details view use a proper id format.
 *
 * @author Arthur Daussy
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ViewFormIdFormatTests extends AbstractWebUMLTest {

    private final URLParser urlParser = new URLParser();

    @Autowired
    private List<IEditingContextRepresentationDescriptionProvider> representationDescriptionProviders;

    @Autowired
    private List<IPropertiesDescriptionRegistryConfigurer> propertyConfigurers;

    @Test
    public void checkRepresentationFormIdFormat() {
        this.representationDescriptionProviders.stream()
                .map(representationDescriptionProvider -> representationDescriptionProvider.getRepresentationDescriptions(new IEditingContext.NoOp()))
                .flatMap(List::stream)
                .filter(FormDescription.class::isInstance)
                .map(FormDescription.class::cast)
                .forEach(this::checkIds);
    }

    @Test
    public void checkDetailFormIdFormat() {
        var registry = new PropertiesDescriptionRegistry();
        this.propertyConfigurers.forEach(configurer -> configurer.addPropertiesDescriptions(registry));

        registry.getPropertiesDescriptions().stream().filter(this::filterFromView).forEach(this::checkIds);
    }

    private void checkIds(PageDescription desc) {

        System.out.println(desc.getId());
        List<AbstractControlDescription> elementToCheck = desc.getGroupDescriptions().stream().flatMap(groupDescription -> groupDescription.getControlDescriptions().stream()).toList();

        for (AbstractControlDescription control : elementToCheck) {
            this.checkId(control);
        }
    }

    private void checkIds(FormDescription desc) {
        Optional<String> optSourceKind = this.getIdParameter(desc.getId(), IFormIdProvider.SOURCE_KIND);
        assertTrue(optSourceKind.isPresent(), MessageFormat.format("Invalid form description id {0} missing sourceKind param", desc));

        List<AbstractControlDescription> elementToCheck = desc.getPageDescriptions().stream().filter(this::filterFromView).flatMap(pageDescription -> pageDescription.getGroupDescriptions().stream())
                .flatMap(groupDescription -> groupDescription.getControlDescriptions().stream()).toList();

        for (AbstractControlDescription control : elementToCheck) {
            this.checkId(control);
        }
    }

    private void checkId(AbstractControlDescription control) {
        this.assertParameterNotNull(control, IFormIdProvider.KIND);
        this.assertParameterNotNull(control, IFormIdProvider.SOURCE_ELEMENT_ID);
        this.assertParameterNotNull(control, IFormIdProvider.SOURCE_ID);
        this.assertParameterEquals(control, IFormIdProvider.SOURCE_KIND, "view");
    }

    private String assertParameterNotNull(AbstractControlDescription control, String expectedParameter) {
        Optional<String> paramValue = this.getIdParameter(control.getId(), expectedParameter);
        assertTrue(paramValue.isPresent(), MessageFormat.format("Missing ID parameter {0} on description {1} of type {2}", expectedParameter, control.getId(), control.getClass().getCanonicalName()));
        return paramValue.get();
    }

    private void assertParameterEquals(AbstractControlDescription control, String expectedParameter, String expectedValue) {
        String value = this.assertParameterNotNull(control, expectedParameter);
        assertEquals(expectedValue, value, MessageFormat.format("Unecpected {0} value on id {1}", expectedParameter, control.getId()));
    }

    private Optional<String> getIdParameter(String id, String expectedParameter) {
        try {
            Map<String, List<String>> parameters = this.urlParser.getParameterValues(id);

            List<String> list = parameters.get(expectedParameter);
            final Optional<String> result;
            if (list == null || list.isEmpty()) {
                result = Optional.empty();
            } else {
                result = Optional.of(list.get(0));
            }
            return result;
        } catch (NullPointerException e) {
            // TODO: handle exception
            fail("Invalid id " + id);
            return Optional.empty();
        }
    }

    private boolean filterFromView(PageDescription desc) {
        String id = desc.getId();
        if (id != null && id.startsWith(IFormIdProvider.FORM_ELEMENT_DESCRIPTION_PREFIX)) {
            Optional<String> paramValue = this.getIdParameter(id, IFormIdProvider.SOURCE_KIND);
            return paramValue.isPresent() && "view".equals(paramValue.get());

        }
        return false;
    }
}
