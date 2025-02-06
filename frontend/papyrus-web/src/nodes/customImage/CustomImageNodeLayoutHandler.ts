/*****************************************************************************
 * Copyright (c) 2024 CEA LIST, Obeo, Artal Technologies.
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
 *  Titouan BOUETE-GIRAUD (Artal Technologies) - Issue 218
 *****************************************************************************/

import {
  Diagram,
  DiagramNodeType,
  ForcedDimensions,
  ILayoutEngine,
  INodeLayoutHandler,
  NodeData,
  computePreviousSize,
  getDefaultOrMinHeight,
  defaultWidth,
  defaultHeight,
} from '@eclipse-sirius/sirius-components-diagrams';
import { Node } from '@xyflow/react';
import { CustomImageNodeData } from './CustomImageNode.types';

export class CustomImageNodeLayoutHandler implements INodeLayoutHandler<NodeData> {
  canHandle(node: Node<NodeData, DiagramNodeType>) {
    return node.type === 'customImageNode';
  }

  handle(
    _layoutEngine: ILayoutEngine,
    previousDiagram: Diagram | null,
    node: Node<CustomImageNodeData, 'customImageNode'>,
    visibleNodes: Node<NodeData, DiagramNodeType>[],
    _directChildren: Node<NodeData, DiagramNodeType>[],
    _newlyAddedNode: Node<NodeData, DiagramNodeType> | undefined,
    forceWidth?: ForcedDimensions
  ) {
    const nodeMinComputeHeight = 10;
    node.width = forceWidth?.width;
    const nodeHeight = getDefaultOrMinHeight(nodeMinComputeHeight, node);
    node.height = nodeHeight;
    node.draggable = false;
    const previousNode = (previousDiagram?.nodes ?? []).find((previouseNode) => previouseNode.id === node.id);
    const previousDimensions = computePreviousSize(previousNode, node);

    if (node.data.resizedByUser) {
      if (nodeMinComputeHeight > previousDimensions.height) {
        node.height = nodeMinComputeHeight;
      } else {
        node.height = previousDimensions.height;
      }
      //reapply ratio
      const initRatio = (node.data.defaultWidth || defaultWidth) / (node.data.defaultHeight || defaultHeight);
      if (node.width && node.height) {
        const newRatio = node.width / node.height;
        if (initRatio < newRatio) {
          node.height = node.width / initRatio;
        }
      }
    }
  }
}
