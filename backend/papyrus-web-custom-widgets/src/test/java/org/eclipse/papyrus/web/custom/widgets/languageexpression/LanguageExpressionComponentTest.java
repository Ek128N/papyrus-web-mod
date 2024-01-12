/*****************************************************************************
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
 *****************************************************************************/
package org.eclipse.papyrus.web.custom.widgets.languageexpression;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.eclipse.papyrus.web.custom.widgets.languageexpression.dto.MoveLanguageDirection;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.UMLFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit tests for language expression component.
 *
 * @author Jerome Gout
 */
public class LanguageExpressionComponentTest {

    private static final String LE_WIDGET_ID = "LanguageExpressionWidget id";

    private static final String INVALID_LANGUAGE_MOVE = "Invalid language move";

    private static final String RUST_LANGUAGE = "RUST";

    private static final String C_LANGUAGE = "C";

    private static final String UNKNOWN_LANGUAGE_ERROR = "'C' is not a language of this language expression.";

    private static final String EXISTING_LANGUAGE_ERROR = "'JAVA' is already in this language expression.";

    private static final String JAVA_LANGUAGE_BODY = "java body";

    private static final String JAVA_LANGUAGE = "JAVA";

    private OpaqueBehavior ob;

    private LanguageExpressionElementProps props;

    @BeforeEach
    public void init() {
        VariableManager variableManager = new VariableManager();
        this.ob = UMLFactory.eINSTANCE.createOpaqueBehavior();
        variableManager.put(VariableManager.SELF, this.ob);

        LanguageExpressionDescription description = LanguageExpressionDescription.newLanguageExpressionDescription(LE_WIDGET_ID)
                .idProvider(varManager -> LE_WIDGET_ID)
                .labelProvider(varManager -> "")
                .isReadOnlyProvider(varManager -> false)
                .helpTextProvider(varManager -> "")
                .iconURLProvider(varManager -> List.of())
                .targetObjectIdProvider(varManager -> UUID.randomUUID().toString())
                .build();


        LanguageExpressionComponent component = new LanguageExpressionComponent(new LanguageExpressionComponentProps(variableManager, description));
        Element elt = component.render();
        this.props = (LanguageExpressionElementProps) elt.getProps();
    }

    @Test
    public void testAddLanguage() {
        IStatus status = this.props.getAddLanguageHandler().apply(JAVA_LANGUAGE);

        assertThat(status).isInstanceOf(Success.class);
        assertThat(this.ob.getLanguages()).hasSize(1);
        assertThat(this.ob.getBodies()).hasSize(1);
        assertThat(this.ob.getLanguages().get(0)).isEqualTo(JAVA_LANGUAGE);
        assertThat(this.ob.getBodies().get(0)).isEqualTo("");
    }

    @Test
    public void testAddLanguageTwice() {
        this.props.getAddLanguageHandler().apply(JAVA_LANGUAGE);
        IStatus status = this.props.getAddLanguageHandler().apply(JAVA_LANGUAGE);

        assertThat(status).isInstanceOf(Failure.class);
        Failure fail = (Failure) status;
        assertThat(fail.getMessages().get(0).body()).isEqualTo(EXISTING_LANGUAGE_ERROR);
        assertThat(this.ob.getLanguages()).hasSize(1);
        assertThat(this.ob.getBodies()).hasSize(1);
        assertThat(this.ob.getLanguages().get(0)).isEqualTo(JAVA_LANGUAGE);
        assertThat(this.ob.getBodies().get(0)).isEqualTo("");
    }

    @Test
    public void testDeleteLanguage() {
        this.props.getAddLanguageHandler().apply(JAVA_LANGUAGE);
        IStatus status = this.props.getDeleteLanguageHandler().apply(JAVA_LANGUAGE);

        assertThat(status).isInstanceOf(Success.class);
        assertThat(this.ob.getLanguages()).hasSize(0);
        assertThat(this.ob.getBodies()).hasSize(0);
    }

    @Test
    public void testDeleteLanguageUnknownLanguage() {
        this.props.getAddLanguageHandler().apply(JAVA_LANGUAGE);
        IStatus status = this.props.getDeleteLanguageHandler().apply(C_LANGUAGE);

        assertThat(status).isInstanceOf(Failure.class);
        Failure fail = (Failure) status;
        assertThat(fail.getMessages().get(0).body()).isEqualTo(UNKNOWN_LANGUAGE_ERROR);
        assertThat(this.ob.getLanguages()).hasSize(1);
        assertThat(this.ob.getBodies()).hasSize(1);
    }

    @Test
    public void testEditLanguageBody() {
        this.props.getAddLanguageHandler().apply(JAVA_LANGUAGE);
        IStatus status = this.props.getEditLanguageBodyHandler().apply(JAVA_LANGUAGE, JAVA_LANGUAGE_BODY);

        assertThat(status).isInstanceOf(Success.class);
        assertThat(this.ob.getLanguages()).hasSize(1);
        assertThat(this.ob.getBodies()).hasSize(1);
        assertThat(this.ob.getLanguages().get(0)).isEqualTo(JAVA_LANGUAGE);
        assertThat(this.ob.getBodies().get(0)).isEqualTo(JAVA_LANGUAGE_BODY);
    }

    @Test
    public void testEditLanguageBodyUnknownLanguage() {
        this.props.getAddLanguageHandler().apply(JAVA_LANGUAGE);
        IStatus status = this.props.getEditLanguageBodyHandler().apply(C_LANGUAGE, JAVA_LANGUAGE_BODY);

        assertThat(status).isInstanceOf(Failure.class);
        Failure fail = (Failure) status;
        assertThat(fail.getMessages().get(0).body()).isEqualTo(UNKNOWN_LANGUAGE_ERROR);
        assertThat(this.ob.getLanguages()).hasSize(1);
        assertThat(this.ob.getBodies()).hasSize(1);
    }

    @Test
    public void testMoveLanguage() {
        this.props.getAddLanguageHandler().apply(JAVA_LANGUAGE);
        this.props.getAddLanguageHandler().apply(C_LANGUAGE);

        assertThat(this.ob.getLanguages()).hasSize(2);
        assertThat(this.ob.getBodies()).hasSize(2);
        assertThat(this.ob.getLanguages().get(0)).isEqualTo(JAVA_LANGUAGE);
        assertThat(this.ob.getBodies().get(0)).isEqualTo("");
        assertThat(this.ob.getLanguages().get(1)).isEqualTo(C_LANGUAGE);
        assertThat(this.ob.getBodies().get(1)).isEqualTo("");

        IStatus status = this.props.getMoveLanguageHandler().apply(JAVA_LANGUAGE, MoveLanguageDirection.FORWARD);

        assertThat(status).isInstanceOf(Success.class);
        assertThat(this.ob.getLanguages()).hasSize(2);
        assertThat(this.ob.getBodies()).hasSize(2);
        assertThat(this.ob.getLanguages().get(0)).isEqualTo(C_LANGUAGE);
        assertThat(this.ob.getBodies().get(0)).isEqualTo("");
        assertThat(this.ob.getLanguages().get(1)).isEqualTo(JAVA_LANGUAGE);
        assertThat(this.ob.getBodies().get(1)).isEqualTo("");
    }

    @Test
    public void testMoveLanguageUnknownLanguage() {
        this.props.getAddLanguageHandler().apply(JAVA_LANGUAGE);
        this.props.getAddLanguageHandler().apply(RUST_LANGUAGE);

        assertThat(this.ob.getLanguages()).hasSize(2);
        assertThat(this.ob.getBodies()).hasSize(2);
        assertThat(this.ob.getLanguages().get(0)).isEqualTo(JAVA_LANGUAGE);
        assertThat(this.ob.getBodies().get(0)).isEqualTo("");
        assertThat(this.ob.getLanguages().get(1)).isEqualTo(RUST_LANGUAGE);
        assertThat(this.ob.getBodies().get(1)).isEqualTo("");

        IStatus status = this.props.getMoveLanguageHandler().apply(C_LANGUAGE, MoveLanguageDirection.FORWARD);

        assertThat(status).isInstanceOf(Failure.class);
        Failure fail = (Failure) status;
        assertThat(fail.getMessages().get(0).body()).isEqualTo(UNKNOWN_LANGUAGE_ERROR);
        assertThat(this.ob.getLanguages()).hasSize(2);
        assertThat(this.ob.getBodies()).hasSize(2);
        assertThat(this.ob.getLanguages().get(0)).isEqualTo(JAVA_LANGUAGE);
        assertThat(this.ob.getBodies().get(0)).isEqualTo("");
    }

    @Test
    public void testMoveLanguageWrongMove() {
        this.props.getAddLanguageHandler().apply(JAVA_LANGUAGE);
        this.props.getAddLanguageHandler().apply(C_LANGUAGE);

        assertThat(this.ob.getLanguages()).hasSize(2);
        assertThat(this.ob.getBodies()).hasSize(2);
        assertThat(this.ob.getLanguages().get(0)).isEqualTo(JAVA_LANGUAGE);
        assertThat(this.ob.getBodies().get(0)).isEqualTo("");
        assertThat(this.ob.getLanguages().get(1)).isEqualTo(C_LANGUAGE);
        assertThat(this.ob.getBodies().get(1)).isEqualTo("");

        IStatus status = this.props.getMoveLanguageHandler().apply(JAVA_LANGUAGE, MoveLanguageDirection.BACKWARD);

        assertThat(status).isInstanceOf(Failure.class);
        Failure fail = (Failure) status;
        assertThat(fail.getMessages().get(0).body()).isEqualTo(INVALID_LANGUAGE_MOVE);
        assertThat(this.ob.getLanguages()).hasSize(2);
        assertThat(this.ob.getBodies()).hasSize(2);
        assertThat(this.ob.getLanguages().get(0)).isEqualTo(JAVA_LANGUAGE);
        assertThat(this.ob.getBodies().get(0)).isEqualTo("");
        assertThat(this.ob.getLanguages().get(1)).isEqualTo(C_LANGUAGE);
        assertThat(this.ob.getBodies().get(1)).isEqualTo("");

        status = this.props.getMoveLanguageHandler().apply(C_LANGUAGE, MoveLanguageDirection.FORWARD);
        fail = (Failure) status;
        assertThat(fail.getMessages().get(0).body()).isEqualTo(INVALID_LANGUAGE_MOVE);
        assertThat(this.ob.getLanguages()).hasSize(2);
        assertThat(this.ob.getBodies()).hasSize(2);
        assertThat(this.ob.getLanguages().get(0)).isEqualTo(JAVA_LANGUAGE);
        assertThat(this.ob.getBodies().get(0)).isEqualTo("");
        assertThat(this.ob.getLanguages().get(1)).isEqualTo(C_LANGUAGE);
        assertThat(this.ob.getBodies().get(1)).isEqualTo("");
    }
}
