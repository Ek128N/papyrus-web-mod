/*****************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo.
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
 ***************************************************************************/

import { ServerContext, ServerContextValue } from '@eclipse-sirius/sirius-components-core';
import { Dialog, DialogContent, DialogContentText, DialogTitle, makeStyles } from '@material-ui/core';
import Button from '@material-ui/core/Button';
import DialogActions from '@material-ui/core/DialogActions';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import { useContext, useState } from 'react';
import { CreateNewChildDialogProps } from './CreateNewChildDialog.types';

const useStyles = makeStyles((theme) => ({
  dialogContent: {
    overflowX: 'hidden',
  },
  paper: {
    width: 400,
    height: 370,
    border: '1px solid',
    borderColor: theme.palette.grey[500],
    overflow: 'auto',
  },
  borderStyle: {
    height: 300,
    overflow: 'auto',
  },
  button: {
    margin: theme.spacing(0.5, 0),
  },
  iconRoot: {
    minWidth: theme.spacing(3),
  },
}));

const CreateNewChildDialog = ({ childTypes, onClose }: CreateNewChildDialogProps) => {
  const classes = useStyles();
  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);
  const [selectedChildTypeId, setSelectedChildTypeId] = useState<string>(childTypes[0].id);

  return (
    <Dialog
      open={true}
      onClose={() => onClose(null)}
      aria-labelledby="dialog-title"
      maxWidth={false}
      data-testid="containment-reference-new-child-dialog">
      <DialogTitle id="dialog-title">New Element</DialogTitle>
      <DialogContent className={classes.dialogContent}>
        <DialogContentText>Select the new element type</DialogContentText>
        <div className={classes.paper}>
          <List data-testid="containment-reference-new-child-dialog-types-list">
            {childTypes.map((creationDescription) => (
              <ListItem
                button
                key={creationDescription.id}
                data-testid={creationDescription.label}
                selected={creationDescription.id === selectedChildTypeId}
                onDoubleClick={() => onClose(creationDescription.id)}
                onClick={() => setSelectedChildTypeId(creationDescription.id)}>
                {creationDescription.iconURL && (
                  <ListItemIcon className={classes.iconRoot}>
                    <img
                      height="16"
                      width="16"
                      alt={creationDescription.label}
                      src={httpOrigin + creationDescription.iconURL}
                    />
                  </ListItemIcon>
                )}
                <ListItemText primary={creationDescription.label} />
              </ListItem>
            ))}
          </List>
        </div>
      </DialogContent>
      <DialogActions>
        <Button
          variant="contained"
          color="primary"
          type="button"
          data-testid="close-containment-reference-new-child-dialog"
          onClick={() => onClose(selectedChildTypeId)}>
          create
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default CreateNewChildDialog;
