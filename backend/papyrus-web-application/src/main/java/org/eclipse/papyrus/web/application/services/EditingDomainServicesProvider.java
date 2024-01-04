/*******************************************************************************
 * Copyright (c) 2022, 2024 CEA LIST, Obeo.
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
 *******************************************************************************/
package org.eclipse.papyrus.web.application.services;

import java.util.Collections;
import java.util.List;

import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.IJavaServiceProvider;
import org.eclipse.sirius.ext.emf.edit.EditingDomainServices;
import org.springframework.stereotype.Service;

/**
 * Provider for {@link org.eclipse.sirius.ext.emf.edit.EditingDomainServices} services.
 *
 * @author adaussy
 */
@Service
public class EditingDomainServicesProvider implements IJavaServiceProvider {

    @Override
    public List<Class<?>> getServiceClasses(View view) {
        return Collections.singletonList(EditingDomainServices.class);
    }

}
