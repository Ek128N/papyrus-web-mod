/*******************************************************************************
 * Copyright (c) 2019, 2024 CEA LIST, Obeo.
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

import { gql, useMutation, useQuery } from '@apollo/client';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import IconButton from '@mui/material/IconButton';
import InputLabel from '@mui/material/InputLabel';
import { useMachine } from '@xstate/react';
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';
import Snackbar from '@mui/material/Snackbar';
import { makeStyles } from 'tss-react/mui';
import CloseIcon from '@mui/icons-material/Close';
import { StateMachine } from 'xstate';
import { useEffect } from 'react';
import {
  ApplyStereotypeModalProps,
  GQLApplyStereotypeMutationData,
  GQLApplyStereotypePayload,
  GQLErrorPayload,
  GQLGetStereotypesQueryData,
  GQLGetStereotypesQueryVariables,
} from './ApplyStereotypeModal.types';
import {
  ApplyStereotypeModalContext,
  ApplyStereotypeModalEvent,
  ChangeStereotypeEvent,
  FetchedStereotypesEvent,
  HandleResponseEvent,
  HideToastEvent,
  SchemaValue,
  ShowToastEvent,
  applyStereotypeModalMachine,
  ApplyStereotypeModalStateSchema,
} from './ApplyStereotypeModalMachine';

const applyStereotypeMutation = gql`
  mutation applyStereotype($input: ApplyStereotypeInput!) {
    applyStereotype(input: $input) {
      __typename
      ... on ApplyStereotypeSuccessPayload {
        id
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const getStereotypesQuery = gql`
  query getStereotypes($editingContextId: ID!, $elementId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        stereotypeMetatadas(elementId: $elementId) {
          id
          label
        }
      }
    }
  }
`;

const useApplyStereotypeModalStyles = makeStyles()((theme) => ({
  form: {
    display: 'flex',
    flexDirection: 'column',
    '& > *': {
      marginBottom: theme.spacing(1),
    },
  },
}));

const isErrorPayload = (payload: GQLApplyStereotypePayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const ApplyStereotypeModal = ({
  editingContextId,
  item,
  onAppliedStereotype,
  onClose,
}: ApplyStereotypeModalProps) => {
  const { classes } = useApplyStereotypeModalStyles();
  const [{ value, context }, dispatch] =
    useMachine<StateMachine<ApplyStereotypeModalContext, ApplyStereotypeModalStateSchema, ApplyStereotypeModalEvent>>(
      applyStereotypeModalMachine
    );
  const { applyStereotypeModal, toast } = value as SchemaValue;
  const { selectedStereotypeId, stereotypes, message } = context;

  const {
    loading: stereotypesLoading,
    data: stereotypesData,
    error: stereotypesError,
  } = useQuery<GQLGetStereotypesQueryData, GQLGetStereotypesQueryVariables>(getStereotypesQuery, {
    variables: { editingContextId, elementId: item.id },
  });

  useEffect(() => {
    if (!stereotypesLoading) {
      if (stereotypesError) {
        const showToastEvent: ShowToastEvent = {
          type: 'SHOW_TOAST',
          message: 'An unexpected error has occurred, please refresh the page',
        };
        dispatch(showToastEvent);
      }
      if (stereotypesData) {
        const fetchStereotypesEvent: FetchedStereotypesEvent = {
          type: 'HANDLE_FETCHED_STEREOTYPES',
          data: stereotypesData,
        };
        dispatch(fetchStereotypesEvent);
      }
    }
  }, [stereotypesLoading, stereotypesData, stereotypesError, dispatch]);

  const onStereotypeChange = (event) => {
    const value = event.target.value;
    const changeStereotypeEvent: ChangeStereotypeEvent = {
      type: 'CHANGE_STEREOTYPE',
      stereotypeId: value,
    };
    dispatch(changeStereotypeEvent);
  };

  const [applyStereotype, { loading: applyStereotypeLoading, data: applyStereotypeData, error: applyStereotypeError }] =
    useMutation<GQLApplyStereotypeMutationData>(applyStereotypeMutation);
  useEffect(() => {
    if (!applyStereotypeLoading) {
      if (applyStereotypeError) {
        const showToastEvent: ShowToastEvent = {
          type: 'SHOW_TOAST',
          message: 'An unexpected error has occurred, please refresh the page',
        };
        dispatch(showToastEvent);
      }
      if (applyStereotypeData) {
        const handleResponseEvent: HandleResponseEvent = { type: 'HANDLE_RESPONSE', data: applyStereotypeData };
        dispatch(handleResponseEvent);

        const { applyStereotype } = applyStereotypeData;
        if (isErrorPayload(applyStereotype)) {
          const { message } = applyStereotype;
          const showToastEvent: ShowToastEvent = { type: 'SHOW_TOAST', message };
          dispatch(showToastEvent);
        }
      }
    }
  }, [applyStereotypeLoading, applyStereotypeData, applyStereotypeError, dispatch]);

  const onApplyStereotype = () => {
    dispatch({ type: 'APPLY_STEREOTYPE' } as ApplyStereotypeModalEvent);
    const input = {
      id: crypto.randomUUID(),
      editingContextId,
      elementId: item.id,
      stereotypeId: selectedStereotypeId,
    };

    applyStereotype({ variables: { input } });
  };

  useEffect(() => {
    console.log(applyStereotypeModal);
    if (applyStereotypeModal === 'success') {
      onAppliedStereotype();
    }
  }, [applyStereotypeModal, onAppliedStereotype]);

  return (
    <>
      <Dialog
        open={true}
        onClose={onClose}
        aria-labelledby="dialog-title"
        maxWidth="xs"
        fullWidth
        data-testid="apply-stereotype-dialog">
        <DialogTitle id="dialog-title" data-testid="apply-stereotype-dialog-title">
          Apply a new stereotype
        </DialogTitle>
        <DialogContent data-testid="apply-stereotype-dialog-content">
          <div className={classes.form}>
            <InputLabel id="applyStereotypeModalStereotypeLabel">Stereotype</InputLabel>
            <Select
              value={selectedStereotypeId}
              onChange={onStereotypeChange}
              disabled={applyStereotypeModal === 'loading' || applyStereotypeModal === 'applyingStereotype'}
              labelId="newDocumentModalStereotypeDescriptionLabel"
              inputProps={{ 'data-testid': 'stereotype-input' }}
              fullWidth
              data-testid="stereotype">
              {stereotypes.map((stereotype) => (
                <MenuItem value={stereotype.id} key={stereotype.id} data-testid={stereotype.label}>
                  {stereotype.label}
                </MenuItem>
              ))}
            </Select>
          </div>
        </DialogContent>
        <DialogActions>
          <Button
            variant="contained"
            disabled={applyStereotypeModal !== 'valid'}
            data-testid="apply-stereotype-submit"
            color="primary"
            onClick={onApplyStereotype}>
            Apply
          </Button>
        </DialogActions>
      </Dialog>
      <Snackbar
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'right',
        }}
        open={toast === 'visible'}
        autoHideDuration={3000}
        onClose={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}
        message={message}
        action={
          <IconButton
            size="small"
            aria-label="close"
            color="inherit"
            onClick={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}>
            <CloseIcon fontSize="small" />
          </IconButton>
        }
        data-testid="error"
      />
    </>
  );
};
