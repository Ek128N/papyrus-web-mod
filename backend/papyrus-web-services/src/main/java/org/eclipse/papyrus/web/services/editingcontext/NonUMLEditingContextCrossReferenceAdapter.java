/*******************************************************************************
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
 *******************************************************************************/
package org.eclipse.papyrus.web.services.editingcontext;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.sirius.components.emf.services.EditingContextCrossReferenceAdapter;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.resource.UMLResource;

/**
 * EditingContextCrossReferenceAdapter that do not self add itself to UML Element.
 *
 * @author Arhtur Daussy
 */
public class NonUMLEditingContextCrossReferenceAdapter extends EditingContextCrossReferenceAdapter {

    @Override
    protected void addAdapter(Notifier notifier) {
        // DO NOT adapt to UML Element nor UML Resource a default CacheAdapter is automatically added on all UML element
        // by default
        // See org.eclipse.uml2.uml.internal.impl.ElementImpl.eAdapters()
        if (!(notifier instanceof Element) && !(notifier instanceof UMLResource)) {
            super.addAdapter(notifier);
        }
    }

}
