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
 *     CEA LIST - Copied from FilterableSortableList.tsx at 10f4e6386bb9eeb3839071e9cdd4d1ceb8efcb39. Adapted to contain metaclasses.
 *******************************************************************************/
import { ServerContext, ServerContextValue } from '@eclipse-sirius/sirius-components-core';
import { splitText } from '@eclipse-sirius/sirius-components-trees';
import { ListItemIcon } from '@material-ui/core';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import Typography from '@material-ui/core/Typography';
import { Theme, makeStyles } from '@material-ui/core/styles';
import { useContext, useState } from 'react';
import {
  FilterableSortableListProps,
  FilterableSortableListState,
  HighlightedLabelProps,
} from './FilterableSortableList.types';
import { ModelBrowserFilterBar } from './ModelBrowserFilterBar';

const useStyles = makeStyles((theme: Theme) => ({
  selectable: {
    cursor: 'pointer',
    '&:hover': {
      backgroundColor: theme.palette.action.hover,
    },
    userSelect: 'none',
  },
  selected: {
    cursor: 'pointer',
    backgroundColor: theme.palette.action.selected,
    userSelect: 'none',
  },
  selectedLabel: {
    fontWeight: 'bold',
  },
  title: {
    opacity: 0.6,
    fontSize: theme.typography.caption.fontSize,
  },
  borderStyle: {
    border: '1px solid',
    borderColor: theme.palette.grey[500],
    height: 300,
    overflow: 'auto',
  },
}));
const useLabelStyles = makeStyles((theme: Theme) => ({
  highlight: {
    backgroundColor: theme.palette.navigation.leftBackground,
  },
}));

const HighlightedLabel = ({ label, textToHighlight }: HighlightedLabelProps) => {
  const classes = useLabelStyles();
  let itemLabel: JSX.Element;
  const splitLabelWithTextToHighlight: string[] = splitText(label, textToHighlight);
  if (
    textToHighlight === null ||
    textToHighlight === '' ||
    (splitLabelWithTextToHighlight.length === 1 &&
      splitLabelWithTextToHighlight[0].toLocaleLowerCase() !== label.toLocaleLowerCase())
  ) {
    itemLabel = <>{label}</>;
  } else {
    const languages: string[] = Array.from(navigator.languages);
    itemLabel = (
      <>
        {splitLabelWithTextToHighlight.map((value, index) => {
          const shouldHighlight = value.localeCompare(textToHighlight, languages, { sensitivity: 'base' }) === 0;
          return (
            <span
              key={value + index}
              data-testid={`${label}-${value}-${index}`}
              className={shouldHighlight ? classes.highlight : ''}>
              {value}
            </span>
          );
        })}
      </>
    );
  }
  return <Typography>{itemLabel}</Typography>;
};

export const FilterableSortableList = ({
  items,
  onClick,
  onDoubleClick,
  selectedItems,
  onFocusFilter,
}: FilterableSortableListProps) => {
  const classes = useStyles();
  const [state, setState] = useState<FilterableSortableListState>({
    filterBarText: '',
    hoveringItemId: undefined,
    draggingItemId: undefined,
  });
  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);

  return (
    <>
      <ModelBrowserFilterBar
        onTextChange={(event) =>
          setState((prevState) => {
            return {
              ...prevState,
              filterBarText: event.target.value,
            };
          })
        }
        onTextClear={() =>
          setState((prevState) => {
            return {
              ...prevState,
              filterBarText: '',
            };
          })
        }
        text={state.filterBarText}
        onFocus={onFocusFilter}
      />
      <span className={classes.title}>Selected</span>
      <div className={classes.borderStyle}>
        <List dense component="div" role="list" data-testid="selected-items-list">
          {items
            .filter(({ label }) => {
              if (state.filterBarText === null || state.filterBarText === '') {
                return true;
              }
              const splitLabelWithTextToHighlight: string[] = splitText(label, state.filterBarText);
              return (
                splitLabelWithTextToHighlight.length > 1 ||
                (splitLabelWithTextToHighlight.length === 1 &&
                  splitLabelWithTextToHighlight[0].toLocaleLowerCase() === state.filterBarText.toLocaleLowerCase())
              );
            })
            .map(({ id, label, iconURL }, _) => {
              const labelId = `transfer-list-item-${id}-label`;
              const selected = selectedItems.some((entry) => entry.id === id);
              return (
                <ListItem
                  key={id}
                  role="listitem"
                  className={selected ? classes.selected : classes.selectable}
                  onClick={(event) => onClick(event, { id, label, iconURL })}
                  onDoubleClick={(_) => onDoubleClick()}
                  data-testid={label}>
                  <ListItemIcon>
                    {iconURL ? <img width="16" height="16" alt={''} src={httpOrigin + iconURL} /> : null}
                  </ListItemIcon>
                  <ListItemText
                    id={labelId}
                    primary={<HighlightedLabel label={label} textToHighlight={state.filterBarText} />}
                  />
                </ListItem>
              );
            })}
        </List>
      </div>
    </>
  );
};
