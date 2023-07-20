/*******************************************************************************
 * Copyright (c) 2023 CEA LIST
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

describe('/projects', () => {
  beforeEach(() => {
    cy.deleteAllProjects();
    cy.createProject('Cypress Project').then((res) => {
      const projectId = res.body.data.createProject.project.id;
      cy.wrap(projectId).as('projectId');
      cy.visit(`/projects/${projectId}/edit`);
    });
  });

  /**
   * Test validating the root UML model creation from a blank project
   */
  it('Test base UML stereotypes', () => {
    cy.getByTestId('new-model').click();
    cy.getByTestId('name-input').type('Base UML.uml');
    cy.getByTestId('stereotype').click();
    cy.get('li').contains('UML.uml').click();
    cy.getByTestId('create-document').click();
    cy.getByTestId('explorer://').findByTestId('Base UML.uml').should('be.visible').dblclick();
  });
});
