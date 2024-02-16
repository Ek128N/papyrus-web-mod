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
import { NodeProps, NodeResizer } from 'reactflow';
import { RectangleWithExternalLabelNodeData } from './RectangleWithExternalLabelNode.types';

const rectangleWithExternalLabelInnerRectangleStyle = (
  theme: Theme,
  style: React.CSSProperties,
  selected: boolean,
  hovered: boolean,
  faded: boolean
): React.CSSProperties => {
  const rectangleWithExternalLabelNodeStyle: React.CSSProperties = {
    display: 'flex',
    padding: '8px',
    width: '100%',
    height: '100%',
    opacity: faded ? '0.4' : '',
    ...style,
    borderColor: getCSSColor(String(style.borderColor), theme),
    backgroundColor: getCSSColor(String(style.backgroundColor), theme),
  };

  if (selected || hovered) {
    rectangleWithExternalLabelNodeStyle.outline = `${theme.palette.selected} solid 1px`;
  }

  return rectangleWithExternalLabelNodeStyle;
};

const rectangleWithExternalLabelNodeStyle = (
  theme: Theme,
  style: React.CSSProperties,
  selected: boolean,
  hovered: boolean,
  faded: boolean
): React.CSSProperties => {
  const rectangleWithExternalLabelNodeStyle: React.CSSProperties = {
    display: 'flex',
    padding: '0px',
    width: '100%',
    height: '100%',
    opacity: faded ? '0.4' : '',
    ...style,
    border: 'none',
    backgroundColor: 'transparent',
  };

  if (selected || hovered) {
    rectangleWithExternalLabelNodeStyle.outline = `${theme.palette.selected} solid 1px`;
  }

  return rectangleWithExternalLabelNodeStyle;
};

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

export const RectangleWithExternalLabelNode = memo(
  ({ data, id, selected }: NodeProps<RectangleWithExternalLabelNodeData>) => {
    const { readOnly } = useContext<DiagramContextValue>(DiagramContext);
    const theme = useTheme();
    const { onDrop, onDragOver } = useDrop();
    const { newConnectionStyleProvider } = useConnector();
    const { style: dropFeedbackStyle } = useDropNodeStyle(id);
    const { hoveredNode } = useContext<NodeContextValue>(NodeContext);

    const handleOnDrop = (event: React.DragEvent) => {
      onDrop(event, id);
    };

    useRefreshConnectionHandles(id, data.connectionHandles);

    return (
      <>
        {data.nodeDescription?.userResizable && !readOnly ? (
          <NodeResizer
            handleStyle={{ ...resizeHandleStyle(theme) }}
            lineStyle={{ ...resizeLineStyle(theme) }}
            color={theme.palette.selected}
            isVisible={selected}
            // Force false here to handle mutualized NodeDescriptions for SMD PseudoStates.
            // The other nodes need to have the aspect ratio, but RectanguleWithExternalLabelNodes
            // should not have it.
            keepAspectRatio={false}
          />
        ) : null}
        <div
          style={{
            ...rectangleWithExternalLabelNodeStyle(theme, data.style, selected, hoveredNode?.id === id, data.faded),
            ...newConnectionStyleProvider.getNodeStyle(id, data.descriptionId),
            ...dropFeedbackStyle,
          }}
          onDragOver={onDragOver}
          onDrop={handleOnDrop}
          data-testid={`RectangleWithExternalLabel - ${data?.insideLabel?.text}`}>
          {data.insideLabel ? (
            <Label diagramElementId={id} label={data.insideLabel} faded={data.faded} transform="" />
          ) : null}
          {selected ? <ConnectionCreationHandles nodeId={id} /> : null}
          <ConnectionTargetHandle nodeId={id} nodeDescription={data.nodeDescription} />
          <ConnectionHandles connectionHandles={data.connectionHandles} />
          <div
            style={{
              ...rectangleWithExternalLabelInnerRectangleStyle(
                theme,
                data.style,
                selected,
                hoveredNode?.id === id,
                data.faded
              ),
              ...newConnectionStyleProvider.getNodeStyle(id, data.descriptionId),
              ...dropFeedbackStyle,
            }}
            onDragOver={onDragOver}
            onDrop={handleOnDrop}
            data-testid={`RectangleWithExternalLabel - ${data?.insideLabel?.text}`}>
            {selected ? (
              <DiagramElementPalette diagramElementId={id} labelId={data.insideLabel ? data.insideLabel.id : null} />
            ) : null}
          </div>
        </div>
      </>
    );
  }
);
