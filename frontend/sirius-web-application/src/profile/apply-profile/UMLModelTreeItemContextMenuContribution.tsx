/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import MenuItem from '@material-ui/core/MenuItem';
import AddIcon from '@material-ui/icons/Add';
import { forwardRef, Fragment, useState } from 'react';
import { ApplyProfileModal } from './ApplyProfileModal';

type Modal = 'ApplyProfile';

export const UMLModelTreeItemContextMenuContribution = forwardRef(
  (
    { editingContextId, item, readOnly, onClose }: TreeItemContextMenuComponentProps,
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
  }
);
