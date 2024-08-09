/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.papyrus.web.services.editingcontext;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.sirius.components.emf.migration.api.IMigrationParticipant;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.web.application.document.services.api.IExternalResourceLoaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * Custom implementation used as a workaround for bug https://github.com/eclipse-sirius/sirius-web/issues/3806.
 *
 * This implementation removes the MigrationService since it forces the use of BasicExtendedMetaData which does not work
 * properly together with EMFJSon and UML.
 *
 * @author Arthur Daussy
 */
@Service
@Order(Ordered.HIGHEST_PRECEDENCE)
public class PapyrusJSONExternalResourceLoaderService implements IExternalResourceLoaderService {

    private final Logger logger = LoggerFactory.getLogger(PapyrusJSONExternalResourceLoaderService.class);

    private final List<IMigrationParticipant> migrationParticipants;

    public PapyrusJSONExternalResourceLoaderService(List<IMigrationParticipant> migrationParticipants) {
        this.migrationParticipants = migrationParticipants;
    }

    @Override
    public boolean canHandle(InputStream inputStream, URI resourceURI, ResourceSet resourceSet) {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        bufferedInputStream.mark(Integer.MAX_VALUE);
        try (var reader = new BufferedReader(new InputStreamReader(bufferedInputStream, StandardCharsets.UTF_8))) {
            char[] read = new char[200];
            int nb = reader.read(read);
            bufferedInputStream.reset();
            if (nb > 0) {
                char[] content = Arrays.copyOf(read, nb);
                String stringContent = new String(content).trim();
                if (stringContent.startsWith("{") && stringContent.contains("UML")) {
                    return true;
                }
            }
        } catch (IOException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }
        return false;
    }

    @Override
    public Optional<Resource> getResource(InputStream inputStream, URI resourceURI, ResourceSet resourceSet) {
        Resource resource = null;
        try {
            Map<String, Object> options = new HashMap<>();

            // Workaround of bug https://github.com/eclipse-sirius/sirius-web/issues/3806
            // We do not want ExtendedMetada for UML resource as long as this problem is not solved
            // var migrationService = new MigrationService(this.migrationParticipants);
            // options.put(JsonResource.OPTION_JSON_RESSOURCE_PROCESSOR, migrationService);
            // options.put(JsonResource.OPTION_EXTENDED_META_DATA, migrationService);

            var jsonResource = new JSONResourceFactory().createResource(resourceURI);
            resourceSet.getResources().add(jsonResource);
            jsonResource.load(inputStream, options);

            resource = jsonResource;
        } catch (IOException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }
        return Optional.ofNullable(resource);
    }
}
