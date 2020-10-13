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
package org.ardenus.input.button;

/**
 * Represents an object which can be selected, either virtually or in real life.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.1-SNAPSHOT
 * @version 0.0.2
 */
public interface Selectable {

	/**
	 * Contains information about a select event.
	 */
	public static class Select {
		// TODO: This will contain some info later, but it's not important now
	}

	/**
	 * Contains information about an unselect event.
	 */
	public static class Unselect {
		// TODO: This will contain some info later, but it's not important now
	}
	
	/**
	 * Returns if this object can currently be selected.
	 * 
	 * @return <code>true</code> if this object can currently be selected,
	 *         <code>false</code> otherwise.
	 */
	public default boolean isSelectable() {
		return true;
	}

	/**
	 * Sets whether or not this object can be selected.
	 * 
	 * @param selectable
	 *            <code>true</code> if this object can be selected,
	 *            <code>false</code> otherwise.
	 * @throws UnsupportedOperationException
	 *             always, unless implemented.
	 */
	public default void setSelectable(boolean selectable) {
		throw new UnsupportedOperationException("setSelectable");
	}

	/**
	 * Called when this object is selected.
	 * 
	 * @param select
	 *            select info, guaranteed to not be <code>null</code>.
	 */
	public default void onSelect(Select select) {
	}

	/**
	 * Called when this object is unselected.
	 * 
	 * @param unselect
	 *            unselect info, guaranteed to not be <code>null</code>.
	 */
	public default void onUnselect(Unselect unselect) {
	}

}
