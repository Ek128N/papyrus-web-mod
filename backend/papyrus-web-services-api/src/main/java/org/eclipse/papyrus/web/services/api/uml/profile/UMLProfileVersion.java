/*******************************************************************************
 * Copyright (c) 2023 CEA LIST.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.web.services.api.uml.profile;

/**
 * The basic information of an UML stereotype.
 *
 * @author lfasani
 */
public record UMLProfileVersion(Integer major, Integer minor, Integer micro) {

}
