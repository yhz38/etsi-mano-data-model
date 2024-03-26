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

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.ubiqube.etsi.mano.dao.audit.Audit;
import com.ubiqube.etsi.mano.dao.audit.AuditListener;
import com.ubiqube.etsi.mano.dao.audit.Auditable;
import com.ubiqube.etsi.mano.dao.mano.common.ListKeyPair;
import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.pkg.OsContainer;
import com.ubiqube.etsi.mano.dao.mano.pkg.OsContainerDeployableUnit;
import com.ubiqube.etsi.mano.dao.mano.pkg.PackageSecurityOptionType;
import com.ubiqube.etsi.mano.dao.mano.pkg.UploadUriParameters;
import com.ubiqube.etsi.mano.dao.mano.pkg.VirtualCp;
import com.ubiqube.etsi.mano.dao.mano.pkg.VnfProfile;
import com.ubiqube.etsi.mano.dao.mano.repo.Repository;
import com.ubiqube.etsi.mano.dao.mano.vim.AffinityRule;
import com.ubiqube.etsi.mano.dao.mano.vim.SecurityGroup;
import com.ubiqube.etsi.mano.dao.mano.vim.SoftwareImage;
import com.ubiqube.etsi.mano.dao.mano.vim.VnfStorage;
import com.ubiqube.etsi.mano.dao.mano.vim.vnfi.VimCapability;
import com.ubiqube.etsi.mano.dao.mano.vnfm.McIops;
import com.ubiqube.etsi.mano.dao.rfc7807.FailureDetails;
import com.ubiqube.etsi.mano.utils.ToStringIgnore;
import com.ubiqube.etsi.mano.utils.ToStringUtil;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Transient;
import jakarta.persistence.Version;
import lombok.Data;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Data
@Entity
@EntityListeners(AuditListener.class)
public class VnfPackage implements PackageBase, Auditable {
	/** Serial. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	private String defaultInstantiationLevel;

	private String vnfdId;

	/**
	 * Identifies a VNFD in a version independent manner. This attribute is
	 * invariant across versions of the VNFD that fulfil certain conditions related
	 * to the external connectivity and management of the VNF. It shall be present
	 * after the VNF package content has been on-boarded if it is included in the
	 * VNFD and shall be absent otherwise. If present it is copied from the VNFD of
	 * the on-boarded VNF package.
	 *
	 * @since 4.3.1
	 */

	private String vnfdExtInvariantId;

	private String vnfProvider;

	private String vnfProductName;

	private String vnfSoftwareVersion;

	private String vnfdVersion;

	private String flavorId;

	private String flavourDescription;

	/**
	 * Store the over written descriptor ID.
	 */
	private String overwriteDescId;

	private String descriptorVersion;

	private String productInfoDescription;

	private String defaultLocalizationLanguage;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> localizationLanguages = new LinkedHashSet<>();

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@OrderColumn
	private Set<ListKeyPair> virtualLinks = new LinkedHashSet<>();

	@Embedded

	private PkgChecksum checksum;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT)

	private Set<AdditionalArtifact> additionalArtifacts = new LinkedHashSet<>();

	@Enumerated(EnumType.STRING)

	private OnboardingStateType onboardingState;

	@Enumerated(EnumType.STRING)

	private PackageOperationalState operationalState;

	@Enumerated(EnumType.STRING)

	private UsageStateEnum usageState;

	@ElementCollection(fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	private Map<String, String> userDefinedData;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<VnfCompute> vnfCompute = new LinkedHashSet<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<VnfIndicator> vnfIndicator = new LinkedHashSet<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<VnfVl> vnfVl = new LinkedHashSet<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn
	private Set<VnfStorage> vnfStorage = new LinkedHashSet<>();

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<VnfLinkPort> vnfLinkPort = new LinkedHashSet<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn
	private Set<VnfExtCp> vnfExtCp = new LinkedHashSet<>();

	@ToStringIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<NsdInstance> nsInstance;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn

	private Set<OsContainerDesc> osContainerDesc;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<UUID> mciopId;

	@Embedded
	private Audit audit;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)

	private Set<VnfInstantiationLevels> vnfInstantiationLevels;

	@ToStringIgnore
	@ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY, mappedBy = "vnfPackage")
	private Set<NsdPackageVnfPackage> nsdPackages;

	// 2.7.1
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> vnfmInfo;
	// 2.8.1
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> vnfmInfo281;
	// 2.7.1
	private PackageSecurityOptionType packageSecurityOption = null;
	// 2.7.1
	private String signingCertificate = null;
	// 2.7.1
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> compatibleSpecificationVersions;
	// 2.7.1
	private FailureDetails onboardingFailureDetails = null;
	// 2.7.1
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> nonManoArtifactSetId;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<SoftwareImage> softwareImages;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<MonitoringParams> monitoringParameters;
	// Original vnf package id in NFVO.
	private String nfvoId;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<SecurityGroup> securityGroups;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<AffinityRule> affinityRules;

	@Embedded
	private VnfProfile vnfProfile;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<ScaleInfo> scaleStatus;

	/**
	 * A collection of mandatory Vim capabilities.
	 */
	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	private Set<VimCapability> vimCapabilities;

	/**
	 * Content type of the generated vnfd file. Either text/plain or
	 * application/zip.
	 */
	private String vnfdContentType;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private UploadUriParameters uploadUriParameters;

	/**
	 * The Vdu.OsContainer node type represents the OsContainerDesc information
	 * element as defined in ETSI GS NFV-IFA 011 [1]. Table 6.8.12.1-1 specifies the
	 * declared names for this node type. These names shall be used as specified in
	 * TOSCA-Simple-Profile-YAML-v1.3 [20].
	 *
	 * @since 4.2.1
	 */
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<OsContainer> osContainer;

	/**
	 * The Vdu.OsContainerDeployableUnit node type describes the aggregate of OS
	 * containers of a VDU (when realized as OS containers) which is a construct
	 * supporting the description of the deployment and operational behaviour of a
	 * VNFC.
	 *
	 * @since 4.2.1
	 */
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<OsContainerDeployableUnit> osContainerDeployableUnits;

	/**
	 * A VirtualCp node type represents the VirtualCpd information element as
	 * defined in ETSI GS NFV-IFA 011 [1], which describes a requirement to create a
	 * virtual connection point allowing the access to a number of VNFC instances
	 * (based on their respective VDUs).
	 *
	 * @since 4.2.1
	 */
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<VirtualCp> virtualCp;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<McIops> mciops;

	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	private Servers server;

	/**
	 * Name of internal package provider/parser class.
	 */
	private String packageProvider;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Attributes> attributes;

	@OneToMany(cascade = CascadeType.ALL)
	private Set<Repository> repositories;

	@Transient
	private List<AttributeAssignements> overloadedAttribute;

	@Version
	private long version;

	public void addInstantiationLevel(final VnfInstantiationLevels il) {
		if (null == vnfInstantiationLevels) {
			vnfInstantiationLevels = new HashSet<>();
		}
		il.setVnfPackage(this);
		vnfInstantiationLevels.add(il);
	}

	public void addNsdPackage(final NsdPackageVnfPackage nsdPackage) {
		if (null == nsdPackages) {
			nsdPackages = new HashSet<>();
		}
		nsdPackages.add(nsdPackage);
	}

	public void addVirtualLink(final String name) {
		if (null == virtualLinks) {
			virtualLinks = new HashSet<>();
		}
		virtualLinks.add(new ListKeyPair(name, virtualLinks.size()));
	}

	@Override
	public String toString() {
		return ToStringUtil.toString(this);
	}
}
