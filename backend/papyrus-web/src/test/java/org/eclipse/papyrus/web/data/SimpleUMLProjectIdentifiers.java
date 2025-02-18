/*****************************************************************************
 * Copyright (c) 2024, 2025 CEA LIST.
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
package org.eclipse.papyrus.web.data;

import java.util.UUID;

/**
 * Identifier of elements inserted in the database with the script "uml-model-import-and-profile.sql".
 *
 * @author Arthur Daussy
 */
public class SimpleUMLProjectIdentifiers {

    public static final UUID UML_DEFAULT_PROJECT = UUID.fromString("c87b6af4-7f3b-4802-9497-94bc5219d5f6");

    public static final UUID UML_DEFAULT_EDITING_CONTEXT_ID = UUID.fromString("cbf34577-d7fb-4d12-a096-27d0d1f9164e");

    public static final UUID MODEL_DOCUMENT_ID = UUID.fromString("b6270893-90c6-4184-9d2c-235c0d5b53b4");

    public static final UUID MODEL_ELEMENT_ID = UUID.fromString("8f568fdc-f688-4ecf-8e5f-9a1ed1a5fc6d");

    public static final UUID PACKAGE_IMPORT_ELEMENT_ID = UUID.fromString("159f422d-051e-4c98-a161-7dcbc5834f60");

    public static final UUID CLASS_ELEMENT_ID = UUID.fromString("ff236757-fcd6-418b-8bf4-bd087adbf988");

    public static final UUID PROFILE_APPLICATION_ELEMENT_ID = UUID.fromString("a5201902-d96a-4304-a16d-d8fbf5854c58");

    public static final String IMPORTED_PRIMITIVETYPES_PACKAGE_ELEMENT_ID = "p://ie?el=UMLPrimitiveTypes.library.uml%23_0&pa=159f422d-051e-4c98-a161-7dcbc5834f60&id=10dc15ea-1f4e-37b3-8476-d397a55bfb66";

}
