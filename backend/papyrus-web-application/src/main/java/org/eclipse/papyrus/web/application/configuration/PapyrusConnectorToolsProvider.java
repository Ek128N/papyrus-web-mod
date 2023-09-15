/*******************************************************************************
 * Copyright (c) 2022 CEA, Obeo.
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
package org.eclipse.papyrus.web.application.configuration;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.papyrus.web.services.representations.PapyrusRepresentationDescriptionRegistry;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.diagrams.api.IConnectorToolsProvider;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramDescriptionService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.tools.ITool;
import org.eclipse.sirius.components.diagrams.tools.SingleClickOnDiagramElementTool;
import org.eclipse.sirius.components.diagrams.tools.SingleClickOnTwoDiagramElementsCandidate;
import org.eclipse.sirius.components.diagrams.tools.SingleClickOnTwoDiagramElementsTool;
import org.eclipse.sirius.components.representations.Success;
import org.springframework.stereotype.Service;

/**
 * {@link IConnectorToolsProvider} that avoid getting https://github.com/PapyrusSirius/papyrus-web/issues/47.
 *
 * @author Arthur Daussy
 */
@Service
public class PapyrusConnectorToolsProvider implements IConnectorToolsProvider {

    private final PapyrusRepresentationDescriptionRegistry papyrusViewRegistry;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IDiagramDescriptionService diagramDescriptionService;

    public PapyrusConnectorToolsProvider(PapyrusRepresentationDescriptionRegistry papyrusViewRegistry, IRepresentationDescriptionSearchService representationDescriptionSearchService,
            IDiagramDescriptionService diagramDescriptionService) {
        this.papyrusViewRegistry = Objects.requireNonNull(papyrusViewRegistry);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.diagramDescriptionService = Objects.requireNonNull(diagramDescriptionService);
    }

    @Override
    public boolean canHandle(DiagramDescription diagramDescription) {
        return this.papyrusViewRegistry.getApiDiagramDescriptionById(diagramDescription.getId()).isPresent();
    }

    @Override
    public List<ITool> getConnectorTools(Object sourceDiagramElement, Object targetDiagramElement, Diagram diagram, IEditingContext editingContext) {

        var optDiagramDescription = this.representationDescriptionSearchService.findById(editingContext, diagram.getDescriptionId());
        var optSourceDiagramElementDescriptionId = this.mapDiagramElementToDescriptionId(sourceDiagramElement);
        var optTargetDiagramElementDescriptionId = this.mapDiagramElementToDescriptionId(targetDiagramElement);

        boolean diagramElementDescriptionsPresent = optDiagramDescription.isPresent() && optSourceDiagramElementDescriptionId.isPresent() && optTargetDiagramElementDescriptionId.isPresent();
        List<ITool> result = null;
        if (diagramElementDescriptionsPresent && optDiagramDescription.get() instanceof DiagramDescription) {

            DiagramDescription diagramDescription = (DiagramDescription) optDiagramDescription.get();
            var optSourceDiagramElementDescription = this.mapDescriptionIdToDescription(optSourceDiagramElementDescriptionId.get(), diagramDescription, sourceDiagramElement);
            var optTargetDiagramElementDescription = this.mapDescriptionIdToDescription(optTargetDiagramElementDescriptionId.get(), diagramDescription, targetDiagramElement);

            if (optSourceDiagramElementDescription.isPresent() && optTargetDiagramElementDescription.isPresent()) {
                Object sourceDescription = optSourceDiagramElementDescription.get();
                Object targetDescription = optTargetDiagramElementDescription.get();
                result = diagramDescription.getPalettes().stream()
                        .flatMap(palette -> Stream.concat(palette.getTools().stream(), palette.getToolSections().stream().flatMap(toolSection -> toolSection.getTools().stream())))
                        .filter(SingleClickOnTwoDiagramElementsTool.class::isInstance).map(SingleClickOnTwoDiagramElementsTool.class::cast).filter(tool -> {
                            List<SingleClickOnTwoDiagramElementsCandidate> candidates = tool.getCandidates();
                            return candidates.stream().anyMatch(candidate -> candidate.getSources().contains(sourceDescription) && candidate.getTargets().contains(targetDescription));
                        }).collect(Collectors.toList());

            }

        }

        if (result == null || result.isEmpty()) {
            // Workaround for bug https://github.com/eclipse-sirius/sirius-components/issues/1378
            return List.of(this.newNoOpTool());
        } else {
            return result;
        }
    }

    private ITool newNoOpTool() {
        return SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("PapyrusMagicEdgeTool") //$NON-NLS-1$
                .appliesToDiagramRoot(false)//
                .handler(v -> new Success(ChangeKind.NOTHING, Map.of()))//
                .label("None")// //$NON-NLS-1$
                .targetDescriptions(List.of())//
                .build();
    }

    private Optional<String> mapDiagramElementToDescriptionId(Object object) {
        Optional<String> descriptionId = Optional.empty();
        if (object instanceof Node) {
            descriptionId = Optional.of(((Node) object).getDescriptionId());
        } else if (object instanceof Edge) {
            descriptionId = Optional.of(((Edge) object).getDescriptionId());
        }
        return descriptionId;
    }

    private Optional<Object> mapDescriptionIdToDescription(String descriptionId, DiagramDescription diagramDescription, Object diagramElement) {
        Optional<Object> result = Optional.empty();
        if (diagramElement instanceof Node) {
            var description = this.diagramDescriptionService.findNodeDescriptionById(diagramDescription, descriptionId);
            if (description.isPresent()) {
                result = Optional.of(description.get());
            }
        } else if (diagramElement instanceof Edge) {
            var description = this.diagramDescriptionService.findEdgeDescriptionById(diagramDescription, descriptionId);
            if (description.isPresent()) {
                result = Optional.of(description.get());
            }
        }
        return result;
    }

}
