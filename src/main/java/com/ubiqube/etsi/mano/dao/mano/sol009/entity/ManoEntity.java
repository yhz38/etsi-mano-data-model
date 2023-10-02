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
package com.ubiqube.etsi.mano.dao.mano.sol009.entity;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.ubiqube.etsi.mano.dao.mano.sol009.peers.PeerEntityEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class ManoEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	/**
	 * Type of NFV-MANO functional entity.
	 */
	@Enumerated(EnumType.STRING)
	private PeerEntityEnum type;

	/**
	 * Human-readable name of the NFV-MANO functional entity. This attribute can be
	 * modified with the PATCH method.
	 */
	@NotNull
	private String name;

	/**
	 * Human-readable description of the NFV-MANO functional entity. This attribute
	 * can be modified with the PATCH method.
	 */
	private String description;

	/**
	 * Information about the provider of the NFV-MANO functional entity. It
	 * typically includes the name of the provider.
	 */
	private String provider;

	/**
	 * The version of the software of the NFV-MANO functional entity.
	 */
	private String softwareVersion;

	/**
	 * Additional information about the software used to realize the NFV- MANO
	 * functional entity. For instance, the attribute can provide information about
	 * sourced software and corresponding release(s) used to build the entity's
	 * software.
	 */
	@ElementCollection
	private Map<String, String> softwareInfo;

	/**
	 * The deployed NFV-MANO functional entity components which realize the NFV-MANO
	 * functional entity. See note 5.
	 */
	@OneToMany(cascade = CascadeType.ALL)
	private List<ManoEntityComponent> manoEntityComponents;

	/**
	 * Information about the NFV-MANO services provided by the NFV-MANO functional
	 * entity.
	 */
	@OneToMany(cascade = CascadeType.ALL)
	private List<ManoService> manoServices;

	/**
	 * Information and current values of the configurable parameters. This attribute
	 * can be modified with the PATCH method.
	 */
	@Transient
	private ManoConfigurableParams manoConfigurableParams;

	/**
	 * Information and current values of the NFV-MANO functional entity's
	 * application state.
	 */
	private ManoEntityManoApplicationState manoApplicationState = new ManoEntityManoApplicationState();

	/**
	 * The information specific to an NFVO entity. See notes 1 and 4.
	 */
	@OneToOne(cascade = CascadeType.ALL)
	private NfvoSpecificInfo nfvoSpecificInfo;

	/**
	 * The information specific to a VNFM entity. See notes 2 and 4.
	 */
	@OneToOne(cascade = CascadeType.ALL)
	private VnfmSpecificInfo vnfmSpecificInfo;

	/**
	 * The information specific to a VIM entity. See notes 3 and 4.
	 */
	@OneToOne(cascade = CascadeType.ALL)
	private VimSpecificInfo vimSpecificInfo;

	/**
	 * The information specific to a WIM entity. See notes 4 and 6.
	 */
	@OneToOne(cascade = CascadeType.ALL)
	private WimSpecificInfo wimSpecificInfo;

	/**
	 * The information specific to a CISM entity. See notes 4 and 7.
	 */
	@Transient
	private CismSpecificInfo cismSpecificInfo;

}
