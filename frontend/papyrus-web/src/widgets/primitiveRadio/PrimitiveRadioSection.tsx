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

import { gql, useMutation } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { PropertySectionComponentProps, PropertySectionLabel } from '@eclipse-sirius/sirius-components-forms';
import FormControl from '@material-ui/core/FormControl';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Radio from '@material-ui/core/Radio';
import RadioGroup from '@material-ui/core/RadioGroup';
import { ChangeEvent, useEffect } from 'react';
import {
  GQLErrorPayload,
  GQLNewValueData,
  GQLNewValueInput,
  GQLNewValuePayload,
  GQLNewValueVariables,
  GQLPrimitiveRadio,
  GQLSuccessPayload,
} from './PrimitiveRadioFragment.type';

export const newValueMutation = gql`
  mutation newValue($input: NewValueInput!) {
    newValue(input: $input) {
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

export const PrimitiveRadioSection = ({
  editingContextId,
  formId,
  widget,
  subscribers,
  readOnly,
}: PropertySectionComponentProps<GQLPrimitiveRadio>) => {
  const { addErrorMessage, addMessages } = useMultiToast();

  const [newValueApi, { loading: newValueLoading, data: newValueData, error: newValueError }] = useMutation<
    GQLNewValueData,
    GQLNewValueVariables
  >(newValueMutation);

  const isErrorPayload = (payload: GQLNewValuePayload): payload is GQLErrorPayload =>
    payload.__typename === 'ErrorPayload';
  const isSuccessPayload = (payload: GQLNewValuePayload): payload is GQLSuccessPayload =>
    payload.__typename === 'SuccessPayload';

  useEffect(() => {
    if (!newValueLoading) {
      if (newValueError) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (newValueData) {
        const { newValue } = newValueData;
        if (isErrorPayload(newValue) || isSuccessPayload(newValue)) {
          addMessages(newValue.messages);
        }
      }
    }
  }, [newValueLoading, newValueError, newValueData]);

  function handleChange(event: ChangeEvent<HTMLInputElement>, value: string): void {
    const input: GQLNewValueInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: formId,
      primitiveRadioId: widget.id,
      value: event.target.value,
    };
    const variables: GQLNewValueVariables = { input };
    newValueApi({ variables });
  }

  return (
    <div data-testid="primitive-radio-widget">
      <PropertySectionLabel
        editingContextId={editingContextId}
        formId={formId}
        widget={widget}
        subscribers={subscribers}
        data-testid={widget.label}
      />
      <FormControl component="fieldset" style={{ marginLeft: '16px' }}>
        <RadioGroup
          row
          aria-label="primitive radio"
          name="primitive-radio"
          value={widget.candidateValue}
          onChange={handleChange}
          data-testid="primitive-radio-candidates">
          {widget.candidateList.map((candidate) => (
            <FormControlLabel
              color="primary"
              key={candidate}
              value={candidate}
              control={<Radio color="primary" />}
              label={candidate}
              data-testid={`primitive-radio-${candidate}`}
              disabled={readOnly}
            />
          ))}
        </RadioGroup>
      </FormControl>
    </div>
  );
};
