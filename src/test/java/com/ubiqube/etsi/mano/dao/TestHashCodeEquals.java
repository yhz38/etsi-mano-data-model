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

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ubiqube.etsi.mano.dao.mano.CpProtocolDataEntity;
import com.ubiqube.etsi.mano.dao.mano.ExtCpInfo;
import com.ubiqube.etsi.mano.dao.mano.ExtVirtualLinkDataEntity;
import com.ubiqube.etsi.mano.dao.mano.VirtualLinkInfo;
import com.ubiqube.etsi.mano.dao.mano.VirtualStorageResourceInfo;
import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfExtCpConfiguration;
import com.ubiqube.etsi.mano.dao.mano.VnfMonitoringParameter;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.VnfcResourceInfoEntity;

import uk.co.jemos.podam.api.DefaultClassInfoStrategy;
import uk.co.jemos.podam.api.PodamFactoryImpl;

class TestHashCodeEquals {
	private final PodamFactoryImpl podam;
	private final ObjectMapper mapper;

	public TestHashCodeEquals() {
		podam = new PodamFactoryImpl();
		podam.getStrategy().addOrReplaceTypeManufacturer(String.class, new UUIDManufacturer());
		podam.getStrategy().setDefaultNumberOfCollectionElements(1);
		podam.getStrategy().addOrReplaceTypeManufacturer(VnfPackage.class, (a, b, c) -> null);
		podam.getStrategy().addOrReplaceTypeManufacturer(VnfCompute.class, (a, b, c) -> null);
		final DefaultClassInfoStrategy classInfoStrategy = DefaultClassInfoStrategy.getInstance();
		classInfoStrategy.addExcludedField(VirtualLinkInfo.class, "vnfLcmOpOccs");
		classInfoStrategy.addExcludedField(ExtVirtualLinkDataEntity.class, "vnfInstance");
		classInfoStrategy.addExcludedField(CpProtocolDataEntity.class, "vnfExtCpConfiguration");
		classInfoStrategy.addExcludedField(VnfExtCpConfiguration.class, "vnfExtCpDataEntity");
		classInfoStrategy.addExcludedField(VirtualLinkInfo.class, "vimConnectionInformation");
		mapper = new ObjectMapper().registerModule(new JavaTimeModule());
	}

	@Test
	void testVirtualStorageResourceInfo() throws IOException {
		final VirtualStorageResourceInfo obj = podam.manufacturePojo(VirtualStorageResourceInfo.class);
		final String str = mapper.writeValueAsString(obj);
		final VirtualStorageResourceInfo a = mapper.readValue(str.getBytes(), VirtualStorageResourceInfo.class);
		final VirtualStorageResourceInfo b = mapper.readValue(str.getBytes(), VirtualStorageResourceInfo.class);
		assertEquals(a, b);
		assertEquals(a.hashCode(), b.hashCode());
		assertNotNull(a.toString());
	}

	/**
	 * Because of recursive dependencies.
	 *
	 * @throws IOException
	 */
	void testExtVirtualLinkDataEntity() throws IOException {
		final ExtVirtualLinkDataEntity obj = podam.manufacturePojo(ExtVirtualLinkDataEntity.class);
		final String str = mapper.writeValueAsString(obj);
		final ExtVirtualLinkDataEntity a = mapper.readValue(str.getBytes(), ExtVirtualLinkDataEntity.class);
		final ExtVirtualLinkDataEntity b = mapper.readValue(str.getBytes(), ExtVirtualLinkDataEntity.class);
		assertEquals(a, b);
		assertEquals(a.hashCode(), b.hashCode());
		assertNotNull(a.toString());
	}

	@Test
	void testExtCpInfo() throws IOException {
		final ExtCpInfo obj = podam.manufacturePojo(ExtCpInfo.class);
		final String str = mapper.writeValueAsString(obj);
		final ExtCpInfo a = mapper.readValue(str.getBytes(), ExtCpInfo.class);
		final ExtCpInfo b = mapper.readValue(str.getBytes(), ExtCpInfo.class);
		assertEquals(a, b);
		assertEquals(a.hashCode(), b.hashCode());
		assertNotNull(a.toString());
	}

	@Test
	void testVnfcResourceInfoEntity() throws IOException {
		final VnfcResourceInfoEntity obj = podam.manufacturePojo(VnfcResourceInfoEntity.class);
		final String str = mapper.writeValueAsString(obj);
		final VnfcResourceInfoEntity a = mapper.readValue(str.getBytes(), VnfcResourceInfoEntity.class);
		final VnfcResourceInfoEntity b = mapper.readValue(str.getBytes(), VnfcResourceInfoEntity.class);
		assertEquals(a, b);
		assertEquals(a.hashCode(), b.hashCode());
		assertNotNull(a.toString());
	}

	@Test
	void testVnfMonitoringParameter() throws IOException {
		final VnfMonitoringParameter obj = podam.manufacturePojo(VnfMonitoringParameter.class);
		final String str = mapper.writeValueAsString(obj);
		final VnfMonitoringParameter a = mapper.readValue(str.getBytes(), VnfMonitoringParameter.class);
		final VnfMonitoringParameter b = mapper.readValue(str.getBytes(), VnfMonitoringParameter.class);
		assertEquals(a, b);
		assertEquals(a.hashCode(), b.hashCode());
		assertNotNull(a.toString());
	}

	@Test
	void testVirtualLinkInfo() throws IOException {
		final VirtualLinkInfo obj = podam.manufacturePojo(VirtualLinkInfo.class);
		final String str = mapper.writeValueAsString(obj);
		final VirtualLinkInfo a = mapper.readValue(str.getBytes(), VirtualLinkInfo.class);
		final VirtualLinkInfo b = mapper.readValue(str.getBytes(), VirtualLinkInfo.class);
		assertEquals(a, b);
		assertEquals(a.hashCode(), b.hashCode());
		assertNotNull(a.toString());
	}
}
