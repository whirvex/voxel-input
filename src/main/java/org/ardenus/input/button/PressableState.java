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
 * A container class for the internal state of a {@link Pressable}. It should
 * not be exposed to members outside of the original class, as the fields
 * represent the state of the pressable object (which can be modified).
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.2-SNAPSHOT
 * @version 0.0.2
 */
public class PressableState {

	/**
	 * Whether or not the object is pressed.
	 */
	public boolean state;

	/**
	 * How long the object has been held down in milliseconds, if
	 * <code>state</code> is <code>true</code>.
	 */
	public long holdTime;

	/**
	 * How much time has elapsed in milliseconds since the last press caused by
	 * continuously holding the object down.
	 */
	public long holdPressTime;

	/**
	 * Constructs a new <code>PressableState</code>.
	 */
	public PressableState() {
		this.state = false;
		this.holdTime = 0L;
		this.holdPressTime = 0L;
	}

}
