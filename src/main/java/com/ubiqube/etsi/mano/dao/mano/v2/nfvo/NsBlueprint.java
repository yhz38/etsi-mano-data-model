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
package com.ubiqube.etsi.mano.dao.mano.v2.nfvo;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import com.ubiqube.etsi.mano.dao.audit.AuditListener;
import com.ubiqube.etsi.mano.dao.mano.BlueZoneGroupInformation;
import com.ubiqube.etsi.mano.dao.mano.ExtManagedVirtualLinkDataEntity;
import com.ubiqube.etsi.mano.dao.mano.ExtVirtualLinkDataEntity;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.ZoneInfoEntity;
import com.ubiqube.etsi.mano.dao.mano.v2.AbstractBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.BlueprintParameters;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vnfm.RejectedLcmCoordination;
import com.ubiqube.etsi.mano.dao.mano.vnfm.VnfLcmCoordination;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
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
public class NsBlueprint extends AbstractBlueprint<NsTask, NsdInstance> {

	/** Serial. */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn
	private Set<NsTask> tasks;

	@ManyToOne(fetch = FetchType.LAZY)
	private NsdInstance nsInstance;

	private String nsInstantiationLevelId;

	private String nsFlavourId;
	// 3.5.1
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<VnfLcmCoordination> lcmCoordinations;
	// 3.5.1
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<RejectedLcmCoordination> rejectedLcmCoordinations;
	// 3.5.1
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> warnings;

	@ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	private Set<VimConnectionInformation> vimConnections;
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<BlueZoneGroupInformation> zoneGroups;
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<ZoneInfoEntity> zones;
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<ExtManagedVirtualLinkDataEntity> extManagedVirtualLinks;
	@Embedded
	private BlueprintParameters parameters;

	@Transient
	private transient Object operationParams;

	public void setNsInstance(final NsdInstance nsInstance) {
		this.nsInstance = nsInstance;
	}

	@Override
	public void addVimConnection(final VimConnectionInformation vimConnection) {
		if (null == this.vimConnections) {
			this.vimConnections = new HashSet<>();
		}
		this.vimConnections.add(vimConnection);
	}

	@Override
	public void addTask(final NsTask task) {
		if (null == tasks) {
			tasks = new LinkedHashSet<>();
		}
		tasks.add(task);
	}

	@Override
	public void setGrantsRequestId(final String string) {
		//
	}

	@Override
	public NsdInstance getInstance() {
		return nsInstance;
	}

	@Override
	public void addExtManagedVirtualLinks(final Set<ExtManagedVirtualLinkDataEntity> extManagedVirtualLinksIn) {
		if (null == extManagedVirtualLinksIn) {
			this.extManagedVirtualLinks = new LinkedHashSet<>();
		}
		this.extManagedVirtualLinks.addAll(extManagedVirtualLinksIn);
	}

	@Override
	public void addExtVirtualLinks(final Set<ExtVirtualLinkDataEntity> extVirtualLinks) {
		// Nothing.
	}
}
