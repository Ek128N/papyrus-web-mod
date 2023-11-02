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
package org.eclipse.papyrus.web.custom.widgets.languageexpression;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.web.custom.widgets.languageexpression.dto.MoveLanguageDirection;
import org.eclipse.sirius.components.forms.components.FormComponent;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.uml2.uml.OpaqueAction;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.OpaqueExpression;

/**
 * The component to render a language expression widget.
 *
 * @author Jerome Gout
 */
public class LanguageExpressionComponent implements IComponent {

    public static final String ELEMENT_VARIABLE = "element";

    private static final String WRONG_LANGUAGE_EXPRESSION_OWNER = "Wrong semantic object: can only be an OpaqueBehavior, OpaqueExpression or OpaqueAction, received: {0}";

    private static final String UNKNOWN_LANGUAGE = "''{0}'' is not a language of this language expression.";

    private static final String EXISTING_LANGUAGE = "''{0}'' is already in this language expression.";

    private final LanguageExpressionComponentProps props;

    public LanguageExpressionComponent(LanguageExpressionComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    private List<LanguageElement> getLanguageElements(EObject object) {
        List<LanguageElement> res = new ArrayList<>();
        List<String> languages = List.of();
        List<String> bodies = List.of();

        if (object instanceof OpaqueBehavior) {
            OpaqueBehavior opaqueBehavior = (OpaqueBehavior) object;
            languages = opaqueBehavior.getLanguages();
            bodies = opaqueBehavior.getBodies();
        } else if (object instanceof OpaqueExpression) {
            OpaqueExpression opaqueExpression = (OpaqueExpression) object;
            languages = opaqueExpression.getLanguages();
            bodies = opaqueExpression.getBodies();
        } else if (object instanceof OpaqueAction) {
            OpaqueAction opaqueAction = (OpaqueAction) object;
            languages = opaqueAction.getLanguages();
            bodies = opaqueAction.getBodies();
        }

        for (int index = 0; index < languages.size(); index++) {
            String languageName = languages.get(index);
            String id = UUID.nameUUIDFromBytes((String.valueOf(index) + "-" + languageName).getBytes()).toString();
            LanguageElement language = LanguageElement.newLanguageElement(id).label(languageName).body(bodies.get(index)).build();
            res.add(language);
        }
        return res;
    }

    /**
     * Returns the list of predefined languages that can be chosen by user in the language expression.<br>
     *
     * @return list of predefined language names.
     */
    private List<String> getPredefinedLanguages() {
        return List.of("C", "C++", "JAVA", "Natural Language", "OCL");
    }

    private static IStatus withLanguage(EObject object, BiFunction<List<String>, List<String>, IStatus> task) {
        List<String> languages = null;
        List<String> bodies = null;

        if (object instanceof OpaqueBehavior) {
            OpaqueBehavior opaqueBehavior = (OpaqueBehavior) object;
            languages = opaqueBehavior.getLanguages();
            bodies = opaqueBehavior.getBodies();
        } else if (object instanceof OpaqueExpression) {
            OpaqueExpression opaqueExpression = (OpaqueExpression) object;
            languages = opaqueExpression.getLanguages();
            bodies = opaqueExpression.getBodies();
        } else if (object instanceof OpaqueAction) {
            OpaqueAction opaqueAction = (OpaqueAction) object;
            languages = opaqueAction.getLanguages();
            bodies = opaqueAction.getBodies();
        }

        if (languages == null) {
            return new Failure(MessageFormat.format(WRONG_LANGUAGE_EXPRESSION_OWNER, object.eClass().getName()));
        }
        return task.apply(languages, bodies);
    }

    private IStatus addLanguage(EObject object, String newLanguage) {
        return withLanguage(object, (languages, bodies) -> {
            int index = languages.indexOf(newLanguage);
            if (index >= 0) {
                return new Failure(MessageFormat.format(EXISTING_LANGUAGE, newLanguage));
            }
            languages.add(newLanguage);
            bodies.add("");
            return new Success();
        });
    }

    private IStatus deleteLanguage(EObject object, String language) {
        return withLanguage(object, (languages, bodies) -> {
            int index = languages.indexOf(language);
            if (index < 0) {
                return new Failure(MessageFormat.format(UNKNOWN_LANGUAGE, language));
            } else {
                languages.remove(index);
                bodies.remove(index);
                return new Success();
            }
        });
    }

    private IStatus editLanguageBody(EObject object, String language, String newBody) {
        return withLanguage(object, (languages, bodies) -> {
            int index = languages.indexOf(language);
            if (index < 0) {
                return new Failure(MessageFormat.format(UNKNOWN_LANGUAGE, language));
            } else {
                bodies.set(index, newBody);
                return new Success();
            }
        });
    }

    private IStatus moveLanguage(EObject object, String language, MoveLanguageDirection direction) {
        return withLanguage(object, (languages, bodies) -> {
            int index = languages.indexOf(language);
            if (index < 0) {
                return new Failure(MessageFormat.format(UNKNOWN_LANGUAGE, language));
            } else {
                IStatus res = new Success();
                if ((index == 0 && direction == MoveLanguageDirection.BACKWARD) || (index == languages.size() - 1 && direction == MoveLanguageDirection.FORWARD)) {
                    res =  new Failure("Invalid language move");
                } else {
                    int delta = 1;
                    if (direction == MoveLanguageDirection.BACKWARD) {
                        delta = -1;
                    }
                    ECollections.move(languages, index, index + delta);
                    ECollections.move(bodies, index, index + delta);
                }
                return res;
            }
        });
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        LanguageExpressionDescription languageExpressionDescription = this.props.getLanguageExpressionDescription();

        String label = languageExpressionDescription.getLabelProvider().apply(variableManager);

        VariableManager idVariableManager = variableManager.createChild();
        idVariableManager.put(FormComponent.TARGET_OBJECT_ID, languageExpressionDescription.getTargetObjectIdProvider().apply(variableManager));
        idVariableManager.put(FormComponent.CONTROL_DESCRIPTION_ID, languageExpressionDescription.getId());
        idVariableManager.put(FormComponent.WIDGET_LABEL, label);
        String id = languageExpressionDescription.getIdProvider().apply(idVariableManager);



        List<String> iconURL = languageExpressionDescription.getIconURLProvider().apply(variableManager);
        Boolean readOnly = languageExpressionDescription.getIsReadOnlyProvider().apply(variableManager);

        EObject languageExpressionOwner = variableManager.get(VariableManager.SELF, EObject.class).orElse(null);
        List<LanguageElement> languages = this.getLanguageElements(languageExpressionOwner);

        Function<String, IStatus> addLanguageHandler = (newLanguage) -> {
            return this.addLanguage(languageExpressionOwner, newLanguage);
        };
        Function<String, IStatus> deleteLanguageHandler = (language) -> {
            return this.deleteLanguage(languageExpressionOwner, language);
        };
        BiFunction<String, String, IStatus> editLanguageBodyHandler = (language, newBody) -> {
            return this.editLanguageBody(languageExpressionOwner, language, newBody);
        };

        BiFunction<String, MoveLanguageDirection, IStatus> moveLanguageHandler = (language, direction) -> {
            return this.moveLanguage(languageExpressionOwner, language, direction);
        };

        var builder = LanguageExpressionElementProps.newLanguageExpressionElementProps(id)//
                .label(label)//
                .iconURL(iconURL)//
                .diagnostics(List.of())//
                .languages(languages)//
                .predefinedLanguages(this.getPredefinedLanguages())//
                .addLanguageHandler(addLanguageHandler)//
                .deleteLanguageHandler(deleteLanguageHandler)//
                .editLanguageBodyHandler(editLanguageBodyHandler)//
                .moveLanguageHandler(moveLanguageHandler);
        if (languageExpressionDescription.getHelpTextProvider() != null) {
            builder.helpTextProvider(() -> languageExpressionDescription.getHelpTextProvider().apply(variableManager));
        }
        if (readOnly != null) {
            builder.readOnly(readOnly);
        }

        return new Element(LanguageExpressionElementProps.TYPE, builder.build());
    }

}
