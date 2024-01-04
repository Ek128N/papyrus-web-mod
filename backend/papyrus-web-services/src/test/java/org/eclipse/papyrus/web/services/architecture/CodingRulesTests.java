/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
package org.eclipse.papyrus.web.services.architecture;

import static com.tngtech.archunit.core.domain.JavaModifier.ABSTRACT;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.papyrus.web.sirius.contributions.AqlServiceClass;
import org.eclipse.papyrus.web.sirius.contributions.FactoryMethod;
import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.tests.architecture.AbstractCodingRulesTests;
import org.junit.jupiter.api.Test;

/**
 * Coding rules tests.
 *
 * @author sbegaudeau
 */
public class CodingRulesTests extends AbstractCodingRulesTests {

    private static final String IS = "is";

    private static final String GET = "get";

    private static final String TESTCASE_SUFFIX = "TestCases";

    @Override
    protected String getProjectRootPackage() {
        return ArchitectureConstants.SIRIUS_WEB_SERVICES_ROOT_PACKAGE;
    }

    @Override
    protected JavaClasses getClasses() {
        return ArchitectureConstants.CLASSES;
    }

    @Test
    @Override
    public void noClassesShouldUseApacheCommons() {
        super.noClassesShouldUseApacheCommons();
    }

    /**
     * Static methods make the lifecycle of the code complex. They make the code harder to reason about and they can
     * very easily become either:
     *
     * <ul>
     * <li>A problem to unit tests a class since they can't be mocked</li>
     * <li>God classes with a collection of unrelated utility methods</li>
     * </ul>
     *
     * With the introduction of this test, it appears that apart from some utility constructors like in the @Immutable
     * classes, we do not have a single static method with a real behavior. Thus nothing of value will be lost.
     *
     * In this test, we will ignore the following use cases:
     *
     * <ul>
     * <li>Enum since Java enum are considered as extending java.lang.Enum which comes with static methods</li>
     * <li>Java 8+ lambdas which are compiled to hidden static methods (to make it short)</li>
     * <li>@Parameters methods used by JUnit test cases</li>
     * <li>@Immutable classes which are using a static method to create the builder</li>
     * <li>@Constructor methods which are used as alternate constructors</li>
     * </ul>
     */
    @Override
    @Test
    public void noMethodsShouldBeStatic() {
        // @formatter:off
        ArchRule rule = ArchRuleDefinition.noMethods()
                .that()
                .areDeclaredInClassesThat()
                .resideInAPackage(this.getProjectRootPackage())
                .and()
                .areDeclaredInClassesThat()
                .areNotAnnotatedWith(Immutable.class)
                .and()
                .areDeclaredInClassesThat()
                .areNotAssignableTo(Enum.class)
                .and()//
                .areNotAnnotatedWith(FactoryMethod.class)//
                .and()
                .areDeclaredInClassesThat(this.isNotTestCase())
                .and(this.isNotLambda())
                .and(this.isNotSwitchTable())
                .should()
                .beStatic();
        // @formatter:on

        rule.check(this.getClasses());
    }

    /**
     * Composition should be used instead of inheritance to share business code since it is way easier to test and it is
     * easier to determine and thus maintain the dependencies of a piece of business code.
     */
    @Override
    @Test
    public void abstractClassesShouldNotContainBusinessCode() {
        // @formatter:off
        ArchRule rule = ArchRuleDefinition.classes()
                .that()
                .resideInAPackage(this.getProjectRootPackage())
                .and()
                .areNotInterfaces()
                .and()
                .haveModifier(ABSTRACT)
                .and()//
                .areNotAnnotatedWith(AqlServiceClass.class)
                .should(this.notContainBusinessCode());
        // @formatter:on

        rule.check(this.getClasses());
    }

    private DescribedPredicate<JavaClass> isNotTestCase() {
        return new DescribedPredicate<>("is not a test case") {
            @Override
            public boolean apply(JavaClass javaClass) {
                return !javaClass.getName().endsWith(TESTCASE_SUFFIX);
            }
        };
    }

    /**
     * Lambda are compiled as hidden static methods named lambda$XXX. This method will help detect them and ignore them.
     *
     * @return A predicate which will help us ignore lambda methods
     */
    private DescribedPredicate<JavaMethod> isNotLambda() {
        return new DescribedPredicate<>("is not a lambda") {
            @Override
            public boolean apply(JavaMethod javaMethod) {
                return !javaMethod.getName().startsWith("lambda$");
            }
        };
    }

    /**
     * This predicate will be used to identify business code in a class.
     *
     * <p>
     * For that it will look for the following patterns in a Java class:
     * <p>
     * <ul>
     * <li>methods which are not getters with a field matching the name of the method</li>
     * </ul>
     *
     * @return A predicate which can be used to identify business code in a class
     */
    private ArchCondition<JavaClass> notContainBusinessCode() {
        return new ArchCondition<>("not contain business code") {
            @Override
            public void check(JavaClass javaClass, ConditionEvents events) {
                boolean isConditionSatisfied = true;

                Set<JavaMethod> methods = javaClass.getMethods();
                Iterator<JavaMethod> iterator = methods.iterator();
                while (isConditionSatisfied && iterator.hasNext()) {
                    JavaMethod javaMethod = iterator.next();
                    String name = javaMethod.getName();
                    if (name.startsWith(IS) && (javaMethod.getRawReturnType().isAssignableTo(Boolean.class) || javaMethod.getRawReturnType().isAssignableTo(boolean.class))) {
                        name = name.substring(IS.length());
                    } else if (name.startsWith(GET)) {
                        name = name.substring(GET.length());
                    }

                    if (!name.isBlank()) {
                        name = name.substring(0, 1).toLowerCase() + name.substring(1, name.length());
                        isConditionSatisfied = javaClass.tryGetField(name).isPresent();
                    } else {
                        isConditionSatisfied = false;
                    }
                }

                String message = "The abstract class does not have any business code";
                if (!isConditionSatisfied) {
                    String pattern = "The abstract class {0} does contain business code, please favor composition over inheritance to share business code";
                    message = MessageFormat.format(pattern, javaClass.getSimpleName());
                }
                events.add(new SimpleConditionEvent(javaClass, isConditionSatisfied, message));
            }
        };
    }

    /**
     * Some switch can be compiled as hidden static methods named $SWITCH_TABLE$. This predicate will help detect them
     * and ignore them.
     *
     * @return A predicate which help us ignore switch expressions
     */
    private DescribedPredicate<JavaMethod> isNotSwitchTable() {
        return new DescribedPredicate<>("is not a switch table (whatever that is...)") {
            @Override
            public boolean apply(JavaMethod javaMethod) {
                return !javaMethod.getFullName().contains("$SWITCH_TABLE$");
            }
        };
    }

}
