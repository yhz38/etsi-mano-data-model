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
import com.ubiqube.etsi.mano.dao.mano.pkg.VirtualCpu;
import com.ubiqube.etsi.mano.dao.mano.pkg.VirtualMemory;
import com.ubiqube.etsi.mano.dao.mano.vim.ImageServiceAware;
import com.ubiqube.etsi.mano.dao.mano.vim.PlacementGroup;
import com.ubiqube.etsi.mano.dao.mano.vim.SoftwareImage;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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
public class VnfCompute implements ImageServiceAware, ToscaEntity, Auditable {
	/** Serial. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	private String toscaId;

	private String toscaName;

	private String state;

	private String name;

	private String description;

	@Column(length = 9000)
	private String cloudInit;
	private String sourcePath;
	private String destinationPath;

	@NotNull
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private VirtualCpu virtualCpu = new VirtualCpu();

	@NotNull
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private VirtualMemory virtualMemory = new VirtualMemory();

	private long diskSize;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private SoftwareImage softwareImage;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> storages;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> networks;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<VnfLinkPort> ports;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<MonitoringParams> monitoringParameters;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "vnfCompute")
	private Set<VduInstantiationLevel> instantiationLevel;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<PlacementGroup> placementGroup;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> affinityRule;
	/**
	 * Initial delta.
	 */
	private Integer initialNumberOfInstance;

	@Embedded
	private Audit audit;

	@Embedded
	private VduProfile vduProfile = new VduProfile();

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> securityGroup;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "vnfCompute")
	private Set<VnfComputeAspectDelta> scalingAspectDeltas;

	public void addScalingAspectDeltas(final VnfComputeAspectDelta scalingDelta) {
		if (null == scalingAspectDeltas) {
			scalingAspectDeltas = new LinkedHashSet<>();
		}
		scalingDelta.setVnfCompute(this);
		scalingAspectDeltas.add(scalingDelta);
	}

	public void addVduInstantiationLevel(final VduInstantiationLevel vduInstantiationLevel) {
		if (null == instantiationLevel) {
			instantiationLevel = new LinkedHashSet<>();
		}
		instantiationLevel.add(vduInstantiationLevel);
	}

	public void addSecurityGroups(final String securityGroupName) {
		if (null == securityGroup) {
			securityGroup = new LinkedHashSet<>();
		}
		securityGroup.add(securityGroupName);
	}

	public void addAffinity(final String toscaName2) {
		if (null == affinityRule) {
			affinityRule = new LinkedHashSet<>();
		}
		affinityRule.add(toscaName2);
	}

}
