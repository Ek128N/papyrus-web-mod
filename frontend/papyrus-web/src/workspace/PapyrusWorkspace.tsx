/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *	Pascal Bannerot (CEA LIST) <pascal.bannerot@cea.fr> - workspace management
 *******************************************************************************/

import { useState, useEffect } from 'react';
import ExitToAppRoundedIcon from '@mui/icons-material/ExitToAppRounded';
import { emphasize } from '@mui/material/styles';
import { makeStyles } from 'tss-react/mui';
import IconButton from '@mui/material/IconButton';
import Link from '@mui/material/Link';
import { httpOrigin } from '../core/URL';
import Tooltip from '@mui/material/Tooltip';

const useWorkspaceStyle = makeStyles()((theme) => ({
  onDarkBackground: {
    '&:hover': {
      backgroundColor: emphasize(theme.palette.secondary.main, 0.08),
    },
  },
}));

const UserConnected = ({ setUserInfo, userInfo }) => {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const { classes } = useWorkspaceStyle();

  useEffect(() => {
    fetch('/isConnected')
      .then((response) => {
        if (!response.ok) {
          throw new Error('Network response was not ok ' + response.statusText);
        }
        return response.json();
      })
      .then((data) => {
        setData(data.url);
        setLoading(false);
      })
      .catch((error) => {
        setError(error);
        setLoading(false);
      });
  }, []);

  if (loading) {
    return <div>Loading...</div>;
  }

  if (error) {
    console.log(error.message + ': ' + data);
    return <></>;
  }

  return (
    <Link href={`${httpOrigin}/logout`} rel="noopener noreferrer" target="_self" color="inherit">
      <Tooltip title="Logout" placement="bottom">
        <IconButton className={classes.onDarkBackground} color="inherit">
          <ExitToAppRoundedIcon aria-label="logout" />
        </IconButton>
      </Tooltip>
    </Link>
  );
};

export const PapyrusWorkspace = () => {
  const [userInfo, setUserInfo] = useState('');
  return <UserConnected userInfo={userInfo} setUserInfo={setUserInfo} />;
};
