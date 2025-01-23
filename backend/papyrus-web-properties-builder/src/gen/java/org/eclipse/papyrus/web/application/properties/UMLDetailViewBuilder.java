/*****************************************************************************
 * Copyright (c) 2023, 2024 CEA LIST, Obeo, Artal Technologies.
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
 *  Titouan BOUETE-GIRAUD (Artal Technologies) - Issue 210
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
        pages.add(new AbstractionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new AcceptCallActionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new AcceptEventActionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ActionExecutionSpecificationUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ActionInputPinUmlPageCustomImpl(this.factory, this.colorRegistry).create());
        pages.add(new ActivityUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ActivityFinalNodeUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ActivityParameterNodeUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ActivityPartitionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ActorUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new AddStructuralFeatureValueActionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new AddVariableValueActionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new AnyReceiveEventUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ArtifactUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new AssociationUmlPageCustomImpl(this.factory, this.colorRegistry).create());
        pages.add(new AssociationClassUmlPageCustomImpl(this.factory, this.colorRegistry).create());
        pages.add(new BehaviorExecutionSpecificationUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new BroadcastSignalActionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new CallBehaviorActionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new CallEventUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new CallOperationActionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new CentralBufferNodeUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ChangeEventUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ClassUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ClassifierTemplateParameterUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ClauseUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ClearAssociationActionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ClearStructuralFeatureActionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ClearVariableActionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new CollaborationUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new CollaborationUseUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new CombinedFragmentUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new CommentUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new CommunicationPathUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ComponentUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ComponentRealizationUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ConditionalNodeUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ConnectableElementTemplateParameterUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ConnectionPointReferenceUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ConnectorUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ConnectorEndUmlPageCustomImpl(this.factory, this.colorRegistry).create());
        pages.add(new ConsiderIgnoreFragmentUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ConstraintUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ContinuationUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ControlFlowUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new CreateLinkActionUmlPageCustomImpl(this.factory, this.colorRegistry).create());
        pages.add(new CreateLinkObjectActionUmlPageCustomImpl(this.factory, this.colorRegistry).create());
        pages.add(new CreateObjectActionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new DataStoreNodeUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new DataTypeUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new DecisionNodeUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new DependencyUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new DeploymentUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new DeploymentSpecificationUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new DestroyLinkActionUmlPageCustomImpl(this.factory, this.colorRegistry).create());
        pages.add(new DestroyObjectActionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new DeviceUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new DurationUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new DurationConstraintUmlPageCustomImpl(this.factory, this.colorRegistry).create());
        pages.add(new DurationIntervalUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new DurationObservationUmlPageCustomImpl(this.factory, this.colorRegistry).create());
        pages.add(new ElementImportUmlPageCustomImpl(this.factory, this.colorRegistry).create());
        pages.add(new EnumerationUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new EnumerationLiteralUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ExceptionHandlerUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ExecutionEnvironmentUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ExecutionOccurrenceSpecificationUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ExpansionNodeUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ExpansionRegionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ExpressionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ExtendUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ExtensionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ExtensionPointUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ExtensionEndUmlPageCustomImpl(this.factory, this.colorRegistry).create());
        pages.add(new FinalStateUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new FlowFinalNodeUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ForkNodeUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new FunctionBehaviorUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new GateUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new GeneralOrderingUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new GeneralizationUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new GeneralizationSetUmlPageCustomImpl(this.factory, this.colorRegistry).create());
        pages.add(new ImageUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new IncludeUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new InformationFlowUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new InformationItemUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new InitialNodeUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new InputPinUmlPageCustomImpl(this.factory, this.colorRegistry).create());
        pages.add(new InstanceSpecificationUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new InstanceValueUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new InteractionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new InteractionConstraintUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new InteractionOperandUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new InteractionUseUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new InterfaceUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new InterfaceRealizationUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new InterruptibleActivityRegionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new IntervalUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new IntervalConstraintUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new JoinNodeUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new LifelineUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new LinkEndCreationDataUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new LinkEndDataUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new LinkEndDestructionDataUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new LiteralBooleanUmlPageCustomImpl(this.factory, this.colorRegistry).create());
        pages.add(new LiteralIntegerUmlPageCustomImpl(this.factory, this.colorRegistry).create());
        pages.add(new LiteralNullUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new LiteralRealUmlPageCustomImpl(this.factory, this.colorRegistry).create());
        pages.add(new LiteralStringUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new LiteralUnlimitedNaturalUmlPageCustomImpl(this.factory, this.colorRegistry).create());
        pages.add(new LoopNodeUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ManifestationUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new MergeNodeUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new MessageUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new MessageOccurrenceSpecificationUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new MetaclassUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ModelUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new NodeUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ObjectFlowUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new OccurrenceSpecificationUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new OpaqueActionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new OpaqueBehaviorUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new OpaqueExpressionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new OperationUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new OperationTemplateParameterUmlPageCustomImpl(this.factory, this.colorRegistry).create());
        pages.add(new OutputPinUmlPageCustomImpl(this.factory, this.colorRegistry).create());
        pages.add(new PackageUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new PackageImportUmlPageCustomImpl(this.factory, this.colorRegistry).create());
        pages.add(new PackageMergeUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ParameterUmlPageCustomImpl(this.factory, this.colorRegistry).create());
        pages.add(new ParameterSetUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new PartDecompositionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new PortUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new PrimitiveTypeUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ProfileUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ProfileApplicationUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new PropertyUmlPageCustomImpl(this.factory, this.colorRegistry).create());
        pages.add(new ProtocolStateMachineUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ProtocolTransitionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new PseudoStateUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new QualifierValueUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new RaiseExceptionActionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ReadExtentActionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ReadIsClassifiedObjectActionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ReadLinkActionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ReadLinkObjectEndActionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ReadLinkObjectEndQualifierActionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ReadSelfActionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ReadStructuralFeatureActionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ReadVariableActionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new RealizationUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ReceptionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ReclassifyObjectActionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new RedefinableTemplateSignatureUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ReduceActionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new RegionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new RemoveStructuralFeatureValueActionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new RemoveVariableValueActionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ReplyActionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new SendObjectActionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new SendSignalActionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new SequenceNodeUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new SignalUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new SignalEventUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new SlotUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new StartClassifierBehaviorActionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new StartObjectBehaviorActionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new StateUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new StateInvariantUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new StateMachineUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new StereotypeUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new StringExpressionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new StructuredActivityNodeUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new SubstitutionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new TemplateBindingUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new TemplateParameterUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new TemplateParameterSubstitutionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new TestIdentityActionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new TimeConstraintUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new TimeEventUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new TimeExpressionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new TimeIntervalUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new TimeObservationUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new TransitionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new TriggerUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new UnmarshallActionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new UsageUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new UseCaseUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new ValuePinUmlPageCustomImpl(this.factory, this.colorRegistry).create());
        pages.add(new ValueSpecificationActionUmlPage(this.factory, this.colorRegistry).create());
        pages.add(new VariableUmlPageCustomImpl(this.factory, this.colorRegistry).create());
        pages.add(new ElementCommentsPageCustomImpl(this.factory, this.colorRegistry).create());
        pages.add(new ProfileDefinitionPageCustomImpl(this.factory, this.colorRegistry).create());
        pages.add(new ProfileDefinitionDefinitionPageCustomImpl(this.factory, this.colorRegistry).create());
        pages.add(new ElementProfilePageCustomImpl(this.factory, this.colorRegistry).create());
        pages.add(new ElementSymbolPage(this.factory, this.colorRegistry).create());

        return pages;
    }

}
