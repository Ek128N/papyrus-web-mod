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
  GQLShareProjectMutationData,
  GQLShareProjectMutationVariables,
  GQLShareProjectPayload,
  UseShareProjectValue,
} from './useShareProject.types';

const shareProjectMutation = gql`
  mutation shareProject($input: ShareProjectInput!) {
    shareProject(input: $input) {
      __typename
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const isErrorPayload = (payload: GQLShareProjectPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const useShareProject = (): UseShareProjectValue => {
  const [performProjectShare, { loading, data, error }] = useMutation<
    GQLShareProjectMutationData,
    GQLShareProjectMutationVariables
  >(shareProjectMutation);

  const { addErrorMessage, addMessages } = useMultiToast();
  useEffect(() => {
    if (error) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
    if (data) {
      const { shareProject } = data;
      if (isErrorPayload(shareProject)) {
        addMessages(shareProject.messages);
      }
    }
  }, [data, error]);

  const shareProject = (projectId: string, userName: string) => {
    const variables: GQLShareProjectMutationVariables = {
      input: {
        id: crypto.randomUUID(),
        projectId,
        userName,
      },
    };
    performProjectShare({ variables });
  };

  const projectShared: boolean = data?.shareProject.__typename === 'ShareProjectSuccessPayload';

  return {
    shareProject,
    loading,
    projectShared,
  };
};
