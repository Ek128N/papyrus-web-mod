/*****************************************************************************
 * Copyright (c) 2024 CEA LIST, Obeo.
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
package org.eclipse.papyrus.web.application.configuration;

import java.util.List;

import org.eclipse.papyrus.web.application.representations.IRepresentationDescriptionOverrider;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextProcessor;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * Plug the custom mechanism for {@link IRepresentationDescriptionOverrider} to work.
 *
 * @author Arthur Daussy
 */
@Service
@Order(Ordered.LOWEST_PRECEDENCE)
public class PapyrusDescriptionOverriderEditingContextConfigurer implements IEditingContextProcessor {

    private final List<IRepresentationDescriptionOverrider> descriptionOverriders;

    public PapyrusDescriptionOverriderEditingContextConfigurer(List<IRepresentationDescriptionOverrider> descriptionOverriders) {
        super();
        this.descriptionOverriders = descriptionOverriders;
    }

    @Override
    public void postProcess(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext swEditingContext) {
            /**
             * <p>
             * This part of the code has been created for bug
             * https://gitlab.eclipse.org/eclipse/papyrus/org.eclipse.papyrus-web/-/issues/97. But once
             * https://github.com/eclipse-sirius/sirius-web/issues/2809 is fixed this is no longer needed
             * </p>
             */
            for (var descriptionOverrider : this.descriptionOverriders) {
                for (var description : descriptionOverrider.getOverridedDescriptions()) {
                    swEditingContext.getRepresentationDescriptions().put(description.getId(), description);
                }
            }
        }
    }

}
