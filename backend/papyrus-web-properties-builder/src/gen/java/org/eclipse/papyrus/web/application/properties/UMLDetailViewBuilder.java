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

package org.eclipse.papyrus.web.application.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.web.application.properties.pages.*;
import org.eclipse.sirius.components.view.PageDescription;

public class UMLDetailViewBuilder {

    private ViewElementsFactory factory = new ViewElementsFactory();

    public List<PageDescription> createPages() {
        List<PageDescription> pages = new ArrayList<>();
        pages.add(new AbstractionUmlPage(factory).create());
        pages.add(new AcceptCallActionUmlPage(factory).create());
        pages.add(new AcceptEventActionUmlPage(factory).create());
        pages.add(new ActionExecutionSpecificationUmlPage(factory).create());
        pages.add(new ActionInputPinUmlPage(factory).create());
        pages.add(new ActivityUmlPage(factory).create());
        pages.add(new ActivityFinalNodeUmlPage(factory).create());
        pages.add(new ActivityParameterNodeUmlPage(factory).create());
        pages.add(new ActivityPartitionUmlPage(factory).create());
        pages.add(new ActorUmlPage(factory).create());
        pages.add(new AddStructuralFeatureValueActionUmlPage(factory).create());
        pages.add(new AddVariableValueActionUmlPage(factory).create());
        pages.add(new AnyReceiveEventUmlPage(factory).create());
        pages.add(new ArtifactUmlPage(factory).create());
        pages.add(new AssociationUmlPage(factory).create());
        pages.add(new AssociationClassUmlPage(factory).create());
        pages.add(new BehaviorExecutionSpecificationUmlPage(factory).create());
        pages.add(new BroadcastSignalActionUmlPage(factory).create());
        pages.add(new CallBehaviorActionUmlPage(factory).create());
        pages.add(new CallEventUmlPage(factory).create());
        pages.add(new CallOperationActionUmlPage(factory).create());
        pages.add(new CentralBufferNodeUmlPage(factory).create());
        pages.add(new ChangeEventUmlPage(factory).create());
        pages.add(new ClassUmlPage(factory).create());
        pages.add(new ClassifierTemplateParameterUmlPage(factory).create());
        pages.add(new ClauseUmlPage(factory).create());
        pages.add(new ClearAssociationActionUmlPage(factory).create());
        pages.add(new ClearStructuralFeatureActionUmlPage(factory).create());
        pages.add(new ClearVariableActionUmlPage(factory).create());
        pages.add(new CollaborationUmlPage(factory).create());
        pages.add(new CollaborationUseUmlPage(factory).create());
        pages.add(new CombinedFragmentUmlPage(factory).create());
        pages.add(new CommentUmlPage(factory).create());
        pages.add(new CommunicationPathUmlPage(factory).create());
        pages.add(new ComponentUmlPage(factory).create());
        pages.add(new ComponentRealizationUmlPage(factory).create());
        pages.add(new ConditionalNodeUmlPage(factory).create());
        pages.add(new ConnectableElementTemplateParameterUmlPage(factory).create());
        pages.add(new ConnectionPointReferenceUmlPage(factory).create());
        pages.add(new ConnectorUmlPage(factory).create());
        pages.add(new ConnectorEndUmlPage(factory).create());
        pages.add(new ConsiderIgnoreFragmentUmlPage(factory).create());
        pages.add(new ConstraintUmlPage(factory).create());
        pages.add(new ContinuationUmlPage(factory).create());
        pages.add(new ControlFlowUmlPage(factory).create());
        pages.add(new CreateLinkActionUmlPage(factory).create());
        pages.add(new CreateLinkObjectActionUmlPage(factory).create());
        pages.add(new CreateObjectActionUmlPage(factory).create());
        pages.add(new DataStoreNodeUmlPage(factory).create());
        pages.add(new DataTypeUmlPage(factory).create());
        pages.add(new DecisionNodeUmlPage(factory).create());
        pages.add(new DependencyUmlPage(factory).create());
        pages.add(new DeploymentUmlPage(factory).create());
        pages.add(new DeploymentSpecificationUmlPage(factory).create());
        pages.add(new DestroyLinkActionUmlPage(factory).create());
        pages.add(new DestroyObjectActionUmlPage(factory).create());
        pages.add(new DeviceUmlPage(factory).create());
        pages.add(new DurationUmlPage(factory).create());
        pages.add(new DurationConstraintUmlPage(factory).create());
        pages.add(new DurationIntervalUmlPage(factory).create());
        pages.add(new DurationObservationUmlPage(factory).create());
        pages.add(new ElementImportUmlPage(factory).create());
        pages.add(new EnumerationUmlPage(factory).create());
        pages.add(new EnumerationLiteralUmlPage(factory).create());
        pages.add(new ExceptionHandlerUmlPage(factory).create());
        pages.add(new ExecutionEnvironmentUmlPage(factory).create());
        pages.add(new ExecutionOccurrenceSpecificationUmlPage(factory).create());
        pages.add(new ExpansionNodeUmlPage(factory).create());
        pages.add(new ExpansionRegionUmlPage(factory).create());
        pages.add(new ExpressionUmlPage(factory).create());
        pages.add(new ExtendUmlPage(factory).create());
        pages.add(new ExtensionUmlPage(factory).create());
        pages.add(new ExtensionPointUmlPage(factory).create());
        pages.add(new ExtensionEndUmlPage(factory).create());
        pages.add(new FinalStateUmlPage(factory).create());
        pages.add(new FlowFinalNodeUmlPage(factory).create());
        pages.add(new ForkNodeUmlPage(factory).create());
        pages.add(new FunctionBehaviorUmlPage(factory).create());
        pages.add(new GateUmlPage(factory).create());
        pages.add(new GeneralOrderingUmlPage(factory).create());
        pages.add(new GeneralizationUmlPage(factory).create());
        pages.add(new GeneralizationSetUmlPage(factory).create());
        pages.add(new ImageUmlPage(factory).create());
        pages.add(new IncludeUmlPage(factory).create());
        pages.add(new InformationFlowUmlPage(factory).create());
        pages.add(new InformationItemUmlPage(factory).create());
        pages.add(new InitialNodeUmlPage(factory).create());
        pages.add(new InputPinUmlPage(factory).create());
        pages.add(new InstanceSpecificationUmlPage(factory).create());
        pages.add(new InstanceValueUmlPage(factory).create());
        pages.add(new InteractionUmlPage(factory).create());
        pages.add(new InteractionConstraintUmlPage(factory).create());
        pages.add(new InteractionOperandUmlPage(factory).create());
        pages.add(new InteractionUseUmlPage(factory).create());
        pages.add(new InterfaceUmlPage(factory).create());
        pages.add(new InterfaceRealizationUmlPage(factory).create());
        pages.add(new InterruptibleActivityRegionUmlPage(factory).create());
        pages.add(new IntervalUmlPage(factory).create());
        pages.add(new IntervalConstraintUmlPage(factory).create());
        pages.add(new JoinNodeUmlPage(factory).create());
        pages.add(new LifelineUmlPage(factory).create());
        pages.add(new LinkEndCreationDataUmlPage(factory).create());
        pages.add(new LinkEndDataUmlPage(factory).create());
        pages.add(new LinkEndDestructionDataUmlPage(factory).create());
        pages.add(new LiteralBooleanUmlPageCustomImpl(factory).create());
        pages.add(new LiteralIntegerUmlPageCustomImpl(factory).create());
        pages.add(new LiteralNullUmlPage(factory).create());
        pages.add(new LiteralRealUmlPageCustomImpl(factory).create());
        pages.add(new LiteralStringUmlPage(factory).create());
        pages.add(new LiteralUnlimitedNaturalUmlPageCustomImpl(factory).create());
        pages.add(new LoopNodeUmlPage(factory).create());
        pages.add(new ManifestationUmlPage(factory).create());
        pages.add(new MergeNodeUmlPage(factory).create());
        pages.add(new MessageUmlPage(factory).create());
        pages.add(new MessageOccurrenceSpecificationUmlPage(factory).create());
        pages.add(new MetaclassUmlPage(factory).create());
        pages.add(new ModelUmlPage(factory).create());
        pages.add(new NodeUmlPage(factory).create());
        pages.add(new ObjectFlowUmlPage(factory).create());
        pages.add(new OccurrenceSpecificationUmlPage(factory).create());
        pages.add(new OpaqueActionUmlPage(factory).create());
        pages.add(new OpaqueBehaviorUmlPage(factory).create());
        pages.add(new OpaqueExpressionUmlPage(factory).create());
        pages.add(new OperationUmlPage(factory).create());
        pages.add(new OperationTemplateParameterUmlPage(factory).create());
        pages.add(new OutputPinUmlPage(factory).create());
        pages.add(new PackageUmlPage(factory).create());
        pages.add(new PackageImportUmlPage(factory).create());
        pages.add(new PackageMergeUmlPage(factory).create());
        pages.add(new ParameterUmlPage(factory).create());
        pages.add(new ParameterSetUmlPage(factory).create());
        pages.add(new PartDecompositionUmlPage(factory).create());
        pages.add(new PortUmlPage(factory).create());
        pages.add(new PrimitiveTypeUmlPage(factory).create());
        pages.add(new ProfileUmlPage(factory).create());
        pages.add(new ProfileApplicationUmlPage(factory).create());
        pages.add(new PropertyUmlPage(factory).create());
        pages.add(new ProtocolStateMachineUmlPage(factory).create());
        pages.add(new ProtocolTransitionUmlPage(factory).create());
        pages.add(new PseudoStateUmlPage(factory).create());
        pages.add(new QualifierValueUmlPage(factory).create());
        pages.add(new RaiseExceptionActionUmlPage(factory).create());
        pages.add(new ReadExtentActionUmlPage(factory).create());
        pages.add(new ReadIsClassifiedObjectActionUmlPage(factory).create());
        pages.add(new ReadLinkActionUmlPage(factory).create());
        pages.add(new ReadLinkObjectEndActionUmlPage(factory).create());
        pages.add(new ReadLinkObjectEndQualifierActionUmlPage(factory).create());
        pages.add(new ReadSelfActionUmlPage(factory).create());
        pages.add(new ReadStructuralFeatureActionUmlPage(factory).create());
        pages.add(new ReadVariableActionUmlPage(factory).create());
        pages.add(new RealizationUmlPage(factory).create());
        pages.add(new ReceptionUmlPage(factory).create());
        pages.add(new ReclassifyObjectActionUmlPage(factory).create());
        pages.add(new RedefinableTemplateSignatureUmlPage(factory).create());
        pages.add(new ReduceActionUmlPage(factory).create());
        pages.add(new RegionUmlPage(factory).create());
        pages.add(new RemoveStructuralFeatureValueActionUmlPage(factory).create());
        pages.add(new RemoveVariableValueActionUmlPage(factory).create());
        pages.add(new ReplyActionUmlPage(factory).create());
        pages.add(new SendObjectActionUmlPage(factory).create());
        pages.add(new SendSignalActionUmlPage(factory).create());
        pages.add(new SequenceNodeUmlPage(factory).create());
        pages.add(new SignalUmlPage(factory).create());
        pages.add(new SignalEventUmlPage(factory).create());
        pages.add(new SlotUmlPage(factory).create());
        pages.add(new StartClassifierBehaviorActionUmlPage(factory).create());
        pages.add(new StartObjectBehaviorActionUmlPage(factory).create());
        pages.add(new StateUmlPage(factory).create());
        pages.add(new StateInvariantUmlPage(factory).create());
        pages.add(new StateMachineUmlPage(factory).create());
        pages.add(new StereotypeUmlPage(factory).create());
        pages.add(new StringExpressionUmlPage(factory).create());
        pages.add(new StructuredActivityNodeUmlPage(factory).create());
        pages.add(new SubstitutionUmlPage(factory).create());
        pages.add(new TemplateBindingUmlPage(factory).create());
        pages.add(new TemplateParameterUmlPage(factory).create());
        pages.add(new TemplateParameterSubstitutionUmlPage(factory).create());
        pages.add(new TestIdentityActionUmlPage(factory).create());
        pages.add(new TimeConstraintUmlPage(factory).create());
        pages.add(new TimeEventUmlPage(factory).create());
        pages.add(new TimeExpressionUmlPage(factory).create());
        pages.add(new TimeIntervalUmlPage(factory).create());
        pages.add(new TimeObservationUmlPage(factory).create());
        pages.add(new TransitionUmlPage(factory).create());
        pages.add(new TriggerUmlPage(factory).create());
        pages.add(new UnmarshallActionUmlPage(factory).create());
        pages.add(new UsageUmlPage(factory).create());
        pages.add(new UseCaseUmlPage(factory).create());
        pages.add(new ValuePinUmlPage(factory).create());
        pages.add(new ValueSpecificationActionUmlPage(factory).create());
        pages.add(new VariableUmlPage(factory).create());
        pages.add(new ElementCommentsPage(factory).create());
        pages.add(new ProfileDefinitionPage(factory).create());
        pages.add(new ProfileDefinitionDefinitionPage(factory).create());
        pages.add(new ElementProfilePage(factory).create());

        return pages;
    }

}
