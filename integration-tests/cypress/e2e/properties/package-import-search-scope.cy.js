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

const projectName = 'Cypress Project - package-import-search-scope';
const context = {};

describe('PackageImport.importedPackage search scope test', () => {
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

  it('PackageImport with standard packages + new non root package', () => {
    // check initial content
    cy.getByTestId('<Package Import> PrimitiveTypes').should('be.visible').click();
    cy.getByTestId('Imported package').should('be.visible').find('.MuiChip-root').should('have.length', 1);
    cy.getByTestId('reference-value-PrimitiveTypes').should('be.visible');
    // change to UML package
    cy.getByTestId('Imported package-more').should('be.visible').click();
    cy.getByTestId('browse-modal').findByTestId('tree-root-elements').children().should('have.length', 7);
    cy.getByTestId('browse-modal').findByTestId('tree-root-elements').findByTestId('UML').click();
    cy.getByTestId('browse-modal').findByTestId('select-value').click();
    // check that the value has changed
    cy.getByTestId('Imported package').should('be.visible').find('.MuiChip-root').should('have.length', 1);
    cy.getByTestId('reference-value-UML').should('be.visible');
    // restore primitivetypes using the dropdown
    cy.getByTestId('Imported package').find('.MuiAutocomplete-endAdornment').find('button').click();
    cy.get('.MuiAutocomplete-popper').find('ul').find('li').contains('PrimitiveTypes').should('be.visible').click();
    // check primitivetypes is back
    cy.getByTestId('Imported package').should('be.visible').find('.MuiChip-root').should('have.length', 1);
    cy.getByTestId('reference-value-PrimitiveTypes').should('be.visible');
    // check dropdown content
    cy.checkDropdownContent('Imported package', [
      'model4test',
      'Package',
      'Ecore',
      'EcorePrimitiveTypes',
      'StandardProfile',
      'UML',
      'Ecore',
    ]);
  });
});
