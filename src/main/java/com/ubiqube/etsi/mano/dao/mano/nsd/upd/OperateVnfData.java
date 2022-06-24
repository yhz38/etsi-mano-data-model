/**
 *     Copyright (C) 2019-2020 Ubiqube.
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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.dao.mano.nsd.upd;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.etsi.mano.dao.mano.OperationalStateType;

import lombok.Data;

/**
 *
 * @author olivier
 *
 */
@Data
public class OperateVnfData {
	@JsonProperty("vnfInstanceId")
	private String vnfInstanceId = null;

	@JsonProperty("changeStateTo")
	private OperationalStateType changeStateTo = null;

	@JsonProperty("stopType")
	private StopType stopType = null;

	@JsonProperty("gracefulStopTimeout")
	private Integer gracefulStopTimeout = null;

	@JsonProperty("additionalParam")
	private Map<String, String> additionalParam = null;

}
