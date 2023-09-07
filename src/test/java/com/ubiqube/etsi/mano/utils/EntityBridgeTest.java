/**
 *     Copyright (C) 2019-2023 Ubiqube.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
 */
package com.ubiqube.etsi.mano.utils;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.dao.mano.GrantResponse;

@SuppressWarnings("static-method")
class EntityBridgeTest {

	@Test
	void testNull_01() {
		try (final EntityBridge eb = new EntityBridge()) {
			final String ret = eb.toIndexedValue(null, null);
			assertNull(ret);
		}
	}

	@Test
	void testNull_02() {
		try (final EntityBridge eb = new EntityBridge()) {
			final GrantResponse be = new GrantResponse();
			final String ret = eb.toIndexedValue(be, null);
			assertNull(ret);
		}
	}

	@Test
	void testNull_03() {
		try (final EntityBridge eb = new EntityBridge()) {
			final GrantResponse be = new GrantResponse();
			be.setId(UUID.randomUUID());
			final String ret = eb.toIndexedValue(be, null);
			assertNotNull(ret);
		}
	}
}
