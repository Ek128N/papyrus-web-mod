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
 *     CEA LIST - initial API and implementation
 *******************************************************************************/

export interface RevokeProjectModalProps {
  project: Project;
  onSuccess: () => void;
  onCancel: () => void;
}

export interface RevokeProjectModalState {
  userName: string;
  pristine: boolean;
}

export interface Project {
  id: string;
  name: string;
}
