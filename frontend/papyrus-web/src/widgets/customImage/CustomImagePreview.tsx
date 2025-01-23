/*****************************************************************************
 * Copyright (c) 2019, 2024 CEA LIST, Obeo, Artal Technologies.
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
import { WidgetProps } from '@eclipse-sirius/sirius-components-formdescriptioneditors';
import { getTextDecorationLineValue } from '@eclipse-sirius/sirius-components-forms';
import { GQLReferenceWidgetStyle } from '@eclipse-sirius/sirius-components-widget-reference';
import AddIcon from '@mui/icons-material/Add';
import DeleteIcon from '@mui/icons-material/Delete';
import HelpOutlineOutlinedIcon from '@mui/icons-material/HelpOutlineOutlined';
import MoreHorizIcon from '@mui/icons-material/MoreHoriz';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import { makeStyles } from 'tss-react/mui';
import { GQLCustomImageWidget } from './CustomImageFragment.types';
import { CustomImageIcon } from './CustomImageIcon';

const useStyles = makeStyles<GQLReferenceWidgetStyle>()(
  (theme, { color, fontSize, italic, bold, underline, strikeThrough }) => ({
    style: {
      color: color ? color : null,
      fontSize: fontSize ? fontSize : null,
      fontStyle: italic ? 'italic' : null,
      fontWeight: bold ? 'bold' : null,
      textDecorationLine: getTextDecorationLineValue(underline, strikeThrough),
    },
    toolbar: {
      marginLeft: 'auto',
      display: 'flex',
      alignItems: 'center',
    },
    propertySectionLabel: {
      display: 'flex',
      flexDirection: 'row',
      alignItems: 'center',
    },
  })
);

type PropertySectionComponentProps = WidgetProps<GQLCustomImageWidget>;

export const CustomImagePreview = ({ widget }: PropertySectionComponentProps) => {
  const styleProps: GQLReferenceWidgetStyle = {
    color: widget.style?.color ?? null,
    fontSize: widget.style?.fontSize ?? null,
    italic: widget.style?.italic ?? null,
    bold: widget.style?.bold ?? null,
    underline: widget.style?.underline ?? null,
    strikeThrough: widget.style?.strikeThrough ?? null,
  };
  const { classes } = useStyles(styleProps);

  return (
    <>
      <div className={classes.propertySectionLabel}>
        <Typography variant="subtitle2">{widget.label}</Typography>
        {widget.hasHelpText ? (
          <HelpOutlineOutlinedIcon color="secondary" style={{ marginLeft: 8, fontSize: 16 }} />
        ) : null}
      </div>
      <div data-testid={`custom-image-${widget.label}`} style={{ display: 'flex' }}>
        <CustomImageIcon width="250%" />
        <div className={classes.toolbar} data-testid="custom-image-toolbar">
          <IconButton data-testid="custom-image-add" size="small" disabled>
            <AddIcon />
          </IconButton>
          <IconButton data-testid="custom-image-select" size="small" disabled>
            <MoreHorizIcon />
          </IconButton>
          <IconButton data-testid="custom-image-remove" size="small" disabled>
            <DeleteIcon />
          </IconButton>
        </div>
      </div>
    </>
  );
};
