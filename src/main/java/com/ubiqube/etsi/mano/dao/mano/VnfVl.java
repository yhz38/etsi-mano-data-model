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

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import com.ubiqube.etsi.mano.dao.audit.Audit;
import com.ubiqube.etsi.mano.dao.audit.AuditListener;
import com.ubiqube.etsi.mano.dao.audit.Auditable;
import com.ubiqube.etsi.mano.dao.base.ToscaEntity;
import com.ubiqube.etsi.mano.dao.mano.vim.PlacementGroup;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Getter
@Setter
@Entity
@EntityListeners(AuditListener.class)
public class VnfVl implements ToscaEntity, Auditable {
	/** Serial. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	private String toscaId;
	@NotNull
	private String toscaName;

	private String state;

	private String description;

	private int initialBrRoot;

	private int initialBrLeaf;

	@Embedded
	private Audit audit;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private VlProfileEntity vlProfileEntity;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<PlacementGroup> placementGroup;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> affinityRules;

	public void addAffinity(final String toscaName2) {
		if (null == affinityRules) {
			this.affinityRules = new LinkedHashSet<>();
		}
		affinityRules.add(toscaName2);
	}
}
