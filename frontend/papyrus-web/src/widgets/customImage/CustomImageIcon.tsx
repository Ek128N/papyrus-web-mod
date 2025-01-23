/*****************************************************************************
 * Copyright (c) 2023, 2024 CEA LIST, Obeo, Artal Technologies.
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

export const CustomImageIcon = (props: SvgIconProps) => {
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
        <path d="M 19 19 H 5 V 5 h 14 v 14 h 2 V 5 c 0 -1.1 -0.89 -2 -2 -2 H 5 C 3.9 3 3 3.9 3 5 v 14 c 0 1.1 0.9 2 2 2 h 14 c 1.11 0 2 -0.9 2 -2 h -2 M 19 14 L 15 10 L 11 16 L 8 13 L 5 16 L 5 18 L 8 15 L 11 18 L 15 12 L 19 16 M 11 7 A 1 1 0 0 0 11 9 A 1 1 0 0 0 11 7z" />
      </g>
    </SvgIcon>
  );
};
