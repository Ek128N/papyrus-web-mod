/*****************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo.
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
 *****************************************************************************/
package org.eclipse.papyrus.web.application.utils.mutations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.papyrus.web.sirius.contributions.ServiceOverride;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.representations.Message;

/**
 * A test-level implementation of {@link IFeedbackMessageService}.
 * <p>
 * This class overrides the existing implementation to allow un-scoped calls to {@link #addFeedbackMessage(Message)}.
 * This service is required for tests that programmatically execute GraphQL queries and mutations, because they aren't
 * in a {@code Request Scope}, meaning that the {@link IFeedbackMessageService} bean is not visible.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
@ServiceOverride(IFeedbackMessageService.class)
public class TestFeedbackMessageService implements IFeedbackMessageService {

    private final List<Message> feedbackMessages = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void addFeedbackMessage(Message message) {
        this.feedbackMessages.add(message);
    }

    @Override
    public List<Message> getFeedbackMessages() {
        return this.feedbackMessages;
    }
}
