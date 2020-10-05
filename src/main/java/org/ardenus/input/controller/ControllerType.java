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

/**
 * Used to indicate what type of controller a controller is.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.1-SNAPSHOT
 */
public enum ControllerType {

	/**
	 * A keyboard and mouse.
	 */
	KEYBOARD("Keyboard & Mouse"),

	/**
	 * A Microsoft XBOX One or XBOX 360 controller.
	 */
	XBOX("Microsoft XBOX One/XBOX 360 controller"),

	/**
	 * A Sony PlayStation 4 controller.
	 */
	PLAYSTATION("Sony PlayStation 4 controller"),

	/**
	 * A Nintendo Switch pro-controller.
	 */
	SWITCH("Nintendo Switch pro-controller"),

	/**
	 * An iPega controller.
	 */
	PEGA("iPega controller"),

	/**
	 * A Nintendo GameCube controller.
	 */
	GAMECUBE("Nintendo GameCube controller");

	private final String name;

	/**
	 * Constructs a <code>ControllerType</code>.
	 * 
	 * @param name
	 *            the name of the controller.
	 * @throws NullPointerException
	 *             if the <code>name</code> is <code>null</code>.
	 */
	private ControllerType(String name) throws NullPointerException {
		if (name == null) {
			throw new NullPointerException("Name cannot be null");
		}
		this.name = name;
	}

	/**
	 * Returns the name of the controller.
	 * 
	 * @return the name of the controller.
	 */
	public String getName() {
		return this.name;
	}

}
