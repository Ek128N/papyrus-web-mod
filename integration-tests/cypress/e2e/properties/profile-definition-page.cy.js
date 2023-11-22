/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
describe('Profile definition page test', () => {

    beforeEach(() => {
        cy.deleteAllProjects();
        // create a new project with profile UML template
        const templateId = 'DefaultProfileWithPrimitiveAndUml';
        cy.createProjectFromTemplate(templateId).then((res) => {
          const projectId = res.body.data.createProjectFromTemplate.project.id;
          const repToOpen = res.body.data.createProjectFromTemplate.representationToOpen.id;
          cy.visit(`/projects/${projectId}/edit/${repToOpen}`);
        });
    });
    
    
    it('published profile is properly displayed in Definition page', ()=> {
        // publish the profile
        cy.getByTestId('testProfile-more').should('be.visible').click();
        cy.get('.MuiPopover-root').findByTestId('publish-profile').should('be.visible').click();
        cy.getByTestId('publish-profile-dialog').findByTestId('publish-profile-author').find('input').type('Jerome');
        cy.getByTestId('publish-profile-dialog').findByTestId('publish-profile-comment').find('textarea').first().type('comment content');
        cy.getByTestId('publish-profile-dialog').findByTestId('publish-profile-copyright').find('textarea').first().type('copyright content');
        cy.getByTestId('publish-profile-dialog').findByTestId('publish-profile-publish').should('be.visible').click();
        
        // check that the Definition page contains the entered data
        cy.activateDetailsTab('Definition').as('definition');
        cy.get('@definition').findByTestId('Author').find('input').should('be.visible').should('be.disabled').should('have.value', 'Jerome');
        cy.get('@definition').findByTestId('Copyright').find('input').should('be.visible').should('be.disabled').should('have.value', 'copyright content');
        cy.get('@definition').findByTestId('Comment').find('textarea').first().should('be.visible').should('be.disabled').should('have.value', 'comment content');
    });

    it('published profile can be removed', ()=> {
        // publish the profile twice
        cy.getByTestId('testProfile-more').should('be.visible').click();
        cy.get('.MuiPopover-root').findByTestId('publish-profile').should('be.visible').click();
        cy.getByTestId('publish-profile-dialog').findByTestId('publish-profile-publish').should('be.visible').click();

        cy.getByTestId('testProfile-more').should('be.visible').click();
        cy.get('.MuiPopover-root').findByTestId('publish-profile').should('be.visible').click();
        cy.getByTestId('publish-profile-dialog').findByTestId('publish-profile-publish').should('be.visible').click();
        
        // check that there are two profile definition
        cy.activateDetailsTab('Definition').as('definition');
        cy.get('@definition').findByTestId('form').children().eq(1).children().should('have.length', 2);

        // remove 0.0.1
        cy.get('@definition').findByTestId('form').children().eq(1).children().eq(1).find('button').click();

        // check that there are two profile definition
        cy.get('@definition').findByTestId('form').children().eq(1).children().should('have.length', 1);
    });
});