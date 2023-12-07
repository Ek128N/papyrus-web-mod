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

describe('Stereotype application page tests', () => {
  /**
   * For each test, we start with a fresh new project containing all concepts gathered in one single model
   */

  var projectId;
  before(() => {
    cy.deleteAllProjects();
    // first load and publish the profile
    cy.createProject('Cypress Project profile').then((res) => {
      const projectId = res.body.data.createProject.project.id;
      cy.wrap(projectId).as('projectId');
      cy.visit(`/projects/${projectId}/edit`).then((res) => {
        cy.getByTestId('upload-document-icon').click();
        cy.fixture('DynamicProfileTypeTests.profile.uml', { mimeType: 'text/xml' }).as('DynamicProfileTypeTests');
        cy.getByTestId('file')
          .selectFile(
            {
              contents: '@DynamicProfileTypeTests',
              fileName: 'DynamicProfileTypeTests.profile.uml', // workaround for selectFile issue https://github.com/cypress-io/cypress/issues/21936
            },
            { force: true }
          )
          .then(() => {
            cy.getByTestId('upload-document-submit').click();
            cy.getByTestId('DynamicProfileTypeTests.profile.uml-more').should('exist').click();
            cy.getByTestId('expand-all').should('be.visible').click();
            cy.getByTestId('DynamicProfileTypeTests-more').first().click();
            cy.getByTestId('publish-profile').should('be.visible').click();
            cy.getByTestId('publish-profile-dialog')
              .findByTestId('publish-profile-author')
              .find('input')
              .type('Jerome');
            cy.getByTestId('publish-profile-publish')
              .should('not.have.class', 'Mui-disabled')
              .should('be.visible')
              .click();
          });
      });

      loadProject();
      applyProfileAndStereotypes();
    });
  });

  //Before each reload the project and select  Class item and dispay Stereotype 1 tab
  beforeEach(() => {
    cy.wrap(projectId).as('projectId');
    cy.visit(`/projects/${projectId}/edit`);
    cy.getByTestId('model4test.uml-more').should('be.visible').click();
    cy.getByTestId('expand-all').should('be.visible').click();
    cy.getByTestId('Class').click();
    cy.activateDetailsTab('Stereotype1');
  });

  it('Check mono string', () => {
    cy.getByTestId('input-testString').clear().type('new string{enter}');

    // Check persisted value
    refreshView();
    cy.getByTestId('input-testString').contains('new string');
  });

  it('Check mono integer', () => {
    // mono Integer => testInt
    cy.getByTestId('input-testInt').clear().type('wrong value{enter}');
    // check that an alert pops up
    cy.get('[role="alert"]')
      .should('be.visible')
      .contains("'wrong value' is not a valid value for feature 'testInt' expecting an integer number");
    // close alert, clear and wait until the alert goes away
    cy.get('[role="alert"]').find('button').should('be.visible').click();
    cy.getByTestId('input-testInt').clear();
    cy.get('[role="alert"]').should('not.exist');
    // replace with a valid integer
    cy.getByTestId('input-testInt').type('1{enter}');
    cy.get('[role="alert"]').should('not.exist');

    // Check persisted value
    refreshView();
    cy.getByTestId('input-testInt').should('have.value', '1');
  });

  it('Check mono Real', () => {
    // mono Double => testReal
    cy.getByTestId('input-testReal').clear().type('wrong value{enter}');
    // check that an alert pops up
    cy.get('[role="alert"]')
      .should('be.visible')
      .contains("'wrong value' is not a valid value for feature 'testReal' expecting a double number");
    // close alert, clear and wait until the alert goes away
    cy.getByTestId('input-testReal').clear();
    cy.get('[role="alert"]').should('not.exist');
    // replace with a valid real number
    cy.getByTestId('input-testReal').type('-0.9999{enter}');
    cy.get('[role="alert"]').should('not.exist');

    // Check persisted value
    refreshView();
    cy.getByTestId('input-testReal').should('have.value', '-0.9999');
  });

  it('Check mono boolean', () => {
    // mono boolean => testBoolean
    cy.getByTestId('testBoolean').should('not.have.class', 'Mui-checked');
    cy.getByTestId('testBoolean').click();

    // mono boolean object => testBooleanObject
    cy.getByTestId('primitive-radio-widget').contains('testBooleanObject').click();
    checkPrimitiveRadioState('true', false);
    checkPrimitiveRadioState('false', false);
    checkPrimitiveRadioState('null', true);
    cy.getByTestId('primitive-radio-true').click();

    // Check persisted value
    refreshView();
    cy.getByTestId('testBoolean').should('have.class', 'Mui-checked');

    checkPrimitiveRadioState('true', true);
    checkPrimitiveRadioState('false', false);
    checkPrimitiveRadioState('null', false);
  });

  it('Check mono enumeration', () => {
    // mono enumeration => testRefToEnumeration
    cy.getByTestId('testRefToEnumeration').contains('None');
    selectEnumerationValue('testRefToEnumeration', 'EnumerationLiteral2');

    // Check persisted value
    refreshView();
    cy.getByTestId('testRefToEnumeration').contains('EnumerationLiteral2');
  });

  it('Check multi String', () => {
    // multi String => testMultiString
    checkPrimitiveListIsEmpty('testMultiString');
    addItemInPrimitiveListFreeMode('testMultiString', 'first value');
    addItemInPrimitiveListFreeMode('testMultiString', 'second value');
    addItemInPrimitiveListFreeMode('testMultiString', 'third value');

    // Check persisted value
    refreshView();
    checkPrimitiveListContent('testMultiString', ['first value', 'second value', 'third value']);
  });

  it('Check multi Integer', () => {
    // multi Integer => testMultiInt
    checkPrimitiveListIsEmpty('testMultiInt');
    addItemInPrimitiveListFreeMode('testMultiInt', '-1');
    addItemInPrimitiveListFreeMode('testMultiInt', '99');

    // Check persisted value
    refreshView();
    checkPrimitiveListContent('testMultiInt', ['-1', '99']);
  });

  it('Check multi Real', () => {
    // multi Double => testMultiReal
    checkPrimitiveListIsEmpty('testMultiReal');
    addItemInPrimitiveListFreeMode('testMultiReal', '-0.1');
    addItemInPrimitiveListFreeMode('testMultiReal', '3.14159265');
    addItemInPrimitiveListFreeMode('testMultiReal', '2.718281828459045');

    // Check persisted value
    refreshView();
    checkPrimitiveListContent('testMultiReal', ['-0.1', '3.14159265', '2.718281828459045']);
  });

  it('Check multi Boolean', () => {
    // multi boolean => testMultiBoolean
    checkPrimitiveListIsEmpty('testMultiBoolean');
    addItemInPrimitiveListStrictMode('testMultiBoolean', 'false');
    addItemInPrimitiveListStrictMode('testMultiBoolean', 'true');
    addItemInPrimitiveListStrictMode('testMultiBoolean', 'true');

    // Check persisted value
    refreshView();
    checkPrimitiveListContent('testMultiBoolean', ['false', 'true', 'true']);
  });

  it('Check multi Enumeration', () => {
    // multi enumeration => testMultiEnumeration
    checkPrimitiveListIsEmpty('testMultiEnumeration');
    addItemInPrimitiveListStrictMode('testMultiEnumeration', 'EnumerationLiteral2');
    addItemInPrimitiveListStrictMode('testMultiEnumeration', 'EnumerationLiteral3');
    addItemInPrimitiveListStrictMode('testMultiEnumeration', 'EnumerationLiteral1');

    // Check persisted value
    refreshView();
    checkPrimitiveListContent('testMultiEnumeration', [
      'EnumerationLiteral2',
      'EnumerationLiteral3',
      'EnumerationLiteral1',
    ]);
  });

  it('Check mono reference to metaclass', () => {
    // mono ref to Metaclass => testMonoRefToMetaclass
    checkReferenceValues('testMonoRefToMetaclass', []);
    checkReferenceDropdownContent('testMonoRefToMetaclass', ['Activity', 'Class', 'FunctionBehavior']);
    // set reference value using dropdown
    selectReferenceDropdownValue('testMonoRefToMetaclass', 'FunctionBehavior');
    checkReferenceValues('testMonoRefToMetaclass', ['FunctionBehavior']);
    checkReferenceDropdownContent('testMonoRefToMetaclass', ['Activity', 'Class']);
    // remove FunctionBehavior
    cy.getByTestId('reference-value-FunctionBehavior').find('.MuiChip-deleteIcon').click();
    checkReferenceValues('testMonoRefToMetaclass', []);
    // set reference value using dialog
    cy.getByTestId('testMonoRefToMetaclass-more').click();
    cy.getByTestId('browse-modal').should('be.visible').as('dialog');
    cy.get('@dialog').findByTestId('tree-root-elements').findByTestId('model4test-toggle').should('be.visible').click();
    cy.get('@dialog').findByTestId('tree-root-elements').findByTestId('Class').should('be.visible').click();
    cy.get('@dialog')
      .findByTestId('tree-root-elements')
      .findByTestId('Class')
      .parent()
      .parent()
      .should('have.attr', 'data-testid', 'selected');
    cy.get('@dialog').findByTestId('select-value').click();
    checkReferenceValues('testMonoRefToMetaclass', ['Class']);
    // clear the reference list
    cy.getByTestId('testMonoRefToMetaclass-clear').click();
    checkReferenceValues('testMonoRefToMetaclass', []);
    // create new instance of Class under Package
    createReferenceNewObject('testMonoRefToMetaclass', 'Package', 'packagedElement::Class');
    cy.checkChildren('Package', ['Class1']);

    // Check persisted value
    refreshView();
    checkReferenceValues('testMonoRefToMetaclass', ['Class1']);
  });

  it('Check multi reference to metaclass', () => {
    // multi ref to Metaclass => testMultiRefToMetaclass
    checkReferenceValues('testMultiRefToMetaclass', []);
    checkReferenceDropdownContent('testMultiRefToMetaclass', ['UseCase1', 'UseCase2']);
    selectReferenceDropdownValue('testMultiRefToMetaclass', 'UseCase1');
    checkReferenceValues('testMultiRefToMetaclass', ['UseCase1']);
    // add another value using the transfer modal
    cy.getByTestId('testMultiRefToMetaclass-more').click();
    cy.getByTestId('transfer-modal').should('be.visible').as('dialog');
    checkReferenceTransferModalRightContent(['UseCase1']);
    cy.get('@dialog').findByTestId('tree-root-elements').findByTestId('model4test-toggle').should('be.visible').click();
    cy.get('@dialog').findByTestId('tree-root-elements').findByTestId('UseCase2').should('be.visible').click();
    cy.get('@dialog')
      .findByTestId('tree-root-elements')
      .findByTestId('UseCase2')
      .parent()
      .parent()
      .should('have.attr', 'data-testid', 'selected');
    cy.get('@dialog').findByTestId('move-right').click();
    checkReferenceTransferModalRightContent(['UseCase1', 'UseCase2']);
    cy.get('@dialog').findByTestId('close-transfer-modal').click();
    checkReferenceValues('testMultiRefToMetaclass', ['UseCase1', 'UseCase2']);
    // create new UseCase under model
    createReferenceNewObject('testMultiRefToMetaclass', 'model4test', 'packagedElement::UseCase');

    // Check persisted value
    refreshView();

    checkReferenceValues('testMultiRefToMetaclass', ['UseCase1', 'UseCase2', 'UseCase3']);
  });

  const loadProject = () => {
    cy.createProject('Cypress Project').then((res) => {
      projectId = res.body.data.createProject.project.id;
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
          });
      });
    });
  };

  const applyProfileAndStereotypes = () => {
    cy.getByTestId('model4test-more').should('be.visible').click();
    cy.getByTestId('apply-profile').should('be.visible').click();
    cy.get('[aria-labelledby="applyProfileModalProfileLabel"]').should('not.have.class', 'Mui-disabled').click();
    cy.get('#menu-').find('ul').children().contains('DynamicProfileTypeTests').first().click();
    cy.getByTestId('apply-profile-submit').should('exist').click();
    // Apply stereotype2 on Activity
    applyStereotypeOn('Stereotype2', 'Activity');
    // Apply stereotype1 on Class
    applyStereotypeOn('Stereotype1', 'Class');
    // check Stereotype1 page is present
    cy.activateDetailsTab('Stereotype1');
  };

  const refreshView = () => {
    cy.getByTestId('Activity').click();
    cy.getByTestId('Class').click();
    cy.activateDetailsTab('Stereotype1');
  };

  const applyStereotypeOn = (stereotype, element) => {
    cy.getByTestId(`${element}-more`).should('be.visible').click();
    cy.getByTestId('apply-stereotype').should('be.visible').click();
    cy.getByTestId('stereotype').should('not.have.class', 'Mui-disabled').click();
    cy.get('#menu-')
      .find(`ul[aria-labelledby="newDocumentModalStereotypeDescriptionLabel"]`)
      .findByTestId(`DynamicProfileTypeTests::${stereotype}`)
      .click();
    cy.getByTestId('apply-stereotype-submit').click();
  };

  const checkPrimitiveRadioState = (value, isChecked) => {
    cy.getByTestId(`primitive-radio-${value}`)
      .children()
      .first()
      .should(`${isChecked ? '' : 'not.'}have.class`, 'Mui-checked');
  };

  const checkPrimitiveListIsEmpty = (primitiveListName) => {
    cy.getByTestId(`primitive-list-table-${primitiveListName}`).children().as('pl-items').should('have.length', 2);
    cy.get('@pl-items').first().contains('None');
  };

  const addItemInPrimitiveListFreeMode = (primitiveListName, itemValue) => {
    cy.getByTestId(`primitive-list-add-section-${primitiveListName}`).find('input').clear().type(itemValue);
    cy.getByTestId(`primitive-list-add-${primitiveListName}`).click();
    cy.getByTestId(`primitive-list-table-${primitiveListName}`)
      .findByTestId(`primitive-list-item-${itemValue}`)
      .should('exist');
  };

  const addItemInPrimitiveListStrictMode = (primitiveListName, itemValue) => {
    cy.getByTestId(`primitive-list-autocomplete-textfield-${primitiveListName}`).click();
    cy.get(`.MuiAutocomplete-popper`)
      .should('be.visible')
      .find(`ul > li`)
      .contains(itemValue)
      .should('be.visible')
      .click();
    cy.getByTestId(`primitive-list-add-${primitiveListName}`).should('not.have.class', 'Mui-disabled').click();
    cy.getByTestId(`primitive-list-table-${primitiveListName}`)
      .findByTestId(`primitive-list-item-${itemValue}`)
      .should('exist');
  };

  const checkPrimitiveListContent = (primitiveListName, values) => {
    cy.getByTestId(`primitive-list-table-${primitiveListName}`)
      .children()
      .should('have.length', values.length + 1);
    values.forEach((val) => {
      cy.getByTestId(`primitive-list-item-${val}`).should('exist');
    });
  };

  const selectEnumerationValue = (selectName, value) => {
    cy.getByTestId(selectName).click();
    cy.get('#menu-').should('be.visible').find(`ul > li[data-value="${value}"]`).click();
  };

  const checkReferenceDropdownContent = (refName, content) => {
    cy.getByTestId(refName).find('.MuiAutocomplete-endAdornment').find('button').should('exist').click();
    cy.get('.MuiAutocomplete-popper')
      .find('ul')
      .should('exist')
      .children()
      .should(($candidates) => {
        const optionTexts = $candidates.toArray().map((el) => el.innerText);
        expect(optionTexts).to.deep.eq(content);
      });
    // close the dropdown
    cy.getByTestId(refName).find('.MuiAutocomplete-endAdornment').find('button').should('be.visible').click();
  };

  const selectReferenceDropdownValue = (refName, value) => {
    cy.getByTestId(refName).find('.MuiAutocomplete-endAdornment').find('button').should('exist').click();
    cy.get('.MuiAutocomplete-popper')
      .find('ul')
      .should('exist')
      .children()
      .contains(value)
      .should('be.visible')
      .click();
  };

  const checkReferenceValues = (refName, values) => {
    cy.getByTestId(refName)
      .find('.MuiChip-root')
      .should(($chips) => {
        const optionTexts = $chips
          .toArray()
          .map((el) => el.getAttribute('data-testid').substring('reference-value-'.length));
        expect(optionTexts).to.deep.eq(values);
      });
  };

  const chooseSelectValue = (selectTestId, value) => {
    cy.getByTestId(selectTestId).should('not.have.class', 'Mui-disabled').click();
    cy.get('#menu-').find(`li[data-value="${value}"]`).should('be.visible').click();
  };

  const checkReferenceTransferModalRightContent = (values) => {
    cy.getByTestId('transfer-modal')
      .should('be.visible')
      .findByTestId('selected-items-list')
      .find('li')
      .should(($lis) => {
        const optionTexts = $lis.toArray().map((el) => el.getAttribute('data-testid'));
        expect(optionTexts).to.deep.eq(values);
      });
  };

  const createReferenceNewObject = (refName, containerName, childDescription) => {
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
    chooseSelectValue('childCreationDescription', childDescription);
    cy.getByTestId('create-object').click();
    cy.getByTestId('create-modal').should('not.exist');
  };
});
