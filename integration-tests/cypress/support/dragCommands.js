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
  let draggable = dragSelector;
  let target = dropSelector;

  if (options?.inside) {
    draggable = `${options.inside} ${dragSelector}`;
    target = `${options.inside} ${dropSelector}`;
  }

  cy.get(draggable).should('exist').should('be.visible');
  cy.get(target).should('exist').should('be.visible');

  const dataTransfer = new DataTransfer();

  cy.get(draggable).trigger('dragstart', { dataTransfer });
  cy.get(target).trigger('drop', { dataTransfer });
  cy.get(draggable).trigger('dragend');

  return cy.get(draggable);
});

Cypress.Commands.add('dragByTestId', (dragId, dropId, options = undefined) => {
  const dragSelector = `[data-testid="${dragId}"]`;
  const dropSelector = `[data-testid="${dropId}"]`;

  return cy.drag(dragSelector, dropSelector, options);
});

Cypress.Commands.add('dragTo', { prevSubject: 'element' }, (subject, targetEl) => {
  const dataTransfer = new DataTransfer();
  cy.get(subject).trigger('dragstart', { dataTransfer });
  cy.get(targetEl).trigger('drop', { dataTransfer });
});
