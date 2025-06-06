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
import { ApplyProfileModal } from './ApplyProfileModal';

type Modal = 'ApplyProfile';

export const UMLModelTreeItemContextMenuContribution = forwardRef(
  (
    { editingContextId, item, readOnly, treeId, onClose }: TreeItemContextMenuComponentProps,
    ref: React.ForwardedRef<HTMLLIElement>
  ) => {
    const [modal, setModal] = useState<Modal>(null);

    const onAppliedProfile = () => {
      onClose();
    };

    let modalElement = null;
    if (modal === 'ApplyProfile') {
      modalElement = (
        <ApplyProfileModal
          editingContextId={editingContextId}
          item={item}
          onAppliedProfile={onAppliedProfile}
          onClose={onClose}
        />
      );
    }

    if (
      treeId.startsWith('explorer://') &&
      item.editable &&
      (item.kind === 'siriusComponents://semantic?domain=uml&entity=Model' ||
        item.kind === 'siriusComponents://semantic?domain=uml&entity=Package' ||
        item.kind === 'siriusComponents://semantic?domain=uml&entity=Profile')
    ) {
      return (
        <Fragment key="umlmodel-tree-item-context-menu-contribution">
          <MenuItem
            key="apply-profile"
            data-testid="apply-profile"
            onClick={() => setModal('ApplyProfile')}
            ref={ref}
            disabled={readOnly}
            aria-disabled>
            <ListItemIcon>
              <AddIcon fontSize="small" />
            </ListItemIcon>
            <ListItemText primary="Apply a profile" />
          </MenuItem>
          {modalElement}
        </Fragment>
      );
    } else {
      return null;
    }
  }
);
