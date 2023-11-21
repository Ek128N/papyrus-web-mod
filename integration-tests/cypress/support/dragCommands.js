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

/**
 * Provide a command to handle drag and drop.
 *
 */
Cypress.Commands.add('drag', (dragSelector, dropSelector, options = undefined) => {
  if (options?.inside) {
    cy.get(`${options.inside} ${dragSelector}`).as('dragElt');
    cy.get(`${options.inside} ${dropSelector}`).as('dropElt');
  } else {
    cy.get(dragSelector).as('dragElt');
    cy.get(dropSelector).as('dropElt');
  }
  cy.get('@dragElt').should('exist').should('be.visible');
  cy.get('@dropElt').should('exist').should('be.visible');

  const dataTransfer = new DataTransfer();
  cy.get('@dragElt').trigger('dragstart', {
    dataTransfer,
  });

  cy.get('@dropElt').trigger('drop', {
    dataTransfer,
  });

  return cy.get('@dragElt');
});

Cypress.Commands.add('dragByTestId', (dragId, dropId, options = undefined) => {
  const dragSelector = `[data-testid="${dragId}"]`;
  const dropSelector = `[data-testid="${dropId}"]`;

  return cy.drag(dragSelector, dropSelector, options);
});
