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

import { ServerContext, ServerContextValue } from '@eclipse-sirius/sirius-components-core';
import { makeStyles } from '@material-ui/core/styles';
import { useContext } from 'react';

interface IconURLProps {
  iconURL: string[];
  alt: string;
}

const useStyles = makeStyles({
  iconContainer: {
    position: 'relative',
    width: '16px',
    height: '16px',
  },
  icon: {
    position: 'absolute',
    top: 0,
    left: 0,
  },
});

export const IconURL = ({ iconURL, alt }: IconURLProps) => {
  const classes = useStyles();
  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);

  return (
    iconURL?.length > 0 && (
      <div className={classes.iconContainer}>
        {iconURL.map((icon, index) => (
          <img
            height="16"
            width="16"
            key={index}
            alt={alt}
            src={httpOrigin + icon}
            className={classes.icon}
            style={{ zIndex: index }}
          />
        ))}
      </div>
    )
  );
};
