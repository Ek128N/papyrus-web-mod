/*****************************************************************************
 * Copyright (c) 2023, 2025 CEA LIST, Obeo.
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
  beforeEach(() => {
    cy.deleteProjectByName('C++ SM');
    const templateId = 'UMLCppSMProject';
    cy.createProjectFromTemplate(templateId).then((res) => {
      const projectId = res.body.data.createProjectFromTemplate.project.id;
      const repToOpen = res.body.data.createProjectFromTemplate.representationToOpen.id;
      cy.visit(`/projects/${projectId}/edit/${repToOpen}`);
    });
  });

  afterEach(() => {
    cy.deleteProjectByName('C++ SM');
  });

  /**
   * Test validating the deletion of a representation
   */
  it('can delete a representation', () => {
    cy.expandAll('SimpleSM.uml');
    cy.getByTestId('«ExecuteTrafoChain, GeneratorHint» CppSMTemplate-more')
      .scrollIntoView()
      .should('be.visible')
      .click();

    cy.getByTestId('new-representation').click();

    cy.getByTestId('name').clear();
    cy.getByTestId('name').type('class diagram test');
    cy.getByTestId('create-representation').click();
    cy.getByTestId('create-representation').should('not.exist');
    
    cy.getByTestId('class diagram test').should('be.visible').click();
    cy.getByTestId('representation-tab-class diagram test').should('be.visible');

    cy.getByTestId('explorer://').contains('class diagram test');

    cy.getByTestId('class diagram test-more').click();
    cy.getByTestId('delete').click();
    cy.getByTestId('confirmation-dialog-button-ok').click();

    cy.getByTestId('class diagram test-more').should('not.exist');
    cy.getByTestId('representation-area')
      .should('be.visible')
      .find('h6')
      .should('have.text', 'The representation is not available anymore');
  });
});
