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
import { ApplyStereotypeModal } from './ApplyStereotypeModal';

type Modal = 'ApplyStereotype';

export const UMLElementTreeItemContextMenuContribution = forwardRef(
  (
    { editingContextId, item, readOnly, onClose }: TreeItemContextMenuComponentProps,
    ref: React.ForwardedRef<HTMLLIElement>
  ) => {
    const [modal, setModal] = useState<Modal | undefined>(null);

    const onAppliedStereotype = () => {
      onClose();
    };

    let modalElement = null;
    if (modal === 'ApplyStereotype') {
      modalElement = (
        <ApplyStereotypeModal
          editingContextId={editingContextId}
          item={item}
          onAppliedStereotype={onAppliedStereotype}
          onClose={onClose}
        />
      );
    }

    return (
      <Fragment key="umlelement-tree-item-context-menu-contribution">
        <MenuItem
          key="apply-stereotype"
          data-testid="apply-stereotype"
          onClick={() => setModal('ApplyStereotype')}
          ref={ref}
          disabled={readOnly}
          aria-disabled>
          <ListItemIcon>
            <AddIcon fontSize="small" />
          </ListItemIcon>
          <ListItemText primary="Apply a stereotype" />
        </MenuItem>
        {modalElement}
      </Fragment>
    );
  }
);
