/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
describe('Basic widgets test', () => {
  /**
   * For each test, we start with a fresh new project containing all concepts gathered in one single model
   */
  beforeEach(() => {
    cy.deleteAllProjects();
    cy.createProject('Cypress Project').then((res) => {
      const projectId = res.body.data.createProject.project.id;
      cy.wrap(projectId).as('projectId');
      cy.visit(`/projects/${projectId}/edit`).then((res) => {
        cy.getByTestId('upload-document').click();
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
            cy.get(`button[data-testid="upload-document"][type="submit"]`).click(); // TODO testid not unique
            cy.getByTestId('model4test.uml-more').should('be.visible').click();
            cy.getByTestId('expand-all').should('be.visible').click();
          });
      });
    });
  });

  /**
   * Test validating that Detail panel has 4 different pages
   */
  it('Check Details panel contents 4 children', () => {
    cy.getByTestId('Package').click();
    cy.activateDetailsTab('Advanced')
      .should('be.visible')
      .get('div[role="tablist"] > button')
      .should(($lis) => {
        expect($lis).to.have.length(4);
      });
  });

  /**
   * Test validating the Text widget
   */
  it('Test Text description', () => {
    cy.getByTestId('Package').click();
    cy.activateDetailsTab('UML').findByTestId('Name').should('be.visible').find('input').as('nameField');
    // Verify the name of the Package
    cy.get('@nameField').should('have.value', 'Package');
    // change the Package name
    cy.get('@nameField').clear().type('myPackage{enter}');
    // click somewhere else (advanced tab)
    cy.activateDetailsTab('Advanced').contains('Core Properties').should('be.visible');
    // return to check new value has been properly persisted
    cy.activateDetailsTab('UML').findByTestId('Name').should('be.visible');
    cy.getByTestId('Name').find('input').should('have.value', 'myPackage');
  });

  /**
   * Test validating the Text area widget
   */
  it('Test Textarea description', () => {
    cy.getByTestId('Comment').click();
    cy.activateDetailsTab('UML');
    cy.getByTestId('Body').should('be.visible').find('textarea').eq(0).as('textarea');
    // Verify comment if empty
    cy.get('@textarea').should('have.value', '');
    // Fill the comment
    cy.get('@textarea').type('myComment{enter}');
    // Verify the comment content is shown in the explorer tree
    cy.getByTestId('explorer://').find('[title="uml::Comment"]').should('have.attr', 'data-testid', 'myComment');
  });

  /**
   * Test validating the Checkbox widget
   */
  it('Test Checkbox description', () => {
    cy.getByTestId('Activity').click();
    cy.activateDetailsTab('UML');
    cy.getByTestId('Is abstract').should('be.visible').find('input').as('checkbox');
    // Verify default value is not checked
    cy.get('@checkbox').should('be.not.checked');
    // Check the checkbox
    cy.get('@checkbox').check().should('be.checked');
    // click somewhere else (advanced tab)
    cy.activateDetailsTab('Advanced').contains('Core Properties').should('be.visible');
    // return to check new value has been properly persisted
    cy.activateDetailsTab('UML');
    cy.getByTestId('Is abstract').should('be.visible').find('input').should('be.checked');
  });

  /**
   * Test validating the Select widget
   */
  it('Test Select description', () => {
    cy.getByTestId('Class').click();
    cy.activateDetailsTab('UML');
    cy.getByTestId('Visibility').as('select');
    // Verify that default visibility is public
    cy.get('@select').should(($select) => {
      expect($select).to.contain('public');
    });
    // open select
    cy.get('@select').click();
    // Verify the content of the Select menu
    cy.get('.MuiPopover-root')
      .should('be.visible')
      .find('ul>li')
      .should(($li) => {
        expect($li).to.have.length(5);
        const values = $li.map((i, el) => Cypress.$(el).attr('data-value'));
        // call classes.get() to make this a plain array
        expect(values.get()).to.deep.eq([
          '', // None
          'http://www.eclipse.org/uml2/5.0.0/UML#//VisibilityKind/public',
          'http://www.eclipse.org/uml2/5.0.0/UML#//VisibilityKind/private',
          'http://www.eclipse.org/uml2/5.0.0/UML#//VisibilityKind/protected',
          'http://www.eclipse.org/uml2/5.0.0/UML#//VisibilityKind/package',
        ]);
      })
      // Choose package
      .eq(4)
      .click();
    // Give focus to other widget to validate Select change
    cy.getByTestId('Name').click().focused();
    // Verify Visibility is actually package
    cy.get('@select').should(($select) => {
      expect($select).to.contain('package');
    });
  });

  /**
   * Test validating the List widget
   */
  it('Test List description', () => {
    cy.getByTestId('DurationObservation').click();
    cy.activateDetailsTab('UML');
    cy.inDetailsCurrentTab()
      .find('tbody>tr')
      .should(($rows) => {
        expect($rows).to.have.length(1);
      });
  });

  /**
   * Test validating the LiteralBoolean custom widget
   */
  it('Test LiteralBoolean concept', () => {
    const retrieveValuesElement = () => {
      cy.get(':nth-child(1) > .MuiFormControl-root > .MuiFormControlLabel-root')
        .should('be.visible')
        .find('input')
        .as('true');
      cy.get(':nth-child(2) > .MuiFormControl-root > .MuiFormControlLabel-root')
        .should('be.visible')
        .find('input')
        .as('false');
    };

    // Select LiteralBoolean in Explorer
    cy.getByTestId('LiteralBoolean').eq(0).click();
    cy.activateDetailsTab('UML');
    retrieveValuesElement();
    // Verify that True is not checked and False is checked
    cy.get('@true').should(($c) => {
      expect($c).to.have.attr('name').equals('True');
      expect($c).to.have.attr('type').equals('checkbox');
      expect($c).not.checked;
    });
    cy.get('@false').should(($c) => {
      expect($c).to.have.attr('name').equals('False');
      expect($c).to.have.attr('type').equals('checkbox');
      expect($c).checked;
    });
    // check TRUE
    cy.get('@true').check().should('be.checked');
    // Verify that in Advanced tab, the value has been changed
    cy.activateDetailsTab('Advanced');
    cy.getByTestId('Value').should('be.visible').find('input').should('be.checked');
    // check value in Advanced tab, the value is now true
    cy.getByTestId('Value').find('input').check().should('be.checked');
    // back to UML tab
    cy.activateDetailsTab('UML');
    retrieveValuesElement();
    // Verify that True is checked and False is not checked
    cy.get('@true').should('be.checked');
    cy.get('@false').should('be.not.checked');
  });

  /**
   * Test validating the MemberEnd container
   */
  it('Test Member end group', () => {
    cy.getByTestId('Association').click();
    cy.activateDetailsTab('UML')
      // Verify that there are 3 groups in UML page (1 for properties and 2 memberEnds)
      .findByTestId('form') // TODO workaround to retrieve member end SC #2183
      .children()
      .eq(1)
      .children()
      .should(($groups) => {
        expect($groups).to.have.length(3);
      })
      // Retrieve the first MemberEnd
      .eq(1)
      .as('memberEnd');
    // Verify that there is a Name section inside MemberEnd group
    cy.get('@memberEnd').findByTestId('Name').should('be.visible').find('input').should('have.value', 'to');
    // Verify that there is a Navigable section inside MemberEnd group
    cy.get('@memberEnd').findByTestId('Navigable').should('be.visible');
    // Verify that there is a Aggregation section inside MemberEnd group
    cy.get('@memberEnd').findByTestId('Aggregation').should('be.visible');
    // Verify that there is a Multiplicity section inside MemberEnd group
    cy.get('@memberEnd').findByTestId('Multiplicity').should('be.visible');
  });

  /**
   * Test validating the Multiplicity control (with validation)
   */
  it('Test Multiplicity widget', () => {
    cy.getByTestId('Association').click();
    // Retrieve the first MemberEnd
    cy.activateDetailsTab('UML')
      .findByTestId('form') // TODO workaround to retrieve member end SC #2183
      .children()
      .eq(1)
      .children()
      .eq(1)
      .as('memberEnd');
    cy.get('@memberEnd').findByTestId('Multiplicity').should('be.visible').find('input').as('multiplicityInput');
    // Change multiplicity value with error
    cy.get('@multiplicityInput').should('have.value', '1').clear().type('WRONG MULTIPLICITY{enter}');
    // Verify error pops up if wrong entry
    cy.get('[role="alert"]').should('be.visible').contains("'WRONG MULTIPLICITY' is not a valid multiplicity value");
    // Close error popup
    cy.get('[role="alert"]').find('button').should('be.visible').click();
    // Change multiplicity value with valid one
    cy.get('@multiplicityInput').clear().type('0..1{enter}');
    // Verify the entered multiplicity
    cy.getByTestId('to').should('be.visible').click();
    cy.activateDetailsTab('UML')
      .findByTestId('Multiplicity')
      .eq(0)
      .should('be.visible')
      .find('input[name="Multiplicity"]')
      .should('have.value', '0..1');
    // cy.get('[role="alert"]').should('be.not.visible');
  });
});
