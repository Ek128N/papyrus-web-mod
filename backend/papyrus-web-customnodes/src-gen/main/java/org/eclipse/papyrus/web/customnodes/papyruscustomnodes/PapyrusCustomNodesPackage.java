/**
 * Copyright (c) 2023, 2024 CEA LIST, Obeo
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - Initial API and implementation
 */
package org.eclipse.papyrus.web.customnodes.papyruscustomnodes;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each operation of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.papyrus.web.customnodes.papyruscustomnodes.PapyrusCustomNodesFactory
 * @model kind="package"
 * @generated
 */
public interface PapyrusCustomNodesPackage extends EPackage {
    /**
     * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNAME = "papyruscustomnodes";

    /**
     * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_URI = "http://www.eclipse.org/papyrus-web/customnodes";

    /**
     * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_PREFIX = "papyruscustomnodes";

    /**
     * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    PapyrusCustomNodesPackage eINSTANCE = org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.PapyrusCustomNodesPackageImpl.init();

    /**
     * The meta object id for the
     * '{@link org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.EllipseNodeStyleDescriptionImpl <em>Ellipse
     * Node Style Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.EllipseNodeStyleDescriptionImpl
     * @see org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.PapyrusCustomNodesPackageImpl#getEllipseNodeStyleDescription()
     * @generated
     */
    int ELLIPSE_NODE_STYLE_DESCRIPTION = 0;

    /**
     * The feature id for the '<em><b>Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ELLIPSE_NODE_STYLE_DESCRIPTION__COLOR = DiagramPackage.NODE_STYLE_DESCRIPTION__COLOR;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ELLIPSE_NODE_STYLE_DESCRIPTION__FONT_SIZE = DiagramPackage.NODE_STYLE_DESCRIPTION__FONT_SIZE;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ELLIPSE_NODE_STYLE_DESCRIPTION__ITALIC = DiagramPackage.NODE_STYLE_DESCRIPTION__ITALIC;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ELLIPSE_NODE_STYLE_DESCRIPTION__BOLD = DiagramPackage.NODE_STYLE_DESCRIPTION__BOLD;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ELLIPSE_NODE_STYLE_DESCRIPTION__UNDERLINE = DiagramPackage.NODE_STYLE_DESCRIPTION__UNDERLINE;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ELLIPSE_NODE_STYLE_DESCRIPTION__STRIKE_THROUGH = DiagramPackage.NODE_STYLE_DESCRIPTION__STRIKE_THROUGH;

    /**
     * The feature id for the '<em><b>Border Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ELLIPSE_NODE_STYLE_DESCRIPTION__BORDER_COLOR = DiagramPackage.NODE_STYLE_DESCRIPTION__BORDER_COLOR;

    /**
     * The feature id for the '<em><b>Border Radius</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ELLIPSE_NODE_STYLE_DESCRIPTION__BORDER_RADIUS = DiagramPackage.NODE_STYLE_DESCRIPTION__BORDER_RADIUS;

    /**
     * The feature id for the '<em><b>Border Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ELLIPSE_NODE_STYLE_DESCRIPTION__BORDER_SIZE = DiagramPackage.NODE_STYLE_DESCRIPTION__BORDER_SIZE;

    /**
     * The feature id for the '<em><b>Border Line Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int ELLIPSE_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE = DiagramPackage.NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE;

    /**
     * The feature id for the '<em><b>Label Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ELLIPSE_NODE_STYLE_DESCRIPTION__LABEL_COLOR = DiagramPackage.NODE_STYLE_DESCRIPTION__LABEL_COLOR;

    /**
     * The feature id for the '<em><b>Show Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ELLIPSE_NODE_STYLE_DESCRIPTION__SHOW_ICON = DiagramPackage.NODE_STYLE_DESCRIPTION__SHOW_ICON;

    /**
     * The feature id for the '<em><b>Label Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ELLIPSE_NODE_STYLE_DESCRIPTION__LABEL_ICON = DiagramPackage.NODE_STYLE_DESCRIPTION__LABEL_ICON;

    /**
     * The number of structural features of the '<em>Ellipse Node Style Description</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ELLIPSE_NODE_STYLE_DESCRIPTION_FEATURE_COUNT = DiagramPackage.NODE_STYLE_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Ellipse Node Style Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ELLIPSE_NODE_STYLE_DESCRIPTION_OPERATION_COUNT = DiagramPackage.NODE_STYLE_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.PackageNodeStyleDescriptionImpl <em>Package
     * Node Style Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.PackageNodeStyleDescriptionImpl
     * @see org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.PapyrusCustomNodesPackageImpl#getPackageNodeStyleDescription()
     * @generated
     */
    int PACKAGE_NODE_STYLE_DESCRIPTION = 1;

    /**
     * The feature id for the '<em><b>Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PACKAGE_NODE_STYLE_DESCRIPTION__COLOR = DiagramPackage.NODE_STYLE_DESCRIPTION__COLOR;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PACKAGE_NODE_STYLE_DESCRIPTION__FONT_SIZE = DiagramPackage.NODE_STYLE_DESCRIPTION__FONT_SIZE;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PACKAGE_NODE_STYLE_DESCRIPTION__ITALIC = DiagramPackage.NODE_STYLE_DESCRIPTION__ITALIC;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PACKAGE_NODE_STYLE_DESCRIPTION__BOLD = DiagramPackage.NODE_STYLE_DESCRIPTION__BOLD;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PACKAGE_NODE_STYLE_DESCRIPTION__UNDERLINE = DiagramPackage.NODE_STYLE_DESCRIPTION__UNDERLINE;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PACKAGE_NODE_STYLE_DESCRIPTION__STRIKE_THROUGH = DiagramPackage.NODE_STYLE_DESCRIPTION__STRIKE_THROUGH;

    /**
     * The feature id for the '<em><b>Border Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PACKAGE_NODE_STYLE_DESCRIPTION__BORDER_COLOR = DiagramPackage.NODE_STYLE_DESCRIPTION__BORDER_COLOR;

    /**
     * The feature id for the '<em><b>Border Radius</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PACKAGE_NODE_STYLE_DESCRIPTION__BORDER_RADIUS = DiagramPackage.NODE_STYLE_DESCRIPTION__BORDER_RADIUS;

    /**
     * The feature id for the '<em><b>Border Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PACKAGE_NODE_STYLE_DESCRIPTION__BORDER_SIZE = DiagramPackage.NODE_STYLE_DESCRIPTION__BORDER_SIZE;

    /**
     * The feature id for the '<em><b>Border Line Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int PACKAGE_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE = DiagramPackage.NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE;

    /**
     * The feature id for the '<em><b>Label Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PACKAGE_NODE_STYLE_DESCRIPTION__LABEL_COLOR = DiagramPackage.NODE_STYLE_DESCRIPTION__LABEL_COLOR;

    /**
     * The feature id for the '<em><b>Show Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PACKAGE_NODE_STYLE_DESCRIPTION__SHOW_ICON = DiagramPackage.NODE_STYLE_DESCRIPTION__SHOW_ICON;

    /**
     * The feature id for the '<em><b>Label Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PACKAGE_NODE_STYLE_DESCRIPTION__LABEL_ICON = DiagramPackage.NODE_STYLE_DESCRIPTION__LABEL_ICON;

    /**
     * The number of structural features of the '<em>Package Node Style Description</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PACKAGE_NODE_STYLE_DESCRIPTION_FEATURE_COUNT = DiagramPackage.NODE_STYLE_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Package Node Style Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PACKAGE_NODE_STYLE_DESCRIPTION_OPERATION_COUNT = DiagramPackage.NODE_STYLE_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.RectangleWithExternalLabelNodeStyleDescriptionImpl
     * <em>Rectangle With External Label Node Style Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.RectangleWithExternalLabelNodeStyleDescriptionImpl
     * @see org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.PapyrusCustomNodesPackageImpl#getRectangleWithExternalLabelNodeStyleDescription()
     * @generated
     */
    int RECTANGLE_WITH_EXTERNAL_LABEL_NODE_STYLE_DESCRIPTION = 2;

    /**
     * The feature id for the '<em><b>Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGLE_WITH_EXTERNAL_LABEL_NODE_STYLE_DESCRIPTION__COLOR = DiagramPackage.NODE_STYLE_DESCRIPTION__COLOR;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGLE_WITH_EXTERNAL_LABEL_NODE_STYLE_DESCRIPTION__FONT_SIZE = DiagramPackage.NODE_STYLE_DESCRIPTION__FONT_SIZE;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGLE_WITH_EXTERNAL_LABEL_NODE_STYLE_DESCRIPTION__ITALIC = DiagramPackage.NODE_STYLE_DESCRIPTION__ITALIC;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGLE_WITH_EXTERNAL_LABEL_NODE_STYLE_DESCRIPTION__BOLD = DiagramPackage.NODE_STYLE_DESCRIPTION__BOLD;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGLE_WITH_EXTERNAL_LABEL_NODE_STYLE_DESCRIPTION__UNDERLINE = DiagramPackage.NODE_STYLE_DESCRIPTION__UNDERLINE;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGLE_WITH_EXTERNAL_LABEL_NODE_STYLE_DESCRIPTION__STRIKE_THROUGH = DiagramPackage.NODE_STYLE_DESCRIPTION__STRIKE_THROUGH;

    /**
     * The feature id for the '<em><b>Border Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGLE_WITH_EXTERNAL_LABEL_NODE_STYLE_DESCRIPTION__BORDER_COLOR = DiagramPackage.NODE_STYLE_DESCRIPTION__BORDER_COLOR;

    /**
     * The feature id for the '<em><b>Border Radius</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGLE_WITH_EXTERNAL_LABEL_NODE_STYLE_DESCRIPTION__BORDER_RADIUS = DiagramPackage.NODE_STYLE_DESCRIPTION__BORDER_RADIUS;

    /**
     * The feature id for the '<em><b>Border Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGLE_WITH_EXTERNAL_LABEL_NODE_STYLE_DESCRIPTION__BORDER_SIZE = DiagramPackage.NODE_STYLE_DESCRIPTION__BORDER_SIZE;

    /**
     * The feature id for the '<em><b>Border Line Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int RECTANGLE_WITH_EXTERNAL_LABEL_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE = DiagramPackage.NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE;

    /**
     * The feature id for the '<em><b>Label Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGLE_WITH_EXTERNAL_LABEL_NODE_STYLE_DESCRIPTION__LABEL_COLOR = DiagramPackage.NODE_STYLE_DESCRIPTION__LABEL_COLOR;

    /**
     * The feature id for the '<em><b>Show Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGLE_WITH_EXTERNAL_LABEL_NODE_STYLE_DESCRIPTION__SHOW_ICON = DiagramPackage.NODE_STYLE_DESCRIPTION__SHOW_ICON;

    /**
     * The feature id for the '<em><b>Label Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGLE_WITH_EXTERNAL_LABEL_NODE_STYLE_DESCRIPTION__LABEL_ICON = DiagramPackage.NODE_STYLE_DESCRIPTION__LABEL_ICON;

    /**
     * The number of structural features of the '<em>Rectangle With External Label Node Style Description</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGLE_WITH_EXTERNAL_LABEL_NODE_STYLE_DESCRIPTION_FEATURE_COUNT = DiagramPackage.NODE_STYLE_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Rectangle With External Label Node Style Description</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGLE_WITH_EXTERNAL_LABEL_NODE_STYLE_DESCRIPTION_OPERATION_COUNT = DiagramPackage.NODE_STYLE_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.NoteNodeStyleDescriptionImpl <em>Note Node
     * Style Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.NoteNodeStyleDescriptionImpl
     * @see org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.PapyrusCustomNodesPackageImpl#getNoteNodeStyleDescription()
     * @generated
     */
    int NOTE_NODE_STYLE_DESCRIPTION = 3;

    /**
     * The feature id for the '<em><b>Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NOTE_NODE_STYLE_DESCRIPTION__COLOR = DiagramPackage.NODE_STYLE_DESCRIPTION__COLOR;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NOTE_NODE_STYLE_DESCRIPTION__FONT_SIZE = DiagramPackage.NODE_STYLE_DESCRIPTION__FONT_SIZE;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NOTE_NODE_STYLE_DESCRIPTION__ITALIC = DiagramPackage.NODE_STYLE_DESCRIPTION__ITALIC;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NOTE_NODE_STYLE_DESCRIPTION__BOLD = DiagramPackage.NODE_STYLE_DESCRIPTION__BOLD;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NOTE_NODE_STYLE_DESCRIPTION__UNDERLINE = DiagramPackage.NODE_STYLE_DESCRIPTION__UNDERLINE;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NOTE_NODE_STYLE_DESCRIPTION__STRIKE_THROUGH = DiagramPackage.NODE_STYLE_DESCRIPTION__STRIKE_THROUGH;

    /**
     * The feature id for the '<em><b>Border Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NOTE_NODE_STYLE_DESCRIPTION__BORDER_COLOR = DiagramPackage.NODE_STYLE_DESCRIPTION__BORDER_COLOR;

    /**
     * The feature id for the '<em><b>Border Radius</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NOTE_NODE_STYLE_DESCRIPTION__BORDER_RADIUS = DiagramPackage.NODE_STYLE_DESCRIPTION__BORDER_RADIUS;

    /**
     * The feature id for the '<em><b>Border Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NOTE_NODE_STYLE_DESCRIPTION__BORDER_SIZE = DiagramPackage.NODE_STYLE_DESCRIPTION__BORDER_SIZE;

    /**
     * The feature id for the '<em><b>Border Line Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int NOTE_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE = DiagramPackage.NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE;

    /**
     * The feature id for the '<em><b>Label Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NOTE_NODE_STYLE_DESCRIPTION__LABEL_COLOR = DiagramPackage.NODE_STYLE_DESCRIPTION__LABEL_COLOR;

    /**
     * The feature id for the '<em><b>Show Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NOTE_NODE_STYLE_DESCRIPTION__SHOW_ICON = DiagramPackage.NODE_STYLE_DESCRIPTION__SHOW_ICON;

    /**
     * The feature id for the '<em><b>Label Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NOTE_NODE_STYLE_DESCRIPTION__LABEL_ICON = DiagramPackage.NODE_STYLE_DESCRIPTION__LABEL_ICON;

    /**
     * The number of structural features of the '<em>Note Node Style Description</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NOTE_NODE_STYLE_DESCRIPTION_FEATURE_COUNT = DiagramPackage.NODE_STYLE_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Note Node Style Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NOTE_NODE_STYLE_DESCRIPTION_OPERATION_COUNT = DiagramPackage.NODE_STYLE_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.InnerFlagNodeStyleDescriptionImpl <em>Inner
     * Flag Node Style Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.InnerFlagNodeStyleDescriptionImpl
     * @see org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.PapyrusCustomNodesPackageImpl#getInnerFlagNodeStyleDescription()
     * @generated
     */
    int INNER_FLAG_NODE_STYLE_DESCRIPTION = 4;

    /**
     * The feature id for the '<em><b>Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int INNER_FLAG_NODE_STYLE_DESCRIPTION__COLOR = DiagramPackage.NODE_STYLE_DESCRIPTION__COLOR;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int INNER_FLAG_NODE_STYLE_DESCRIPTION__FONT_SIZE = DiagramPackage.NODE_STYLE_DESCRIPTION__FONT_SIZE;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int INNER_FLAG_NODE_STYLE_DESCRIPTION__ITALIC = DiagramPackage.NODE_STYLE_DESCRIPTION__ITALIC;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int INNER_FLAG_NODE_STYLE_DESCRIPTION__BOLD = DiagramPackage.NODE_STYLE_DESCRIPTION__BOLD;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int INNER_FLAG_NODE_STYLE_DESCRIPTION__UNDERLINE = DiagramPackage.NODE_STYLE_DESCRIPTION__UNDERLINE;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int INNER_FLAG_NODE_STYLE_DESCRIPTION__STRIKE_THROUGH = DiagramPackage.NODE_STYLE_DESCRIPTION__STRIKE_THROUGH;

    /**
     * The feature id for the '<em><b>Border Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int INNER_FLAG_NODE_STYLE_DESCRIPTION__BORDER_COLOR = DiagramPackage.NODE_STYLE_DESCRIPTION__BORDER_COLOR;

    /**
     * The feature id for the '<em><b>Border Radius</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int INNER_FLAG_NODE_STYLE_DESCRIPTION__BORDER_RADIUS = DiagramPackage.NODE_STYLE_DESCRIPTION__BORDER_RADIUS;

    /**
     * The feature id for the '<em><b>Border Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int INNER_FLAG_NODE_STYLE_DESCRIPTION__BORDER_SIZE = DiagramPackage.NODE_STYLE_DESCRIPTION__BORDER_SIZE;

    /**
     * The feature id for the '<em><b>Border Line Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int INNER_FLAG_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE = DiagramPackage.NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE;

    /**
     * The feature id for the '<em><b>Label Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int INNER_FLAG_NODE_STYLE_DESCRIPTION__LABEL_COLOR = DiagramPackage.NODE_STYLE_DESCRIPTION__LABEL_COLOR;

    /**
     * The feature id for the '<em><b>Show Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int INNER_FLAG_NODE_STYLE_DESCRIPTION__SHOW_ICON = DiagramPackage.NODE_STYLE_DESCRIPTION__SHOW_ICON;

    /**
     * The feature id for the '<em><b>Label Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int INNER_FLAG_NODE_STYLE_DESCRIPTION__LABEL_ICON = DiagramPackage.NODE_STYLE_DESCRIPTION__LABEL_ICON;

    /**
     * The number of structural features of the '<em>Inner Flag Node Style Description</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int INNER_FLAG_NODE_STYLE_DESCRIPTION_FEATURE_COUNT = DiagramPackage.NODE_STYLE_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Inner Flag Node Style Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int INNER_FLAG_NODE_STYLE_DESCRIPTION_OPERATION_COUNT = DiagramPackage.NODE_STYLE_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.OuterFlagNodeStyleDescriptionImpl <em>Outer
     * Flag Node Style Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.OuterFlagNodeStyleDescriptionImpl
     * @see org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.PapyrusCustomNodesPackageImpl#getOuterFlagNodeStyleDescription()
     * @generated
     */
    int OUTER_FLAG_NODE_STYLE_DESCRIPTION = 5;

    /**
     * The feature id for the '<em><b>Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OUTER_FLAG_NODE_STYLE_DESCRIPTION__COLOR = DiagramPackage.NODE_STYLE_DESCRIPTION__COLOR;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OUTER_FLAG_NODE_STYLE_DESCRIPTION__FONT_SIZE = DiagramPackage.NODE_STYLE_DESCRIPTION__FONT_SIZE;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OUTER_FLAG_NODE_STYLE_DESCRIPTION__ITALIC = DiagramPackage.NODE_STYLE_DESCRIPTION__ITALIC;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OUTER_FLAG_NODE_STYLE_DESCRIPTION__BOLD = DiagramPackage.NODE_STYLE_DESCRIPTION__BOLD;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OUTER_FLAG_NODE_STYLE_DESCRIPTION__UNDERLINE = DiagramPackage.NODE_STYLE_DESCRIPTION__UNDERLINE;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OUTER_FLAG_NODE_STYLE_DESCRIPTION__STRIKE_THROUGH = DiagramPackage.NODE_STYLE_DESCRIPTION__STRIKE_THROUGH;

    /**
     * The feature id for the '<em><b>Border Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OUTER_FLAG_NODE_STYLE_DESCRIPTION__BORDER_COLOR = DiagramPackage.NODE_STYLE_DESCRIPTION__BORDER_COLOR;

    /**
     * The feature id for the '<em><b>Border Radius</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OUTER_FLAG_NODE_STYLE_DESCRIPTION__BORDER_RADIUS = DiagramPackage.NODE_STYLE_DESCRIPTION__BORDER_RADIUS;

    /**
     * The feature id for the '<em><b>Border Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OUTER_FLAG_NODE_STYLE_DESCRIPTION__BORDER_SIZE = DiagramPackage.NODE_STYLE_DESCRIPTION__BORDER_SIZE;

    /**
     * The feature id for the '<em><b>Border Line Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int OUTER_FLAG_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE = DiagramPackage.NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE;

    /**
     * The feature id for the '<em><b>Label Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OUTER_FLAG_NODE_STYLE_DESCRIPTION__LABEL_COLOR = DiagramPackage.NODE_STYLE_DESCRIPTION__LABEL_COLOR;

    /**
     * The feature id for the '<em><b>Show Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OUTER_FLAG_NODE_STYLE_DESCRIPTION__SHOW_ICON = DiagramPackage.NODE_STYLE_DESCRIPTION__SHOW_ICON;

    /**
     * The feature id for the '<em><b>Label Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OUTER_FLAG_NODE_STYLE_DESCRIPTION__LABEL_ICON = DiagramPackage.NODE_STYLE_DESCRIPTION__LABEL_ICON;

    /**
     * The number of structural features of the '<em>Outer Flag Node Style Description</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OUTER_FLAG_NODE_STYLE_DESCRIPTION_FEATURE_COUNT = DiagramPackage.NODE_STYLE_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Outer Flag Node Style Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OUTER_FLAG_NODE_STYLE_DESCRIPTION_OPERATION_COUNT = DiagramPackage.NODE_STYLE_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.CuboidNodeStyleDescriptionImpl <em>Cuboid
     * Node Style Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.CuboidNodeStyleDescriptionImpl
     * @see org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.PapyrusCustomNodesPackageImpl#getCuboidNodeStyleDescription()
     * @generated
     */
    int CUBOID_NODE_STYLE_DESCRIPTION = 6;

    /**
     * The feature id for the '<em><b>Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CUBOID_NODE_STYLE_DESCRIPTION__COLOR = DiagramPackage.NODE_STYLE_DESCRIPTION__COLOR;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CUBOID_NODE_STYLE_DESCRIPTION__FONT_SIZE = DiagramPackage.NODE_STYLE_DESCRIPTION__FONT_SIZE;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CUBOID_NODE_STYLE_DESCRIPTION__ITALIC = DiagramPackage.NODE_STYLE_DESCRIPTION__ITALIC;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CUBOID_NODE_STYLE_DESCRIPTION__BOLD = DiagramPackage.NODE_STYLE_DESCRIPTION__BOLD;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CUBOID_NODE_STYLE_DESCRIPTION__UNDERLINE = DiagramPackage.NODE_STYLE_DESCRIPTION__UNDERLINE;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CUBOID_NODE_STYLE_DESCRIPTION__STRIKE_THROUGH = DiagramPackage.NODE_STYLE_DESCRIPTION__STRIKE_THROUGH;

    /**
     * The feature id for the '<em><b>Border Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CUBOID_NODE_STYLE_DESCRIPTION__BORDER_COLOR = DiagramPackage.NODE_STYLE_DESCRIPTION__BORDER_COLOR;

    /**
     * The feature id for the '<em><b>Border Radius</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CUBOID_NODE_STYLE_DESCRIPTION__BORDER_RADIUS = DiagramPackage.NODE_STYLE_DESCRIPTION__BORDER_RADIUS;

    /**
     * The feature id for the '<em><b>Border Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CUBOID_NODE_STYLE_DESCRIPTION__BORDER_SIZE = DiagramPackage.NODE_STYLE_DESCRIPTION__BORDER_SIZE;

    /**
     * The feature id for the '<em><b>Border Line Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CUBOID_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE = DiagramPackage.NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE;

    /**
     * The feature id for the '<em><b>Label Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CUBOID_NODE_STYLE_DESCRIPTION__LABEL_COLOR = DiagramPackage.NODE_STYLE_DESCRIPTION__LABEL_COLOR;

    /**
     * The feature id for the '<em><b>Show Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CUBOID_NODE_STYLE_DESCRIPTION__SHOW_ICON = DiagramPackage.NODE_STYLE_DESCRIPTION__SHOW_ICON;

    /**
     * The feature id for the '<em><b>Label Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CUBOID_NODE_STYLE_DESCRIPTION__LABEL_ICON = DiagramPackage.NODE_STYLE_DESCRIPTION__LABEL_ICON;

    /**
     * The number of structural features of the '<em>Cuboid Node Style Description</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CUBOID_NODE_STYLE_DESCRIPTION_FEATURE_COUNT = DiagramPackage.NODE_STYLE_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Cuboid Node Style Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CUBOID_NODE_STYLE_DESCRIPTION_OPERATION_COUNT = DiagramPackage.NODE_STYLE_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.papyrus.web.customnodes.papyruscustomnodes.EllipseNodeStyleDescription <em>Ellipse Node Style
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Ellipse Node Style Description</em>'.
     * @see org.eclipse.papyrus.web.customnodes.papyruscustomnodes.EllipseNodeStyleDescription
     * @generated
     */
    EClass getEllipseNodeStyleDescription();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.papyrus.web.customnodes.papyruscustomnodes.PackageNodeStyleDescription <em>Package Node Style
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Package Node Style Description</em>'.
     * @see org.eclipse.papyrus.web.customnodes.papyruscustomnodes.PackageNodeStyleDescription
     * @generated
     */
    EClass getPackageNodeStyleDescription();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.papyrus.web.customnodes.papyruscustomnodes.RectangleWithExternalLabelNodeStyleDescription
     * <em>Rectangle With External Label Node Style Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Rectangle With External Label Node Style Description</em>'.
     * @see org.eclipse.papyrus.web.customnodes.papyruscustomnodes.RectangleWithExternalLabelNodeStyleDescription
     * @generated
     */
    EClass getRectangleWithExternalLabelNodeStyleDescription();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.papyrus.web.customnodes.papyruscustomnodes.NoteNodeStyleDescription <em>Note Node Style
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Note Node Style Description</em>'.
     * @see org.eclipse.papyrus.web.customnodes.papyruscustomnodes.NoteNodeStyleDescription
     * @generated
     */
    EClass getNoteNodeStyleDescription();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.papyrus.web.customnodes.papyruscustomnodes.InnerFlagNodeStyleDescription <em>Inner Flag Node
     * Style Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Inner Flag Node Style Description</em>'.
     * @see org.eclipse.papyrus.web.customnodes.papyruscustomnodes.InnerFlagNodeStyleDescription
     * @generated
     */
    EClass getInnerFlagNodeStyleDescription();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.papyrus.web.customnodes.papyruscustomnodes.OuterFlagNodeStyleDescription <em>Outer Flag Node
     * Style Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Outer Flag Node Style Description</em>'.
     * @see org.eclipse.papyrus.web.customnodes.papyruscustomnodes.OuterFlagNodeStyleDescription
     * @generated
     */
    EClass getOuterFlagNodeStyleDescription();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.papyrus.web.customnodes.papyruscustomnodes.CuboidNodeStyleDescription <em>Cuboid Node Style
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Cuboid Node Style Description</em>'.
     * @see org.eclipse.papyrus.web.customnodes.papyruscustomnodes.CuboidNodeStyleDescription
     * @generated
     */
    EClass getCuboidNodeStyleDescription();

    /**
     * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the factory that creates the instances of the model.
     * @generated
     */
    PapyrusCustomNodesFactory getPapyrusCustomNodesFactory();

    /**
     * <!-- begin-user-doc --> Defines literals for the meta objects that represent
     * <ul>
     * <li>each class,</li>
     * <li>each feature of each class,</li>
     * <li>each operation of each class,</li>
     * <li>each enum,</li>
     * <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     *
     * @generated
     */
    interface Literals {
        /**
         * The meta object literal for the
         * '{@link org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.EllipseNodeStyleDescriptionImpl
         * <em>Ellipse Node Style Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.EllipseNodeStyleDescriptionImpl
         * @see org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.PapyrusCustomNodesPackageImpl#getEllipseNodeStyleDescription()
         * @generated
         */
        EClass ELLIPSE_NODE_STYLE_DESCRIPTION = eINSTANCE.getEllipseNodeStyleDescription();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.PackageNodeStyleDescriptionImpl
         * <em>Package Node Style Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.PackageNodeStyleDescriptionImpl
         * @see org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.PapyrusCustomNodesPackageImpl#getPackageNodeStyleDescription()
         * @generated
         */
        EClass PACKAGE_NODE_STYLE_DESCRIPTION = eINSTANCE.getPackageNodeStyleDescription();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.RectangleWithExternalLabelNodeStyleDescriptionImpl
         * <em>Rectangle With External Label Node Style Description</em>}' class. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @see org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.RectangleWithExternalLabelNodeStyleDescriptionImpl
         * @see org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.PapyrusCustomNodesPackageImpl#getRectangleWithExternalLabelNodeStyleDescription()
         * @generated
         */
        EClass RECTANGLE_WITH_EXTERNAL_LABEL_NODE_STYLE_DESCRIPTION = eINSTANCE.getRectangleWithExternalLabelNodeStyleDescription();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.NoteNodeStyleDescriptionImpl <em>Note
         * Node Style Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.NoteNodeStyleDescriptionImpl
         * @see org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.PapyrusCustomNodesPackageImpl#getNoteNodeStyleDescription()
         * @generated
         */
        EClass NOTE_NODE_STYLE_DESCRIPTION = eINSTANCE.getNoteNodeStyleDescription();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.InnerFlagNodeStyleDescriptionImpl
         * <em>Inner Flag Node Style Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.InnerFlagNodeStyleDescriptionImpl
         * @see org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.PapyrusCustomNodesPackageImpl#getInnerFlagNodeStyleDescription()
         * @generated
         */
        EClass INNER_FLAG_NODE_STYLE_DESCRIPTION = eINSTANCE.getInnerFlagNodeStyleDescription();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.OuterFlagNodeStyleDescriptionImpl
         * <em>Outer Flag Node Style Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.OuterFlagNodeStyleDescriptionImpl
         * @see org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.PapyrusCustomNodesPackageImpl#getOuterFlagNodeStyleDescription()
         * @generated
         */
        EClass OUTER_FLAG_NODE_STYLE_DESCRIPTION = eINSTANCE.getOuterFlagNodeStyleDescription();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.CuboidNodeStyleDescriptionImpl <em>Cuboid
         * Node Style Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.CuboidNodeStyleDescriptionImpl
         * @see org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.PapyrusCustomNodesPackageImpl#getCuboidNodeStyleDescription()
         * @generated
         */
        EClass CUBOID_NODE_STYLE_DESCRIPTION = eINSTANCE.getCuboidNodeStyleDescription();

    }

} // PapyrusCustomNodesPackage
