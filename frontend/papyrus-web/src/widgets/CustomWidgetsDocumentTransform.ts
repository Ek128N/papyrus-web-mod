/*******************************************************************************
 * Copyright (c) 2024 CEA LIST, Obeo.
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

import { DocumentTransform } from '@apollo/client';
import { DocumentNode, FieldNode, InlineFragmentNode, Kind, SelectionNode, visit, FragmentSpreadNode } from 'graphql';

const shouldTransform = (document: DocumentNode) => {
  return (
    document.definitions[0] &&
    document.definitions[0].kind === Kind.OPERATION_DEFINITION &&
    (document.definitions[0].name?.value === 'detailsEvent' || document.definitions[0].name?.value === 'formEvent')
  );
};

const isWidgetFragment = (field: FieldNode) => {
  if (field.name.value === 'widgets' || field.name.value === 'children') {
    const fragmentSpreads = field.selectionSet.selections
      .filter((selection) => selection.kind === Kind.FRAGMENT_SPREAD)
      .map((fragmentSpread: FragmentSpreadNode) => fragmentSpread.name.value);
    if (fragmentSpreads.includes('widgetFields')) {
      return true;
    }
  }
  return false;
};

const fieldBuilder = (label: string): SelectionNode => {
  return {
    kind: Kind.FIELD,
    name: {
      kind: Kind.NAME,
      value: label,
    },
  };
};

const structuredFieldBuilder = (label: string, children: SelectionNode[]): SelectionNode => {
  return {
    kind: Kind.FIELD,
    name: {
      kind: Kind.NAME,
      value: label,
    },
    selectionSet: {
      kind: Kind.SELECTION_SET,
      selections: children,
    },
  };
};

const styleField: SelectionNode = structuredFieldBuilder('style', [
  fieldBuilder('color'),
  fieldBuilder('fontSize'),
  fieldBuilder('italic'),
  fieldBuilder('bold'),
  fieldBuilder('underline'),
  fieldBuilder('strikeThrough'),
]);

export const customWidgetsDocumentTransform = new DocumentTransform((document) => {
  if (shouldTransform(document)) {
    return visit(document, {
      Field(field) {
        if (!isWidgetFragment(field)) {
          return undefined;
        }
        const selections = field.selectionSet?.selections ?? [];

        const referenceWidgetInlineFragment: InlineFragmentNode = {
          kind: Kind.INLINE_FRAGMENT,
          selectionSet: {
            kind: Kind.SELECTION_SET,
            selections: [
              fieldBuilder('label'),
              fieldBuilder('iconURL'),
              fieldBuilder('ownerId'),
              fieldBuilder('descriptionId'),
              fieldBuilder('hasHelpText'),
              fieldBuilder('readOnly'),
              structuredFieldBuilder('reference', [
                fieldBuilder('ownerKind'),
                fieldBuilder('referenceKind'),
                fieldBuilder('containment'),
                fieldBuilder('manyValued'),
              ]),
              structuredFieldBuilder('referenceValues', [
                fieldBuilder('id'),
                fieldBuilder('label'),
                fieldBuilder('kind'),
                fieldBuilder('iconURL'),
              ]),
              styleField,
            ],
          },
          typeCondition: {
            kind: Kind.NAMED_TYPE,
            name: {
              kind: Kind.NAME,
              value: 'ReferenceWidget',
            },
          },
        };

        const primitiveListInlineFragment: InlineFragmentNode = {
          kind: Kind.INLINE_FRAGMENT,
          selectionSet: {
            kind: Kind.SELECTION_SET,
            selections: [
              fieldBuilder('label'),
              fieldBuilder('iconURL'),
              fieldBuilder('canAdd'),
              fieldBuilder('canReorder'),
              fieldBuilder('hasCandidates'),
              structuredFieldBuilder('items', [
                fieldBuilder('id'),
                fieldBuilder('label'),
                fieldBuilder('iconURL'),
                fieldBuilder('deletable'),
                fieldBuilder('hasAction'),
                fieldBuilder('actionIconURL'),
              ]),
              styleField,
            ],
          },
          typeCondition: {
            kind: Kind.NAMED_TYPE,
            name: {
              kind: Kind.NAME,
              value: 'PrimitiveListWidget',
            },
          },
        };

        const containmentReferenceWidgetInlineFragment: InlineFragmentNode = {
          kind: Kind.INLINE_FRAGMENT,
          selectionSet: {
            kind: Kind.SELECTION_SET,
            selections: [
              fieldBuilder('label'),
              fieldBuilder('iconURL'),
              fieldBuilder('ownerId'),
              fieldBuilder('descriptionId'),
              structuredFieldBuilder('containmentReference', [
                fieldBuilder('ownerKind'),
                fieldBuilder('referenceKind'),
                fieldBuilder('isMany'),
                fieldBuilder('canMove'),
              ]),
              structuredFieldBuilder('referenceValues', [
                fieldBuilder('id'),
                fieldBuilder('label'),
                fieldBuilder('kind'),
                fieldBuilder('iconURL'),
              ]),
              styleField,
            ],
          },
          typeCondition: {
            kind: Kind.NAMED_TYPE,
            name: {
              kind: Kind.NAME,
              value: 'ContainmentReferenceWidget',
            },
          },
        };

        const languageExpressionInlineFragment: InlineFragmentNode = {
          kind: Kind.INLINE_FRAGMENT,
          selectionSet: {
            kind: Kind.SELECTION_SET,
            selections: [
              fieldBuilder('id'),
              fieldBuilder('label'),
              fieldBuilder('iconURL'),
              structuredFieldBuilder('languages', [fieldBuilder('id'), fieldBuilder('label'), fieldBuilder('body')]),
              fieldBuilder('predefinedLanguages'),
            ],
          },
          typeCondition: {
            kind: Kind.NAMED_TYPE,
            name: {
              kind: Kind.NAME,
              value: 'LanguageExpression',
            },
          },
        };

        const primitiveRadioInlineFragment: InlineFragmentNode = {
          kind: Kind.INLINE_FRAGMENT,
          selectionSet: {
            kind: Kind.SELECTION_SET,
            selections: [
              fieldBuilder('id'),
              fieldBuilder('label'),
              fieldBuilder('iconURL'),
              fieldBuilder('candidateList'),
              fieldBuilder('candidateValue'),
            ],
          },
          typeCondition: {
            kind: Kind.NAMED_TYPE,
            name: {
              kind: Kind.NAME,
              value: 'PrimitiveRadio',
            },
          },
        };

        return {
          ...field,
          selectionSet: {
            ...field.selectionSet,
            selections: [
              ...selections,
              containmentReferenceWidgetInlineFragment,
              primitiveListInlineFragment,
              languageExpressionInlineFragment,
              referenceWidgetInlineFragment,
              primitiveRadioInlineFragment,
            ],
          },
        };
      },
    });
  }
  return document;
});
