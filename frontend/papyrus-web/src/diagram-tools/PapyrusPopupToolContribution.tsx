/*****************************************************************************
 * Copyright (c) 2023, 2025 CEA LIST, Obeo.
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
import { gql, useMutation, useQuery } from '@apollo/client';
import { ServerContext, ServerContextValue, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import {
  DiagramContext,
  DiagramContextValue,
  DiagramPaletteToolContributionComponentProps,
  NodeData,
} from '@eclipse-sirius/sirius-components-diagrams';
import { useCurrentProject } from '@eclipse-sirius/sirius-web-application';
import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import IconButton from '@mui/material/IconButton';
import { Node, useNodes } from '@xyflow/react';
import { Fragment, useContext, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { EditProjectViewParams, GQLGetProjectQueryData, GQLGetProjectQueryVariables } from './EditProjectView.types';
import {
  ErrorPayload,
  GQLCreateMetaclassImportData,
  GQLCreateMetaclassImportVariables,
  GQLGetMetaclassesQueryData,
  GQLGetMetaclassesQueryVariables,
} from './PapyrusPopupToolContribution.types';
import { TransferModal } from './TransferModal';

type Modal = 'dialog';

const getProjectQuery = gql`
  query getRepresentation($projectId: ID!, $representationId: ID!, $includeRepresentation: Boolean!) {
    viewer {
      project(projectId: $projectId) {
        id
        name
        currentEditingContext {
          id
          representation(representationId: $representationId) @include(if: $includeRepresentation) {
            id
            label
            kind
            isProfileDiagram
          }
        }
      }
    }
  }
`;

const getMetaclassMetadatas = gql`
  query getMetaclassMetadatas($editingContextId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        metaclassMetadatas {
          id
          name
          imagePath
        }
      }
    }
  }
`;

const createMetaclassImportMutation = gql`
  mutation createMetaclassImport($input: CreateMetaclassImportInput!) {
    createMetaclassImport(input: $input) {
      __typename
      ... on ErrorPayload {
        message
      }
    }
  }
`;

export const PapyrusPopupToolContribution = ({
  diagramElementId,
  x,
  y,
}: DiagramPaletteToolContributionComponentProps) => {
  const [modal, setModal] = useState<Modal | null>(null);
  const { addErrorMessage } = useMultiToast();
  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);

  const onClose = (selectedElementIds: string[]) => {
    setModal(null);
    const variables: GQLCreateMetaclassImportVariables = {
      input: {
        id: crypto.randomUUID(),
        editingContextId,
        representationId,
        diagramElementId,
        x,
        y,
        metaclassIds: selectedElementIds,
      },
    };
    createMetaclassImport({ variables });
  };

  const { projectId, representationId } = useParams<EditProjectViewParams>();
  const { project } = useCurrentProject();
  const editingContextId = project.currentEditingContext.id;

  const [isProfileDiagram, setIsProfileDIagram] = useState(false);
  const [metaclasses, setMetaclasses] = useState([{ id: '0', name: 'Loading...', imagePath: '' }]);

  const {
    loading: loadingRepresentationQuery,
    data: dataRepresentationQuery,
    error: errorRepresentationQuery,
  } = useQuery<GQLGetProjectQueryData, GQLGetProjectQueryVariables>(getProjectQuery, {
    variables: {
      projectId,
      representationId: representationId ?? '',
      includeRepresentation: !!representationId,
    },
  });
  useEffect(() => {
    if (!loadingRepresentationQuery) {
      if (errorRepresentationQuery) {
        addErrorMessage(errorRepresentationQuery.message);
      }
      if (dataRepresentationQuery) {
        setIsProfileDIagram(
          dataRepresentationQuery?.viewer?.project?.currentEditingContext?.representation?.isProfileDiagram
        );
      }
    }
  }, [loadingRepresentationQuery, dataRepresentationQuery, errorRepresentationQuery]);

  const {
    loading: loadingQuery,
    error: errorQuery,
    data: dataQuery,
  } = useQuery<GQLGetMetaclassesQueryData, GQLGetMetaclassesQueryVariables>(getMetaclassMetadatas, {
    variables: { editingContextId },
  });

  useEffect(() => {
    if (dataQuery?.viewer?.editingContext?.metaclassMetadatas) {
      setMetaclasses(dataQuery.viewer.editingContext.metaclassMetadatas);
    }
    if (!loadingQuery) {
      if (errorQuery) {
        addErrorMessage(errorQuery.message);
      } else if (!dataQuery?.viewer?.editingContext?.metaclassMetadatas) {
        addErrorMessage('Failed to retrieve MetaClass metadatas');
      }
    }
  }, [loadingQuery, dataQuery, errorQuery]);

  const [createMetaclassImport, { data, error }] = useMutation<
    GQLCreateMetaclassImportData,
    GQLCreateMetaclassImportVariables
  >(createMetaclassImportMutation);
  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
    if (data && data.createMetaclassImport.__typename === 'ErrorPayload') {
      const errorPayload = data.createMetaclassImport as ErrorPayload;
      addErrorMessage(errorPayload.message);
    }
  }, [data, error, onClose]);

  let modalElement: JSX.Element | null = null;
  if (modal === 'dialog') {
    modalElement = (
      <>
        <Dialog open={true} onClose={onClose} onClick={(event) => event.stopPropagation()} fullWidth>
          <DialogContent>
            <TransferModal editingContextId={editingContextId} items={metaclasses} onClose={onClose} />
          </DialogContent>
        </Dialog>
      </>
    );
  }

  const nodes = useNodes<Node<NodeData>>();

  const { diagramId } = useContext<DiagramContextValue>(DiagramContext);

  const result = (
    <Fragment key="import-metaclass-modal-contribution">
      <IconButton
        size="small"
        color="inherit"
        aria-label="Import Metaclass"
        title="Import Metaclass"
        onClick={() => setModal('dialog')}
        data-testid="import-metaclass">
        <img width="16" height="16" alt={''} src={httpOrigin + '/api/images/icons-override/full/obj16/Metaclass.svg'} />
      </IconButton>
      {modalElement}
    </Fragment>
  );

  if (!isProfileDiagram) {
    return null;
  }
  const targetedNode = nodes.find((node) => node.id === diagramElementId);
  if (diagramId === diagramElementId) {
    return result;
  } else if (targetedNode?.data.targetObjectKind === 'siriusComponents://semantic?domain=uml&entity=Profile') {
    return result;
  } else {
    return null;
  }
};
