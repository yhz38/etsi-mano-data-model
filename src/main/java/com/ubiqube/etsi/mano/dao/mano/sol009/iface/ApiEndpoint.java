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
package com.ubiqube.etsi.mano.dao.mano.sol009.iface;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ApiEndpoint {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	/**
	 * Indicates the scheme ("http" or "https"), the host name and optional port,
	 * and an optional sequence of path segments that together represent a prefix
	 * path. Shall be present for ETSI NFV specified RESTful NFV-MANO APIs (see also
	 * clause 4.1 of ETSI GS NFV-SOL 013 [4]). May be present otherwise. This
	 * attribute can be modified with the PATCH method.
	 */
	private String apiRoot;

	/**
	 * Indicates the interface name in an abbreviated form. Shall be present for
	 * ETSI NFV specified RESTful NFV-MANO APIs. The {apiName} of each interface is
	 * defined in the standard the interface is compliant to (see also clause 4.1 of
	 * ETSI GS NFV-SOL 013 [4]). May be present otherwise.
	 */
	private String apiName;

	/**
	 * Indicates the current major version of the API. Shall be present for ETSI NFV
	 * specified RESTful NFV-MANO APIs. The major version is defined in the standard
	 * the interface is compliant to (see also clause 4.1 of ETSI GS NFV-SOL 013
	 * [4]). May be present otherwise.
	 */
	private String apiMajorVersion;

	/**
	 * URL of the API endpoint. For ETSI NFV specified RESTful NFV-MANO APIs, the
	 * following prefix structure is used (see also clause 4.1 of ETSI GS NFV-SOL
	 * 013 [4]): {apiRoot}/{apiName}/{apiMajorVersion} For APIs not specified by
	 * ETSI NFV as part of the RESTful NFV-MANO APIs, this attribute can be modified
	 * with the PATCH method. For RESTful NFV-MANO APIs specified by ETSI NFV, this
	 * attribute shall not be modified. Instead, changes are handled indirectly via
	 * patching of the "apiRoot" attribute's value.
	 */
	private String apiUri;

}
