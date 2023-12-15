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

describe('Redefined reference types test', () => {
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

  const openChildCreationDialog = (refName, containerName, childDescription) => {
    cy.getByTestId(`${refName}-add`).click();
    cy.getByTestId('create-modal').should('be.visible').as('dialog');
    cy.get('@dialog')
      .findByTestId('tree-root-elements')
      .findByTestId('model4test.uml-toggle')
      .should('be.visible')
      .click();
    cy.get('@dialog').findByTestId('tree-root-elements').findByTestId('model4test-toggle').click();
    cy.get('@dialog').findByTestId('tree-root-elements').findByTestId(containerName).should('be.visible').click();
    cy.get('@dialog')
      .findByTestId('tree-root-elements')
      .findByTestId(containerName)
      .parent()
      .parent()
      .should('have.attr', 'data-testid', 'selected');
  };

  const checkChildDescriptionContent = (content) => {
    cy.getByTestId('create-modal').should('be.visible').as('dialog');
    cy.getByTestId('create-modal')
      .findByTestId('childCreationDescription')
      .should('not.have.class', 'Mui-disabled')
      .click();
    cy.get('#menu-').find(`li`).should('have.length', content.length);
    content.forEach((element) => {
      cy.get('#menu-').find(`li`).contains(element).should('be.visible');
    });
  };

  it('check ConnectableElementTemplateParameter.parameteredElement: ConnectableElement instead of ParameterableElement', () => {
    cy.getByTestId('ConnectableElementTemplateParameter').should('be.visible').click();
    cy.activateDetailsTab('UML').should('be.visible');
    openChildCreationDialog('Parametered element', 'Activity');
    checkChildDescriptionContent([
      'ExtensionEnd (in ownedAttribute)',
      'Parameter (in ownedParameter)',
      'Port (in ownedAttribute)',
      'Property (in ownedAttribute)',
      'Variable (in variable)',
    ]);
  });

  it('check DurationConstraint.specification: DurationInterval instead of ValueSpecification', () => {
    cy.getByTestId('DurationConstraint').should('be.visible').click();
    cy.activateDetailsTab('UML').should('be.visible');
    cy.getByTestId('containment-reference-Specification')
      .findByTestId('containment-reference-create-child')
      .should('be.visible')
      .click();
    cy.getByTestId('containment-reference-Specification').findByTestId('DurationInterval1').should('be.visible');
  });
});
