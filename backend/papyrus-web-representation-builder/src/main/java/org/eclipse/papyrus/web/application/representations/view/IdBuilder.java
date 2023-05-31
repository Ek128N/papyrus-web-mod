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
package org.eclipse.papyrus.web.application.representations.view;

import static java.util.stream.Collectors.joining;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.sirius.components.view.DiagramElementDescription;
import org.eclipse.sirius.components.view.EdgeDescription;
import org.eclipse.sirius.components.view.NodeDescription;

/**
 * Build ids using consistent rules.
 *
 * @author Arthur Daussy
 */
public class IdBuilder {

    private static final String FAKE_CHILD_LABEL_NODE = "_FakeChildLabelNode";

    private static final String LABEL_NODE_PREFIX = "LabelNode"; //$NON-NLS-1$

    private static final String COMPARTMENT_NODE_SUFFIX = "_CompartmentNode"; //$NON-NLS-1$

    private static final String UNDERSTORE = "_"; //$NON-NLS-1$

    private static final String SPACE = " "; //$NON-NLS-1$

    private static final Pattern WORD_FINDER = Pattern.compile("(([A-Z]?[a-z]+)|([A-Z]))"); //$NON-NLS-1$

    private static final String NEW = "New "; //$NON-NLS-1$

    private final String diagramPrefix;

    private IDomainHelper metamodelHelper;

    public IdBuilder(String diagramPrefix, IDomainHelper metamodelHelper) {
        super();
        this.diagramPrefix = diagramPrefix;
        this.metamodelHelper = metamodelHelper;
    }

    private List<String> findWordsInMixedCase(String text) {
        Matcher matcher = WORD_FINDER.matcher(text);
        List<String> words = new ArrayList<>();
        while (matcher.find()) {
            words.add(matcher.group(0));
        }
        return words;
    }

    public String getCreationToolId(EClass newElementType) {
        return NEW + newElementType.getName();
    }

    public String getSiblingCreationToolId(EClass newElementType) {
        return NEW + " Sibling " + newElementType.getName(); //$NON-NLS-1$
    }

    /**
     * Id to be used for fake child nodes only used to contains tools. See
     * https://github.com/PapyrusSirius/papyrus-web/issues/164
     * 
     * @param parentNode
     *            the parent node
     * @return an id
     */
    // Workaround for https://github.com/PapyrusSirius/papyrus-web/issues/164
    public String getFakeChildNodeId(NodeDescription parentNode) {
        return parentNode.getName() + FAKE_CHILD_LABEL_NODE;
    }

    public static boolean isCompartmentNode(NodeDescription description) {
        String name = description.getName();
        return name != null && name.endsWith(COMPARTMENT_NODE_SUFFIX);
    }

    // Workaround for https://github.com/PapyrusSirius/papyrus-web/issues/164
    public static boolean isFakeChildNode(DiagramElementDescription description) {
        String name = description.getName();
        return name != null && name.endsWith(FAKE_CHILD_LABEL_NODE);
    }

    public String getDomainBaseEdgeId(EClass domain) {
        return diagramPrefix + domain.getName() + "_DomainEdge"; //$NON-NLS-1$
    }

    public String getFeatureBaseEdgeId(EStructuralFeature feature) {
        return diagramPrefix + feature.getEContainingClass().getName() + UNDERSTORE + feature.getName() + "_FeatureEdge"; //$NON-NLS-1$
    }

    private String getBaseName(DiagramElementDescription description) {
        String base = findWordsInMixedCase(metamodelHelper.toEClass(description.getDomainType()).getName()).stream().collect(joining(SPACE));
        return base; // $NON-NLS-1$
    }

    public String getDomainNodeName(EClass domain) {
        return diagramPrefix + domain.getName();
    }

    public String getSpecializedDomainNodeName(EClass domain, String specialization) {
        return diagramPrefix + domain.getName() + UNDERSTORE + specialization;
    }

    public String getCompartmentDomainNodeName(EClass domain, String compartmentName) {
        return diagramPrefix + domain.getName() + UNDERSTORE + compartmentName + COMPARTMENT_NODE_SUFFIX;
    }

    public String getListItemDomainNodeName(EClass domain, EClass parentContainer) {
        return diagramPrefix + domain.getName() + "In" + parentContainer.getName() + UNDERSTORE + LABEL_NODE_PREFIX; //$NON-NLS-1$
    }

    public String getDropToolName(NodeDescription nodeToCreate) {
        return diagramPrefix + "DropTool_" + getBaseName(nodeToCreate); //$NON-NLS-1$
    }

    public String getDropToolId() {
        return diagramPrefix + "DropTool"; //$NON-NLS-1$
    }

    public String getCreationToolId(EdgeDescription description) {
        if (description.isIsDomainBasedEdge()) {
            return NEW + getBaseName(description);
        }
        // Improve this
        return diagramPrefix + getBaseName(description) + "_EdgeTool"; //$NON-NLS-1$
    }

    public String getSourceReconnectionToolId(EdgeDescription padPackageMerge) {
        return padPackageMerge.getName() + "_SourceReconnectionTool"; //$NON-NLS-1$
    }

    public String getTargetReconnectionToolId(EdgeDescription padPackageMerge) {
        return padPackageMerge.getName() + "_TargetReconnectionTool"; //$NON-NLS-1$
    }

}
