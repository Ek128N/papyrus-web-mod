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
describe('/projects/:projectId/edit - Diagram Context Menu', () => {
  beforeEach(() => {
    cy.deleteAllProjects();
    cy.createProject('Cypress Project').then((res) => {
      const projectId = res.body.data.createProject.project.id;
      cy.wrap(projectId).as('projectId');
      cy.visit(`/projects/${projectId}/edit`).then(() => {
        cy.getByTestId('new-model').click();
        cy.getByTestId('name-input').type('Base UML.uml');
        cy.getByTestId('stereotype').click();
        cy.get('li').contains('UML.uml').click();
        cy.getByTestId('create-document').click();
        cy.getByTestId('Base UML.uml-more').should('be.visible').click();
        cy.getByTestId('expand-all').should('be.visible').click();
      });
    });
  });

  /**
   * Test validating the application of a static profile and a stereotype. Use Java profile.
   */
  it('Test Apply static profile and stereotype', () => {
    // open Apply profile dialog
    cy.getByTestId('Model-more').should('be.visible').click();
    cy.getByTestId('apply-profile').should('be.visible').click();

    // open select
    cy.getByTestId('profile').should('be.visible').contains('Standard'); // need to wait that select is populated
    cy.getByTestId('profile').click();
    cy.get('div#menu-.MuiPopover-root').should('be.visible').find('ul > li').eq(2).click(); // 2 => Java
    cy.getByTestId('apply-profile-submit').click();
    cy.getByTestId('Model-more').click();
    cy.getByTestId('expand-all').should('be.visible').click();
    cy.getByTestId('ProfileApplication').should('be.visible').click();
    // wait until details panel is populated (previous click finished)
    cy.getByTestId('view-Details').findByTestId('Is strict').should('be.visible');
    // before switching to Advanced tab
    cy.activateDetailsTab('Advanced').should('be.visible').as('details');
    cy.get('@details').contains('Core Properties').should('be.visible');
    cy.get('@details').findByTestId('reference-value-PapyrusJava').should('be.visible').contains('PapyrusJava');
    // Apply stereotype
    cy.getByTestId('Model-more').should('be.visible').click();
    cy.getByTestId('apply-stereotype').should('be.visible').click();
    // Verify stereotype dialog
    cy.getByTestId('apply-stereotype-dialog-title').contains('Apply a new stereotype').should('be.visible');
    cy.getByTestId('apply-stereotype-dialog-content')
      .findByTestId('stereotype-input')
      .should('have.value', 'PapyrusJava.profile.uml#_PmXVQByJEduN1bTiWJ0lyw');
    // Apply the presented stereotype
    cy.getByTestId('apply-stereotype-dialog')
      .findByTestId('apply-stereotype-submit')
      .should('have.prop', 'type', 'button')
      .click();
    // Verify stereotype applied
    cy.getByTestId('Model').click();
    cy.activateDetailsTab('Advanced').should('be.visible').as('details');
    cy.getByTestId('table-Applied stereotypes')
      .findByTestId('representation-PapyrusJava::ExternLibrary')
      .contains('PapyrusJava::ExternLibrary')
      .should('be.visible');
    cy.getByTestId('ExternLibrary').should('be.visible');
  });
});
