/*******************************************************************************
 * Copyright (c) 2024, 2025 CEA LIST.
 *
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

package org.eclipse.papyrus.web.application.representations.aqlservices.tables;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrus.uml.domain.services.properties.ILogger;
import org.eclipse.papyrus.uml.domain.services.properties.PropertiesStereotypeApplicationServices;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.web.application.tables.comment.UMLCommentTableRepresentationDescriptionBuilder;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.emf.tables.CursorBasedNavigationServices;
import org.eclipse.sirius.components.tables.ColumnFilter;
import org.eclipse.sirius.components.tables.descriptions.PaginatedData;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
import org.springframework.stereotype.Service;

/**
 * AQL services used in UML tables.
 * @author Jerome Gout
 */
@Service
public class TableService {

    private final ILogger logger;

    private final IObjectService objectService;

    private final CursorBasedNavigationServices cursorBasedNavigationServices;

    private final PropertiesStereotypeApplicationServices stereotypeApplicationServices;

    private final ObjectMapper objectMapper;

    public TableService(ILogger logger, IObjectService objectService, ObjectMapper objectMapper) {
        this.logger = Objects.requireNonNull(logger);
        this.objectService = Objects.requireNonNull(objectService);
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.cursorBasedNavigationServices = new CursorBasedNavigationServices();
        this.stereotypeApplicationServices = new PropertiesStereotypeApplicationServices(logger);
    }

    /**
     * Returns the label of a given UML element.
     * @param element an UML element
     * @return a String that is the label of the given element
     */
    public String getElementLabel(EObject element) {
        String label = this.objectService.getLabel(element);
        // Fallback for elements which are not NamedElement and not properly handled by the Edit framework
        if (label == null || label.isBlank()) {
            label = "<" + this.splitCamelCase(element.eClass().getName() + ">");
        }
        return label;
    }

    /**
     * 'MyClassName' => 'My Class Name'
     */
    private String splitCamelCase(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        result.append(input.charAt(0));

        for (int i = 1; i < input.length(); i++) {
            if (Character.isUpperCase(input.charAt(i))) {
                result.append(' ');
            }
            result.append(input.charAt(i));
        }
        return result.toString();
    }



    /** Returns a String that contains all annotated elements label of a given {@link Comment} separated by a given separators.
     * @param comment an UML Comment
     * @param separator a separator used between labels of the comment annotated elements.
     * @return a String that contains all annotated elements label of a given {@link Comment} separated by a given separators.
     */
    public String getCommentAnnotatedElementLabels(Comment comment, String separator) {
        return comment.getAnnotatedElements().stream()
                .map(this::getElementLabel)
                .collect(Collectors.joining(separator));
    }

    /**
     * Returns the icon path of the given UML element.
     * @param element an UML Element
     * @return the list of all paths associated to the given element
     */
    public List<String> getElementIconPath(Element element) {
        return this.objectService.getImagePath(element);
    }

    /**
     * Convert a numerical index to an alphabetic one.
     * @param indexObject an Integer index
     * @return the alphabetic index starting to "A" equivalent to the given index.
     */
    public String alphabetic(Integer indexObject) {
        return this.alphabetic(indexObject, 0);
    }

    public String alphabetic(Integer indexObject, int offset) {
        var index = indexObject + offset;
        if (index >= 0) {
            StringBuilder result = new StringBuilder();
            while (index >= 0) {
                result.insert(0, (char) ('A' + (index % 26)));
                index = (index / 26) - 1;
            }
            return result.toString();
        }
        return "";
    }

    /**
     * Returns the list of structural features (attribute or reference) that are used as column in the Comment table.
     * @param self a comment
     * @return the structural features that are presented as column in the Comment Table
     */
    public List<EStructuralFeature> getCommentColumns(EObject self) {
        return List.of(UMLPackage.eINSTANCE.getComment_Body(), UMLPackage.eINSTANCE.getComment_AnnotatedElement());
    }

    /**
     * Returns the label used in the column header of the table for the given structural feature.
     * @param self a structural feature that designate a column of the table.
     * @return the label of the column header of the table for the given structural feature.
     */
    public String getCommentColumnLabel(EStructuralFeature self) {
        String result = self.getName();
        if (UMLPackage.eINSTANCE.getComment_Body().getName().equals(self.getName())) {
            result = "Body";
        } else if (UMLPackage.eINSTANCE.getComment_AnnotatedElement().getName().equals(self.getName())) {
            result = "Annotated Elements";
        }
        return result;
    }

    /**
     * Returns the text value for a given structural feature (column) and a Comment (row).
     * @param self a UML element which is expected to be a Comment
     * @param columnFeature a structural feature of the given element
     * @return the textual value of the given structural feature of the given comment.
     */
    public String getCommentCellValue(EObject self, EStructuralFeature columnFeature) {
        String result = "";
        if (self instanceof Comment comment) {
            if (Objects.equals(columnFeature, UMLPackage.eINSTANCE.getComment_Body())) {
                result = comment.getBody();
            } else if (Objects.equals(columnFeature, UMLPackage.eINSTANCE.getComment_AnnotatedElement())) {
                result = this.getCommentAnnotatedElementLabels(comment, ", ");
            }
        }
        return result;
    }

    /**
     * Returns the list of semantic objects of the given self element through its feature given by name.
     *
     * @param self
     *         an UML element
     * @param featureName
     *         the structural feature that holds the semantic objects
     * @param globalFilter
     *         the global text that can filter semantic objects
     * @param columnFilters
     *         the column specific filters that can filter semantic objects
     * @param cursor
     *         last element rendered in the current page (last row if forward and first if backward)
     * @param direction
     *         the direction of the navigation
     * @param size
     *         the size of the table page (number of row chosen in the navigation control)
     * @return the list of semantic objects of the given self element through its feature given by name
     * this list is wrapped inside a pagination mechanism.
     */
    public PaginatedData getSemanticObjectsFromFeatureName(EObject self, String featureName, String globalFilter, List<ColumnFilter> columnFilters, EObject cursor, String direction, int size) {
        var result = new ArrayList<Object>();

        Predicate<EObject> predicate = this.getValidCommentCandidatePredicate(self, globalFilter, columnFilters);
        return this.cursorBasedNavigationServices.collect(self, cursor, direction, size, predicate);
    }

    /**
     * Delete the given UML element.
     *
     * @param self
     *         an element to delete
     * @return the deleted element
     */
    public EObject deleteElement(EObject self) {
        EcoreUtil.delete(self);
        return self;
    }

    private Predicate<EObject> getValidCommentCandidatePredicate(EObject self, String globalFilter, List<ColumnFilter> columnFilters) {
        return eObject -> {
            boolean isValidCandidate = eObject instanceof Comment && eObject.eContainer() == self; // only comments owned by the given element
            if (isValidCandidate) {
                var comment = (Comment) eObject;
                if (globalFilter != null && !globalFilter.isBlank()) {
                    isValidCandidate = comment.getBody() != null && comment.getBody().contains(globalFilter);
                    isValidCandidate = isValidCandidate || comment.getAnnotatedElements() != null && this.getCommentAnnotatedElementLabels(comment, "").contains(globalFilter);
                }
                isValidCandidate = isValidCandidate && columnFilters.stream().allMatch(columnFilter -> {
                    boolean isCandidate = true;
                    String columnFilterValue = this.getColumnFilterValue(columnFilter);
                    if (columnFilter.id().equals(UMLCommentTableRepresentationDescriptionBuilder.COMMENT_COLUMN_BODY)) {
                        isCandidate = comment.getBody() != null && comment.getBody().contains(columnFilterValue);
                    } else if (columnFilter.id().equals(UMLCommentTableRepresentationDescriptionBuilder.COMMENT_COLUMN_ANNOTATED_ELEMENTS)) {
                        String annotatedElementNames = this.getCommentAnnotatedElementLabels(comment, "");
                        isCandidate = !annotatedElementNames.isBlank() && annotatedElementNames.contains(columnFilterValue);
                    }
                    return isCandidate;
                });
            }
            return isValidCandidate;
        };
    }

    private String getColumnFilterValue(ColumnFilter columnFilter) {
        try {
            return this.objectMapper.readValue(columnFilter.value(), new TypeReference<>() {
            });
        } catch (JsonProcessingException exception) {
            this.logger.log(exception.getMessage(), ILogger.ILogLevel.WARNING);
        }
        return "";
    }

    /**
     * Returns all types found underneath the given UML Package.
     * @param umlPackage an UML Package
     * @return the list of types owned by the given package
     */
    public List<Type> getOwnedTypes(Package umlPackage) {
        return umlPackage.getOwnedTypes();
    }

    /**
     * Return whether the given type contains the filters text in one of the properties of one stereotype applied to this type.
     * @param type ab UML type
     * @param editingContext the current editing context used to retrieve semantic objects
     * @param globalFilter the global filter text
     * @param columnFilters the list of column filters
     * @return <code>true</code> if the given type contains the global filter text in one of the properties of one stereotype applied to this type and <code>false</code> otherwise.
     * if no filters are given returns <code>true</code> as well.
     */
    public boolean containsFilterValue(Type type, IEditingContext editingContext, String globalFilter, List<ColumnFilter> columnFilters) {
        boolean result = true;
        if (globalFilter != null && !globalFilter.isBlank()) {
            result = type.getStereotypeApplications().stream()
                    .anyMatch(stereotypeApplication ->
                            this.stereotypeApplicationServices.getAllFeatures(stereotypeApplication).stream()
                                    .anyMatch(eStructuralFeature -> this.containsInValue(stereotypeApplication, eStructuralFeature, globalFilter)));
        }
        result = result && columnFilters.stream().allMatch(columnFilter -> {
            boolean isMatching = true;
            String columnFilterValue = this.getColumnFilterValue(columnFilter);
            if (Objects.equals("stereotype", columnFilter.id())) {
                isMatching = String.join("", this.getStereotypeApplicationLabels(type)).contains(columnFilterValue);
            } else {
                isMatching = type.getStereotypeApplications().stream()
                        .anyMatch(stereotypeApplication -> {
                            return this.objectService.getObject(editingContext, columnFilter.id()).stream()
                                    .filter(EStructuralFeature.class::isInstance)
                                    .map(EStructuralFeature.class::cast)
                                    .map(eStructuralFeature -> this.getStereotypeCellValue(type, eStructuralFeature).toString().contains(columnFilterValue))
                                    .findFirst()
                                    .orElse(false);
                        });
            }
            return isMatching;
        });
        return result;
    }

    private boolean containsInValue(EObject stereotypeApplication, EStructuralFeature feature, String globalFilter) {
        return Optional.ofNullable(stereotypeApplication.eGet(feature)).stream()
                .map(o -> o.toString().contains(globalFilter))
                .findFirst()
                .orElse(false);
    }

    /**
     * Return the list of label of stereotype applications applied on the given element.
     * @param self an UML element
     * @return the list of label of stereotype applications applied on the given element separated by the given separator.
     */
    public List<String> getStereotypeApplicationLabels(Element self) {
        return self.getStereotypeApplications().stream()
                .map(EObject::eClass)
                .map(this.objectService::getLabel)
                .toList();
    }

    /**
     * Return a joined String of the given list of String and the given separator.
     * @param labels a list of String to join
     * @param separator the separator placed in between two strings
     * @return a joined String of the given list of String and the given separator.
     */
    public String join(List<String> labels, String separator) {
        return String.join(separator, labels);
    }

    /**
     * Return the list of all stereotypes applied to the given type.
     * @param type an UML type
     * @return the list of all stereotypes applied to the given type.
     */
    public List<Stereotype> getStereotypes(Type type) {
        return  type.getApplicableStereotypes().stream()
                .toList();
    }

    /**
     * Return the list of all distinct stereotype classes applied to the given type.
     * @param type an UML type
     * @return the list of all distinct stereotype classes applied to the given type.
     */
    public List<EClass> getStereotypeClasses(Type type) {
        return  type.getApplicableStereotypes().stream()
                .map(Stereotype::getDefinition)
                .toList();
    }

    /**
     * Return the list of all valid structural features defined by the given EClass.
     * @param eClass an EClass
     * @return  the list of all valid structural features defined by the given EClass.
     */
    public List<EStructuralFeature> getAllFeatures(EClass eClass) {
        return eClass.getEAllStructuralFeatures().stream()
                .filter(this::isValidFeature)
                .toList();
    }

    private boolean isValidFeature(EStructuralFeature feature) {
        return !feature.getName().startsWith("base_") && !feature.isTransient() && !feature.isDerived()
                && !this.isContainmentReference(feature);
    }

    private boolean isContainmentReference(EStructuralFeature feature) {
        if (feature instanceof EReference reference) {
            return reference.isContainment();
        }
        return false;
    }

    /**
     * Returns the content of the cell between the given element (row) and the given feature (column).
     * @param self a UML element
     * @param columnFeature a structural feature of a stereotype application of the given element
     * @return the textual value of the given structural feature or "N/A" if this feature is not available on the given element.
     */
    public Object getStereotypeCellValue(Element self, EStructuralFeature columnFeature) {
        EObject stereotypeApplication = this.getStereotypeApplicationAppliedOn(self, columnFeature);
        if (stereotypeApplication != null) {
            return this.stereotypeApplicationServices.getStereotypeFeatureValue(stereotypeApplication, columnFeature);
        } else {
            return "N/A";
        }
    }

    /**
     * Given an Element and a feature, return the stereotype application applied on the Element defining the feature.
     * @param self an Element
     * @param columnFeature a feature
     * @return the stereotype application applied on the given Element defining the given feature or <code>null</code> if the feature is not belonging to any stereotype application of the element.
     */
    private EObject getStereotypeApplicationAppliedOn(Element self, EStructuralFeature columnFeature) {
        EClass stereotypeApplicationEClass = columnFeature.getEContainingClass();
        return self.getStereotypeApplications().stream()
                .filter(eObject -> Objects.equals(eObject.eClass(), stereotypeApplicationEClass))
                .findFirst()
                .orElse(null);
    }
}