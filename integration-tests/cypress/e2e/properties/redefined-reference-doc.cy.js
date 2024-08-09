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
const context = {};

describe('Redefined reference types test', () => {
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
