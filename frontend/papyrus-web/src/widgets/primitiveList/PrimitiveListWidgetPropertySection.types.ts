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
import { Selection } from '@eclipse-sirius/sirius-components-core';
import {
  GQLListItem,
  GQLListStyle,
  GQLMessage,
  GQLSubscriber,
  GQLWidget,
} from '@eclipse-sirius/sirius-components-forms';

export interface PrimitiveListStyleProps {
  color: string | null;
  fontSize: number | null;
  italic: boolean | null;
  bold: boolean | null;
  underline: boolean | null;
  strikeThrough: boolean | null;
}

export interface PrimitiveListPropertySectionProps {
  editingContextId: string;
  formId: string;
  widget: EditableGQLList;
  subscribers: GQLSubscriber[];
  readOnly: boolean;
  setSelection: (selection: Selection) => void;
}

export interface EditableGQLList extends GQLWidget {
  canAdd: boolean;
  canReorder: boolean;
  hasCandidates: boolean;
  items: GQLActionableListItem[];
  style: GQLListStyle;
}

export interface GQLActionableListItem extends GQLListItem {
  hasAction: Boolean;
  actionIconURL: string;
}

export interface GQLDeletePrimitiveListItemMutationData {
  deletePrimitiveListItem: GQLDeletePrimitiveListItemPayload;
}

export interface GQLAddPrimitiveListItemMutationData {
  addPrimitiveListItem: GQLDeletePrimitiveListItemPayload;
}

export interface GQLDeletePrimitiveListItemPayload {
  __typename: string;
}

export interface GQLAddPrimitiveListItemPayload {
  __typename: string;
}

export interface GQLErrorPayload extends GQLDeletePrimitiveListItemPayload {
  messages: GQLMessage[];
}

export interface GQLSuccessPayload extends GQLDeletePrimitiveListItemPayload {
  messages: GQLMessage[];
}

export interface GQLErrorPayload extends GQLAddPrimitiveListItemPayload {
  messages: GQLMessage[];
}

export interface GQLSuccessPayload extends GQLAddPrimitiveListItemPayload {
  messages: GQLMessage[];
}
export interface GQLReorderPrimitiveListItemsMutationData {
  reorderPrimitiveListItems: GQLReorderPrimitiveListItemsPayload;
}

export interface GQLReorderPrimitiveListItemsPayload {
  __typename: string;
}

export interface GQLReorderPrimitiveListItemsMutationVariables {
  input: GQLReorderPrimitiveListItemsInput;
}

export interface GQLReorderPrimitiveListItemsInput {
  id: string;
  editingContextId: string;
  representationId: string;
  listId: string;
  itemId: string;
  fromIndex: number;
  toIndex: number;
}

export interface GQLActionPrimitiveListItemMutationData {
  actionPrimitiveListItem: GQLActionPrimitiveListItemPayload;
}

export interface GQLActionPrimitiveListItemPayload {
  __typename: string;
}

export interface GQLActionPrimitiveListItemMutationVariables {
  input: GQLActionPrimitiveListItemInput;
}

export interface GQLActionPrimitiveListItemInput {
  id: string;
  editingContextId: string;
  representationId: string;
  listId: string;
  itemId: string;
}

export interface GQLPrimitiveListCandidate {
  value: string;
  label: string;
}

export interface PrimitiveListAutocompleteState {
  open: boolean;
  candidates: GQLPrimitiveListCandidate[] | null;
}

export interface GQLGetPrimitiveListCandidatesQueryData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  editingContext: GQLEditingContext;
}

export interface GQLEditingContext {
  representation: GQLRepresentationMetadata;
}

export interface GQLRepresentationMetadata {
  id: string;
  label: string;
  kind: string;
  description: GQLRepresentationDescription;
}

export interface GQLRepresentationDescription {
  id: string;
  __typename: string;
}

export interface GQLFormDescription extends GQLRepresentationDescription {
  primitiveListCandidates: GQLPrimitiveListCandidate[];
}

export interface GQLGetPrimitiveListCandidatesQueryVariables {
  editingContextId: string;
  representationId: string;
  primitiveListId: string;
}
