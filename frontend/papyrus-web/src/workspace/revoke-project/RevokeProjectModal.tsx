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
import { RevokeProjectModalProps, RevokeProjectModalState } from './RevokeProjectModal.types';
import { useRevokeProject } from './useRevokeProject';

export const RevokeProjectModal = ({ project, onCancel, onSuccess }: RevokeProjectModalProps) => {
  const [state, setState] = useState<RevokeProjectModalState>({
    userName: project.name,
    pristine: true,
  });

  const onNewUserName = (event: React.ChangeEvent<HTMLInputElement>) => {
    const userName = event.target.value;
    setState((prevState) => ({ ...prevState, userName, pristine: false }));
  };

  const { revokeProject, loading, projectRevoked } = useRevokeProject();

  const onRevokeProject = (event: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    event.preventDefault();
    revokeProject(project.id, state.userName);
  };

  useEffect(() => {
    if (projectRevoked) {
      onSuccess();
    }
  }, [projectRevoked, onSuccess]);

  const nameIsInvalid: boolean =
    !state.pristine && (state.userName.trim().length === 0 || state.userName.trim().length > 1024);

  return (
    <Dialog open onClose={onCancel} aria-labelledby="dialog-title" maxWidth="xs" fullWidth>
      <DialogTitle id="dialog-title">Revoke user access for this project</DialogTitle>
      <DialogContent>
        <TextField
          variant="standard"
          value={state.userName}
          onChange={onNewUserName}
          error={nameIsInvalid}
          helperText="Enter another user name"
          label="Revoke user access"
          placeholder="Revoke user access for this project"
          data-testid="revoke-textfield"
          autoFocus
          fullWidth
        />
      </DialogContent>
      <DialogActions>
        <Button
          variant="contained"
          disabled={loading}
          onClick={onRevokeProject}
          color="primary"
          data-testid="revoke-project">
          Revoke access
        </Button>
      </DialogActions>
    </Dialog>
  );
};
