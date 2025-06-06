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

import { PropertySectionComponentProps, PropertySectionLabel } from '@eclipse-sirius/sirius-components-forms';

import MuiAccordion from '@mui/material/Accordion';
import MuiAccordionDetails from '@mui/material/AccordionDetails';
import MuiAccordionSummary from '@mui/material/AccordionSummary';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import IconButton from '@mui/material/IconButton';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';
import { useTheme } from '@mui/material/styles';
import { makeStyles, withStyles } from 'tss-react/mui';
import AddIcon from '@mui/icons-material/Add';
import ArrowDownwardIcon from '@mui/icons-material/ArrowDownward';
import ArrowForwardIosSharpIcon from '@mui/icons-material/ArrowForwardIosSharp';
import ArrowUpwardIcon from '@mui/icons-material/ArrowUpward';
import DeleteIcon from '@mui/icons-material/Delete';

import { gql, useMutation } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useEffect, useState } from 'react';
import {
  GQLAddLanguageData,
  GQLAddLanguageInput,
  GQLAddLanguagePayload,
  GQLAddLanguageVariables,
  GQLDeleteLanguageData,
  GQLDeleteLanguagePayload,
  GQLDeleteLanguageVariables,
  GQLEditLanguageBodyData,
  GQLEditLanguageBodyInput,
  GQLEditLanguageBodyVariables,
  GQLErrorPayload,
  GQLLanguageExpression,
  GQLMoveLanguageData,
  GQLMoveLanguageInput,
  GQLMoveLanguageVariables,
  GQLSuccessPayload,
} from './LanguageExpressionFragment.types';

const Accordion = withStyles(MuiAccordion, (theme) => ({
  root: {
    border: `1px solid ${theme.palette.divider}`,
    boxShadow: 'none',
    '&:not(:last-child)': {
      borderBottom: 0,
    },
    '&:before': {
      display: 'none',
    },
    '&$expanded': {
      margin: 'auto',
    },
  },
  expanded: {},
}));

const AccordionSummary = withStyles(MuiAccordionSummary, (theme) => ({
  root: {
    backgroundColor: 'rgba(0, 0, 0, .03)',
    borderBottom: '1px solid rgba(0, 0, 0, .125)',
    marginBottom: -1,
    minHeight: 56,
    paddingLeft: 8,
    flexDirection: 'row-reverse',
    '& .MuiAccordionSummary-expandIcon.Mui-expanded': {
      transform: 'rotate(90deg)',
    },
    '& .MuiIconButton-edgeEnd': {
      marginRight: 0,
    },
    '&$expanded': {
      minHeight: 56,
    },
  },
  content: {
    alignItems: 'center',
    '&$expanded': {
      margin: '12px 0',
    },
  },
  expanded: {},
}));

const AccordionDetails = withStyles(MuiAccordionDetails, (theme) => ({
  root: {
    padding: theme.spacing(1),
    borderTop: '1px solid rgba(0, 0, 0, .125)',
  },
}));

const useStyles = makeStyles()(() => ({
  paper: {
    minWidth: '450px',
  },
}));

export const addLanguageMutation = gql`
  mutation addLanguage($input: AddLanguageInput!) {
    addLanguage(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

export const deleteLanguageMutation = gql`
  mutation deleteLanguage($input: DeleteLanguageInput!) {
    deleteLanguage(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

export const editLanguageBodyMutation = gql`
  mutation editLanguageBody($input: EditLanguageBodyInput!) {
    editLanguageBody(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

export const moveLanguageMutation = gql`
  mutation moveLanguage($input: MoveLanguageInput!) {
    moveLanguage(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;
export const LanguageExpressionSection = ({
  editingContextId,
  formId,
  widget,
  readOnly,
}: PropertySectionComponentProps<GQLLanguageExpression>) => {
  const { addErrorMessage, addMessages } = useMultiToast();
  const { classes } = useStyles();
  const theme = useTheme();
  const [expanded, setExpanded] = useState<string | false>(false);
  const [addLanguageDialogOpen, setAddLanguageDialogOpen] = useState(false);
  const [selectedPredefinedLanguage, setSelectedPredefinedLanguage] = useState('');
  const [newLanguage, setNewLanguage] = useState('');
  const [currentBody, setCurrentBody] = useState('');
  const [knownLanguagesSelected, setKnownLanguagesSelected] = useState(false);

  const [addLanguageApi, { loading: addLanguageLoading, data: addLanguageData, error: addLanguageError }] = useMutation<
    GQLAddLanguageData,
    GQLAddLanguageVariables
  >(addLanguageMutation);

  const [deleteLanguageApi, { loading: deleteLanguageLoading, data: deleteLanguageData, error: deleteLanguageError }] =
    useMutation<GQLDeleteLanguageData, GQLDeleteLanguageVariables>(deleteLanguageMutation);

  const [
    editLanguageBodyApi,
    { loading: editLanguageBodyLoading, data: editLanguageBodyData, error: editLanguageBodyError },
  ] = useMutation<GQLEditLanguageBodyData, GQLEditLanguageBodyVariables>(editLanguageBodyMutation);

  const [moveLanguageApi, { loading: moveLanguageLoading, data: moveLanguageData, error: moveLanguageError }] =
    useMutation<GQLMoveLanguageData, GQLMoveLanguageVariables>(moveLanguageMutation);

  const isErrorPayload = (payload: GQLAddLanguagePayload | GQLDeleteLanguagePayload): payload is GQLErrorPayload =>
    payload.__typename === 'ErrorPayload';
  const isSuccessPayload = (payload: GQLAddLanguagePayload | GQLDeleteLanguagePayload): payload is GQLSuccessPayload =>
    payload.__typename === 'SuccessPayload';

  useEffect(() => {
    if (!addLanguageLoading) {
      if (addLanguageError) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (addLanguageData) {
        const { addLanguage } = addLanguageData;
        if (isErrorPayload(addLanguage) || isSuccessPayload(addLanguage)) {
          addMessages(addLanguage.messages);
        }
      }
    }
  }, [addLanguageLoading, addLanguageError, addLanguageData]);

  useEffect(() => {
    if (!deleteLanguageLoading) {
      if (deleteLanguageError) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (deleteLanguageData) {
        const { deleteLanguage } = deleteLanguageData;
        if (isErrorPayload(deleteLanguage) || isSuccessPayload(deleteLanguage)) {
          addMessages(deleteLanguage.messages);
        }
      }
    }
  }, [deleteLanguageLoading, deleteLanguageError, deleteLanguageData]);

  useEffect(() => {
    if (!editLanguageBodyLoading) {
      if (editLanguageBodyError) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (editLanguageBodyData) {
        const { editLanguageBody } = editLanguageBodyData;
        if (isErrorPayload(editLanguageBody) || isSuccessPayload(editLanguageBody)) {
          addMessages(editLanguageBody.messages);
        }
      }
    }
  }, [editLanguageBodyLoading, editLanguageBodyError, editLanguageBodyData]);

  useEffect(() => {
    if (!moveLanguageLoading) {
      if (moveLanguageError) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (moveLanguageData) {
        const { moveLanguage } = moveLanguageData;
        if (isErrorPayload(moveLanguage) || isSuccessPayload(moveLanguage)) {
          addMessages(moveLanguage.messages);
        }
      }
    }
  }, [moveLanguageLoading, moveLanguageError, moveLanguageData]);

  const handlePanelExpansion = (panel: string) => (event: React.SyntheticEvent, expanded: boolean) => {
    setExpanded(expanded ? panel : false);
    if (expanded) {
      // language body is now visible
      setCurrentBody(getLanguage(panel)?.body);
    }
  };

  const handleDeleteLanguage = (language: string) => {
    const input: GQLAddLanguageInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: formId,
      languageExpressionId: widget.id,
      language,
    };
    const variables: GQLAddLanguageVariables = { input };
    deleteLanguageApi({ variables });
  };

  // Move language operations

  const moveLanguage = (language, direction) => {
    const input: GQLMoveLanguageInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: formId,
      languageExpressionId: widget.id,
      language: language.label,
      direction,
    };
    const variables: GQLMoveLanguageVariables = { input };
    moveLanguageApi({ variables });
  };

  const moveLanguageUp = (language) => moveLanguage(language, 'BACKWARD');
  const moveLanguageDown = (language) => moveLanguage(language, 'FORWARD');

  // Add language dialog

  const handleAddLanguage = () => {
    closeAddLanguageDialog();
    const language = knownLanguagesSelected ? selectedPredefinedLanguage : newLanguage;
    const input: GQLAddLanguageInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: formId,
      languageExpressionId: widget.id,
      language,
    };
    const variables: GQLAddLanguageVariables = { input };
    addLanguageApi({ variables });
  };

  const openAddLanguageDialog = () => {
    setSelectedPredefinedLanguage('');
    setNewLanguage('');
    setAddLanguageDialogOpen(true);
  };

  const closeAddLanguageDialog = () => {
    setKnownLanguagesSelected(false);
    setAddLanguageDialogOpen(false);
  };

  const handlePredefinedLanguageSelected = (_: React.MouseEvent<HTMLDivElement, MouseEvent>, language: string) => {
    setSelectedPredefinedLanguage(language);
  };

  const handleNewLanguageChange = (event) => {
    setNewLanguage(event.target.value);
  };

  const handleKnownLanguagesFocusGained = () => {
    setKnownLanguagesSelected(true);
    setNewLanguage('');
  };

  const handleNewLanguageFocusGained = () => {
    setKnownLanguagesSelected(false);
    setSelectedPredefinedLanguage('');
  };

  const isAlreadyExisting = (language: string): boolean => {
    return widget.languages.find((l) => l.label === language) !== undefined;
  };

  const handleLanguageBodyChange = (language: string) => (event: React.ChangeEvent<HTMLInputElement>) => {
    setCurrentBody(event.target.value);
  };

  const getLanguage = (label) => {
    const language = widget.languages.find((l) => l.label === label);
    if (!language) {
      addErrorMessage('Language has changed, please refresh the page');
    } else return language;
  };

  const handleLanguageBodyFocusLost =
    (lang: string) => (event: React.FocusEvent<HTMLInputElement | HTMLTextAreaElement>) => {
      const language = getLanguage(lang);
      if (language?.body !== event.target.value) {
        const input: GQLEditLanguageBodyInput = {
          id: crypto.randomUUID(),
          editingContextId,
          representationId: formId,
          languageExpressionId: widget.id,
          language: language.label,
          newBody: event.target.value,
        };
        const variables: GQLEditLanguageBodyVariables = { input };
        editLanguageBodyApi({ variables });
      }
    };

  return (
    <div data-testid="language-expression-widget">
      <div style={{ display: 'flex' }}>
        <PropertySectionLabel
          editingContextId={editingContextId}
          formId={formId}
          widget={widget}
          data-testid={widget.label}
        />
        <IconButton
          data-testid="le-open-add-language-dialog"
          onClick={openAddLanguageDialog}
          disabled={readOnly || widget.readOnly}
          style={{ marginLeft: 'auto' }}>
          <AddIcon />
        </IconButton>
      </div>
      {widget.languages.map((lang, index) => {
        return (
          <Accordion
            key={index}
            expanded={expanded === lang.label}
            onChange={handlePanelExpansion(lang.label)}
            square
            data-testid={`le-language-${lang.label}`}
            elevation={0}>
            <AccordionSummary
              aria-controls="panel1d-content"
              expandIcon={<ArrowForwardIosSharpIcon style={{ fontSize: '0.9rem' }} />}
              id="panel1d-header">
              <Typography>{lang.label}</Typography>
              <Box sx={{ marginLeft: 'auto' }}>
                <IconButton
                  size="small"
                  data-testid={`le-language-${lang.label}-up`}
                  disabled={index === 0 || moveLanguageLoading || readOnly || widget.readOnly}
                  onClick={(event) => {
                    moveLanguageUp(lang);
                    event.stopPropagation();
                  }}>
                  <ArrowUpwardIcon />
                </IconButton>
                <IconButton
                  size="small"
                  data-testid={`le-language-${lang.label}-down`}
                  disabled={index === widget.languages.length - 1 || moveLanguageLoading || readOnly || widget.readOnly}
                  onClick={(event) => {
                    moveLanguageDown(lang);
                    event.stopPropagation();
                  }}>
                  <ArrowDownwardIcon />
                </IconButton>
                <IconButton
                  size="small"
                  data-testid={`le-language-${lang.label}-delete`}
                  disabled={readOnly || widget.readOnly}
                  onClick={(event) => {
                    handleDeleteLanguage(lang.label);
                    event.stopPropagation();
                  }}>
                  <DeleteIcon />
                </IconButton>
              </Box>
            </AccordionSummary>
            <AccordionDetails>
              <TextField
                data-testid={`le-language-${lang.label}-body`}
                fullWidth
                id="outlined-multiline-flexible"
                value={currentBody}
                disabled={readOnly || widget.readOnly}
                onChange={handleLanguageBodyChange(lang.label)}
                onBlur={handleLanguageBodyFocusLost(lang.label)}
                multiline
                variant="outlined"
                minRows={5}
                maxRows={8}
              />
            </AccordionDetails>
          </Accordion>
        );
      })}
      <Dialog
        open={addLanguageDialogOpen}
        data-testid="le-add-language-dialog"
        onClose={closeAddLanguageDialog}
        classes={{ paper: classes.paper }}>
        <DialogTitle>Add Language</DialogTitle>
        <DialogContent>
          <DialogContentText>Known languages</DialogContentText>
          <List
            style={{
              border: `2px solid ${knownLanguagesSelected ? theme.palette.selected : 'rgba(0, 0, 0, .125)'}`,
              borderRadius: '4px',
            }}
            data-testid="le-add-language-dialog-know-languages"
            onFocus={handleKnownLanguagesFocusGained}>
            {widget.predefinedLanguages.map((lang, index) => (
              <ListItem
                key={index}
                data-testid={`le-add-language-dialog-language-${lang}`}
                button
                disabled={isAlreadyExisting(lang)}
                selected={selectedPredefinedLanguage === lang}
                onClick={(event) => handlePredefinedLanguageSelected(event, lang)}>
                <ListItemText primary={lang} />
              </ListItem>
            ))}
          </List>
          <TextField
            data-testid="le-add-language-dialog-new-language"
            style={{ marginTop: '1rem' }}
            fullWidth
            variant="outlined"
            label="New language"
            placeholder="New language"
            value={newLanguage}
            onChange={handleNewLanguageChange}
            onFocus={handleNewLanguageFocusGained}
            error={isAlreadyExisting(newLanguage)}
            helperText={`${isAlreadyExisting(newLanguage) ? 'This language already exists' : ''}`}
          />
        </DialogContent>
        <DialogActions>
          <Button
            data-testid="le-add-language-dialog-ok"
            color="primary"
            variant="contained"
            disabled={(selectedPredefinedLanguage === '' && newLanguage === '') || isAlreadyExisting(newLanguage)}
            onClick={handleAddLanguage}>
            Ok
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
};
