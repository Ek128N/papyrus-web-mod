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
            cy.getByTestId('FunctionBehavior').should('be.visible').click();
            cy.activateDetailsTab('UML');
          });
      });
    });
  });

  const toggleLanguage = (language) => {
    cy.getByTestId(`le-language-${language}`).children().first().click(); // first children is the accordion summary
  };

  it('Language expression custom widget tests', () => {
    // add predefined: JAVA
    cy.getByTestId('le-open-add-language-dialog').should('be.visible').click();
    cy.getByTestId('le-add-language-dialog-know-languages')
      .should('be.visible')
      .should('have.css', 'border', '2px solid rgba(0, 0, 0, 0.125)');
    cy.getByTestId('le-add-language-dialog-ok').should('have.class', 'Mui-disabled');
    cy.getByTestId('le-add-language-dialog-language-JAVA').click();
    cy.getByTestId('le-add-language-dialog-know-languages').should('have.css', 'border', '2px solid rgb(190, 26, 120)');
    cy.getByTestId('le-add-language-dialog-ok').should('not.have.class', 'Mui-disabled').click();
    cy.getByTestId('le-language-JAVA').should('be.visible');
    cy.getByTestId('le-language-JAVA-body').should('not.be.visible');
    toggleLanguage('JAVA');
    cy.getByTestId('le-language-JAVA-body').should('be.visible').should('have.value', '');
    cy.getByTestId('le-language-JAVA-body').find('textarea').first().as('java-body');
    cy.get('@java-body').type('Java');
    toggleLanguage('JAVA');
    cy.getByTestId('le-language-JAVA-body').should('not.be.visible');
    // add new language: JAVA => error
    cy.getByTestId('le-open-add-language-dialog').should('be.visible').click();
    cy.getByTestId('le-add-language-dialog-new-language').as('new-language');
    cy.get('@new-language').find('input').type('JAVA');
    cy.get('@new-language').contains('This language already exists');
    // close dialog
    cy.getByTestId('le-add-language-dialog').click(0, 0);
    // add new language: RUST
    cy.getByTestId('le-open-add-language-dialog').should('be.visible').click();
    cy.getByTestId('le-add-language-dialog-new-language').find('input').type('RUST');
    cy.getByTestId('le-add-language-dialog-ok').click();
    cy.getByTestId('le-language-RUST').should('be.visible');
    toggleLanguage('RUST');
    cy.getByTestId('le-language-RUST-body').find('textarea').first().as('rust-body');
    cy.get('@rust-body').type('Rust');
    toggleLanguage('JAVA');
    cy.get('@java-body').should('be.visible').should('have.value', 'Java');
    cy.get('@rust-body').should('not.be.visible');
    // Check order: JAVA, RUST
    cy.getByTestId('language-expression-widget').children().should('have.length', 3);
    cy.getByTestId('language-expression-widget').children().eq(1).contains('JAVA');
    cy.getByTestId('language-expression-widget').children().eq(2).contains('RUST');
    // move down JAVA
    cy.getByTestId('le-language-JAVA-down').should('be.visible').click();
    cy.getByTestId('language-expression-widget').children().eq(2).contains('JAVA');
    cy.getByTestId('language-expression-widget').children().eq(1).contains('RUST');
    // Move does not change expanding state
    cy.get('@java-body').should('be.visible').should('have.value', 'Java');
    cy.get('@rust-body').should('not.be.visible');
    // Move up JAVA
    cy.getByTestId('le-language-JAVA-up').should('be.visible').click();
    cy.getByTestId('language-expression-widget').children().eq(1).contains('JAVA');
    cy.getByTestId('language-expression-widget').children().eq(2).contains('RUST');
    // Delete JAVA
    cy.getByTestId('le-language-JAVA-delete').should('be.visible').click();
    // Verify only RUST remains (its body is still not visible)
    cy.getByTestId('language-expression-widget').children().should('have.length', 2);
    cy.getByTestId('language-expression-widget').children().eq(1).contains('RUST');
    cy.get('@rust-body').should('not.be.visible');
  });
});
