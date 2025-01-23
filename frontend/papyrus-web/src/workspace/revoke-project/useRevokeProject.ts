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

import { gql, useMutation } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useEffect } from 'react';
import {
  GQLErrorPayload,
  GQLRevokeProjectMutationData,
  GQLRevokeProjectMutationVariables,
  GQLRevokeProjectPayload,
  UseRevokeProjectValue,
} from './useRevokeProject.types';

const revokeProjectMutation = gql`
  mutation revokeProject($input: RevokeProjectInput!) {
    revokeProject(input: $input) {
      __typename
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const isErrorPayload = (payload: GQLRevokeProjectPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const useRevokeProject = (): UseRevokeProjectValue => {
  const [performProjectRevoke, { loading, data, error }] = useMutation<
    GQLRevokeProjectMutationData,
    GQLRevokeProjectMutationVariables
  >(revokeProjectMutation);

  const { addErrorMessage, addMessages } = useMultiToast();
  useEffect(() => {
    if (error) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
    if (data) {
      const { revokeProject } = data;
      if (isErrorPayload(revokeProject)) {
        addMessages(revokeProject.messages);
      }
    }
  }, [data, error]);

  const revokeProject = (projectId: string, userName: string) => {
    const variables: GQLRevokeProjectMutationVariables = {
      input: {
        id: crypto.randomUUID(),
        projectId,
        userName,
      },
    };
    performProjectRevoke({ variables });
  };

  const projectRevoked: boolean = data?.revokeProject.__typename === 'RevokeProjectSuccessPayload';

  return {
    revokeProject,
    loading,
    projectRevoked,
  };
};
