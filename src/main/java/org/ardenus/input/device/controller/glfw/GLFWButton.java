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
package org.ardenus.input.device.controller.glfw;

import org.ardenus.input.device.controller.ControllerButton;

/**
 * Represents a button found on a controller from GLFW.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.2-SNAPSHOT
 * @version 0.0.2
 */
public class GLFWButton extends ControllerButton {

	/**
	 * The button ID.
	 */
	public final int id;

	/**
	 * Constructs a new <code>GLFWButton</code>.
	 * 
	 * @param id
	 *            the button ID.
	 * @param name
	 *            the button name.
	 * @throws NullPointerException
	 *             if <code>name</code> or <code>controllerClazz</code> are
	 *             <code>null</code>.
	 */
	public GLFWButton(int id, String name,
			Class<? extends GLFWController> controllerClazz) {
		super(name, controllerClazz);
		this.id = id;
	}

}
