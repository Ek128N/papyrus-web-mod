/*****************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo.
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
package org.eclipse.papyrus.web.services.aqlservices.activity;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.uml2.uml.AcceptCallAction;
import org.eclipse.uml2.uml.AcceptEventAction;
import org.eclipse.uml2.uml.AddStructuralFeatureValueAction;
import org.eclipse.uml2.uml.BroadcastSignalAction;
import org.eclipse.uml2.uml.CallBehaviorAction;
import org.eclipse.uml2.uml.CallOperationAction;
import org.eclipse.uml2.uml.ClearAssociationAction;
import org.eclipse.uml2.uml.ClearStructuralFeatureAction;
import org.eclipse.uml2.uml.ConditionalNode;
import org.eclipse.uml2.uml.CreateLinkAction;
import org.eclipse.uml2.uml.CreateLinkObjectAction;
import org.eclipse.uml2.uml.CreateObjectAction;
import org.eclipse.uml2.uml.DestroyLinkAction;
import org.eclipse.uml2.uml.DestroyObjectAction;
import org.eclipse.uml2.uml.ExpansionRegion;
import org.eclipse.uml2.uml.LoopNode;
import org.eclipse.uml2.uml.OpaqueAction;
import org.eclipse.uml2.uml.ReadExtentAction;
import org.eclipse.uml2.uml.ReadIsClassifiedObjectAction;
import org.eclipse.uml2.uml.ReadLinkAction;
import org.eclipse.uml2.uml.ReadSelfAction;
import org.eclipse.uml2.uml.ReadStructuralFeatureAction;
import org.eclipse.uml2.uml.ReadVariableAction;
import org.eclipse.uml2.uml.ReclassifyObjectAction;
import org.eclipse.uml2.uml.ReduceAction;
import org.eclipse.uml2.uml.SendSignalAction;
import org.eclipse.uml2.uml.SequenceNode;
import org.eclipse.uml2.uml.StartClassifierBehaviorAction;
import org.eclipse.uml2.uml.StartObjectBehaviorAction;
import org.eclipse.uml2.uml.StructuredActivityNode;
import org.eclipse.uml2.uml.TestIdentityAction;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.UnmarshallAction;
import org.eclipse.uml2.uml.ValueSpecificationAction;
import org.eclipse.uml2.uml.util.UMLSwitch;

/**
 * This class provides an implementation of UMLSwitch to retrieve the feature to use according to the semantic context
 * and the type of the element to create.
 * <p>
 * Duplicated from Papyrus Sirius Desktop Repository: <a href=
 * "https://git.eclipse.org/r/plugins/gitiles/papyrus/org.eclipse.papyrus-sirius/+/refs/heads/master/plugins/uml/org.eclipse.papyrus.sirius.uml.diagram.activity/src/org/eclipse/papyrus/sirius/uml/diagram/activity/services/ActivityFeatureProvider.java">ActivityFeatureProvider</a>.
 * </p>
 *
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public class ActivityFeatureProvider {
    /**
     * Gets the the feature to use depending on the type of the element to create and the semantic context in which we
     * want to add it.
     *
     * @param semanticContext
     *            the semantic context in which we want to add the object to create
     * @param type
     *            the type of the element to create
     * @return the feature to use
     */
    public EStructuralFeature getActivityFeature(EObject semanticContext, String type) {
        EStructuralFeature feature = null;
        if (semanticContext != null) {
            feature = new ActivityFeatureProviderSwitch(type).doSwitch(semanticContext);
        }
        return feature;
    }

    /**
     * An implementation of {@link UMLSwitch} to retrieve the feature to use according to the semantic context and the
     * type of the element to create.
     *
     * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
     */
    public class ActivityFeatureProviderSwitch extends UMLSwitch<EStructuralFeature> {

        /**
         * The type of the element to create.
         */
        private final String type;

        /**
         * Constructor.
         *
         * @param type
         *            the type of the element to create
         */
        public ActivityFeatureProviderSwitch(String type) {
            super();
            this.type = type;
        }

        /**
         * When an OutputPin is created on an AcceptCallAction, the AcceptCallAction#result feature should be used.
         *
         * @param semanticContext
         *            the container object
         * @return the feature to use
         */
        @Override
        public EStructuralFeature caseAcceptCallAction(AcceptCallAction semanticContext) {
            EStructuralFeature feature = null;
            if (this.isOutputPinType()) {
                feature = UMLPackage.eINSTANCE.getAcceptEventAction_Result();
            }
            return feature;
        }

        /**
         * When an OutputPin is created on an AcceptEventAction, the AcceptEventAction#result feature should be used.
         *
         * @param semanticContext
         *            the container object
         * @return the feature to use
         */
        @Override
        public EStructuralFeature caseAcceptEventAction(AcceptEventAction semanticContext) {
            EStructuralFeature feature = null;
            if (this.isOutputPinType()) {
                feature = UMLPackage.eINSTANCE.getAcceptEventAction_Result();
            }
            return feature;
        }

        /**
         * When an OutputPin is created on an AddStructuralFeatureValueAction, the
         * AddStructuralFeatureValueAction#result feature should be used.
         *
         * @param semanticContext
         *            the container object
         * @return the feature to use
         */
        @Override
        public EStructuralFeature caseAddStructuralFeatureValueAction(AddStructuralFeatureValueAction semanticContext) {
            EStructuralFeature feature = null;
            if (this.isOutputPinType()) {
                feature = UMLPackage.eINSTANCE.getWriteStructuralFeatureAction_Result();
            }
            return feature;
        }

        /**
         * When an InputPin, ActionInputPin, or ValuePin is created on an BroadcastSignalAction, the
         * BroadcastSignalAction#argument feature should be used.
         *
         * @param semanticContext
         *            the container object
         * @return the feature to use
         */
        @Override
        public EStructuralFeature caseBroadcastSignalAction(BroadcastSignalAction semanticContext) {
            EStructuralFeature feature = null;
            if (this.isInputPinType()) {
                feature = UMLPackage.eINSTANCE.getInvocationAction_Argument();
            }
            return feature;
        }

        /**
         * When an InputPin, ActionInputPin, or ValuePin is created on an CallBehaviorAction, the
         * CallBehaviorAction#argument feature should be used. When an OutputPin is created on an CallBehaviorAction,
         * the CallBehaviorAction#result feature should be used.
         *
         * @param semanticContext
         *            the container object
         * @return the feature to use
         */
        @Override
        public EStructuralFeature caseCallBehaviorAction(CallBehaviorAction semanticContext) {
            EStructuralFeature feature = null;
            if (this.isInputPinType()) {
                feature = UMLPackage.eINSTANCE.getInvocationAction_Argument();
            } else if (this.isOutputPinType()) {
                feature = UMLPackage.eINSTANCE.getCallAction_Result();
            }
            return feature;
        }

        /**
         * When an InputPin, ActionInputPin, or ValuePin is created on an CallOperationAction, the
         * CallOperationAction#argument feature should be used. When an OutputPin is created on an CallOperationAction,
         * the CallOperationAction#result feature should be used.
         *
         * @param semanticContext
         *            the container object
         * @return the feature to use
         */
        @Override
        public EStructuralFeature caseCallOperationAction(CallOperationAction semanticContext) {
            EStructuralFeature feature = null;
            if (this.isInputPinType()) {
                feature = UMLPackage.eINSTANCE.getInvocationAction_Argument();
            } else if (this.isOutputPinType()) {
                feature = UMLPackage.eINSTANCE.getCallAction_Result();
            }
            return feature;
        }

        /**
         * When an InputPin, ActionInputPin, or ValuePin is created on an ClearAssociationAction, the
         * ClearAssociationAction#object feature should be used.
         *
         * @param semanticContext
         *            the container object
         * @return the feature to use
         */
        @Override
        public EStructuralFeature caseClearAssociationAction(ClearAssociationAction semanticContext) {
            EStructuralFeature feature = null;
            if (this.isInputPinType()) {
                feature = UMLPackage.eINSTANCE.getClearAssociationAction_Object();
            }
            return feature;
        }

        /**
         * When an InputPin, ActionInputPin, or ValuePin is created on an ClearStructuralFeatureAction, the
         * ClearStructuralFeatureAction#object feature should be used. When an OutputPin is created on an
         * ClearStructuralFeatureAction, the ClearStructuralFeatureAction#result feature should be used.
         *
         * @param semanticContext
         *            the container object
         * @return the feature to use
         */
        @Override
        public EStructuralFeature caseClearStructuralFeatureAction(ClearStructuralFeatureAction semanticContext) {
            EStructuralFeature feature = null;
            if (this.isInputPinType()) {
                feature = UMLPackage.eINSTANCE.getStructuralFeatureAction_Object();
            } else if (this.isOutputPinType()) {
                feature = UMLPackage.eINSTANCE.getClearStructuralFeatureAction_Result();
            }
            return feature;
        }

        /**
         * When an InputPin, ActionInputPin, or ValuePin is created on an ConditionalNode, the
         * ConditionalNode#structuredNodeInput feature should be used. When an OutputPin is created on an
         * ConditionalNode, the ConditionalNode#result feature should be used.
         *
         * @param semanticContext
         *            the container object
         * @return the feature to use
         */
        @Override
        public EStructuralFeature caseConditionalNode(ConditionalNode semanticContext) {
            EStructuralFeature feature = null;
            if (this.isInputPinType()) {
                feature = UMLPackage.eINSTANCE.getStructuredActivityNode_StructuredNodeInput();
            } else if (this.isOutputPinType()) {
                feature = UMLPackage.eINSTANCE.getConditionalNode_Result();
            }
            return feature;
        }

        /**
         * When an InputPin, ActionInputPin, or ValuePin is created on an CreateLinkAction, the
         * CreateLinkAction#inputValue feature should be used.
         *
         * @param semanticContext
         *            the container object
         * @return the feature to use
         */
        @Override
        public EStructuralFeature caseCreateLinkAction(CreateLinkAction semanticContext) {
            EStructuralFeature feature = null;
            if (this.isInputPinType()) {
                feature = UMLPackage.eINSTANCE.getLinkAction_InputValue();
            }
            return feature;
        }

        /**
         * When an InputPin, ActionInputPin, or ValuePin is created on an CreateLinkObjectAction, the
         * CreateLinkObjectAction#inputValue feature should be used. When an OutputPin is created on an
         * CreateLinkObjectAction, the CreateLinkObjectAction#result feature should be used.
         *
         * @param semanticContext
         *            the container object
         * @return the feature to use
         */
        @Override
        public EStructuralFeature caseCreateLinkObjectAction(CreateLinkObjectAction semanticContext) {
            EStructuralFeature feature = null;
            if (this.isInputPinType()) {
                feature = UMLPackage.eINSTANCE.getLinkAction_InputValue();
            } else if (this.isOutputPinType()) {
                feature = UMLPackage.eINSTANCE.getCreateLinkObjectAction_Result();
            }
            return feature;
        }

        /**
         * When an OutputPin is created on an CreateObjectAction, the CreateObjectAction#result feature should be used.
         *
         * @param semanticContext
         *            the container object
         * @return the feature to use
         */
        @Override
        public EStructuralFeature caseCreateObjectAction(CreateObjectAction semanticContext) {
            EStructuralFeature feature = null;
            if (this.isOutputPinType()) {
                feature = UMLPackage.eINSTANCE.getCreateObjectAction_Result();
            }
            return feature;
        }

        /**
         * When an InputPin, ActionInputPin, or ValuePin is created on an DestroyLinkAction, the
         * DestroyLinkAction#inputValue feature should be used.
         *
         * @param semanticContext
         *            the container object
         * @return the feature to use
         */
        @Override
        public EStructuralFeature caseDestroyLinkAction(DestroyLinkAction semanticContext) {
            EStructuralFeature feature = null;
            if (this.isInputPinType()) {
                feature = UMLPackage.eINSTANCE.getLinkAction_InputValue();
            }
            return feature;
        }

        /**
         * When an InputPin, ActionInputPin, or ValuePin is created on an DestroyObjectAction, the
         * DestroyObjectAction#target feature should be used.
         *
         * @param semanticContext
         *            the container object
         * @return the feature to use
         */
        @Override
        public EStructuralFeature caseDestroyObjectAction(DestroyObjectAction semanticContext) {
            EStructuralFeature feature = null;
            if (this.isInputPinType()) {
                feature = UMLPackage.eINSTANCE.getDestroyObjectAction_Target();
            }
            return feature;
        }

        /**
         * When an InputPin, ActionInputPin, or ValuePin is created on an ExpansionRegion, the
         * ExpansionRegion#structuredNodeInput feature should be used. When an OutputPin is created on an
         * ExpansionRegion, the ExpansionRegion#structuredNodeOutput feature should be used.
         *
         * @param semanticContext
         *            the container object
         * @return the feature to use
         */
        @Override
        public EStructuralFeature caseExpansionRegion(ExpansionRegion semanticContext) {
            EStructuralFeature feature = null;
            if (this.isInputPinType()) {
                feature = UMLPackage.eINSTANCE.getStructuredActivityNode_StructuredNodeInput();
            } else if (this.isOutputPinType()) {
                feature = UMLPackage.eINSTANCE.getStructuredActivityNode_StructuredNodeOutput();
            }
            return feature;
        }

        /**
         * When an InputPin, ActionInputPin, or ValuePin is created on an LoopNode, the LoopNode#loopVariableInput
         * feature should be used.
         *
         * @param semanticContext
         *            the container object
         * @return the feature to use
         */
        @Override
        public EStructuralFeature caseLoopNode(LoopNode semanticContext) {
            EStructuralFeature feature = null;
            if (this.isInputPinType()) {
                feature = UMLPackage.eINSTANCE.getLoopNode_LoopVariableInput();
            } else if (this.isOutputPinType()) {
                feature = UMLPackage.eINSTANCE.getLoopNode_Result();
            }
            return feature;
        }

        /**
         * When an InputPin, ActionInputPin, or ValuePin is created on an OpaqueAction, the OpaqueAction#inputValue
         * feature should be used. When an OutputPin is created on an OpaqueAction, the OpaqueAction#outputValue feature
         * should be used.
         *
         * @param semanticContext
         *            the container object
         * @return the feature to use
         */
        @Override
        public EStructuralFeature caseOpaqueAction(OpaqueAction semanticContext) {
            EStructuralFeature feature = null;
            if (this.isInputPinType()) {
                feature = UMLPackage.eINSTANCE.getOpaqueAction_InputValue();
            } else if (this.isOutputPinType()) {
                feature = UMLPackage.eINSTANCE.getOpaqueAction_OutputValue();
            }
            return feature;
        }

        /**
         * When an OutputPin is created on an ReadExtentAction, the ReadExtentAction#result feature should be used.
         *
         * @param semanticContext
         *            the container object
         * @return the feature to use
         */
        @Override
        public EStructuralFeature caseReadExtentAction(ReadExtentAction semanticContext) {
            EStructuralFeature feature = null;
            if (this.isOutputPinType()) {
                feature = UMLPackage.eINSTANCE.getReadExtentAction_Result();
            }
            return feature;
        }

        /**
         * When an InputPin, ActionInputPin, or ValuePin is created on an ReadIsClassifiedObjectAction, the
         * ReadIsClassifiedObjectAction#object feature should be used. When an OutputPin is created on an
         * ReadIsClassifiedObjectAction, the ReadIsClassifiedObjectAction#result feature should be used.
         *
         * @param semanticContext
         *            the container object
         * @return the feature to use
         */
        @Override
        public EStructuralFeature caseReadIsClassifiedObjectAction(ReadIsClassifiedObjectAction semanticContext) {
            EStructuralFeature feature = null;
            if (this.isInputPinType()) {
                feature = UMLPackage.eINSTANCE.getReadIsClassifiedObjectAction_Object();
            } else if (this.isOutputPinType()) {
                feature = UMLPackage.eINSTANCE.getReadIsClassifiedObjectAction_Result();
            }
            return feature;
        }

        /**
         * When an InputPin, ActionInputPin, or ValuePin is created on an ReadLinkAction, the ReadLinkAction#inputValue
         * feature should be used. When an OutputPin is created on an ReadLinkAction, the ReadLinkAction#result feature
         * should be used.
         *
         * @param semanticContext
         *            the container object
         * @return the feature to use
         */
        @Override
        public EStructuralFeature caseReadLinkAction(ReadLinkAction semanticContext) {
            EStructuralFeature feature = null;
            if (this.isInputPinType()) {
                feature = UMLPackage.eINSTANCE.getLinkAction_InputValue();
            } else if (this.isOutputPinType()) {
                feature = UMLPackage.eINSTANCE.getReadLinkAction_Result();
            }
            return feature;
        }

        /**
         * When an OutputPin is created on an ReadSelfAction, the ReadSelfAction#result feature should be used.
         *
         * @param semanticContext
         *            the container object
         * @return the feature to use
         */
        @Override
        public EStructuralFeature caseReadSelfAction(ReadSelfAction semanticContext) {
            EStructuralFeature feature = null;
            if (this.isOutputPinType()) {
                feature = UMLPackage.eINSTANCE.getReadSelfAction_Result();
            }
            return feature;
        }

        /**
         * When an InputPin, ActionInputPin, or ValuePin is created on an ReadStructuralFeatureAction, the
         * ReadStructuralFeatureAction#object feature should be used. When an OutputPin is created on an
         * ReadStructuralFeatureAction, the ReadStructuralFeatureAction#result feature should be used.
         *
         * @param semanticContext
         *            the container object
         * @return the feature to use
         */
        @Override
        public EStructuralFeature caseReadStructuralFeatureAction(ReadStructuralFeatureAction semanticContext) {
            EStructuralFeature feature = null;
            if (this.isInputPinType()) {
                feature = UMLPackage.eINSTANCE.getStructuralFeatureAction_Object();
            } else if (this.isOutputPinType()) {
                feature = UMLPackage.eINSTANCE.getReadStructuralFeatureAction_Result();
            }
            return feature;
        }

        /**
         * When an OutputPin is created on an ReadVariableAction, the ReadVariableAction#result feature should be used.
         *
         * @param semanticContext
         *            the container object
         * @return the feature to use
         */
        @Override
        public EStructuralFeature caseReadVariableAction(ReadVariableAction semanticContext) {
            EStructuralFeature feature = null;
            if (this.isOutputPinType()) {
                feature = UMLPackage.eINSTANCE.getReadVariableAction_Result();
            }
            return feature;
        }

        /**
         * When an InputPin, ActionInputPin, or ValuePin is created on an ReclassifyObjectAction, the
         * ReclassifyObjectAction#object feature should be used.
         *
         * @param semanticContext
         *            the container object
         * @return the feature to use
         */
        @Override
        public EStructuralFeature caseReclassifyObjectAction(ReclassifyObjectAction semanticContext) {
            EStructuralFeature feature = null;
            if (this.isInputPinType()) {
                feature = UMLPackage.eINSTANCE.getReclassifyObjectAction_Object();
            }
            return feature;
        }

        /**
         * When an InputPin, ActionInputPin, or ValuePin is created on an ReduceAction, the ReduceAction#collection
         * feature should be used. When an OutputPin is created on an ReduceAction, the ReduceAction#result feature
         * should be used.
         *
         * @param semanticContext
         *            the container object
         * @return the feature to use
         */
        @Override
        public EStructuralFeature caseReduceAction(ReduceAction semanticContext) {
            EStructuralFeature feature = null;
            if (this.isInputPinType()) {
                feature = UMLPackage.eINSTANCE.getReduceAction_Collection();
            } else if (this.isOutputPinType()) {
                feature = UMLPackage.eINSTANCE.getReduceAction_Result();
            }
            return feature;
        }

        /**
         * When an InputPin, ActionInputPin, or ValuePin is created on an SendSignalAction, the
         * SendSignalAction#argument feature should be used.
         *
         * @param semanticContext
         *            the container object
         * @return the feature to use
         */
        @Override
        public EStructuralFeature caseSendSignalAction(SendSignalAction semanticContext) {
            EStructuralFeature feature = null;
            if (this.isInputPinType()) {
                feature = UMLPackage.eINSTANCE.getInvocationAction_Argument();
            }
            return feature;
        }

        /**
         * When an InputPin, ActionInputPin, or ValuePin is created on an SequenceNode, the
         * SequenceNode#structuredNodeInput feature should be used. When an OutputPin is created on an SequenceNode, the
         * SequenceNode#structuredNodeOutput feature should be used.
         *
         * @param semanticContext
         *            the container object
         * @return the feature to use
         */
        @Override
        public EStructuralFeature caseSequenceNode(SequenceNode semanticContext) {
            EStructuralFeature feature = null;
            if (this.isInputPinType()) {
                feature = UMLPackage.eINSTANCE.getStructuredActivityNode_StructuredNodeInput();
            } else if (this.isOutputPinType()) {
                feature = UMLPackage.eINSTANCE.getStructuredActivityNode_StructuredNodeOutput();
            }
            return feature;
        }

        /**
         * When an InputPin, ActionInputPin, or ValuePin is created on an StartClassifierBehaviorAction, the
         * StartClassifierBehaviorAction#object feature should be used.
         *
         * @param semanticContext
         *            the container object
         * @return the feature to use
         */
        @Override
        public EStructuralFeature caseStartClassifierBehaviorAction(StartClassifierBehaviorAction semanticContext) {
            EStructuralFeature feature = null;
            if (this.isInputPinType()) {
                feature = UMLPackage.eINSTANCE.getStartClassifierBehaviorAction_Object();
            }
            return feature;
        }

        /**
         * When an InputPin, ActionInputPin, or ValuePin is created on an StartObjectBehaviorAction, the
         * StartObjectBehaviorAction#argument feature should be used. When an OutputPin is created on an
         * StartObjectBehaviorAction, the StartObjectBehaviorAction#result feature should be used.
         *
         * @param semanticContext
         *            the container object
         * @return the feature to use
         */
        @Override
        public EStructuralFeature caseStartObjectBehaviorAction(StartObjectBehaviorAction semanticContext) {
            EStructuralFeature feature = null;
            if (this.isInputPinType()) {
                feature = UMLPackage.eINSTANCE.getInvocationAction_Argument();
            } else if (this.isOutputPinType()) {
                feature = UMLPackage.eINSTANCE.getCallAction_Result();
            }
            return feature;
        }

        /**
         * When an InputPin, ActionInputPin, or ValuePin is created on an StructuredActivityNode, the
         * StructuredActivityNode#structuredNodeInput feature should be used. When an OutputPin is created on an
         * StructuredActivityNode, the StructuredActivityNode#structuredNodeOutput feature should be used.
         *
         * @param semanticContext
         *            the container object
         * @return the feature to use
         */
        @Override
        public EStructuralFeature caseStructuredActivityNode(StructuredActivityNode semanticContext) {
            EStructuralFeature feature = null;
            if (this.isInputPinType()) {
                feature = UMLPackage.eINSTANCE.getStructuredActivityNode_StructuredNodeInput();
            } else if (this.isOutputPinType()) {
                feature = UMLPackage.eINSTANCE.getStructuredActivityNode_StructuredNodeOutput();
            }
            return feature;
        }

        /**
         * When an OutputPin is created on an TestIdentityAction, the TestIdentityAction#result feature should be used.
         *
         * @param semanticContext
         *            the container object
         * @return the feature to use
         */
        @Override
        public EStructuralFeature caseTestIdentityAction(TestIdentityAction semanticContext) {
            EStructuralFeature feature = null;
            if (this.isOutputPinType()) {
                feature = UMLPackage.eINSTANCE.getTestIdentityAction_Result();
            }
            return feature;
        }

        /**
         * When an InputPin, ActionInputPin, or ValuePin is created on an UnmarshallAction, the UnmarshallAction#object
         * feature should be used. When an OutputPin is created on an UnmarshallAction, the UnmarshallAction#result
         * feature should be used.
         *
         * @param semanticContext
         *            the container object
         * @return the feature to use
         */
        @Override
        public EStructuralFeature caseUnmarshallAction(UnmarshallAction semanticContext) {
            EStructuralFeature feature = null;
            if (this.isInputPinType()) {
                feature = UMLPackage.eINSTANCE.getUnmarshallAction_Object();
            } else if (this.isOutputPinType()) {
                feature = UMLPackage.eINSTANCE.getUnmarshallAction_Result();
            }
            return feature;
        }

        /**
         * When an OutputPin is created on an ValueSpecificationAction, the ValueSpecificationAction#result feature
         * should be used.
         *
         * @param semanticContext
         *            the container object
         * @return the feature to use
         */
        @Override
        public EStructuralFeature caseValueSpecificationAction(ValueSpecificationAction semanticContext) {
            EStructuralFeature feature = null;
            if (this.isOutputPinType()) {
                feature = UMLPackage.eINSTANCE.getValueSpecificationAction_Result();
            }
            return feature;
        }

        /**
         * Return <code>true</code> if the type is a kind of InputPin.
         *
         * @return <code>true</code> if the type is a kind of InputPin; <code>false</code> otherwise.
         */
        private boolean isInputPinType() {
            return UMLPackage.eINSTANCE.getActionInputPin().getName().equals(this.type) || UMLPackage.eINSTANCE.getInputPin().getName().equals(this.type)
                    || UMLPackage.eINSTANCE.getValuePin().getName().equals(this.type);
        }

        /**
         * Return <code>true</code> if the type is an OutputPin.
         *
         * @return <code>true</code> if the type is an OutputPin; <code>false</code> otherwise.
         */
        private boolean isOutputPinType() {
            return UMLPackage.eINSTANCE.getOutputPin().getName().equals(this.type);
        }
    }
}
