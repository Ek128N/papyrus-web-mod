/*******************************************************************************
 * Copyright (c) 2022, 2024 CEA LIST, Obeo.
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
 *******************************************************************************/
package org.eclipse.papyrus.web.sirius.contributions.query;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.eclipse.sirius.components.diagrams.Node;

/**
 * Matcher used to select node in a diagram.
 *
 * @author Arthur Daussy
 */
public class NodeMatcher {

    /**
     * Type of node to match.
     *
     * @author Arthur Daussy
     */
    public enum BorderNodeStatus {
        /**
         * The searched node is a bordered node.
         */
        BORDERED_NODE,
        /**
         * The searched node basic node (not a bordered node).
         */
        BASIC_NODE,
        /**
         * The search node can be both.
         */
        BOTH
    }

    private final Optional<Predicate<Object>> semanticPredicate;

    private final Optional<Predicate<Node>> nodePredicate;

    private final BorderNodeStatus borderedNodeStatus;

    public NodeMatcher(Optional<Predicate<Object>> semanticPredicate, Optional<Predicate<Node>> nodePredicate, BorderNodeStatus borderedNodeStatus) {
        super();
        this.semanticPredicate = semanticPredicate;
        this.nodePredicate = nodePredicate;
        this.borderedNodeStatus = borderedNodeStatus;
    }

    public static NodeMatcher buildSemanticMatcher(BorderNodeStatus borderedNodeStatus, Predicate<Object> semanticPredicate) {
        return new NodeMatcher(Optional.of(semanticPredicate), Optional.empty(), borderedNodeStatus);
    }

    public static NodeMatcher buildSemanticAndNodeMatcher(BorderNodeStatus borderedNodeStatus, Predicate<Object> semanticPredicate, Predicate<Node> nodePredicate) {
        return new NodeMatcher(Optional.ofNullable(semanticPredicate), Optional.ofNullable(nodePredicate), borderedNodeStatus);
    }

    public boolean match(Node node, Supplier<Object> semanticProvider) {

        Boolean matchSemantic = this.semanticPredicate.map(semPred -> semPred.test(semanticProvider.get())).orElse(Boolean.TRUE);
        Boolean matchView = this.nodePredicate.map(viewPred -> viewPred.test(node)).orElse(Boolean.TRUE);

        return matchSemantic && matchView;
    }

    public BorderNodeStatus getBorderedNodeStatus() {
        return this.borderedNodeStatus;
    }

}
