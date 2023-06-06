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
package org.eclipse.papyrus.web.application.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.sirius.components.view.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Util class sue to serialized view Models.
 *
 * @author Arthur Daussy
 */
public class ViewSerializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ViewSerializer.class);

    public void printAndSaveViewModel(View view) {
        XMIResourceImpl xmiResourceImpl = new XMIResourceImpl();
        View viewCopy = EcoreUtil.copy(view);
        viewCopy.getDescriptions().forEach(d -> d.setName(d.getName() + " serialized")); //$NON-NLS-1$
        xmiResourceImpl.getContents().add(viewCopy);

        try (var fileOutputStream = new ByteArrayOutputStream()) {
            xmiResourceImpl.save(fileOutputStream, Collections.emptyMap());

            byte[] content = fileOutputStream.toByteArray();
            String contentString = new String(content, "UTF-8"); //$NON-NLS-1$
            LOGGER.info(contentString);

            String userHome = System.getProperty("user.home"); //$NON-NLS-1$
            if (userHome != null) {
                String[] parts = view.eResource().getURI().toString().split("/"); //$NON-NLS-1$
                String fileName = parts[parts.length - 1];
                Path targetFile = Path.of(userHome + "/papyrus-web/" + fileName + ".view"); //$NON-NLS-1$ //$NON-NLS-2$

                if (!targetFile.getParent().toFile().exists()) {
                    targetFile.getParent().toFile().mkdirs();
                }

                Files.write(targetFile, content);
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

}
