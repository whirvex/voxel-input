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

import static org.lwjgl.glfw.GLFW.*;

import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.ardenus.engine.input.Input;
import org.ardenus.engine.input.controller.Controller;
import org.ardenus.engine.input.controller.ControllerButton;
import org.ardenus.engine.input.handler.WirelessInputHandler;

/**
 * A wireless controller handled and managed via the GLFW API.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.1-SNAPSHOT
 */
public abstract class WirelessController extends Controller {

	/**
	 * Creates a wireless controller using the specified joystick ID.
	 * 
	 * @param handler
	 *            the input handler.
	 * @param jid
	 *            the joystick ID.
	 * @return the generated controller, <code>null</code> if no
	 *         {@link WirelessInputHandler} for the controller exists.
	 * @throws NullPointerException
	 *             if the input <code>handler</code> is <code>null</code>.
	 */
	public static WirelessController getController(WirelessInputHandler handler, int jid) throws NullPointerException {
		if (handler == null) {
			throw new NullPointerException("Input handler cannot be null");
		}
		String id = glfwGetJoystickName(jid);
		String guid = glfwGetJoystickGUID(jid);
		Class<? extends WirelessController> controllerClazz = WirelessControllerType.getControllerClassByName(id);
		if (controllerClazz != null) {
			try {
				return controllerClazz.getConstructor(WirelessInputHandler.class, int.class, String.class, String.class).newInstance(handler, jid, guid, id);
			} catch (InstantiationException | InvocationTargetException e) {
				throw new RuntimeException("Failed to instantiate controller class " + controllerClazz.getName(), e);
			} catch (IllegalAccessException | SecurityException e) {
				throw new IllegalArgumentException("Wireless controller constructor must be public", e);
			} catch (NoSuchMethodException e) {
				throw new IllegalArgumentException("Wirless controller constructor not present", e);
			}
		}
		return null;
	}

	private final int jid;
	private final String guid;
	private final String name;
	private boolean useDisconnectButtons;

	/**
	 * Creates a wireless controller.
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
	public WirelessController(WirelessInputHandler handler, int jid, String guid, String name) throws NullPointerException {
		super(handler);
		this.jid = jid;
		this.guid = guid;
		this.name = name;
		this.useDisconnectButtons = true;
	}

	/**
	 * Returns the joystick ID.
	 * 
	 * @return the joystick ID.
	 */
	public int getJID() {
		return this.jid;
	}

	/**
	 * Returns the globally unique ID.
	 * 
	 * @return the globally unique ID.
	 */
	public String getGuid() {
		return this.guid;
	}

	/**
	 * Returns the name of the controller.
	 * 
	 * @return the name of the controller.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returns whether or not the controller will disconnect if the disconnect
	 * buttons are simultaneously pressed.
	 * 
	 * @return <code>true</code> if the controller will disconnect if the
	 *         disconnect buttons are simultaneously pressed, <code>false</code>
	 *         otherwise.
	 */
	public boolean isUsingDisconnectButtons() {
		return this.useDisconnectButtons;
	}

	/**
	 * Sets whether or not the controller will disconnect if the disconnect
	 * buttons are simultaneously pressed.
	 * 
	 * @param useDisconnectButtons
	 *            <code>true</code> if the controller should disconnect when the
	 *            disconnect buttons are simultaneously pressed,
	 *            <code>false</code> otherwise.
	 */
	public void setUsingDisconnectButtons(boolean useDisconnectButtons) {
		this.useDisconnectButtons = useDisconnectButtons;
	}

	/**
	 * Checks whether or not the controller should be disconnected based on the
	 * specified button statuses and returns whether or not the controller was
	 * disconnected as a result of calling this method.
	 * 
	 * @param buttons
	 *            the press states of the buttons.
	 * @return <code>true</code> if the controller was disconnected,
	 *         <code>false</code> otherwise.
	 * @throws NullPointerException
	 *             if the <code>buttons</code> are <code>null</code>.
	 */
	protected final boolean checkDisconnect(boolean[] buttons) throws NullPointerException {
		if (buttons == null) {
			throw new NullPointerException("Buttons cannot be null");
		}
		for (ControllerButton[] disconnectButtons : this.getDisconnectButtons()) {
			for (ControllerButton disconnectButton : disconnectButtons) {
				if (buttons[disconnectButton.getId()] == false) {
					return false; // Not enough buttons pressed
				}
			}
		}
		if (Input.hasController(this)) {
			Input.disconnectController(this);
			return true;
		}
		return false;
	}

	/**
	 * Updates the controller using the data retrieved from GLFW.
	 * 
	 * @param axes
	 *            the axes.
	 * @param buttons
	 *            the buttons.
	 * @param hats
	 *            the hats.
	 * @throws NullPointerException
	 *             if the <code>axes</code>, <code>buttons</code>, or
	 *             <code>hats</code> are <code>null</code>.
	 */
	public abstract void updateGLFWData(FloatBuffer axes, ByteBuffer buttons, ByteBuffer hats) throws NullPointerException;

	/**
	 * Returns the combinations of buttons that when pressed will result in the
	 * controller being disconnected when at the same time.
	 * <p>
	 * These buttons must be ignored when determining whether or not a
	 * controller should be connected. If this is not done, it will result in
	 * the controller being reconnected immediately after (because it will show
	 * activity via the disconnect button)!
	 * 
	 * @return the combinations of buttons that when pressed will result in the
	 *         controller being disconnected when at the same time.
	 */
	public abstract ControllerButton[][] getDisconnectButtons();

}
