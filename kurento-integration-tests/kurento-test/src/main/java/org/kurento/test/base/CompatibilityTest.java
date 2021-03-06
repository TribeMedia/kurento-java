/*
 * (C) Copyright 2015 Kurento (http://kurento.org/)
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 */
package org.kurento.test.base;

import org.junit.experimental.categories.Category;
import org.kurento.commons.testing.SystemCompatibilityTests;
import org.kurento.test.config.TestScenario;

/**
 * Compatibility tests.
 * 
 * @author Boni Garcia (bgarcia@gsyc.es)
 * @since 5.0.5
 */
@Category(SystemCompatibilityTests.class)
public class CompatibilityTest extends BrowserKurentoClientTest {

	public CompatibilityTest(TestScenario testScenario) {
		super(testScenario);
	}

}
