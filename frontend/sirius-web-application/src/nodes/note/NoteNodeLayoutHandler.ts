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
 ******************************************************************************/
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

const rectangularNodePadding = 20;

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
    const nodeElement = document.getElementById(`${node.id}-rectangularNode-${nodeIndex}`)?.children[0];
    const borderWidth = nodeElement ? parseFloat(window.getComputedStyle(nodeElement).borderWidth) : 0;

    const labelElement = document.getElementById(`${node.id}-label-${nodeIndex}`);

    const labelWidth =
      rectangularNodePadding +
      (labelElement?.getBoundingClientRect().width ?? 0) +
      rectangularNodePadding +
      borderWidth * 2;
    const labelHeight =
      rectangularNodePadding + (labelElement?.getBoundingClientRect().height ?? 0) + rectangularNodePadding;

    const nodeWidth = labelWidth;
    const nodeHeight = labelHeight + borderWidth * 2;

    const minNodeWith = forceWidth ?? getNodeOrMinWidth(nodeWidth, node); // WARN: not sure yet for the forceWidth to be here.
    const minNodeheight = getNodeOrMinHeight(nodeHeight, node);

    const previousNode = (previousDiagram?.nodes ?? []).find((previouseNode) => previouseNode.id === node.id);
    const previousDimensions = computePreviousSize(previousNode, node);
    if (node.data.nodeDescription?.userResizable) {
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
