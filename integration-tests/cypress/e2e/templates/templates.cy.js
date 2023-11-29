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
describe('/projects/:projectId/edit - Diagram Context Menu', () => {
  beforeEach(() => {
    cy.deleteAllProjects();
    const templateId = 'UMLCppSMProject';
    cy.createProjectFromTemplate(templateId).then((res) => {
      const projectId = res.body.data.createProjectFromTemplate.project.id;
      const repToOpen = res.body.data.createProjectFromTemplate.representationToOpen.id;
      cy.visit(`/projects/${projectId}/edit/${repToOpen}`);
    });
  });

  /**
   * Test validating creation and deletion of a representation created in a project made using template
   */
  it('Test CPP SM Template', () => {
    cy.getByTestId('SimpleSM.uml').dblclick();
    cy.getByTestId('CppSMTemplate-more').should('be.visible').click();

    cy.getByTestId('new-representation').click();

    cy.getByTestId('name').clear().type('class diagram test');
    cy.getByTestId('create-representation').click();

    cy.getByTestId('explorer://').contains('class diagram test');

    cy.getByTestId('class diagram test-more').click();
    cy.getByTestId('delete').click();

    cy.getByTestId('class diagram test-more').should('not.exist');
    cy.getByTestId('representation-area').should('be.visible').contains('The representation is not available anymore');
  });
});
