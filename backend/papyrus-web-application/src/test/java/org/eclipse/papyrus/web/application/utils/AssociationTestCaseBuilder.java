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
package org.eclipse.papyrus.web.application.utils;

import org.eclipse.emf.common.util.EList;
import org.eclipse.papyrus.uml.domain.services.create.ElementCreator;
import org.eclipse.papyrus.uml.domain.services.edges.ElementDomainBasedEdgeInitializer;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.VisibilityKind;

/**
 * Builder a complex test case to test UML {@link Association}.
 *
 * @author Arthur Daussy
 */
public class AssociationTestCaseBuilder {

    private final Model model;

    public AssociationTestCaseBuilder(Model model) {
        super();
        this.model = model;
    }

    /**
     * Create model with a list of {@link Association}s with all decorators. Each association has the following name
     * "$SourceCode_$TargetCode" where each code use the following pattern :
     * <ul>
     * <li>N : None for such case the cardinality is set to 0..1</li>
     * <li>A: Arrow for such case the cardinality no set</li>
     * <li>AFD: Arrow filled diamond for such case the cardinality is set to 1..*</li>
     * <li>AD: Arrow diamond for such case the cardinality is set to 5..*</li>
     * <li>D : Diamond for such case the cardinality is set to 0..*</li>
     * <li>FD : Fill diamond for such case the cardinality is set to 1..1</li>
     * </ul>
     */
    public void createAssociations() {

        // Source no decorator
        this.createAssociation(false, AggregationKind.NONE_LITERAL, false, AggregationKind.NONE_LITERAL);
        this.createAssociation(false, AggregationKind.NONE_LITERAL, false, AggregationKind.SHARED_LITERAL);
        this.createAssociation(false, AggregationKind.NONE_LITERAL, false, AggregationKind.COMPOSITE_LITERAL);
        this.createAssociation(true, AggregationKind.NONE_LITERAL, false, AggregationKind.NONE_LITERAL);
        this.createAssociation(true, AggregationKind.NONE_LITERAL, false, AggregationKind.COMPOSITE_LITERAL);
        this.createAssociation(true, AggregationKind.NONE_LITERAL, false, AggregationKind.SHARED_LITERAL);

        // Source with arrow
        this.createAssociation(false, AggregationKind.NONE_LITERAL, true, AggregationKind.NONE_LITERAL);
        this.createAssociation(false, AggregationKind.NONE_LITERAL, true, AggregationKind.SHARED_LITERAL);
        this.createAssociation(false, AggregationKind.NONE_LITERAL, true, AggregationKind.COMPOSITE_LITERAL);
        this.createAssociation(true, AggregationKind.NONE_LITERAL, true, AggregationKind.NONE_LITERAL);
        this.createAssociation(true, AggregationKind.NONE_LITERAL, true, AggregationKind.COMPOSITE_LITERAL);
        this.createAssociation(true, AggregationKind.NONE_LITERAL, true, AggregationKind.SHARED_LITERAL);

        // Source with arrow diamond
        this.createAssociation(false, AggregationKind.SHARED_LITERAL, true, AggregationKind.NONE_LITERAL);
        this.createAssociation(false, AggregationKind.SHARED_LITERAL, true, AggregationKind.SHARED_LITERAL);
        this.createAssociation(false, AggregationKind.SHARED_LITERAL, true, AggregationKind.COMPOSITE_LITERAL);
        this.createAssociation(true, AggregationKind.SHARED_LITERAL, true, AggregationKind.NONE_LITERAL);
        this.createAssociation(true, AggregationKind.SHARED_LITERAL, true, AggregationKind.COMPOSITE_LITERAL);
        this.createAssociation(true, AggregationKind.SHARED_LITERAL, true, AggregationKind.SHARED_LITERAL);

        // Source with arrow filled diamond
        this.createAssociation(false, AggregationKind.COMPOSITE_LITERAL, true, AggregationKind.NONE_LITERAL);
        this.createAssociation(false, AggregationKind.COMPOSITE_LITERAL, true, AggregationKind.SHARED_LITERAL);
        this.createAssociation(false, AggregationKind.COMPOSITE_LITERAL, true, AggregationKind.COMPOSITE_LITERAL);
        this.createAssociation(true, AggregationKind.COMPOSITE_LITERAL, true, AggregationKind.NONE_LITERAL);
        this.createAssociation(true, AggregationKind.COMPOSITE_LITERAL, true, AggregationKind.COMPOSITE_LITERAL);
        this.createAssociation(true, AggregationKind.COMPOSITE_LITERAL, true, AggregationKind.SHARED_LITERAL);

        // Source with diamond
        this.createAssociation(false, AggregationKind.SHARED_LITERAL, false, AggregationKind.NONE_LITERAL);
        this.createAssociation(false, AggregationKind.SHARED_LITERAL, false, AggregationKind.SHARED_LITERAL);
        this.createAssociation(false, AggregationKind.SHARED_LITERAL, false, AggregationKind.COMPOSITE_LITERAL);
        this.createAssociation(true, AggregationKind.SHARED_LITERAL, false, AggregationKind.NONE_LITERAL);
        this.createAssociation(true, AggregationKind.SHARED_LITERAL, false, AggregationKind.COMPOSITE_LITERAL);
        this.createAssociation(true, AggregationKind.SHARED_LITERAL, false, AggregationKind.SHARED_LITERAL);

        // Source with filled diamond
        this.createAssociation(false, AggregationKind.COMPOSITE_LITERAL, false, AggregationKind.NONE_LITERAL);
        this.createAssociation(false, AggregationKind.COMPOSITE_LITERAL, false, AggregationKind.SHARED_LITERAL);
        this.createAssociation(false, AggregationKind.COMPOSITE_LITERAL, false, AggregationKind.COMPOSITE_LITERAL);
        this.createAssociation(true, AggregationKind.COMPOSITE_LITERAL, false, AggregationKind.NONE_LITERAL);
        this.createAssociation(true, AggregationKind.COMPOSITE_LITERAL, false, AggregationKind.COMPOSITE_LITERAL);
        this.createAssociation(true, AggregationKind.COMPOSITE_LITERAL, false, AggregationKind.SHARED_LITERAL);

    }

    // * <li>N : None for such case the cardinality is to 2..5 using LiteralInteger</li>
    // * <li>A: Arrow for such case the cardinality set to 0..1</li>
    // * <li>AFD: Arrow filled diamond for such case the cardinality is set to 1..*</li>
    // * <li>AD: Arrow diamond for such case the cardinality is set to 5..*</li>
    // * <li>D : Diamond for such case the cardinality is set to 0..*</li>
    // * <li>FD : Fill diamond for such case the cardinality is set to 1..1</li>
    private void setMultiplicity(Property prop, boolean navigable, AggregationKind sourceAggrKind) {

        if (!navigable && sourceAggrKind == AggregationKind.NONE_LITERAL) {
            LiteralInteger upper = UMLFactory.eINSTANCE.createLiteralInteger();
            upper.setValue(5);
            prop.setUpperValue(upper);
            LiteralInteger lower = UMLFactory.eINSTANCE.createLiteralInteger();
            lower.setValue(2);
            prop.setLowerValue(lower);
            prop.setVisibility(VisibilityKind.PUBLIC_LITERAL);
        } else if (navigable && sourceAggrKind == AggregationKind.NONE_LITERAL) {
            prop.setLower(0);
            prop.setUpper(1);
            prop.setVisibility(VisibilityKind.PRIVATE_LITERAL);
        } else if (navigable && sourceAggrKind == AggregationKind.COMPOSITE_LITERAL) {
            prop.setLower(1);
            prop.setUpper(-1);
            prop.setVisibility(VisibilityKind.PROTECTED_LITERAL);
        } else if (navigable && sourceAggrKind == AggregationKind.SHARED_LITERAL) {
            prop.setLower(5);
            prop.setUpper(-1);
            prop.setVisibility(VisibilityKind.PACKAGE_LITERAL);
        } else if (!navigable && sourceAggrKind == AggregationKind.SHARED_LITERAL) {
            prop.setLower(0);
            prop.setUpper(-1);
        } else if (!navigable && sourceAggrKind == AggregationKind.COMPOSITE_LITERAL) {
            prop.setLower(1);
            prop.setUpper(1);
        }
    }

    private Association createAssociation(boolean sourceNavigable, AggregationKind sourceAggrKind, boolean targetNavigable, AggregationKind targetAggrKind) {

        String sourceTypeName = this.buildLabel(sourceNavigable, sourceAggrKind);
        String targetTypeName = this.buildLabel(targetNavigable, targetAggrKind);
        String name = sourceTypeName + "-" + targetTypeName;

        Association association = (Association) ElementCreator.buildDefault(null, e -> true).create(this.model, "Association", UMLPackage.eINSTANCE.getPackage_PackagedElement().getName())
                .getElement();
        Class sourceClazz = UMLFactory.eINSTANCE.createClass();
        sourceClazz.setName(name + "_source");
        this.model.getPackagedElements().add(sourceClazz);
        Class targetClazz = UMLFactory.eINSTANCE.createClass();
        this.model.getPackagedElements().add(targetClazz);
        targetClazz.setName(name + "_target");
        association.setName(name);
        new ElementDomainBasedEdgeInitializer().initialize(association, sourceClazz, targetClazz, null, null, null);

        Property source = this.getAssociationSource(association);

        if (sourceNavigable) {
            association.getNavigableOwnedEnds().add(source);
        }
        source.setAggregation(sourceAggrKind);
        this.setMultiplicity(source, sourceNavigable, sourceAggrKind);

        Property target = this.getAssociationTarget(association);
        if (targetNavigable) {
            association.getNavigableOwnedEnds().add(target);
        }
        target.setAggregation(targetAggrKind);
        this.setMultiplicity(target, targetNavigable, targetAggrKind);

        this.model.getPackagedElements().add(association);

        return association;
    }

    private String buildLabel(boolean sourceNavigable, AggregationKind sourceAggrKind) {
        String label = "";

        if (sourceNavigable) {
            label += "N";
        }
        label += switch (sourceAggrKind) {
            case COMPOSITE_LITERAL -> "FD";
            case SHARED_LITERAL -> "D";
            default -> "";
        };

        if (label.isBlank()) {
            label = "None";
        }
        return label;
    }

    private Property getAssociationSource(Association association) {
        EList<Property> memberEnds = association.getMemberEnds();
        if (!memberEnds.isEmpty()) {
            return memberEnds.get(0);
        }
        return null;
    }

    private Property getAssociationTarget(Association association) {
        EList<Property> memberEnds = association.getMemberEnds();
        if (memberEnds.size() > 1) {
            return memberEnds.get(1);
        }
        return null;
    }

}
