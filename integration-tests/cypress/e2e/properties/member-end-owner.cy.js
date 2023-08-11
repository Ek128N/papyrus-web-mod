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

describe('Member end owner test', () => {
  /**
   * For each test, we start with a fresh new project containing all concepts gathered in one single model
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

  const checkChildren = (node, children) => {
    cy.getByTestId(`${node}-toggle`).should('be.visible').parent().parent().children('ul').as('father');
    cy.get('@father').children().should('length', children.length);
    children.forEach((child) => {
      cy.get('@father').contains(child);
    });
  };

  const checkNoChildren = (node) => {
    cy.getByTestId(`${node}`)
      .should('be.visible')
      .parent()
      .parent()
      .siblings()
      .first()
      .should('have.prop', 'tagName')
      .should('not.eq', 'SVG');
  };

  it('member end owner test', () => {
    // Verify initial situation (Association has two children: properties)
    checkChildren('Association', ['from', 'to']);
    checkNoChildren('Activity');
    // Change the owner of 'from' association's member to 'classifier'
    cy.getByTestId('Association').click();
    cy.activateDetailsTab('UML');
    cy.getByTestId('primitive-radio-Classifier')
      .first() // 'from' is the first member end owner
      .as('classifier')
      .click();
    cy.get('@classifier').children().first().should('have.class', 'Mui-checked');
    // Verify that Association has only one child ('to')
    checkChildren('Association', ['to']);
    // Verify that Activity has one child ('from')
    checkChildren('Activity', ['from']);
    // Change the owner of 'to' association's member to 'association'
    cy.activateDetailsTab('UML');
    cy.getByTestId('Association').click();
    cy.getByTestId('primitive-radio-Association')
      .first() // 'from' is the first member end owner
      .as('association')
      .click();
    cy.get('@association').children().first().should('have.class', 'Mui-checked');
    // Verify that Association has two children ('form', 'to')
    checkChildren('Association', ['from', 'to']);
    // Verify that Activity has no more children now
    checkNoChildren('Activity');
  });
});
