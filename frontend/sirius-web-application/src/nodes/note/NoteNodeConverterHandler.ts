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
 *******************************************************************************/
import {
  BorderNodePositon,
  ConnectionHandle,
  GQLDiagram,
  GQLEdge,
  GQLNode,
  GQLNodeDescription,
  GQLNodeStyle,
  GQLViewModifier,
  IConvertEngine,
  INodeConverterHandler,
  convertHandles,
  convertLabelStyle,
  convertLineStyle,
} from '@eclipse-sirius/sirius-components-diagrams-reactflow';
import { Node, XYPosition } from 'reactflow';
import { GQLNoteNodeStyle, NoteNodeData } from './NoteNode.types';

const defaultPosition: XYPosition = { x: 0, y: 0 };

const toNoteNode = (
  gqlDiagram: GQLDiagram,
  gqlNode: GQLNode<GQLNoteNodeStyle>,
  gqlParentNode: GQLNode<GQLNodeStyle> | null,
  nodeDescription: GQLNodeDescription | undefined,
  isBorderNode: boolean,
  gqlEdges: GQLEdge[]
): Node<NoteNodeData> => {
  const {
    targetObjectId,
    targetObjectLabel,
    targetObjectKind,
    descriptionId,
    id,
    insideLabel,
    state,
    style,
    labelEditable,
  } = gqlNode;

  const connectionHandles: ConnectionHandle[] = convertHandles(gqlNode, gqlEdges);
  const isNew = gqlDiagram.layoutData.nodeLayoutData.find((nodeLayoutData) => nodeLayoutData.id === id) === undefined;

  const data: NoteNodeData = {
    targetObjectId,
    targetObjectLabel,
    targetObjectKind,
    descriptionId,
    style: {
      display: 'flex',
      backgroundColor: style.color,
      borderColor: style.borderColor,
      borderWidth: style.borderSize,
      borderStyle: convertLineStyle(style.borderStyle),
    },
    label: undefined,
    faded: state === GQLViewModifier.Faded,
    isBorderNode: isBorderNode,
    nodeDescription,
    defaultWidth: gqlNode.defaultWidth,
    defaultHeight: gqlNode.defaultHeight,
    borderNodePosition: isBorderNode ? BorderNodePositon.EAST : null,
    connectionHandles,
    labelEditable,
    isNew,
  };

  if (insideLabel) {
    const labelStyle = insideLabel.style;
    data.label = {
      id: insideLabel.id,
      text: insideLabel.text,
      isHeader: insideLabel.isHeader,
      style: {
        display: 'flex',
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'center',
        padding: '8px 20px',
        textAlign: 'center',
        ...convertLabelStyle(labelStyle),
      },
      iconURL: labelStyle.iconURL,
    };

    data.style = {
      ...data.style,
      display: 'flex',
      flexDirection: 'column',
      justifyContent: 'flex-start',
    };
    data.label.style = { ...data.label.style, justifyContent: 'top' };
  }

  const node: Node<NoteNodeData> = {
    id,
    type: 'noteNode',
    data,
    position: defaultPosition,
    hidden: gqlNode.state === GQLViewModifier.Hidden,
  };

  if (gqlParentNode) {
    node.parentNode = gqlParentNode.id;
  }

  const nodeLayoutData = gqlDiagram.layoutData.nodeLayoutData.filter((data) => data.id === id)[0];
  if (nodeLayoutData) {
    const {
      position,
      size: { height, width },
    } = nodeLayoutData;
    node.position = position;
    node.height = height;
    node.width = width;
    node.style = {
      ...node.style,
      width: `${node.width}px`,
      height: `${node.height}px`,
    };
  }

  return node;
};

export class NoteNodeConverterHandler implements INodeConverterHandler {
  canHandle(gqlNode: GQLNode<GQLNodeStyle>) {
    return gqlNode.style.__typename === 'NoteNodeStyle';
  }

  handle(
    convertEngine: IConvertEngine,
    gqlDiagram: GQLDiagram,
    gqlNode: GQLNode<GQLNoteNodeStyle>,
    gqlEdges: GQLEdge[],
    parentNode: GQLNode<GQLNodeStyle> | null,
    isBorderNode: boolean,
    nodes: Node[],
    nodeDescriptions: GQLNodeDescription[]
  ) {
    const nodeDescription = this.findNodeDescription(nodeDescriptions, gqlNode.descriptionId);
    nodes.push(toNoteNode(gqlDiagram, gqlNode, parentNode, nodeDescription, isBorderNode, gqlEdges));
    convertEngine.convertNodes(
      gqlDiagram,
      gqlNode.borderNodes ?? [],
      gqlNode,
      nodes,
      nodeDescription?.borderNodeDescriptions ?? []
    );
    convertEngine.convertNodes(
      gqlDiagram,
      gqlNode.childNodes ?? [],
      gqlNode,
      nodes,
      nodeDescription?.childNodeDescriptions ?? []
    );
  }

  findNodeDescription(nodeDescriptions: GQLNodeDescription[], id: string): GQLNodeDescription | null {
    for (let nodeDescription of nodeDescriptions) {
      if (nodeDescription.id === id) {
        return nodeDescription;
      }
      if (nodeDescription.childNodeDescriptions) {
        let subNodeDescription = this.findNodeDescription(nodeDescription.childNodeDescriptions, id);
        if (subNodeDescription !== null) {
          return subNodeDescription;
        }
      }
    }
    return null;
  }
}
