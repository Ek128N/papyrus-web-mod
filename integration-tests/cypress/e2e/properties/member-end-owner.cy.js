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

const projectName = 'Cypress Project - member-end-owner';
const context = {};

describe('Member end owner test', () => {
  /**
   * For each test, we start with a fresh new project containing all concepts gathered in one single model
   */
  beforeEach(() => {
    cy.deleteProjectByName(projectName);
    cy.createTestProject(context, projectName);
  });

  afterEach(() => {
    cy.deleteProjectByName(projectName);
  });

  it('member end owner test', () => {
    // Verify initial situation (Association has two children: properties)
    cy.checkChildren('Association', ['from', 'to']);
    cy.checkNoChildren('Activity');
    // Change the owner of 'from' association's member to 'classifier'
    cy.getByTestId('Association').click();
    cy.activateDetailsTabAndWaitForElement('UML', 'primitive-radio-Classifier')
      .first() // 'from' is the first member end owner
      .as('classifier')
      .click();
    cy.get('@classifier').children().first().should('have.class', 'Mui-checked');
    // Verify that Association has only one child ('to')
    cy.checkChildren('Association', ['to']);
    // Verify that Activity has one child ('from')
    cy.checkChildren('Activity', ['from']);
    // Change the owner of 'to' association's member to 'association'
    cy.getByTestId('Association').click();
    cy.activateDetailsTabAndWaitForElement('UML', 'primitive-radio-Association')
      .first() // 'from' is the first member end owner
      .as('association')
      .click();
    cy.get('@association').children().first().should('have.class', 'Mui-checked');
    // Verify that Association has two children ('form', 'to')
    cy.checkChildren('Association', ['from', 'to']);
    // Verify that Activity has no more children now
    cy.checkNoChildren('Activity');
  });
});
