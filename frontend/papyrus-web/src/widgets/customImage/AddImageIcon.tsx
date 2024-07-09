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
 *  Titouan BOUETE-GIRAUD (Artal Technologies) - Issue 210
 *****************************************************************************/

import SvgIcon, { SvgIconProps } from '@mui/material/SvgIcon';

export const AddImageIcon = (props: SvgIconProps) => {
  return (
    <SvgIcon
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 24 24"
      aria-labelledby="title"
      aria-describedby="desc"
      role="img"
      {...props}>
      <g>
        <rect fill="none" height="24" width="24" />
      </g>
      <g>
        <path d="M 5 5 h 14 v 2 h 2 V 5 c 0 -1.1 -0.89 -2 -2 -2 H 5 C 3.9 3 3 3.9 3 5 v 2 h 2 v -2 M 11 19 h 2 v -9 L 15.59 12.59 L 17 11 L 12 6 L 7 11 L 8.59 12.59 L 11 10 z" />
      </g>
    </SvgIcon>
  );
};
