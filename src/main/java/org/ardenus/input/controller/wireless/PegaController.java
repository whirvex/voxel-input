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
 * An iPega controller.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.1-SNAPSHOT
 */
@ButtonSetActions(press = "BUTTON_A", back = "BUTTON_B", scrollLeft = "BUTTON_LB", scrollRight = "BUTTON_RB")
public class PegaController extends WirelessController {

	/**
	 * The A button.
	 */
	public static final ControllerButton BUTTON_A = new ControllerButton(0, "A", PegaController.class);

	/**
	 * The B button.
	 */
	public static final ControllerButton BUTTON_B = new ControllerButton(1, "B", PegaController.class);

	/**
	 * The first mystery button.
	 */
	public static final ControllerButton BUTTON_MYSTERY_1 = new ControllerButton(2, "Mystery 1", PegaController.class);

	/**
	 * The X button.
	 */
	public static final ControllerButton BUTTON_X = new ControllerButton(3, "X", PegaController.class);

	/**
	 * The Y button.
	 */
	public static final ControllerButton BUTTON_Y = new ControllerButton(4, "Y", PegaController.class);

	/**
	 * The second mystery button.
	 */
	public static final ControllerButton BUTTON_MYSTERY_2 = new ControllerButton(5, "Myster 2", PegaController.class);

	/**
	 * The LB button.
	 */
	public static final ControllerButton BUTTON_LB = new ControllerButton(6, "LB", PegaController.class);

	/**
	 * The RB button.
	 */
	public static final ControllerButton BUTTON_RB = new ControllerButton(7, "RB", PegaController.class);

	/**
	 * The LT button.
	 */
	public static final ControllerButton BUTTON_LT = new ControllerButton(8, "LT", PegaController.class);

	/**
	 * The RT button.
	 */
	public static final ControllerButton BUTTON_RT = new ControllerButton(9, "RT", PegaController.class);

	/**
	 * The select button.
	 */
	public static final ControllerButton BUTTON_SELECT = new ControllerButton(10, "Select", PegaController.class);

	/**
	 * The start button.
	 */
	public static final ControllerButton BUTTON_START = new ControllerButton(11, "Start", PegaController.class);

	/**
	 * The third mystery button.
	 */
	public static final ControllerButton BUTTON_MYSTERY_3 = new ControllerButton(12, "Mystery 3", PegaController.class);

	/**
	 * The LS button.
	 */
	public static final ControllerButton BUTTON_LS = new ControllerButton(13, "LS", PegaController.class);

	/**
	 * The RS button.
	 */
	public static final ControllerButton BUTTON_RS = new ControllerButton(14, "RS", PegaController.class);

	/**
	 * The up button.
	 */
	public static final ControllerButton BUTTON_UP = new ControllerButton(15, "Up", PegaController.class);

	/**
	 * The right button.
	 */
	public static final ControllerButton BUTTON_RIGHT = new ControllerButton(16, "Right", PegaController.class);

	/**
	 * The down button.
	 */
	public static final ControllerButton BUTTON_DOWN = new ControllerButton(17, "Down", PegaController.class);

	/**
	 * The left button.
	 */
	public static final ControllerButton BUTTON_LEFT = new ControllerButton(18, "Left", PegaController.class);

	private float leftAnalogXAxis;
	private float leftAnalogYAxis;
	private float rightAnalogXAxis;
	private float rightAnalogYAxis;
	private float leftAnalogTriggerZAxis;
	private float rightAnalogTriggerZAxis;

	/**
	 * Creates an iPega controller.
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
	public PegaController(WirelessInputHandler handler, int jid, String guid, String name) throws NullPointerException {
		super(handler, jid, guid, name);
		this.setButtons(BUTTON_A, BUTTON_B, BUTTON_MYSTERY_1, BUTTON_X, BUTTON_Y, BUTTON_MYSTERY_2, BUTTON_LB, BUTTON_RB, BUTTON_LT, BUTTON_RT, BUTTON_SELECT, BUTTON_START,
				BUTTON_SELECT, BUTTON_START, BUTTON_MYSTERY_3, BUTTON_LS, BUTTON_RS, BUTTON_UP, BUTTON_RIGHT, BUTTON_DOWN, BUTTON_LEFT);
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
			axes.get(); // Spooky mystery axis
			this.rightAnalogXAxis = axes.get();
			this.rightAnalogYAxis = axes.get() * -1.0F;
			this.leftAnalogTriggerZAxis = buttonStatuses[BUTTON_LT.getId()] ? 1.0F : -1.0F;
			this.rightAnalogTriggerZAxis = buttonStatuses[BUTTON_RT.getId()] ? 1.0F : -1.0F;

			// Update buttons
			for (int i = 0; i < buttonStatuses.length; i++) {
				this.updateButtonStatus(this.getButton(i), buttonStatuses[i]);
			}
		}
	}

	@Override
	public ControllerButton[][] getDisconnectButtons() {
		return new ControllerButton[][] { { BUTTON_SELECT, BUTTON_START } };
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
		return ControllerType.PEGA;
	}

}
