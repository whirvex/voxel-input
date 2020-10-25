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

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.ardenus.input.device.controller.Controller;
import org.ardenus.input.device.controller.ControllerButton;

/**
 * A wireless controller handled and managed via the GLFW API.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.2-SNAPSHOT
 * @version 0.0.2
 */
public abstract class GLFWController extends Controller {

	private final int joystickId;
	private final String guid;
	private final String name;
	private boolean dcButtons;

	/**
	 * Constructs a new <code>GLFWController</code>.
	 *
	 * @param joystickId
	 *            the joystick ID.
	 * @param guid
	 *            the globally unique ID.
	 * @param name
	 *            the name of the controller.
	 */
	public GLFWController(int joystickId, String guid, String name) {
		this.joystickId = joystickId;
		this.guid = guid;
		this.name = name;
		this.dcButtons = true;
	}

	/**
	 * Returns the GLFW joystick ID.
	 * 
	 * @return the GLFW joystick ID.
	 */
	public final int getJoystickID() {
		return this.joystickId;
	}

	/**
	 * Returns the globally unique ID.
	 * 
	 * @return the globally unique ID.
	 */
	public final String getGUID() {
		return this.guid;
	}

	/**
	 * Returns the name of the controller.
	 * 
	 * @return the name of the controller.
	 */
	public final String getName() {
		return this.name;
	}

	/**
	 * Returns if this controller will disconnect if a set of disconnect buttons
	 * are simultaneously pressed.
	 * 
	 * @return <code>true</code> if this controller will disconnect if a set of
	 *         disconnect buttons are simultaneously pressed, <code>false</code>
	 *         otherwise.
	 */
	public final boolean usingDisconnectButtons() {
		return this.dcButtons;
	}

	/**
	 * Sets whether or not this controller will disconnect if a set of
	 * disconnect buttons are simultaneously pressed.
	 * 
	 * @param dcButtons
	 *            <code>true</code> if this controller should disconnect if a
	 *            set of disconnect buttons are simultaneously pressed,
	 *            <code>false</code> otherwise.
	 */
	public final void useDisconnectButtons(boolean dcButtons) {
		this.dcButtons = dcButtons;
	}

	/**
	 * Returns the combinations of buttons that, when pressed simultaneously,
	 * will result in the controller being disconnected.
	 * <p>
	 * <b>Note:</b> When determining whether or not a controller should be
	 * connected based on activity, these buttons <i>must not</i> be considered.
	 * Otherwise, this will result in a feedback loop of the controller being
	 * disconnected, and then immediately reconnected.
	 * 
	 * @return the combinations of buttons that, when pressed simultaneously,
	 *         will result in the controller being disconnected.
	 */
	public abstract ControllerButton[][] getDisconnectButtons();

	/**
	 * Updates the controller via data polled from GLFW.
	 * 
	 * @param axes
	 *            the axes.
	 * @param buttons
	 *            the buttons.
	 * @param hats
	 *            the hats.
	 * @throws NullPointerException
	 *             if <code>axes</code>, <code>buttons</code>, or
	 *             <code>hats</code> are <code>null</code>.
	 */
	public abstract void glfwUpdate(FloatBuffer axes, ByteBuffer buttons,
			ByteBuffer hats);

}
