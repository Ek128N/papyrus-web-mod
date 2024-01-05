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
import { useSelection } from '@eclipse-sirius/sirius-components-core';
import MuiAccordion from '@material-ui/core/Accordion';
import MuiAccordionDetails from '@material-ui/core/AccordionDetails';
import MuiAccordionSummary from '@material-ui/core/AccordionSummary';
import Box from '@material-ui/core/Box';
import IconButton from '@material-ui/core/IconButton';
import TextField from '@material-ui/core/TextField';
import Typography from '@material-ui/core/Typography';
import { Theme, makeStyles, withStyles } from '@material-ui/core/styles';
import ArrowDownwardIcon from '@material-ui/icons/ArrowDownward';
import ArrowForwardIosSharpIcon from '@material-ui/icons/ArrowForwardIosSharp';
import ArrowUpwardIcon from '@material-ui/icons/ArrowUpward';
import HelpOutlineOutlined from '@material-ui/icons/HelpOutlineOutlined';
import DeleteIcon from '@material-ui/icons/Delete';
import { useEffect, useRef, useState } from 'react';
import { GQLLanguageExpression } from './LanguageExpressionFragment.types';
import { WidgetProps } from '@eclipse-sirius/sirius-components-formdescriptioneditors';

const Accordion = withStyles((theme) => ({
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
}))(MuiAccordion);

const AccordionSummary = withStyles((theme) => ({
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
}))(MuiAccordionSummary);

const AccordionDetails = withStyles((theme) => ({
  root: {
    padding: theme.spacing(1),
    borderTop: '1px solid rgba(0, 0, 0, .125)',
  },
}))(MuiAccordionDetails);

const useStyles = makeStyles<Theme>((theme) => ({
  style: {
    color: theme.palette.secondary.main,
  },
  selected: {
    color: theme.palette.selected,
  },
  propertySectionLabel: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
  },
}));

type PropertySectionComponentProps = WidgetProps<GQLLanguageExpression>;

export const LanguageExpressionPreview = ({ widget }: PropertySectionComponentProps) => {
  const classes = useStyles();
  const [selected, setSelected] = useState<boolean>(false);
  const [expanded, setExpanded] = useState<boolean>(false);

  const { selection } = useSelection();

  const ref = useRef<HTMLInputElement | null>(null);

  useEffect(() => {
    if (ref.current && selection.entries.find((entry) => entry.id === widget.id)) {
      ref.current.focus();
      setSelected(true);
    } else {
      setSelected(false);
    }
  }, [selection, widget]);

  const handlePanelExpansion = () => (event: React.SyntheticEvent, expanded: boolean) => {
    setExpanded(expanded);
  };

  return (
    <div>
      <div className={classes.propertySectionLabel}>
        <Typography variant="subtitle2" className={selected ? classes.selected : ''}>
          {widget.label}
        </Typography>
        {widget.hasHelpText && <HelpOutlineOutlined color="secondary" style={{ marginLeft: 8, fontSize: 16 }} />}
      </div>
      <Accordion square elevation={0} ref={ref} onChange={handlePanelExpansion()} expanded={expanded}>
        <AccordionSummary
          aria-controls="panel1d-content"
          expandIcon={<ArrowForwardIosSharpIcon style={{ fontSize: '0.9rem' }} />}
          id="panel1d-header">
          <Typography>Language</Typography>
          <Box sx={{ marginLeft: 'auto' }}>
            <IconButton size="small" disabled>
              <ArrowUpwardIcon />
            </IconButton>
            <IconButton size="small" disabled>
              <ArrowDownwardIcon />
            </IconButton>
            <IconButton size="small" disabled>
              <DeleteIcon />
            </IconButton>
          </Box>
        </AccordionSummary>
        <AccordionDetails>
          <TextField
            fullWidth
            id="outlined-multiline-flexible"
            value={
              'Lorem ipsum dolor, sit amet consectetur adipisicing elit. Qui fugit saepe tenetur molestiae officia cupiditate rem voluptas repellendus, consequatur cumque!'
            }
            multiline
            variant="outlined"
            minRows={5}
            maxRows={8}
          />
        </AccordionDetails>
      </Accordion>
    </div>
  );
};
