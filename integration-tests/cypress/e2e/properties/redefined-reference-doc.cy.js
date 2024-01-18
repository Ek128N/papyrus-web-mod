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

const projectName = 'Cypress Project - redefined-reference-doc';

describe('Redefined reference types test', () => {
  /**
   * For each test, we start with a fresh new project containing all concepts gathered in one single model
   */
  beforeEach(() => {
    cy.deleteProjectByName(projectName);
    cy.createProject(projectName).then((res) => {
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
            cy.expandAll('model4test.uml');
          });
      });
    });
  });

  it('check Class.ownedAttribute has the correct tooltip info', () => {
    cy.getByTestId('Class').should('be.visible').click();
    cy.activateDetailsTabAndWaitForElement('UML', 'containment-reference-Owned attribute')
      .children()
      .first()
      .children()
      .first()
      .find('svg')
      .trigger('mouseover');
    cy.get('.MuiTooltip-popper').contains('The attributes (i.e., the Properties) owned by the Class.');
  });
});
