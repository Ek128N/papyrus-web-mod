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
package org.eclipse.papyrus.web.custom.widgets.containmentreference;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.papyrus.uml.domain.services.EMFUtils;
import org.eclipse.papyrus.web.custom.widgets.IAQLInterpreterProvider;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ContainmentReferenceWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage;
import org.eclipse.sirius.components.core.URLParser;
import org.eclipse.sirius.components.core.api.ChildCreationDescription;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.SemanticKindConstants;
import org.eclipse.sirius.components.emf.services.api.IEMFKindService;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.IRepresentationDescriptionIdProvider;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.OperationInterpreter;
import org.eclipse.sirius.components.view.emf.form.IFormIdProvider;
import org.eclipse.sirius.components.widget.reference.IReferenceWidgetCreateElementHandler;
import org.eclipse.sirius.web.services.editingcontext.EditingContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * Containment reference implementation of {@link IReferenceWidgetCreateElementHandler}.
 *
 * @author Jerome Gout
 */
@Service
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ContainmentReferenceCreateElementHandler implements IReferenceWidgetCreateElementHandler {

    private final IEMFKindService emfKindService;

    private final IObjectService objectService;

    private final IViewRepresentationDescriptionSearchService viewSearchService;

    private final IAQLInterpreterProvider interpreterProvider;

    private final IEditService editService;

    public ContainmentReferenceCreateElementHandler(IEMFKindService emfKindService, IObjectService objectService, IViewRepresentationDescriptionSearchService viewSearchService, IAQLInterpreterProvider interpreterProvider, IEditService editService) {
        this.emfKindService = Objects.requireNonNull(emfKindService);
        this.objectService = Objects.requireNonNull(objectService);
        this.viewSearchService = Objects.requireNonNull(viewSearchService);
        this.interpreterProvider = Objects.requireNonNull(interpreterProvider);
        this.editService = Objects.requireNonNull(editService);
    }

    @Override
    public boolean canHandle(String descriptionId) {
        if (descriptionId != null && descriptionId.startsWith(IFormIdProvider.FORM_ELEMENT_DESCRIPTION_PREFIX)) {
            Map<String, List<String>> parameters = new URLParser().getParameterValues(descriptionId);
            String sourceKind = parameters.get(IRepresentationDescriptionIdProvider.SOURCE_KIND).get(0);
            String kind = parameters.get(IRepresentationDescriptionIdProvider.KIND).get(0);
            return PapyrusWidgetsPackage.eINSTANCE.getContainmentReferenceWidgetDescription().getName().equals(kind) && IRepresentationDescriptionIdProvider.VIEW_SOURCE_KIND.equals(sourceKind);
        } else {
            return false;
        }
    }

    @Override
    public List<ChildCreationDescription> getRootCreationDescriptions(IEditingContext editingContext, String domainId, String referenceKind, String descriptionId) {
        // No creation at resource level is allowed in Papyrus
        return List.of();
    }

    @Override
    public List<ChildCreationDescription> getChildCreationDescriptions(IEditingContext editingContext, String kind, String referenceKind, String descriptionId) {
        return this.getInstanciableTypesOf(editingContext, referenceKind) //
                .stream() //
                .map(type -> this.createChildCreationDescription(type)) //
                .sorted(Comparator.comparing(ChildCreationDescription::getLabel)) //
                .toList();
    }

    private ChildCreationDescription createChildCreationDescription(EClass eClass) {
        String id = eClass.getEPackage().getName() + "::" + eClass.getName();
        List<String> iconURL = this.objectService.getImagePath(eClass.getEPackage().getEFactoryInstance().create(eClass));
        String label = eClass.getName();
        return new ChildCreationDescription(id, label, iconURL);
    }

    private List<EClass> getInstanciableTypesOf(IEditingContext editingContext, String referenceKind) {
        Optional<Registry> optionalRegistry = this.getPackageRegistry(editingContext);
        if (optionalRegistry.isPresent() && !referenceKind.isBlank() && referenceKind.startsWith(SemanticKindConstants.PREFIX)) {
            var ePackageRegistry = optionalRegistry.get();
            String ePackageName = this.emfKindService.getEPackageName(referenceKind);
            String eClassName = this.emfKindService.getEClassName(referenceKind);
            Optional<EPackage> optionalPackage = this.emfKindService.findEPackage(ePackageRegistry, ePackageName);
            if (optionalPackage.isPresent()) {
                var pack = optionalPackage.get();
                return this.collectNonAbstractTypes(eClassName, pack);
            }
        }
        return List.of();
    }

    private List<EClass> collectNonAbstractTypes(String eClassName, EPackage pack) {
        EClassifier targetType = pack.getEClassifier(eClassName);
        if (targetType instanceof EClass targetEClass) {
            return EMFUtils.allContainedObjectOfType(targetType.getEPackage(), EClass.class).filter(eClass -> this.isNonAbstractSubTypes(targetEClass, eClass)).toList();
        }
        return List.of();
    }

    private boolean isNonAbstractSubTypes(EClass targetEClass, EClass eClass) {
        return !eClass.isAbstract() && !eClass.isInterface() && targetEClass.isSuperTypeOf(eClass);
    }

    private Optional<Registry> getPackageRegistry(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext) {
            Registry packageRegistry = ((EditingContext) editingContext).getDomain().getResourceSet().getPackageRegistry();
            return Optional.of(packageRegistry);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Object> createRootObject(IEditingContext editingContext, UUID documentId, String domainId, String rootObjectCreationDescriptionId, String descriptionId) {
        return Optional.empty();
    }

    @Override
    public Optional<Object> createChild(IEditingContext editingContext, Object parent, String childCreationDescriptionId, String descriptionId) {
        var optionalWidgetDescription = this.viewSearchService.findViewFormElementDescriptionById(editingContext, descriptionId).filter(ContainmentReferenceWidgetDescription.class::isInstance).map(ContainmentReferenceWidgetDescription.class::cast);
        if (optionalWidgetDescription.isPresent()) {
            var reference = optionalWidgetDescription.get();
            var optionalView = this.getViewFromWidgetDescription(reference);
            if (optionalView.isPresent() && reference.getName() != null) {
                var createOperation = reference.getCreateElementOperation();
                VariableManager variableManager = this.createVariableManagerForElementCreation(parent, childCreationDescriptionId, reference.getName());
                VariableManager childVariableManager = variableManager.createChild();
                AQLInterpreter interpreter = this.interpreterProvider.createInterpreter(optionalView.get(), editingContext);
                OperationInterpreter operationInterpreter = new OperationInterpreter(interpreter, this.editService);
                Optional<VariableManager> optionalVariableManager = operationInterpreter.executeOperations(createOperation.getBody(), childVariableManager);
                if (optionalVariableManager.isPresent()) {
                    Optional<Object> res = optionalVariableManager.get().get(VariableManager.SELF, Object.class);
                    return res;
                }
            }
        }
        return Optional.empty();
    }

    private VariableManager createVariableManagerForElementCreation(Object parent, String creationDescriptionId, String name) {
        var variableManager = new VariableManager();
        variableManager.put("parent", parent);
        variableManager.put("kind", creationDescriptionId);
        variableManager.put("feature", name);
        return variableManager;
    }

    private Optional<View> getViewFromWidgetDescription(ContainmentReferenceWidgetDescription widgetDescription) {
        EObject container = widgetDescription;
        while (!(container instanceof View) && container != null) {
            container = container.eContainer();
        }
        if (container instanceof View view) {
            return Optional.of(view);
        }
        return Optional.empty();
    }
}
