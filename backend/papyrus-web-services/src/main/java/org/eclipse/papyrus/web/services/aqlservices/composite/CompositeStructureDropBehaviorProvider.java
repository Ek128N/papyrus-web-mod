/*******************************************************************************
 * Copyright (c) 2022 CEA, Obeo
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.web.services.aqlservices.composite;

import java.util.Objects;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.papyrus.uml.domain.services.IEditableChecker;
import org.eclipse.papyrus.uml.domain.services.modify.ElementFeatureModifier;
import org.eclipse.papyrus.web.services.aqlservices.IWebExternalSourceToRepresentationDropBehaviorProvider;
import org.eclipse.papyrus.web.services.aqlservices.utils.GenericDropOnDiagramSwitch;
import org.eclipse.papyrus.web.services.aqlservices.utils.GenericDropOnNodeSwitch;
import org.eclipse.papyrus.web.services.aqlservices.utils.IViewCreationHelper;
import org.eclipse.papyrus.web.sirius.contributions.DiagramNavigator;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Provides the behavior on a drop event in the "Composite Structure Diagram".
 *
 * @author Arthur Daussy
 */
public class CompositeStructureDropBehaviorProvider implements IWebExternalSourceToRepresentationDropBehaviorProvider {

    private final IEditingContext editionContext;

    private final IViewCreationHelper viewHelper;

    private final IObjectService objectService;

    private final ECrossReferenceAdapter crossRef;

    private final IEditableChecker editableChecker;

    private DiagramNavigator diagramNavigator;

    public CompositeStructureDropBehaviorProvider(IEditingContext editionContext, IViewCreationHelper viewHelper, IObjectService objectService, ECrossReferenceAdapter crossRef,
            IEditableChecker editableChecker, DiagramNavigator diagramNavigator) {
        this.diagramNavigator = Objects.requireNonNull(diagramNavigator);
        this.crossRef = Objects.requireNonNull(crossRef);
        this.editableChecker = Objects.requireNonNull(editableChecker);
        this.editionContext = Objects.requireNonNull(editionContext);
        this.viewHelper = Objects.requireNonNull(viewHelper);
        this.objectService = Objects.requireNonNull(objectService);
    }

    /**
     * Handle a drop event.
     *
     * @param droppedElement
     *            the dropped element
     * @param targetNode
     *            the target node or <code>null</code> if the drop occurred on the diagram
     */
    @Override
    public void handleDrop(EObject droppedElement, org.eclipse.sirius.components.diagrams.Node targetNode) {
        if (targetNode != null) {
            new DropOnNodeSwitch(targetNode, this.viewHelper, this::getSemanticObject, this.crossRef, this.editableChecker, this.diagramNavigator).doSwitch(droppedElement);
        } else {
            new GenericDropOnDiagramSwitch(this.viewHelper).doSwitch(droppedElement);
        }
    }

    private Object getSemanticObject(String id) {
        return this.objectService.getObject(this.editionContext, id).orElse(null);

    }

    private static final class DropOnNodeSwitch extends GenericDropOnNodeSwitch {

        private final Function<String, Object> eObjectResolver;

        private final ECrossReferenceAdapter crossRef;

        private final IEditableChecker editableChecker;

        private DropOnNodeSwitch(Node selectedNode, IViewCreationHelper viewHelper, Function<String, Object> eObjectResolver, ECrossReferenceAdapter crossRef, IEditableChecker editableChecker,
                DiagramNavigator diagramNavigator) {
            super(selectedNode, viewHelper, diagramNavigator);
            this.eObjectResolver = eObjectResolver;
            this.crossRef = crossRef;
            this.editableChecker = editableChecker;
        }

        @Override
        public Boolean caseType(Type type) {
            Object semanticTarget = this.getSemanticTarget();
            if (semanticTarget instanceof Property) {
                Property targetProperty = (Property) semanticTarget;
                new ElementFeatureModifier(this.crossRef, this.editableChecker).setValue(targetProperty, UMLPackage.eINSTANCE.getTypedElement_Type().getName(), type);
                return Boolean.TRUE;
            }
            return super.caseType(type);
        }

        private Object getSemanticTarget() {
            return this.eObjectResolver.apply(this.getSelectedNode().getTargetObjectId());

        }
    }

}
