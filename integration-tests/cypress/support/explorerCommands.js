/*****************************************************************************
 * Copyright (c) 2023, 2024 CEA LIST, Obeo.
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
 * Expand all underneath nodes of the given element.
 * @param nodeName the testid of the explorer node we want to expand
 */
Cypress.Commands.add('expandAll', (nodeName) => {
  cy.getByTestId(`${nodeName}-more`).should('be.exist').click({ scrollBehavior: 'center' });
  cy.getByTestId('expand-all').should('be.visible').click();
});

/**
 * Check that the given node has the given children set.
 * This check can be strict or not.
 * @param node the testid of the node we want to check children
 * @param children the array of children testid
 * @param strict if <code>true</code> then children found underneath the given node should match exactly the given array.
 * If <code>false</code> only check that given children are present underneath the given node.
 * Default value is <code>true</code>.
 */
Cypress.Commands.add('checkChildren', (node, children, strict = true) => {
  cy.getByTestId(`${node}-toggle`).should('be.visible').parent().parent().children('ul').as('father');
  if (strict) {
    cy.get('@father').children().should('length', children.length);
  }
  children.forEach((child) => {
    cy.get('@father').contains(child);
  });
});

/**
 * Check that the given node has no children in the explorer tree.
 * @param node the testid of the node
 */
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
