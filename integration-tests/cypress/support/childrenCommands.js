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

Cypress.Commands.add('checkChildren', (node, children, strict = true) => {
  cy.getByTestId(`${node}-toggle`).should('be.visible').parent().parent().children('ul').as('father');
  if (strict) {
    cy.get('@father').children().should('length', children.length);
  }
  children.forEach((child) => {
    cy.get('@father').contains(child);
  });
});

Cypress.Commands.add('checkNoChildren', (node) => {
  cy.getByTestId(`${node}`)
    .should('be.visible')
    .parent()
    .parent()
    .siblings()
    .first()
    .should('have.prop', 'tagName')
    .should('not.eq', 'SVG');
});
