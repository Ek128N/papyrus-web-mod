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

import { getCSSColor } from '@eclipse-sirius/sirius-components-core';
import {
  ConnectionCreationHandles,
  ConnectionHandles,
  ConnectionTargetHandle,
  DiagramElementPalette,
  Label,
  useConnector,
  useDrop,
  useDropNodeStyle,
  useRefreshConnectionHandles,
} from '@eclipse-sirius/sirius-components-diagrams-reactflow';
import { Theme, useTheme } from '@material-ui/core/styles';
import React, { memo } from 'react';
import { NodeProps, NodeResizer } from 'reactflow';
import { NoteNodeData } from './NoteNode.types';

const noteNodeStyle = (
  theme: Theme,
  style: React.CSSProperties,
  selected: boolean,
  faded: boolean
): React.CSSProperties => {
  const noteNodeStyle: React.CSSProperties = {
    display: 'flex',
    padding: '0px',
    width: '100%',
    height: '100%',
    opacity: faded ? '0.4' : '',
    ...style,
    borderColor: getCSSColor(String(style.borderColor), theme),
    backgroundColor: getCSSColor(String(style.backgroundColor), theme),
  };

  if (selected) {
    noteNodeStyle.outline = `${theme.palette.primary.main} solid 1px`;
  }

  return noteNodeStyle;
};

export const NoteNode = memo(({ data, id, selected }: NodeProps<NoteNodeData>) => {
  const theme = useTheme();
  const { onDrop, onDragOver } = useDrop();
  const { newConnectionStyleProvider } = useConnector();
  const { style: dropFeedbackStyle } = useDropNodeStyle(id);

  const handleOnDrop = (event: React.DragEvent) => {
    onDrop(event, id);
  };

  const updatedLabel: any = {
    ...data.label,
    style: {
      ...data?.label?.style,
      paddingLeft: '8px',
    },
  };

  useRefreshConnectionHandles(id, data.connectionHandles);

  return (
    <>
      <NodeResizer color={theme.palette.primary.main} isVisible={selected} />
      <div
        style={{
          ...noteNodeStyle(theme, data.style, selected, data.faded),
          ...newConnectionStyleProvider.getNodeStyle(id, data.descriptionId),
          ...dropFeedbackStyle,
        }}
        onDragOver={onDragOver}
        onDrop={handleOnDrop}
        data-testid={`Note - ${data?.label?.text}`}>
        {data.label ? <Label diagramElementId={id} label={updatedLabel} faded={data.faded} transform="" /> : null}
        {selected ? <DiagramElementPalette diagramElementId={id} labelId={data.label ? data.label.id : null} /> : null}
        {selected ? <ConnectionCreationHandles nodeId={id} /> : null}
        <ConnectionTargetHandle nodeId={id} />
        <ConnectionHandles connectionHandles={data.connectionHandles} />
        <div
          style={{
            position: 'absolute',
            top: '1px',
            right: '1px',
            width: '20px',
            height: '20px',
            borderBottom: getCSSColor(String(data.style.borderColor), theme) + ' solid 1px',
            borderLeft: getCSSColor(String(data.style.borderColor), theme) + ' solid 1px',
            borderStyle: data.style.borderStyle,
            borderWidth: data.style.borderWidth,
            borderTopColor: 'transparent',
            borderRight: 'transparent',
          }}></div>
        <div
          style={{
            position: 'absolute',
            top: '0',
            right: '0',
            width: '0',
            height: '0',
            borderTop: theme.palette.background.default + (selected ? ' solid 20px' : ' solid 25px'),
            borderBottom: 'transparent solid 21px',
            borderLeft: selected ? 'transparent solid 20px' : 'transparent solid 25px',
            transform: selected ? 'translate(-0.5px, 0.5px)' : 'translate(2px, -2px)',
          }}></div>
        <div
          style={{
            position: 'absolute',
            top: '0',
            right: '0',
            width: '27px',
            height: data.style.borderWidth,
            // Use data.style.borderColor here because this div acts as a border
            backgroundColor: getCSSColor(String(data.style.borderColor), theme),
            transform: 'rotate(45deg) translate(8.9px, 5px)',
          }}></div>
      </div>
    </>
  );
});
