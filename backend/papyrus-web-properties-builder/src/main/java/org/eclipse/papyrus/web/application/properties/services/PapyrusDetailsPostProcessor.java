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
 ***************************************************************************/
package org.eclipse.papyrus.web.application.properties.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.forms.api.IFormPostProcessor;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.forms.Page;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Service;

/**
 * This service is in charge of sorting pages in Details panel. Order of pages should be: UML Comments Profiles Advanced
 * (last one if other pages )
 *
 * @author Jerome Gout
 */
@Service
public class PapyrusDetailsPostProcessor implements IFormPostProcessor {

    /**
     * The label of the UML page.
     */
    private static final String UML_PAGE_LABEL = "UML";

    /**
     * The label of the Comments page
     */
    private static final String COMMENTS_PAGE_LABEL = "Comments";

    /**
     * The label of the Profile page
     */
    private static final String PROFILE_PAGE_LABEL = "Profile";

    /**
     * The label of the Advanced page
     */
    private static final String ADVANCED_PAGE_LABEL = "Advanced";

    private Optional<Page> getPage(Form form, String pageLabel) {
        return form.getPages().stream().filter(p -> pageLabel.equals(p.getLabel())).findFirst();
    }

    private List<Page> getExtraPages(Form form) {
        return form.getPages().stream().filter(
                p -> !UML_PAGE_LABEL.equals(p.getLabel()) && !COMMENTS_PAGE_LABEL.equals(p.getLabel()) && !PROFILE_PAGE_LABEL.equals(p.getLabel()) && !ADVANCED_PAGE_LABEL.equals(p.getLabel()))
                .toList();
    }

    @Override
    public Form postProcess(Form form, VariableManager variableManager) {
        List<Page> newPages = new ArrayList<>();
        Optional<Page> umlPage = this.getPage(form, UML_PAGE_LABEL);
        Optional<Page> commentsPage = this.getPage(form, COMMENTS_PAGE_LABEL);
        Optional<Page> profilePage = this.getPage(form, PROFILE_PAGE_LABEL);
        Optional<Page> advancedPage = this.getPage(form, ADVANCED_PAGE_LABEL);
        if (umlPage.isPresent()) {
            newPages.add(umlPage.get());
        }
        // other pages are just sorted by name
        newPages.addAll(this.getExtraPages(form).stream().sorted(Comparator.comparing(Page::getLabel)).toList());
        if (commentsPage.isPresent()) {
            newPages.add(commentsPage.get());
        }
        if (profilePage.isPresent()) {
            newPages.add(profilePage.get());
        }
        if (advancedPage.isPresent()) {
            newPages.add(advancedPage.get());
        }
        return Form.newForm(form).pages(newPages).build();
    }

}
