/*****************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo.
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

import SvgIcon, { SvgIconProps } from '@material-ui/core/SvgIcon';

export const LanguageExpressionIcon = (props: SvgIconProps) => {
  return (
    <SvgIcon
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 24 24"
      aria-labelledby="title"
      aria-describedby="desc"
      role="img"
      {...props}>
      <g>
        <title>language Expression icon</title>
        <path d="m3,3.01l0.01,12.96l6.98,-0.01l-0.01,-2.47l-4.49,0l0.01,-10.49l-2.51,0.01z" fill="#333333" />
        <path
          d="m13.02,21.49l7.97,0l0,-2.48l-4.99,0l-0.02,-3l3.55,0l0,-2.48l-3.55,-0.01l0.03,-2.52l4.98,0l0,-2.5l-8,0l0.03,12.99z"
          fill="#333333"
        />
      </g>
    </SvgIcon>
  );
};
