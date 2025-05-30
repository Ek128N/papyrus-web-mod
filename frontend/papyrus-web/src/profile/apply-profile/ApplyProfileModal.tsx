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
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';
import Snackbar from '@mui/material/Snackbar';
import { makeStyles } from 'tss-react/mui';
import CloseIcon from '@mui/icons-material/Close';
import { useMachine } from '@xstate/react';
import { useEffect } from 'react';
import { StateMachine } from 'xstate';
import {
  ApplyProfileModalProps,
  GQLApplyProfileMutationData,
  GQLApplyProfilePayload,
  GQLErrorPayload,
  GQLGetProfilesQueryData,
  GQLGetProfilesQueryVariables,
} from './ApplyProfileModal.types';
import {
  ApplyProfileEvent,
  ApplyProfileModalContext,
  ApplyProfileModalEvent,
  ChangeProfileEvent,
  FetchedProfilesEvent,
  HandleResponseEvent,
  HideToastEvent,
  SchemaValue,
  ShowToastEvent,
  applyProfileModalMachine,
  ApplyProfileModalStateSchema,
} from './ApplyProfileModalMachine';

const applyProfileMutation = gql`
  mutation applyProfile($input: ApplyProfileInput!) {
    applyProfile(input: $input) {
      __typename
      ... on ApplyProfileSuccessPayload {
        id
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const getUMLProfileMetadatasQuery = gql`
  query getUMLProfileMetadatas {
    viewer {
      profileMetadatas {
        label
        uriPath
      }
    }
  }
`;

const useApplyProfileModalStyles = makeStyles()((theme) => ({
  form: {
    display: 'flex',
    flexDirection: 'column',
    '& > *': {
      marginBottom: theme.spacing(1),
    },
  },
}));

const isErrorPayload = (payload: GQLApplyProfilePayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const ApplyProfileModal = ({ editingContextId, item, onAppliedProfile, onClose }: ApplyProfileModalProps) => {
  const { classes } = useApplyProfileModalStyles();
  const [{ value, context }, dispatch] =
    useMachine<StateMachine<ApplyProfileModalContext, ApplyProfileModalStateSchema, ApplyProfileModalEvent>>(
      applyProfileModalMachine
    );
  const { applyProfileModal, toast } = value as SchemaValue;
  const { selectedProfileId, profiles, message } = context;

  const {
    loading: profilesLoading,
    data: profilesData,
    error: profilesError,
  } = useQuery<GQLGetProfilesQueryData, GQLGetProfilesQueryVariables>(getUMLProfileMetadatasQuery, {
    variables: { editingContextId, kind: item.kind },
  });
  useEffect(() => {
    if (!profilesLoading) {
      if (profilesError) {
        const showToastEvent: ShowToastEvent = {
          type: 'SHOW_TOAST',
          message: 'An unexpected error has occurred, please refresh the page',
        };
        dispatch(showToastEvent);
      }
      if (profilesData) {
        const fetchProfilesEvent: FetchedProfilesEvent = {
          type: 'HANDLE_FETCHED_PROFILES',
          data: profilesData,
        };
        dispatch(fetchProfilesEvent);
      }
    }
  }, [profilesLoading, profilesData, profilesError, dispatch]);

  const onProfileChange = (event) => {
    const value = event.target.value;
    const changeProfileEvent: ChangeProfileEvent = {
      type: 'CHANGE_PROFILE',
      profileId: value,
    };
    dispatch(changeProfileEvent);
  };

  const [applyProfile, { loading: applyProfileLoading, data: applyProfileData, error: applyProfileError }] =
    useMutation<GQLApplyProfileMutationData>(applyProfileMutation);
  useEffect(() => {
    if (!applyProfileLoading) {
      if (applyProfileError) {
        const showToastEvent: ShowToastEvent = {
          type: 'SHOW_TOAST',
          message: 'An unexpected error has occurred, please refresh the page',
        };
        dispatch(showToastEvent);
      }
      if (applyProfileData) {
        const handleResponseEvent: HandleResponseEvent = { type: 'HANDLE_RESPONSE', data: applyProfileData };
        dispatch(handleResponseEvent);

        const { applyProfile } = applyProfileData;
        if (isErrorPayload(applyProfile)) {
          const { message } = applyProfile;
          const showToastEvent: ShowToastEvent = { type: 'SHOW_TOAST', message };
          dispatch(showToastEvent);
        }
      }
    }
  }, [applyProfileLoading, applyProfileData, applyProfileError, dispatch]);

  const onApplyProfile = () => {
    dispatch({ type: 'APPLY_PROFILE' } as ApplyProfileEvent);
    const input = {
      id: crypto.randomUUID(),
      editingContextId,
      modelId: item.id,
      profileUriPath: selectedProfileId,
    };
    applyProfile({ variables: { input } });
  };

  useEffect(() => {
    if (applyProfileModal === 'success') {
      onAppliedProfile();
    }
  }, [applyProfileModal, onAppliedProfile]);

  return (
    <>
      <Dialog
        open={true}
        onClose={onClose}
        aria-labelledby="dialog-title"
        maxWidth="xs"
        fullWidth
        data-testid="apply-profile-dialog">
        <DialogTitle id="dialog-title" data-testid="apply-profile-dialog-title">
          Apply a profile
        </DialogTitle>
        <DialogContent data-testid="apply-profile-dialog-content">
          <div className={classes.form}>
            <InputLabel id="applyProfileModalProfileLabel">Object type</InputLabel>
            <Select
              value={selectedProfileId}
              onChange={onProfileChange}
              disabled={applyProfileModal === 'loading' || applyProfileModal === 'applyingProfile'}
              labelId="applyProfileModalProfileLabel"
              fullWidth
              data-testid="profile">
              {profiles.map((profile) => (
                <MenuItem value={profile.uriPath} key={profile.uriPath}>
                  {profile.label}
                </MenuItem>
              ))}
            </Select>
          </div>
        </DialogContent>
        <DialogActions>
          <Button
            variant="contained"
            disabled={applyProfileModal !== 'valid'}
            data-testid="apply-profile-submit"
            color="primary"
            onClick={onApplyProfile}>
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
