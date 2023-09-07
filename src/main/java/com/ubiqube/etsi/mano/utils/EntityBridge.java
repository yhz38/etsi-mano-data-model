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
package com.ubiqube.etsi.mano.utils;

import java.util.Optional;
import java.util.UUID;

import org.hibernate.search.mapper.pojo.bridge.ValueBridge;
import org.hibernate.search.mapper.pojo.bridge.runtime.ValueBridgeToIndexedValueContext;

import com.ubiqube.etsi.mano.dao.mano.BaseEntity;

/**
 * Simple extractor for {@link BaseEntity} object type.
 */
public class EntityBridge implements ValueBridge<BaseEntity, String> {

	@Override
	public String toIndexedValue(final BaseEntity value, final ValueBridgeToIndexedValueContext context) {
		return Optional.ofNullable(value)
				.map(x -> x.getId())
				.map(UUID::toString)
				.orElse(null);
	}

}
