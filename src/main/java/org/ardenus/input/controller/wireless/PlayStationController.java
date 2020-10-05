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
package org.ardenus.engine.input.controller.wireless;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.ardenus.engine.input.button.ButtonSetActions;
import org.ardenus.engine.input.controller.ControllerButton;
import org.ardenus.engine.input.controller.ControllerType;
import org.ardenus.engine.input.handler.WirelessInputHandler;

/**
 * A Sony PlayStation 4 controller.
 * 
 * @author Trent Summerlin.
 * @since Ardenus Input v0.0.1-SNAPSHOT
 */
@ButtonSetActions(press = "BUTTON_CROSS", back = "BUTTON_TRIANGLE", scrollLeft = "BUTTON_L1", scrollRight = "BUTTON_R1")
public class PlayStationController extends WirelessController {

	/**
	 * The square button.
	 */
	public static final ControllerButton BUTTON_SQUARE = new ControllerButton(0, "Square", PlayStationController.class);

	/**
	 * The cross button.
	 */
	public static final ControllerButton BUTTON_CROSS = new ControllerButton(1, "Cross", PlayStationController.class);

	/**
	 * The circle button.
	 */
	public static final ControllerButton BUTTON_CIRCLE = new ControllerButton(2, "Circle", PlayStationController.class);

	/**
	 * The triangle button.
	 */
	public static final ControllerButton BUTTON_TRIANGLE = new ControllerButton(3, "Triangle", PlayStationController.class);

	/**
	 * The L1 button.
	 */
	public static final ControllerButton BUTTON_L1 = new ControllerButton(4, "L1", PlayStationController.class);

	/**
	 * The R1 button.
	 */
	public static final ControllerButton BUTTON_R1 = new ControllerButton(5, "R1", PlayStationController.class);

	/**
	 * The L2 button.
	 */
	public static final ControllerButton BUTTON_L2 = new ControllerButton(6, "L2", PlayStationController.class);

	/**
	 * The R2 button.
	 */
	public static final ControllerButton BUTTON_R2 = new ControllerButton(7, "R2", PlayStationController.class);

	/**
	 * The share button.
	 */
	public static final ControllerButton BUTTON_SHARE = new ControllerButton(8, "Share", PlayStationController.class);

	/**
	 * The options button.
	 */
	public static final ControllerButton BUTTON_OPTIONS = new ControllerButton(9, "Options", PlayStationController.class);

	/**
	 * The LS button.
	 */
	public static final ControllerButton BUTTON_LS = new ControllerButton(10, "LS", PlayStationController.class);

	/**
	 * The RS button.
	 */
	public static final ControllerButton BUTTON_RS = new ControllerButton(11, "RS", PlayStationController.class);

	/**
	 * The PlayStation button.
	 */
	public static final ControllerButton BUTTON_PLAYSTATION = new ControllerButton(12, "PlayStation", PlayStationController.class);

	/**
	 * The trackpad button.
	 */
	public static final ControllerButton BUTTON_TRACKPAD = new ControllerButton(13, "Trackpad", PlayStationController.class);

	/**
	 * The up button.
	 */
	public static final ControllerButton BUTTON_UP = new ControllerButton(14, "Up", PlayStationController.class);

	/**
	 * The right button.
	 */
	public static final ControllerButton BUTTON_RIGHT = new ControllerButton(15, "Right", PlayStationController.class);

	/**
	 * The down button.
	 */
	public static final ControllerButton BUTTON_DOWN = new ControllerButton(16, "Down", PlayStationController.class);

	/**
	 * The left button.
	 */
	public static final ControllerButton BUTTON_LEFT = new ControllerButton(17, "Left", PlayStationController.class);

	private float leftAnalogXAxis;
	private float leftAnalogYAxis;
	private float rightAnalogXAxis;
	private float leftAnalogTriggerZAxis;
	private float rightAnalogTriggerZAxis;
	private float rightAnalogYAxis;

	/**
	 * Creates a Sony PlayStation controller.
	 * 
	 * @param handler
	 *            the input handler.
	 * @param jid
	 *            the joystick ID.
	 * @param guid
	 *            the globally unique ID.
	 * @param name
	 *            the name of the controller.
	 * @throws NullPointerException
	 *             if the input <code>handler</code> is <code>null</code>.
	 */
	public PlayStationController(WirelessInputHandler handler, int jid, String guid, String name) throws NullPointerException {
		super(handler, jid, guid, name);
		this.setButtons(BUTTON_SQUARE, BUTTON_CROSS, BUTTON_CIRCLE, BUTTON_TRIANGLE, BUTTON_L1, BUTTON_R1, BUTTON_L2, BUTTON_R2, BUTTON_SHARE, BUTTON_OPTIONS, BUTTON_LS, BUTTON_RS,
				BUTTON_PLAYSTATION, BUTTON_TRACKPAD, BUTTON_UP, BUTTON_RIGHT, BUTTON_DOWN, BUTTON_LEFT);
		this.setDirectionalButtons(BUTTON_UP, BUTTON_DOWN, BUTTON_LEFT, BUTTON_RIGHT);
	}

	@Override
	public void updateGLFWData(FloatBuffer axes, ByteBuffer buttons, ByteBuffer hats) {
		boolean[] buttonStatuses = new boolean[buttons.capacity()];
		for (int i = 0; i < buttonStatuses.length; i++) {
			buttonStatuses[i] = buttons.get() > 0;
		}
		if (!this.checkDisconnect(buttonStatuses)) {
			// Update axes
			this.leftAnalogXAxis = axes.get();
			this.leftAnalogYAxis = axes.get() * -1.0F;
			this.rightAnalogXAxis = axes.get();
			this.leftAnalogTriggerZAxis = axes.get();
			this.rightAnalogTriggerZAxis = axes.get();
			this.rightAnalogYAxis = axes.get() * -1.0F;

			// Update buttons
			for (int i = 0; i < buttonStatuses.length; i++) {
				this.updateButtonStatus(this.getButton(i), buttonStatuses[i]);
			}
		}
	}

	@Override
	public ControllerButton[][] getDisconnectButtons() {
		return new ControllerButton[][] { { BUTTON_SHARE, BUTTON_OPTIONS } };
	}

	@Override
	public float getLeftXAxis() {
		return this.leftAnalogXAxis;
	}

	@Override
	public float getLeftYAxis() {
		return this.leftAnalogYAxis;
	}

	@Override
	public float getRightXAxis() {
		return this.rightAnalogXAxis;
	}

	@Override
	public float getRightYAxis() {
		return this.rightAnalogYAxis;
	}

	@Override
	public float getLeftTriggerZAxis() {
		return this.leftAnalogTriggerZAxis;
	}

	@Override
	public float getRightTriggerZAxis() {
		return this.rightAnalogTriggerZAxis;
	}

	@Override
	public ControllerType getType() {
		return ControllerType.PLAYSTATION;
	}

}
