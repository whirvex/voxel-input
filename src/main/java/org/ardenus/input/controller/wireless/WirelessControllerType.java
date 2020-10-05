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
package org.ardenus.input.controller.wireless;

import java.nio.FloatBuffer;

import org.ardenus.input.controller.Controller;
import org.ardenus.input.controller.ControllerType;
import org.lwjgl.glfw.GLFW;

/**
 * Contains information about the different wireless controller types.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.1-SNAPSHOT
 */
public enum WirelessControllerType {

	/**
	 * A Microsoft XBOX One/XBOX 360 controller.
	 */
	XBOX(XboxController.class, ControllerType.XBOX, 0, 1, axis -> axis, axis -> axis * -1.0F, "Afterglow Gamepad for Xbox 360", "Microsoft X-Box 360 pad", "Wireless Xbox 360 Controller", "Wireless Xbox Controller", "Redgear", "Xbox 360 Controller", "Xbox Controller"),

	/**
	 * A Sony PlayStation 4 controller.
	 */
	PLAYSTATION(PlayStationController.class, ControllerType.PLAYSTATION, 0, 1, axis -> axis, axis -> axis * -1.0F, "Wireless Controller", "USB Joystick", "SPEEDLINK STRIKE Gamepad"),

	/**
	 * A Nintendo Switch pro controller.
	 */
	SWITCH(SwitchController.class, ControllerType.SWITCH, 0, 1, axis -> SwitchController.LEFT_ANALOG_STICK_X_AXIS.getAxis(axis), axis -> SwitchController.LEFT_ANALOG_STICK_Y_AXIS.getAxis(axis), "Wireless Gamepad", "Pro Controller"),

	/**
	 * An iPega controller.
	 */
	PEGA(PegaController.class, ControllerType.PEGA, 0, 1, axis -> axis, axis -> axis * -1.0F, "GamePad");

	/**
	 * The base lambda interface class used as a way for the wireless controller
	 * type to compute axis information.
	 * 
	 * @author Trent Summerlin
	 * @since Ardenus Input v0.0.1-SNAPSHOT
	 */
	@FunctionalInterface
	private static interface AxisComputation {

		/**
		 * Converts the specified axis value to the correct axis scaling based
		 * on the controller type.
		 * 
		 * @param axis
		 *            the axis to convert.
		 * @return the converted axis.
		 */
		public float compute(float axis);

		/**
		 * Returns whether or not the specified axis is pressed.
		 * 
		 * @param axis
		 *            the unconverted axis.
		 * @return <code>true</code> if the axis is pressed, <code>false</code>
		 *         otherwise.
		 */
		public default boolean isPressed(float axis) {
			return Math.abs(compute(axis)) >= Controller.DIRECTIONAL_PRESS;
		}

	}

	private final Class<? extends WirelessController> controllerClazz;
	private final ControllerType type;
	private final int leftAnalogXIndex;
	private final int leftAnalogYIndex;
	private final AxisComputation leftAxisXComputation;
	private final AxisComputation leftAxisYComputation;
	private final String[] names;

	/**
	 * Constructs a <code>WirlessControllerType</code>.
	 * 
	 * @param controllerClazz
	 *            the controller class.
	 * @param type
	 *            the controller type.
	 * @param leftAnalogXIndex
	 *            the left analog X index.
	 * @param leftAnalogYIndex
	 *            the left analog Y index.
	 * @param leftAxisXComputation
	 *            the left analog X axis computation.
	 * @param leftAxisYComputation
	 *            the left analog Y axis computation.
	 * @param names
	 *            the names the controller can go by according to GLFW.
	 * @throws NullPointerException
	 *             if the <code>controllerClazz</code>, controller
	 *             <code>type</code>, <code>leftAxisXComputation</code>,
	 *             <code>leftAxisYComputation</code>, or <code>names</code> or
	 *             one of the names are <code>null</code>.
	 */
	private WirelessControllerType(Class<? extends WirelessController> controllerClazz, ControllerType type, int leftAnalogXIndex, int leftAnalogYIndex,
			AxisComputation leftAxisXComputation, AxisComputation leftAxisYComputation, String... names) throws NullPointerException {
		if (controllerClazz == null) {
			throw new NullPointerException("Controller class cannot be null");
		} else if (type == null) {
			throw new NullPointerException("Controller type cannot be null");
		} else if (leftAxisXComputation == null) {
			throw new NullPointerException("Left axis X computation cannot be null");
		} else if (leftAxisYComputation == null) {
			throw new NullPointerException("Left axis Y computation cannot be null");
		} else if (names == null) {
			throw new NullPointerException("Controller names cannot be null");
		}
		for (String name : names) {
			if (name == null) {
				throw new NullPointerException("Controller name cannot be null");
			}
		}
		this.controllerClazz = controllerClazz;
		this.type = type;
		this.leftAnalogXIndex = leftAnalogXIndex;
		this.leftAnalogYIndex = leftAnalogYIndex;
		this.leftAxisXComputation = leftAxisXComputation;
		this.leftAxisYComputation = leftAxisYComputation;
		this.names = names;
	}

	/**
	 * Returns the controller class.
	 * 
	 * @return the controller class.
	 */
	public Class<? extends WirelessController> getControllerClass() {
		return this.controllerClazz;
	}

	/**
	 * Returns the controller type.
	 * 
	 * @return the controller type.
	 */
	public ControllerType getControllerType() {
		return this.type;
	}

	/**
	 * Returns the names the controller can go by according to GLFW.
	 * 
	 * @return the names the controller can go by according to GLFW.
	 */
	public String[] getNames() {
		return this.names;
	}

	/**
	 * Gets the controller type based on the specified name.
	 * 
	 * @param name
	 *            the controller name.
	 * @return the controller type, <code>null</code> if none with the
	 *         <code>name</code> exist.
	 */
	public static WirelessControllerType getByName(String name) {
		for (WirelessControllerType type : WirelessControllerType.values()) {
			for (String controllerId : type.getNames()) {
				if (controllerId.equalsIgnoreCase(name)) {
					return type;
				}
			}
		}
		return null;
	}

	/**
	 * Gets the controller class from the specified name.
	 * 
	 * @param name
	 *            the name.
	 * @return the controller class, <code>null</code> if none with the
	 *         <code>name</code> exist.
	 */
	public static Class<? extends WirelessController> getControllerClassByName(String name) {
		WirelessControllerType type = getByName(name);
		if (type != null) {
			return type.getControllerClass();
		}
		return null;
	}

	/**
	 * Checks if the joystick with the specified joystick ID has pressed the
	 * left analog stick in any direction.
	 * 
	 * @param joystickId
	 *            the joystick ID.
	 * @return <code>true</code> if the controller is a known controller type
	 *         and has pressed any directions on the left analog stick,
	 *         <code>false</code> otherwise.
	 */
	public static boolean isAxisPressed(int joystickId) {
		WirelessControllerType type = getByName(GLFW.glfwGetJoystickName(joystickId));
		if (type != null) {
			FloatBuffer axesBuffer = GLFW.glfwGetJoystickAxes(joystickId);
			float leftXAxis = axesBuffer.get(type.leftAnalogXIndex);
			float leftYAxis = axesBuffer.get(type.leftAnalogYIndex);
			return type.leftAxisXComputation.isPressed(leftXAxis) || type.leftAxisYComputation.isPressed(leftYAxis);
		}
		return false;
	}

}
