/*******************************************************************************
 * Copyright (c) 2023 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *     CEA LIST - Copied from TranserModal.tsx at b975254b422a849f96b6fd56d7e9316d52b750e0. Adapted to show a FilterableSortableList on the left and right.
 *******************************************************************************/
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import Grid from '@material-ui/core/Grid';
import IconButton from '@material-ui/core/IconButton';
import { makeStyles } from '@material-ui/core/styles';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import ChevronRightIcon from '@material-ui/icons/ChevronRight';
import React, { useState } from 'react';
import { FilterableSortableList } from './FilterableSortableList';
import { ItemWithIcon, TransferModalProps, TransferModalState } from './TransferModal.types';

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
    overflow: 'auto',
  },
  button: {
    margin: theme.spacing(0.5, 0),
  },
}));

export const TransferModal = ({ items, onClose }: TransferModalProps) => {
  const classes = useStyles();
  const [state, setState] = useState<TransferModalState>({
    right: [],
    rightSelection: [],
    draggingRightItemId: undefined,
    left: items.map((i) => ({
      id: i.id,
      label: i.name,
      iconURL: i.imagePath,
    })),
    leftSelection: [],
  });

  const onClick = (event: React.MouseEvent<Element, MouseEvent>, item: ItemWithIcon) => {
    const isItemRight = state.right.find((entry) => entry.id === item.id);
    const isItemLeft = state.left.find((entry) => entry.id === item.id);

    if (event.ctrlKey || event.metaKey) {
      event.stopPropagation();

      if (isItemRight) {
        const isItemInSelection = state.rightSelection.find((entry) => entry.id === item.id);
        const newSelection: ItemWithIcon[] = isItemInSelection
          ? state.rightSelection.filter((entry) => entry.id !== item.id)
          : [...state.rightSelection, item];
        setState((prevState) => {
          return {
            ...prevState,
            rightSelection: newSelection,
            leftSelection: [],
          };
        });
      } else if (isItemLeft) {
        const isItemInSelection = state.leftSelection.find((entry) => entry.id === item.id);
        const newSelection: ItemWithIcon[] = isItemInSelection
          ? state.leftSelection.filter((entry) => entry.id !== item.id)
          : [...state.leftSelection, item];
        setState((prevState) => {
          return {
            ...prevState,
            rightSelection: [],
            leftSelection: newSelection,
          };
        });
      }
    } else {
      setState((prevState) => {
        return {
          ...prevState,
          rightSelection: isItemRight ? [item] : [],
          leftSelection: isItemLeft ? [item] : [],
        };
      });
    }
  };

  const handleDispatchRight = () => {
    setState((prevState) => {
      return {
        ...prevState,
        right: prevState.right
          .concat(
            prevState.leftSelection.filter(
              (newEntry) => !prevState.right.some((existingEntry) => existingEntry.id === newEntry.id)
            )
          )
          .sort((o1, o2) => o1.label.localeCompare(o2.label)),
        left: prevState.left.filter(
          (newEntry) => !prevState.leftSelection.some((existingEntry) => existingEntry.id === newEntry.id)
        ),
        leftSelection: [],
        rightSelection: [],
      };
    });
  };

  const handleDispatchLeft = () => {
    setState((prevState) => {
      return {
        ...prevState,
        right: prevState.right.filter(
          (entry) => !prevState.rightSelection.some((selected) => selected.id === entry.id)
        ),
        rightSelection: [],
        left: prevState.left
          .concat(
            prevState.rightSelection.filter(
              (newEntry) => !prevState.left.some((existingEntry) => existingEntry.id === newEntry.id)
            )
          )
          .sort((o1, o2) => o1.label.localeCompare(o2.label)),
        leftSelection: [],
      };
    });
  };

  return (
    <Dialog
      open={true}
      onClose={() => onClose([])}
      aria-labelledby="dialog-title"
      maxWidth={false}
      data-testid="transfer-modal">
      <DialogTitle id="dialog-title">Select Metaclass</DialogTitle>
      <DialogContent className={classes.dialogContent}>
        <Grid container spacing={2} justifyContent="center" alignItems="center" className={classes.root}>
          <Grid item>
            <div className={classes.paper}>
              <FilterableSortableList
                items={state.left}
                setItems={(items: ItemWithIcon[]) =>
                  setState((prevState) => {
                    return {
                      ...prevState,
                      left: items,
                    };
                  })
                }
                onClick={onClick}
                onDoubleClick={handleDispatchRight}
                selectedItems={state.leftSelection}
                onFocusFilter={true}
              />
            </div>
          </Grid>
          <Grid item>
            <Grid container direction="column" alignItems="center">
              <IconButton
                className={classes.button}
                onClick={handleDispatchRight}
                disabled={
                  !state.leftSelection.some(
                    (leftEntry) => !state.right.some((rightEntry) => rightEntry.id === leftEntry.id)
                  )
                }
                aria-label="move selected right"
                data-testid="move-right">
                <ChevronRightIcon />
              </IconButton>
              <IconButton
                className={classes.button}
                onClick={handleDispatchLeft}
                disabled={
                  !state.rightSelection.some(
                    (rightEntry) => !state.left.some((leftEntry) => leftEntry.id === rightEntry.id)
                  )
                }
                aria-label="move selected left"
                data-testid="move-left">
                <ChevronLeftIcon />
              </IconButton>
            </Grid>
          </Grid>
          <Grid item>
            <div className={classes.paper}>
              <FilterableSortableList
                items={state.right}
                setItems={(items: ItemWithIcon[]) =>
                  setState((prevState) => {
                    return {
                      ...prevState,
                      right: items,
                    };
                  })
                }
                onClick={onClick}
                onDoubleClick={handleDispatchLeft}
                selectedItems={state.rightSelection}
                onFocusFilter={false}
              />
            </div>
          </Grid>
        </Grid>
      </DialogContent>
      <DialogActions>
        <Button
          variant="contained"
          color="primary"
          type="button"
          data-testid="apply-change"
          onClick={() => {
            onClose(state.right.map((entry) => entry.id));
          }}>
          Apply
        </Button>
      </DialogActions>
    </Dialog>
  );
};
