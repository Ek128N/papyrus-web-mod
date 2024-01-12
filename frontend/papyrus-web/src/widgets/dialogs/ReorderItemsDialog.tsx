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
 ***************************************************************************/

import { Dialog, DialogContent, DialogContentText, DialogTitle, makeStyles } from '@material-ui/core';
import Button from '@material-ui/core/Button';
import DialogActions from '@material-ui/core/DialogActions';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import DragHandleIcon from '@material-ui/icons/DragHandle';
import { useState } from 'react';
import { ReorderItemsDialogProps, ReorderItemsDialogState } from './ReorderItemsDialog.types';
import { IconOverlay } from '@eclipse-sirius/sirius-components-core';

const useStyles = makeStyles((theme) => ({
  dialogContent: {
    overflowX: 'hidden',
  },
  root: {
    margin: 'auto',
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
  dragIcon: {
    minWidth: '0px;',
    marginRight: '10px',
  },
}));

const ReorderItemsDialog = ({ items, onClose, moveElement }: ReorderItemsDialogProps) => {
  const classes = useStyles();
  const [state, setState] = useState<ReorderItemsDialogState>({
    draggingItemId: undefined,
    draggingStartIndex: -1,
    draggingIndex: -1,
  });

  const handleDragStartOrder = (id: string, index: number) => {
    setState((prevState) => {
      return {
        ...prevState,
        draggingItemId: id,
        draggingStartIndex: index,
      };
    });
  };

  const handleDragOverOrder = (event: React.DragEvent, index: number) => {
    event.preventDefault();
    if (state.draggingItemId) {
      setState((prevState) => {
        return {
          ...prevState,
          draggingIndex: index,
        };
      });
    }
  };

  const handleDragEndOrder = () => {
    if (state.draggingItemId) {
      moveElement(state.draggingItemId, state.draggingStartIndex, state.draggingIndex);
    }
    setState((prevState) => {
      return {
        ...prevState,
        draggingItemId: undefined,
      };
    });
  };

  return (
    <Dialog
      open={true}
      onClose={() => onClose()}
      aria-labelledby="dialog-title"
      maxWidth={false}
      data-testid="containment-reference-reorder-items-dialog">
      <DialogTitle id="dialog-title">Sort elements</DialogTitle>
      <DialogContent className={classes.dialogContent}>
        <DialogContentText>Drag and drop elements to reorder them.</DialogContentText>
        <div className={classes.paper}>
          <List dense component="div" role="list" data-testid="containment-reference-items-list">
            {items.map(({ label, id, iconURL }, index) => (
              <ListItem
                style={{ cursor: 'grab' }}
                key={id}
                role="listitem"
                data-testid={label}
                draggable
                onDragStart={() => handleDragStartOrder(id, index)}
                onDragOver={(event) => handleDragOverOrder(event, index)}
                onDragEnd={handleDragEndOrder}>
                <ListItemIcon className={classes.dragIcon}>
                  <DragHandleIcon />
                </ListItemIcon>
                <ListItemIcon className={classes.dragIcon}>
                  <IconOverlay iconURL={iconURL} alt={label} />
                </ListItemIcon>
                <ListItemText id={`containment-reference-reorder-item-${id}-label`} primary={label} />
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
          data-testid="close-containment-reference-reorder-items-dialog"
          onClick={() => onClose()}>
          close
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default ReorderItemsDialog;
