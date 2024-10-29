/*******************************************************************************
 * Copyright (c) 2019, 2024 CEA LIST, Obeo.
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
import { ExtensionRegistry } from '@eclipse-sirius/sirius-components-core';
import {
  SiriusWebApplication,
  DiagramRepresentationConfiguration,
  NodeTypeRegistry,
  navigationBarIconExtensionPoint,
  ApolloClientOptionsConfigurer,
  apolloClientOptionsConfigurersExtensionPoint,
  footerExtensionPoint,
  navigationBarMenuIconExtensionPoint,
  navigationBarRightContributionExtensionPoint,
} from '@eclipse-sirius/sirius-web-application';
import FormatListBulletedIcon from '@mui/icons-material/FormatListBulleted';
import { loadDevMessages, loadErrorMessages } from '@apollo/client/dev';
import { NodeTypeContribution, diagramPaletteToolExtensionPoint } from '@eclipse-sirius/sirius-components-diagrams';
import ReactDOM from 'react-dom';
import { httpOrigin, wsOrigin } from './core/URL';
import { PapyrusIcon } from './core/PapyrusIcon';
import { CuboidNodeLayoutHandler } from './nodes/cuboid/CuboidNodeLayoutHandler';
import { CuboidNodeConverter } from './nodes/cuboid/CuboidNodeConverter';
import { CuboidNode } from './nodes/cuboid/CuboidNode';
import { EllipseNode } from './nodes/ellipse/EllipseNode';
import { EllipseNodeConverter } from './nodes/ellipse/EllipseNodeConverter';
import { EllipseNodeLayoutHandler } from './nodes/ellipse/EllipseNodeLayoutHandler';
import { NoteNode } from './nodes/note/NoteNode';
import { NoteNodeConverter } from './nodes/note/NoteNodeConverter';
import { NoteNodeLayoutHandler } from './nodes/note/NoteNodeLayoutHandler';
import { RectangleWithExternalLabelNode } from './nodes/rectangleWithExternalLabel/RectangleWithExternalLabelNode';
import { RectangleWithExternalLabelNodeConverter } from './nodes/rectangleWithExternalLabel/RectangleWithExternalLabelNodeConverter';
import { RectangleWithExternalLabelNodeLayoutHandler } from './nodes/rectangleWithExternalLabel/RectangleWithExternalLabelNodeLayoutHandler';
import { PackageNode } from './nodes/package/PackageNode';
import { PackageNodeConverter } from './nodes/package/PackageNodeConverter';
import { PackageNodeLayoutHandler } from './nodes/package/PackageNodeLayoutHandler';
import { InnerFlagNodeLayoutHandler } from './nodes/innerFlag/InnerFlagNodeLayoutHandler';
import { InnerFlagNodeConverter } from './nodes/innerFlag/InnerFlagNodeConverter';
import { InnerFlagNode } from './nodes/innerFlag/InnerFlagNode';
import { OuterFlagNodeLayoutHandler } from './nodes/outerFlag/OuterFlagNodeLayoutHandler';
import { OuterFlagNodeConverter } from './nodes/outerFlag/OuterFlagNodeConverter';
import { OuterFlagNode } from './nodes/outerFlag/OuterFlagNode';
import { Help } from './core/Help';
import { PapyrusWorkspace } from './workspace/PapyrusWorkspace';

import './ReactFlow.css';
import './fonts.css';
import './portals.css';
import './reset.css';
import './variables.css';
import { nodesStyleDocumentTransform } from './nodes/NodesDocumentTransform';
import { Footer } from './footer/Footer';
import { PapyrusPopupToolContribution } from './diagram-tools/PapyrusPopupToolContribution';
import { customWidgetsDocumentTransform } from './widgets/CustomWidgetsDocumentTransform';
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
import { UMLModelTreeItemContextMenuContribution } from './profile/apply-profile/UMLModelTreeItemContextMenuContribution';
import { UMLElementTreeItemContextMenuContribution } from './profile/apply-stereotype/UMLElementTreeItemContextMenuContribution';

import ContainmentReferenceSection from './widgets/containmentReference/ContainmentReferenceSection';
import { ContainmentReferencePreview } from './widgets/containmentReference/ContainmentReferencePreview';
import { ContainmentReferenceIcon } from './widgets/containmentReference/ContainmentReferenceIcon';
import { PrimitiveListWidgetPreview } from './widgets/primitiveList/PrimitiveListWidgetPreview';
import { PrimitiveListSection } from './widgets/primitiveList/PrimitiveListWidgetPropertySection';
import { LanguageExpressionIcon } from './widgets/languageExpression/LanguageExpressionIcon';
import { LanguageExpressionPreview } from './widgets/languageExpression/LanguageExpressionPreview';
import { LanguageExpressionSection } from './widgets/languageExpression/LanguageExpressionSection';
import { PrimitiveRadioIcon } from './widgets/primitiveRadio/PrimitiveRadioIcon';
import { PrimitiveRadioPreview } from './widgets/primitiveRadio/PrimitiveRadioPreview';
import { PrimitiveRadioSection } from './widgets/primitiveRadio/PrimitiveRadioSection';
import { PublishProfileTreeItemContextMenuContribution } from './profile/publish-profile/PublishProfileTreeItemContextMenuContribution';
import { ShareProjectTreeItemContextMenuContribution } from './workspace/share-project/ShareProjectTreeItemContextMenuContribution';
import { RevokeProjectTreeItemContextMenuContribution } from './workspace/revoke-project/RevokeProjectTreeItemContextMenuContribution';

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
  ],
  nodeConverters: [
    new EllipseNodeConverter(),
    new PackageNodeConverter(),
    new RectangleWithExternalLabelNodeConverter(),
    new NoteNodeConverter(),
    new InnerFlagNodeConverter(),
    new OuterFlagNodeConverter(),
    new CuboidNodeConverter(),
  ],
  nodeTypeContributions: [
    <NodeTypeContribution component={EllipseNode} type={'ellipseNode'} />,
    <NodeTypeContribution component={PackageNode} type={'packageNode'} />,
    <NodeTypeContribution component={RectangleWithExternalLabelNode} type={'rectangleWithExternalLabelNode'} />,
    <NodeTypeContribution component={NoteNode} type={'noteNode'} />,
    <NodeTypeContribution component={InnerFlagNode} type={'innerFlagNode'} />,
    <NodeTypeContribution component={OuterFlagNode} type={'outerFlagNode'} />,
    <NodeTypeContribution component={CuboidNode} type={'cuboidNode'} />,
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
extensionRegistry.addComponent(treeItemContextMenuEntryExtensionPoint, {
  identifier: 'papyrus-custom-tree-menu-share-project',
  Component: ShareProjectTreeItemContextMenuContribution,
});
extensionRegistry.addComponent(treeItemContextMenuEntryExtensionPoint, {
  identifier: 'papyrus-custom-tree-menu-revoke-project',
  Component: RevokeProjectTreeItemContextMenuContribution,
});

// Help component contribution
extensionRegistry.addComponent(navigationBarMenuIconExtensionPoint, {
  identifier: 'papyrus-help',
  Component: () => <Help />,
});

// Workspace component contribution to navigationBarRightContributionExtensionPoint
extensionRegistry.addComponent(navigationBarRightContributionExtensionPoint, {
  identifier: 'papyrus-workspace',
  Component: () => <PapyrusWorkspace />,
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
