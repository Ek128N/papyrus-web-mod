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
package org.eclipse.papyrus.web.application.properties.pages;

import org.eclipse.papyrus.web.application.properties.ColorRegistry;
import org.eclipse.papyrus.web.application.properties.MonoReferenceWidgetBuilder;
import org.eclipse.papyrus.web.application.properties.MultiReferenceWidgetBuilder;
import org.eclipse.papyrus.web.application.properties.ViewElementsFactory;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsFactory;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListAddOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListDeleteOperation;
import org.eclipse.sirius.components.view.ChangeContext;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.form.FormElementDescription;
import org.eclipse.sirius.components.view.form.FormElementIf;
import org.eclipse.sirius.components.view.form.FormFactory;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;
import org.eclipse.sirius.components.view.form.PageDescription;

/**
 * Stereotype application page.
 * This page contains widget definitions for the feature of the stereotype application linked to an Element.
 * 
 * @author Jerome Gout
 */
public class StereotypeApplicationPage {

    private static final String AQL_SELF_CLEAR_REFERENCE_FEATURE_NAME = "aql:self.clearReference(feature.name)";

    private static final String AQL_SELF_GET_ALL_REACHABLE_ROOT_ELEMENTS = "aql:self.getAllReachableRootElements()";

    private static final String AQL_SELF_GET_FEATURE_TYPE_QUALIFIED_NAME_FEATURE_NAME = "aql:self.getFeatureTypeQualifiedName(feature.name)";

    private static final String AQL_GET_STEREOTYPE_FEATURE_VALUE = "aql:self.getStereotypeFeatureValue(feature)";

    private static final String AQL_SET_STEREOTYPE_FEATURE_VALUE = "aql:self.setStereotypeFeatureValue(feature,newValue)";

    private static final String AQL_FEATURE_NAME = "aql:feature.name";

    private static final String AQL_FEATURE_IS_EDITABLE = "aql:feature.isEditable()";

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public StereotypeApplicationPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {
        PageDescription page = createPage();
        createStereotypeApplicationGroup(page);
        return page;
    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("stereotype_application_page", "uml::Element", "aql:self.eClass().name", "aql:self.getStereotypeApplications()", "");
    }

    protected void createStereotypeApplicationGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("stereotype_application_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        var featureIterator = FormFactory.eINSTANCE.createFormElementFor();
        featureIterator.setName("for: feature in allFeatures");
        featureIterator.setIterator("feature");
        featureIterator.setIterableExpression("aql:self.getAllFeatures()");
        
        // Mono
        featureIterator.getChildren().add(createMonoStringAttributeIf("isMonoStringAttribute", "monoString", "aql:'Widget to set \"'+feature.name+'\": expecting a String.'", true));
        featureIterator.getChildren().add(createMonoStringAttributeIf("isMonoIntegerAttribute", "monoInteger", "aql:'Widget to set \"'+feature.name+'\": expecting an Integer.'"));
        featureIterator.getChildren().add(createMonoStringAttributeIf("isMonoDoubleAttribute", "monoDouble", "aql:'Widget to set \"'+feature.name+'\": expecting a Double.'"));
        featureIterator.getChildren().add(createMonoStringAttributeIf("isMonoFloatAttribute", "monoFloat", "aql:'Widget to set \"'+feature.name+'\": expecting a Float.'"));
        // boolean -> check
        featureIterator.getChildren().add(createMonoBooleanAttributeIf());
        // Boolean -> radio with "true", "false" and "null"
        featureIterator.getChildren().add(createMonoBooleanObjectAttributeIf());
        // Enumeration -> Select
        featureIterator.getChildren().add(createMonoEnumerationAttributeIf());
        
        // Multi
        featureIterator.getChildren().add(createMultiStringAttributeIf("isMultiStringAttribute", "multiString", "aql:'Widget to set \"'+feature.name+'\": expecting a list of String.'"));
        featureIterator.getChildren().add(createMultiStringAttributeIf("isMultiIntegerAttribute", "multiInteger", "aql:'Widget to set \"'+feature.name+'\": expecting a list of Ineger.'"));
        featureIterator.getChildren().add(createMultiStringAttributeIf("isMultiDoubleAttribute", "multiDouble", "aql:'Widget to set \"'+feature.name+'\": expecting a list of Double.'"));
        featureIterator.getChildren().add(createMultiStringAttributeIf("isMultiFloatAttribute", "multiFloat", "aql:'Widget to set \"'+feature.name+'\": expecting a list of Float.'"));
        // Multi boolean
        featureIterator.getChildren().add(
                createMultiEnumerationAttributeIf("isMultiBooleanAttribute", "multiBoolean", "aql:Sequence{'true', 'false'}",
                        "aql:'Widget to set \"'+feature.name+'\": expecting a list of Boolean.'"));
        // Multi enum
        featureIterator.getChildren().add(createMultiEnumerationAttributeIf("isMultiEnumeration", "multiEnumeration", "aql:feature.getStereotypeEnumerationLiterals()",
                "aql:'Widget to set \"'+feature.name+'\": expecting a list of Enum.'"));

        // Reference
        // Mono
        featureIterator.getChildren().add(createMonoUMLReferenceIf());
        featureIterator.getChildren().add(createMonoStereotypeReferenceIf());
        // Multi
        featureIterator.getChildren().add(createMultiUMLReferenceIf());
        featureIterator.getChildren().add(createMultiStereotypeReferenceIf());
        
        group.getChildren().add(featureIterator);
    }

    private FormElementIf createMonoStringAttributeIf(String ifName, String widgetName, String help) {
        return createMonoStringAttributeIf(ifName, widgetName, help, false);
    }
    
    private String getPredicateExpression(String ifName) {
        return "aql:feature." + ifName + "()";
    }
    
    private FormElementIf createMonoStringAttributeIf(String ifName, String widgetName, String help, boolean isMultiline) {
        var ifWidget = FormFactory.eINSTANCE.createFormElementIf();
        ifWidget.setName(ifName);
        ifWidget.setPredicateExpression(getPredicateExpression(ifName));
        final FormElementDescription monoStringWidget;
        if (isMultiline) {
            monoStringWidget = viewElementFactory.createTextAreaDescription(widgetName, //
                    AQL_FEATURE_NAME, //
                    AQL_GET_STEREOTYPE_FEATURE_VALUE, //
                    AQL_SET_STEREOTYPE_FEATURE_VALUE, //
                    help, //
                    AQL_FEATURE_IS_EDITABLE);            
        } else {
            monoStringWidget = viewElementFactory.createTextfieldDescription(widgetName, //
                    AQL_FEATURE_NAME, //
                    AQL_GET_STEREOTYPE_FEATURE_VALUE, //
                    AQL_SET_STEREOTYPE_FEATURE_VALUE, //
                    help, //
                    AQL_FEATURE_IS_EDITABLE);            
        }
        ifWidget.getChildren().add(monoStringWidget);
        return ifWidget;
    }

    private FormElementIf createMultiStringAttributeIf(String ifName, String widgetName, String help) {
        var ifWidget = FormFactory.eINSTANCE.createFormElementIf();
        ifWidget.setName(ifName);
        ifWidget.setPredicateExpression(getPredicateExpression(ifName));
        
        var primListWidget = PapyrusWidgetsFactory.eINSTANCE.createPrimitiveListWidgetDescription();
        primListWidget.setName(widgetName);
        primListWidget.setLabelExpression(AQL_FEATURE_NAME);
        primListWidget.setValueExpression(AQL_GET_STEREOTYPE_FEATURE_VALUE);
        PrimitiveListDeleteOperation deleteOperation = PapyrusWidgetsFactory.eINSTANCE.createPrimitiveListDeleteOperation();
        deleteOperation.getBody().add(createChangeContext("aql:self.removeFromUsingIndex(feature.name,candidateIndex)"));
        primListWidget.setDeleteOperation(deleteOperation); 
        PrimitiveListAddOperation addOperation = PapyrusWidgetsFactory.eINSTANCE.createPrimitiveListAddOperation();
        addOperation.getBody().add(createChangeContext("aql:self.addToAttribute(feature.name,newValue)"));
        primListWidget.setAddOperation(addOperation);
        primListWidget.setHelpExpression(help);
        primListWidget.setIsEnabledExpression(AQL_FEATURE_IS_EDITABLE);
        ifWidget.getChildren().add(primListWidget);
        return ifWidget;
    }

    private ChangeContext createChangeContext(String contextExp) {
        ChangeContext changeCtxt = ViewFactory.eINSTANCE.createChangeContext();
        changeCtxt.setExpression(contextExp);
        return changeCtxt;
    }
    
    private FormElementIf createMultiEnumerationAttributeIf(String ifName, String name, String options, String help) {
        var ifWidget = FormFactory.eINSTANCE.createFormElementIf();
        ifWidget.setName(ifName);
        ifWidget.setPredicateExpression(getPredicateExpression(ifName));
        
        var multiEnumerationWidget = PapyrusWidgetsFactory.eINSTANCE.createPrimitiveListWidgetDescription();
        multiEnumerationWidget.setName(name);
        multiEnumerationWidget.setLabelExpression(AQL_FEATURE_NAME);
        multiEnumerationWidget.setValueExpression(AQL_GET_STEREOTYPE_FEATURE_VALUE);
        multiEnumerationWidget.setCandidatesExpression(options);
        
        PrimitiveListDeleteOperation deleteOperation = PapyrusWidgetsFactory.eINSTANCE.createPrimitiveListDeleteOperation();
        deleteOperation.getBody().add(createChangeContext("aql:self.removeFromUsingIndex(feature.name,candidateIndex)"));
        multiEnumerationWidget.setDeleteOperation(deleteOperation); 
        PrimitiveListAddOperation addOperation = PapyrusWidgetsFactory.eINSTANCE.createPrimitiveListAddOperation();
        addOperation.getBody().add(createChangeContext("aql:self.addToAttribute(feature.name,newValue)"));
        multiEnumerationWidget.setAddOperation(addOperation);
        multiEnumerationWidget.setHelpExpression(help);
        multiEnumerationWidget.setIsEnabledExpression(AQL_FEATURE_IS_EDITABLE);
        ifWidget.getChildren().add(multiEnumerationWidget);
        
        return ifWidget;
    }

    private FormElementIf createMonoEnumerationAttributeIf() {
        var ifMonoEnum = FormFactory.eINSTANCE.createFormElementIf();
        ifMonoEnum.setName("isMonoEnumeration");
        ifMonoEnum.setPredicateExpression("aql:feature.isMonoEnumeration()");
        var monoEnumRadio = viewElementFactory.createSelectDescription("monoEnumeration", //
                AQL_FEATURE_NAME, //
                "aql:self.getStereotypeEnumerationValue(feature)", //
                AQL_SET_STEREOTYPE_FEATURE_VALUE, //
                "aql:feature.getStereotypeEnumerationLiterals()", //
                "aql:candidate.toString()", //
                "aql:'Widget to set \"'+feature.name+'\": expecting a enum literal.'", //
                AQL_FEATURE_IS_EDITABLE);
        ifMonoEnum.getChildren().add(monoEnumRadio);
        return ifMonoEnum;
    }

    private FormElementIf createMonoBooleanAttributeIf() {
        var ifMonoBoolean = FormFactory.eINSTANCE.createFormElementIf();
        ifMonoBoolean.setName("isMonoBooleanAttribute");
        ifMonoBoolean.setPredicateExpression("aql:feature.isMonoBooleanAttribute()");
        var monoBooleanCheck = viewElementFactory.createCheckboxDescription("monoBoolean", //
                AQL_FEATURE_NAME, //
                AQL_GET_STEREOTYPE_FEATURE_VALUE, //
                AQL_SET_STEREOTYPE_FEATURE_VALUE, //
                "aql:'Widget to set \"'+feature.name+'\": expecting a boolean.'", //
                AQL_FEATURE_IS_EDITABLE);
        ifMonoBoolean.getChildren().add(monoBooleanCheck);
        return ifMonoBoolean;
    }
    
    private FormElementIf createMonoBooleanObjectAttributeIf() {
        var ifMonoBooleanObject = FormFactory.eINSTANCE.createFormElementIf();
        ifMonoBooleanObject.setName("isMonoBooleanObjectAttribute");
        ifMonoBooleanObject.setPredicateExpression("aql:feature.isMonoBooleanObjectAttribute()");
        var monoBooleanObjectRadio = PapyrusWidgetsFactory.eINSTANCE.createPrimitiveRadioWidgetDescription();
        monoBooleanObjectRadio.setName("monoBooleanObject");
        monoBooleanObjectRadio.setLabelExpression(AQL_FEATURE_NAME);
        monoBooleanObjectRadio.setValueExpression("aql:self.getStereotypeBooleanObjectValue(feature)");
        monoBooleanObjectRadio.getBody().add(createChangeContext(AQL_SET_STEREOTYPE_FEATURE_VALUE));
        monoBooleanObjectRadio.setCandidatesExpression("aql:feature.getStereotypeBooleanObjectLiterals()");
        monoBooleanObjectRadio.setHelpExpression("aql:'Widget to set \"'+feature.name+'\": expecting a Boolean object.'");
        monoBooleanObjectRadio.setIsEnabledExpression(AQL_FEATURE_IS_EDITABLE);
        ifMonoBooleanObject.getChildren().add(monoBooleanObjectRadio);
        return ifMonoBooleanObject;
    }

    private FormElementIf createMonoStereotypeReferenceIf() {
        var ifWidget = FormFactory.eINSTANCE.createFormElementIf();
        ifWidget.setName("isMonoStereotypeReference");
        ifWidget.setPredicateExpression("aql:feature.isMonoReference() and feature.eType.isStereotypeDataType()");
        var widget = new MonoReferenceWidgetBuilder() //
                .name("monoStereotypeReference") //
                .label(AQL_FEATURE_NAME) //
                .help("aql:'Widget to set \"'+feature.name+'\": expecting a single '+self.getFeatureTypeQualifiedName(feature.name)+' stereotyped element.'") //
                .isEnable(AQL_FEATURE_IS_EDITABLE) //
                .owner("") //
                .type(AQL_SELF_GET_FEATURE_TYPE_QUALIFIED_NAME_FEATURE_NAME) //
                .value("aql:self.getStereotypeFeatureBaseElementValue(feature)") //
                .searchScope(AQL_SELF_GET_ALL_REACHABLE_ROOT_ELEMENTS) //
                .dropdownOptions("aql:self.getAllReachableStereotypeApplicationsBaseElements(feature.name)") //
                .createOperation("") //
                .setOperation("aql:self.addStereotypeApplicationFromBase(newValue,feature.name,feature.eType)") //
                .unsetOperation("aql:item.removeStereotypeApplicationFromBase(self, feature.name,feature.eType))") //
                .clearOperation(AQL_SELF_CLEAR_REFERENCE_FEATURE_NAME) //
                .build();
        ifWidget.getChildren().add(widget);
        return ifWidget;
    }

    private FormElementIf createMultiStereotypeReferenceIf() {
        var ifWidget = FormFactory.eINSTANCE.createFormElementIf();
        ifWidget.setName("isMultiStereotypeReference");
        ifWidget.setPredicateExpression("aql:feature.isMultiReference() and feature.eType.isStereotypeDataType()");
        var widget = new MultiReferenceWidgetBuilder() //
                .name("multiStereotypeReference") //
                .label(AQL_FEATURE_NAME) //
                .help("aql:'Widget to set \"'+feature.name+'\": expecting a list of '+self.getFeatureTypeQualifiedName(feature.name)+' stereotyped elements.'") //
                .isEnable(AQL_FEATURE_IS_EDITABLE) //
                .owner("") //
                .type(AQL_SELF_GET_FEATURE_TYPE_QUALIFIED_NAME_FEATURE_NAME) //
                .value("aql:self.getStereotypeFeatureBaseElementValue(feature)") //
                .searchScope(AQL_SELF_GET_ALL_REACHABLE_ROOT_ELEMENTS) //
                .dropdownOptions("aql:self.getAllReachableStereotypeApplicationsBaseElements(feature.name)") //
                .createOperation("") //
                .addOperation("aql:self.addReferenceStereotypeApplicationfromBase(newValue, feature.name, feature.eType)") //
                .removeOperation("aql:item.removeStereotypeApplicationFromBase(self, feature.name,feature.eType))") //
                .reorderOperation("aql:self.moveReferenceStereotypeApplicationFromBase(feature.name, item, feature.eType, fromIndex, toIndex)") //
                .clearOperation(AQL_SELF_CLEAR_REFERENCE_FEATURE_NAME) //
                .build();
        ifWidget.getChildren().add(widget);
        return ifWidget;
    }
    private FormElementIf createMonoUMLReferenceIf() {
        var ifWidget = FormFactory.eINSTANCE.createFormElementIf();
        ifWidget.setName("isMonoReference");
        ifWidget.setPredicateExpression("aql:feature.isMonoReference() and feature.eType.isUMLDataType()");
        var widget = new MonoReferenceWidgetBuilder() //
                .name("monoReference") //
                .label(AQL_FEATURE_NAME) //
                .help("aql:'Widget to set \"'+feature.name+'\": expecting a single '+self.getFeatureTypeQualifiedName(feature.name)+' object.'") //
                .isEnable(AQL_FEATURE_IS_EDITABLE) //
                .owner("") //
                .type(AQL_SELF_GET_FEATURE_TYPE_QUALIFIED_NAME_FEATURE_NAME) //
                .value(AQL_GET_STEREOTYPE_FEATURE_VALUE) //
                .searchScope(AQL_SELF_GET_ALL_REACHABLE_ROOT_ELEMENTS) //
                .dropdownOptions("aql:self.getAllReachableElements(feature.name)") //
                .createOperation("aql:parent.create(kind, feature)") //
                .setOperation("aql:self.updateReference(newValue,feature.name)") //
                .unsetOperation("aql:item.delete(self, feature.name))") //
                .clearOperation(AQL_SELF_CLEAR_REFERENCE_FEATURE_NAME) //
                .build();
        ifWidget.getChildren().add(widget);
        return ifWidget;
    }
    
    private FormElementIf createMultiUMLReferenceIf() {
        var ifWidget = FormFactory.eINSTANCE.createFormElementIf();
        ifWidget.setName("isMultiReference");
        ifWidget.setPredicateExpression("aql:feature.isMultiReference() and feature.eType.isUMLDataType()");
        var widget = new MultiReferenceWidgetBuilder() //
                .name("multiReference") //
                .label(AQL_FEATURE_NAME) //
                .help("aql:'Widget to set \"'+feature.name+'\": expecting a list of '+self.getFeatureTypeQualifiedName(feature.name)+' objects.'") //
                .isEnable(AQL_FEATURE_IS_EDITABLE) //
                .owner("") //
                .type(AQL_SELF_GET_FEATURE_TYPE_QUALIFIED_NAME_FEATURE_NAME) //
                .value(AQL_GET_STEREOTYPE_FEATURE_VALUE) //
                .searchScope(AQL_SELF_GET_ALL_REACHABLE_ROOT_ELEMENTS) //
                .dropdownOptions("aql:self.getAllReachableElements(feature.name)") //
                .createOperation("aql:parent.create(kind, feature)") //
                .addOperation("aql:self.addReferenceElement(newValue, feature.name)") //
                .removeOperation("aql:item.delete(self, feature.name))") //
                .reorderOperation("aql:self.moveReferenceElement(feature.name, item, fromIndex, toIndex)") //
                .clearOperation(AQL_SELF_CLEAR_REFERENCE_FEATURE_NAME) //
                .build();
        ifWidget.getChildren().add(widget);
        return ifWidget;
    }
}
