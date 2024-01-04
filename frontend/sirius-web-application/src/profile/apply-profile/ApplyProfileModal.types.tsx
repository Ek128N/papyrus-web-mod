/*******************************************************************************
 * Copyright (c) 2022, 2024 CEA LIST, Obeo.
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

export interface ApplyProfileModalProps {
  editingContextId: string;
  item: any;
  onClose: () => void;
  onAppliedProfile: () => void;
}

export interface Profile {
  uriPath: string;
  label: string;
}

export interface GQLGetProfilesQueryVariables {
  editingContextId: string;
  kind: string;
}

export interface GQLGetProfilesQueryData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  profileMetadatas: GQLProfileMetadata[];
}

export interface GQLProfileMetadata {
  label: string;
  uriPath: string;
}

export interface GQLApplyProfileMutationData {
  applyProfile: GQLApplyProfilePayload;
}

export interface GQLApplyProfilePayload {
  __typename: string;
}

export interface GQLApplyProfileSuccessPayload extends GQLApplyProfilePayload {
  id: string;
}

export interface GQLObject {
  id: string;
  label: string;
  kind: string;
}

export interface GQLErrorPayload extends GQLApplyProfilePayload {
  message: string;
}
