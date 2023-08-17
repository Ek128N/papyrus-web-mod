/*****************************************************************************
 * Copyright (c) 2024 CEA LIST, Obeo.
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
package org.eclipse.papyrus.web.application.services.activity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrus.web.application.utils.AbstractWebUMLTest;
import org.eclipse.papyrus.web.services.aqlservices.activity.ActivityFeatureProvider;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Test class for {@link ActivityFeatureProvider}.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
@SpringBootTest
@WebAppConfiguration
public class ActivityFeatureProviderTests extends AbstractWebUMLTest {

    private ActivityFeatureProvider activityFeatureProvider;

    @BeforeEach
    public void setup() {
        this.activityFeatureProvider = new ActivityFeatureProvider();
    }

    private static Stream<Arguments> featureParameters() {
        return Stream.of(//
                Arguments.of(null, null, null), //
                Arguments.of(AcceptCallAction.class, UMLPackage.eINSTANCE.getAcceptEventAction_Result(), null), //
                Arguments.of(AcceptEventAction.class, UMLPackage.eINSTANCE.getAcceptEventAction_Result(), null), //
                Arguments.of(AddStructuralFeatureValueAction.class, UMLPackage.eINSTANCE.getWriteStructuralFeatureAction_Result(), null), //
                Arguments.of(BroadcastSignalAction.class, null, UMLPackage.eINSTANCE.getInvocationAction_Argument()), //
                Arguments.of(CallBehaviorAction.class, UMLPackage.eINSTANCE.getCallAction_Result(), UMLPackage.eINSTANCE.getInvocationAction_Argument()), //
                Arguments.of(CallOperationAction.class, UMLPackage.eINSTANCE.getCallAction_Result(), UMLPackage.eINSTANCE.getInvocationAction_Argument()), //
                Arguments.of(ClearAssociationAction.class, null, UMLPackage.eINSTANCE.getClearAssociationAction_Object()), //
                Arguments.of(ClearStructuralFeatureAction.class, UMLPackage.eINSTANCE.getClearStructuralFeatureAction_Result(), UMLPackage.eINSTANCE.getStructuralFeatureAction_Object()), //
                Arguments.of(ConditionalNode.class, UMLPackage.eINSTANCE.getConditionalNode_Result(), UMLPackage.eINSTANCE.getStructuredActivityNode_StructuredNodeInput()), //
                Arguments.of(CreateLinkAction.class, null, UMLPackage.eINSTANCE.getLinkAction_InputValue()), //
                Arguments.of(CreateLinkObjectAction.class, UMLPackage.eINSTANCE.getCreateLinkObjectAction_Result(), UMLPackage.eINSTANCE.getLinkAction_InputValue()), //
                Arguments.of(CreateObjectAction.class, UMLPackage.eINSTANCE.getCreateObjectAction_Result(), null), //
                Arguments.of(DestroyLinkAction.class, null, UMLPackage.eINSTANCE.getLinkAction_InputValue()), //
                Arguments.of(DestroyObjectAction.class, null, UMLPackage.eINSTANCE.getDestroyObjectAction_Target()), //
                Arguments.of(ExpansionRegion.class, UMLPackage.eINSTANCE.getStructuredActivityNode_StructuredNodeOutput(), UMLPackage.eINSTANCE.getStructuredActivityNode_StructuredNodeInput()), //
                Arguments.of(LoopNode.class, UMLPackage.eINSTANCE.getLoopNode_Result(), UMLPackage.eINSTANCE.getLoopNode_LoopVariableInput()), //
                Arguments.of(OpaqueAction.class, UMLPackage.eINSTANCE.getOpaqueAction_OutputValue(), UMLPackage.eINSTANCE.getOpaqueAction_InputValue()), //
                Arguments.of(ReadExtentAction.class, UMLPackage.eINSTANCE.getReadExtentAction_Result(), null), //
                Arguments.of(ReadIsClassifiedObjectAction.class, UMLPackage.eINSTANCE.getReadIsClassifiedObjectAction_Result(), UMLPackage.eINSTANCE.getReadIsClassifiedObjectAction_Object()), //
                Arguments.of(ReadLinkAction.class, UMLPackage.eINSTANCE.getReadLinkAction_Result(), UMLPackage.eINSTANCE.getLinkAction_InputValue()), //
                Arguments.of(ReadSelfAction.class, UMLPackage.eINSTANCE.getReadSelfAction_Result(), null), //
                Arguments.of(ReadStructuralFeatureAction.class, UMLPackage.eINSTANCE.getReadStructuralFeatureAction_Result(), UMLPackage.eINSTANCE.getStructuralFeatureAction_Object()), //
                Arguments.of(ReadVariableAction.class, UMLPackage.eINSTANCE.getReadVariableAction_Result(), null), //
                Arguments.of(ReclassifyObjectAction.class, null, UMLPackage.eINSTANCE.getReclassifyObjectAction_Object()), //
                Arguments.of(ReduceAction.class, UMLPackage.eINSTANCE.getReduceAction_Result(), UMLPackage.eINSTANCE.getReduceAction_Collection()), //
                Arguments.of(SendSignalAction.class, null, UMLPackage.eINSTANCE.getInvocationAction_Argument()), //
                Arguments.of(SequenceNode.class, UMLPackage.eINSTANCE.getStructuredActivityNode_StructuredNodeOutput(), UMLPackage.eINSTANCE.getStructuredActivityNode_StructuredNodeInput()), //
                Arguments.of(StartClassifierBehaviorAction.class, null, UMLPackage.eINSTANCE.getStartClassifierBehaviorAction_Object()), //
                Arguments.of(StartObjectBehaviorAction.class, UMLPackage.eINSTANCE.getCallAction_Result(), UMLPackage.eINSTANCE.getInvocationAction_Argument()), //
                Arguments.of(StructuredActivityNode.class, UMLPackage.eINSTANCE.getStructuredActivityNode_StructuredNodeOutput(), UMLPackage.eINSTANCE.getStructuredActivityNode_StructuredNodeInput()), //
                Arguments.of(TestIdentityAction.class, UMLPackage.eINSTANCE.getTestIdentityAction_Result(), null), //
                Arguments.of(UnmarshallAction.class, UMLPackage.eINSTANCE.getUnmarshallAction_Result(), UMLPackage.eINSTANCE.getUnmarshallAction_Object()), //
                Arguments.of(ValueSpecificationAction.class, UMLPackage.eINSTANCE.getValueSpecificationAction_Result(), null));
    }

    @ParameterizedTest
    @MethodSource("featureParameters")
    public void testGetActivityFeatureFromContainer(java.lang.Class clazz, EStructuralFeature expectedOutputPinFeature, EStructuralFeature expectedInputPinFeature) {
        EObject container = null;
        if (clazz != null) {
            container = this.create(clazz);
        }
        this.checkActivityFeatureFromContainer(container, expectedOutputPinFeature, expectedInputPinFeature);
    }

    private void checkActivityFeatureFromContainer(EObject semanticContext, EStructuralFeature expectedOutputPinFeature, EStructuralFeature expectedInputPinFeature) {
        // case OutputPin
        String outputPinType = UMLPackage.eINSTANCE.getOutputPin().getName();
        EStructuralFeature activityFeature = this.activityFeatureProvider.getActivityFeature(semanticContext, outputPinType);
        assertEquals(expectedOutputPinFeature, activityFeature);

        // case InputPin
        String inputPinType = UMLPackage.eINSTANCE.getInputPin().getName();
        activityFeature = this.activityFeatureProvider.getActivityFeature(semanticContext, inputPinType);
        assertEquals(expectedInputPinFeature, activityFeature);

        // case ValuePin
        String valuePinType = UMLPackage.eINSTANCE.getValuePin().getName();
        activityFeature = this.activityFeatureProvider.getActivityFeature(semanticContext, valuePinType);
        assertEquals(expectedInputPinFeature, activityFeature);

        // case ActionInputPin
        String actionInputPinType = UMLPackage.eINSTANCE.getActionInputPin().getName();
        activityFeature = this.activityFeatureProvider.getActivityFeature(semanticContext, actionInputPinType);
        assertEquals(expectedInputPinFeature, activityFeature);

        // case No Pin
        String commentType = UMLPackage.eINSTANCE.getComment().getName();
        activityFeature = this.activityFeatureProvider.getActivityFeature(semanticContext, commentType);
        assertEquals(null, activityFeature);

    }
}
