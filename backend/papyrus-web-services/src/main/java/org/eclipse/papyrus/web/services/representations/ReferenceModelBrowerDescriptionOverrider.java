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
package org.eclipse.papyrus.web.services.representations;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.uml.domain.services.profile.StereotypeUtil;
import org.eclipse.sirius.components.collaborative.widget.reference.browser.ModelBrowsersDescriptionProvider;
import org.eclipse.sirius.components.collaborative.widget.reference.browser.ReferenceWidgetDefaultCandidateSearchProvider;
import org.eclipse.sirius.components.core.URLParser;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.emf.services.api.IEMFKindService;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.eclipse.sirius.components.widget.reference.IReferenceWidgetRootCandidateSearchProvider;
import org.eclipse.sirius.web.services.editingcontext.EditingContext;
import org.eclipse.uml2.uml.Element;
import org.springframework.stereotype.Service;

/**
 * Override default representation
 * {@link org.eclipse.sirius.components.collaborative.widget.reference.browser.ModelBrowsersDescriptionProvider#REFERENCE_DESCRIPTION_ID}.
 *
 * <p>
 * This interface has been created for bug
 * https://gitlab.eclipse.org/eclipse/papyrus/org.eclipse.papyrus-web/-/issues/97. But once
 * https://github.com/eclipse-sirius/sirius-web/issues/2809 is fixed this is no longer needed
 * </p>
 *
 * @author Arthur Daussy
 */
// Most of this call has been copied from
// org.eclipse.sirius.components.collaborative.widget.reference.browser.ModelBrowsersDescriptionProvider
@Service
public class ReferenceModelBrowerDescriptionOverrider implements IRepresentationDescriptionOverrider {
    public static final String DOCUMENT_KIND = "siriusWeb://document";

    public static final String TREE_KIND = "modelBrowser://";

    public static final String REFERENCE_DESCRIPTION_ID = UUID.nameUUIDFromBytes("model_browser_reference_tree_description".getBytes()).toString();

    private static final String TARGET_TYPE = "targetType";

    private final IObjectService objectService;

    private final ReferenceWidgetDefaultCandidateSearchProvider defaultCandidateProvider;

    private final IEMFKindService emfKindService;

    private final List<IReferenceWidgetRootCandidateSearchProvider> candidateProviders;

    private ModelBrowsersDescriptionProvider modelBrowserDescriptionProvider;

    public ReferenceModelBrowerDescriptionOverrider(IObjectService objectService, IEMFKindService emfKindService, List<IReferenceWidgetRootCandidateSearchProvider> candidateProviders,
            ModelBrowsersDescriptionProvider modelBrowserDescriptionProvider) {
        super();
        this.objectService = objectService;
        this.defaultCandidateProvider = new ReferenceWidgetDefaultCandidateSearchProvider();
        this.emfKindService = emfKindService;
        this.candidateProviders = candidateProviders;
        this.modelBrowserDescriptionProvider = Objects.requireNonNull(modelBrowserDescriptionProvider);
    }

    @Override
    public List<IRepresentationDescription> getOverridedDescriptions() {
        TreeDescription description = this.modelBrowserDescriptionProvider.getModelBrowserDescription(REFERENCE_DESCRIPTION_ID, variableManager -> this.canCreateModelBrowser(variableManager),
                this.browserIsSelectableProvider(), this::getSearchScopeElements);
        return List.of(description);
    }

    private Function<VariableManager, Boolean> browserIsSelectableProvider() {
        return variableManager -> {

            // Customization happens here. If the target type is a stereotype use a custom predicate
            if (this.isStereotypePredicate(variableManager)) {
                return this.buildStereotypePredicate(variableManager);
            } else {
                EClass targetType = this.resolveTargetType(variableManager).orElse(null);
                boolean isContainment = this.resolveIsContainment(variableManager);
                return this.isTypeSelectable(variableManager, targetType, isContainment);
            }

        };
    }

    private Boolean canCreateModelBrowser(VariableManager variableManager) {
        return variableManager.get("treeId", String.class).map(treeId -> treeId.startsWith("modelBrowser://reference")).orElse(false);
    }

    private boolean buildStereotypePredicate(VariableManager variableManager) {

        Predicate<Element> elementFilter = this.getStereotypeQualifiedName(variableManager);
        var optionalSelf = variableManager.get(VariableManager.SELF, EObject.class);
        if (optionalSelf.isPresent() && elementFilter != null) {
            EObject self = optionalSelf.get();
            return self instanceof Element element && elementFilter.test(element);
        }

        return false;
    }

    private Predicate<Element> getStereotypeQualifiedName(VariableManager variableManager) {
        var optionalTreeId = variableManager.get(GetOrCreateRandomIdProvider.PREVIOUS_REPRESENTATION_ID, String.class);
        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEMFEditingContext.class);
        if (optionalTreeId.isPresent() && optionalTreeId.get().startsWith(TREE_KIND) && optionalEditingContext.isPresent()) {
            Map<String, List<String>> parameters = new URLParser().getParameterValues(optionalTreeId.get());
            String targetType = parameters.get(TARGET_TYPE).get(0);

            String ePackageName = this.emfKindService.getEPackageName(targetType);
            String eClassName = this.emfKindService.getEClassName(targetType);

            // We need this to handle case where stereotype are named with non compliant EMF EClass name (for example
            // when using a - in there name)
            return element -> this.hasStereotypeMatchingEClass(ePackageName, eClassName, element);
        } else {
            return null;
        }
    }

    private boolean hasStereotypeMatchingEClass(String ePackageName, String eClassName, Element element) {
        return element.getAppliedStereotypes().stream().anyMatch(s -> {
            EClass def = s.getDefinition();
            return def != null && def.getName().equals(eClassName) && def.getEPackage().getName().equals(ePackageName);
        });
    }

    private boolean isStereotypePredicate(VariableManager variableManager) {
        var optionalTreeId = variableManager.get(GetOrCreateRandomIdProvider.PREVIOUS_REPRESENTATION_ID, String.class);
        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEMFEditingContext.class);
        if (optionalTreeId.isPresent() && optionalTreeId.get().startsWith(TREE_KIND) && optionalEditingContext.isPresent()) {
            Map<String, List<String>> parameters = new URLParser().getParameterValues(optionalTreeId.get());
            String kind = parameters.get(TARGET_TYPE).get(0);
            Registry ePackageRegistry = optionalEditingContext.get().getDomain().getResourceSet().getPackageRegistry();

            String ePackageName = this.emfKindService.getEPackageName(kind);
            String eClassName = this.emfKindService.getEClassName(kind);

            // Has a EReference starting with base_ and pointing a an UML element
            Optional<EClass> targetEClass = this.modelBrowserDescriptionProvider.findEPackage(ePackageRegistry, ePackageName).map(ePackage -> ePackage.getEClassifier(eClassName))
                    .filter(EClass.class::isInstance).map(EClass.class::cast);

            return targetEClass.map(StereotypeUtil::getBaseReferences).map(s -> s.count() > 0).orElse(false);
        } else {
            return false;
        }
    }

    private boolean isTypeSelectable(VariableManager variableManager, EClass targetType, boolean isContainment) {
        var optionalSelf = variableManager.get(VariableManager.SELF, EObject.class);
        if (optionalSelf.isPresent() && targetType != null) {
            return targetType.isInstance(optionalSelf.get())
                    && this.resolveOwnerEObject(variableManager).map(eObject -> !(isContainment && EcoreUtil.isAncestor(optionalSelf.get(), eObject))).orElse(true);
        } else {
            return false;
        }
    }

    private Optional<EObject> resolveOwnerEObject(VariableManager variableManager) {
        var optionalTreeId = variableManager.get(GetOrCreateRandomIdProvider.PREVIOUS_REPRESENTATION_ID, String.class);
        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, EditingContext.class);
        if (optionalTreeId.isPresent() && optionalTreeId.get().startsWith(TREE_KIND) && optionalEditingContext.isPresent()) {
            Map<String, List<String>> parameters = new URLParser().getParameterValues(optionalTreeId.get());
            String ownerId = parameters.get("ownerId").get(0);

            return this.objectService.getObject(optionalEditingContext.get(), ownerId).filter(EObject.class::isInstance).map(EObject.class::cast);
        } else {
            return Optional.empty();
        }
    }

    private List<? extends Object> getSearchScopeElements(VariableManager variableManager) {
        var optionalTreeId = variableManager.get(GetOrCreateRandomIdProvider.PREVIOUS_REPRESENTATION_ID, String.class);
        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, EditingContext.class);
        if (optionalTreeId.isPresent() && optionalTreeId.get().startsWith(TREE_KIND) && optionalEditingContext.isPresent()) {
            Map<String, List<String>> parameters = new URLParser().getParameterValues(optionalTreeId.get());
            String descriptionId = parameters.get("descriptionId").get(0);
            String ownerId = parameters.get("ownerId").get(0);
            var semanticOwner = this.objectService.getObject(optionalEditingContext.get(), ownerId).get();

            return this.candidateProviders.stream().filter(provider -> provider.canHandle(descriptionId)).findFirst().orElse(this.defaultCandidateProvider).getRootElementsForReference(semanticOwner,
                    descriptionId, optionalEditingContext.get());
        }
        return Collections.emptyList();
    }

    private Optional<EClass> resolveTargetType(VariableManager variableManager) {
        var optionalTreeId = variableManager.get(GetOrCreateRandomIdProvider.PREVIOUS_REPRESENTATION_ID, String.class);
        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, EditingContext.class);
        if (optionalTreeId.isPresent() && optionalTreeId.get().startsWith(TREE_KIND) && optionalEditingContext.isPresent()) {
            Registry ePackageRegistry = optionalEditingContext.get().getDomain().getResourceSet().getPackageRegistry();
            Map<String, List<String>> parameters = new URLParser().getParameterValues(optionalTreeId.get());
            String kind = parameters.get(TARGET_TYPE).get(0);

            String ePackageName = this.emfKindService.getEPackageName(kind);
            String eClassName = this.emfKindService.getEClassName(kind);

            return this.modelBrowserDescriptionProvider.findEPackage(ePackageRegistry, ePackageName).map(ePackage -> ePackage.getEClassifier(eClassName)).filter(EClass.class::isInstance)
                    .map(EClass.class::cast);
        } else {
            return Optional.empty();
        }
    }

    private boolean resolveIsContainment(VariableManager variableManager) {
        var optionalTreeId = variableManager.get(GetOrCreateRandomIdProvider.PREVIOUS_REPRESENTATION_ID, String.class);
        if (optionalTreeId.isPresent() && optionalTreeId.get().startsWith(TREE_KIND)) {
            Map<String, List<String>> parameters = new URLParser().getParameterValues(optionalTreeId.get());
            String isContainment = parameters.get("isContainment").get(0);
            return Boolean.parseBoolean(isContainment);
        } else {
            return false;
        }
    }

}
