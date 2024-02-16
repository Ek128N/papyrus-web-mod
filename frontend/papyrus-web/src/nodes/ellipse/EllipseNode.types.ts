/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

/**
 * Code duplicated from <a href="https://github.com/eclipse-sirius/sirius-web">Sirius Web</a> (packages\sirius-web\frontend\sirius-web\src\nodes\EllipseNode.types.tsx).
 */
import { GQLNodeStyle, NodeData } from '@eclipse-sirius/sirius-components-diagrams';

export interface EllipseNodeData extends NodeData {}

export interface GQLEllipseNodeStyle extends GQLNodeStyle {
  color: string;
  borderColor: string;
  borderStyle: string;
  borderSize: string;
}
