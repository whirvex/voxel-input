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
 * Represents an object which can be pressed, either virtually or in real life.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.1-SNAPSHOT
 * @version 1.0.0
 */
public interface Pressable {

	/**
	 * Contains information about a press event.
	 */
	public static class Press {
		// TODO: This will contain some info later, but it's not important now
	}

	/**
	 * Contains information about a hold event.
	 */
	public static class Hold {
		// TODO: This will contain some info later, but it's not important now
	}

	/**
	 * Contains information about a release event.
	 */
	public static class Release {
		// TODO: This will contain some info later, but it's not important now
	}

	/**
	 * Called when this object is pressed down.
	 * 
	 * @param press
	 *            press info, guaranteed to not be <code>null</code>.
	 */
	public default void onPress(Press press) {
	}

	/**
	 * Called when this object has been pressed down after a certain amount of
	 * time, and is therefore considered to be held down.
	 * 
	 * @param hold
	 *            hold info, guaranteed to not be <code>null</code>.
	 */
	public default void onHold(Hold hold) {
	}

	/**
	 * Called when this object has been released after having been pressed down.
	 * 
	 * @param release
	 *            release info, guaranteed to not be <code>null</code>.
	 */
	public default void onRelease(Release release) {
	}

}
