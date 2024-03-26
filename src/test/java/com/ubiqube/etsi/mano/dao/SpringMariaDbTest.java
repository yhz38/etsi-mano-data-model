/**
 *     Copyright (C) 2019-2024 Ubiqube.
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
package com.ubiqube.etsi.mano.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.test.context.ActiveProfiles;

//@SpringBootTest
@EntityScan({ "com.ubiqube.etsi.mano" })
@ActiveProfiles(value = "mariadb")
class SpringMariaDbTest {

	@Autowired
	DataSource ds;

	@Test
	void dummy() {
		assertTrue(true);
	}

	// @Test
	void testMariaDbSchema() throws Exception {
		assertTrue(true);
		try (Connection conn = ds.getConnection();
				final PreparedStatement stmt = conn.prepareStatement("select * from vnf_package", 0)) {
			stmt.execute();
		}
	}
}
