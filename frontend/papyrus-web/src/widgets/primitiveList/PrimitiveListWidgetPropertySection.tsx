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
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { GQLListItem, PropertySectionLabel, getTextDecorationLineValue } from '@eclipse-sirius/sirius-components-forms';
import { Input, InputAdornment } from '@material-ui/core';
import FormControl from '@material-ui/core/FormControl';
import FormHelperText from '@material-ui/core/FormHelperText';
import IconButton from '@material-ui/core/IconButton';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableRow from '@material-ui/core/TableRow';
import Typography from '@material-ui/core/Typography';
import { Theme, makeStyles } from '@material-ui/core/styles';
import AddIcon from '@material-ui/icons/Add';
import DeleteIcon from '@material-ui/icons/Delete';
import { MouseEvent, useEffect, useState } from 'react';
import { IconURL } from '../IconURL';
import {
  GQLAddPrimitiveListItemMutationData,
  GQLAddPrimitiveListItemPayload,
  GQLDeletePrimitiveListItemMutationData,
  GQLDeletePrimitiveListItemPayload,
  GQLErrorPayload,
  GQLSuccessPayload,
  PrimitiveListPropertySectionProps,
  PrimitiveListStyleProps,
} from './PrimitiveListWidgetPropertySection.types';

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
}));

const NONE_WIDGET_ITEM_ID = 'none';

const isErrorPayload = (payload: GQLDeletePrimitiveListItemPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
const isSuccessPayload = (payload: GQLDeletePrimitiveListItemPayload): payload is GQLSuccessPayload =>
  payload.__typename === 'SuccessPayload';

const isAddErrorPayload = (payload: GQLAddPrimitiveListItemPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
const isAddSuccessPayload = (payload: GQLAddPrimitiveListItemPayload): payload is GQLSuccessPayload =>
  payload.__typename === 'SuccessPayload';

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

  const [newValue, setNewValue] = useState('');
  const classes = usePrimitiveListPropertySectionStyles(props);

  let items = [...widget.items];
  if (items.length === 0) {
    items.push({
      id: NONE_WIDGET_ITEM_ID,
      iconURL: [],
      label: 'None',
      kind: 'Unknown',
      deletable: false,
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
  };

  const { addErrorMessage, addMessages } = useMultiToast();

  useEffect(() => {
    if (!deleteLoading) {
      if (deleteError) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (deleteData) {
        const { deletePrimitiveListItem } = deleteData;
        if (isAddErrorPayload(deletePrimitiveListItem) || isAddSuccessPayload(deletePrimitiveListItem)) {
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

  const getTableCellContent = (item: GQLListItem): JSX.Element => {
    return (
      <>
        <TableCell className={classes.cell}>
          <IconURL iconURL={item.iconURL} alt={item.label} />
          <Typography
            className={`${classes.style}`}
            color="textPrimary"
            data-testid={`primitive-list-item-content-${item.label}`}>
            {item.label}
          </Typography>

          {item.deletable && (
            <IconButton
              aria-label="deleteListItem"
              onClick={(event) => onDelete(event, item)}
              size="small"
              disabled={readOnly || !item.deletable || widget.readOnly}
              data-testid={`primitive-list-item-delete-button-${item.label}`}>
              <DeleteIcon />
            </IconButton>
          )}
        </TableCell>
      </>
    );
  };

  const addSection = (
    <TableRow key="Add">
      <TableCell className={classes.cell}>
        <Input
          id={'new-item-text-field' + widget.id}
          type="text"
          value={newValue}
          fullWidth
          margin="dense"
          placeholder="New Item"
          disabled={readOnly || widget.readOnly}
          data-testid={'primitive-list-input'}
          endAdornment={
            <InputAdornment position="end">
              <IconButton
                data-testid={'primitive-list-add'}
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
      </TableCell>
    </TableRow>
  );

  return (
    <FormControl error={widget.diagnostics.length > 0} fullWidth>
      <PropertySectionLabel
        editingContextId={editingContextId}
        formId={formId}
        widget={widget}
        subscribers={subscribers}
      />
      <Table size="small" data-testid={'primitive-list-table'}>
        <TableBody>
          {items.map((item) => (
            <TableRow key={item.id}>{getTableCellContent(item)}</TableRow>
          ))}
          {widget.canAdd && addSection}
        </TableBody>
      </Table>

      <FormHelperText>{widget.diagnostics[0]?.message}</FormHelperText>
    </FormControl>
  );
};
