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
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import TextField from '@mui/material/TextField';
import { useEffect, useState } from 'react';
import { ShareProjectModalProps, ShareProjectModalState } from './ShareProjectModal.types';
import { useShareProject } from './useShareProject';

export const ShareProjectModal = ({ project, onCancel, onSuccess }: ShareProjectModalProps) => {
  const [state, setState] = useState<ShareProjectModalState>({
    userName: project.name,
    pristine: true,
  });

  const onNewUserName = (event: React.ChangeEvent<HTMLInputElement>) => {
    const userName = event.target.value;
    setState((prevState) => ({ ...prevState, userName, pristine: false }));
  };

  const { shareProject, loading, projectShared } = useShareProject();

  const onShareProject = (event: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    event.preventDefault();
    shareProject(project.id, state.userName);
  };

  useEffect(() => {
    if (projectShared) {
      onSuccess();
    }
  }, [projectShared, onSuccess]);

  const nameIsInvalid: boolean =
    !state.pristine && (state.userName.trim().length === 0 || state.userName.trim().length > 1024);

  return (
    <Dialog open onClose={onCancel} aria-labelledby="dialog-title" maxWidth="xs" fullWidth>
      <DialogTitle id="dialog-title">Share the project</DialogTitle>
      <DialogContent>
        <TextField
          variant="standard"
          value={state.userName}
          onChange={onNewUserName}
          error={nameIsInvalid}
          helperText="Enter another user name"
          label="With user"
          placeholder="Share this project with other"
          data-testid="share-textfield"
          autoFocus
          fullWidth
        />
      </DialogContent>
      <DialogActions>
        <Button
          variant="contained"
          disabled={loading}
          onClick={onShareProject}
          color="primary"
          data-testid="share-project">
          Share
        </Button>
      </DialogActions>
    </Dialog>
  );
};
