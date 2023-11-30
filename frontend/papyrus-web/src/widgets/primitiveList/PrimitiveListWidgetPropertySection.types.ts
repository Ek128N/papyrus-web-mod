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
import { GQLList, GQLMessage, GQLSubscriber } from '@eclipse-sirius/sirius-components-forms';

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

export interface EditableGQLList extends GQLList {
  canAdd: boolean;
  candidates: string[] | undefined;
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
