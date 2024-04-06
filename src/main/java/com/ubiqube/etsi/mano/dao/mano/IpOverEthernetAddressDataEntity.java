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
package com.ubiqube.etsi.mano.dao.mano;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import lombok.Data;

/**
 * TODO: maybe we can remove this table. USED:
 * IpOverEthernetAddressDataIpAddressesEntity
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Data
@Entity
public class IpOverEthernetAddressDataEntity implements Serializable {
	/** Serial. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	private String macAddress = null;

	@Valid
	@OneToMany
	private List<IpOverEthernetAddressDataIpAddressesEntity> ipAddresses = null;

	private TypeEnum type = null;

	private String addresses = null;

	private Boolean isDynamic = null;
	// 3.3.1
	private String segmentationId;
	@Embedded
	private IpOverEthernetAddressInfoAddressRangeEntity addressRange;

	private String subnetId = null;

	/**
	 * The type of the IP addresses
	 */
	public enum TypeEnum {
		PV4("PV4"),

		PV6("PV6");

		private final String value;

		TypeEnum(final String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static TypeEnum fromValue(final String text) {
			for (final TypeEnum b : TypeEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}

	/**
	 * Specifies the encapsulation type for the traffics coming in and out of the
	 * trunk subport. Permitted values: - VLAN: the subport uses VLAN as
	 * encapsulation type. - INHERIT: the subport gets its segmentation type from
	 * the network it’s connected to. This attribute may be present for CP instances
	 * that represent subports in a trunk and shall be absent otherwise. If this
	 * attribute is not present for a subport CP instance, default value VLAN shall
	 * be used.
	 */
	public enum SegmentationTypeEnum {
		VLAN("VLAN"),

		INHERIT("INHERIT");

		private final String value;

		SegmentationTypeEnum(final String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static SegmentationTypeEnum fromValue(final String text) {
			for (final SegmentationTypeEnum b : SegmentationTypeEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}

	private SegmentationTypeEnum segmentationType = null;

}
