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
package org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.papyrus.web.customnodes.papyruscustomnodes.EllipseNodeStyleDescription;
import org.eclipse.papyrus.web.customnodes.papyruscustomnodes.NoteNodeStyleDescription;
import org.eclipse.papyrus.web.customnodes.papyruscustomnodes.PackageNodeStyleDescription;
import org.eclipse.papyrus.web.customnodes.papyruscustomnodes.PapyrusCustomnodesFactory;
import org.eclipse.papyrus.web.customnodes.papyruscustomnodes.PapyrusCustomnodesPackage;
import org.eclipse.papyrus.web.customnodes.papyruscustomnodes.RectangleWithExternalLabelNodeStyleDescription;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 *
 * @generated
 */
public class PapyrusCustomnodesFactoryImpl extends EFactoryImpl implements PapyrusCustomnodesFactory {
    /**
     * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static PapyrusCustomnodesFactory init() {
        try {
            PapyrusCustomnodesFactory thePapyrusCustomnodesFactory = (PapyrusCustomnodesFactory) EPackage.Registry.INSTANCE.getEFactory(PapyrusCustomnodesPackage.eNS_URI);
            if (thePapyrusCustomnodesFactory != null) {
                return thePapyrusCustomnodesFactory;
            }
        } catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new PapyrusCustomnodesFactoryImpl();
    }

    /**
     * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public PapyrusCustomnodesFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
            case PapyrusCustomnodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION:
                return this.createEllipseNodeStyleDescription();
            case PapyrusCustomnodesPackage.PACKAGE_NODE_STYLE_DESCRIPTION:
                return this.createPackageNodeStyleDescription();
            case PapyrusCustomnodesPackage.RECTANGLE_WITH_EXTERNAL_LABEL_NODE_STYLE_DESCRIPTION:
                return this.createRectangleWithExternalLabelNodeStyleDescription();
            case PapyrusCustomnodesPackage.NOTE_NODE_STYLE_DESCRIPTION:
                return this.createNoteNodeStyleDescription();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EllipseNodeStyleDescription createEllipseNodeStyleDescription() {
        EllipseNodeStyleDescriptionImpl ellipseNodeStyleDescription = new EllipseNodeStyleDescriptionImpl();
        return ellipseNodeStyleDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public PackageNodeStyleDescription createPackageNodeStyleDescription() {
        PackageNodeStyleDescriptionImpl packageNodeStyleDescription = new PackageNodeStyleDescriptionImpl();
        return packageNodeStyleDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public RectangleWithExternalLabelNodeStyleDescription createRectangleWithExternalLabelNodeStyleDescription() {
        RectangleWithExternalLabelNodeStyleDescriptionImpl rectangleWithExternalLabelNodeStyleDescription = new RectangleWithExternalLabelNodeStyleDescriptionImpl();
        return rectangleWithExternalLabelNodeStyleDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NoteNodeStyleDescription createNoteNodeStyleDescription() {
        NoteNodeStyleDescriptionImpl noteNodeStyleDescription = new NoteNodeStyleDescriptionImpl();
        return noteNodeStyleDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public PapyrusCustomnodesPackage getPapyrusCustomnodesPackage() {
        return (PapyrusCustomnodesPackage) this.getEPackage();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @deprecated
     * @generated
     */
    @Deprecated
    public static PapyrusCustomnodesPackage getPackage() {
        return PapyrusCustomnodesPackage.eINSTANCE;
    }

} // PapyrusCustomnodesFactoryImpl
