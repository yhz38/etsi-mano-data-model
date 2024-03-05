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
package com.ubiqube.etsi.mano.dao.mano.sol009.entity;

import java.util.Set;
import java.util.UUID;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ManoService {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	/**
	 * Human-readable name of the NFV-MANO service. This attribute can be modified
	 * with the PATCH method.
	 */
	private String name;

	/**
	 * Human-readable description of the NFV-MANO service. This attribute can be
	 * modified with the PATCH method.
	 */
	private String description;

	/**
	 * Reference to the NFV-MANO interfaces associated to the NFV-MANO service. If
	 * cardinality is greater than one, the type of ManoServiceInterface (see clause
	 * 5.6.3.3) shall be the same. The identifier of the ManoServiceInterface is
	 * referred. See note.
	 */
	@ElementCollection
	private Set<String> manoServiceInterfaceIds;

}
