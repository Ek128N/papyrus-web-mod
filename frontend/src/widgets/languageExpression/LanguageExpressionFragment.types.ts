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

export interface GQLLanguageExpression extends GQLWidget {
  languages: Array<GQLLanguageElement> | null;
  predefinedLanguages: Array<string>;
}

export interface GQLLanguageElement {
  id: string;
  label: string;
  body: string;
}

export interface GQLAddLanguageData {
  addLanguage: GQLAddLanguagePayload;
}

export interface GQLAddLanguageVariables {
  input: GQLAddLanguageInput;
}

export interface GQLAddLanguageInput {
  id: string;
  editingContextId: string;
  representationId: string;
  languageExpressionId: string;
  language: string;
}

export interface GQLAddLanguagePayload {
  __typename: string;
}

export interface GQLSuccessPayload extends GQLAddLanguagePayload {
  messages: GQLMessage[];
}

export interface GQLErrorPayload extends GQLAddLanguagePayload {
  messages: GQLMessage[];
}

export interface GQLDeleteLanguageVariables {
  input: GQLDeleteLanguageInput;
}

export interface GQLDeleteLanguageInput {
  id: string;
  editingContextId: string;
  representationId: string;
  languageExpressionId: string;
  language: string;
}

export interface GQLDeleteLanguageData {
  deleteLanguage: GQLDeleteLanguagePayload;
}

export interface GQLDeleteLanguagePayload {
  __typename: string;
}

export interface GQLEditLanguageBodyVariables {
  input: GQLEditLanguageBodyInput;
}

export interface GQLEditLanguageBodyInput {
  id: string;
  editingContextId: string;
  representationId: string;
  languageExpressionId: string;
  language: string;
  newBody: string;
}

export interface GQLEditLanguageBodyData {
  editLanguageBody: GQLEditLanguageBodyPayload;
}

export interface GQLEditLanguageBodyPayload {
  __typename: string;
}

export interface GQLMoveLanguageVariables {
  input: GQLMoveLanguageInput;
}

export interface GQLMoveLanguageInput {
  id: string;
  editingContextId: string;
  representationId: string;
  languageExpressionId: string;
  language: string;
  direction: 'BACKWARD' | 'FORWARD';
}

export interface GQLMoveLanguageData {
  moveLanguage: GQLMoveLanguagePayload;
}

export interface GQLMoveLanguagePayload {
  __typename: string;
}
