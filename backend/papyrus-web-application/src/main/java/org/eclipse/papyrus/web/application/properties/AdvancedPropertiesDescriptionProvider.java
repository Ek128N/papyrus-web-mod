/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *     CEA - Adaptation for Papyrus Web
 *******************************************************************************/
package org.eclipse.papyrus.web.application.properties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.papyrus.web.sirius.contributions.ServiceOverride;
import org.eclipse.sirius.components.collaborative.forms.api.IPropertiesDefaultDescriptionProvider;
import org.eclipse.sirius.components.compatibility.emf.properties.EEnumIfDescriptionProvider;
import org.eclipse.sirius.components.compatibility.emf.properties.EStringIfDescriptionProvider;
import org.eclipse.sirius.components.compatibility.emf.properties.NumberIfDescriptionProvider;
import org.eclipse.sirius.components.compatibility.emf.properties.PropertiesDefaultDescriptionProvider;
import org.eclipse.sirius.components.compatibility.emf.properties.api.IPropertiesValidationProvider;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.emf.services.messages.IEMFMessageService;
import org.eclipse.sirius.components.forms.description.AbstractControlDescription;
import org.eclipse.sirius.components.forms.description.ForDescription;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.forms.description.IfDescription;
import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.uml2.uml.Element;

/**
 * Custom implemenation of {@link PropertiesDefaultDescriptionProvider} to display an advanced view for UML properties.
 *
 * @see https://github.com/PapyrusSirius/papyrus-web/issues/58
 *
 * @author Arthur Daussy
 */

@ServiceOverride(PropertiesDefaultDescriptionProvider.class)
public class AdvancedPropertiesDescriptionProvider implements IPropertiesDefaultDescriptionProvider {

    public static final String ESTRUCTURAL_FEATURE = "eStructuralFeature";

    private final IObjectService objectService;

    private final ComposedAdapterFactory composedAdapterFactory;

    private final IPropertiesValidationProvider propertiesValidationProvider;

    private final IEMFMessageService emfMessageService;

    public AdvancedPropertiesDescriptionProvider(IObjectService objectService, ComposedAdapterFactory composedAdapterFactory, IEMFMessageService emfMessageService) {
        this.objectService = Objects.requireNonNull(objectService);
        this.composedAdapterFactory = Objects.requireNonNull(composedAdapterFactory);
        this.propertiesValidationProvider = new IPropertiesValidationProvider.NoOp(); // Unplug live validation
                                                                                      // validation
        this.emfMessageService = Objects.requireNonNull(emfMessageService);
    }

    @Override
    public FormDescription getFormDescription() {
        List<GroupDescription> groupDescriptions = new ArrayList<>();
        GroupDescription groupDescription = this.getGroupDescription();

        groupDescriptions.add(groupDescription);
        groupDescriptions.add(this.getStereotypeGroupDescription());

        List<PageDescription> pageDescriptions = new ArrayList<>();
        PageDescription firstPageDescription = this.getPageDescription(groupDescriptions);
        pageDescriptions.add(firstPageDescription);

        // @formatter:off
        Function<VariableManager, String> labelProvider = variableManager -> "Properties";
        // @formatter:on

        // @formatter:off
        Function<VariableManager, String> targetObjectIdProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(this.objectService::getId)
                .orElse(null);

        return FormDescription.newFormDescription(UUID.nameUUIDFromBytes("UMLAdvancedPropertyViewForm".getBytes()).toString()) //$NON-NLS-1$
                .label("Default form description")
                .idProvider(new GetOrCreateRandomIdProvider())
                .labelProvider(labelProvider)
                .targetObjectIdProvider(targetObjectIdProvider)
                .canCreatePredicate(variableManager -> false)
                .pageDescriptions(pageDescriptions)
                .build();
        // @formatter:on
    }

    private PageDescription getPageDescription(List<GroupDescription> groupDescriptions) {
        Function<VariableManager, String> idProvider = variableManager -> {
            var optionalSelf = variableManager.get(VariableManager.SELF, Object.class);
            if (optionalSelf.isPresent()) {
                Object self = optionalSelf.get();
                return this.objectService.getId(self);
            }
            return UUID.randomUUID().toString();
        };

        Function<VariableManager, String> labelProvider = variableManager -> "Advanced";

        // @formatter:off
        return PageDescription.newPageDescription("UMLAdvancedPropertyViewPage")
                .idProvider(idProvider)
                .labelProvider(labelProvider)
                .semanticElementsProvider(variableManager -> Collections.singletonList(variableManager.getVariables().get(VariableManager.SELF)))
                .groupDescriptions(groupDescriptions)
                .canCreatePredicate(variableManager -> true)
                .build();
        // @formatter:on
    }

    private GroupDescription getStereotypeGroupDescription() {
        List<AbstractControlDescription> controlDescriptions = new ArrayList<>();
        // @formatter:off
        ForDescription forDescriptionStereotypes = ForDescription.newForDescription("forElement") //$NON-NLS-1$
                .iterator("element")
                .iterableProvider(varMan -> this.getSelfElement(varMan))
                .ifDescriptions(List.of(new AppliedStereotypeIfDescriptionProvider(this.propertiesValidationProvider, this.objectService).getIfDescription()))
                .build();
        // @formatter:on

        controlDescriptions.add(forDescriptionStereotypes);

        // @formatter:off
        return GroupDescription.newGroupDescription("groupIdStereotypes") //$NON-NLS-1$
                .idProvider(variableManager -> "Core Properties Applied Stereotypes") //$NON-NLS-1$
                .labelProvider(variableManager -> "Stereotypes") //$NON-NLS-1$
                .semanticElementsProvider(variableManager -> Collections.singletonList(variableManager.getVariables().get(VariableManager.SELF)))
                .controlDescriptions(controlDescriptions)
                .build();
        // @formatter:on

    }

    private List<Element> getSelfElement(VariableManager varMan) {
        return varMan.get(VariableManager.SELF, Element.class).map(Collections::singletonList).orElse(Collections.emptyList());
    }

    private GroupDescription getGroupDescription() {
        List<AbstractControlDescription> controlDescriptions = new ArrayList<>();

        Function<VariableManager, List<?>> iterableProvider = variableManager -> {
            List<Object> objects = new ArrayList<>();

            Object self = variableManager.getVariables().get(VariableManager.SELF);
            if (self instanceof EObject) {
                EObject eObject = (EObject) self;

                // @formatter:off
                List<IItemPropertyDescriptor> propertyDescriptors = Optional.ofNullable(this.composedAdapterFactory.adapt(eObject, IItemPropertySource.class))
                        .filter(IItemPropertySource.class::isInstance)
                        .map(IItemPropertySource.class::cast)
                        .map(iItemPropertySource -> iItemPropertySource.getPropertyDescriptors(eObject))
                        .orElse(new ArrayList<>());

                propertyDescriptors.stream()
                        .map(propertyDescriptor -> propertyDescriptor.getFeature(eObject))
                        .filter(EStructuralFeature.class::isInstance)
                        .map(EStructuralFeature.class::cast)
                        // Prevents EReference targeting EModelElements and EObject. (https://github.com/PapyrusSirius/papyrus-web/issues/58)
                        // * It can return thousands of elements making the UI really slow
                        // * On some candidates an id cannot be computed (EPackage) causing NPE (nevertheless this case should be fixed in Sirius Component)
                        // https://github.com/eclipse-sirius/sirius-components/issues/1433
                        .filter(feature -> feature.getEType() != EcorePackage.eINSTANCE.getEObject() && feature.getEType() != EcorePackage.eINSTANCE.getEModelElement())
                        .forEach(objects::add);
                // @formatter:on
            }
            return objects;
        };

        List<IfDescription> ifDescriptions = new ArrayList<>();
        ifDescriptions.add(new EStringIfDescriptionProvider(this.composedAdapterFactory, this.propertiesValidationProvider).getIfDescription());
        ifDescriptions.add(new EBooleanIfDescriptionProvider(this.composedAdapterFactory, this.propertiesValidationProvider).getIfDescription());
        ifDescriptions.add(new EEnumIfDescriptionProvider(this.composedAdapterFactory, this.propertiesValidationProvider).getIfDescription());

        // Do not display derived feature for the moment
        ifDescriptions.add(new NonDerivedMonoValuedNonContainmentReferenceIfDescriptionProvider(this.composedAdapterFactory, this.objectService, this.propertiesValidationProvider).getIfDescription());
        ifDescriptions
                .add(new NonDerivedMultiValuedNonContainmentReferenceIfDescriptionProvider(this.composedAdapterFactory, this.objectService, this.propertiesValidationProvider).getIfDescription());

        // @formatter:off
        var numericDataTypes = List.of(
                EcorePackage.Literals.EINT,
                EcorePackage.Literals.EINTEGER_OBJECT,
                EcorePackage.Literals.EDOUBLE,
                EcorePackage.Literals.EDOUBLE_OBJECT,
                EcorePackage.Literals.EFLOAT,
                EcorePackage.Literals.EFLOAT_OBJECT,
                EcorePackage.Literals.ELONG,
                EcorePackage.Literals.ELONG_OBJECT,
                EcorePackage.Literals.ESHORT,
                EcorePackage.Literals.ESHORT_OBJECT
                );
        // @formatter:on
        for (var dataType : numericDataTypes) {
            ifDescriptions.add(new NumberIfDescriptionProvider(dataType, this.composedAdapterFactory, this.propertiesValidationProvider, this.emfMessageService).getIfDescription());
        }

        // @formatter:off
        ForDescription forDescription = ForDescription.newForDescription("forId")
                .iterator(ESTRUCTURAL_FEATURE)
                .iterableProvider(iterableProvider)
                .ifDescriptions(ifDescriptions)
                .build();
        // @formatter:on

        controlDescriptions.add(forDescription);

        // @formatter:off
        return GroupDescription.newGroupDescription("groupId")
                .idProvider(variableManager -> "Core Properties")
                .labelProvider(variableManager -> "Core Properties")
                .semanticElementsProvider(variableManager -> Collections.singletonList(variableManager.getVariables().get(VariableManager.SELF)))
                .controlDescriptions(controlDescriptions)
                .build();
        // @formatter:on
    }

}
