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
package org.eclipse.papyrus.web.application.properties;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.papyrus.uml.domain.services.profile.ProfileUtil;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.compatibility.emf.properties.api.IPropertiesValidationProvider;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.components.ListComponent;
import org.eclipse.sirius.components.forms.description.IfDescription;
import org.eclipse.sirius.components.forms.description.ListDescription;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Stereotype;

/**
 * Provides the default description of the widget to displayed applied stereotype(s) on an UML element.
 *
 * @author Arthur Daussy
 */
public class AppliedStereotypeIfDescriptionProvider {

    private static final String IF_DESCRIPTION_ID = "AppliedStereotypeIfDescription"; //$NON-NLS-1$

    private static final String LIST_DESCRIPTION_ID = "AppliedStereotypeList"; //$NON-NLS-1$

    private final IPropertiesValidationProvider propertiesValidationProvider;

    private final IObjectService objectService;

    private Function<VariableManager, String> semanticTargetIdProvider;

    public AppliedStereotypeIfDescriptionProvider(IPropertiesValidationProvider propertiesValidationProvider, IObjectService objectService,
            Function<VariableManager, String> semanticTargetIdProvider) {
        this.objectService = objectService;
        this.propertiesValidationProvider = Objects.requireNonNull(propertiesValidationProvider);
        this.semanticTargetIdProvider = Objects.requireNonNull(semanticTargetIdProvider);
    }

    public IfDescription getIfDescription() {
        // @formatter:off
        return IfDescription.newIfDescription(IF_DESCRIPTION_ID)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .predicate(this.getPredicate())
                .controlDescriptions(List.of(this.buildListDescription()))
                .build();
        // @formatter:on
    }

    private Function<VariableManager, Boolean> getPredicate() {
        return variableManager -> {
            var optionalEAttribute = variableManager.get(VariableManager.SELF, EObject.class);
            return optionalEAttribute.filter(self -> self instanceof Element).isPresent();
        };
    }

    private String getKind(VariableManager variableManager) {
        Object candidate = variableManager.getVariables().get(ListComponent.CANDIDATE_VARIABLE);
        return this.objectService.getKind(candidate);
    }

    private String getListItemImageURL(VariableManager variablemanager) {
        // @formatter:off
        return variablemanager.get(ListComponent.CANDIDATE_VARIABLE, EObject.class)
                .map(this::getImage)
                .orElse("");
        // @formatter:on
    }

    private ListDescription buildListDescription() {
        // @formatter:off
        return ListDescription.newListDescription(LIST_DESCRIPTION_ID)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .idProvider(new GetOrCreateRandomIdProvider())
                .labelProvider(this.getLabelProvider())
                .itemsProvider(this.getItemsProvider())
                .itemLabelProvider(this.getItemLabelProvider())
                .itemIdProvider(this::getItemId)
                .itemDeletableProvider(varMan -> Boolean.TRUE)
                .itemDeleteHandlerProvider(this.getDeleteProvider())
                .itemKindProvider(this::getKind)
                .itemClickHandlerProvider(e -> new Success())
                .styleProvider(e -> null)
                .itemImageURLProvider(this::getListItemImageURL)
                .diagnosticsProvider(this.propertiesValidationProvider.getDiagnosticsProvider())
                .kindProvider(this.propertiesValidationProvider.getKindProvider())
                .messageProvider(this.propertiesValidationProvider.getMessageProvider())
                .build();
        // @formatter:on
    }

    private String getItemId(VariableManager variableManager) {
        Object candidate = variableManager.getVariables().get(ListComponent.CANDIDATE_VARIABLE);
        return this.objectService.getId(candidate);
    }

    private Function<VariableManager, IStatus> getDeleteProvider() {
        return varMan -> {
            Optional<EObject> optStereotypeApplication = varMan.get(ListComponent.CANDIDATE_VARIABLE, EObject.class);
            if (optStereotypeApplication.isPresent()) {
                Element self = varMan.get(VariableManager.SELF, Element.class).get();
                EObject candidate = optStereotypeApplication.get();
                Stereotype stereotype = ProfileUtil.getStereotype(candidate);
                if (stereotype != null) {
                    self.unapplyStereotype(stereotype);
                    return new Success(ChangeKind.SEMANTIC_CHANGE, Map.of());
                }

            }
            return new Failure("Fail to remove from stereotype");
        };
    }

    private Function<VariableManager, String> getItemLabelProvider() {
        return varMan -> {
            EObject stApplication = varMan.get(ListComponent.CANDIDATE_VARIABLE, EObject.class).get();
            Stereotype appliedStereotype = ProfileUtil.getStereotype(stApplication);
            if (appliedStereotype != null) {
                return appliedStereotype.getQualifiedName();
            } else {
                return "";
            }
        };
    }

    private Function<VariableManager, String> getLabelProvider() {
        return e -> "Applied stereotypes";
    }

    private Function<VariableManager, List<?>> getItemsProvider() {
        return variableManager -> {
            var optionalEObject = variableManager.get(VariableManager.SELF, Element.class);
            if (optionalEObject.isPresent()) {
                return optionalEObject.get().getStereotypeApplications();
            }
            return Collections.emptyList();
        };
    }

    private String getImage(EObject stereotypeApplication) {
        if (stereotypeApplication instanceof DynamicEObjectImpl) {
            return "/icons/full/obj16/Stereotype.gif";
        }
        return this.objectService.getImagePath(stereotypeApplication);
    }

}
