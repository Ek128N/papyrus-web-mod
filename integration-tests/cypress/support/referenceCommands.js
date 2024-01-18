/*****************************************************************************
 * Copyright (c) 2024 CEA LIST, Obeo.
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

/**
 * Open the create child dialog of the given reference within the given container.
 * This command does not create the element, only opens the dialog.
 *
 * @param refName the reference widget testid
 * @param containerName the testid of the element which will contains the new created element
 */
Cypress.Commands.add('openReferenceChildCreationDialog', (refName, containerName) => {
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
});
