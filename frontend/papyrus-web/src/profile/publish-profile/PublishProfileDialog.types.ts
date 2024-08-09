/*******************************************************************************
 * Copyright (c) 2023, 2024 CEA LIST, Obeo.
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
 *******************************************************************************/

import { GQLTreeItem } from '@eclipse-sirius/sirius-components-trees';

export interface PublishProfileDialogProps {
  editingContextId: string;
  item: GQLTreeItem;
  onClose: () => void;
}

export type NewVersionKind = 'Development' | 'Release' | 'Major' | 'Custom';

export interface GQLGetProfileLastVersionQueryVariables {
  editingContextId: string;
  profileId: string;
}

export interface GQLGetProfileLastVersionQueryData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  editingContext: GQLEditingContext;
}

export interface GQLEditingContext {
  profileLastVersion: GQLProfileLastVersion;
}

export interface GQLProfileLastVersion {
  major: string;
  minor: string;
  micro: string;
}
export interface PublishProfileDialogState {
  customVersion: string;
  selectedNewVersionKind: NewVersionKind;
  date: string;
  author: string;
  comments: string;
  copyright: string;
  message: string | null;
}

export interface PublishProfileVariables {
  input: PublishProfileInput;
}

export interface PublishProfileInput {
  id: string;
  editingContextId: string;
  objectId: string;
  version: String;
  comment: String;
  copyright: String;
  author: String;
  date: string;
  saveOCLConstraint: Boolean;
}

export interface PublishProfileData {
  publishProfile: PublishProfilePayload;
}

export interface PublishProfilePayload {
  __typename: string;
}

export interface ErrorPayload extends PublishProfilePayload {
  __typename: 'ErrorPayload';
  message: string;
}
