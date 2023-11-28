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

describe('PackageImport.importedPackage search scope test', () => {
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
          });
      });
    });
  });

  const checkDropdownContent = (content) => {
    cy.get('.MuiAutocomplete-popper').find('ul').as('dropdown');
    cy.get('@dropdown').find('li').should('have.length', content.length);
    cy.get('@dropdown')
      .find('li')
      .should(($options) => {
        const optionTexts = $options.toArray().map((el) => el.innerText);
        expect(optionTexts).to.deep.eq(content);
      });
  };

  it('PackageImport with standard packages + new non root package', () => {
    // check initial content
    cy.getByTestId('PackageImport').should('be.visible').click();
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
    cy.getByTestId('Imported package').find('.MuiAutocomplete-endAdornment').find('button').click();
    checkDropdownContent(['model4test', 'Package', 'Ecore', 'EcorePrimitiveTypes', 'StandardProfile', 'UML', 'Ecore']);
  });
});
