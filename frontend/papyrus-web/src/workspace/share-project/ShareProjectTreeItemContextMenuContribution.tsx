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
 *     Pascal Bannerot (CEA LIST) <pascal.bannerot@cea.fr> - Share project with
 *******************************************************************************/
import { TreeItemContextMenuComponentProps } from '@eclipse-sirius/sirius-components-trees';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import CloudUploadOutlinedIcon from '@mui/icons-material/CloudUploadOutlined';
import { forwardRef, Fragment, useState } from 'react';
import { ShareProjectModal } from './ShareProjectModal';
import { Project } from './ShareProjectModal.types';

type Modal = 'ShareProject';

export const ShareProjectTreeItemContextMenuContribution = forwardRef(
  (
    { editingContextId, item, readOnly, treeId, onClose }: TreeItemContextMenuComponentProps,
    ref: React.ForwardedRef<HTMLLIElement>
  ) => {
    const [modal, setModal] = useState<Modal>(null);

    const onSharedProject = () => {
      onClose();
    };

    //!\ Enhancement needed
    const myProject: Project = {
      id: editingContextId,
      name: '',
    };

    let modalElement = null;
    if (modal === 'ShareProject') {
      modalElement = <ShareProjectModal project={myProject} onSuccess={onSharedProject} onCancel={onClose} />;
    }

    if (treeId.startsWith('explorer://') && item.kind.startsWith('siriusWeb://document')) {
      return (
        <Fragment key="umlmodel-tree-item-context-menu-contribution">
          <MenuItem
            key="share-project"
            data-testid="share-project"
            onClick={() => setModal('ShareProject')}
            ref={ref}
            disabled={readOnly}
            aria-disabled>
            <ListItemIcon>
              <CloudUploadOutlinedIcon fontSize="small" />
            </ListItemIcon>
            <ListItemText primary="Share a project" />
          </MenuItem>
          {modalElement}
        </Fragment>
      );
    } else {
      return null;
    }
  }
);
