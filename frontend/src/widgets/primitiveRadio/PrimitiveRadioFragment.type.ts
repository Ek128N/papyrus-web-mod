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
 ***************************************************************************/

import { GQLMessage, GQLWidget } from '@eclipse-sirius/sirius-components-forms';

export interface GQLPrimitiveRadio extends GQLWidget {
  candidateList: Array<string> | null;
  candidateValue: string;
}

export interface GQLNewValueData {
  newValue: GQLNewValuePayload;
}

export interface GQLNewValueVariables {
  input: GQLNewValueInput;
}

export interface GQLNewValueInput {
  id: string;
  editingContextId: string;
  representationId: string;
  primitiveRadioId: string;
  value: string;
}

export interface GQLNewValuePayload {
  __typename: string;
}

export interface GQLSuccessPayload extends GQLNewValuePayload {
  messages: GQLMessage[];
}

export interface GQLErrorPayload extends GQLNewValuePayload {
  messages: GQLMessage[];
}
