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
package org.eclipse.papyrus.web.application.tools.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;

/**
 * A generic creation tool.
 * <p>
 * This class is extended by diagram-specific creation tools. Subclasses can override {@link #computeToolName(EClass)}
 * to convert the type of the element to create into the actual tool name. This is useful to handle diagram-specific
 * tools or particular tool naming conventions.
 * </p>
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class CreationTool {

    private static final Pattern WORD_FINDER = Pattern.compile("(([A-Z]?[a-z]+)|([A-Z]))");

    /**
     * The name of the tool section containing the tool.
     */
    private String toolSection;

    /**
     * The name of the tool inside the tool section.
     */
    private String toolName;

    /**
     * The type of the element created by the tool.
     */
    private EClass toolEClass;

    /**
     * Build the creation tool from the provided {@code toolSection} and {@code eClass}.
     *
     * @param toolSection
     *            the name of the tool section containing the creation tool
     * @param eClass
     *            the type of the element to create
     *
     * @see #computeToolName(EClass) to implement custom {@code eClass} to tool name conversion
     */
    public CreationTool(String toolSection, EClass eClass) {
        this.toolSection = toolSection;
        this.toolEClass = eClass;
        this.toolName = this.computeToolName(eClass);
    }

    /**
     * Returns the name of the tool section containing the tool.
     *
     * @return the name of the tool section containing the tool
     */
    public final String getToolSection() {
        return this.toolSection;
    }

    /**
     * Returns the name of the tool inside the tool section.
     *
     * @return the name of the tool inside the tool section
     */
    public final String getToolName() {
        return this.toolName;
    }

    /**
     * Returns the type of the element created by the tool.
     *
     * @return the type of the element created by the tool
     */
    public final EClass getToolEClass() {
        return this.toolEClass;
    }

    /**
     * Computes the actual name of the tool that creates an {@code eClass} instance.
     * <p>
     * This method can be overriden by subclasses to handle diagram-specific tools or particular tool naming
     * conventions. The default implementation of this method returns {@code "New <Type Name>"}, where {@code Type Name}
     * is the provided {@code eClass} name splitted with a space before each upper case letter (see
     * {@link #splitWordsInMixedCase(String)}). This name is the most common tool name in Papyrus Web.
     * </p>
     *
     * @param eClass
     *            the type of the element to create with the tool
     * @return the name of the tool
     */
    protected String computeToolName(EClass eClass) {
        return "New " + this.splitWordsInMixedCase(eClass.getName());
    }

    /**
     * Splits the provided {@code text} and adds a space before each upper case letter.
     *
     * @param text
     *            the text to split
     * @return the splitted text
     */
    protected String splitWordsInMixedCase(String text) {
        Matcher matcher = WORD_FINDER.matcher(text);
        List<String> words = new ArrayList<>();
        while (matcher.find()) {
            words.add(matcher.group(0));
        }
        return words.stream().map(n -> n.substring(0, 1).toUpperCase() + n.substring(1)).collect(Collectors.joining(" "));
    }

}
