/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
import {
  GQLApplyStereotypeMutationData,
  GQLGetStereotypesQueryData,
  GQLStereotypeMetadata,
} from './ApplyStereotypeModal.types';
import { assign, Machine } from 'xstate';

export interface ApplyStereotypeModalStateSchema {
  states: {
    toast: {
      states: {
        visible: {};
        hidden: {};
      };
    };
    applyStereotypeModal: {
      states: {
        loading: {};
        valid: {};
        applyingStereotype: {};
        success: {};
      };
    };
  };
}

export type SchemaValue = {
  applyStereotypeModal: 'loading' | 'valid' | 'applyingStereotype' | 'success';
  toast: 'visible' | 'hidden';
};

export interface ApplyStereotypeModalContext {
  selectedStereotypeId: string;
  stereotypes: GQLStereotypeMetadata[];
  message: string | null;
}

export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };
export type FetchedStereotypesEvent = {
  type: 'HANDLE_FETCHED_STEREOTYPES';
  data: GQLGetStereotypesQueryData;
};
export type ChangeStereotypeEvent = {
  type: 'CHANGE_STEREOTYPE';
  stereotypeId: string;
};

export type ApplyStereotypeEvent = { type: 'APPLY_STEREOTYPE' };
export type HandleResponseEvent = { type: 'HANDLE_RESPONSE'; data: GQLApplyStereotypeMutationData };
export type ApplyStereotypeModalEvent =
  | FetchedStereotypesEvent
  | ChangeStereotypeEvent
  | ApplyStereotypeEvent
  | HandleResponseEvent
  | ShowToastEvent
  | HideToastEvent;

export const applyStereotypeModalMachine = Machine<
  ApplyStereotypeModalContext,
  ApplyStereotypeModalStateSchema,
  ApplyStereotypeModalEvent
>(
  {
    id: 'ApplyStereotypesModal',
    type: 'parallel',
    context: {
      selectedStereotypeId: '',
      stereotypes: [],
      message: null,
    },
    states: {
      toast: {
        initial: 'hidden',
        states: {
          hidden: {
            on: {
              SHOW_TOAST: {
                target: 'visible',
                actions: 'setMessage',
              },
            },
          },
          visible: {
            on: {
              HIDE_TOAST: {
                target: 'hidden',
                actions: 'clearMessage',
              },
            },
          },
        },
      },
      applyStereotypeModal: {
        initial: 'loading',
        states: {
          loading: {
            on: {
              HANDLE_FETCHED_STEREOTYPES: [
                {
                  target: 'valid',
                  actions: 'updateStereotypes',
                },
              ],
            },
          },
          valid: {
            on: {
              CHANGE_STEREOTYPE: [
                {
                  actions: 'updateStereotype',
                },
              ],
              APPLY_STEREOTYPE: [
                {
                  target: 'applyingStereotype',
                },
              ],
            },
          },
          applyingStereotype: {
            on: {
              HANDLE_RESPONSE: [
                {
                  cond: 'isResponseSuccessful',
                  target: 'success',
                },
                {
                  target: 'valid',
                },
              ],
            },
          },
          success: {
            type: 'final',
          },
        },
      },
    },
  },
  {
    guards: {
      isResponseSuccessful: (_, event) => {
        const { data } = event as HandleResponseEvent;
        return data.applyStereotype.__typename === 'ApplyStereotypeSuccessPayload';
      },
    },
    actions: {
      updateStereotypes: assign((_, event) => {
        const { data } = event as FetchedStereotypesEvent;
        const stereotypes = data.viewer.editingContext.stereotypeMetatadas;
        const selectedStereotypeId = stereotypes.length > 0 ? stereotypes[0].id : '';
        const name = stereotypes.length > 0 ? stereotypes[0].label : '';
        return {
          stereotypes,
          selectedStereotypeId,
          name,
        };
      }),
      updateStereotype: assign((_, event) => {
        const { stereotypeId } = event as ChangeStereotypeEvent;
        return { selectedStereotypeId: stereotypeId };
      }),
      setMessage: assign((_, event) => {
        const { message } = event as ShowToastEvent;
        return { message };
      }),
      clearMessage: assign((_) => {
        return { message: null };
      }),
    },
  }
);
