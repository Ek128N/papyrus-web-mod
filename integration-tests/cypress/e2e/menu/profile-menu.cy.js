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

describe('/projects/:projectId/edit - Diagram Context Menu', () => {
  const instanceProjectName = 'Cypress Test - ProfileMenu';

  beforeEach(() => {
    cy.deleteProjectByName(instanceProjectName);
    cy.createProjectFromStereotype(instanceProjectName, 'EmptyUMLTemplate', 'Model UML', 'Base UML.uml');
  });

  afterEach(() => {
    cy.deleteProjectByName(instanceProjectName);
  });

  /**
   * Test validating the application of a static profile and a stereotype. Use Java profile.
   */
  it('Test Apply static profile and stereotype', () => {
    // Apply profile Java
    cy.applyProfileByMenu('Model', 'Java');

    cy.expandAll('Model');
    cy.getByTestId('ProfileApplication').should('be.visible').click();

    // wait until details panel is populated (previous click finished)
    cy.getByTestId('view-Details').findByTestId('Is strict').should('be.visible');
    // before switching to Advanced tab
    cy.activateDetailsTabAndWaitForElement('Advanced', 'reference-value-PapyrusJava').contains('PapyrusJava');
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
    cy.activateDetailsTabAndWaitForElement('Profile', 'primitive-list-table-Applied stereotypes')
      .findByTestId('primitive-list-item-content-ExternLibrary (from PapyrusJava)')
      .should('be.visible');
  });
});
