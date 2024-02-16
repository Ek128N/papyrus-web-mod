/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
import { loadDevMessages, loadErrorMessages } from '@apollo/client/dev';
import { NodeTypeContribution } from '@eclipse-sirius/sirius-components-diagrams';
import {
  GQLWidget,
  PropertySectionComponent,
  PropertySectionComponentRegistry,
  PropertySectionContext,
  PropertySectionContextValue,
  WidgetContribution,
} from '@eclipse-sirius/sirius-components-forms';
import {
  GQLReferenceWidget,
  ReferenceIcon,
  ReferencePreview,
  ReferencePropertySection,
} from '@eclipse-sirius/sirius-components-widget-reference';
import FormatListBulletedIcon from '@material-ui/icons/FormatListBulleted';
import LinearScaleOutlinedIcon from '@material-ui/icons/LinearScaleOutlined';
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
import { SliderPreview } from './widgets/SliderPreview';
import { GQLSlider } from './widgets/SliderFragment.types';
import { SliderPropertySection } from './widgets/SliderPropertySection';
import { ContainmentReferenceIcon } from './widgets/containmentReference/ContainmentReferenceIcon';
import ContainmentReferenceSection from './widgets/containmentReference/ContainmentReferenceSection';
import { LanguageExpressionIcon } from './widgets/languageExpression/LanguageExpressionIcon';
import { LanguageExpressionSection } from './widgets/languageExpression/LanguageExpressionSection';
import { PrimitiveListWidgetPreview } from './widgets/primitiveList/PrimitiveListWidgetPreview';
import { PrimitiveListSection } from './widgets/primitiveList/PrimitiveListWidgetPropertySection';
import { PrimitiveRadioIcon } from './widgets/primitiveRadio/PrimitiveRadioIcon';
import { PrimitiveRadioSection } from './widgets/primitiveRadio/PrimitiveRadioSection';
import {
  SiriusWebApplication,
  NodeTypeRegistry,
  navigationBarIconExtensionPoint,
  navigationBarMenuExtensionPoint,
  DiagramRepresentationConfiguration,
} from '@papyrus-web/sirius-web-application';
import { ContainmentReferencePreview } from './widgets/containmentReference/ContainmentReferencePreview';
import { PrimitiveRadioPreview } from './widgets/primitiveRadio/PrimitiveRadioPreview';
import { LanguageExpressionPreview } from './widgets/languageExpression/LanguageExpressionPreview';
import { Help } from './core/Help';

import './ReactFlow.css';
import './fonts.css';
import './portals.css';
import './reset.css';
import './variables.css';

if (process.env.NODE_ENV !== 'production') {
  loadDevMessages();
  loadErrorMessages();
}

const isSlider = (widget: GQLWidget): widget is GQLSlider => widget.__typename === 'Slider';
const isReferenceWidget = (widget: GQLWidget): widget is GQLReferenceWidget => widget.__typename === 'ReferenceWidget';

const propertySectionsRegistry: PropertySectionComponentRegistry = {
  getComponent: (widget: GQLWidget): PropertySectionComponent<GQLWidget> | null => {
    if (isSlider(widget)) {
      return SliderPropertySection;
    } else if (isReferenceWidget(widget)) {
      return ReferencePropertySection;
    } else if (widget.__typename === 'LanguageExpression') {
      return LanguageExpressionSection;
    } else if (widget.__typename === 'PrimitiveRadio') {
      return PrimitiveRadioSection;
    } else if (widget.__typename === 'PrimitiveListWidget') {
      return PrimitiveListSection;
    } else if (widget.__typename === 'ContainmentReferenceWidget') {
      return ContainmentReferenceSection;
    }
    return null;
  },
  getPreviewComponent: (widget: GQLWidget) => {
    if (widget.__typename === 'Slider') {
      return SliderPreview;
    } else if (widget.__typename === 'ReferenceWidget') {
      return ReferencePreview;
    } else if (widget.__typename === 'PrimitiveListWidget') {
      return PrimitiveListWidgetPreview;
    } else if (widget.__typename === 'LanguageExpression') {
      return LanguageExpressionPreview;
    } else if (widget.__typename === 'PrimitiveRadio') {
      return PrimitiveRadioPreview;
    } else if (widget.__typename === 'ContainmentReferenceWidget') {
      return ContainmentReferencePreview;
    }
    return null;
  },
  getWidgetContributions: () => {
    const sliderWidgetContribution: WidgetContribution = {
      name: 'Slider',
      fields: `label iconURL minValue maxValue currentValue`,
      icon: <LinearScaleOutlinedIcon />,
    };
    const referenceWidget: WidgetContribution = {
      name: 'ReferenceWidget',
      fields: `label
               iconURL
               ownerId
               descriptionId
               reference {
                 ownerKind
                 referenceKind
                 containment
                 manyValued
               }
               referenceValues {
                 id
                 label
                 kind
                 iconURL
               }
               style {
                 color
                 fontSize
                 italic
                 bold
                 underline
                 strikeThrough
               }`,
      icon: <ReferenceIcon />,
    };
    const languageExpressionWidget: WidgetContribution = {
      name: 'LanguageExpression',
      fields: 'id label iconURL languages { id label body } predefinedLanguages',
      icon: <LanguageExpressionIcon />,
    };
    const primitiveRadioWidget: WidgetContribution = {
      name: 'PrimitiveRadio',
      fields: 'id label iconURL candidateList candidateValue',
      icon: <PrimitiveRadioIcon />,
    };
    const primitiveListWidget: WidgetContribution = {
      name: 'PrimitiveListWidget',
      fields:
        'label iconURL canAdd canReorder hasCandidates items { id label iconURL deletable hasAction actionIconURL } style { color fontSize italic bold underline strikeThrough }',
      icon: <FormatListBulletedIcon />,
    };
    const containmentReferenceWidget: WidgetContribution = {
      name: 'ContainmentReferenceWidget',
      icon: <ContainmentReferenceIcon />,
      fields: `label
              iconURL
              ownerId
              descriptionId
              containmentReference {
                ownerKind
                referenceKind
                isMany
                canMove
              }
              referenceValues {
                id
                label
                kind
                iconURL
              }
              style {
                color
                fontSize
                italic
                bold
                underline
                strikeThrough
              }`,
    };
    return [
      sliderWidgetContribution,
      referenceWidget,
      languageExpressionWidget,
      primitiveRadioWidget,
      primitiveListWidget,
      containmentReferenceWidget,
    ];
  },
};

const propertySectionRegistryValue: PropertySectionContextValue = {
  propertySectionsRegistry,
};

const nodeTypeRegistryValue: NodeTypeRegistry = {
  graphQLNodeStyleFragments: [
    {
      type: 'EllipseNodeStyle',
      fields: `borderColor borderSize borderStyle color`,
    },
    {
      type: 'PackageNodeStyle',
      fields: `borderColor borderSize borderStyle color`,
    },
    {
      type: 'RectangleWithExternalLabelNodeStyle',
      fields: `borderColor borderSize borderStyle color`,
    },
    {
      type: 'NoteNodeStyle',
      fields: `borderColor borderSize borderStyle color`,
    },
    {
      type: 'InnerFlagNodeStyle',
      fields: `borderColor borderSize borderStyle color`,
    },
    {
      type: 'OuterFlagNodeStyle',
      fields: `borderColor borderSize borderStyle color`,
    },
    {
      type: 'CuboidNodeStyle',
      fields: 'borderColor borderSize borderStyle color',
    },
  ],
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

const extensionRegistry = new ExtensionRegistry();
extensionRegistry.addComponent(navigationBarIconExtensionPoint, {
  Component: PapyrusIcon,
});
extensionRegistry.addComponent(navigationBarMenuExtensionPoint, {
  Component: Help,
});

ReactDOM.render(
  <PropertySectionContext.Provider value={propertySectionRegistryValue}>
    <SiriusWebApplication httpOrigin={httpOrigin} wsOrigin={wsOrigin} extensionRegistry={extensionRegistry}>
      <DiagramRepresentationConfiguration nodeTypeRegistry={nodeTypeRegistryValue} />+{' '}
    </SiriusWebApplication>
  </PropertySectionContext.Provider>,
  document.getElementById('root')
);
