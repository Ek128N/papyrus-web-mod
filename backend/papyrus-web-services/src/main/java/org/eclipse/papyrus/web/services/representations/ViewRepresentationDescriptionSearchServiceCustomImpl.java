/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
package org.eclipse.papyrus.web.services.representations;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.papyrus.web.sirius.contributions.ServiceOverride;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.view.form.FormElementDescription;
import org.eclipse.sirius.web.services.representations.ViewRepresentationDescriptionSearchService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * Used to find view representation descriptions.
 *
 * @author arichard
 */
// TODO Check if we can reused IInMemoryViewRegistry instead of the custom implementation
@ServiceOverride(org.eclipse.sirius.web.services.representations.ViewRepresentationDescriptionSearchService.class)
@ConditionalOnProperty(prefix = "org.eclipse.sirius.web.features", name = "studioDefinition")
public class ViewRepresentationDescriptionSearchServiceCustomImpl implements IViewRepresentationDescriptionSearchService {

    private PapyrusRepresentationDescriptionRegistry papyrusRepresentationDescription;

    private ViewRepresentationDescriptionSearchService baseViewRepresentationDescriptionSearchService;

    public ViewRepresentationDescriptionSearchServiceCustomImpl(ViewRepresentationDescriptionSearchService baseViewRepresentationDescriptionSearchService,
            PapyrusRepresentationDescriptionRegistry papyrusRepresentationDescription) {
        this.baseViewRepresentationDescriptionSearchService = baseViewRepresentationDescriptionSearchService;
        this.papyrusRepresentationDescription = Objects.requireNonNull(papyrusRepresentationDescription);
    }

    @Override
    public Optional<RepresentationDescription> findById(IEditingContext editingContext, String representationDescriptionId) {
        Optional<RepresentationDescription> papyrusDescription = (Optional) this.papyrusRepresentationDescription.getViewDiagramDescriptionById(representationDescriptionId);
        if (papyrusDescription.isPresent()) {
            return papyrusDescription;
        } else {
            return this.baseViewRepresentationDescriptionSearchService.findById(editingContext, representationDescriptionId);
        }

    }

    @Override
    public Optional<NodeDescription> findViewNodeDescriptionById(IEditingContext editingContext, String nodeDescriptionId) {
        Optional<NodeDescription> papyrusDescription = this.papyrusRepresentationDescription.getViewNodeDescriptionById(nodeDescriptionId);
        if (papyrusDescription.isPresent()) {
            return papyrusDescription;
        } else {

            return this.baseViewRepresentationDescriptionSearchService.findViewNodeDescriptionById(editingContext, nodeDescriptionId);
        }

    }

    @Override
    public Optional<EdgeDescription> findViewEdgeDescriptionById(IEditingContext editingContext, String edgeDescriptionId) {

        Optional<EdgeDescription> papyrusDescription = this.papyrusRepresentationDescription.getViewEdgeDescriptionById(edgeDescriptionId);
        if (papyrusDescription.isPresent()) {
            return papyrusDescription;
        } else {
            return this.baseViewRepresentationDescriptionSearchService.findViewEdgeDescriptionById(editingContext, edgeDescriptionId);
        }

    }

    @Override
    public Optional<FormElementDescription> findViewFormElementDescriptionById(IEditingContext editingContext, String formDescriptionId) {
        return this.baseViewRepresentationDescriptionSearchService.findViewFormElementDescriptionById(editingContext, formDescriptionId);
    }

}
