/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
import { GQLApplyProfileMutationData, GQLGetProfilesQueryData, Profile } from './ApplyProfileModal.types';
import { assign, Machine } from 'xstate';

export interface ApplyProfileModalStateSchema {
  states: {
    toast: {
      states: {
        visible: {};
        hidden: {};
      };
    };
    applyProfileModal: {
      states: {
        loading: {};
        valid: {};
        applyingProfile: {};
        success: {};
      };
    };
  };
}

export type SchemaValue = {
  applyProfileModal: 'loading' | 'valid' | 'applyingProfile' | 'success';
  toast: 'visible' | 'hidden';
};

export interface ApplyProfileModalContext {
  selectedProfileId: string;
  profiles: Profile[];
  message: string | null;
}

export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };
export type FetchedProfilesEvent = {
  type: 'HANDLE_FETCHED_PROFILES';
  data: GQLGetProfilesQueryData;
};
export type ChangeProfileEvent = {
  type: 'CHANGE_PROFILE';
  profileId: string;
};
export type ApplyProfileEvent = { type: 'APPLY_PROFILE' };
export type HandleResponseEvent = { type: 'HANDLE_RESPONSE'; data: GQLApplyProfileMutationData };
export type ApplyProfileModalEvent =
  | FetchedProfilesEvent
  | ChangeProfileEvent
  | ApplyProfileEvent
  | HandleResponseEvent
  | ShowToastEvent
  | HideToastEvent;

export const applyProfileModalMachine = Machine<
  ApplyProfileModalContext,
  ApplyProfileModalStateSchema,
  ApplyProfileModalEvent
>(
  {
    id: 'ApplyProfileModal',
    type: 'parallel',
    context: {
      selectedProfileId: '',
      profiles: [],
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
      applyProfileModal: {
        initial: 'loading',
        states: {
          loading: {
            on: {
              HANDLE_FETCHED_PROFILES: [
                {
                  target: 'valid',
                  actions: 'updateProfiles',
                },
              ],
            },
          },
          valid: {
            on: {
              CHANGE_PROFILE: [
                {
                  actions: 'updateProfile',
                },
              ],
              APPLY_PROFILE: [
                {
                  target: 'applyingProfile',
                },
              ],
            },
          },
          applyingProfile: {
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
        return data.applyProfile.__typename === 'ApplyProfileSuccessPayload';
      },
    },
    actions: {
      updateProfiles: assign((_, event) => {
        const { data } = event as FetchedProfilesEvent;
        const { profileMetadatas: profiles } = data.viewer;
        const selectedProfileId = profiles.length > 0 ? profiles[0].uriPath : '';
        return { profiles, selectedProfileId };
      }),
      updateProfile: assign((_, event) => {
        const { profileId } = event as ChangeProfileEvent;
        return { selectedProfileId: profileId };
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
