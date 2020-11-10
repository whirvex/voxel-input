/*
 * ___  ____ ___  ____ __   _    ___    ____ __   ____ _    ____
 * |  \ | . \|  \ | __\| \|\|| \ | _\   |___\| \|\| . \|| \ |_ _\
 * | . \|  <_| . \|  ]_|  \|||_|\[__ \  | /  |  \|| __/||_|\  ||
 * |/\_/|/\_/|___/|___/|/\_/|___/|___/  |/   |/\_/|/   |___/  |/
 *
 * An input system created primarily for video games.
 * Copyright (C) 2017-2020 Ardenus Studios
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301
 * USA
 */

package org.ardenus.input.action;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

/**
 * Indicates that a class can attach an {@link ActionProfile} for sets of
 * actions.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.2-SNAPSHOT
 * @version 0.0.2
 */
public interface ActionProfiling {
	
	public boolean areAttached(Collection<ActionProfile> profiles);
	
	public default boolean isAttached(ActionProfile profile) {
		return this.areAttached(Arrays.asList(profile));
	}
	
	public default boolean areAttached(ActionProfile... profiles) {
		if(profiles != null) {
			return this.areAttached(Arrays.asList(profiles));
		}
		return false;
	}
	
	public void attach(ActionProfile profile);
	
	public default void attach(Collection<ActionProfile> profiles) {
		Objects.requireNonNull(profiles, "profiles cannot be null");
		for(ActionProfile profile : profiles) {
			this.attach(profile);
		}
	}
	
	public default void attach(ActionProfile... profiles) {
		this.attach(Arrays.asList(profiles));
	}
	
	public void detach(ActionProfile profile);
	
	public default void detach(Collection<ActionProfile> profiles) {
		Objects.requireNonNull(profiles, "profiles cannot be null");
		for(ActionProfile profile : profiles) {
			this.detach(profile);
		}
	}
	
	public default void detach(ActionProfile... profiles) {
		Objects.requireNonNull(profiles, "profiles cannot be null");
		this.detach(Arrays.asList(profiles));
	}
	
}
