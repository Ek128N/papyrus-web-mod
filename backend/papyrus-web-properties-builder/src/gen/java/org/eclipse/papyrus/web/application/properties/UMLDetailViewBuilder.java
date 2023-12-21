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
import org.eclipse.sirius.components.view.form.PageDescription;

public class UMLDetailViewBuilder {

    private ViewElementsFactory factory = new ViewElementsFactory();

    private final ColorRegistry colorRegistry;

    public UMLDetailViewBuilder(ColorRegistry colorRegistry) {
        this.colorRegistry = colorRegistry;
    }

    public List<PageDescription> createPages() {
        List<PageDescription> pages = new ArrayList<>();
        pages.add(new AbstractionUmlPage(factory, colorRegistry).create());
        pages.add(new AcceptCallActionUmlPage(factory, colorRegistry).create());
        pages.add(new AcceptEventActionUmlPage(factory, colorRegistry).create());
        pages.add(new ActionExecutionSpecificationUmlPage(factory, colorRegistry).create());
        pages.add(new ActionInputPinUmlPageCustomImpl(factory, colorRegistry).create());
        pages.add(new ActivityUmlPage(factory, colorRegistry).create());
        pages.add(new ActivityFinalNodeUmlPage(factory, colorRegistry).create());
        pages.add(new ActivityParameterNodeUmlPage(factory, colorRegistry).create());
        pages.add(new ActivityPartitionUmlPage(factory, colorRegistry).create());
        pages.add(new ActorUmlPage(factory, colorRegistry).create());
        pages.add(new AddStructuralFeatureValueActionUmlPage(factory, colorRegistry).create());
        pages.add(new AddVariableValueActionUmlPage(factory, colorRegistry).create());
        pages.add(new AnyReceiveEventUmlPage(factory, colorRegistry).create());
        pages.add(new ArtifactUmlPage(factory, colorRegistry).create());
        pages.add(new AssociationUmlPageCustomImpl(factory, colorRegistry).create());
        pages.add(new AssociationClassUmlPageCustomImpl(factory, colorRegistry).create());
        pages.add(new BehaviorExecutionSpecificationUmlPage(factory, colorRegistry).create());
        pages.add(new BroadcastSignalActionUmlPage(factory, colorRegistry).create());
        pages.add(new CallBehaviorActionUmlPage(factory, colorRegistry).create());
        pages.add(new CallEventUmlPage(factory, colorRegistry).create());
        pages.add(new CallOperationActionUmlPage(factory, colorRegistry).create());
        pages.add(new CentralBufferNodeUmlPage(factory, colorRegistry).create());
        pages.add(new ChangeEventUmlPage(factory, colorRegistry).create());
        pages.add(new ClassUmlPage(factory, colorRegistry).create());
        pages.add(new ClassifierTemplateParameterUmlPage(factory, colorRegistry).create());
        pages.add(new ClauseUmlPage(factory, colorRegistry).create());
        pages.add(new ClearAssociationActionUmlPage(factory, colorRegistry).create());
        pages.add(new ClearStructuralFeatureActionUmlPage(factory, colorRegistry).create());
        pages.add(new ClearVariableActionUmlPage(factory, colorRegistry).create());
        pages.add(new CollaborationUmlPage(factory, colorRegistry).create());
        pages.add(new CollaborationUseUmlPage(factory, colorRegistry).create());
        pages.add(new CombinedFragmentUmlPage(factory, colorRegistry).create());
        pages.add(new CommentUmlPage(factory, colorRegistry).create());
        pages.add(new CommunicationPathUmlPage(factory, colorRegistry).create());
        pages.add(new ComponentUmlPage(factory, colorRegistry).create());
        pages.add(new ComponentRealizationUmlPage(factory, colorRegistry).create());
        pages.add(new ConditionalNodeUmlPage(factory, colorRegistry).create());
        pages.add(new ConnectableElementTemplateParameterUmlPage(factory, colorRegistry).create());
        pages.add(new ConnectionPointReferenceUmlPage(factory, colorRegistry).create());
        pages.add(new ConnectorUmlPage(factory, colorRegistry).create());
        pages.add(new ConnectorEndUmlPageCustomImpl(factory, colorRegistry).create());
        pages.add(new ConsiderIgnoreFragmentUmlPage(factory, colorRegistry).create());
        pages.add(new ConstraintUmlPage(factory, colorRegistry).create());
        pages.add(new ContinuationUmlPage(factory, colorRegistry).create());
        pages.add(new ControlFlowUmlPage(factory, colorRegistry).create());
        pages.add(new CreateLinkActionUmlPageCustomImpl(factory, colorRegistry).create());
        pages.add(new CreateLinkObjectActionUmlPageCustomImpl(factory, colorRegistry).create());
        pages.add(new CreateObjectActionUmlPage(factory, colorRegistry).create());
        pages.add(new DataStoreNodeUmlPage(factory, colorRegistry).create());
        pages.add(new DataTypeUmlPage(factory, colorRegistry).create());
        pages.add(new DecisionNodeUmlPage(factory, colorRegistry).create());
        pages.add(new DependencyUmlPage(factory, colorRegistry).create());
        pages.add(new DeploymentUmlPage(factory, colorRegistry).create());
        pages.add(new DeploymentSpecificationUmlPage(factory, colorRegistry).create());
        pages.add(new DestroyLinkActionUmlPageCustomImpl(factory, colorRegistry).create());
        pages.add(new DestroyObjectActionUmlPage(factory, colorRegistry).create());
        pages.add(new DeviceUmlPage(factory, colorRegistry).create());
        pages.add(new DurationUmlPage(factory, colorRegistry).create());
        pages.add(new DurationConstraintUmlPageCustomImpl(factory, colorRegistry).create());
        pages.add(new DurationIntervalUmlPage(factory, colorRegistry).create());
        pages.add(new DurationObservationUmlPageCustomImpl(factory, colorRegistry).create());
        pages.add(new ElementImportUmlPageCustomImpl(factory, colorRegistry).create());
        pages.add(new EnumerationUmlPage(factory, colorRegistry).create());
        pages.add(new EnumerationLiteralUmlPage(factory, colorRegistry).create());
        pages.add(new ExceptionHandlerUmlPage(factory, colorRegistry).create());
        pages.add(new ExecutionEnvironmentUmlPage(factory, colorRegistry).create());
        pages.add(new ExecutionOccurrenceSpecificationUmlPage(factory, colorRegistry).create());
        pages.add(new ExpansionNodeUmlPage(factory, colorRegistry).create());
        pages.add(new ExpansionRegionUmlPage(factory, colorRegistry).create());
        pages.add(new ExpressionUmlPage(factory, colorRegistry).create());
        pages.add(new ExtendUmlPage(factory, colorRegistry).create());
        pages.add(new ExtensionUmlPage(factory, colorRegistry).create());
        pages.add(new ExtensionPointUmlPage(factory, colorRegistry).create());
        pages.add(new ExtensionEndUmlPageCustomImpl(factory, colorRegistry).create());
        pages.add(new FinalStateUmlPage(factory, colorRegistry).create());
        pages.add(new FlowFinalNodeUmlPage(factory, colorRegistry).create());
        pages.add(new ForkNodeUmlPage(factory, colorRegistry).create());
        pages.add(new FunctionBehaviorUmlPage(factory, colorRegistry).create());
        pages.add(new GateUmlPage(factory, colorRegistry).create());
        pages.add(new GeneralOrderingUmlPage(factory, colorRegistry).create());
        pages.add(new GeneralizationUmlPage(factory, colorRegistry).create());
        pages.add(new GeneralizationSetUmlPageCustomImpl(factory, colorRegistry).create());
        pages.add(new ImageUmlPage(factory, colorRegistry).create());
        pages.add(new IncludeUmlPage(factory, colorRegistry).create());
        pages.add(new InformationFlowUmlPage(factory, colorRegistry).create());
        pages.add(new InformationItemUmlPage(factory, colorRegistry).create());
        pages.add(new InitialNodeUmlPage(factory, colorRegistry).create());
        pages.add(new InputPinUmlPageCustomImpl(factory, colorRegistry).create());
        pages.add(new InstanceSpecificationUmlPage(factory, colorRegistry).create());
        pages.add(new InstanceValueUmlPage(factory, colorRegistry).create());
        pages.add(new InteractionUmlPage(factory, colorRegistry).create());
        pages.add(new InteractionConstraintUmlPage(factory, colorRegistry).create());
        pages.add(new InteractionOperandUmlPage(factory, colorRegistry).create());
        pages.add(new InteractionUseUmlPage(factory, colorRegistry).create());
        pages.add(new InterfaceUmlPage(factory, colorRegistry).create());
        pages.add(new InterfaceRealizationUmlPage(factory, colorRegistry).create());
        pages.add(new InterruptibleActivityRegionUmlPage(factory, colorRegistry).create());
        pages.add(new IntervalUmlPage(factory, colorRegistry).create());
        pages.add(new IntervalConstraintUmlPage(factory, colorRegistry).create());
        pages.add(new JoinNodeUmlPage(factory, colorRegistry).create());
        pages.add(new LifelineUmlPage(factory, colorRegistry).create());
        pages.add(new LinkEndCreationDataUmlPage(factory, colorRegistry).create());
        pages.add(new LinkEndDataUmlPage(factory, colorRegistry).create());
        pages.add(new LinkEndDestructionDataUmlPage(factory, colorRegistry).create());
        pages.add(new LiteralBooleanUmlPageCustomImpl(factory, colorRegistry).create());
        pages.add(new LiteralIntegerUmlPageCustomImpl(factory, colorRegistry).create());
        pages.add(new LiteralNullUmlPage(factory, colorRegistry).create());
        pages.add(new LiteralRealUmlPageCustomImpl(factory, colorRegistry).create());
        pages.add(new LiteralStringUmlPage(factory, colorRegistry).create());
        pages.add(new LiteralUnlimitedNaturalUmlPageCustomImpl(factory, colorRegistry).create());
        pages.add(new LoopNodeUmlPage(factory, colorRegistry).create());
        pages.add(new ManifestationUmlPage(factory, colorRegistry).create());
        pages.add(new MergeNodeUmlPage(factory, colorRegistry).create());
        pages.add(new MessageUmlPage(factory, colorRegistry).create());
        pages.add(new MessageOccurrenceSpecificationUmlPage(factory, colorRegistry).create());
        pages.add(new MetaclassUmlPage(factory, colorRegistry).create());
        pages.add(new ModelUmlPage(factory, colorRegistry).create());
        pages.add(new NodeUmlPage(factory, colorRegistry).create());
        pages.add(new ObjectFlowUmlPage(factory, colorRegistry).create());
        pages.add(new OccurrenceSpecificationUmlPage(factory, colorRegistry).create());
        pages.add(new OpaqueActionUmlPage(factory, colorRegistry).create());
        pages.add(new OpaqueBehaviorUmlPage(factory, colorRegistry).create());
        pages.add(new OpaqueExpressionUmlPage(factory, colorRegistry).create());
        pages.add(new OperationUmlPage(factory, colorRegistry).create());
        pages.add(new OperationTemplateParameterUmlPageCustomImpl(factory, colorRegistry).create());
        pages.add(new OutputPinUmlPageCustomImpl(factory, colorRegistry).create());
        pages.add(new PackageUmlPage(factory, colorRegistry).create());
        pages.add(new PackageImportUmlPageCustomImpl(factory, colorRegistry).create());
        pages.add(new PackageMergeUmlPage(factory, colorRegistry).create());
        pages.add(new ParameterUmlPageCustomImpl(factory, colorRegistry).create());
        pages.add(new ParameterSetUmlPage(factory, colorRegistry).create());
        pages.add(new PartDecompositionUmlPage(factory, colorRegistry).create());
        pages.add(new PortUmlPage(factory, colorRegistry).create());
        pages.add(new PrimitiveTypeUmlPage(factory, colorRegistry).create());
        pages.add(new ProfileUmlPage(factory, colorRegistry).create());
        pages.add(new ProfileApplicationUmlPage(factory, colorRegistry).create());
        pages.add(new PropertyUmlPageCustomImpl(factory, colorRegistry).create());
        pages.add(new ProtocolStateMachineUmlPage(factory, colorRegistry).create());
        pages.add(new ProtocolTransitionUmlPage(factory, colorRegistry).create());
        pages.add(new PseudoStateUmlPage(factory, colorRegistry).create());
        pages.add(new QualifierValueUmlPage(factory, colorRegistry).create());
        pages.add(new RaiseExceptionActionUmlPage(factory, colorRegistry).create());
        pages.add(new ReadExtentActionUmlPage(factory, colorRegistry).create());
        pages.add(new ReadIsClassifiedObjectActionUmlPage(factory, colorRegistry).create());
        pages.add(new ReadLinkActionUmlPage(factory, colorRegistry).create());
        pages.add(new ReadLinkObjectEndActionUmlPage(factory, colorRegistry).create());
        pages.add(new ReadLinkObjectEndQualifierActionUmlPage(factory, colorRegistry).create());
        pages.add(new ReadSelfActionUmlPage(factory, colorRegistry).create());
        pages.add(new ReadStructuralFeatureActionUmlPage(factory, colorRegistry).create());
        pages.add(new ReadVariableActionUmlPage(factory, colorRegistry).create());
        pages.add(new RealizationUmlPage(factory, colorRegistry).create());
        pages.add(new ReceptionUmlPage(factory, colorRegistry).create());
        pages.add(new ReclassifyObjectActionUmlPage(factory, colorRegistry).create());
        pages.add(new RedefinableTemplateSignatureUmlPage(factory, colorRegistry).create());
        pages.add(new ReduceActionUmlPage(factory, colorRegistry).create());
        pages.add(new RegionUmlPage(factory, colorRegistry).create());
        pages.add(new RemoveStructuralFeatureValueActionUmlPage(factory, colorRegistry).create());
        pages.add(new RemoveVariableValueActionUmlPage(factory, colorRegistry).create());
        pages.add(new ReplyActionUmlPage(factory, colorRegistry).create());
        pages.add(new SendObjectActionUmlPage(factory, colorRegistry).create());
        pages.add(new SendSignalActionUmlPage(factory, colorRegistry).create());
        pages.add(new SequenceNodeUmlPage(factory, colorRegistry).create());
        pages.add(new SignalUmlPage(factory, colorRegistry).create());
        pages.add(new SignalEventUmlPage(factory, colorRegistry).create());
        pages.add(new SlotUmlPage(factory, colorRegistry).create());
        pages.add(new StartClassifierBehaviorActionUmlPage(factory, colorRegistry).create());
        pages.add(new StartObjectBehaviorActionUmlPage(factory, colorRegistry).create());
        pages.add(new StateUmlPage(factory, colorRegistry).create());
        pages.add(new StateInvariantUmlPage(factory, colorRegistry).create());
        pages.add(new StateMachineUmlPage(factory, colorRegistry).create());
        pages.add(new StereotypeUmlPage(factory, colorRegistry).create());
        pages.add(new StringExpressionUmlPage(factory, colorRegistry).create());
        pages.add(new StructuredActivityNodeUmlPage(factory, colorRegistry).create());
        pages.add(new SubstitutionUmlPage(factory, colorRegistry).create());
        pages.add(new TemplateBindingUmlPage(factory, colorRegistry).create());
        pages.add(new TemplateParameterUmlPage(factory, colorRegistry).create());
        pages.add(new TemplateParameterSubstitutionUmlPage(factory, colorRegistry).create());
        pages.add(new TestIdentityActionUmlPage(factory, colorRegistry).create());
        pages.add(new TimeConstraintUmlPage(factory, colorRegistry).create());
        pages.add(new TimeEventUmlPage(factory, colorRegistry).create());
        pages.add(new TimeExpressionUmlPage(factory, colorRegistry).create());
        pages.add(new TimeIntervalUmlPage(factory, colorRegistry).create());
        pages.add(new TimeObservationUmlPage(factory, colorRegistry).create());
        pages.add(new TransitionUmlPage(factory, colorRegistry).create());
        pages.add(new TriggerUmlPage(factory, colorRegistry).create());
        pages.add(new UnmarshallActionUmlPage(factory, colorRegistry).create());
        pages.add(new UsageUmlPage(factory, colorRegistry).create());
        pages.add(new UseCaseUmlPage(factory, colorRegistry).create());
        pages.add(new ValuePinUmlPageCustomImpl(factory, colorRegistry).create());
        pages.add(new ValueSpecificationActionUmlPage(factory, colorRegistry).create());
        pages.add(new VariableUmlPageCustomImpl(factory, colorRegistry).create());
        pages.add(new ElementCommentsPageCustomImpl(factory, colorRegistry).create());
        pages.add(new ProfileDefinitionPageCustomImpl(factory, colorRegistry).create());
        pages.add(new ProfileDefinitionDefinitionPageCustomImpl(factory, colorRegistry).create());
        pages.add(new ElementProfilePageCustomImpl(factory, colorRegistry).create());

        return pages;
    }

}
