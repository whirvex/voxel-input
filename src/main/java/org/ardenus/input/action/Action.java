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

import java.util.Objects;
import java.util.UUID;

/**
 * An action which can be performed, usually by input devices.
 * <p>
 * Actions work by being bound to input devices and their specific peripherals.
 * This allows for input listeners to listens for actions, rather than
 * individual buttons based on the type of input device.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.2-SNAPSHOT
 * @version 0.0.2
 */
public class Action {

	/**
	 * The action ID.
	 */
	public final String id;

	/**
	 * The name of the action. If a <code>null</code> value was specified during
	 * construction, this field will be equal to <code>id</code>.
	 * <p>
	 * <b>Note:</b> This field is not taken into account for hashing or
	 * determining the equality of two <code>Action</code> instances. It is only
	 * used for {@link #toString()}.
	 */
	public final String name;

	/**
	 * Constructs a new <code>Action</code>.
	 * 
	 * @param id
	 *            the action ID.
	 * @param name
	 *            the name of the action, a <code>null</code> value is
	 *            permitted.
	 * @throws NullPointerException
	 *             if <code>id</code> is <code>null</code>.
	 */
	public Action(String id, String name) {
		this.id = Objects.requireNonNull(id, "id cannot be null");
		this.name = name != null ? name : id;
	}

	/**
	 * Constructs a new <code>Action</code>.
	 * 
	 * @param id
	 *            the action ID.
	 * @throws NullPointerException
	 *             if <code>id</code> is <code>null</code>.
	 */
	public Action(String id) {
		this(id, null);
	}

	/**
	 * Constructs a new <code>Action</code> with a randomly generated ID via
	 * {@link UUID#randomUUID()}.
	 */
	public Action() {
		this(UUID.randomUUID().toString());
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Action) {
			Action action = (Action) obj;
			return Objects.equals(this.id, action.id);
		}
		return false;
	}

	/**
	 * Returns the name of the action.
	 * 
	 * @return the name of the action.
	 */
	@Override
	public String toString() {
		return this.name;
	}

}
