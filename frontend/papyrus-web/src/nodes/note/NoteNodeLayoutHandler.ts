/*****************************************************************************
 * Copyright (c) 2023, 2024 CEA LIST, Obeo.
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
 *****************************************************************************/
import {
  Diagram,
  DiagramNodeType,
  ILayoutEngine,
  INodeLayoutHandler,
  NodeData,
  applyRatioOnNewNodeSizeValue,
  computePreviousSize,
  findNodeIndex,
  getNodeOrMinHeight,
  getNodeOrMinWidth,
} from '@eclipse-sirius/sirius-components-diagrams-reactflow';
import { Node } from 'reactflow';
import { NoteNodeData } from './NoteNode.types';
import { getHeaderFootprint } from '@eclipse-sirius/sirius-components-diagrams-reactflow';

export class NoteNodeLayoutHandler implements INodeLayoutHandler<NodeData> {
  canHandle(node: Node<NodeData, DiagramNodeType>) {
    return node.type === 'noteNode';
  }

  handle(
    _layoutEngine: ILayoutEngine,
    previousDiagram: Diagram | null,
    node: Node<NoteNodeData, 'noteNode'>,
    visibleNodes: Node<NodeData, DiagramNodeType>[],
    _directChildren: Node<NodeData, DiagramNodeType>[],
    _newlyAddedNode: Node<NodeData, DiagramNodeType> | undefined,
    forceWidth?: number
  ) {
    const nodeIndex = findNodeIndex(visibleNodes, node.id);

    const nodeElement = document.getElementById(`${node.id}-noteNode-${nodeIndex}`)?.children[0];
    const borderWidth = nodeElement ? parseFloat(window.getComputedStyle(nodeElement).borderWidth) : 0;

    const labelElement = document.getElementById(`${node.id}-label-${nodeIndex}`);

    const labelWidth = (labelElement?.getBoundingClientRect().width ?? 0) + borderWidth * 2 + 8 + 20;
    const labelHeight = getHeaderFootprint(labelElement, true, false);

    const nodeWidth = labelWidth;
    const nodeHeight = labelHeight + borderWidth * 2;

    const minNodeWith = forceWidth ?? getNodeOrMinWidth(nodeWidth, node); // WARN: not sure yet for the forceWidth to be here.
    const minNodeheight = getNodeOrMinHeight(nodeHeight, node);

    const previousNode = (previousDiagram?.nodes ?? []).find((previouseNode) => previouseNode.id === node.id);
    const previousDimensions = computePreviousSize(previousNode, node);
    if (node.data.resizedByUser) {
      if (minNodeWith > previousDimensions.width) {
        node.width = minNodeWith;
      } else {
        node.width = previousDimensions.width;
      }
      if (minNodeheight > previousDimensions.height) {
        node.height = minNodeheight;
      } else {
        node.height = previousDimensions.height;
      }
    } else {
      node.width = minNodeWith;
      node.height = minNodeheight;
    }

    if (node.data.nodeDescription?.keepAspectRatio) {
      applyRatioOnNewNodeSizeValue(node);
    }
  }
}
