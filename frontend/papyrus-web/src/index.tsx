/*****************************************************************************
 * Copyright (c) 2019, 2024 CEA LIST, Obeo, Artal Technologies.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *     Titouan BOUETE-GIRAUD (Artal Technologies) - Issues 210, 218
 *******************************************************************************/
import { loadDevMessages, loadErrorMessages } from '@apollo/client/dev';
import { ExtensionRegistry } from '@eclipse-sirius/sirius-components-core';
import { NodeTypeContribution, diagramPaletteToolExtensionPoint } from '@eclipse-sirius/sirius-components-diagrams';
import {
  ApolloClientOptionsConfigurer,
  DiagramRepresentationConfiguration,
  NodeTypeRegistry,
  SiriusWebApplication,
  apolloClientOptionsConfigurersExtensionPoint,
  footerExtensionPoint,
  navigationBarIconExtensionPoint,
  navigationBarMenuIconExtensionPoint,
} from '@eclipse-sirius/sirius-web-application';
import FormatListBulletedIcon from '@mui/icons-material/FormatListBulleted';
import ReactDOM from 'react-dom';
import { Help } from './core/Help';
import { PapyrusIcon } from './core/PapyrusIcon';
import { httpOrigin, wsOrigin } from './core/URL';
import { CuboidNode } from './nodes/cuboid/CuboidNode';
import { CuboidNodeConverter } from './nodes/cuboid/CuboidNodeConverter';
import { CuboidNodeLayoutHandler } from './nodes/cuboid/CuboidNodeLayoutHandler';
import { CustomImageNode } from './nodes/customImage/CustomImageNode';
import { CustomImageNodeConverter } from './nodes/customImage/CustomImageNodeConverter';
import { CustomImageNodeLayoutHandler } from './nodes/customImage/CustomImageNodeLayoutHandler';
import { EllipseNode } from './nodes/ellipse/EllipseNode';
import { EllipseNodeConverter } from './nodes/ellipse/EllipseNodeConverter';
import { EllipseNodeLayoutHandler } from './nodes/ellipse/EllipseNodeLayoutHandler';
import { InnerFlagNode } from './nodes/innerFlag/InnerFlagNode';
import { InnerFlagNodeConverter } from './nodes/innerFlag/InnerFlagNodeConverter';
import { InnerFlagNodeLayoutHandler } from './nodes/innerFlag/InnerFlagNodeLayoutHandler';
import { NoteNode } from './nodes/note/NoteNode';
import { NoteNodeConverter } from './nodes/note/NoteNodeConverter';
import { NoteNodeLayoutHandler } from './nodes/note/NoteNodeLayoutHandler';
import { OuterFlagNode } from './nodes/outerFlag/OuterFlagNode';
import { OuterFlagNodeConverter } from './nodes/outerFlag/OuterFlagNodeConverter';
import { OuterFlagNodeLayoutHandler } from './nodes/outerFlag/OuterFlagNodeLayoutHandler';
import { PackageNode } from './nodes/package/PackageNode';
import { PackageNodeConverter } from './nodes/package/PackageNodeConverter';
import { PackageNodeLayoutHandler } from './nodes/package/PackageNodeLayoutHandler';
import { RectangleWithExternalLabelNode } from './nodes/rectangleWithExternalLabel/RectangleWithExternalLabelNode';
import { RectangleWithExternalLabelNodeConverter } from './nodes/rectangleWithExternalLabel/RectangleWithExternalLabelNodeConverter';
import { RectangleWithExternalLabelNodeLayoutHandler } from './nodes/rectangleWithExternalLabel/RectangleWithExternalLabelNodeLayoutHandler';

import {
  GQLWidget,
  PropertySectionComponent,
  widgetContributionExtensionPoint,
} from '@eclipse-sirius/sirius-components-forms';
import { treeItemContextMenuEntryExtensionPoint } from '@eclipse-sirius/sirius-components-trees';
import {
  ReferenceIcon,
  ReferencePreview,
  ReferencePropertySection,
} from '@eclipse-sirius/sirius-components-widget-reference';
import './ReactFlow.css';
import { PapyrusPopupToolContribution } from './diagram-tools/PapyrusPopupToolContribution';
import './fonts.css';
import { Footer } from './footer/Footer';
import { nodesStyleDocumentTransform } from './nodes/NodesDocumentTransform';
import './portals.css';
import { UMLModelTreeItemContextMenuContribution } from './profile/apply-profile/UMLModelTreeItemContextMenuContribution';
import { UMLElementTreeItemContextMenuContribution } from './profile/apply-stereotype/UMLElementTreeItemContextMenuContribution';
import './reset.css';
import './variables.css';
import { customWidgetsDocumentTransform } from './widgets/CustomWidgetsDocumentTransform';

import { PublishProfileTreeItemContextMenuContribution } from './profile/publish-profile/PublishProfileTreeItemContextMenuContribution';
import { ContainmentReferenceIcon } from './widgets/containmentReference/ContainmentReferenceIcon';
import { ContainmentReferencePreview } from './widgets/containmentReference/ContainmentReferencePreview';
import ContainmentReferenceSection from './widgets/containmentReference/ContainmentReferenceSection';
import { CustomImageIcon } from './widgets/customImage/CustomImageIcon';
import { CustomImagePreview } from './widgets/customImage/CustomImagePreview';
import { CustomImageSection } from './widgets/customImage/CustomImageSection';
import { LanguageExpressionIcon } from './widgets/languageExpression/LanguageExpressionIcon';
import { LanguageExpressionPreview } from './widgets/languageExpression/LanguageExpressionPreview';
import { LanguageExpressionSection } from './widgets/languageExpression/LanguageExpressionSection';
import { PrimitiveListWidgetPreview } from './widgets/primitiveList/PrimitiveListWidgetPreview';
import { PrimitiveListSection } from './widgets/primitiveList/PrimitiveListWidgetPropertySection';
import { PrimitiveRadioIcon } from './widgets/primitiveRadio/PrimitiveRadioIcon';
import { PrimitiveRadioPreview } from './widgets/primitiveRadio/PrimitiveRadioPreview';
import { PrimitiveRadioSection } from './widgets/primitiveRadio/PrimitiveRadioSection';

if (process.env.NODE_ENV !== 'production') {
  loadDevMessages();
  loadErrorMessages();
}

const extensionRegistry: ExtensionRegistry = new ExtensionRegistry();

/*
 * Custom node contribution
 */
const nodeTypeRegistryValue: NodeTypeRegistry = {
  nodeLayoutHandlers: [
    new EllipseNodeLayoutHandler(),
    new PackageNodeLayoutHandler(),
    new RectangleWithExternalLabelNodeLayoutHandler(),
    new NoteNodeLayoutHandler(),
    new InnerFlagNodeLayoutHandler(),
    new OuterFlagNodeLayoutHandler(),
    new CuboidNodeLayoutHandler(),
    new CustomImageNodeLayoutHandler(),
  ],
  nodeConverters: [
    new EllipseNodeConverter(),
    new PackageNodeConverter(),
    new RectangleWithExternalLabelNodeConverter(),
    new NoteNodeConverter(),
    new InnerFlagNodeConverter(),
    new OuterFlagNodeConverter(),
    new CuboidNodeConverter(),
    new CustomImageNodeConverter(),
  ],
  nodeTypeContributions: [
    <NodeTypeContribution component={EllipseNode} type={'ellipseNode'} />,
    <NodeTypeContribution component={PackageNode} type={'packageNode'} />,
    <NodeTypeContribution component={RectangleWithExternalLabelNode} type={'rectangleWithExternalLabelNode'} />,
    <NodeTypeContribution component={NoteNode} type={'noteNode'} />,
    <NodeTypeContribution component={InnerFlagNode} type={'innerFlagNode'} />,
    <NodeTypeContribution component={OuterFlagNode} type={'outerFlagNode'} />,
    <NodeTypeContribution component={CuboidNode} type={'cuboidNode'} />,
    <NodeTypeContribution component={CustomImageNode} type={'customImageNode'} />,
  ],
};

// Contribution to modify GraphQl requests to handle custom node
const nodeApolloClientOptionsConfigurer: ApolloClientOptionsConfigurer = (currentOptions) => {
  const { documentTransform } = currentOptions;

  const newDocumentTransform = documentTransform
    ? documentTransform.concat(nodesStyleDocumentTransform)
    : nodesStyleDocumentTransform;
  return {
    ...currentOptions,
    documentTransform: newDocumentTransform,
  };
};

/*
 * Custom widgets contribution
 */

// Contribution to modify GraphQl requests to handle custom widgets
const widgetsApolloClientOptionsConfigurer: ApolloClientOptionsConfigurer = (currentOptions) => {
  const { documentTransform } = currentOptions;

  const newDocumentTransform = documentTransform
    ? documentTransform.concat(customWidgetsDocumentTransform)
    : customWidgetsDocumentTransform;
  return {
    ...currentOptions,
    documentTransform: newDocumentTransform,
  };
};

extensionRegistry.putData(widgetContributionExtensionPoint, {
  identifier: 'papyrus-custom-widget-primitive-list',
  data: [
    {
      name: 'PrimitiveListWidget',
      icon: <FormatListBulletedIcon />,
      previewComponent: PrimitiveListWidgetPreview,
      component: (widget: GQLWidget): PropertySectionComponent<GQLWidget> | null => {
        if (widget.__typename === 'PrimitiveListWidget') {
          return PrimitiveListSection;
        }
        return null;
      },
    },
    {
      name: 'LanguageExpression',
      icon: <LanguageExpressionIcon />,
      previewComponent: LanguageExpressionPreview,
      component: (widget: GQLWidget): PropertySectionComponent<GQLWidget> | null => {
        if (widget.__typename === 'LanguageExpression') {
          return LanguageExpressionSection;
        }
        return null;
      },
    },
    {
      name: 'ContainmentReferenceWidget',
      icon: <ContainmentReferenceIcon />,
      previewComponent: ContainmentReferencePreview,
      component: (widget: GQLWidget): PropertySectionComponent<GQLWidget> | null => {
        if (widget.__typename === 'ContainmentReferenceWidget') {
          return ContainmentReferenceSection;
        }
        return null;
      },
    },
    {
      name: 'PrimitiveRadio',
      icon: <PrimitiveRadioIcon />,
      previewComponent: PrimitiveRadioPreview,
      component: (widget: GQLWidget): PropertySectionComponent<GQLWidget> | null => {
        if (widget.__typename === 'PrimitiveRadio') {
          return PrimitiveRadioSection;
        }
        return null;
      },
    },
    {
      name: 'ReferenceWidget',
      icon: <ReferenceIcon />,
      previewComponent: ReferencePreview,
      component: (widget: GQLWidget): PropertySectionComponent<GQLWidget> | null => {
        let propertySectionComponent: PropertySectionComponent<GQLWidget> | null = null;

        if (widget.__typename === 'ReferenceWidget') {
          propertySectionComponent = ReferencePropertySection;
        }
        return propertySectionComponent;
      },
    },
    {
      name: 'CustomImageWidget',
      icon: <CustomImageIcon />,
      previewComponent: CustomImagePreview,
      component: (widget: GQLWidget): PropertySectionComponent<GQLWidget> | null => {
        if (widget.__typename === 'CustomImageWidget') {
          return CustomImageSection;
        }
        return null;
      },
    },
  ],
});

// Plug both (widgets and node) graphQl document transformers
extensionRegistry.putData(apolloClientOptionsConfigurersExtensionPoint, {
  identifier: `papyrusweb_${apolloClientOptionsConfigurersExtensionPoint.identifier}`,
  data: [nodeApolloClientOptionsConfigurer, widgetsApolloClientOptionsConfigurer],
});

// Palette tools contribution
extensionRegistry.addComponent(diagramPaletteToolExtensionPoint, {
  identifier: 'papyrus-diagram-tools',
  Component: PapyrusPopupToolContribution,
});

// Tree Item context menu contributions
extensionRegistry.addComponent(treeItemContextMenuEntryExtensionPoint, {
  identifier: 'papyrus-custom-tree-menu-profile',
  Component: UMLModelTreeItemContextMenuContribution,
});
extensionRegistry.addComponent(treeItemContextMenuEntryExtensionPoint, {
  identifier: 'papyrus-custom-tree-menu-stereotype',
  Component: UMLElementTreeItemContextMenuContribution,
});
extensionRegistry.addComponent(treeItemContextMenuEntryExtensionPoint, {
  identifier: 'papyrus-custom-tree-menu-publish-profile',
  Component: PublishProfileTreeItemContextMenuContribution,
});

// Help component contribution
extensionRegistry.addComponent(navigationBarMenuIconExtensionPoint, {
  identifier: 'papyrus-help',
  Component: () => <Help />,
});

// Footer contribution
extensionRegistry.addComponent(footerExtensionPoint, {
  identifier: 'papyrus-footer',
  Component: Footer,
});

// Main icon contribution
extensionRegistry.addComponent(navigationBarIconExtensionPoint, {
  identifier: 'papyrusweb_navigationbar#icon',
  Component: PapyrusIcon,
});

ReactDOM.render(
  <SiriusWebApplication httpOrigin={httpOrigin} wsOrigin={wsOrigin} extensionRegistry={extensionRegistry}>
    <DiagramRepresentationConfiguration nodeTypeRegistry={nodeTypeRegistryValue} />
  </SiriusWebApplication>,
  document.getElementById('root')
);
