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
package org.ardenus.engine.input.handler;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.ByteBuffer;

import org.ardenus.engine.graphics.Window;
import org.ardenus.engine.input.Input;
import org.ardenus.engine.input.controller.Controller;
import org.ardenus.engine.input.controller.ControllerButton;
import org.ardenus.engine.input.controller.ControllerType;
import org.ardenus.engine.input.controller.wireless.WirelessController;
import org.ardenus.engine.input.controller.wireless.WirelessControllerType;

/**
 * An extension of the {@link InputHandler} that can be used to implement
 * support for wireless controller inputs via the GLFW controller API.
 * 
 * @author Trent Summerlin.
 * @since Ardenus Input v0.0.1-SNAPSHOT
 */
public class WirelessInputHandler extends InputHandler {

	private final ControllerType[] types;

	/**
	 * Creates a wireless input handler with support for the given wireless
	 * controller types.
	 * 
	 * @param types
	 *            the wireless controller types, if none are specified all
	 *            controller types will be considered supported.
	 * @throws NullPointerException
	 *             if the <code>types</code> or one of the wireless controller
	 *             types are <code>null</code>.
	 */
	public WirelessInputHandler(WirelessControllerType... types) throws NullPointerException {
		if (types == null) {
			throw new NullPointerException("Wireless controller types cannot be null");
		} else if (types.length <= 0) {
			this.types = null;
		} else {
			this.types = new ControllerType[types.length];
			for (int i = 0; i < types.length; i++) {
				if (types[i] == null) {
					throw new NullPointerException("Wireless controller type cannot be null");
				}
				this.types[i] = types[i].getControllerType();
			}
		}
	}

	/**
	 * Returns whether or not the specified controller type is a valid
	 * controller type.
	 * 
	 * @param type
	 *            the controller type.
	 * @return <code>true</code> if the controller type is a valid controller
	 *         type, <code>false</code> otherwise.
	 */
	private boolean isValidControllerType(ControllerType type) {
		if (types != null) {
			for (ControllerType controllerType : types) {
				if (controllerType.equals(type)) {
					return true;
				}
			}
			return false;
		}
		return true;
	}

	/**
	 * Returns whether or not there is a connected controller with the specified
	 * joystick ID.
	 * 
	 * @param joystickId
	 *            the joystick ID.
	 * @return <code>true</code> if there is a connected controller with the
	 *         joystick ID, <code>false</code> otherwise.
	 */
	public boolean hasController(int joystickId) {
		for (Controller controller : Input.getControllers()) {
			if (controller instanceof WirelessController) {
				if (((WirelessController) controller).getJID() == joystickId) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void onUpdate(Window window, Controller[] controllers) {
		// Check for activity from unconnected controllers
		for (int i = 0; i < GLFW_JOYSTICK_LAST; i++) {
			if (glfwJoystickPresent(i) && !this.hasController(i) && Input.hasEmptyPlayerSlot()) {
				// Check for any pressed buttons
				int buttonId = -1;
				ByteBuffer buttons = glfwGetJoystickButtons(i);
				for (int j = 0; j < buttons.capacity(); j++) {
					if (buttons.get() > 0) {
						buttonId = j;
						break;
					}
				}

				// Connect controller if activity is detected
				if (buttonId >= 0 || WirelessControllerType.isAxisPressed(i)) {
					WirelessController controller = WirelessController.getController(this, i);
					boolean isDisconnected = false;
					disconnect_search: for (ControllerButton[] disconnect : controller.getDisconnectButtons()) {
						for (ControllerButton button : disconnect) {
							if (button.getId() == buttonId) {
								isDisconnected = true;
								break disconnect_search;
							}
						}
					}
					if (this.isValidControllerType(controller.getType()) && isDisconnected == false) {
						Input.connectController(controller);
					}
				}
			}
		}

		// Update currently connected controllers
		for (int i = 0; i < controllers.length; i++) {
			WirelessController controller = (WirelessController) controllers[i];
			if (!glfwJoystickPresent(controller.getJID())) {
				Input.disconnectController(controller);
			} else {
				controller.updateGLFWData(glfwGetJoystickAxes(controller.getJID()), glfwGetJoystickButtons(controller.getJID()), glfwGetJoystickHats(controller.getJID()));
			}
		}

	}

}
