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
package org.ardenus.engine.input.controller;

import org.ardenus.engine.input.controller.action.ControllerAction;

/**
 * Represents a direction that is being pressed on a controller with either with
 * its left analog stick or its directional pad.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.1-SNAPSHOT
 */
public enum GamepadDirection {

	/**
	 * The up direction.
	 */
	UP(0, "Up", new ControllerAction("up")),

	/**
	 * The down direction.
	 */
	DOWN(1, "Down", new ControllerAction("down")),

	/**
	 * The left direction.
	 */
	LEFT(2, "Left", new ControllerAction("left")),

	/**
	 * The right direction.
	 */
	RIGHT(3, "Right", new ControllerAction("right"));

	private final int id;
	private final String name;
	private final ControllerAction action;

	/**
	 * Constructs a <code>GamepadDirection</code>.
	 * 
	 * @param id
	 *            the ID of the direction.
	 * @param name
	 *            the name of the direction.
	 * @param action
	 *            the action of the direction.
	 * @throws NullPointerException
	 *             if the <code>name</code> or <code>action</code> are
	 *             <code>null</code>.
	 */
	private GamepadDirection(int id, String name, ControllerAction action) throws NullPointerException {
		if (name == null) {
			throw new NullPointerException("Name cannot be null");
		} else if (action == null) {
			throw new NullPointerException("Action cannot be null");
		}
		this.id = id;
		this.name = name;
		this.action = action;
	}

	/**
	 * Returns the ID of the direction.
	 * 
	 * @return the ID of the direction.
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Returns the name of the direction.
	 * 
	 * @return the name of the direction.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returns the direction as an action.
	 * 
	 * @return the direction as an action.
	 */
	public ControllerAction getAction() {
		return this.action;
	}

}
