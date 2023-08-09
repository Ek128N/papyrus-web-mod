/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
describe('User doc test', () => {
  beforeEach(() => {
    cy.visit('/projects');
  });

  it('user doc test', () => {
    cy.getByTestId('help-link').invoke('removeAttr', 'target').click();
    cy.get('h1').should('have.text', 'User Documentation');
  });
});
