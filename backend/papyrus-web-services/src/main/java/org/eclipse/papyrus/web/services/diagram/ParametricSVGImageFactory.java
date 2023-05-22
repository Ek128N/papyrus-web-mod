/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
package org.eclipse.papyrus.web.services.diagram;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.eclipse.papyrus.web.application.representations.configuration.ParametricSVGImageRegistry;
import org.eclipse.sirius.components.collaborative.diagrams.api.IParametricSVGImageFactory;
import org.eclipse.sirius.components.collaborative.diagrams.api.IParametricSVGImageRegistry;
import org.eclipse.sirius.components.collaborative.diagrams.api.ParametricSVGImage;
import org.eclipse.sirius.components.collaborative.diagrams.api.SVGAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Implementation of {@link IParametricSVGImageFactory}.
 *
 * @author lfasani
 */
@Service
public class ParametricSVGImageFactory implements IParametricSVGImageFactory {

    private static final String D_ATTRIBUTE = "d";

    private static final String ID_ATTRIBUTE = "id";

    private static final String CONTAINS_ID = "//*[contains(@id, '%s')]";
    
    private static final String CONTAINS_IDS = CONTAINS_ID + "|" + CONTAINS_ID;

    private static final String HEIGHT = "height";

    private static final String WIDTH = "width";
    
    private static final String RECTANGLE_ELEMENT_LABEL_ID = "labelRectangle";

    private static final String RECTANGLE_ELEMENT_MAIN_ID = "mainRectangle";

    private static final String NOTE_MAIN_ID = "note_main";

    private static final String NOTE_TRIANGLE_ID = "note_triangle";

    private static final Integer PADDING = 5;

    private static final Integer NOTE_TRIANGLE_SIDE = 15;

    private final Logger logger = LoggerFactory.getLogger(ParametricSVGImageFactory.class);

    private final List<IParametricSVGImageRegistry> parametricSVGImageServices;

    public ParametricSVGImageFactory(List<IParametricSVGImageRegistry> parametricSVGImageServices) {
        this.parametricSVGImageServices = parametricSVGImageServices;
    }

    @Override
    public Optional<byte[]> createSvg(String svgName, EnumMap<SVGAttribute, String> attributesValues) {

        // @formatter:off
        Optional<ParametricSVGImage> svgImageOpt = this.parametricSVGImageServices.stream()
            .flatMap(service-> service.getImages().stream())
            .filter(image -> {
                return svgName.equals(image.getId().toString());
            })
            .findFirst();
        // @formatter:on

        if (svgImageOpt.isPresent()) {
            ParametricSVGImage parametricSVGImage = svgImageOpt.get();
            ClassPathResource classPathResource = new ClassPathResource(parametricSVGImage.getPath());
            try (InputStream inputStream = classPathResource.getInputStream()) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
                factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
                Document document = factory.newDocumentBuilder().parse(inputStream);
                XPath xpath = XPathFactory.newInstance().newXPath();

                // change the global size
                // @formatter:off
                Node svgNode = Optional.of(xpath.evaluate("/svg", document, XPathConstants.NODESET))
                        .filter(NodeList.class::isInstance)
                        .map(NodeList.class::cast)
                        .filter(nodeList -> nodeList.getLength() > 0)
                        .map(nodeList -> nodeList.item(0))
                        .orElse(null);
                // @formatter:on

                if (svgNode instanceof Element) {
                    Element element = (Element) svgNode;
                    Double halfBorderSize = Double.valueOf(attributesValues.get(SVGAttribute.BORDERSIZE)) * 0.5d;
                    String realWidth = String.valueOf(Double.valueOf(attributesValues.get(SVGAttribute.WIDTH)) + halfBorderSize * 2);
                    String realHeight = String.valueOf(Double.valueOf(attributesValues.get(SVGAttribute.HEIGHT)) + halfBorderSize * 2);
                    element.setAttribute(WIDTH, realWidth);
                    element.setAttribute(HEIGHT, realHeight);
                    // Allows to break the ratio and to have a better feedback when the SVG is resized in the diagram.
                    element.removeAttribute("viewBox"); //$NON-NLS-1$

                }

                if (ParametricSVGImageRegistry.PARAMETRIC_CLASS_IMAGE_ID.equals(parametricSVGImage.getId())
                        || ParametricSVGImageRegistry.PARAMETRIC_PACKAGE_IMAGE_ID.equals(parametricSVGImage.getId())) {
                    this.updateClassOrPackageSVG(attributesValues, parametricSVGImage, document);
                } else if (ParametricSVGImageRegistry.PARAMETRIC_NOTE_IMAGE_ID.equals(parametricSVGImage.getId())) {
                    this.updateNoteSVG(attributesValues, parametricSVGImage, document);
                } else if (ParametricSVGImageRegistry.PARAMETRIC_FORK_IMAGE_ID.equals(parametricSVGImage.getId())
                        || ParametricSVGImageRegistry.PARAMETRIC_JOIN_IMAGE_ID.equals(parametricSVGImage.getId())) {
                    this.updateForkSVG(attributesValues, parametricSVGImage, document);
                } else if (ParametricSVGImageRegistry.PARAMETRIC_CHOICE_IMAGE_ID.equals(parametricSVGImage.getId())) {
                    this.updateChoiceSVG(attributesValues, parametricSVGImage, document);
                }

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                Source xmlSource = new DOMSource(document);
                Result outputTarget = new StreamResult(outputStream);
                try {
                    TransformerFactory.newInstance().newTransformer().transform(xmlSource, outputTarget);
                } catch (TransformerException | TransformerFactoryConfigurationError e) {
                    this.logger.warn(e.getMessage());
                }

                InputStream documentInputStream = new ByteArrayInputStream(outputStream.toByteArray());
                return Optional.of(documentInputStream.readAllBytes());
            } catch (IOException | ParserConfigurationException | XPathExpressionException | SAXException e) {
                this.logger.warn(e.getMessage());
            }
        }

        return Optional.empty();
    }

    private void updateChoiceSVG(EnumMap<SVGAttribute, String> attributesValues, ParametricSVGImage parametricSVGImage, Document document) throws XPathExpressionException {
        String expr = String.format(CONTAINS_ID, "lozenge"); //$NON-NLS-1$

        XPath xpath = XPathFactory.newInstance().newXPath();
        // @formatter:off
        Optional.of(xpath.evaluate(expr, document, XPathConstants.NODESET))
            .filter(NodeList.class::isInstance)
            .map(NodeList.class::cast)
            .ifPresent(nodes-> {
                Element node = (Element) nodes.item(0);
                this.updateChoiceNode(attributesValues, node, parametricSVGImage);
            });
        // @formatter:on
    }

    private void updateChoiceNode(EnumMap<SVGAttribute, String> attributesValues, Element node, ParametricSVGImage parametricSVGImage) {
        attributesValues.put(SVGAttribute.BORDERSIZE, "1"); //$NON-NLS-1$
        this.updateNodeStyle(attributesValues, node);

        Double width = Double.valueOf(attributesValues.get(SVGAttribute.WIDTH));
        Double height = Double.valueOf(attributesValues.get(SVGAttribute.HEIGHT));

        // compute the path
        double leftRightShift = Double.valueOf(attributesValues.get(SVGAttribute.BORDERSIZE)) * .55 * width / height;
        double topBottomShift = Double.valueOf(attributesValues.get(SVGAttribute.BORDERSIZE)) * .55 * height / width;
        StringBuilder pathDefinition = new StringBuilder();

        pathDefinition.append(String.format("M %s,%s", String.valueOf(leftRightShift), String.valueOf(height / 2))); //$NON-NLS-1$
        pathDefinition.append(String.format(" %s,%s", String.valueOf(width / 2), String.valueOf(height - topBottomShift))); //$NON-NLS-1$
        pathDefinition.append(String.format(" %s,%s", String.valueOf(width - leftRightShift), String.valueOf(height / 2))); //$NON-NLS-1$
        pathDefinition.append(String.format(" %s,%s Z", String.valueOf(width / 2), String.valueOf(topBottomShift))); //$NON-NLS-1$

        node.setAttribute(D_ATTRIBUTE, pathDefinition.toString());
    }

    private void updateForkSVG(EnumMap<SVGAttribute, String> attributesValues, ParametricSVGImage parametricSVGImage, Document document) throws XPathExpressionException {
        String expr = String.format(CONTAINS_ID, RECTANGLE_ELEMENT_MAIN_ID);

        XPath xpath = XPathFactory.newInstance().newXPath();
        // @formatter:off
        Optional.of(xpath.evaluate(expr, document, XPathConstants.NODESET))
            .filter(NodeList.class::isInstance)
            .map(NodeList.class::cast)
            .ifPresent(nodes-> {
                Element node = (Element) nodes.item(0);
                attributesValues.put(SVGAttribute.BORDERSIZE, "2"); //$NON-NLS-1$
                this.updateNodeStyle(attributesValues, node);
                node.setAttribute(HEIGHT, attributesValues.get(SVGAttribute.HEIGHT));
                node.setAttribute(WIDTH, attributesValues.get(SVGAttribute.WIDTH));
            });
        // @formatter:on
    }

    private void updateClassOrPackageSVG(EnumMap<SVGAttribute, String> attributesValues, ParametricSVGImage parametricSVGImage, Document document) throws XPathExpressionException {

        String expr = String.format(CONTAINS_IDS, RECTANGLE_ELEMENT_LABEL_ID, RECTANGLE_ELEMENT_MAIN_ID);

        XPath xpath = XPathFactory.newInstance().newXPath();
        // @formatter:off
        Optional.of(xpath.evaluate(expr, document, XPathConstants.NODESET))
                .filter(NodeList.class::isInstance)
                .map(NodeList.class::cast)
                .ifPresent(nodes-> {
                    for (int i = 0; i < nodes.getLength(); i++) {
                        Element node = (Element) nodes.item(i);
                        this.updateClassOrPackageNode(attributesValues, node, parametricSVGImage);
                        this.translateNode(attributesValues, node);
                    }
                });
        // @formatter:on
    }

    /**
     * Translate the node of half the border vertically and horizontally so that the border thickness is fully
     * displayed.
     */
    private void translateNode(EnumMap<SVGAttribute, String> attributesValues, Element node) {
        Double halfBorderSize = Double.valueOf(attributesValues.get(SVGAttribute.BORDERSIZE)) * 0.5d;
        node.setAttribute("transform", "translate(" + String.valueOf(halfBorderSize) + "," + String.valueOf(halfBorderSize) + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    }

    private void updateNoteSVG(EnumMap<SVGAttribute, String> attributesValues, ParametricSVGImage parametricSVGImage, Document document) throws XPathExpressionException {

        String expr = String.format(CONTAINS_IDS, NOTE_MAIN_ID, NOTE_TRIANGLE_ID);

        XPath xpath = XPathFactory.newInstance().newXPath();
        // @formatter:off
        Optional.of(xpath.evaluate(expr, document, XPathConstants.NODESET))
            .filter(NodeList.class::isInstance)
            .map(NodeList.class::cast)
            .ifPresent(nodes-> {
                for (int i = 0; i < nodes.getLength(); i++) {
                    Element node = (Element) nodes.item(i);
                    this.updateNoteNode(attributesValues, node, parametricSVGImage);
                    this.translateNode(attributesValues, node);
                }
            });
        // @formatter:on
    }

    private void updateClassOrPackageNode(EnumMap<SVGAttribute, String> attributesValues, Element node, ParametricSVGImage parametricSVGImage) {
        this.updateNodeStyle(attributesValues, node);

        String idValue = node.getAttributes().getNamedItem(ID_ATTRIBUTE).getNodeValue();
        if (RECTANGLE_ELEMENT_LABEL_ID.equals(idValue)) {
            // TODO we should use ICustomNodeLabelPositionProvider
            if (ParametricSVGImageRegistry.PARAMETRIC_CLASS_IMAGE_ID.equals(parametricSVGImage.getId())) {
                // The label is centered so the label area is the same size than the container
                node.setAttribute(WIDTH, attributesValues.get(SVGAttribute.WIDTH));
            } else {
                // The label is left aligned
                node.setAttribute(WIDTH, String.valueOf(Double.valueOf(attributesValues.get(SVGAttribute.LABELWIDTH)) + 2 * PADDING));
            }
            node.setAttribute(HEIGHT, String.valueOf(Double.valueOf(attributesValues.get(SVGAttribute.LABELHEIGHT)) + 2 * PADDING));
        } else if (RECTANGLE_ELEMENT_MAIN_ID.equals(idValue)) {
            Double globalHeight = 100.;
            Double labelHeight = 10.;
            try {
                globalHeight = Double.valueOf(attributesValues.get(SVGAttribute.HEIGHT));
                labelHeight = Double.valueOf(Double.valueOf(attributesValues.get(SVGAttribute.LABELHEIGHT)) + 2 * PADDING);
            } catch (NumberFormatException e) {
                this.logger.error(e.getMessage());
            }
            Double mainRectangleYPosition = labelHeight;
            Double mainRectangleHeight = globalHeight - labelHeight;
            node.setAttribute("y", mainRectangleYPosition.toString()); //$NON-NLS-1$
            node.setAttribute(HEIGHT, mainRectangleHeight.toString());
            node.setAttribute(WIDTH, attributesValues.get(SVGAttribute.WIDTH));
        }
    }

    private void updateNoteNode(EnumMap<SVGAttribute, String> attributesValues, Element node, ParametricSVGImage parametricSVGImage) {
        this.updateNodeStyle(attributesValues, node);

        String idValue = node.getAttributes().getNamedItem(ID_ATTRIBUTE).getNodeValue();
        Double width = Math.max(NOTE_TRIANGLE_SIDE, Double.valueOf(attributesValues.get(SVGAttribute.WIDTH)));
        Double height = Math.max(NOTE_TRIANGLE_SIDE, Double.valueOf(attributesValues.get(SVGAttribute.HEIGHT)));
        String verticalSegment = " V %s"; //$NON-NLS-1$
        // compute the path
        if (NOTE_MAIN_ID.equals(idValue)) {
            StringBuilder pathDefinition = new StringBuilder();

            pathDefinition.append(String.format("M %s,0", String.valueOf(width - NOTE_TRIANGLE_SIDE))); //$NON-NLS-1$
            pathDefinition.append(" H 0"); //$NON-NLS-1$
            pathDefinition.append(String.format(verticalSegment, String.valueOf(height)));
            pathDefinition.append(String.format(" H %s", String.valueOf(width))); //$NON-NLS-1$
            pathDefinition.append(String.format(verticalSegment, NOTE_TRIANGLE_SIDE));
            pathDefinition.append(String.format(" L %s,0", width - NOTE_TRIANGLE_SIDE)); //$NON-NLS-1$

            node.setAttribute(D_ATTRIBUTE, pathDefinition.toString());
        } else if (NOTE_TRIANGLE_ID.equals(idValue)) {
            StringBuilder pathDefinition = new StringBuilder();
            pathDefinition.append(String.format("M %s,0", String.valueOf(width - NOTE_TRIANGLE_SIDE))); //$NON-NLS-1$
            pathDefinition.append(String.format(verticalSegment, String.valueOf(NOTE_TRIANGLE_SIDE)));
            pathDefinition.append(String.format(" H %s", String.valueOf(width))); //$NON-NLS-1$
            pathDefinition.append(String.format(" L %s,0", String.valueOf(width - NOTE_TRIANGLE_SIDE))); //$NON-NLS-1$

            node.setAttribute(D_ATTRIBUTE, pathDefinition.toString());
        }
    }

    private void updateNodeStyle(EnumMap<SVGAttribute, String> attributesValues, Element node) {
        Node styleNode = node.getAttributes().getNamedItem("style"); //$NON-NLS-1$
        if (styleNode != null) {
            String style = styleNode.getNodeValue();
            for (SVGAttribute svgAttribute : attributesValues.keySet()) {
                String styleProperty = null;
                if (SVGAttribute.COLOR.equals(svgAttribute)) {
                    styleProperty = "fill"; //$NON-NLS-1$
                } else if (SVGAttribute.BORDERCOLOR.equals(svgAttribute)) {
                    styleProperty = "stroke"; //$NON-NLS-1$
                } else if (SVGAttribute.BORDERSIZE.equals(svgAttribute)) {
                    styleProperty = "stroke-width"; //$NON-NLS-1$
                } else if (SVGAttribute.BORDERSTYLE.equals(svgAttribute)) {
                    styleProperty = "stroke-dasharray"; //$NON-NLS-1$
                }
                if (styleProperty != null) {
                    style = this.updateStyleValue(style, styleProperty, attributesValues.get(svgAttribute));
                }
            }
            node.setAttribute("style", style); //$NON-NLS-1$
        }
    }

    private String updateStyleValue(String style, String styleProperty, String newValue) {
        String updatedStyle = style;
        if (style.contains(styleProperty)) {
            updatedStyle = style.replaceFirst(styleProperty + ":(.*?)([;\"])", styleProperty + ":" + newValue + "$2"); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
        } else {
            updatedStyle = styleProperty + ":" + newValue + ";" + updatedStyle; //$NON-NLS-1$//$NON-NLS-2$
        }
        return updatedStyle;
    }
}
