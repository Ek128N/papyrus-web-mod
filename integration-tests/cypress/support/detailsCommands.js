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
 * Activate a tab of the Details view according to its name
 *
 * @param {string} tabName the label of the tab
 */
Cypress.Commands.add('activateDetailsTab', (tabName) => {
  cy.getByTestId(`page-tab-${tabName}`).click();
  return cy.get('[data-testid="view-Details"]');
});

/**
 * Return the root element of Details panel
 */
Cypress.Commands.add('inDetailsCurrentTab', () => {
  return cy.get('[data-testid="view-Details"]');
});
