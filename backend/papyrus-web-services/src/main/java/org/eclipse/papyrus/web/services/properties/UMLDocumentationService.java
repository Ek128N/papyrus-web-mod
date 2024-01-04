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
package org.eclipse.papyrus.web.services.properties;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.uml2.uml.UMLPackage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

/**
 * This class is used to load the documentation stored in "/org.eclipse.uml2.uml/model/UML.ecore" resource.
 *
 * @author Jerome Gout
 */
@Service
public class UMLDocumentationService {

    /**
     * Value of the "source" attribute to get the right EAnnotation which contains the documentation for each model
     * element.
     */
    private static final String SOURCE_ANNOTATION_GEN_MODEL = "http://www.eclipse.org/emf/2002/GenModel";

    private static final String SOURCE_ANNOTATION_DUPLICATES = "duplicates";

    /**
     * The key of the EAnnotation details map used to get the documentation for each model element.
     */
    private static final String DOCUMENTATION_ANNOTATION_DETAILS_KEY = "documentation";

    /**
     * The pattern used to match additional documentation from superclasses.
     */
    private static final String SUPERCLASS_DOCUMENTATION_PATTERN = "<p>.*</p>";

    /**
     * The example of valid formats for the multiplicity text widget, displayed in the Help content.
     */
    private static final String MULTIPLICITY_DOC_EXAMPLE = "Example of valid formats: 1, 0..12, 1..*, *";

    private Map<String, String> documentationEntries;

    public UMLDocumentationService() {
        this.documentationEntries = new HashMap<>();
        this.loadDocumentation();
    }

    private void loadDocumentation() {
        Resource resource = new XMIResourceImpl();
        try (var inputStream = new ClassPathResource("model/UML.ecore").getInputStream()) {
            resource.load(inputStream, Collections.emptyMap());
        } catch (IOException exception) {
            throw new WrappedException(exception);
        }
        EPackage uml2EPackage = (EPackage) resource.getContents().get(0);
        for (EClassifier eClassifier : uml2EPackage.getEClassifiers()) {
            if (eClassifier instanceof EClass) {
                EClass clazz = (EClass) eClassifier;
                for (EStructuralFeature feature : clazz.getEAllStructuralFeatures()) {
                    String doc = this.findDocumentation(clazz, feature);
                    String docKey = this.getDocumentationEntryKey(eClassifier.getName(), feature.getName());
                    this.documentationEntries.put(docKey, doc);
                }
            }
        }
        // store the MultiplicityElement root documentation
        String multiplicityElementTypeName = UMLPackage.eINSTANCE.getMultiplicityElement().getName();
        EClassifier multiplicityClassifier = uml2EPackage.getEClassifier(multiplicityElementTypeName);
        EAnnotation eAnnotation = multiplicityClassifier.getEAnnotation(SOURCE_ANNOTATION_GEN_MODEL);
        String description = this.extractDescriptionFromEAnnotation(eAnnotation).concat(MULTIPLICITY_DOC_EXAMPLE);
        String docKey = this.getDocumentationEntryKey(multiplicityElementTypeName, "");
        this.documentationEntries.put(docKey, description);
    }

    private String findDocumentation(EClass clazz, EStructuralFeature feature) {
        EAnnotation docAnnotation = feature.getEAnnotation(SOURCE_ANNOTATION_GEN_MODEL);
        // this feature may have redefined its documentation
        EAnnotation dupAnnotation = clazz.getEAnnotation(SOURCE_ANNOTATION_DUPLICATES);
        if (dupAnnotation != null) {
            EReference redefinedRef = dupAnnotation.getContents()//
                    .stream()//
                    .filter(EReference.class::isInstance)//
                    .map(EReference.class::cast)//
                    .filter(ref -> feature.getName().equals(ref.getName()))//
                    .findFirst().orElse(null);
            if (redefinedRef != null) {
                docAnnotation = redefinedRef.getEAnnotation(SOURCE_ANNOTATION_GEN_MODEL);
            }
        }
        return this.extractDescriptionFromEAnnotation(docAnnotation);
    }

    /**
     * Returns the documentation entry key associated to the given feature of the given classifier.
     *
     * @param classifier
     *            the name of the classifier.
     * @param featureName
     *            the name of the feature for which the documentation is looking for.
     * @return the documentation entry key for the given feature of the given classifier.
     */
    public String getDocumentationEntryKey(String classifierName, String featureName) {
        return classifierName + ":" + featureName;
    }

    /**
     * Returns the documentation associated to the given documentation entry key.
     *
     * @param entryKey
     *            the documentation entry key. See
     *            {@link UMLDocumentationService#getDocumentationEntryKey(EClassifier, String)} to build such a key.
     * @return the documentation associated to the given documentation entry key or {@code null} if no such
     *         documentation exists.
     */
    public String getDocumentation(String entryKey) {
        return this.documentationEntries.get(entryKey);
    }

    private String extractDescriptionFromEAnnotation(EAnnotation eAnnotation) {
        String description = "";
        if (eAnnotation != null) {
            description = eAnnotation.getDetails().get(DOCUMENTATION_ANNOTATION_DETAILS_KEY);
            if (description != null) {
                String regex = SUPERCLASS_DOCUMENTATION_PATTERN;
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(description);
                if (m.find()) {
                    description = description.replaceAll(m.group(0), "");
                }
            }
        }
        return description;
    }
}
