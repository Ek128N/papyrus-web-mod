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

const projectName = 'Cypress Project - first-event-feature';
const context = {};

describe('First event tests', () => {
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

  it('Test the primitive list widget handling first event feature', () => {
    cy.getByTestId('DurationObservation').should('be.visible').click();
    cy.activateDetailsTabAndWaitForElement('UML', 'primitive-list-table-First event').find('tr>td>p').as('prim-items');
    cy.getByTestId('primitive-list-autocomplete-textfield-First event').find('input').as('prim-input');

    // add 'true' value using add button
    cy.get('@prim-input').type('true');
    cy.get('.MuiAutocomplete-popper').find('ul').as('dropdown');
    cy.get('@dropdown').find('li').contains('true').click();
    cy.getByTestId('primitive-list-add-First event').click();
    cy.get('@prim-items').first().should('have.text', 'true');

    // add 'false' using keyboard
    cy.get('@prim-input').type('false{downArrow}{enter}{enter}');
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

    // Add invalid => Empty dropdown and add is disabled
    cy.get('@prim-input').type('invalid value');
    cy.get('.MuiAutocomplete-popper').contains('No options');
    cy.getByTestId('primitive-list-add-First event').should('have.attr', 'disabled');
  });
});
