/*******************************************************************************
 * Copyright (c) 2024 CEA List.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Eeshvaran Dilan - initial API and implementation
 *******************************************************************************/
import { Button, Dialog, DialogActions, DialogTitle } from '@mui/material';
import { useState } from 'react';

import { gql, useMutation, useQuery } from '@apollo/client';

const SHOW_POPUP_QUERY = gql`
  query {
    showPopup
  }
`;

const SET_POPUP_MUTATION = gql`
  mutation setPopup($shouldShow: Boolean!) {
    setPopup(shouldShow: $shouldShow)
  }
`;

export const CodeGenerationPopupMessage = () => {
  const [isOpen, setIsOpen] = useState(false);
  const [setPopup] = useMutation(SET_POPUP_MUTATION);

  /**
   * the page reloads when the generate code button is clicked,
   * so this query is used at the launch of this component to get the current popup state and then to act accordingly.
   */
  const { loading } = useQuery(SHOW_POPUP_QUERY, {
    onCompleted: (data) => {
      if (data?.showPopup === 'Show Popup') {
        setIsOpen(true);
      }
    },
    onError: (error) => {
      console.error('Error fetching popup state:', error);
    },
  });

  const handleClose = () => {
    setIsOpen(false);
    setPopup({ variables: { shouldShow: false } })
      .then(() => console.log('Popup visibility updated'))
      .catch((error) => console.error('Error updating popup visibility:', error));
  };

  // prevents the component from trying to render any content before the data has been fetched
  if (loading) return null;

  return (
    <Dialog open={isOpen} onClose={handleClose} aria-labelledby="dialog-title" maxWidth="xs" fullWidth>
      <DialogTitle id="dialog-title">Function in development !</DialogTitle>
      <DialogActions>
        <Button color="primary" onClick={handleClose}>
          Close
        </Button>
      </DialogActions>
    </Dialog>
  );
};
