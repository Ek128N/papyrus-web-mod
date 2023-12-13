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
export interface GQLGetMetaclassesQueryVariables {
  editingContextId: string;
}

export interface GQLGetMetaclassesQueryData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  editingContext: GQLEditingContext;
}

export interface GQLEditingContext {
  metaclassMetadatas: [GQLMetaclassMetadata];
}

export interface GQLMetaclassMetadata {
  id: string;
  name: string;
  imagePath: string;
}

export interface GQLCreateMetaclassImportData {
  createMetaclassImport: GQLCreateMetaclassImportPayload;
}

export interface GQLCreateMetaclassImportPayload {
  __typename: string;
}

export interface ErrorPayload extends GQLCreateMetaclassImportPayload {
  __typename: 'ErrorPayload';
  message: string;
}

export interface GQLCreateMetaclassImportVariables {
  input: GQLCreateMetaclassImportInput;
}

export interface GQLCreateMetaclassImportInput {
  id: string;
  editingContextId: string;
  representationId: string;
  diagramElementId: string;
  metaclassIds: string[];
  x: number;
  y: number;
}
