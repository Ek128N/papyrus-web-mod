/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Pascal Bannerot (CEA LIST) <pascal.bannerot@cea.fr> - Revoke share access
 *******************************************************************************/
import { TreeItemContextMenuComponentProps } from '@eclipse-sirius/sirius-components-trees';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import CloudOffRoundedIcon from '@mui/icons-material/CloudOffRounded';
import { forwardRef, Fragment, useState } from 'react';
import { RevokeProjectModal } from './RevokeProjectModal';
import { Project } from './RevokeProjectModal.types';

type Modal = 'RevokeProject';

export const RevokeProjectTreeItemContextMenuContribution = forwardRef(
  (
    { editingContextId, item, readOnly, treeId, onClose }: TreeItemContextMenuComponentProps,
    ref: React.ForwardedRef<HTMLLIElement>
  ) => {
    const [modal, setModal] = useState<Modal>(null);

    const onRevokedProject = () => {
      onClose();
    };

    //!\ Enhancement needed
    const myProject: Project = {
      id: editingContextId,
      name: '',
    };

    let modalElement = null;
    if (modal === 'RevokeProject') {
      modalElement = <RevokeProjectModal project={myProject} onSuccess={onRevokedProject} onCancel={onClose} />;
    }

    if (treeId.startsWith('explorer://') && item.kind.startsWith('siriusWeb://document')) {
      return (
        <Fragment key="umlmodel-tree-item-context-menu-contribution">
          <MenuItem
            key="revoke-project"
            data-testid="revoke-project"
            onClick={() => setModal('RevokeProject')}
            ref={ref}
            disabled={readOnly}
            aria-disabled>
            <ListItemIcon>
              <CloudOffRoundedIcon fontSize="small" />
            </ListItemIcon>
            <ListItemText primary="Revoke share access" />
          </MenuItem>
          {modalElement}
        </Fragment>
      );
    } else {
      return null;
    }
  }
);
