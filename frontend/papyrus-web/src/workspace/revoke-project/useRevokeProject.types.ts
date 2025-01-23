/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     CEA LIST - initial API and implementation
 *******************************************************************************/

import { GQLMessage } from '@eclipse-sirius/sirius-components-core';

export interface UseRevokeProjectValue {
  revokeProject: (projectId: string, userName: string) => void;
  loading: boolean;
  projectRevoked: boolean;
}

export interface GQLRevokeProjectMutationData {
  revokeProject: GQLRevokeProjectPayload;
}

export interface GQLRevokeProjectPayload {
  __typename: string;
}

export interface GQLErrorPayload extends GQLRevokeProjectPayload {
  messages: GQLMessage[];
}

export interface GQLRevokeProjectMutationVariables {
  input: GQLRevokeProjectMutationInput;
}

export interface GQLRevokeProjectMutationInput {
  id: string;
  projectId: string;
  userName: string;
}
