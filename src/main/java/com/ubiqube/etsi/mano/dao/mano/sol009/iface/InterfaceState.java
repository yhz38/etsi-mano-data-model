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

import com.ubiqube.etsi.mano.dao.mano.OperationalStateType;
import com.ubiqube.etsi.mano.dao.mano.UsageStateEnum;
import com.ubiqube.etsi.mano.dao.mano.sol009.peers.AdministrativeState;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class InterfaceState {
	/**
	 * The operational state of the NFV-MANO service interface.
	 */
	private OperationalStateType operationalState;

	/**
	 * The administrative state of the NFV-MANO service interface.
	 */
	private AdministrativeState administrativeState;

	/**
	 * The usage state of the NFV-MANO service interface.
	 */
	private UsageStateEnum usageState;
}
