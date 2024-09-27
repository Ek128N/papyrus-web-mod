/*******************************************************************************
 * Copyright (c) 2021, 2024 CEA LIST, Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
import { TreeItemContextMenuComponentProps } from '@eclipse-sirius/sirius-components-trees';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import AddIcon from '@mui/icons-material/Add';
import { forwardRef, Fragment, useState } from 'react';
import { PublishProfileDialog } from './PublishProfileDialog';

type Modal = 'PublishProfile';

export const PublishProfileTreeItemContextMenuContribution = forwardRef(
  (
    { editingContextId, item, treeId, readOnly, onClose }: TreeItemContextMenuComponentProps,
    ref: React.ForwardedRef<HTMLLIElement>
  ) => {
    const [modal, setModal] = useState<Modal | undefined>(null);

    let modalElement = null;
    if (modal === 'PublishProfile') {
      modalElement = <PublishProfileDialog editingContextId={editingContextId} item={item} onClose={onClose} />;
    }
    if (
      treeId.startsWith('explorer://') &&
      item.kind.includes('siriusComponents://semantic?domain=uml&entity=Profile')
    ) {
      return (
        <Fragment key="umlelement-tree-item-context-menu-contribution">
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
          {modalElement}
        </Fragment>
      );
    } else {
      return null;
    }
  }
);
