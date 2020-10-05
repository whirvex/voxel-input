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

import org.ardenus.engine.input.button.ButtonSetActions;
import org.ardenus.engine.input.controller.axis.GamecubeAxis;
import org.ardenus.engine.input.handler.InputHandler;

/**
 * A Nintendo GameCube controller connected to a Nintendo Wii U or Nintendo
 * Switch Super Smash Bros. adapter.
 *
 * @author Trent Summerlin.
 * @since Ardenus Input v0.0.1-SNAPSHOT
 */
@ButtonSetActions(press = "BUTTON_A", back = "BUTTON_B", scrollLeft = "BUTTON_L", scrollRight = "BUTTON_R")
public class GameCubeController extends Controller {

	/**
	 * Contains the USB adapter information that will be used when updating the
	 * controller via the
	 * {@link GameCubeController#updateAdapterData(AdapterPacket)
	 * updateAdapterData(AdapterPacket)} method.
	 * 
	 * @author Trent Summerlin.
	 * @since Ardenus Input v0.0.1-SNAPSHOT
	 */
	public static class AdapterPacket {

		private boolean buttons[];
		private float leftAnalogXAxis;
		private float leftAnalogYAxis;
		private float rightAnalogXAxis;
		private float rightAnalogYAxis;
		private float leftAnalogTriggerZAxis;
		private float rightAnalogTriggerZAxis;

		/**
		 * Creates an adapter packet.
		 * 
		 * @param buttonData0
		 *            the first set of button states.
		 * @param buttonData1
		 *            the second set of button states.
		 * @param leftAnalogStickX
		 *            the left analog stick X axis.
		 * @param leftAnalogStickY
		 *            the left analog stick Y axis.
		 * @param rightAnalogStickX
		 *            the right analog stick X axis.
		 * @param rightAnalogStickY
		 *            the right analog stick Y axis.
		 * @param leftTriggerAnalogZ
		 *            the left trigger analog Z axis.
		 * @param rightTriggerAnalogZ
		 *            the right trigger analog Z axis.
		 */
		public AdapterPacket(int buttonData0, int buttonData1, int leftAnalogStickX, int leftAnalogStickY, int rightAnalogStickX, int rightAnalogStickY, int leftTriggerAnalogZ,
				int rightTriggerAnalogZ) {
			this.buttons = new boolean[BUTTON_COUNT];
			for (int i = 0; i < buttons.length; i++) {
				this.buttons[i] = ((i < 8 ? buttonData0 : buttonData1) & (1 << (i < 8 ? i : i - 8))) > 0;
			}
			this.leftAnalogXAxis = LEFT_ANALOG_STICK_X_AXIS.getAxis(leftAnalogStickX);
			this.leftAnalogYAxis = LEFT_ANALOG_STICK_Y_AXIS.getAxis(leftAnalogStickY);
			this.rightAnalogXAxis = RIGHT_ANALOG_STICK_X_AXIS.getAxis(rightAnalogStickX);
			this.rightAnalogYAxis = RIGHT_ANALOG_STICK_Y_AXIS.getAxis(rightAnalogStickY);
			this.leftAnalogTriggerZAxis = TRIGGER_ANALOG_Z_AXIS.getAxis(leftTriggerAnalogZ);
			this.rightAnalogTriggerZAxis = TRIGGER_ANALOG_Z_AXIS.getAxis(rightTriggerAnalogZ);
		}

	}

	/**
	 * The controller is disconnected.
	 */
	public static final int STATE_DISCONNECTED = 0x00;

	/**
	 * The controller is connected.
	 */
	public static final int STATE_CONNECTED = 0x01;

	/**
	 * The controller is connected wirelessly.
	 */
	public static final int STATE_WIRELESS = 0x02;

	/**
	 * The amount of buttons a Nintendo GameCube controller has.
	 */
	private static final int BUTTON_COUNT = 12;

	/**
	 * The A button.
	 */
	public static final ControllerButton BUTTON_A = new ControllerButton(0, "A", GameCubeController.class);

	/**
	 * The B button.
	 */
	public static final ControllerButton BUTTON_B = new ControllerButton(1, "B", GameCubeController.class);

	/**
	 * The X button.
	 */
	public static final ControllerButton BUTTON_X = new ControllerButton(2, "X", GameCubeController.class);

	/**
	 * The Y button.
	 */
	public static final ControllerButton BUTTON_Y = new ControllerButton(3, "Y", GameCubeController.class);

	/**
	 * The left button.
	 */
	public static final ControllerButton BUTTON_LEFT = new ControllerButton(4, "Left", GameCubeController.class);

	/**
	 * The right button.
	 */
	public static final ControllerButton BUTTON_RIGHT = new ControllerButton(5, "Right", GameCubeController.class);

	/**
	 * The down button.
	 */
	public static final ControllerButton BUTTON_DOWN = new ControllerButton(6, "Down", GameCubeController.class);

	/**
	 * The up button.
	 */
	public static final ControllerButton BUTTON_UP = new ControllerButton(7, "Up", GameCubeController.class);

	/**
	 * The start button.
	 */
	public static final ControllerButton BUTTON_START = new ControllerButton(8, "Start", GameCubeController.class);

	/**
	 * The Z button.
	 */
	public static final ControllerButton BUTTON_Z = new ControllerButton(9, "Z", GameCubeController.class);

	/**
	 * The R button.
	 */
	public static final ControllerButton BUTTON_R = new ControllerButton(10, "R", GameCubeController.class);

	/**
	 * The L button.
	 */
	public static final ControllerButton BUTTON_L = new ControllerButton(11, "L", GameCubeController.class);

	/**
	 * The left analog stick X axis..
	 */
	public static final GamecubeAxis LEFT_ANALOG_STICK_X_AXIS = new GamecubeAxis(34, 230);

	/**
	 * The left analog stick Y axis.
	 */
	public static final GamecubeAxis LEFT_ANALOG_STICK_Y_AXIS = new GamecubeAxis(30, 232);

	/**
	 * The right analog stick X axis.
	 */
	public static final GamecubeAxis RIGHT_ANALOG_STICK_X_AXIS = new GamecubeAxis(48, 226);

	/**
	 * The right analog stick Y axis.
	 */
	public static final GamecubeAxis RIGHT_ANALOG_STICK_Y_AXIS = new GamecubeAxis(30, 218);

	/**
	 * The left and right trigger analog Z axis.
	 */
	public static final GamecubeAxis TRIGGER_ANALOG_Z_AXIS = new GamecubeAxis(28, 206);

	private final boolean wireless;
	private float leftAnalogXAxis;
	private float leftAnalogYAxis;
	private float rightAnalogXAxis;
	private float rightAnalogYAxis;
	private float leftAnalogTriggerZAxis;
	private float rightAnalogTriggerZAxis;

	/**
	 * Creates a Nintendo GameCube controller.
	 * 
	 * @param handler
	 *            the input handler.
	 * @param wireless
	 *            <code>true</codE> if the controller is wireless,
	 *            <code>false</code> otherwise.
	 * @throws NullPointerException
	 *             if the input <code>handler</code> is <code>null</code>.
	 */
	public GameCubeController(InputHandler handler, boolean wireless) throws NullPointerException {
		super(handler);
		this.wireless = wireless;
		this.setButtons(BUTTON_A, BUTTON_B, BUTTON_X, BUTTON_Y, BUTTON_LEFT, BUTTON_RIGHT, BUTTON_DOWN, BUTTON_UP, BUTTON_START, BUTTON_Z, BUTTON_R, BUTTON_L);
		this.setDirectionalButtons(BUTTON_UP, BUTTON_DOWN, BUTTON_LEFT, BUTTON_RIGHT);
	}

	/**
	 * Returns whether or not the controller is wireless.
	 * <p>
	 * Although this is supposed to return whether or not this controller is
	 * wirles, it could possibly mean that the controller is specifically a
	 * Wavebird controller. It is unknown whether the manufacturer interprets
	 * the wirless state to mean if the controller is supposed to be a Wavebird
	 * or a wireless controller in general. The wireless controllers that have
	 * been used to test this feature are third party, and do not indicate to
	 * the adapter that they are indeed wireless for whatever reason.
	 * 
	 * @return <code>true</code> if the controller is wireless,
	 *         <code>false</code> otherwise.
	 */
	public boolean isWireless() {
		return this.wireless;
	}

	/**
	 * Updates the controller button and axes data using the
	 * <code>AdapterData</code> object.
	 * 
	 * @param data
	 *            the new adapter data.
	 */
	public void updateAdapterData(AdapterPacket data) {
		for (int i = 0; i < data.buttons.length; i++) {
			this.updateButtonStatus(this.getButton(i), data.buttons[i]);
		}
		this.leftAnalogXAxis = data.leftAnalogXAxis;
		this.leftAnalogYAxis = data.leftAnalogYAxis;
		this.rightAnalogXAxis = data.rightAnalogXAxis;
		this.rightAnalogYAxis = data.rightAnalogYAxis;
		this.leftAnalogTriggerZAxis = data.leftAnalogTriggerZAxis;
		this.rightAnalogTriggerZAxis = data.rightAnalogTriggerZAxis;
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
		return ControllerType.GAMECUBE;
	}

}
