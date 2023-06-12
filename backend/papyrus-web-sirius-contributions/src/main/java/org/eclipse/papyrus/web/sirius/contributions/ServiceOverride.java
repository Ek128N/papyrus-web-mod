/*****************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo.
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
package org.eclipse.papyrus.web.sirius.contributions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * Annotation used override a service.
 *
 * <p>
 * Be aware that in many cases, the overriding service <b>must not</b> have the same class name than the overridden
 * service. Indeed, the name of the service should be different. The only exception is if the overridden service use a
 * custom service name defined in the @service annotation.
 * </p>
 *
 * @author Arthur Daussy
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Service
@Primary
public @interface ServiceOverride {

    /**
     * Class of the service to override. This field is only used for tractability
     *
     * @return a class
     */
    Class<?> value();

}
