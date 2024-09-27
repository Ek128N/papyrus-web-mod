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
import { DocumentNode, FragmentDefinitionNode, InlineFragmentNode, Kind, SelectionNode, visit } from 'graphql';

const shouldTransform = (document: DocumentNode) => {
  return document.definitions.some(
    (definition) =>
      definition.kind === Kind.OPERATION_DEFINITION &&
      (definition.name?.value === 'detailsEvent' ||
        definition.name?.value === 'formEvent' ||
        definition.name?.value === 'formDescriptionEditorEvent')
  );
};

const isWidgetFragmentDefinition = (node: FragmentDefinitionNode) => {
  return node.name.value === 'widgetFields';
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
      FragmentDefinition(node) {
        if (!isWidgetFragmentDefinition(node)) {
          return undefined;
        }

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
          ...node,
          selectionSet: {
            ...node.selectionSet,
            selections: [
              ...node.selectionSet.selections,
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
