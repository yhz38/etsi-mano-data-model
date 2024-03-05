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
package com.ubiqube.etsi.mano.dao.mano.sol009.iface;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.ubiqube.etsi.mano.dao.mano.sol009.peers.ClientInterfaceSecurityInfo;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class ManoServiceInterface {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	/**
	 * Human-readable name of the NFV-MANO functional entity interface. This
	 * attribute can be modified with the PATCH method.
	 */
	private String name;

	/**
	 * Type of the NFV-MANO service interface produced by the NFV-MANO functional
	 * entity. Valid values are defined in clause 5.6.4.3.
	 */
	private String type;

	/**
	 * Version of the standard the interface is compliant to. See note 1.
	 */
	private String standardVersion;

	/**
	 * Provider-specific software API version.
	 */
	private String providerSpecificApiVersion;

	/**
	 * API version, in compliance with the version identifiers and parameters format
	 * specified in clause 9.1 of ETSI GS NFV-SOL 013 [4].
	 */
	private String apiVersion;

	/**
	 * Exposed API endpoint of the interface.
	 */
	@OneToOne
	private ApiEndpoint apiEndpoint;

	/**
	 * Maximum number of concurrent operation requests supported on this interface.
	 * See note 2.
	 */
	private Integer maxConcurrentIntOpNumber;

	/**
	 * Information about supported operations of this interface.
	 */
	@OneToMany
	private List<SupportedOperations> supportedOperations;

	/**
	 * State of the NFV-MANO service interface.
	 */
	private InterfaceState interfaceState;

	/**
	 * Security related information. This attribute can be modified with the PATCH
	 * method. See note 3.
	 */
	@OneToOne
	private ClientInterfaceSecurityInfo securityInfo;

	/**
	 * Additional attributes that provide metadata describing the NFV-MANO service
	 * interface. These attributes can be created, modified or removed with the
	 * PATCH method.
	 */
	@ElementCollection
	private Map<String, String> metadata = null;

}
