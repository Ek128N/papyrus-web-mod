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
package org.eclipse.papyrus.web.application.utils;

import org.eclipse.emf.ecore.EObject;

/**
 * Items used to help the search of an element in a representation.
 *
 * @author Arthur Daussy
 */
public class ElementMatcher {

    private final EObject semanticElement;

    private final String descriptionId;

    public ElementMatcher(EObject semanticElement, String descriptionId) {
        super();
        this.semanticElement = semanticElement;
        this.descriptionId = descriptionId;
    }

    public String getDescriptionId() {
        return this.descriptionId;
    }

    public EObject getSemanticElement() {
        return this.semanticElement;
    }
}
