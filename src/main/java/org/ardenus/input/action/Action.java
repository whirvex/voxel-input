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

/**
 * An action which can be performed by an input device.
 * <p>
 * Actions work by being bound to input devices and their specific buttons. This
 * allows for input listeners to listens for actions, rather than individual
 * buttons based on the type of input device.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.1-SNAPSHOT
 */
public class Action {

	/**
	 * The action ID.
	 */
	public final String id;

	/**
	 * The name of the action. If a <code>null</code> value was specified during
	 * construction, this field will be equal to <code>id</code>.
	 */
	public final String name;

	/**
	 * Constructs a new <code>Action</code>.
	 * 
	 * @param id
	 *            the action ID.
	 * @param name
	 *            the name of the action, <code>null</code> value is permitted.
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

}
