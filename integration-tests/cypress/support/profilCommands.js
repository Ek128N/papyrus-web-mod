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
const url = Cypress.env('baseAPIUrl') + '/api/graphql';

/**
 * Delete a already published profile using its name as identifier
 * @param profileName the name of the profile to delete
 */
Cypress.Commands.add('deletePublishedDynamicProfileByName', (profileName) => {
  const deletePublishProfileMutation = `
  mutation deletePublishedDynamicProfileByName($name: String!){
    deletePublishedDynamicProfileByName(input : $name) {
          __typename
    }
  }
  `;
  cy.request({
    method: 'POST',
    mode: 'cors',
    url,
    body: { query: deletePublishProfileMutation, variables: { name: profileName } },
  });
});

/**
 * Apply a profile (using menu) on a given element
 *
 * @param elementName the name of the element on which the profile needs to be applied
 * @param profileName the name of the profile to apply
 */
Cypress.Commands.add('applyProfileByMenu', (elementName, profileName) => {
  cy.getByTestId(`${elementName}-more`).should('be.visible').click();
  cy.getByTestId('apply-profile').should('be.visible').click();
  cy.get('[aria-labelledby="applyProfileModalProfileLabel"]').should('not.have.class', 'Mui-disabled').click();
  cy.get('#menu-').find('ul').children().contains(profileName).first().click();
  cy.getByTestId('apply-profile-submit').should('exist').click();
});
