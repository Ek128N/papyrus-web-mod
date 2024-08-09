/*******************************************************************************
 * Copyright (c) 2024 CEA LIST, Obeo.
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
package org.eclipse.papyrus.web.infra;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring Data JDBC configuration.
 *
 * @author Arthur Daussy
 */
@Configuration
@EnableTransactionManagement
@EnableJdbcRepositories(basePackages = { "org.eclipse.papyrus.web.domain" })
public class PapyrusJDBCConfiguration {
}
