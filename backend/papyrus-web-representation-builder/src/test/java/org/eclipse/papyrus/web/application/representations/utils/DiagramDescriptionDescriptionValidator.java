/*****************************************************************************
 * Copyright (c) 2022, 2023 CEA LIST, Obeo.
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
package org.eclipse.papyrus.web.application.representations.utils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.eclipse.papyrus.uml.domain.services.EMFUtils;
import org.eclipse.papyrus.uml.domain.services.UMLHelper;
import org.eclipse.papyrus.web.application.representations.uml.AbstractRepresentationDescriptionBuilder;
import org.eclipse.papyrus.web.tests.utils.Status;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramElementDescription;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * A class gathering validation rules for {@link DiagramDescription}.
 *
 * @author Arthur Daussy
 */
public class DiagramDescriptionDescriptionValidator {

    private Predicate<DiagramElementDescription> excludedFromDeleteToolValidation = p -> !p.getName().equals(AbstractRepresentationDescriptionBuilder.SHARED_DESCRIPTIONS);

    private Predicate<DiagramElementDescription> excludedFromDirectEditValidation = p -> !p.getName().equals(AbstractRepresentationDescriptionBuilder.SHARED_DESCRIPTIONS);

    private boolean enableSharedDescriptionValidation = true;

    private boolean enableReusedNodeDescriptionsValidation = true;

    public List<Status> validate(DiagramDescription description) {

        List<Status> result = new ArrayList<>();

        result.addAll(this.validateUniqueName(description));
        result.addAll(this.validateDeleteTool(description));
        result.addAll(this.validateDirectEditTool(description));
        if (this.enableSharedDescriptionValidation) {
            result.addAll(this.validateSharedDescription(description));
        }
        if (this.enableReusedNodeDescriptionsValidation) {
            result.addAll(this.validateReusedNodeDescriptions(description));
        }

        if (result.isEmpty()) {
            result.add(Status.OK_SATUS);
        }
        return result;
    }

    public DiagramDescriptionDescriptionValidator excludeFromDirectEditValidation(Predicate<DiagramElementDescription> filter) {
        this.excludedFromDirectEditValidation = this.excludedFromDirectEditValidation.and(filter);
        return this;
    }

    public DiagramDescriptionDescriptionValidator excludeFromDeleteToolValidation(Predicate<DiagramElementDescription> filter) {
        this.excludedFromDeleteToolValidation = this.excludedFromDeleteToolValidation.and(filter);
        return this;
    }

    public DiagramDescriptionDescriptionValidator disableSharedDescriptionsValidation() {
        this.enableSharedDescriptionValidation = false;
        return this;
    }

    public DiagramDescriptionDescriptionValidator disableReusedNodeDescriptionsValidation() {
        this.enableReusedNodeDescriptionsValidation = false;
        return this;
    }

    /**
     * Check that all Node and domain based edge have a direct edit tool associated.
     * 
     * @param description
     *            a description
     * @return a list of error status
     */
    private Collection<? extends Status> validateDirectEditTool(DiagramDescription description) {
        List<Status> result = new ArrayList<>();

        EMFUtils.allContainedObjectOfType(description, NodeDescription.class)//
                .filter(this.excludedFromDirectEditValidation)//
                .filter(d -> d.getPalette().getLabelEditTool() == null).forEach(d -> {
                    if (d instanceof NodeDescription || this.isDomainBasedEdge(d)) {
                        result.add(Status.error("Missing direct edit tool on " + d.getName()));
                    }
                });
        EMFUtils.allContainedObjectOfType(description, EdgeDescription.class)//
                .filter(this.excludedFromDirectEditValidation)//
                .filter(d -> d.getPalette().getCenterLabelEditTool() == null).forEach(d -> {
                    if (d instanceof NodeDescription || this.isDomainBasedEdge(d)) {
                        result.add(Status.error("Missing direct edit tool on " + d.getName()));
                    }
                });
        return result;
    }

    private boolean isDomainBasedEdge(DiagramElementDescription description) {
        return description instanceof EdgeDescription && ((EdgeDescription) description).isIsDomainBasedEdge();
    }

    /**
     * All {@link NodeDescription}s should have a proper delete tool.
     *
     * @param description
     *            a diagram description
     */
    private List<Status> validateDeleteTool(DiagramDescription description) {
        List<Status> result = new ArrayList<>();

        EMFUtils.allContainedObjectOfType(description, NodeDescription.class)//
                .filter(this.excludedFromDeleteToolValidation).filter(d -> d.getPalette().getDeleteTool() == null).forEach(d -> {
                    result.add(Status.error("Missing deletion tool on " + d.getName()));
                });
        return result;
    }

    private List<Status> validateUniqueName(DiagramDescription description) {
        Set<String> names = new HashSet<>();
        List<Status> result = new ArrayList<>();

        EMFUtils.allContainedObjectOfType(description, DiagramElementDescription.class).forEach(d -> {
            String name = d.getName();
            if (name == null || name.isBlank()) {
                result.add(Status.error("Missing name on" + d));
            } else if (names.contains(name)) {
                result.add(Status.error("Duplicated name" + d.getName()));
            } else {
                names.add(name);
            }

        });

        return result;

    }

    /**
     * Validates the {@code SHARED_DESCRIPTIONS} element of the provided {@code description}.
     * <p>
     * This method checks that:
     * <ul>
     * <li>The {@code SHARED_DESCRIPTIONS} element is unique in the diagram</li>
     * <li>The {@code SHARED_DESCRIPTIONS} domain type is {@link Element}</li>
     * <li>The {@code SHARED_DESCRIPTIONS}'s palette is empty</li>
     * <li>The nodes inside the {@code SHARED_DESCRIPTIONS} are reused at least 2 times</li>
     * </ul>
     * </p>
     * 
     * @param description
     *            the {@link DiagramDescription} to validate
     * @return the list of error {@link Status}
     */
    private List<Status> validateSharedDescription(DiagramDescription description) {
        List<Status> result = new ArrayList<>();
        List<NodeDescription> sharedDescriptions = EMFUtils.allContainedObjectOfType(description, NodeDescription.class) //
                .filter(nodeDescription -> nodeDescription.getName().equals(AbstractRepresentationDescriptionBuilder.SHARED_DESCRIPTIONS)) //
                .toList();
        if (sharedDescriptions.size() > 1) {
            result.add(Status.error("Duplicated " + AbstractRepresentationDescriptionBuilder.SHARED_DESCRIPTIONS));
        }
        if (!sharedDescriptions.isEmpty()) {
            NodeDescription sharedDescription = sharedDescriptions.get(0);
            if (!UMLHelper.toEClass(sharedDescription.getDomainType()).equals(UMLPackage.eINSTANCE.getElement())) {
                String errorMessage = MessageFormat.format("{0} domain type is {1} but should be {2}", AbstractRepresentationDescriptionBuilder.SHARED_DESCRIPTIONS, sharedDescription.getDomainType(),
                        UMLPackage.eINSTANCE.getElement());
                result.add(Status.error(errorMessage));
            }
            result.addAll(this.checkNodeDescriptionHasEmptyPalette(sharedDescription));
            List<NodeDescription> childrenSharedDescriptions = sharedDescription.getChildrenDescriptions();
            for (NodeDescription childSharedDescription : childrenSharedDescriptions) {
                List<NodeDescription> reusingNodes = this.getReusingNodes(description, childSharedDescription);
                if (reusingNodes.size() < 2) {
                    String errorMessage = MessageFormat.format("Shared NodeDescription {0} should be reused by at least 2 NodeDescriptions", childSharedDescription.getName());
                    result.add(Status.error(errorMessage));
                }
                if (!childSharedDescription.getName().contains(AbstractRepresentationDescriptionBuilder.SHARED_SUFFIX)) {
                    String errorMessage = MessageFormat.format("Shared NodeDescription {0} should be suffixed by \'SHARED\'", childSharedDescription.getName());
                    result.add(Status.error(errorMessage));
                }
            }

        }
        return result;
    }

    /**
     * Checks that the palette of the provided {@code nodeDescription} is empty.
     * <p>
     * This method checks that the palette doesn't contain node tools, edge tools, label edit tools, delete tools, as
     * well as tool sections.
     * </p>
     * 
     * @param nodeDescription
     *            the {@link NodeDescription} to check
     * @return the list of error {@link Status}
     */
    private List<Status> checkNodeDescriptionHasEmptyPalette(NodeDescription nodeDescription) {
        List<Status> result = new ArrayList<>();
        if (nodeDescription.getPalette().getDeleteTool() != null) {
            String errorMessage = MessageFormat.format("{0} shouldn't contain a delete tool", AbstractRepresentationDescriptionBuilder.SHARED_DESCRIPTIONS);
            result.add(Status.error(errorMessage));
        }
        if (nodeDescription.getPalette().getLabelEditTool() != null) {
            String errorMessage = MessageFormat.format("{0} shouldn't contain a label edit tool", AbstractRepresentationDescriptionBuilder.SHARED_DESCRIPTIONS);
            result.add(Status.error(errorMessage));
        }
        if (!nodeDescription.getPalette().getNodeTools().isEmpty()) {
            String errorMessage = MessageFormat.format("{0} shouldn't contain node tools", AbstractRepresentationDescriptionBuilder.SHARED_DESCRIPTIONS);
            result.add(Status.error(errorMessage));
        }
        if (!nodeDescription.getPalette().getEdgeTools().isEmpty()) {
            String errorMessage = MessageFormat.format("{0} shouldn't contain edge tools", AbstractRepresentationDescriptionBuilder.SHARED_DESCRIPTIONS);
            result.add(Status.error(errorMessage));
        }
        if (!nodeDescription.getPalette().getToolSections().isEmpty()) {
            String errorMessage = MessageFormat.format("{0} shoudln't contain tool sections", AbstractRepresentationDescriptionBuilder.SHARED_DESCRIPTIONS);
            result.add(Status.error(errorMessage));
        }
        if (!nodeDescription.getSemanticCandidatesExpression().equals("aql:Sequence{}")) {
            String errorMessage = MessageFormat.format("{0} semantic candidate expression should be 'aql:Sequence{}'", AbstractRepresentationDescriptionBuilder.SHARED_DESCRIPTIONS);
            result.add(Status.error(errorMessage));
        }
        return result;
    }

    /**
     * Validates that reused {@link NodeDescription} in the provided {@code description} are defined in a
     * {@code SHARED_DESCRIPTIONS}.
     * 
     * @param description
     *            the {@link DiagramDescription} to validate
     * @return the list of error {@link Status}
     */
    private List<Status> validateReusedNodeDescriptions(DiagramDescription description) {
        List<Status> results = new ArrayList<>();
        EMFUtils.allContainedObjectOfType(description, NodeDescription.class).forEach(nodeDescription -> {
            for (NodeDescription reusedChildNode : nodeDescription.getReusedChildNodeDescriptions()) {
                if (!(reusedChildNode.eContainer() instanceof NodeDescription parentNode && parentNode.getName().equals(AbstractRepresentationDescriptionBuilder.SHARED_DESCRIPTIONS))) {
                    String errorMessage = MessageFormat.format("Reused NodeDescription {0} should be contained in {1} if it is reused at least 2 times, or be a direct children of {2}",
                            reusedChildNode.getName(), AbstractRepresentationDescriptionBuilder.SHARED_DESCRIPTIONS, nodeDescription.getName());
                    results.add(Status.error(errorMessage));
                }
            }
            for (NodeDescription reusedBorderNode : nodeDescription.getReusedBorderNodeDescriptions()) {
                if (!(reusedBorderNode.eContainer() instanceof NodeDescription parentNode && parentNode.getName().equals(AbstractRepresentationDescriptionBuilder.SHARED_DESCRIPTIONS))) {
                    String errorMessage = MessageFormat.format("Reused NodeDescription {0} should be contained in {1} if it is reused at least 2 times, or be a direct children of {2}",
                            reusedBorderNode.getName(), AbstractRepresentationDescriptionBuilder.SHARED_DESCRIPTIONS, nodeDescription.getName());
                    results.add(Status.error(errorMessage));
                }
            }
        });
        return results;
    }

    /**
     * Returns the {@link NodeDescription}s reusing the provided {@code reusedNodeDescription}.
     * 
     * @param description
     *            the {@link DiagramDescription} to inspect to find the reusing {@link NodeDescription}s
     * @param reusedNodeDescription
     *            the reused {@link NodeDescription}
     * @return a list of {@link NodeDescription}s that reuse the provided {@code reusedNodeDescription}
     */
    private List<NodeDescription> getReusingNodes(DiagramDescription description, NodeDescription reusedNodeDescription) {
        return EMFUtils.allContainedObjectOfType(description, NodeDescription.class)
                .filter(diagramNodeDescription -> diagramNodeDescription.getReusedChildNodeDescriptions().contains(reusedNodeDescription)
                        || diagramNodeDescription.getReusedBorderNodeDescriptions().contains(reusedNodeDescription))
                .toList();
    }

}
