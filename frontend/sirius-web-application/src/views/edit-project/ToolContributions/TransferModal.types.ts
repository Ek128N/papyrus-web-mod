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
 *     CEA LIST - copied from TranserModal.types.ts at 10f4e6386bb9eeb3839071e9cdd4d1ceb8efcb39. Adapted to show a FilterableSortableList on the left and right.
 *******************************************************************************/

import { GQLMetaclassMetadata } from './PapyrusPopupToolContribution.types';

export interface TransferModalProps {
  editingContextId: string;
  items: GQLMetaclassMetadata[];
  onClose: (selectedElementIds: string[]) => void;
}

export interface TransferModalState {
  left: ItemWithIcon[];
  leftSelection: ItemWithIcon[];
  right: ItemWithIcon[];
  rightSelection: ItemWithIcon[];
  draggingRightItemId: string | undefined;
}

export interface ItemWithIcon {
  id: string;
  label: string;
  iconURL: string;
}
