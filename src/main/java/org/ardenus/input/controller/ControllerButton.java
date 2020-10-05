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
package org.ardenus.input.controller;

/**
 * A button on a controller.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.1-SNAPSHOT
 */
public class ControllerButton {

	private final int id;
	private final String name;
	private final Class<? extends Controller> controllerClazz;

	/**
	 * Creates a controller button.
	 * 
	 * @param id
	 *            the ID of the button.
	 * @param name
	 *            the name of the button.
	 * @param controllerClazz
	 *            the class of the controller the button belongs to.
	 */
	public ControllerButton(int id, String name, Class<? extends Controller> controllerClazz) {
		this.id = id;
		this.name = name;
		this.controllerClazz = controllerClazz;
	}

	/**
	 * Returns the ID of the button.
	 * 
	 * @return the ID of the button.
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Returns the name of the button.
	 * 
	 * @return the name of the button.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returns the controller class that the button belongs tto.
	 * 
	 * @return the controller class that the button belongs to.
	 */
	public Class<? extends Controller> getControllerClass() {
		return this.controllerClazz;
	}

	@Override
	public final String toString() {
		return "ControllerButton [id=" + id + ", name=" + name + ", controllerClazz=" + controllerClazz + "]";
	}

}
