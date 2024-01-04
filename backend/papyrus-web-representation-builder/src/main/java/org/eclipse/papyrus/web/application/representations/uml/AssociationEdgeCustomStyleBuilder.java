/*******************************************************************************
 * Copyright (c) 2023, 2024 CEA LIST, Obeo.
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
package org.eclipse.papyrus.web.application.representations.uml;

import static org.eclipse.papyrus.web.application.representations.view.aql.OperatorQuery.and;
import static org.eclipse.papyrus.web.application.representations.view.aql.OperatorQuery.not;

import org.eclipse.papyrus.web.application.representations.view.aql.CallQuery;
import org.eclipse.papyrus.web.application.representations.view.builders.EdgeConditionalStyleBuilder;
import org.eclipse.sirius.components.view.diagram.ArrowStyle;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;

/**
 * Custom style builder for Association Edge.
 * 
 * @author Arthur Daussy
 */
// See https://github.com/PapyrusSirius/papyrus-web/issues/208
public class AssociationEdgeCustomStyleBuilder {


    private static final String SOURCE_DIAMOND = CallQuery.queryServiceOnSelf(ClassDiagramServices.IS_ASSOCIATION_SOURCE_SHARED);

    private static final String TARGET_DIAMOND = CallQuery.queryServiceOnSelf(ClassDiagramServices.IS_ASSOCIATION_TARGET_SHARED);

    private static final String SOURCE_FILL_DIAMOND = CallQuery.queryServiceOnSelf(ClassDiagramServices.IS_ASSOCIATION_SOURCE_COMPOSITE);

    private static final String TARGET_FILL_DIAMOND = CallQuery.queryServiceOnSelf(ClassDiagramServices.IS_ASSOCIATION_TARGET_COMPOSITE);

    private static final String SOURCE_ARROW = CallQuery.queryServiceOnSelf(ClassDiagramServices.IS_ASSOCIATION_TARGET_NAVIGABLE);

    private static final String TARGET_ARROW = CallQuery.queryServiceOnSelf(ClassDiagramServices.IS_ASSOCIATION_SOURCE_NAVIGABLE);

    private static final String SOURCE_ARROW_WITH_DIAMON = and(SOURCE_DIAMOND, SOURCE_ARROW);

    private static final String TARGET_ARROW_WITH_DIAMON = and(TARGET_DIAMOND, TARGET_ARROW);

    private static final String SOURCE_ARROW_WITH_FILL_DIAMON = and(SOURCE_FILL_DIAMOND, SOURCE_ARROW);

    private static final String TARGET_ARROW_WITH_FILL_DIAMON = and(TARGET_FILL_DIAMOND, TARGET_ARROW);

    private static final String SOURCE_ARROW_NO_DIAMON = and(and(not(SOURCE_FILL_DIAMOND), not(SOURCE_FILL_DIAMOND)), SOURCE_ARROW);

    private static final String TARGET_ARROW_NO_DIAMOND = and(and(not(TARGET_FILL_DIAMOND), not(TARGET_FILL_DIAMOND)), TARGET_ARROW);

    private static final String SOURCE_NO_DECORATOR = and(and(not(SOURCE_FILL_DIAMOND), not(SOURCE_FILL_DIAMOND)), not(SOURCE_ARROW));

    private static final String TARGET_NO_DECORATOR = and(and(not(TARGET_FILL_DIAMOND), not(TARGET_FILL_DIAMOND)), not(TARGET_ARROW));

    private final EdgeDescription edgeDescription;

    public AssociationEdgeCustomStyleBuilder(EdgeDescription edgeDescription) {
        super();
        this.edgeDescription = edgeDescription;
    }

    public void addCustomArowStyles() {

        sourceArrowFilledDiamond();
        sourceArrowDiamond();
        sourceOnlyArrow();
        sourceDiamond();
        sourceFilledDiamond();
        sourceNoDecorations();

    }

    private void sourceFilledDiamond() {
        addCustomStyle(and(SOURCE_FILL_DIAMOND, TARGET_ARROW_WITH_FILL_DIAMON), ArrowStyle.FILL_DIAMOND, ArrowStyle.INPUT_ARROW_WITH_FILL_DIAMOND);
        addCustomStyle(and(SOURCE_FILL_DIAMOND, TARGET_ARROW_WITH_DIAMON), ArrowStyle.FILL_DIAMOND, ArrowStyle.INPUT_ARROW_WITH_DIAMOND);
        addCustomStyle(and(SOURCE_FILL_DIAMOND, TARGET_FILL_DIAMOND), ArrowStyle.FILL_DIAMOND, ArrowStyle.FILL_DIAMOND);
        addCustomStyle(and(SOURCE_FILL_DIAMOND, TARGET_DIAMOND), ArrowStyle.FILL_DIAMOND, ArrowStyle.DIAMOND);
        addCustomStyle(and(SOURCE_FILL_DIAMOND, TARGET_ARROW_NO_DIAMOND), ArrowStyle.FILL_DIAMOND, ArrowStyle.INPUT_ARROW);
        addCustomStyle(and(SOURCE_FILL_DIAMOND, TARGET_NO_DECORATOR), ArrowStyle.FILL_DIAMOND, ArrowStyle.NONE);
    }

    private void sourceDiamond() {
        addCustomStyle(and(SOURCE_DIAMOND, TARGET_ARROW_WITH_FILL_DIAMON), ArrowStyle.DIAMOND, ArrowStyle.INPUT_ARROW_WITH_FILL_DIAMOND);
        addCustomStyle(and(SOURCE_DIAMOND, TARGET_ARROW_WITH_DIAMON), ArrowStyle.DIAMOND, ArrowStyle.INPUT_ARROW_WITH_DIAMOND);
        addCustomStyle(and(SOURCE_DIAMOND, TARGET_FILL_DIAMOND), ArrowStyle.DIAMOND, ArrowStyle.FILL_DIAMOND);
        addCustomStyle(and(SOURCE_DIAMOND, TARGET_DIAMOND), ArrowStyle.DIAMOND, ArrowStyle.DIAMOND);
        addCustomStyle(and(SOURCE_DIAMOND, TARGET_ARROW_NO_DIAMOND), ArrowStyle.DIAMOND, ArrowStyle.INPUT_ARROW);
        addCustomStyle(and(SOURCE_DIAMOND, TARGET_NO_DECORATOR), ArrowStyle.DIAMOND, ArrowStyle.NONE);
    }

    private void sourceArrowFilledDiamond() {
        addCustomStyle(and(SOURCE_ARROW_WITH_FILL_DIAMON, TARGET_ARROW_WITH_FILL_DIAMON), ArrowStyle.INPUT_ARROW_WITH_FILL_DIAMOND, ArrowStyle.INPUT_ARROW_WITH_FILL_DIAMOND);
        addCustomStyle(and(SOURCE_ARROW_WITH_FILL_DIAMON, TARGET_ARROW_WITH_DIAMON), ArrowStyle.INPUT_ARROW_WITH_FILL_DIAMOND, ArrowStyle.INPUT_ARROW_WITH_DIAMOND);
        addCustomStyle(and(SOURCE_ARROW_WITH_FILL_DIAMON, TARGET_FILL_DIAMOND), ArrowStyle.INPUT_ARROW_WITH_FILL_DIAMOND, ArrowStyle.FILL_DIAMOND);
        addCustomStyle(and(SOURCE_ARROW_WITH_FILL_DIAMON, TARGET_DIAMOND), ArrowStyle.INPUT_ARROW_WITH_FILL_DIAMOND, ArrowStyle.DIAMOND);
        addCustomStyle(and(SOURCE_ARROW_WITH_FILL_DIAMON, TARGET_ARROW_NO_DIAMOND), ArrowStyle.INPUT_ARROW_WITH_FILL_DIAMOND, ArrowStyle.INPUT_ARROW);
        addCustomStyle(and(SOURCE_ARROW_WITH_FILL_DIAMON, TARGET_NO_DECORATOR), ArrowStyle.INPUT_ARROW_WITH_FILL_DIAMOND, ArrowStyle.NONE);
    }

    private void sourceArrowDiamond() {
        addCustomStyle(and(SOURCE_ARROW_WITH_DIAMON, TARGET_ARROW_WITH_FILL_DIAMON), ArrowStyle.INPUT_ARROW_WITH_DIAMOND, ArrowStyle.INPUT_ARROW_WITH_FILL_DIAMOND);
        addCustomStyle(and(SOURCE_ARROW_WITH_DIAMON, TARGET_ARROW_WITH_DIAMON), ArrowStyle.INPUT_ARROW_WITH_DIAMOND, ArrowStyle.INPUT_ARROW_WITH_DIAMOND);
        addCustomStyle(and(SOURCE_ARROW_WITH_DIAMON, TARGET_FILL_DIAMOND), ArrowStyle.INPUT_ARROW_WITH_DIAMOND, ArrowStyle.FILL_DIAMOND);
        addCustomStyle(and(SOURCE_ARROW_WITH_DIAMON, TARGET_DIAMOND), ArrowStyle.INPUT_ARROW_WITH_DIAMOND, ArrowStyle.DIAMOND);
        addCustomStyle(and(SOURCE_ARROW_WITH_DIAMON, TARGET_ARROW_NO_DIAMOND), ArrowStyle.INPUT_ARROW_WITH_DIAMOND, ArrowStyle.INPUT_ARROW);
        addCustomStyle(and(SOURCE_ARROW_WITH_DIAMON, TARGET_NO_DECORATOR), ArrowStyle.INPUT_ARROW_WITH_DIAMOND, ArrowStyle.NONE);
    }

    private void sourceOnlyArrow() {
        addCustomStyle(and(SOURCE_ARROW_NO_DIAMON, TARGET_ARROW_WITH_FILL_DIAMON), ArrowStyle.INPUT_ARROW, ArrowStyle.INPUT_ARROW_WITH_FILL_DIAMOND);
        addCustomStyle(and(SOURCE_ARROW_NO_DIAMON, TARGET_ARROW_WITH_DIAMON), ArrowStyle.INPUT_ARROW, ArrowStyle.INPUT_ARROW_WITH_DIAMOND);
        addCustomStyle(and(SOURCE_ARROW_NO_DIAMON, TARGET_FILL_DIAMOND), ArrowStyle.INPUT_ARROW, ArrowStyle.FILL_DIAMOND);
        addCustomStyle(and(SOURCE_ARROW_NO_DIAMON, TARGET_DIAMOND), ArrowStyle.INPUT_ARROW, ArrowStyle.DIAMOND);
        addCustomStyle(and(SOURCE_ARROW_NO_DIAMON, TARGET_ARROW_NO_DIAMOND), ArrowStyle.INPUT_ARROW, ArrowStyle.INPUT_ARROW);
        addCustomStyle(and(SOURCE_ARROW_NO_DIAMON, TARGET_NO_DECORATOR), ArrowStyle.INPUT_ARROW, ArrowStyle.NONE);
    }

    private void sourceNoDecorations() {
        addCustomStyle(and(SOURCE_NO_DECORATOR, TARGET_ARROW_WITH_FILL_DIAMON), ArrowStyle.NONE, ArrowStyle.INPUT_ARROW_WITH_FILL_DIAMOND);
        addCustomStyle(and(SOURCE_NO_DECORATOR, TARGET_ARROW_WITH_DIAMON), ArrowStyle.NONE, ArrowStyle.INPUT_ARROW_WITH_DIAMOND);
        addCustomStyle(and(SOURCE_NO_DECORATOR, TARGET_FILL_DIAMOND), ArrowStyle.NONE, ArrowStyle.FILL_DIAMOND);
        addCustomStyle(and(SOURCE_NO_DECORATOR, TARGET_DIAMOND), ArrowStyle.NONE, ArrowStyle.DIAMOND);
        addCustomStyle(and(SOURCE_NO_DECORATOR, TARGET_ARROW_NO_DIAMOND), ArrowStyle.NONE, ArrowStyle.INPUT_ARROW);
        // Default style
        // addCustomStyle(and(SOURCE_NO_DECORATOR, TARGET_NO_DECORATOR), ArrowStyle.NONE, ArrowStyle.NONE);

    }

    private void addCustomStyle(String expression, ArrowStyle source, ArrowStyle target) {
        new EdgeConditionalStyleBuilder(edgeDescription, expression)//
                .fromExistingStyle()//
                .setSourceArrowStyle(source)//
                .setTargetArrowStyle(target);
    }

}
