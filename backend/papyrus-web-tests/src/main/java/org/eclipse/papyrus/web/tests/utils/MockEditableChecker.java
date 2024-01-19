/*******************************************************************************
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
 *******************************************************************************/
package org.eclipse.papyrus.web.tests.utils;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.uml.domain.services.IEditableChecker;

/**
 *
 * This class is used to mock the behavior of {@link IEditableChecker}.
 *
 * @author Jerome Gout
 *
 */
public class MockEditableChecker implements IEditableChecker {

    @Override
    public boolean canEdit(EObject eObject) {
        return true;
    }

}
