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
package org.eclipse.papyrus.web.custom.widgets;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.view.View;

/**
 * Used to create an AQL interpreter.
 *
 * @author Jerome Gout
 */
public interface IAQLInterpreterProvider {

    AQLInterpreter createInterpreter(View view, IEditingContext editingContext);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author Jerome Gout
     */
    class NoOp implements IAQLInterpreterProvider {
        @Override
        public AQLInterpreter createInterpreter(View view, IEditingContext editingContext) {
            return null;
        }
    }
}
