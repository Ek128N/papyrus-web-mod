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
package org.eclipse.papyrus.web.application.explorer.builder.aqlservices;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.papyrus.uml.domain.services.labels.UMLCharacters;
import org.eclipse.papyrus.web.application.explorer.AttributeTypeTreeItem;
import org.eclipse.papyrus.web.application.explorer.ImportedElementTreeItem;
import org.eclipse.papyrus.web.application.explorer.PapyrusTreeFilterProvider;
import org.eclipse.papyrus.web.application.readonly.services.api.IPapyrusReadOnlyChecker;
import org.eclipse.sirius.components.collaborative.api.IRepresentationImageProvider;
import org.eclipse.sirius.components.core.CoreImageConstants;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IRepresentationMetadataSearchService;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.views.explorer.services.ExplorerDescriptionProvider;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.projections.RepresentationDataMetadataOnly;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationDataSearchService;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ElementImport;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.PackageImport;
import org.eclipse.uml2.uml.ProfileApplication;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.StructuredClassifier;
import org.eclipse.uml2.uml.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Services used in the UML demo explorer Tree descriptions.
 *
 * @author Jerome Gout
 */
public class UMLDemoTreeServices {

    /**
     * ID prefix used for {@link ImportedElementTreeItem}.
     */
    // Shorten id as a workaround for bug https://github.com/eclipse-sirius/sirius-web/issues/4081
    public static final String PAPYRUS_IMPORTED_ELEMENT_PREFIX = "p://ie";

    /**
     * ID prefix used for {@link AttributeTypeTreeItem}.
     */
    // Shorten id as a workaround for bug https://github.com/eclipse-sirius/sirius-web/issues/4081
    public static final String PAPYRUS_ATTRIBUTE_TYPE_ELEMENT_PREFIX = "p://at";

    public static final String ATTRIBUTE_PARENT_ID = "classifierId";

    public static final String ATTRIBUTE_TYPE_ID = "typeId";

    /**
     * Id of the semantic element imported.
     */
    // Shorten id has a workaround for bug https://github.com/eclipse-sirius/sirius-web/issues/4081
    public static final String IMPORTED_ELEMENT_ID = "el";

    /**
     * Tree Item id of a {@link ImportedElementTreeItem}
     */
    private static final String ITEM_ID = "itemId";

    // Shorten id has a workaround for bug https://github.com/eclipse-sirius/sirius-web/issues/4081
    private static final String PARENT_ID = "pa";

    private static final Logger LOGGER = LoggerFactory.getLogger(UMLDemoTreeServices.class);

    private final List<IRepresentationImageProvider> representationImageProviders;

    private final IObjectService objectService;

    private final IRepresentationMetadataSearchService representationMetadataSearchService;

    private final IRepresentationDataSearchService representationDataSearchService;

    private IPapyrusReadOnlyChecker readOnlyChecker;

    private IURLParser urlParser;

    public UMLDemoTreeServices(List<IRepresentationImageProvider> representationImageProviders, IObjectService objectService,
            IRepresentationMetadataSearchService representationMetadataSearchService, IRepresentationDataSearchService representationDataSearchService, IPapyrusReadOnlyChecker readOnlyChecker,
            IURLParser urlParser) {
        super();
        this.representationImageProviders = Objects.requireNonNull(representationImageProviders);
        this.objectService = Objects.requireNonNull(objectService);
        this.representationMetadataSearchService = Objects.requireNonNull(representationMetadataSearchService);
        this.representationDataSearchService = Objects.requireNonNull(representationDataSearchService);
        this.readOnlyChecker = Objects.requireNonNull(readOnlyChecker);
        this.urlParser = Objects.requireNonNull(urlParser);
    }

    /**
     * Gets the list of children for the given item element.
     *
     * @param self
     *            the item element
     * @param editingContext
     *            the current {@link IEditingContext}
     * @param expandedIds
     *            the list of expanded elements
     * @return the children
     */
    public List<Object> getChildrenItems(Object self, IEditingContext editingContext, List<String> expandedIds, List<String> ancestorsIds, int index) {
        List<Object> result = new ArrayList<>();

        if (editingContext != null) {
            String id = this.getItemId(self);
            if (expandedIds.contains(id)) {
                if (self instanceof Resource resource) {
                    result.addAll(this.filterNonUMLElement(resource.getContents()));
                } else if (self instanceof StructuredClassifier classifier) {
                    result = new ArrayList<>();
                    result.addAll(this.getAttributeTypeTreeItem(classifier, ancestorsIds, index));
                } else if (self instanceof Element element) {
                    result.addAll(this.getElementDefaultChildren(element, id, editingContext, expandedIds, ancestorsIds, index));
                } else if (self instanceof ImportedElementTreeItem importTreeItem) {
                    this.getElementDefaultChildren(importTreeItem.importedElement(), id, editingContext, expandedIds, ancestorsIds, index).stream()
                            .map(c -> this.wrapToImportedElement(c, importTreeItem, ancestorsIds, index))
                            .filter(c -> c != null)
                            .forEach(result::add);
                } else if (self instanceof AttributeTypeTreeItem typeTreeItem) {
                    typeTreeItem.classifier().getOwnedAttributes().stream()
                            .filter(this.hasSameType(typeTreeItem))
                            .forEach(result::add);
                }
            }
        }
        return result;
    }

    private Predicate<? super Property> hasSameType(AttributeTypeTreeItem attributeType) {
        return prop -> {
            if (attributeType.type() != null) {
                return Objects.equals(attributeType.type(), prop.getType());
            } else {
                return prop.getType() == null;
            }
        };
    }

    /**
     * Checks if the given element is a {@link ImportedElementTreeItem}.
     *
     * @param o
     *            an object
     * @return <code>true</code> if is a {@link ImportedElementTreeItem}
     */
    public boolean isImportedElementItem(Object o) {
        return o instanceof ImportedElementTreeItem;
    }

    /**
     * Gets the label used in the "StereotypeApplication" part of a label.
     *
     * @param input
     *            a TreeItem
     * @return a label
     */
    public String getAppliedStereotypesLabel(Object input) {
        String label = "";
        if (input instanceof ImportedElementTreeItem item) {
            label = this.getAppliedStereotypesLabel(item.importedElement());
        } else if (input instanceof Element element) {
            EList<Stereotype> appliedStereotypes = element.getAppliedStereotypes();
            if (!appliedStereotypes.isEmpty()) {
                label = appliedStereotypes.stream().map(Stereotype::getName).collect(joining(", ", UMLCharacters.ST_LEFT, UMLCharacters.ST_RIGHT + " "));
            }
        }
        return label;
    }

    private Object wrapToImportedElement(Object c, ImportedElementTreeItem parent, List<String> ancestorsIds, int index) {
        final Object result;
        if (c instanceof Element childElement) {
            result = new ImportedElementTreeItem(childElement, parent.itemId(), this.computeUniqueId(ancestorsIds, index));
        } else if (c instanceof ImportedElementTreeItem childImportedElement) {
            result = childImportedElement;
        } else {
            result = null;
        }

        return result;
    }

    /**
     * Gets the default children for a basic {@link Element}.
     *
     * @param element
     *            the current element
     * @param elementId
     *            the id of the current element
     * @param editingContext
     *            the current {@link IEditingContext}
     * @param expandedIds
     *            the list of expanded elements
     * @param index
     *            index of item in its parent list
     * @param ancestorsIds
     *            list of all ancestor ids
     * @return the children
     */
    private List<Object> getElementDefaultChildren(Element element, String elementId, IEditingContext editingContext, List<String> expandedIds, List<String> ancestorsIds, int index) {
        List<Object> result = new ArrayList<>();

        result.addAll(this.getRepresentations(elementId, editingContext));
        result.addAll(this.getSemanticChildren(element));
        result.addAll(this.getComputedChildren(element, ancestorsIds, index));

        return result;

    }

    private List<Object> getComputedChildren(Element element, List<String> ancestorsIds, int index) {
        final List<Object> result;
        if (element instanceof PackageImport pImport && pImport.getImportedPackage() != null) {
            result = List.of(new ImportedElementTreeItem(pImport.getImportedPackage(), this.objectService.getId(pImport), this.computeUniqueId(ancestorsIds, index)));
        } else if (element instanceof ElementImport eImport && eImport.getImportedElement() != null) {
            result = List.of(new ImportedElementTreeItem(eImport.getImportedElement(), this.objectService.getId(eImport), this.computeUniqueId(ancestorsIds, index)));
        } else {
            result = List.of();
        }

        return result;
    }

    private List<AttributeTypeTreeItem> getAttributeTypeTreeItem(StructuredClassifier classifier, List<String> ancestorsIds, int index) {
        Set<AttributeTypeTreeItem> types = new HashSet<>();
        types = classifier.getOwnedAttributes().stream()
                .map(property -> new AttributeTypeTreeItem(classifier, property.getType()))
                .collect(Collectors.toSet());
        return List.copyOf(types);
    }

    private List<Object> getSemanticChildren(Element element) {
        List<Object> defaultChildren = this.filterNonUMLElement(this.objectService.getContents(element));

        return defaultChildren;
    }

    private String computeUniqueId(List<String> ancestorIds, int index) {
        return UUID.nameUUIDFromBytes((ancestorIds.stream().collect(joining("::")) + "#" + index).getBytes()).toString();
    }

    private List<Object> filterNonUMLElement(List<? extends Object> input) {
        // The main purpose of this filter is to remove
        // * EAnnotations
        // * StereotypeApplications
        return (List<Object>) input.stream().filter(Element.class::isInstance).collect(toList());
    }

    /**
     * Get all representations linked to the given semantic element id
     *
     * @param elementId
     *            the id
     * @param editingContext
     *            the current {@link IEditingContext}
     * @return a list of representation
     */
    private List<RepresentationMetadata> getRepresentations(String elementId, IEditingContext editingContext) {
        var representationMetadata = new ArrayList<>(this.representationMetadataSearchService.findAllByTargetObjectId(editingContext, elementId));
        representationMetadata.sort(Comparator.comparing(RepresentationMetadata::getLabel));
        return representationMetadata;
    }

    /**
     * Gets the root elements of the tree.
     *
     * @param self
     *            the context
     * @param activeFilterIds
     *            the active filters
     * @return a list of resources
     */
    public List<Resource> getRootElements(Object self, List<String> activeFilterIds) {
        if (self instanceof EditingContext emfWebContext) {
            boolean excludeRealOnlyResources = activeFilterIds.contains(PapyrusTreeFilterProvider.HIDE_PATHMAP_URI_TREE_ITEM_FILTER_ID);
            return emfWebContext.getDomain().getResourceSet().getResources().stream()
                    .filter(r -> !excludeRealOnlyResources || !this.readOnlyChecker.isReadOnly(r))
                    .sorted(Comparator.nullsLast(Comparator.comparing(this::getResourceLabel, String.CASE_INSENSITIVE_ORDER)))
                    .toList();
        }
        return Collections.emptyList();
    }

    /**
     * Gets the kind of the given tree item.
     *
     * @param self
     *            the current tree item
     * @return the kind of this element
     */
    public String getItemKind(Object self) {
        String kind = "";
        if (self instanceof RepresentationMetadata representationMetadata) {
            kind = representationMetadata.getKind();
        } else if (self instanceof Resource) {
            kind = ExplorerDescriptionProvider.DOCUMENT_KIND;
        } else if (self instanceof ImportedElementTreeItem) {
            kind = "ImportedElement";
        } else {
            kind = this.objectService.getKind(self);
        }
        return kind;
    }

    /**
     * Check if the given item has at least one child.
     *
     * @param self
     *            the current item
     * @return <code>true</code> if it has at least one children
     */
    public boolean hasChildren(Object self, List<String> ancestorsIds, int index) {
        boolean hasChildren = false;
        if (self instanceof Resource resource) {
            hasChildren = !resource.getContents().isEmpty();
        } else if (self instanceof Element element) {
            hasChildren = !this.getSemanticChildren(element).isEmpty()
                    || this.hasRepresentation(element)
                    || !this.getComputedChildren(element, ancestorsIds, index).isEmpty();
        } else if (self instanceof ImportedElementTreeItem importElement) {
            hasChildren = this.hasChildren(importElement.importedElement(), ancestorsIds, index);
        } else if (self instanceof AttributeTypeTreeItem) {
            hasChildren = true;
        }
        return hasChildren;
    }

    private boolean hasRepresentation(EObject self) {
        String id = this.objectService.getId(self);
        return this.representationDataSearchService.existAnyRepresentationForTargetObjectId(id);
    }

    /**
     * Compute the id of the given element.
     *
     * @param self
     *            the current tree item
     * @return an id
     */
    public String getItemId(Object self) {
        String id = null;
        if (self instanceof RepresentationMetadata representationMetadata) {
            id = representationMetadata.getId();
        } else if (self instanceof Resource resource) {
            id = resource.getURI().path().substring(1);
        } else if (self instanceof EObject) {
            id = this.objectService.getId(self);
        } else if (self instanceof ImportedElementTreeItem importElement) {
            id = this.getImporterElementId(importElement);
        } else if (self instanceof AttributeTypeTreeItem attributeType) {
            id = this.getAttributeTypeId(attributeType);
        }
        return id;
    }

    private String getImporterElementId(ImportedElementTreeItem importElement) {
        return UriComponentsBuilder.fromUriString(PAPYRUS_IMPORTED_ELEMENT_PREFIX)
                .queryParam(IMPORTED_ELEMENT_ID, this.objectService.getId(importElement.importedElement()))
                .queryParam(PARENT_ID, importElement.parentElementId())
                .queryParam(ITEM_ID, importElement.itemId())
                .build().toUri().toString();

    }

    private String getAttributeTypeId(AttributeTypeTreeItem attributeType) {
        String qualifiedName = "null";
        if (attributeType.type() != null) {
            qualifiedName = attributeType.type().getQualifiedName();
        }
        return UriComponentsBuilder.fromUriString(PAPYRUS_ATTRIBUTE_TYPE_ELEMENT_PREFIX)
                .queryParam(ATTRIBUTE_PARENT_ID, this.objectService.getId(attributeType.classifier()))
                .queryParam(ATTRIBUTE_TYPE_ID, qualifiedName)
                .build().toUri().toString();
    }

    /**
     * Gets the label of a tree item.
     *
     * @param self
     *            a tree item
     * @return a label
     */
    public String getItemLabel(Object self) {
        String label = "";

        if (self instanceof RepresentationMetadata representationMetadata) {
            label = representationMetadata.getLabel();
        } else if (self instanceof Resource resource) {
            label = this.getResourceLabel(resource);
        } else if (self instanceof Element element) {
            label = this.getElementLabel(element);
        } else if (self instanceof ImportedElementTreeItem importedElement) {
            label = this.getElementLabel(importedElement.importedElement());
        } else if (self instanceof AttributeTypeTreeItem attributeType) {
            if (attributeType.type() != null) {
                label = this.getElementLabel(attributeType.type());
            } else {
                label = "Untyped";
            }
        } else if (self instanceof EObject) {
            label = this.objectService.getLabel(self);
        }
        return label;
    }

    /**
     * Handle the label computation for UML elements.
     *
     * @param element
     *            an UML element
     * @return a label
     */
    private String getElementLabel(Element element) {
        final String mainLabel;

        if (element instanceof ProfileApplication pApplication) {
            mainLabel = "<Profile Application> " + this.objectService.getLabel(pApplication.getAppliedProfile());
        } else if (element instanceof PackageImport packageImport) {
            mainLabel = "<Package import> " + this.objectService.getLabel(packageImport.getImportedPackage());
        } else if (element instanceof ElementImport elementImport) {
            mainLabel = "<Element import> " + this.objectService.getLabel(elementImport.getImportedElement());
        } else {
            mainLabel = this.objectService.getLabel(element);
        }

        return mainLabel;
    }

    /**
     * Returns <code>true</code> if the {@link Element} or the {@link ImportedElementTreeItem#importedElement()} has a
     * 'isStatic' feature that return a <code>true</code> value.
     *
     * @param input
     *            a {@link TreeItem}
     * @return <code>true</code> if the element is static
     */
    public boolean isStatic(Object input) {
        boolean isStatic = false;
        if (input instanceof ImportedElementTreeItem item) {
            isStatic = this.isStatic(item.importedElement());
        } else if (input instanceof Element element) {
            EStructuralFeature staticFeature = element.eClass().getEStructuralFeature("isStatic");
            if (staticFeature != null) {
                isStatic = (boolean) element.eGet(staticFeature);
            }
        }
        return isStatic;
    }

    /**
     * Returns <code>true</code> if the {@link Element} or the {@link ImportedElementTreeItem#importedElement()} has a
     * 'isAbstract' feature that return a <code>true</code> value.
     *
     * @param input
     *            a {@link TreeItem}
     * @return <code>true</code> if the element is static
     */
    public boolean isAbstract(Object input) {
        boolean isAbstract = false;
        if (input instanceof ImportedElementTreeItem item) {
            isAbstract = this.isStatic(item.importedElement());
        } else if (input instanceof Element element) {
            EStructuralFeature staticFeature = element.eClass().getEStructuralFeature("isAbstract");
            if (staticFeature != null) {
                isAbstract = (boolean) element.eGet(staticFeature);
            }
        }
        return isAbstract;
    }

    /**
     * Gets a object from its id.
     *
     * @param treeItemId
     *            an id
     * @param editingContext
     *            the current editing context
     * @return an object or <code>null</code>
     */
    public Object toObject(String treeItemId, IEditingContext editingContext) {
        Object result = null;
        // Handle here the importedElements
        if (treeItemId != null && treeItemId.startsWith(PAPYRUS_IMPORTED_ELEMENT_PREFIX)) {
            result = this.fromImportedElementId(editingContext, treeItemId);
        } else if (treeItemId != null && treeItemId.startsWith(PAPYRUS_ATTRIBUTE_TYPE_ELEMENT_PREFIX)) {
            result = this.fromAttributeTypeId(editingContext, treeItemId);
        } else {
            var optionalObject = this.objectService.getObject(editingContext, treeItemId);
            if (optionalObject.isPresent()) {
                result = optionalObject.get();
            } else {
                var optionalEditingDomain = Optional.of(editingContext)
                        .filter(IEMFEditingContext.class::isInstance)
                        .map(IEMFEditingContext.class::cast)
                        .map(IEMFEditingContext::getDomain);

                if (optionalEditingDomain.isPresent()) {
                    var editingDomain = optionalEditingDomain.get();
                    ResourceSet resourceSet = editingDomain.getResourceSet();
                    URI uri = new JSONResourceFactory().createResourceURI(treeItemId);

                    result = resourceSet.getResources().stream()
                            .filter(resource -> resource.getURI().equals(uri))
                            .findFirst()
                            .orElse(null);
                }
            }
        }
        return result;
    }

    /**
     * Creates a new {@link ImportedElementTreeItem} from a given id
     *
     * @param parser
     *            an Id parser
     * @param editingContext
     *            the current {@link IEditingContext}
     * @param objectService
     *            the IDefaultObjectSearchService
     * @param itemId
     * @return
     */
    private ImportedElementTreeItem fromImportedElementId(IEditingContext editingContext, String itemId) {
        ImportedElementTreeItem result = null;
        try {
            Map<String, List<String>> parameters = this.urlParser.getParameterValues(itemId);
            if (parameters != null) {
                List<String> parentIds = parameters.get(PARENT_ID);
                Optional<Element> aImportedElement = this.toElement(parameters, IMPORTED_ELEMENT_ID, editingContext);

                List<String> importItemIts = parameters.get(ITEM_ID);

                if (this.isNonEmpty(parentIds) && aImportedElement.isPresent() && this.isNonEmpty(importItemIts)) {
                    result = new ImportedElementTreeItem(aImportedElement.get(), parentIds.get(0), importItemIts.get(0));
                }
            }

        } catch (IllegalStateException e) {
            LOGGER.warn("Invalid importedElement id {} : {}", itemId, e.getCause());
        }

        if (result == null) {
            LOGGER.warn("Invalid importedElement id {}", itemId);
        }

        return result;
    }

    private Optional<Element> toElement(Map<String, List<String>> parameters, String parameterName, IEditingContext editingContext) {
        List<String> id = parameters.get(parameterName);
        if (this.isNonEmpty(id)) {
            return this.objectService.getObject(editingContext, id.get(0))
                    .filter(Element.class::isInstance)
                    .map(Element.class::cast);
        }
        return Optional.empty();
    }

    private boolean isNonEmpty(Collection<?> collection) {
        return collection != null && !collection.isEmpty();
    }

    private AttributeTypeTreeItem fromAttributeTypeId(IEditingContext editingContext, String itemId) {
        AttributeTypeTreeItem result = null;
        try {
            Map<String, List<String>> parameters = this.urlParser.getParameterValues(itemId);
            if (parameters != null) {
                Optional<StructuredClassifier> classifier = this.toClassifier(parameters, editingContext);
                Optional<Type> type = this.toType(parameters, editingContext);

                if (classifier.isPresent()) {
                    result = new AttributeTypeTreeItem(classifier.get(), type.orElse(null));
                }
            }

        } catch (IllegalStateException e) {
            LOGGER.warn("Invalid attributeType id {} : {}", itemId, e.getCause());
        }

        if (result == null) {
            LOGGER.warn("Invalid attributeType id {}", itemId);
        }
        return result;
    }

    private Optional<StructuredClassifier> toClassifier(Map<String, List<String>> parameters, IEditingContext editingContext) {
        List<String> id = parameters.get(ATTRIBUTE_PARENT_ID);
        if (this.isNonEmpty(id)) {
            return this.objectService.getObject(editingContext, id.get(0))
                    .filter(StructuredClassifier.class::isInstance)
                    .map(StructuredClassifier.class::cast);
        }
        return Optional.empty();
    }

    private Optional<Type> toType(Map<String, List<String>> parameters, IEditingContext editingContext) {
        List<String> id = parameters.get(ATTRIBUTE_TYPE_ID);
        if (this.isNonEmpty(id)) {
            return this.objectService.getObject(editingContext, id.get(0))
                    .filter(Type.class::isInstance)
                    .map(Type.class::cast);
        }
        return Optional.empty();
    }

    /**
     * Gets the list of all icons to be applied on tree item.
     *
     * @param self
     *            the tree item
     * @return a list of icons URL
     */
    public List<String> getIconURLs(Object self) {
        List<String> imageURL = List.of(CoreImageConstants.DEFAULT_SVG);
        if (self instanceof Property property) {
            imageURL = this.objectService.getImagePath(property);
            if (property.getType() == null) {
                // objectService returns a immutable list
                imageURL = new ArrayList<>(imageURL);
                imageURL.add("/images/property-no-type.svg");
            }
        } else if (self instanceof EObject) {
            imageURL = this.objectService.getImagePath(self);
        } else if (self instanceof RepresentationMetadata representationMetadata) {
            imageURL = this.representationImageProviders.stream()
                    .map(representationImageProvider -> representationImageProvider.getImageURL(representationMetadata.getKind()))
                    .flatMap(Optional::stream)
                    .toList();
        } else if (self instanceof Resource) {
            imageURL = List.of("/explorer/Resource.svg");
        } else if (self instanceof ImportedElementTreeItem importedElement) {
            imageURL = this.objectService.getImagePath(importedElement.importedElement());
        } else if (self instanceof AttributeTypeTreeItem) {
            imageURL = List.of("/images/papyrus-icon.png");
        }
        return imageURL;
    }

    /**
     * Checks if an element can be deleted.
     *
     * @param self
     *            the tree item
     * @return true if the element can be deleted
     */
    public boolean canBeDeleted(Object self) {
        return !this.readOnlyChecker.isReadOnly(self) && !(self instanceof ImportedElementTreeItem) && !(self instanceof AttributeTypeTreeItem);
    }

    /**
     * Checks if an element can be renamed.
     *
     * @param self
     *            the tree item
     * @return true if the element can be renamed
     */
    public boolean canBeRenamed(Object self) {
        return !this.readOnlyChecker.isReadOnly(self) && !(self instanceof ImportedElementTreeItem) && !(self instanceof AttributeTypeTreeItem);
    }

    /**
     * Gets the parent item of the given item.
     *
     * @param self
     *            the current item
     * @param treeItemId
     *            the id of the current item
     * @param editingContext
     *            the editing context
     * @return the parent item or null
     */
    public Object getParentItem(Object self, String treeItemId, IEditingContext editingContext) {
        Object result = null;

        if (self instanceof RepresentationMetadata) {
            var optionalRepresentationMetadata = new UUIDParser().parse(treeItemId).flatMap(this.representationDataSearchService::findMetadataById);
            var repId = optionalRepresentationMetadata.map(RepresentationDataMetadataOnly::targetObjectId).orElse(null);
            result = this.objectService.getObject(editingContext, repId);
        } else if (self instanceof EObject eObject) {
            Object semanticContainer = eObject.eContainer();
            if (semanticContainer == null) {
                semanticContainer = eObject.eResource();
            }
            result = semanticContainer;
        } else if (self instanceof AttributeTypeTreeItem attributeType) {
            result = attributeType.classifier();
        }
        return result;
    }

    private String getResourceLabel(Resource resource) {
        return resource.eAdapters().stream()
                .filter(ResourceMetadataAdapter.class::isInstance)
                .map(ResourceMetadataAdapter.class::cast)
                .findFirst()
                .map(ResourceMetadataAdapter::getName)
                .orElse(resource.getURI().lastSegment());
    }

    public Object capitalizeNamedElement(NamedElement namedElement) {
        var name = namedElement.getName();
        namedElement.setName(name.substring(0, 1).toUpperCase() + name.substring(1));
        return namedElement;
    }

    public String getGenerateJavaCodeURL(Model self, EditingContext editingContext) {
        return this.getGenerateCodeURL(self, editingContext, "Java");
    }

    public String getGenerateCPlusPlusCodeURL(Model self, EditingContext editingContext) {
        return this.getGenerateCodeURL(self, editingContext, "C%2B%2B");
    }

    public String getGeneratePythonCodeURL(Model self, EditingContext editingContext) {
        return this.getGenerateCodeURL(self, editingContext, "Python");
    }

    private String getGenerateCodeURL(Model self, EditingContext editingContext, String language) {
        var resourceId = this.objectService.getId(self.eResource());
        return new StringBuilder()
                .append("http://localhost:8083/generateCode?project_id=")
                .append(editingContext.getId())
                .append("&tree_item_id=")
                .append(resourceId)
                .append("&output_language=")
                .append(language)
                .toString();
    }
}
