/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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

import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.sirius.components.emf.forms.EStructuralFeatureLabelProvider;
import org.eclipse.sirius.components.emf.forms.api.IPropertiesValidationProvider;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.description.CheckboxDescription;
import org.eclipse.sirius.components.forms.description.IfDescription;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.uml2.types.TypesPackage;

/**
 * Provides the default description of the widget to use to support an EBoolean feature.
 *
 * @author sbegaudeau
 */
public class EBooleanIfDescriptionProvider {

    private static final String IF_DESCRIPTION_ID = "EBoolean";

    private static final String CHECKBOX_DESCRIPTION_ID = "Checkbox";

    private final ComposedAdapterFactory composedAdapterFactory;

    private final IPropertiesValidationProvider propertiesValidationProvider;

    private final Function<VariableManager, String> semanticTargetIdProvider;

    public EBooleanIfDescriptionProvider(ComposedAdapterFactory composedAdapterFactory, IPropertiesValidationProvider propertiesValidationProvider,
            Function<VariableManager, String> semanticTargetIdProvider) {
        this.composedAdapterFactory = Objects.requireNonNull(composedAdapterFactory);
        this.propertiesValidationProvider = Objects.requireNonNull(propertiesValidationProvider);
        this.semanticTargetIdProvider = Objects.requireNonNull(semanticTargetIdProvider);
    }

    public IfDescription getIfDescription() {
        return IfDescription.newIfDescription(IF_DESCRIPTION_ID)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .predicate(this.getPredicate())
                .controlDescriptions(List.of(this.getCheckboxDescription()))
                .build();
    }

    private Function<VariableManager, Boolean> getPredicate() {
        return variableManager -> {
            var optionalEAttribute = variableManager.get(AdvancedPropertiesDescriptionProvider.ESTRUCTURAL_FEATURE, EAttribute.class);
            return optionalEAttribute.filter(eAttribute -> this.isBoolean(eAttribute)).isPresent();
        };
    }

    private boolean isBoolean(EAttribute eAttribute) {
        return eAttribute.getEType().equals(EcorePackage.Literals.EBOOLEAN) || eAttribute.getEType().equals(TypesPackage.eINSTANCE.getBoolean());
    }

    private CheckboxDescription getCheckboxDescription() {
        return CheckboxDescription.newCheckboxDescription(CHECKBOX_DESCRIPTION_ID)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .idProvider(new WidgetIdProvider())
                .labelProvider(this.getLabelProvider())
                .valueProvider(this.getValueProvider())
                .newValueHandler(this.getNewValueHandler())
                .diagnosticsProvider(this.propertiesValidationProvider.getDiagnosticsProvider())
                .kindProvider(this.propertiesValidationProvider.getKindProvider())
                .messageProvider(this.propertiesValidationProvider.getMessageProvider())
                .build();
    }

    private Function<VariableManager, String> getLabelProvider() {
        return new EStructuralFeatureLabelProvider(AdvancedPropertiesDescriptionProvider.ESTRUCTURAL_FEATURE, this.composedAdapterFactory);
    }

    private Function<VariableManager, Boolean> getValueProvider() {
        return variableManager -> {
            var optionalEObject = variableManager.get(VariableManager.SELF, EObject.class);
            var optionalEAttribute = variableManager.get(AdvancedPropertiesDescriptionProvider.ESTRUCTURAL_FEATURE, EAttribute.class);
            if (optionalEObject.isPresent() && optionalEAttribute.isPresent()) {
                EObject eObject = optionalEObject.get();
                EAttribute eAttribute = optionalEAttribute.get();

                Object value = eObject.eGet(eAttribute);
                return Boolean.valueOf(value.toString());
            }
            return Boolean.FALSE;
        };
    }

    private BiFunction<VariableManager, Boolean, IStatus> getNewValueHandler() {
        return (variableManager, newValue) -> {
            var optionalEObject = variableManager.get(VariableManager.SELF, EObject.class);
            var optionalEAttribute = variableManager.get(AdvancedPropertiesDescriptionProvider.ESTRUCTURAL_FEATURE, EAttribute.class);
            if (optionalEObject.isPresent() && optionalEAttribute.isPresent()) {
                EObject eObject = optionalEObject.get();
                EAttribute eAttribute = optionalEAttribute.get();

                eObject.eSet(eAttribute, newValue);
                return new Success();
            }
            return new Failure("");
        };
    }

}
