/*****************************************************************************
 * Copyright (c) 2024 CEA LIST.
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
package org.eclipse.papyrus.web.application.explorer;

import java.util.Objects;

import org.eclipse.uml2.uml.StructuredClassifier;
import org.eclipse.uml2.uml.Type;

/**
 * Record to represent a virtual tree item for {@link Type}.
 *
 * @author Jerome Gout
 */
public record AttributeTypeTreeItem(StructuredClassifier classifier, Type type) {
    @Override
    public final boolean equals(Object other) {
        var result = false;
        if (other instanceof AttributeTypeTreeItem otherAttr) {
            if (this.type() == null) {
                result = otherAttr.type() == null;
            } else if (otherAttr.type() != null) {
                result = Objects.equals(this.type().getQualifiedName(), otherAttr.type().getQualifiedName());
            }
        }
        return result;
    }

    @Override
    public final int hashCode() {
        int result = this.classifier().hashCode();
        if (this.type() != null) {
            result += this.type().hashCode();
        }
        return result;
    }
}
