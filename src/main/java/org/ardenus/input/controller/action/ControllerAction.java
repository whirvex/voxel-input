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
package org.ardenus.input.controller.action;

/**
 * An action that can be done in the game by a controller.
 * <p>
 * These are bound to controllers to allow for the game to listen for a specific
 * action, rather than for a specific button. This makes implementing the
 * support of multiple controllers much easier.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.1-SNAPSHOT
 */
public class ControllerAction {

	private final String id;
	private final String name;

	/**
	 * Creates a controller action.
	 * 
	 * @param id
	 *            the ID of the action.
	 * @param name
	 *            the name of the action.
	 * @throws NullPointerException
	 *             if the <code>id</code> is <code>null</code>.
	 */
	public ControllerAction(String id, String name) throws NullPointerException {
		if (id == null) {
			throw new NullPointerException("ID cannot be null");
		}
		this.id = id;
		this.name = name != null ? name : id;
	}

	/**
	 * Creates a controller action.
	 * 
	 * @param id
	 *            the ID of the action.
	 * @throws NullPointerException
	 *             if the <code>id</code> is <code>null</code>.
	 */
	public ControllerAction(String id) throws NullPointerException {
		this(id, null);
	}

	/**
	 * Returns the ID of the action.
	 * 
	 * @return the ID of the action.
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Returns the name of the action.
	 * 
	 * @return the name of the action.
	 */
	public String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		return "ControllerAction [id=" + id + ", name=" + name + "]";
	}

}
