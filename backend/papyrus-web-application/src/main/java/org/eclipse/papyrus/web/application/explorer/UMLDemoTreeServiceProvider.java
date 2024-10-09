/*****************************************************************************
 * Copyright (c) 2024 CEA LIST.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.web.application.explorer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.web.application.explorer.builder.UMLDemoTreeDescriptionBuilder;
import org.eclipse.papyrus.web.application.explorer.builder.aqlservices.UMLDemoTreeServices;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.IJavaServiceProvider;
import org.eclipse.sirius.components.view.tree.TreeDescription;
import org.springframework.context.annotation.Configuration;

/**
 * Provider of the services required for the UML Default Explorer tree description.
 *
 * @author Jerome Gout
 */
@Configuration
public class UMLDemoTreeServiceProvider implements IJavaServiceProvider {

    @Override
    public List<Class<?>> getServiceClasses(View view) {

        List<Class<?>> serviceClasses = new ArrayList<>();
        if (view.getDescriptions().stream()
                .filter(TreeDescription.class::isInstance)
                .anyMatch(this::isUMLDemoExplorerTreeDescription)) {
            serviceClasses.add(UMLDemoTreeServices.class);
        }
        return serviceClasses;
    }

    private boolean isUMLDemoExplorerTreeDescription(RepresentationDescription td) {
        return UMLDemoTreeDescriptionBuilder.UML_DEMO_EXPLORER.equals(((TreeDescription) td).getName());
    }

}
