/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
package org.eclipse.papyrus.web.services.api.projects;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * Represents the manifest of the project.
 *
 * @author gcoutable
 */
@Immutable
public final class ProjectManifest {
    private String manifestVersion;

    private String papyrusWebVersion;

    private List<String> metamodels;

    private Map<String, String> documentIdsToName;

    private Map<String, RepresentationManifest> representations;

    private ProjectManifest() {
        // Prevent instantiation
    }

    public String getManifestVersion() {
        return this.manifestVersion;
    }

    public String getPapyrusWebVersion() {
        return this.papyrusWebVersion;
    }

    public List<String> getMetamodels() {
        return this.metamodels;
    }

    public Map<String, String> getDocumentIdsToName() {
        return this.documentIdsToName;
    }

    public Map<String, RepresentationManifest> getRepresentations() {
        return this.representations;
    }

    public static Builder newProjectManifest(String manifestVersion, String papyrusWebVersion) {
        return new Builder(manifestVersion, papyrusWebVersion);
    }

    /**
     * The builder for the {@link ProjectManifest} class.
     *
     * @author gcoutable
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String manifestVersion;

        private String papyrusWebVersion;

        private List<String> metamodels;

        private Map<String, String> documentIdsToName;

        private Map<String, RepresentationManifest> representations;

        private Builder(String manifestVersion, String papyrusWebVersion) {
            this.manifestVersion = Objects.requireNonNull(manifestVersion);
            this.papyrusWebVersion = Objects.requireNonNull(papyrusWebVersion);
        }

        public Builder metamodels(List<String> metamodels) {
            this.metamodels = Objects.requireNonNull(metamodels);
            return this;
        }

        public Builder documentIdsToName(Map<String, String> documentIdsToName) {
            this.documentIdsToName = Objects.requireNonNull(documentIdsToName);
            return this;
        }

        public Builder representations(Map<String, RepresentationManifest> representations) {
            this.representations = Objects.requireNonNull(representations);
            return this;
        }

        public ProjectManifest build() {
            ProjectManifest projectManifest = new ProjectManifest();
            projectManifest.manifestVersion = Objects.requireNonNull(this.manifestVersion);
            projectManifest.papyrusWebVersion = Objects.requireNonNull(this.papyrusWebVersion);
            projectManifest.metamodels = Objects.requireNonNull(this.metamodels);
            projectManifest.documentIdsToName = Objects.requireNonNull(this.documentIdsToName);
            projectManifest.representations = Objects.requireNonNull(this.representations);
            return projectManifest;
        }
    }

}
