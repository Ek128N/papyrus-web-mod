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

import java.util.Objects;

import org.eclipse.papyrus.web.application.templates.service.api.IUMLProjectCheckerService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextProcessor;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.springframework.stereotype.Service;

/**
 * Processor that remove the ResourceSet load option : {@link JsonResource#OPTION_EXTENDED_META_DATA}. This option
 * causes problem with EMFJson version EMFJSon 2.3.6.
 *
 * @author Arthur Daussy
 */
@Service
public class ExtendedMetadataDisablerEditingContextConfigurer implements IEditingContextProcessor {

    private final IUMLProjectCheckerService umlChecker;

    public ExtendedMetadataDisablerEditingContextConfigurer(IUMLProjectCheckerService umlChecker) {
        super();
        this.umlChecker = Objects.requireNonNull(umlChecker);
    }

    @Override
    public void preProcess(IEditingContext editingContext) {
        if (editingContext instanceof IEMFEditingContext swEditingContext && this.umlChecker.isPapyrusProject(editingContext.getId())) {
            // Workaround regarding the regression on EMFJSon 2.3.6 that now uses the option
            // JsonResource.OPTION_EXTENDED_META_DATA for metamodel migration
            // See commit
            // https://github.com/eclipse-sirius/sirius-emf-json/commit/25da059df31534ce8cdbebf3ced7243951bb9a1f
            // See also https://github.com/eclipse-sirius/sirius-web/issues/3806
            swEditingContext.getDomain().getResourceSet().getLoadOptions().remove(JsonResource.OPTION_EXTENDED_META_DATA);
        }
    }
}
