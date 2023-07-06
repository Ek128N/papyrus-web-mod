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

package org.eclipse.papyrus.web.application.properties;

import org.eclipse.sirius.components.view.ChangeContext;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.form.CheckboxDescription;
import org.eclipse.sirius.components.view.form.FormDescription;
import org.eclipse.sirius.components.view.form.FormFactory;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;
import org.eclipse.sirius.components.view.form.ListDescription;
import org.eclipse.sirius.components.view.form.PageDescription;
import org.eclipse.sirius.components.view.form.RadioDescription;
import org.eclipse.sirius.components.view.form.SelectDescription;
import org.eclipse.sirius.components.view.form.TextAreaDescription;
import org.eclipse.sirius.components.view.form.TextfieldDescription;

public class ViewElementsFactory {

    private ChangeContext createChangeContext(String contextExp) {
        ChangeContext changeCtxt = ViewFactory.eINSTANCE.createChangeContext();
        changeCtxt.setExpression(contextExp);
        return changeCtxt;
    }

    public TextfieldDescription createTextfieldDescription(String name, String labelExp, String valueExp, String contextExp, String helpExpression) {
        TextfieldDescription description = FormFactory.eINSTANCE.createTextfieldDescription();
        description.setName(name);
        description.setLabelExpression(labelExp);
        description.setValueExpression(valueExp);
        description.getBody().add(createChangeContext(contextExp));
        description.setHelpExpression(helpExpression);
        return description;
    }

    public TextAreaDescription createTextAreaDescription(String name, String labelExp, String valueExp, String contextExp, String helpExpression) {
        TextAreaDescription description = FormFactory.eINSTANCE.createTextAreaDescription();
        description.setName(name);
        description.setLabelExpression(labelExp);
        description.setValueExpression(valueExp);
        description.getBody().add(createChangeContext(contextExp));
        description.setHelpExpression(helpExpression);
        return description;
    }

    public CheckboxDescription createCheckboxDescription(String name, String labelExp, String valueExp, String contextExp, String helpExpression) {
        CheckboxDescription description = FormFactory.eINSTANCE.createCheckboxDescription();
        description.setName(name);
        description.setLabelExpression(labelExp);
        description.setValueExpression(valueExp);
        description.getBody().add(createChangeContext(contextExp));
        description.setHelpExpression(helpExpression);
        return description;
    }

    public ListDescription createListDescription(String name, String labelExp, String valueExp, String contextExp, String isDeletableExp, String helpExpression) {
        ListDescription description = FormFactory.eINSTANCE.createListDescription();
        description.setName(name);
        description.setLabelExpression(labelExp);
        description.setValueExpression(valueExp);
        description.setIsDeletableExpression(isDeletableExp);
        description.getBody().add(createChangeContext(contextExp));
        description.setHelpExpression(helpExpression);
        return description;
    }

    public RadioDescription createRadioDescription(String name, String labelExp, String valueExp, String contextExp, String candidatesExp, String candidateLabelExp, String helpExpression) {
        RadioDescription description = FormFactory.eINSTANCE.createRadioDescription();
        description.setName(name);
        description.setLabelExpression(labelExp);
        description.setValueExpression(valueExp);
        description.setCandidatesExpression(candidatesExp);
        description.setCandidateLabelExpression(candidateLabelExp);
        description.getBody().add(createChangeContext(contextExp));
        description.setHelpExpression(helpExpression);
        return description;
    }

    public SelectDescription createSelectDescription(String name, String labelExp, String valueExp, String contextExp, String candidatesExp, String candidateLabelExp, String helpExpression) {
        SelectDescription description = FormFactory.eINSTANCE.createSelectDescription();
        description.setName(name);
        description.setLabelExpression(labelExp);
        description.setValueExpression(valueExp);
        description.setCandidatesExpression(candidatesExp);
        description.setCandidateLabelExpression(candidateLabelExp);
        description.getBody().add(createChangeContext(contextExp));
        description.setHelpExpression(helpExpression);
        return description;
    }

    public PageDescription createPageDescription(String name, String domainType, String labelExpression, String semanticCandidateExpression, String preconditionExpresion) {
        PageDescription page = FormFactory.eINSTANCE.createPageDescription();
        page.setName(name);
        page.setDomainType(domainType);
        page.setLabelExpression(labelExpression);
        page.setPreconditionExpression(preconditionExpresion);
        page.setSemanticCandidatesExpression(semanticCandidateExpression);
        return page;

    }

    public FormDescription createFormDescription(String name, String domainType, String titleExpression, String preconditionExpression) {
        FormDescription form = FormFactory.eINSTANCE.createFormDescription();
        form.setName(name);
        form.setDomainType(domainType);
        form.setPreconditionExpression(preconditionExpression);
        form.setTitleExpression(titleExpression);
        return form;

    }

    public GroupDescription createGroupDescription(String name, String labelExpression, String semanticCandidateExpression, GroupDisplayMode groupDisplayMode) {
        GroupDescription form = FormFactory.eINSTANCE.createGroupDescription();
        form.setName(name);
        form.setLabelExpression(labelExpression);
        form.setDisplayMode(groupDisplayMode);
        form.setSemanticCandidatesExpression(semanticCandidateExpression);
        return form;

    }
}
