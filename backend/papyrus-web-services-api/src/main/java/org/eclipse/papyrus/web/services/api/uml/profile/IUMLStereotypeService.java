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
package org.eclipse.papyrus.web.services.api.uml.profile;

import java.util.List;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;

/**
 * The service to manage UML Stereotype.
 *
 * @author lfasani
 */
public interface IUMLStereotypeService {
    List<UMLStereotypeMetadata> getApplicableStereotypeOn(IEditingContext editingContext, String elementUMLId);

    /**
     * Apply a stereotype on the given element.
     *
     * @return an object that represents an application of the specified stereotype. If empty, the stereotype
     *         application failed.
     */
    Optional<Object> applyStereotype(IEditingContext editingContext, String elementUMLId, String stereotypeId);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author lfasani
     */
    class NoOp implements IUMLStereotypeService {

        @Override
        public List<UMLStereotypeMetadata> getApplicableStereotypeOn(IEditingContext editingContext, String elementUMLId) {
            return null;
        }

        @Override
        public Optional<Object> applyStereotype(IEditingContext editingContext, String elementUMLId, String stereotypeId) {
            return Optional.empty();
        }
    }
}
