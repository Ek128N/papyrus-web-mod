/*******************************************************************************
 * Copyright (c) 2022, 2023 CEA, Obeo.
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
package org.eclipse.papyrus.web.application.representations.uml;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.text.MessageFormat;
import java.util.List;

import org.eclipse.papyrus.web.application.representations.utils.DiagramDescriptionDescriptionValidator;
import org.eclipse.papyrus.web.application.representations.view.IdBuilder;
import org.eclipse.papyrus.web.tests.utils.Status;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramElementDescription;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test that validates all diagram descriptions.
 *
 * @author Arthur Daussy
 */
public class RepresentationValidatorTests {

    private static final String EOL = "\n"; //$NON-NLS-1$

    @Test
    public void validateCompositeStructure() {
        DiagramDescription diagram = new CSDDiagramDescriptionBuilder().createDiagramDescription(ViewFactory.eINSTANCE.createView());

        List<Status> validations = buildeDefaultValidator().validate(diagram);

        List<Status> errors = validations.stream().filter(v -> !v.isValid()).collect(toList());

        if (!errors.isEmpty()) {
            Assertions.fail(MessageFormat.format("Invalid Composite Structure Diagram description : \n{0}", errors.stream().map(e -> e.getMessage()).collect(joining(EOL)))); //$NON-NLS-1$
        }
    }

    private DiagramDescriptionDescriptionValidator buildeDefaultValidator() {
        return new DiagramDescriptionDescriptionValidator()//
                .excludeFromDeleteToolValidation(p -> !IdBuilder.isFakeChildNode(p) && isNotCompartment(p))//
                .excludeFromDirectEditValidation(p -> !IdBuilder.isFakeChildNode(p) && isNotCompartment(p));
    }

    @Test
    public void validatePackageDiagram() {
        DiagramDescription diagram = new PADDiagramDescriptionBuilder().createDiagramDescription(ViewFactory.eINSTANCE.createView());

        List<Status> validations = buildeDefaultValidator().validate(diagram);

        List<Status> errors = validations.stream().filter(v -> !v.isValid()).collect(toList());

        if (!errors.isEmpty()) {
            Assertions.fail(MessageFormat.format("Invalid Package Diagram description : \n{0}", errors.stream().map(e -> e.getMessage()).collect(joining(EOL)))); //$NON-NLS-1$
        }
    }

    @Test
    public void validateClassDiagram() {
        DiagramDescription diagram = new CDDiagramDescriptionBuilder().createDiagramDescription(ViewFactory.eINSTANCE.createView());

        List<Status> validations = buildeDefaultValidator().validate(diagram);

        List<Status> errors = validations.stream().filter(v -> !v.isValid()).collect(toList());

        if (!errors.isEmpty()) {
            Assertions.fail(MessageFormat.format("Invalid Class Diagram description : \n{0}", errors.stream().map(e -> e.getMessage()).collect(joining(EOL)))); //$NON-NLS-1$
        }
    }

    @Test
    public void validateStateMachineDiagram() {
        DiagramDescription diagram = new SMDDiagramDescriptionBuilder().createDiagramDescription(ViewFactory.eINSTANCE.createView());

        DiagramDescriptionDescriptionValidator validator = buildeDefaultValidator();
        // Exclude the direct edit tool check on transition since it is a complex semantic and we do not have yet a way
        // to implement it
        validator.excludeFromDirectEditValidation(p -> !isTransitionEdge(p));

        List<Status> validations = validator.validate(diagram);

        List<Status> errors = validations.stream().filter(v -> !v.isValid()).collect(toList());

        if (!errors.isEmpty()) {
            Assertions.fail(MessageFormat.format("Invalid Class Diagram description : \n{0}", errors.stream().map(e -> e.getMessage()).collect(joining(EOL)))); //$NON-NLS-1$
        }
    }

    private boolean isTransitionEdge(DiagramElementDescription p) {
        return "SMD_Transition_DomainEdge".equals(p.getName());
    }

    private boolean isNotCompartment(DiagramElementDescription diagramelementdescription1) {
        return !(diagramelementdescription1 instanceof NodeDescription) || !IdBuilder.isCompartmentNode((NodeDescription) diagramelementdescription1);
    }

}
