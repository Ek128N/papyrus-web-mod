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

import { IconOverlay, useSelection } from '@eclipse-sirius/sirius-components-core';
import { WidgetProps } from '@eclipse-sirius/sirius-components-formdescriptioneditors';
import Chip from '@material-ui/core/Chip';
import IconButton from '@material-ui/core/IconButton';
import AddIcon from '@material-ui/icons/Add';
import Typography from '@material-ui/core/Typography';
import { Theme, makeStyles } from '@material-ui/core/styles';
import HelpOutlineOutlined from '@material-ui/icons/HelpOutlineOutlined';
import { useEffect, useRef, useState } from 'react';
import { GQLContainmentReferenceWidget } from './ContainmentReferenceFragment.types';
import ReorderIcon from './ReorderIcon';

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
  toolbar: {
    marginLeft: 'auto',
    display: 'flex',
    alignItems: 'center',
  },
  chip: {
    margin: '3px',
  },
}));

type PropertySectionComponentProps = WidgetProps<GQLContainmentReferenceWidget>;

export const ContainmentReferencePreview = ({ widget }: PropertySectionComponentProps) => {
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

  const values = ['element-1', 'element-2', 'element-3'];

  return (
    <div>
      <div className={classes.propertySectionLabel}>
        <Typography variant="subtitle2" className={selected ? classes.selected : ''}>
          {widget.label}
        </Typography>
        {widget.hasHelpText && <HelpOutlineOutlined color="secondary" style={{ marginLeft: 8, fontSize: 16 }} />}
        <div className={classes.toolbar} data-testid="containment-reference-toolbar">
          <IconButton data-testid="containment-reference-create-child" disabled size="small">
            <AddIcon />
          </IconButton>
          {widget.containmentReference.canMove && (
            <IconButton data-testid="containment-reference-reorder-children" disabled size="small">
              <ReorderIcon fill="#B3BFC5" />
            </IconButton>
          )}
        </div>
      </div>
      <div ref={ref}>
        {values.map((label) => (
          <Chip
            key={label}
            classes={{ label: classes.labelItemStyle, root: classes.chip }}
            label={label}
            data-testid={`${label}`}
            icon={
              <div>
                <IconOverlay iconURL={['/api/images/icons-override/full/obj16/Class.svg']} alt={label} />
              </div>
            }
            onDelete={() => console.log('deleted')}
          />
        ))}
      </div>
    </div>
  );
};
