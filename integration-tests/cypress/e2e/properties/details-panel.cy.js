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
describe('Basic widgets test', () => {
  /**
   * For each test, we start with a fresh new project containing several concepts useful to exercise the properties view
   */
  beforeEach(() => {
    cy.deleteAllProjects();
    cy.createProject('Cypress Project').then((res) => {
      const projectId = res.body.data.createProject.project.id;
      cy.wrap(projectId).as('projectId');
      cy.visit(`/projects/${projectId}/edit`).then((res) => {
        cy.getByTestId('upload-document').click();
        cy.fixture('model4test.uml', { mimeType: 'text/xml' }).as('model4test');
        cy.getByTestId('file')
          .selectFile(
            {
              contents: '@model4test',
              fileName: 'model4test.uml', // workaround for selectFile issue https://github.com/cypress-io/cypress/issues/21936
            },
            { force: true }
          )
          .then(() => {
            cy.get(`button[data-testid="upload-document"][type="submit"]`).click(); // TODO testid not unique
            cy.getByTestId('model4test.uml-more').should('be.visible').click();
            cy.getByTestId('expand-all').should('be.visible').click();
          });
      });
    });
  });

  /**
   * Test validating that Detail panel has 4 different pages
   */
  it.skip('Check Details panel contents 4 children', () => {
    cy.getByTestId('Package').click();
    cy.activateDetailsTab('Advanced')
      .should('be.visible')
      .get('div[role="tablist"] > button')
      .should(($buttons) => {
        expect($buttons).to.have.length(4);
      });
  });

  /**
   * Check order of tabs in details panel
   */
  it('Check Details panel tabs order', () => {
    cy.getByTestId('Abstraction').click();
    cy.inDetailsCurrentTab()
      .should('be.visible')
      .find('[role="tablist"]')
      .children()
      .then(($els) => {
        // jQuery => Array => get "innerText" from each
        return Cypress._.map(Cypress.$.makeArray($els), 'innerText');
      })
      .should('deep.equal', ['UML', 'Comments', 'Profile', 'Advanced']);
  });
});
