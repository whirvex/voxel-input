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

import org.ardenus.input.device.controller.ButtonPresent;
import org.ardenus.input.device.controller.ControllerButton;

/**
 * A Microsoft XBOX One/XBOX 360 controller.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.2-SNAPSHOT
 * @version 0.0.2
 */
public class XBOXController extends GLFWController {

	/**
	 * An XBOX controller button.
	 */
	@ButtonPresent
	public static final GLFWButton BUTTON_A =
			new GLFWButton(0, "A", XBOXController.class),
			BUTTON_B = new GLFWButton(1, "B", XBOXController.class),
			BUTTON_X = new GLFWButton(2, "X", XBOXController.class),
			BUTTON_Y = new GLFWButton(3, "Y", XBOXController.class),
			BUTTON_LB = new GLFWButton(4, "LB", XBOXController.class),
			BUTTON_RB = new GLFWButton(5, "RB", XBOXController.class),
			BUTTON_MENU = new GLFWButton(6, "Menu", XBOXController.class),
			BUTTON_PAUSE = new GLFWButton(7, "Pause", XBOXController.class),
			BUTTON_LS = new GLFWButton(8, "LS", XBOXController.class),
			BUTTON_RS = new GLFWButton(9, "RS", XBOXController.class),
			BUTTON_UP = new GLFWButton(10, "Up", XBOXController.class),
			BUTTON_RIGHT = new GLFWButton(11, "Right", XBOXController.class),
			BUTTON_DOWN = new GLFWButton(12, "Down", XBOXController.class),
			BUTTON_LEFT = new GLFWButton(13, "Left", XBOXController.class);
	
	public XBOXController(int joystickId, String guid, String name) {
		super(joystickId, guid, name);
	}

	@Override
	public ControllerButton[][] getDisconnectButtons() {
		return new ControllerButton[][]{{BUTTON_MENU, BUTTON_PAUSE}};
	}

	@Override
	public void glfwUpdate(FloatBuffer axes, ByteBuffer buttons,
			ByteBuffer hats) {
		// TODO: Auto-generated method stub
	}

}
