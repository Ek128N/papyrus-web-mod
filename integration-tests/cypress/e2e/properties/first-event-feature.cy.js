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

describe('Basic widgets test', () => {
  /**
   * For each test, we start with a fresh new project containing all concepts gathered in one single model
   */
  beforeEach(() => {
    cy.deleteAllProjects();
    cy.createProject('Cypress Project').then((res) => {
      const projectId = res.body.data.createProject.project.id;
      cy.wrap(projectId).as('projectId');
      cy.visit(`/projects/${projectId}/edit`).then((res) => {
        cy.getByTestId('upload-document-icon').click();
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
            cy.getByTestId('upload-document-submit').click();
            cy.getByTestId('model4test.uml-more').should('be.visible').click();
            cy.getByTestId('expand-all').should('be.visible').click();
            cy.getByTestId('DurationObservation').should('be.visible').click();
            cy.activateDetailsTab('UML').should('be.visible');
          });
      });
    });
  });

  it('Test the primitive list widget handling first event feature', () => {
    cy.getByTestId('primitive-list-table').get('tbody>tr>td>p').as('prim-items');
    cy.getByTestId('primitive-list-input').find('input').as('prim-input');

    // add 'true' value using add button
    cy.get('@prim-input').type('true');
    cy.getByTestId('primitive-list-add').click();
    cy.get('@prim-items').first().should('have.text', 'true');

    // add 'false' using keyboard
    cy.get('@prim-input').type('false{enter}');
    cy.get('@prim-items').should('have.lengthOf', 2);
    cy.get('@prim-items').eq(0).should('have.text', 'true');
    cy.get('@prim-items').eq(1).should('have.text', 'false');

    // Try to add another value but nothing should be done => No other value is added (because the feature is tagged as isUnique)
    cy.get('@prim-input').type('false{enter}');
    cy.get('@prim-items').should('have.lengthOf', 2);

    // delete false value => the false value is removed
    cy.getByTestId('primitive-list-item-delete-button-false').click();
    cy.get('@prim-items').should('have.lengthOf', 1);
    cy.get('@prim-items').eq(0).should('have.text', 'true');

    // Add invalid => No item added and input field is empty
    cy.get('@prim-input').type('invalid value');
    cy.getByTestId('primitive-list-add').click();
    cy.get('@prim-items').should('have.lengthOf', 2);
    cy.get('@prim-items').eq(0).should('have.text', 'true');
    cy.get('@prim-items').eq(1).should('have.text', 'false');
    cy.get('@prim-input').should('have.value', '');
  });
});
