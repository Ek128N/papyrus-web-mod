/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.tables.ColumnFilter;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.UMLPackage;
import org.springframework.stereotype.Service;

/**
 * AQL services used in UML tables.
 * @author Jerome Gout
 */
@Service
public class TableService {

    private final IObjectService objectService;

    public TableService(IObjectService objectService) {
        this.objectService = Objects.requireNonNull(objectService);
    }

    /**
     * Returns the label of a given UML element.
     * @param element an UML element
     * @return a String that is the label of the given element
     */
    public String getElementLabel(Element element) {
        return this.objectService.getLabel(element);
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
        var index = indexObject;
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
     *  Returns the list of semantic objects of the given self element through its feature given by name.
     * @param self an UML element
     * @param featureName the structural feature that holds the semantic objects
     * @param globalFilter the global text that can filter semantic objects
     * @param columnFilters the column specific filters that can filter semantic objects
     * @return the list of semantic objects of the given self element through its feature given by name.
     */
    public List<EObject> getSemanticObjectsFromFeatureName(EObject self, String featureName, String globalFilter, List<ColumnFilter> columnFilters) {
        var result = new ArrayList<EObject>();

        Predicate<EObject> predicate = this.getValidCommentCandidatePredicate(self, globalFilter, columnFilters);
        var feature = self.eClass().getEStructuralFeature(featureName);
        if (feature.isMany()) {
            result.addAll(((List<EObject>) self.eGet(feature)).stream()
                    .filter(predicate)
                    .toList());
        } else {
            EObject object = (EObject) self.eGet(feature);
            if (predicate.test(object)) {
                result.add(object);
            }
        }
        return result;
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
                    if (columnFilter.id().equals(this.objectService.getId(UMLPackage.eINSTANCE.getComment_Body()))) {
                        isCandidate = comment.getBody() != null && comment.getBody().contains(columnFilter.value());
                    } else if (columnFilter.id().equals(this.objectService.getId(UMLPackage.eINSTANCE.getComment_AnnotatedElement()))) {
                        String annotatedElementNames = this.getCommentAnnotatedElementLabels(comment, "");
                        isCandidate = !annotatedElementNames.isBlank() && annotatedElementNames.contains(columnFilter.value());
                    }
                    return isCandidate;
                });
            }
            return isValidCandidate;
        };
    }
}
