/*******************************************************************************
 * Copyright (c) 2021, 2024 CEA LIST, Obeo.
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
import { Selection, useSelection } from '@eclipse-sirius/sirius-components-core';
import { TreeItemContextMenuComponentProps } from '@eclipse-sirius/sirius-components-trees';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import MenuItem from '@material-ui/core/MenuItem';
import AddIcon from '@material-ui/icons/Add';
import { Fragment, forwardRef, useState } from 'react';
import { NewObjectModal } from '../../modals/new-object/NewObjectModal';
import { NewRepresentationModal } from '../../modals/new-representation/NewRepresentationModal';
import { PublishProfileDialog } from './PublishProfileDialog';

type Modal = 'CreateNewObject' | 'CreateNewRepresentation' | 'PublishProfile';

export const ObjectTreeItemContextMenuContribution = forwardRef(
  (
    { editingContextId, item, readOnly, expandItem, onClose }: TreeItemContextMenuComponentProps,
    ref: React.ForwardedRef<HTMLLIElement>
  ) => {
    const [modal, setModal] = useState<Modal>(null);
    const { setSelection } = useSelection();

    const onObjectCreated = (selection: Selection) => {
      setSelection(selection);
      expandItem();
      onClose();
    };
    let modalElement = null;
    if (modal === 'CreateNewObject') {
      modalElement = (
        <NewObjectModal
          editingContextId={editingContextId}
          item={item}
          onObjectCreated={onObjectCreated}
          onClose={onClose}
        />
      );
    } else if (modal === 'CreateNewRepresentation') {
      modalElement = (
        <NewRepresentationModal
          editingContextId={editingContextId}
          item={item}
          onRepresentationCreated={onObjectCreated}
          onClose={onClose}
        />
      );
    } else if (modal === 'PublishProfile') {
      modalElement = <PublishProfileDialog editingContextId={editingContextId} item={item} onClose={onClose} />;
    }

    let applyProfileMenuItem = null;
    if (item.kind === 'siriusComponents://semantic?domain=uml&entity=Profile') {
      applyProfileMenuItem = (
        <MenuItem
          key="publish-profile"
          data-testid="publish-profile"
          onClick={() => setModal('PublishProfile')}
          disabled={readOnly}
          aria-disabled>
          <ListItemIcon>
            <AddIcon fontSize="small" />
          </ListItemIcon>
          <ListItemText primary="Publish profile" />
        </MenuItem>
      );
    }

    return (
      <Fragment key="object-tree-item-context-menu-contribution">
        <MenuItem
          key="new-object"
          onClick={() => setModal('CreateNewObject')}
          data-testid="new-object"
          disabled={readOnly}
          ref={ref}
          aria-disabled>
          <ListItemIcon>
            <AddIcon fontSize="small" />
          </ListItemIcon>
          <ListItemText primary="New object" />
        </MenuItem>
        <MenuItem
          key="new-representation"
          onClick={() => setModal('CreateNewRepresentation')}
          data-testid="new-representation"
          disabled={readOnly}
          aria-disabled>
          <ListItemIcon>
            <AddIcon fontSize="small" />
          </ListItemIcon>
          <ListItemText primary="New representation" />
        </MenuItem>
        {applyProfileMenuItem}
        {modalElement}
      </Fragment>
    );
  }
);
