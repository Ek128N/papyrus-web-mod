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

import { getCSSColor, ServerContext, ServerContextValue, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import {
  useConnectorNodeStyle,
  useDropNodeStyle,
  DiagramContext,
  DiagramContextValue,
} from '@eclipse-sirius/sirius-components-diagrams';
import { Theme, useTheme } from '@mui/material/styles';
// import { ResizeControlVariant } from '@xyflow/system';
import Typography from '@mui/material/Typography';
import { Edge, Node, NodeProps, useStoreApi } from '@xyflow/react';
import { memo, useContext, useEffect, useState } from 'react';
import { CustomImageNodeData, NodeComponentsMap } from './CustomImageNode.types';
import { EdgeData, NodeData } from '@eclipse-sirius/sirius-components-diagrams';

// const customImageStyle = (theme: Theme, style: React.CSSProperties): React.CSSProperties => {
//   return {
//     display: 'flex',
//     padding: '0px',
//     boxSizing: 'border-box',
//     width: '100%',
//     height: '100%',
//     border: 'none',
//     background: getCSSColor(String(style.background), theme),
//     alignItems: 'center',
//     justifyContent: 'center',
//     verticalAlign: 'middle',
//   };
// };

const defaultErrorMessage = 'The provided shape for this node is not a valid image';

interface CustomImageNodeState {
  url: string;
  validImage: boolean;
}

const customImageNodeStyle = (
  theme: Theme,
  style: React.CSSProperties,
  hovered: boolean,
  faded: boolean
): React.CSSProperties => {
  const customImageNodeStyle: React.CSSProperties = {
    display: 'flex',
    padding: '0px',
    boxSizing: 'border-box',
    width: '100%',
    height: '100%',
    opacity: faded ? '0.4' : '',
    ...style,
    // border: 'none',
    flexDirection: 'row',
    background: getCSSColor(String(style.background), theme),
    alignItems: 'center',
    justifyContent: 'center',
  };

  return customImageNodeStyle;
};

export const CustomImageNode: NodeComponentsMap['customImageNode'] = memo(
  ({ data, id, dragging }: NodeProps<Node<CustomImageNodeData>>) => {
    const { readOnly } = useContext<DiagramContextValue>(DiagramContext);
    const theme = useTheme();
    const { addErrorMessage } = useMultiToast();
    const { style: connectionFeedbackStyle } = useConnectorNodeStyle(id, data.nodeDescription.id);
    const { style: dropFeedbackStyle } = useDropNodeStyle(data.isDropNodeTarget, data.isDropNodeCandidate, dragging);
    const { httpOrigin } = useContext<ServerContextValue>(ServerContext);
    const [state, setState] = useState<CustomImageNodeState>({
      url: data.shape && data.shape !== '' ? httpOrigin + data.shape : '',
      validImage: data.shape !== undefined && data.shape !== '',
    });

    const onErrorLoadingImage = () => {
      setState((prevState) => ({ ...prevState, validImage: false }));
      addErrorMessage(defaultErrorMessage);
    };

    const storeApi = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
    const getNodeById = (id: string) => storeApi.getState().nodeLookup.get(id);
    const node = getNodeById(id);
    const parentNode = getNodeById(node.parentId);

    useEffect(() => {
      setState((prevState) => ({
        ...prevState,
        url: data.shape && data.shape !== '' ? httpOrigin + data.shape : '',
        validImage: data.shape !== undefined && data.shape !== '',
      }));
    }, [data.shape]);

    return (
      <>
        {!readOnly ? <>{}</> : null}
        <div
          style={{
            ...customImageNodeStyle(theme, data.style, data.isHovered, data.faded),
            ...connectionFeedbackStyle,
            ...dropFeedbackStyle,
          }}>
          {state.validImage ? (
            <img
              id={id}
              src={state.url}
              width={parentNode.width - 5}
              height={node.height - 5}
              box-sizing={'border-box'}
              draggable={false}
              justify-content={'center'}
              object-fit={'contain'}
              onError={onErrorLoadingImage}
            />
          ) : (
            <Typography data-testid="custom-image-node-no-image" variant="caption">
              No image
            </Typography>
          )}
        </div>
      </>
    );
  }
);
