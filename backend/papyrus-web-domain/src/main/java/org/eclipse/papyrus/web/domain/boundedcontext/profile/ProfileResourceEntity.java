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
package org.eclipse.papyrus.web.domain.boundedcontext.profile;

import java.text.MessageFormat;
import java.util.UUID;

import org.eclipse.papyrus.web.domain.boundedcontext.profile.events.ProfilePublishedEvent;
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.domain.boundedcontexts.AbstractValidatingAggregateRoot;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

/**
 * ProfileResourceEntity entity used by the persistence layer.
 *
 * @author sbegaudeau
 */
@Table(name = "profile")
public class ProfileResourceEntity extends AbstractValidatingAggregateRoot<ProfileResourceEntity> implements Persistable<UUID> {

    @Transient
    private boolean isNew;

    @Id
    private UUID id;

    private String content;

    @Override
    public UUID getId() {
        return this.id;
    }

    public void setNew(boolean isNewEntity) {
        this.isNew = isNewEntity;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id);
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    public static Builder newProfile() {
        return new Builder();
    }

    /**
     * Builder of ProfileResourceEntity.
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private String content;

        private UUID id;

        private boolean isNew;

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder isNew(boolean isNew) {
            this.isNew = isNew;
            return this;
        }

        public ProfileResourceEntity build(ICause cause) {
            var profile = new ProfileResourceEntity();
            profile.content = this.content;
            profile.id = this.id;
            profile.isNew = this.isNew;

            profile.registerEvent(new ProfilePublishedEvent(UUID.randomUUID(), cause, profile, this.isNew));

            return profile;
        }
    }
}
