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
 ***************************************************************************/

import SvgIcon, { SvgIconProps } from '@mui/material/SvgIcon';

export const ContainmentReferenceIcon = (props: SvgIconProps) => {
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
        <polygon
          points="17,17 22,12 17,7 15.59,8.41 18.17,11 9,11 9,13 18.17,13 15.59,15.59"
          transform="matrix(-1,0,0,1,28.981308,0)"
        />
        <path d="M19,19H5V5h14v2h2V5c0-1.1-0.89-2-2-2H5C3.9,3,3,3.9,3,5v14c0,1.1,0.9,2,2,2h14c1.11,0,2-0.9,2-2v-2h-2V19z" />
      </g>
    </SvgIcon>
  );
};
