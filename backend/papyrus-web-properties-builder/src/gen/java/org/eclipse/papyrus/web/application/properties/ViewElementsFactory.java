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
import org.eclipse.sirius.components.view.CheckboxDescription;
import org.eclipse.sirius.components.view.FormDescription;
import org.eclipse.sirius.components.view.GroupDescription;
import org.eclipse.sirius.components.view.GroupDisplayMode;
import org.eclipse.sirius.components.view.ListDescription;
import org.eclipse.sirius.components.view.PageDescription;
import org.eclipse.sirius.components.view.RadioDescription;
import org.eclipse.sirius.components.view.SelectDescription;
import org.eclipse.sirius.components.view.TextAreaDescription;
import org.eclipse.sirius.components.view.TextfieldDescription;
import org.eclipse.sirius.components.view.ViewFactory;

public class ViewElementsFactory {

    private ChangeContext createChangeContext(String contextExp) {
        ChangeContext changeCtxt = ViewFactory.eINSTANCE.createChangeContext();
        changeCtxt.setExpression(contextExp);
        return changeCtxt;
    }

    public TextfieldDescription createTextfieldDescription(String name, String labelExp, String valueExp, String contextExp) {
        TextfieldDescription description = ViewFactory.eINSTANCE.createTextfieldDescription();
        description.setName(name);
        description.setLabelExpression(labelExp);
        description.setValueExpression(valueExp);
        description.getBody().add(createChangeContext(contextExp));
        return description;
    }

    public TextAreaDescription createTextAreaDescription(String name, String labelExp, String valueExp, String contextExp) {
        TextAreaDescription description = ViewFactory.eINSTANCE.createTextAreaDescription();
        description.setName(name);
        description.setLabelExpression(labelExp);
        description.setValueExpression(valueExp);
        description.getBody().add(createChangeContext(contextExp));
        return description;
    }

    public CheckboxDescription createCheckboxDescription(String name, String labelExp, String valueExp, String contextExp) {
        CheckboxDescription description = ViewFactory.eINSTANCE.createCheckboxDescription();
        description.setName(name);
        description.setLabelExpression(labelExp);
        description.setValueExpression(valueExp);
        description.getBody().add(createChangeContext(contextExp));
        return description;
    }

    public ListDescription createListDescription(String name, String labelExp, String valueExp, String contextExp, String isDeletableExp) {
        ListDescription description = ViewFactory.eINSTANCE.createListDescription();
        description.setName(name);
        description.setLabelExpression(labelExp);
        description.setValueExpression(valueExp);
        description.setIsDeletableExpression(isDeletableExp);
        description.getBody().add(createChangeContext(contextExp));
        return description;
    }

    public RadioDescription createRadioDescription(String name, String labelExp, String valueExp, String contextExp, String candidatesExp, String candidateLabelExp) {
        RadioDescription description = ViewFactory.eINSTANCE.createRadioDescription();
        description.setName(name);
        description.setLabelExpression(labelExp);
        description.setValueExpression(valueExp);
        description.setCandidatesExpression(candidatesExp);
        description.setCandidateLabelExpression(candidateLabelExp);
        description.getBody().add(createChangeContext(contextExp));
        return description;
    }

    public SelectDescription createSelectDescription(String name, String labelExp, String valueExp, String contextExp, String candidatesExp, String candidateLabelExp) {
        SelectDescription description = ViewFactory.eINSTANCE.createSelectDescription();
        description.setName(name);
        description.setLabelExpression(labelExp);
        description.setValueExpression(valueExp);
        description.setCandidatesExpression(candidatesExp);
        description.setCandidateLabelExpression(candidateLabelExp);
        description.getBody().add(createChangeContext(contextExp));
        return description;
    }

    public PageDescription createPageDescription(String name, String domainType, String labelExpression, String semanticCandidateExpression, String preconditionExpresion) {
        PageDescription page = ViewFactory.eINSTANCE.createPageDescription();
        page.setName(name);
        page.setDomainType(domainType);
        page.setLabelExpression(labelExpression);
        page.setPreconditionExpression(preconditionExpresion);
        page.setSemanticCandidatesExpression(semanticCandidateExpression);
        return page;

    }

    public FormDescription createFormDescription(String name, String domainType, String titleExpression, String preconditionExpression) {
        FormDescription form = ViewFactory.eINSTANCE.createFormDescription();
        form.setName(name);
        form.setDomainType(domainType);
        form.setPreconditionExpression(preconditionExpression);
        form.setTitleExpression(titleExpression);
        return form;

    }

    public GroupDescription createGroupDescription(String name, String labelExpression, String semanticCandidateExpression, GroupDisplayMode groupDisplayMode) {
        GroupDescription form = ViewFactory.eINSTANCE.createGroupDescription();
        form.setName(name);
        form.setLabelExpression(labelExpression);
        form.setDisplayMode(groupDisplayMode);
        form.setSemanticCandidatesExpression(semanticCandidateExpression);
        return form;

    }
}
