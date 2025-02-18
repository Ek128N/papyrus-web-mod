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
package org.eclipse.papyrus.web.readonly;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.papyrus.uml.domain.services.EMFUtils;
import org.eclipse.papyrus.web.application.readonly.services.api.IPapyrusReadOnlyChecker;
import org.eclipse.papyrus.web.data.SimpleUMLProjectIdentifiers;
import org.eclipse.papyrus.web.profile.java.JavaProfileProvider;
import org.eclipse.papyrus.web.utils.AbstractIntegrationTest;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test read only service on UML editing context.
 *
 * @author Arthur Daussy
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PapyrusReadOnlyCheckerTests extends AbstractIntegrationTest {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IEditingContextSearchService editingContextSearchService;

    @Autowired
    private IPapyrusReadOnlyChecker readOnlyChecker;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @DisplayName("Check that the profile and library ressources are read only using the IPapyrusReadOnlyChecker service")
    @Sql(scripts = { "/scripts/uml-model-import-and-profile.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = { "/scripts/cleanup.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void checkReadOnlyService() {

        Optional<IEditingContext> editingContext = this.editingContextSearchService.findById(SimpleUMLProjectIdentifiers.UML_DEFAULT_EDITING_CONTEXT_ID.toString());
        assertTrue(editingContext.isPresent());
        assertTrue(editingContext.get() instanceof EditingContext);

        EditingContext siriusEditingContext = (EditingContext) editingContext.get();

        ResourceSet resourceSet = siriusEditingContext.getDomain().getResourceSet();
        for (Resource r : resourceSet.getResources()) {
            this.checkReadOnly(r, !r.getURI().toString().contains(SimpleUMLProjectIdentifiers.MODEL_DOCUMENT_ID.toString()));
        }

        // Load a read only resource for example a profile
        EObject profile = resourceSet.getEObject(URI.createURI(JavaProfileProvider.JAVA_PROFILE_URI), true);
        this.checkReadOnly(profile.eResource(), true);
    }

    private void checkReadOnly(Resource r, boolean expectedToBeReadOnly) {
        List<Notifier> notifiers = EMFUtils.eAllContentSteamWithSelf(r).toList();
        for (Notifier n : notifiers) {

            assertThat(this.readOnlyChecker.isReadOnly(n)).as(this.messageProvider(r, expectedToBeReadOnly)).isEqualTo(expectedToBeReadOnly);
        }
    }

    private Supplier<String> messageProvider(Resource r, boolean expectedToBeReadOnly) {
        return () -> {
            String message = "";
            String readOnlyPart = "";
            if (!expectedToBeReadOnly) {
                readOnlyPart = "not ";
            }
            if (r instanceof Resource) {
                message = MessageFormat.format("Resource {0} should {1}be read only", r.getURI().toFileString(), readOnlyPart);
            } else if (r instanceof EObject eObject) {
                message = MessageFormat.format("EObject {0} in ressource {1} should {2}be read only", r.getURIFragment(eObject), r.getURI().toFileString(), readOnlyPart);
            }
            return message;
        };
    }

}
