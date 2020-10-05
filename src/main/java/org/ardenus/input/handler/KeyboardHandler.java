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

import java.util.jar.Attributes.Name;

import org.ardenus.engine.Ardenus;
import org.ardenus.engine.Game;
import org.ardenus.engine.graphics.Window;
import org.ardenus.engine.input.Input;
import org.ardenus.engine.input.controller.Controller;
import org.ardenus.engine.input.controller.keyboard.KeyboardController;
import org.ardenus.engine.input.controller.keyboard.KeyboardKey;
import org.lwjgl.glfw.GLFWScrollCallbackI;

/**
 * An input handler that can be used to listen for keyboard and mouse inputs.
 * <p>
 * Unlike any other handler, this one will automatically connect the keyboard to
 * the input manager on it's first {@link #onUpdate(Window, Controller[])} call.
 * The only reason this is done is due to the fact that every computer will most
 * likely have a keyboard connected to it (especially if they are playing a
 * video game.) Because of this, it would feel redundant and unnecessary to have
 * to manually connect the keyboard controller to the input manager or wait
 * until it shows activity. If any other input handler forcibly connects it's
 * controller type on startup whether or not it's controllers have shown
 * activity, then they are not designed properly.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.1-SNAPSHOT
 */
public class KeyboardHandler extends InputHandler implements GLFWScrollCallbackI {

	/**
	 * The key to the Ardenus Manifest property that enabled Logitech LogiLED
	 * support.
	 */
	public static final Name ENABLE_LOGILED_NAME = new Name("Enable-Logiled");

	private final boolean enableLogiled;
	private boolean shouldConnectKeyboard;
	private KeyboardController keyboard;
	private double xScroll;
	private double yScroll;

	/**
	 * Creates a keyboard handler.
	 * 
	 * @param enableLogiled
	 *            <code>true</code> if Logiled should be enabled,
	 *            <code>false</code> otherwise.
	 */
	public KeyboardHandler(boolean enableLogiled) {
		this.enableLogiled = enableLogiled;
		this.shouldConnectKeyboard = true;
	}

	/**
	 * Returns whether or not LogiLED should be initialized.
	 * 
	 * @return <code>true</code> if LogiLED should be initialized,
	 *         <code>false</code> otherwise.
	 */
	public boolean shouldEnableLogiled() {
		return this.enableLogiled;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws RuntimeException
	 *             if the <code>controllers</code> contains more than one
	 *             {@link KeyboardController}.
	 */
	@Override
	public void onUpdate(Window window, Controller[] controllers) throws RuntimeException {
		if (controllers.length > 1) {
			throw new RuntimeException("No more than one " + KeyboardController.class.getName() + " can be connected at a time");
		} else if (Input.hasController(keyboard)) {
			// Show mouse and set scroll callback
			window.setCursorVisible(true);
			if (!window.hasScrollCallback()) {
				window.setScrollCallback(this);
			}

			// Set camera for mouse X and mouse Y
			if (!keyboard.hasCamera()) {
				Game game = Ardenus.getGame();
				if (game != null) {
					keyboard.setCamera(game.getCamera());
				}
			}

			// Update controller
			for (int keyId = 0; keyId < GLFW_KEY_LAST; keyId++) {
				KeyboardKey key = keyboard.getKey(keyId);
				if (key != null) {
					boolean isPressed = false;
					if (keyId >= GLFW_KEY_SPACE && keyId <= GLFW_KEY_LAST) {
						isPressed = window.isKeyPressed(keyId);
					} else if (keyId >= GLFW_MOUSE_BUTTON_1 && keyId <= GLFW_MOUSE_BUTTON_8) {
						isPressed = window.isMouseButtonPressed(keyId);
					}
					keyboard.updateButtonStatus(key, isPressed);
				}
			}
			keyboard.updateButtonStatus(KeyboardController.SCROLL_MOUSE_UP, yScroll > 0.10F);
			keyboard.updateButtonStatus(KeyboardController.SCROLL_MOUSE_DOWN, yScroll < -0.10F);
			keyboard.updateButtonStatus(KeyboardController.SCROLL_MOUSE_LEFT, xScroll < -0.10F);
			keyboard.updateButtonStatus(KeyboardController.SCROLL_MOUSE_RIGHT, xScroll > 0.10F);
			this.xScroll = 0;
			this.yScroll = 0;
		} else {
			// Hide mouse automatically
			if (shouldConnectKeyboard == false) {
				window.setCursorVisible(false);
			}

			// Check for activity on the keyboard and mouse
			boolean hasActivity = false;
			for (int keyId = 0; keyId <= GLFW_KEY_LAST; keyId++) {
				if (keyId >= GLFW_KEY_SPACE && keyId <= GLFW_KEY_LAST) {
					if (window.isKeyPressed(keyId)) {
						/*
						 * If a controller is holding up, down, left, or right
						 * on it's left analog stick, GLFW will return
						 * GLFW_PRESS (GLFW_TRUE). For this reason, we ignore
						 * these specific keys.
						 */
						if (keyId < GLFW_KEY_RIGHT || keyId > GLFW_KEY_UP) {
							hasActivity = true;
							break;
						}
					}
				} else if (keyId >= GLFW_MOUSE_BUTTON_1 && keyId <= GLFW_MOUSE_BUTTON_8) {
					if (window.isMouseButtonPressed(keyId)) {
						hasActivity = true;
						break;
					}
				}
			}

			// Connect controller
			if ((hasActivity == true && !(Input.hasController(0, KeyboardController.class))) || shouldConnectKeyboard == true) {
				this.shouldConnectKeyboard = false;
				this.keyboard = new KeyboardController(this);
				Input.connectController(0, keyboard);
			}
		}
	}

	@Override
	public void invoke(long window, double xScroll, double yScroll) {
		if (!Input.hasController(keyboard)) {
			this.shouldConnectKeyboard = true;
		}
		this.xScroll = xScroll;
		this.yScroll = yScroll;
	}

}
