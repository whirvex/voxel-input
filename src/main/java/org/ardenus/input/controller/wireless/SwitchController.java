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
import org.ardenus.engine.input.controller.axis.SwitchAxis;
import org.ardenus.engine.input.handler.WirelessInputHandler;

/**
 * A Nintendo Switch pro-controller.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.1-SNAPSHOT
 */
@ButtonSetActions(press = "BUTTON_A", back = "BUTTON_B", scrollLeft = "BUTTON_L", scrollRight = "BUTTON_R")
public class SwitchController extends WirelessController {

	/**
	 * The B button.
	 */
	public static final ControllerButton BUTTON_B = new ControllerButton(0, "B", SwitchController.class);

	/**
	 * The A button.
	 */
	public static final ControllerButton BUTTON_A = new ControllerButton(1, "A", SwitchController.class);

	/**
	 * The Y button.
	 */
	public static final ControllerButton BUTTON_Y = new ControllerButton(2, "Y", SwitchController.class);

	/**
	 * The X button.
	 */
	public static final ControllerButton BUTTON_X = new ControllerButton(3, "X", SwitchController.class);

	/**
	 * The L button.
	 */
	public static final ControllerButton BUTTON_L = new ControllerButton(4, "L", SwitchController.class);

	/**
	 * The R button.
	 */
	public static final ControllerButton BUTTON_R = new ControllerButton(5, "R", SwitchController.class);

	/**
	 * The ZL button.
	 */
	public static final ControllerButton BUTTON_ZL = new ControllerButton(6, "ZL", SwitchController.class);

	/**
	 * The ZR button.
	 */
	public static final ControllerButton BUTTON_ZR = new ControllerButton(7, "ZR", SwitchController.class);

	/**
	 * The minus button.
	 */
	public static final ControllerButton BUTTON_MINUS = new ControllerButton(8, "-", SwitchController.class);

	/**
	 * The plus button.
	 */
	public static final ControllerButton BUTTON_PLUS = new ControllerButton(9, "+", SwitchController.class);

	/**
	 * The LS button.
	 */
	public static final ControllerButton BUTTON_LS = new ControllerButton(10, "LS", SwitchController.class);

	/**
	 * The RS button.
	 */
	public static final ControllerButton BUTTON_RS = new ControllerButton(11, "RS", SwitchController.class);

	/**
	 * The home button.
	 */
	public static final ControllerButton BUTTON_HOME = new ControllerButton(12, "Home", SwitchController.class);

	/**
	 * The screenshot button.
	 */
	public static final ControllerButton BUTTON_SCREENSHOT = new ControllerButton(13, "Screenshot", SwitchController.class);

	/**
	 * The bummper button.
	 */
	public static final ControllerButton BUTTON_BUMPER = new ControllerButton(14, "Bumper", SwitchController.class);

	/**
	 * The Z bumper button.
	 */
	public static final ControllerButton BUTTON_BUMPER_Z = new ControllerButton(15, "Z Bumper", SwitchController.class);

	/**
	 * The up button.
	 */
	public static final ControllerButton BUTTON_UP = new ControllerButton(16, "Up", SwitchController.class);

	/**
	 * The right button.
	 */
	public static final ControllerButton BUTTON_RIGHT = new ControllerButton(17, "Right", SwitchController.class);

	/**
	 * The down button.
	 */
	public static final ControllerButton BUTTON_DOWN = new ControllerButton(18, "Down", SwitchController.class);

	/**
	 * The left button.
	 */
	public static final ControllerButton BUTTON_LEFT = new ControllerButton(19, "Left", SwitchController.class);

	/**
	 * The left analog stick X axis.
	 */
	public static final SwitchAxis LEFT_ANALOG_STICK_X_AXIS = new SwitchAxis(-0.76F, 0.70F);

	/**
	 * The left analog stick Y axis.
	 */
	public static final SwitchAxis LEFT_ANALOG_STICK_Y_AXIS = new SwitchAxis(-0.76F, 0.72F);

	/**
	 * The right analog stick X axis.
	 */
	public static final SwitchAxis RIGHT_ANALOG_STICK_X_AXIS = new SwitchAxis(-0.78F, 0.70F);

	/**
	 * The right analog stick Y axis.
	 */
	public static final SwitchAxis RIGHT_ANALOG_STICK_Y_AXIS = new SwitchAxis(-0.74F, 0.76F);

	private float leftAnalogXAxis;
	private float leftAnalogYAxis;
	private float rightAnalogXAxis;
	private float rightAnalogYAxis;
	private float leftAnalogTriggerZAxis;
	private float rightAnalogTriggerZAxis;

	/**
	 * Creates a Nintendo Switch controller.
	 * 
	 * @param handler
	 *            the handler.
	 * @param jid
	 *            the joystick ID.
	 * @param guid
	 *            the globally unique ID.
	 * @param name
	 *            the name of the controller.
	 * @throws NullPointerException
	 *             if the <code>handler</code> is <code>null</code>.
	 */
	public SwitchController(WirelessInputHandler handler, int jid, String guid, String name) throws NullPointerException {
		super(handler, jid, guid, name);
		this.setButtons(BUTTON_B, BUTTON_A, BUTTON_Y, BUTTON_X, BUTTON_L, BUTTON_R, BUTTON_ZL, BUTTON_ZR, BUTTON_MINUS, BUTTON_PLUS, BUTTON_LS, BUTTON_RS, BUTTON_HOME,
				BUTTON_SCREENSHOT, BUTTON_BUMPER, BUTTON_BUMPER_Z, BUTTON_UP, BUTTON_RIGHT, BUTTON_DOWN, BUTTON_LEFT);
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
			this.leftAnalogXAxis = LEFT_ANALOG_STICK_X_AXIS.getAxis(axes.get());
			this.leftAnalogYAxis = LEFT_ANALOG_STICK_Y_AXIS.getAxis(axes.get()) * -1.0F;
			this.rightAnalogXAxis = RIGHT_ANALOG_STICK_X_AXIS.getAxis(axes.get());
			this.rightAnalogYAxis = RIGHT_ANALOG_STICK_Y_AXIS.getAxis(axes.get()) * -1.0F;
			this.leftAnalogTriggerZAxis = buttonStatuses[BUTTON_ZL.getId()] ? 1.0F : -1.0F;
			this.rightAnalogTriggerZAxis = buttonStatuses[BUTTON_ZR.getId()] ? 1.0F : -1.0F;

			// Update buttons
			for (int i = 0; i < buttonStatuses.length; i++) {
				this.updateButtonStatus(this.getButton(i), buttonStatuses[i]);
			}
		}
	}

	@Override
	public ControllerButton[][] getDisconnectButtons() {
		return new ControllerButton[][] { { BUTTON_MINUS, BUTTON_PLUS }, { BUTTON_BUMPER, BUTTON_BUMPER_Z } };
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
		return ControllerType.SWITCH;
	}

}
