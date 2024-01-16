/*****************************************************************************
 * Copyright (c) 2023, 2024 CEA LIST, Obeo.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.web.custom.widgets;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.papyrus.uml.domain.services.EMFUtils;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage;
import org.eclipse.sirius.components.core.URLParser;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.IRepresentationDescriptionIdProvider;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.form.IFormIdProvider;
import org.eclipse.sirius.components.view.form.FormElementDescription;
import org.eclipse.sirius.components.widget.reference.IReferenceWidgetRootCandidateSearchProvider;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IReferenceWidgetRootCandidateSearchProvider} for {@link MonoReferenceWidgetDescription} and {@link MultiReferenceWidgetDescription}.
 *
 * @author Jerome Gout
 */
@Service
@Order(Ordered.HIGHEST_PRECEDENCE)
public class PapyrusReferenceRootCandidateSearchProvider implements IReferenceWidgetRootCandidateSearchProvider {

    private static final String MULTI_REF_DESCRIPTION = PapyrusWidgetsPackage.eINSTANCE.getMultiReferenceWidgetDescription().getName();

    private static final String MONO_REF_DESCRIPTION = PapyrusWidgetsPackage.eINSTANCE.getMonoReferenceWidgetDescription().getName();

    private final IViewRepresentationDescriptionSearchService viewSearchService;

    private final AQLInterpreterProvider interpreterProvider;

    public PapyrusReferenceRootCandidateSearchProvider(IViewRepresentationDescriptionSearchService viewSearchService, AQLInterpreterProvider interpreterProvider) {
        super();
        this.viewSearchService = Objects.requireNonNull(viewSearchService);
        this.interpreterProvider = Objects.requireNonNull(interpreterProvider);
    }

    @Override
    public boolean canHandle(String descriptionId) {
        if (descriptionId != null && descriptionId.startsWith(IFormIdProvider.FORM_ELEMENT_DESCRIPTION_PREFIX)) {
            Map<String, List<String>> parameters = new URLParser().getParameterValues(descriptionId);
            String sourceKind = parameters.get(IRepresentationDescriptionIdProvider.SOURCE_KIND).get(0);
            String kind = parameters.get(IRepresentationDescriptionIdProvider.KIND).get(0);
            return IRepresentationDescriptionIdProvider.VIEW_SOURCE_KIND.equals(sourceKind) && (MULTI_REF_DESCRIPTION.equals(kind) || MONO_REF_DESCRIPTION.equals(kind));
        } else {
            return false;
        }
    }

    @Override
    public List<? extends Object> getRootElementsForReference(Object targetElement, String descriptionId, IEditingContext editingContext) {
        Optional<FormElementDescription> widgetDescription = this.viewSearchService.findViewFormElementDescriptionById(editingContext, descriptionId);
        if (widgetDescription.isPresent()) {
            final String searchExpression;
            if (widgetDescription.get() instanceof MonoReferenceWidgetDescription mono) {
                searchExpression = mono.getCandidatesSearchScopeExpression();
            } else {
                searchExpression = ((MultiReferenceWidgetDescription) widgetDescription.get()).getCandidatesSearchScopeExpression();
            }
            var view = EMFUtils.getAncestor(View.class,  widgetDescription.get());
            if (view != null && !searchExpression.isBlank()) {
                AQLInterpreter interpreter = this.interpreterProvider.createInterpreter(view, editingContext);
                var variableManager = new VariableManager();
                variableManager.put(VariableManager.SELF, targetElement);
                return interpreter.evaluateExpression(variableManager.getVariables(), searchExpression).asObjects().orElse(List.of());
            }
        }
        return List.of();
    }

}
