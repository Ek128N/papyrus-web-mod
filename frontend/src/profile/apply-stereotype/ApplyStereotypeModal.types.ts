/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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

export interface GQLGetStereotypesQueryVariables {
  editingContextId: string;
  elementId: string;
}

export interface GQLGetStereotypesQueryData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  editingContext: GQLEditingContext;
}

export interface GQLEditingContext {
  stereotypeMetatadas: GQLStereotypeMetadata[];
}

export interface GQLStereotypeMetadata {
  label: string;
  id: string;
}

export interface ApplyStereotypeModalProps {
  editingContextId: string;
  item: any;
  onAppliedStereotype: () => void;
  onClose: () => void;
}

export interface GQLApplyStereotypeMutationData {
  applyStereotype: GQLApplyStereotypePayload;
}

export interface GQLApplyStereotypePayload {
  __typename: string;
}

export interface GQLApplyStereotypeSuccessPayload extends GQLApplyStereotypePayload {
  id: string;
}

export interface GQLErrorPayload extends GQLApplyStereotypePayload {
  message: string;
}
