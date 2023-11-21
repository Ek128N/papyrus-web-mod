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

describe('Mono-valued reference widget tests', () => {
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
              cy.getByTestId('FunctionBehavior').should('be.visible').click();
              cy.activateDetailsTab('UML');
            });
        });
      });
    });

    it('set reference value with dropdown and remove value', () => {
        // open dropdown
        cy.getByTestId('Specification').click();
        cy.get('.MuiAutocomplete-popper').find('ul').as('dropdown');
        // check content
        cy.get('@dropdown').find('li').should('have.length', 2);
        cy.get('@dropdown').findByTestId('option-Operation1').should('be.visible');
        cy.get('@dropdown').findByTestId('option-Operation2').should('be.visible');
        // select Operation1
        cy.get('@dropdown').findByTestId('option-Operation1').click();
        // reopen dropdown
        cy.getByTestId('Specification').click();
        cy.get('.MuiAutocomplete-popper').should('be.visible').find('ul').as('dropdown');
        // check that Operation1 is not longer in the dropdown
        cy.get('@dropdown').find('li').should('have.length', 1);
        cy.get('@dropdown').findByTestId('option-Operation2').should('be.visible');
        // close dropdown
        cy.getByTestId('Specification').click();
        // check Operation1 is set and remove it
        cy.getByTestId('reference-value-Operation1').should('be.visible').find('svg').click();
        // check that it is no longer there
        cy.getByTestId('reference-value-Operation1').should('not.exist');
    });

    it('set reference value with dialog', () => {
        // open ... dialog
        cy.getByTestId('Specification-more').click();
        cy.get('[role="dialog"]').as('dialog');
        // two roots Primitive types and model4test, expand model4test
        cy.get('@dialog').findByTestId('tree-root-elements').find('li').should('have.length', 2);
        cy.get('@dialog').findByTestId('model4test-toggle').should('be.visible').click();
        cy.get('@dialog').findByTestId('Class-toggle').should('be.visible').click();
        // select Operation2
        cy.get('@dialog').findByTestId('tree-root-elements')
            .find('ul')
            .find('ul')
            .children()
            .should('have.length', 2)
            .findByTestId('Operation2')
            .click();
        // close the dialog
        cy.get('@dialog').findByTestId('select-value').click();    
        // check the reference value content
        cy.getByTestId('reference-value-Operation1').should('not.exist');
        cy.getByTestId('reference-value-Operation2').should('be.visible');
    });

    const checkChildren = (node, children) => {
        cy.getByTestId(`${node}-toggle`).should('be.visible').parent().parent().children('ul').as('father');
        cy.get('@father').children().should('length', children.length);
        children.forEach((child) => {
          cy.get('@father').contains(child);
        });
      };

    it('create new value element and clear reference content', () => {
        cy.getByTestId('Specification-add').should('be.visible').click();
        cy.get('[role="dialog"]').as('dialog');
        // check only model4test model as root of the tree
        cy.get('@dialog').findByTestId('tree-root-elements').find('li').should('have.length', 1);
        // create a Reception under Activity node
        cy.get('@dialog').findByTestId('model4test.uml-toggle').should('be.visible').click();
        cy.get('@dialog').findByTestId('model4test-toggle').should('be.visible').click();
        cy.get('@dialog').findByTestId('Activity').should('be.visible').click();
        cy.get('@dialog').findByTestId('childCreationDescription').children('[role="button"]').contains('Operation (in ownedOperation)').click();
        cy.get('[data-value="ownedReception::Reception"]').should('be.visible').click();
        cy.getByTestId('create-object').click();
        // check reference value added
        cy.getByTestId('reference-value-Reception1').should('be.visible');
        // check instance is properly created
        checkChildren('Activity', ['Reception1']);
        // clear reference content
        cy.getByTestId('Specification-clear').should('be.visible').click();
        // check reference no longer contains previous content
        cy.getByTestId('reference-value-').should('not.exist');
    });

});