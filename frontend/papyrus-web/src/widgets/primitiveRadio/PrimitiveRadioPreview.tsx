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
import { WidgetProps } from '@eclipse-sirius/sirius-components-formdescriptioneditors';
import { FormControl, FormControlLabel, Radio, RadioGroup } from '@material-ui/core';
import Typography from '@material-ui/core/Typography';
import { Theme, makeStyles } from '@material-ui/core/styles';
import HelpOutlineOutlined from '@material-ui/icons/HelpOutlineOutlined';
import { useEffect, useRef, useState } from 'react';
import { GQLPrimitiveRadio } from './PrimitiveRadioFragment.type';

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

type PropertySectionComponentProps = WidgetProps<GQLPrimitiveRadio>;

export const PrimitiveRadioPreview = ({ widget }: PropertySectionComponentProps) => {
  const classes = useStyles();
  const [selected, setSelected] = useState<boolean>(false);

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

  const candidateList = ['option1', 'option2', 'option3'];

  return (
    <div>
      <div className={classes.propertySectionLabel}>
        <Typography variant="subtitle2" className={selected ? classes.selected : ''}>
          {widget.label}
        </Typography>
        {widget.hasHelpText && <HelpOutlineOutlined color="secondary" style={{ marginLeft: 8, fontSize: 16 }} />}
      </div>
      <FormControl component="fieldset" style={{ marginLeft: '16px' }}>
        <RadioGroup
          ref={ref}
          row
          aria-label="primitive radio"
          name="primitive-radio"
          value={candidateList.at(1)}
          data-testid="primitive-radio-candidates">
          {candidateList.map((candidate) => (
            <FormControlLabel
              color="primary"
              key={candidate}
              value={candidate}
              control={<Radio color="primary" />}
              label={candidate}
            />
          ))}
        </RadioGroup>
      </FormControl>
    </div>
  );
};
