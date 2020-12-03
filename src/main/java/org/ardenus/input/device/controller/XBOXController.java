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
package org.ardenus.input.device.controller;

import org.ardenus.input.button.ButtonPresent;

/**
 * A Microsoft XBOX One/XBOX 360 controller.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.2-SNAPSHOT
 * @version 0.0.2
 */
public class XBOXController extends Controller {

	/**
	 * An XBOX controller button.
	 */
	@ButtonPresent
	public static final ControllerButton BUTTON_A =
			new ControllerButton("A", XBOXController.class),
			BUTTON_B = new ControllerButton("B", XBOXController.class),
			BUTTON_X = new ControllerButton("X", XBOXController.class),
			BUTTON_Y = new ControllerButton("Y", XBOXController.class),
			BUTTON_LB = new ControllerButton("LB", XBOXController.class),
			BUTTON_RB = new ControllerButton("RB", XBOXController.class),
			BUTTON_MENU = new ControllerButton("Menu", XBOXController.class),
			BUTTON_PAUSE = new ControllerButton("Pause", XBOXController.class),
			BUTTON_LS = new ControllerButton("LS", XBOXController.class),
			BUTTON_RS = new ControllerButton("RS", XBOXController.class),
			BUTTON_UP = new ControllerButton("Up", XBOXController.class),
			BUTTON_RIGHT = new ControllerButton("Right", XBOXController.class),
			BUTTON_DOWN = new ControllerButton("Down", XBOXController.class),
			BUTTON_LEFT = new ControllerButton("Left", XBOXController.class);

	public XBOXController() {
	}

	@Override
	public int getPort() {
		// TODO Auto-generated method stub
		return 0;
	}

}
