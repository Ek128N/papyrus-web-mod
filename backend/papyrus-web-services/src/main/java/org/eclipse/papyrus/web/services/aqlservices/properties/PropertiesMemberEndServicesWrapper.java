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
package org.eclipse.papyrus.web.services.aqlservices.properties;

import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrus.uml.domain.services.IEditableChecker;
import org.eclipse.papyrus.uml.domain.services.properties.ILogger;
import org.eclipse.papyrus.uml.domain.services.properties.PropertiesMemberEndServices;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

/**
 * At the moment, there is an error if a service returns void (see discussion in
 * {@link https://github.com/eclipse-sirius/sirius-components/issues/1343}), for this reason we wrap all services of
 * {@see PropertiesImageServices} class in order to be sure that services return something.
 *
 * @author Jerome Gout
 */
public class PropertiesMemberEndServicesWrapper {
    private PropertiesMemberEndServices delegate;

    public PropertiesMemberEndServicesWrapper(ILogger logger, IEditableChecker checker) {
        this.delegate = new PropertiesMemberEndServices(logger, checker);
    }

    /**
     * Replacement of {@link PropertiesMemberEndServices#setNavigable(Property, boolean)}.
     */
    public Property setNavigable(Property memberEnd, boolean isNavigable) {
        this.delegate.setNavigable(memberEnd, isNavigable);
        return memberEnd;
    }

    /**
     * Replacement of {@link PropertiesMemberEndServices#getOwner(Property)}.
     */
    public String getOwner(Property memberEnd) {
        return this.delegate.getOwner(memberEnd);
    }

    /**
     * Replacement of {@link PropertiesMemberEndServices#getOwnerEnumerations(Element)}.
     */
    public List<String> getOwnerEnumerations(Element obj) {
        return this.delegate.getOwnerEnumerations(obj);
    }

    /**
     * Replacement of {@link PropertiesMemberEndServices#setOwner(Property, String)}.<br>
     */
    public Property setOwner(Property memberEnd, String owner) {
        this.delegate.setOwner(memberEnd, owner);
        return memberEnd;
    }

    /**
     * Replacement of {@link PropertiesMemberEndServices#getOwnedAttributeFeatureForType(Type)}.
     */
    public EStructuralFeature getOwnedAttributeFeatureForType(Type type) {
        return this.delegate.getOwnedAttributeFeatureForType(type);
    }

    /**
     * Replacement of {@link PropertiesMemberEndServices#isMemberEndPropertyEditable(Element, String)}.
     */
    public boolean isMemberEndPropertyEditable(Element source, String propertyPath) {
        return this.delegate.isMemberEndPropertyEditable(source, propertyPath);
    }
}
