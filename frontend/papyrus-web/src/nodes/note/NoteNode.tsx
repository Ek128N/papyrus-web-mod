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

import { getCSSColor } from '@eclipse-sirius/sirius-components-core';
import {
  ConnectionCreationHandles,
  ConnectionHandles,
  ConnectionTargetHandle,
  DiagramContext,
  DiagramContextValue,
  DiagramElementPalette,
  Label,
  NodeContext,
  NodeContextValue,
  useConnector,
  useDrop,
  useDropNodeStyle,
  useRefreshConnectionHandles,
} from '@eclipse-sirius/sirius-components-diagrams';
import { Theme, useTheme } from '@material-ui/core/styles';
import React, { memo, useContext } from 'react';
import { NodeProps, NodeResizer, useReactFlow } from 'reactflow';
import { NoteNodeData } from './NoteNode.types';

const resizeLineStyle = (theme: Theme): React.CSSProperties => {
  return { borderWidth: theme.spacing(0.15) };
};

const resizeHandleStyle = (theme: Theme): React.CSSProperties => {
  return {
    width: theme.spacing(1),
    height: theme.spacing(1),
    borderRadius: '100%',
  };
};

const noteNodeStyle = (
  theme: Theme,
  style: React.CSSProperties,
  selected: boolean,
  hovered: boolean,
  faded: boolean
): React.CSSProperties => {
  const noteNodeStyle: React.CSSProperties = {
    display: 'flex',
    padding: '0px',
    width: '100%',
    height: '100%',
    opacity: faded ? '0.4' : '',
    ...style,
    // No border nor background color: this is handled by the SVG image
    border: 'none',
    backgroundColor: 'transparent',
  };

  if (selected || hovered) {
    noteNodeStyle.outline = `${theme.palette.selected} solid 1px`;
  }

  return noteNodeStyle;
};

const svgPathStyle = (theme: Theme, style: React.CSSProperties, faded: boolean): React.CSSProperties => {
  const svgPathStyle: React.CSSProperties = {
    stroke: getCSSColor(String(style.borderColor), theme),
    fill: getCSSColor(String(style.backgroundColor), theme),
    fillOpacity: faded ? '0.4' : '1',
    strokeOpacity: faded ? '0.4' : '1',
    strokeWidth: style.borderWidth,
    vectorEffect: 'non-scaling-stroke',
  };
  return svgPathStyle;
};

export const NoteNode = memo(({ data, id, selected }: NodeProps<NoteNodeData>) => {
  const { readOnly } = useContext<DiagramContextValue>(DiagramContext);
  const theme = useTheme();
  const { onDrop, onDragOver } = useDrop();
  const { newConnectionStyleProvider } = useConnector();
  const { style: dropFeedbackStyle } = useDropNodeStyle(id);
  const { hoveredNode } = useContext<NodeContextValue>(NodeContext);
  const { getNodes } = useReactFlow<NoteNodeData>();
  const node = getNodes().find((node) => node.id === id);

  const handleOnDrop = (event: React.DragEvent) => {
    onDrop(event, id);
  };

  const updatedLabel: any = {
    ...data.insideLabel,
    style: {
      ...data?.insideLabel?.style,
      paddingLeft: parseInt(data.style.borderWidth?.toString() ?? '0') + 8 + 'px',
      paddingTop: parseInt(data.style.borderWidth?.toString() ?? '0') + 8 + 'px',
      paddingRight: parseInt(data.style.borderWidth?.toString() ?? '1') / 2 + 20 + 'px',
      paddingBottom: parseInt(data.style.borderWidth?.toString() ?? '0') + 8 + 'px',
    },
  };

  useRefreshConnectionHandles(id, data.connectionHandles);

  const borderOffset = data.style.borderWidth ? parseInt(data.style.borderWidth.toString()) / 2 : 0;

  return (
    <>
      {data.nodeDescription?.userResizable && !readOnly ? (
        <NodeResizer
          handleStyle={{ ...resizeHandleStyle(theme) }}
          lineStyle={{ ...resizeLineStyle(theme) }}
          color={theme.palette.selected}
          isVisible={selected}
          keepAspectRatio={data.nodeDescription?.keepAspectRatio}
        />
      ) : null}
      <div
        style={{
          ...noteNodeStyle(theme, data.style, selected, hoveredNode?.id === id, data.faded),
          ...newConnectionStyleProvider.getNodeStyle(id, data.descriptionId),
          ...dropFeedbackStyle,
        }}
        onDragOver={onDragOver}
        onDrop={handleOnDrop}
        data-testid={`Note - ${data?.insideLabel?.text}`}>
        <div style={{ position: 'absolute', width: '100%', height: '100%' }}>
          {data.insideLabel ? (
            <Label diagramElementId={id} label={updatedLabel} faded={data.faded} transform="" />
          ) : null}
        </div>
        {selected ? (
          <DiagramElementPalette diagramElementId={id} labelId={data.insideLabel ? data.insideLabel.id : null} />
        ) : null}
        {selected ? <ConnectionCreationHandles nodeId={id} /> : null}
        <ConnectionTargetHandle nodeId={id} nodeDescription={data.nodeDescription} />
        <ConnectionHandles connectionHandles={data.connectionHandles} />
        <svg viewBox={`0 0 ${node.width} ${node.height}`}>
          <path
            style={svgPathStyle(theme, data.style, data.faded)}
            d={`M ${borderOffset},${borderOffset} H ${node.width - 15} L ${node.width - borderOffset} 15 V ${
              node.height - borderOffset
            } H ${borderOffset} Z`}
          />
          <path
            style={{
              ...svgPathStyle(theme, data.style, data.faded),
              fillOpacity: 0,
            }}
            d={`M ${node.width - 15},${borderOffset} V 15 H ${node.width - borderOffset}`}
          />
        </svg>
      </div>
    </>
  );
});
