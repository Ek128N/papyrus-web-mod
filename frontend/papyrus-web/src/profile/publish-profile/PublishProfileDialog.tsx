/*******************************************************************************
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
 *******************************************************************************/

import { gql, useMutation, useQuery } from '@apollo/client';
import Button from '@material-ui/core/Button';
import Checkbox from '@material-ui/core/Checkbox';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import IconButton from '@material-ui/core/IconButton';
import Snackbar from '@material-ui/core/Snackbar';
import { makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import Typography from '@material-ui/core/Typography';
import CloseIcon from '@material-ui/icons/Close';
import { useEffect, useState } from 'react';
import {
  ErrorPayload,
  GQLGetProfileLastVersionQueryData,
  GQLGetProfileLastVersionQueryVariables,
  NewVersionKind,
  PublishProfileData,
  PublishProfileDialogProps,
  PublishProfileDialogState,
  PublishProfileVariables,
} from './PublishProfileDialog.types';

const publishProfileMutation = gql`
  mutation publishProfile($input: PublishProfileInput!) {
    publishProfile(input: $input) {
      __typename
      ... on ErrorPayload {
        message
      }
    }
  }
`;
const getProfileLastVersion = gql`
  query getProfileLastVersion($editingContextId: ID!, $profileId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        profileLastVersion(profileId: $profileId) {
          major
          minor
          micro
        }
      }
    }
  }
`;

const usePublishProfileDialogStyles = makeStyles((theme) => ({
  content: {
    display: 'flex',
    flexDirection: 'column',
    gap: theme.spacing(3),
  },
  main: {
    display: 'grid',
    gridTemplateColumns: '1fr 1fr',
    gridTemplateRows: '1fr',
    gap: theme.spacing(4),
  },
  version: {
    display: 'flex',
    flexDirection: 'column',
  },
  versionEntries: {
    display: 'grid',
    gridTemplateRows: 'repeat(4, 1fr)',
    gridTemplateColumns: '3fr 1fr',
    gap: theme.spacing(1),
  },
  info: {},
  comments: {},
  copyright: {},
}));

export const PublishProfileDialog = ({ editingContextId, item, onClose }: PublishProfileDialogProps) => {
  const classes = usePublishProfileDialogStyles();

  const [state, setState] = useState<PublishProfileDialogState>({
    customVersion: '0.0.0',
    selectedNewVersionKind: 'Development',
    date: '',
    author: '',
    comments: '',
    copyright: '',
    message: null,
  });

  // DATE MANAGMENT
  useEffect(() => {
    const currentDate = new Date();
    const year = currentDate.toLocaleString('default', { year: 'numeric' });
    const month = currentDate.toLocaleString('default', { month: '2-digit' });
    const day = currentDate.toLocaleString('default', { day: '2-digit' });

    setState((prevState) => ({
      ...prevState,
      date: `${year}-${month}-${day}`,
    }));
  }, []);

  const onDateChange = (event) => {
    const {
      target: { value },
    } = event;
    setState((prevState) => ({
      ...prevState,
      date: value,
    }));
  };

  //VERSION MANAGEMENT
  let currentVersion = 'Loading...';
  let newDevVersion = 'Loading...';
  let newReleaseVersion = 'Loading...';
  let newMajorVersion = 'Loading...';

  const {
    loading: loadingQuery,
    error: errorQuery,
    data: dataQuery,
  } = useQuery<GQLGetProfileLastVersionQueryData, GQLGetProfileLastVersionQueryVariables>(getProfileLastVersion, {
    variables: { editingContextId, profileId: item.id },
  });
  if (dataQuery?.viewer?.editingContext?.profileLastVersion) {
    const { major, minor, micro } = dataQuery.viewer.editingContext.profileLastVersion;
    currentVersion = `${major}.${minor}.${micro}`;
    newDevVersion = `${major}.${minor}.${micro + 1}`;
    newReleaseVersion = `${major}.${minor + 1}.0`;
    newMajorVersion = `${major + 1}.0.0`;
  }

  useEffect(() => {
    if (!loadingQuery) {
      if (errorQuery) {
        setState((prevState) => ({ ...prevState, message: errorQuery.message }));
      } else if (!dataQuery?.viewer?.editingContext?.profileLastVersion) {
        setState((prevState) => ({ ...prevState, message: 'Failed to retrieve the current version of the profile' }));
      }
    }
  }, [loadingQuery, dataQuery, errorQuery]);

  const onChange = (name: string, event) => {
    const {
      target: { value },
    } = event;
    setState((prevState) => ({ ...prevState, [name]: value }));
  };

  const onCheckboxChange = (versionKind: NewVersionKind) => {
    setState((prevState) => ({ ...prevState, selectedNewVersionKind: versionKind }));
  };

  // VALIDATION
  const isPublishEnabled = () => {
    let valid: boolean = !loadingQuery && dataQuery?.viewer?.editingContext?.profileLastVersion !== null;
    if (valid && state.selectedNewVersionKind === 'Custom') {
      const versions = state.customVersion.split('.');
      valid =
        versions.length === 3 &&
        isCorrectDigit(versions[0]) &&
        isCorrectDigit(versions[1]) &&
        isCorrectDigit(versions[2]);
    }
    return valid;
  };

  const isCorrectDigit = (str) => {
    var n = Math.floor(Number(str));
    return n !== Infinity && String(n) === str && n >= 0;
  };

  // PUBLISH ACTION
  const [publishProfile, { data, error }] = useMutation<PublishProfileData, PublishProfileVariables>(
    publishProfileMutation
  );
  useEffect(() => {
    if (error) setState((prevState) => ({ ...prevState, message: error.message }));
    if (data && data.publishProfile.__typename === 'ErrorPayload') {
      const errorPayload = data.publishProfile as ErrorPayload;
      setState((prevState) => ({ ...prevState, message: errorPayload.message }));
    } else if (data && data.publishProfile.__typename === 'PublishProfileSuccessPayload') {
      onClose();
    }
  }, [data, error, onClose]);

  const handlePublishProfile: React.MouseEventHandler<HTMLButtonElement> = () => {
    let version: String;
    if (state.selectedNewVersionKind === 'Development') {
      version = newDevVersion;
    } else if (state.selectedNewVersionKind === 'Release') {
      version = newReleaseVersion;
    } else if (state.selectedNewVersionKind === 'Major') {
      version = newMajorVersion;
    } else {
      version = state.customVersion;
    }
    const variables: PublishProfileVariables = {
      input: {
        id: crypto.randomUUID(),
        editingContextId,
        objectId: item.id,
        version: version,
        comment: state.comments,
        copyright: state.copyright,
        author: state.author,
        date: state.date,
        saveOCLConstraint: true,
      },
    };
    publishProfile({ variables });
  };

  return (
    <>
      <Dialog open={true} onClose={onClose} maxWidth="md" fullWidth data-testid="publish-profile-dialog">
        <DialogTitle>Publish the profile</DialogTitle>
        <DialogContent className={classes.content}>
          <div className={classes.main}>
            <div className={classes.version}>
              <Typography gutterBottom>Version</Typography>
              <Typography variant="caption">previous version: {currentVersion}</Typography>
              <div className={classes.versionEntries}>
                <FormControlLabel
                  control={
                    <Checkbox
                      name="custom"
                      color="primary"
                      checked={state.selectedNewVersionKind === 'Development'}
                      onChange={(_) => onCheckboxChange('Development')}
                    />
                  }
                  label="Development version"
                />
                <Typography>{newDevVersion}</Typography>
                <FormControlLabel
                  control={
                    <Checkbox
                      name="custom"
                      color="primary"
                      checked={state.selectedNewVersionKind === 'Release'}
                      onChange={(_) => onCheckboxChange('Release')}
                    />
                  }
                  label="Release version"
                />
                <Typography>{newReleaseVersion}</Typography>
                <FormControlLabel
                  control={
                    <Checkbox
                      name="custom"
                      color="primary"
                      checked={state.selectedNewVersionKind === 'Major'}
                      onChange={(_) => onCheckboxChange('Major')}
                    />
                  }
                  label="Major release"
                />
                <Typography>{newMajorVersion}</Typography>
                <FormControlLabel
                  control={
                    <Checkbox
                      name="custom"
                      color="primary"
                      checked={state.selectedNewVersionKind === 'Custom'}
                      onChange={(_) => onCheckboxChange('Custom')}
                    />
                  }
                  label="Custom"
                />
                <TextField
                  variant="outlined"
                  margin="dense"
                  value={state.customVersion}
                  onChange={(event) => onChange('customVersion', event)}
                  fullWidth
                  disabled={state.selectedNewVersionKind !== 'Custom'}
                />
              </div>
            </div>

            <div className={classes.info}>
              <Typography gutterBottom>Info</Typography>
              <TextField
                id="date"
                label="Date"
                type="date"
                defaultValue={state.date}
                onChange={onDateChange}
                InputLabelProps={{
                  shrink: true,
                }}
              />
              <TextField
                data-testid="publish-profile-author"
                label="Author"
                value={state.author}
                onChange={(event) => onChange('author', event)}
                fullWidth
              />
            </div>
          </div>

          <div className={classes.comments}>
            <TextField
              data-testid="publish-profile-comment"
              label="Comments"
              value={state.comments}
              onChange={(event) => onChange('comments', event)}
              multiline
              minRows={4}
              fullWidth
            />
          </div>

          <div className={classes.copyright}>
            <TextField
              data-testid="publish-profile-copyright"
              label="Copyright"
              value={state.copyright}
              onChange={(event) => onChange('copyright', event)}
              multiline
              minRows={4}
              fullWidth
            />
          </div>
        </DialogContent>
        <DialogActions>
          <Button onClick={onClose} variant="contained" color="secondary">
            Cancel
          </Button>
          <Button
            data-testid="publish-profile-publish"
            onClick={handlePublishProfile}
            variant="contained"
            color="primary"
            disabled={!isPublishEnabled()}>
            Publish
          </Button>
        </DialogActions>
      </Dialog>
      <Snackbar
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'right',
        }}
        open={!!state.message}
        autoHideDuration={3000}
        onClose={() => setState((prevState) => ({ ...prevState, message: null }))}
        message={state.message}
        action={
          <IconButton
            size="small"
            aria-label="close"
            color="inherit"
            onClick={() => setState((prevState) => ({ ...prevState, message: null }))}>
            <CloseIcon fontSize="small" />
          </IconButton>
        }
        data-testid="error"
      />
    </>
  );
};
