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
package org.ardenus.engine.input;

import static org.lwjgl.glfw.GLFW.*;

import org.ardenus.engine.input.button.Button;
import org.ardenus.engine.input.button.ButtonSet;
import org.ardenus.engine.input.controller.Controller;
import org.ardenus.engine.input.controller.ControllerButton;
import org.ardenus.engine.input.controller.GamepadDirection;
import org.ardenus.engine.input.controller.action.ControllerAction;

/**
 * Allows for classes to listen for events relating to input.
 * <p>
 * In order to listen for events, input listeners must use the
 * {@link Input#addListener(InputListener)} method.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.1-SNAPSHOT
 * @see org.ardenus.engine.input.Input Input
 */
public interface InputListener {

	/**
	 * Returns whether or not the shift key is pressed for the specified mode.
	 * 
	 * @param mode
	 *            the mode.
	 * @return <code>true</code> if the shift key is pressed, <code>false</code>
	 *         otherwise.
	 */
	public default boolean isShift(int mode) {
		return (mode & GLFW_MOD_SHIFT) >= GLFW_MOD_SHIFT;
	}

	/**
	 * Returns whether or not the control key is pressed for the specified mode.
	 * 
	 * @param mode
	 *            the mode.
	 * @return <code>true</code> if the control key is pressed,
	 *         <code>false</code> otherwise.
	 */
	public default boolean isControl(int mode) {
		return (mode & GLFW_MOD_CONTROL) >= GLFW_MOD_CONTROL;
	}

	/**
	 * Returns whether or not the alt key is pressed for the specified mode.
	 * 
	 * @param mode
	 *            the mode.
	 * @return <code>true</code> if the alt key is pressed, <code>false</code>
	 *         otherwise.
	 */
	public default boolean isAlt(int mode) {
		return (mode & GLFW_MOD_ALT) >= GLFW_MOD_ALT;
	}

	/**
	 * Returns whether or not the super key is pressed for the specified mode.
	 * 
	 * @param mode
	 *            the mode.
	 * @return <code>true</code> if the super key is pressed, <code>false</code>
	 *         otherwise.
	 */
	public default boolean isSuper(int mode) {
		return (mode & GLFW_MOD_SUPER) >= GLFW_MOD_SUPER;
	}

	/**
	 * Called when an {@link InputListener} is added.
	 * 
	 * @param listener
	 *            the input listener.
	 */
	public default void onListenerAdd(InputListener listener) {
	}

	/**
	 * Called when an {@link InputListener} is removed.
	 * 
	 * @param listener
	 *            the input listener.
	 */
	public default void onListenerRemove(InputListener listener) {
	}

	/**
	 * Called when a {@link ButtonSet} is enabled.
	 * 
	 * @param buttonSet
	 *            the button set.
	 */
	public default void onButtonSetEnable(ButtonSet buttonSet) {
	}

	/**
	 * Called when a {@link ButtonSet} is disabled.
	 * 
	 * @param buttonSet
	 *            the button set.
	 */
	public default void onButtonSetDisable(ButtonSet buttonSet) {
	}

	/**
	 * Called when a button has been enabled.
	 * <p>
	 * A button is considered enabled if one of the button sets it belongs to
	 * has been enabled.
	 * 
	 * @param button
	 *            the button.
	 */
	public default void onButtonEnable(Button button) {
	}

	/**
	 * Called when a button has been disabled.
	 * <p>
	 * A button is considered disabled if all of the button sets it belongs to
	 * have been disabled.
	 * 
	 * @param button
	 *            the button.
	 */
	public default void onButtonDisable(Button button) {

	}

	/**
	 * Called when a keyboard key is pressed.
	 * 
	 * @param key
	 *            the key.
	 * @param scancode
	 *            the scancode.
	 * @param mode
	 *            the mode.
	 */
	public default void onKeyPress(int key, int scancode, int mode) {
	}

	/**
	 * Called when a keyboard key is released.
	 * 
	 * @param key
	 *            the key.
	 * @param scancode
	 *            the scancode.
	 * @param mode
	 *            the mode.
	 */
	public default void onKeyRelease(int key, int scancode, int mode) {
	}

	/**
	 * Called when a button is selected.
	 * 
	 * @param button
	 *            the button that was selected.
	 */
	public default void onButtonSelect(Button button) {
	}

	/**
	 * Called when a button is unselected.
	 * 
	 * @param button
	 *            the button that was unselected.
	 */
	public default void onButtonUnselect(Button button) {
	}

	/**
	 * Called when a button is pressed.
	 * 
	 * @param button
	 *            the button that was pressed.
	 */
	public default void onButtonPress(Button button) {
	}

	/**
	 * Called when the button is double pressed.
	 * 
	 * @param button
	 *            the button that was double pressed.
	 */
	public default void onButtonDoublePress(Button button) {
	}

	/**
	 * Called when a button is unpressed.
	 * 
	 * @param button
	 *            the button that was unpressed.
	 */
	public default void onButtonUnpress(Button button) {
	}

	/**
	 * Called when a player is connected.
	 * 
	 * @param controller
	 *            the connected controller.
	 * @param oldController
	 *            the controller that was previously connected in that player
	 *            slot, <code>null</code> if there was none beforehand.
	 */
	public default void onPlayerConnect(Controller controller, Controller oldController) {
	}

	/**
	 * Called when a player is disconnected.
	 * 
	 * @param controller
	 *            the controller that disconnected.
	 */
	public default void onPlayerDisconnect(Controller controller) {
	}

	/**
	 * Called when a controller button is pressed.
	 * 
	 * @param controller
	 *            the controller that pressed the button.
	 * @param button
	 *            the controller button that was pressed.
	 */
	public default void onControllerButtonPress(Controller controller, ControllerButton button) {
	}

	/**
	 * Called when a controller button is released.
	 * 
	 * @param controller
	 *            the controller that released the button.
	 * @param button
	 *            the controller button that was released.
	 */
	public default void onControllerButtonRelease(Controller controller, ControllerButton button) {
	}

	/**
	 * Called when a direction is pressed.
	 * 
	 * @param controller
	 *            the controller that pressed the direction.
	 * @param direction
	 *            the gamepad direction that was pressed.
	 */
	public default void onDirectionPress(Controller controller, GamepadDirection direction) {
	}

	/**
	 * Called when a direction is released.
	 * 
	 * @param controller
	 *            the controller that released the direction.
	 * @param direction
	 *            the gamepad action that was released.
	 */
	public default void onDirectionRelease(Controller controller, GamepadDirection direction) {
	}

	/**
	 * Called when a controller action is pressed.
	 * 
	 * @param controller
	 *            the controller that pressed the action.
	 * @param action
	 *            the controller action that was pressed.
	 */
	public default void onControllerActionPress(Controller controller, ControllerAction action) {
	}

	/**
	 * Called when a controller action is released.
	 * 
	 * @param controller
	 *            the controller that released the action.
	 * @param action
	 *            the controller action that was released.
	 */
	public default void onControllerActionRelease(Controller controller, ControllerAction action) {
	}

}
