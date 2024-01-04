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
package org.eclipse.papyrus.web.application.representations.view.builders;

import org.eclipse.emf.common.notify.impl.AdapterImpl;

/**
 * Adapter used to run callacks at the ends of the of the construction of a representation.
 *
 * @author Arthur Daussy
 */
public class CallbackAdapter extends AdapterImpl {

    private final Runnable callback;

    public CallbackAdapter(Runnable callback) {
        super();
        this.callback = callback;
    }

    public void run() {
        callback.run();
    }

}
