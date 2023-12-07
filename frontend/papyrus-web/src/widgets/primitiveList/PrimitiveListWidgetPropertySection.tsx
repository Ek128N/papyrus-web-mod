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
 *****************************************************************************/
import { gql, useMutation } from '@apollo/client';
import { ServerContext, ServerContextValue, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { GQLListItem, PropertySectionLabel, getTextDecorationLineValue } from '@eclipse-sirius/sirius-components-forms';
import { Input, InputAdornment } from '@material-ui/core';
import FormControl from '@material-ui/core/FormControl';
import FormHelperText from '@material-ui/core/FormHelperText';
import IconButton from '@material-ui/core/IconButton';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TextField from '@material-ui/core/TextField';
import TableCell from '@material-ui/core/TableCell';
import TableRow from '@material-ui/core/TableRow';
import Typography from '@material-ui/core/Typography';
import { Theme, makeStyles } from '@material-ui/core/styles';
import AddIcon from '@material-ui/icons/Add';
import DeleteIcon from '@material-ui/icons/Delete';
import BubbleChartIcon from '@material-ui/icons/BubbleChart';
import { MouseEvent, useContext, useEffect, useState } from 'react';
import { IconURL } from '../IconURL';
import {
  GQLActionPrimitiveListItemMutationData,
  GQLActionPrimitiveListItemMutationVariables,
  GQLActionPrimitiveListItemPayload,
  GQLActionableListItem,
  GQLAddPrimitiveListItemMutationData,
  GQLAddPrimitiveListItemPayload,
  GQLDeletePrimitiveListItemMutationData,
  GQLDeletePrimitiveListItemPayload,
  GQLErrorPayload,
  GQLReorderPrimitiveListItemsMutationData,
  GQLReorderPrimitiveListItemsMutationVariables,
  GQLReorderPrimitiveListItemsPayload,
  GQLSuccessPayload,
  PrimitiveListPropertySectionProps,
  PrimitiveListStyleProps,
} from './PrimitiveListWidgetPropertySection.types';

import { Autocomplete } from '@material-ui/lab';
import ReorderIcon from '../containmentReference/ReorderIcon';
import ReorderItemsDialog from '../dialogs/ReorderItemsDialog';

export const deletePrimitiveListItemMutation = gql`
  mutation deletePrimitiveListItem($input: DeleteListItemInput!) {
    deletePrimitiveListItem(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

export const addPrimitiveListItemMutation = gql`
  mutation addPrimitiveListItem($input: AddPrimitiveListItemInput!) {
    addPrimitiveListItem(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

export const reorderPrimitiveListItemsMutation = gql`
  mutation reorderPrimitiveListItems($input: ReorderPrimitiveListItemsInput!) {
    reorderPrimitiveListItems(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

export const actionPrimitiveListItemMutation = gql`
  mutation actionPrimitiveListItemMutation($input: ActionListItemInput!) {
    actionPrimitiveListItem(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

const usePrimitiveListPropertySectionStyles = makeStyles<Theme, PrimitiveListStyleProps>((theme) => ({
  cell: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
  },
  canBeSelectedItem: {
    '&:hover': {
      textDecoration: 'underline',
      cursor: 'pointer',
    },
  },
  style: {
    color: ({ color }) => (color ? color : null),
    fontSize: ({ fontSize }) => (fontSize ? fontSize : null),
    fontStyle: ({ italic }) => (italic ? 'italic' : null),
    fontWeight: ({ bold }) => (bold ? 'bold' : null),
    textDecorationLine: ({ underline, strikeThrough }) => getTextDecorationLineValue(underline, strikeThrough),
    overflow: 'hidden',
    textOverflow: 'ellipsis',
    whiteSpace: 'nowrap',
    flexGrow: 1,
  },
  autocomplete: {
    display: 'flex',
    justifyContent: 'space-between',
    width: '100%',
  },
}));

const NONE_WIDGET_ITEM_ID = 'none';

const isErrorPayload = (
  payload:
    | GQLAddPrimitiveListItemPayload
    | GQLDeletePrimitiveListItemPayload
    | GQLReorderPrimitiveListItemsPayload
    | GQLActionPrimitiveListItemPayload
): payload is GQLErrorPayload => payload.__typename === 'ErrorPayload';

const isSuccessPayload = (
  payload:
    | GQLAddPrimitiveListItemPayload
    | GQLDeletePrimitiveListItemPayload
    | GQLReorderPrimitiveListItemsPayload
    | GQLActionPrimitiveListItemPayload
): payload is GQLSuccessPayload => payload.__typename === 'SuccessPayload';

export const PrimitiveListSection = ({
  editingContextId,
  formId,
  widget,
  subscribers,
  readOnly,
}: PrimitiveListPropertySectionProps) => {
  const props: PrimitiveListStyleProps = {
    color: widget.style?.color ?? null,
    fontSize: widget.style?.fontSize ?? null,
    italic: widget.style?.italic ?? null,
    bold: widget.style?.bold ?? null,
    underline: widget.style?.underline ?? null,
    strikeThrough: widget.style?.strikeThrough ?? null,
  };
  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);
  const [newValue, setNewValue] = useState('');
  const [openReorderDialog, setOpenReorderDialog] = useState<boolean>(false);
  const classes = usePrimitiveListPropertySectionStyles(props);

  let items = [...widget.items];
  if (items.length === 0) {
    items.push({
      id: NONE_WIDGET_ITEM_ID,
      iconURL: [],
      label: 'None',
      kind: 'Unknown',
      deletable: false,
      hasAction: false,
      actionIconURL: '',
    });
  }

  const [deleteListItem, { loading: deleteLoading, error: deleteError, data: deleteData }] =
    useMutation<GQLDeletePrimitiveListItemMutationData>(deletePrimitiveListItemMutation);

  const onDelete = (_: MouseEvent<HTMLElement>, item: GQLListItem) => {
    const variables = {
      input: {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: formId,
        listId: widget.id,
        listItemId: item.id,
      },
    };
    deleteListItem({ variables });
  };

  const [addListItem, { loading: addLoading, error: addError, data: addData }] =
    useMutation<GQLAddPrimitiveListItemMutationData>(addPrimitiveListItemMutation);

  const onAdd = () => {
    if (newValue) {
      const variables = {
        input: {
          id: crypto.randomUUID(),
          editingContextId,
          representationId: formId,
          listId: widget.id,
          newValue,
        },
      };
      addListItem({ variables });
    }
  };

  const [reorderPrimitiveListItems, { loading: reorderLoading, error: reorderError, data: reorderData }] = useMutation<
    GQLReorderPrimitiveListItemsMutationData,
    GQLReorderPrimitiveListItemsMutationVariables
  >(reorderPrimitiveListItemsMutation);

  const [actionPrimitiveListItem, { loading: actionLoading, error: actionError, data: actionData }] = useMutation<
    GQLActionPrimitiveListItemMutationData,
    GQLActionPrimitiveListItemMutationVariables
  >(actionPrimitiveListItemMutation);

  const { addErrorMessage, addMessages } = useMultiToast();

  useEffect(() => {
    if (!deleteLoading) {
      if (deleteError) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (deleteData) {
        const { deletePrimitiveListItem } = deleteData;
        if (isErrorPayload(deletePrimitiveListItem) || isSuccessPayload(deletePrimitiveListItem)) {
          addMessages(deletePrimitiveListItem.messages);
        }
      }
    }
  }, [deleteLoading, deleteError, deleteData]);

  useEffect(() => {
    if (!addLoading) {
      if (addError) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (addData) {
        const { addPrimitiveListItem } = addData;
        if (isSuccessPayload(addPrimitiveListItem)) {
          setNewValue('');
        }
        if (isErrorPayload(addPrimitiveListItem) || isSuccessPayload(addPrimitiveListItem)) {
          addMessages(addPrimitiveListItem.messages);
        }
      }
    }
  }, [addLoading, addError, addData]);

  useEffect(() => {
    if (!reorderLoading) {
      if (reorderError) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (reorderData) {
        const { reorderPrimitiveListItems } = reorderData;
        if (isErrorPayload(reorderPrimitiveListItems) || isSuccessPayload(reorderPrimitiveListItems)) {
          addMessages(reorderPrimitiveListItems.messages);
        }
      }
    }
  }, [reorderLoading, reorderError, reorderData]);

  useEffect(() => {
    if (!actionLoading) {
      if (actionError) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (actionData) {
        const { actionPrimitiveListItem } = actionData;
        if (isErrorPayload(actionPrimitiveListItem) || isSuccessPayload(actionPrimitiveListItem)) {
          addMessages(actionPrimitiveListItem.messages);
        }
      }
    }
  }, [actionLoading, actionError, actionData]);

  const callActionPrimitvieListItem = (_: MouseEvent<HTMLElement>, item: GQLActionableListItem) => {
    const variables: GQLActionPrimitiveListItemMutationVariables = {
      input: {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: formId,
        listId: widget.id,
        itemId: item.id,
      },
    };
    actionPrimitiveListItem({ variables });
  };

  const getTableCellContent = (item: GQLActionableListItem): JSX.Element => {
    return (
      <TableCell className={classes.cell}>
        <IconURL iconURL={item.iconURL} alt={item.label} />
        <Typography
          className={classes.style}
          color="textPrimary"
          data-testid={`primitive-list-item-content-${item.label}`}>
          {item.label}
        </Typography>
        {item.hasAction && (
          <IconButton
            aria-label="action item"
            onClick={(event) => callActionPrimitvieListItem(event, item)}
            size="small"
            disabled={readOnly || widget.readOnly}
            data-testid={`primitive-list-item-action-button-${item.label}`}>
            {item.actionIconURL ? (
              <img height="24" width="24" src={httpOrigin + item.actionIconURL} />
            ) : (
              <BubbleChartIcon />
            )}
          </IconButton>
        )}
        {item.deletable && (
          <IconButton
            aria-label="delete list item"
            onClick={(event) => onDelete(event, item)}
            size="small"
            title="Delete item"
            disabled={readOnly || !item.deletable || widget.readOnly}
            data-testid={`primitive-list-item-delete-button-${item.label}`}>
            <DeleteIcon />
          </IconButton>
        )}
      </TableCell>
    );
  };

  const freeValue = (
    <Input
      id={'new-item-text-field' + widget.id}
      type="text"
      value={newValue}
      fullWidth
      margin="dense"
      placeholder="New Item"
      disabled={readOnly || widget.readOnly}
      data-testid={`primitive-list-input-${widget.label}`}
      endAdornment={
        <InputAdornment position="end">
          <IconButton
            data-testid={`primitive-list-add-${widget.label}`}
            size="small"
            onClick={(_) => onAdd()}
            disabled={readOnly || widget.readOnly}>
            <AddIcon />
          </IconButton>
        </InputAdornment>
      }
      onKeyPress={(event) => {
        if (event.key === 'Enter') {
          onAdd();
        }
      }}
      onChange={(event) => setNewValue(event.target.value)}
    />
  );

  const strictValue = (
    <div className={classes.autocomplete}>
      <Autocomplete
        data-testid={`primitive-list-autocomplete-${widget.label}`}
        options={widget.candidates}
        clearOnEscape
        openOnFocus
        fullWidth
        disableClearable
        value={newValue}
        onChange={(_, value) => setNewValue(value)}
        renderInput={(params) => (
          <TextField
            {...params}
            data-testid={`primitive-list-autocomplete-textfield-${widget.label}`}
            variant="standard"
            placeholder={'New Item'}
            error={widget.diagnostics.length > 0}
            helperText={widget.diagnostics[0]?.message}
          />
        )}
        onKeyPress={(event) => {
          if (event.key === 'Enter') {
            onAdd();
          }
        }}
      />
      <IconButton
        aria-label="add"
        size="small"
        title="Add item"
        disabled={readOnly || widget.readOnly || !newValue}
        data-testid={`primitive-list-add-${widget.label}`}
        onClick={() => onAdd()}>
        <AddIcon />
      </IconButton>
    </div>
  );

  const addSection = (
    <TableRow key="Add" data-testid={`primitive-list-add-section-${widget.label}`}>
      <TableCell className={classes.cell}>{widget.candidates?.length > 0 ? strictValue : freeValue}</TableCell>
    </TableRow>
  );

  const callReorderPrimitiveListItems = (valueId: string, fromIndex: number, toIndex: number) => {
    if (valueId && fromIndex !== -1 && toIndex !== -1) {
      const variables: GQLReorderPrimitiveListItemsMutationVariables = {
        input: {
          id: crypto.randomUUID(),
          editingContextId,
          representationId: formId,
          listId: widget.id,
          itemId: valueId,
          fromIndex,
          toIndex,
        },
      };
      reorderPrimitiveListItems({ variables });
    }
  };

  const isEnabled = !readOnly && !widget.readOnly && widget.canReorder && widget.items.length > 1;

  return (
    <>
      <FormControl error={widget.diagnostics.length > 0} fullWidth>
        <div style={{ display: 'flex' }}>
          <PropertySectionLabel
            editingContextId={editingContextId}
            formId={formId}
            widget={widget}
            subscribers={subscribers}
          />
          {widget.canReorder && (
            <IconButton
              style={{ marginLeft: 'auto', marginRight: '16px' }}
              data-testid="containment-reference-reorder-children"
              onClick={() => setOpenReorderDialog(true)}
              disabled={!isEnabled}
              size="small">
              <ReorderIcon fill={`${isEnabled ? '#00000077' : '#B3BFC5'}`} />
            </IconButton>
          )}
        </div>
        <Table size="small">
          <TableBody data-testid={`primitive-list-table-${widget.label}`}>
            {items.map((item) => (
              <TableRow key={item.id} data-testid={`primitive-list-item-${item.label}`}>
                {getTableCellContent(item)}
              </TableRow>
            ))}
            {widget.canAdd && addSection}
          </TableBody>
        </Table>

        <FormHelperText>{widget.diagnostics[0]?.message}</FormHelperText>
      </FormControl>
      {openReorderDialog && (
        <ReorderItemsDialog
          items={widget.items.map(({ label, id, iconURL }) => ({ label, id, iconURL }))}
          moveElement={callReorderPrimitiveListItems}
          onClose={() => setOpenReorderDialog(false)}
        />
      )}
    </>
  );
};
