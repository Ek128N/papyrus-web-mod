/*****************************************************************************
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
 *****************************************************************************/
import { gql, useMutation, useQuery } from '@apollo/client';
import { ServerContext, ServerContextValue, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { DiagramPaletteToolContributionComponentProps } from '@eclipse-sirius/sirius-components-diagrams';
import Dialog from '@material-ui/core/Dialog';
import DialogContent from '@material-ui/core/DialogContent';
import IconButton from '@material-ui/core/IconButton';
import { Fragment, useContext, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { EditProjectViewParams } from '../EditProjectView.types';
import {
  ErrorPayload,
  GQLCreateMetaclassImportData,
  GQLCreateMetaclassImportVariables,
  GQLGetMetaclassesQueryData,
  GQLGetMetaclassesQueryVariables,
} from './PapyrusPopupToolContribution.types';
import { TransferModal } from './TransferModal';

type Modal = 'dialog';

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

  const { projectId: editingContextId, representationId } = useParams<EditProjectViewParams>();

  const [metaclasses, setMetaclasses] = useState([{ id: '0', name: 'Loading...', imagePath: '' }]);

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

  return (
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
};
