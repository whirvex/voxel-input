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

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ardenus.engine.input.button.Button;
import org.ardenus.engine.input.button.ButtonSet;
import org.ardenus.engine.input.button.ButtonSetActions;
import org.ardenus.engine.input.controller.Controller;
import org.ardenus.engine.input.controller.ControllerButton;
import org.ardenus.engine.input.controller.GamepadDirection;
import org.ardenus.engine.input.controller.action.ControllerAction;
import org.ardenus.engine.input.controller.action.ControllerActionProfile;

/**
 * Instantiated and added as a listener by {@link Input} during initialization.
 * Its sole purpose is to listen for controller connections and add their
 * annotation defined button set actions and to also to determine which button
 * to select when a controller presses one of its directions down.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.1-SNAPSHOT
 */
public class ProfileEnforcer implements InputListener {

	/**
	 * The base button set profile.
	 */
	private static final ControllerActionProfile BUTTON_SET_PROFILE = new ControllerActionProfile("button_set");

	/**
	 * Calculates the X and Y values that should be used when searching for the
	 * nearest button to be selected based on the specified button, the given
	 * direction, and whether or not the button is currently selected.
	 * 
	 * @param button
	 *            the button.
	 * @param direction
	 *            the direction.
	 * @param selected
	 *            <code>true</code> if the given button is the button that is
	 *            currently selected, <code>false</code> otherwise.
	 * @return the X and Y values that should be used as a float array, with the
	 *         first value being the X and the second being the Y.
	 */
	private static float[] rangeableXY(Button button, GamepadDirection direction, boolean selected) {
		float[] position = new float[2];
		if (direction == GamepadDirection.UP) {
			if (selected == true) {
				position[0] = button.getX() + (button.getWidth() / 2);
				position[1] = button.getY();
			} else {
				position[0] = button.getX() + (button.getWidth() / 2);
				position[1] = button.getY() + button.getHeight();
			}
		} else if (direction == GamepadDirection.DOWN) {
			if (selected == true) {
				position[0] = button.getX() + (button.getWidth() / 2);
				position[1] = button.getY() + button.getHeight();
			} else {
				position[0] = button.getX() + (button.getWidth() / 2);
				position[1] = button.getY();
			}
		} else if (direction == GamepadDirection.LEFT) {
			if (selected == true) {
				position[0] = button.getX();
				position[1] = button.getY() + (button.getHeight() / 2);
			} else {
				position[0] = button.getX() + button.getWidth();
				position[1] = button.getY() + (button.getHeight() / 2);
			}
		} else if (direction == GamepadDirection.RIGHT) {
			if (selected == true) {
				position[0] = button.getX() + button.getWidth();
				position[1] = button.getY() + (button.getHeight() / 2);
			} else {
				position[0] = button.getX();
				position[1] = button.getY() + (button.getHeight() / 2);
			}
		}
		return position;
	}

	private final Logger logger;

	/**
	 * Creates a profile enforcer and adds the global profile
	 * {@link #BUTTON_SET_PROFILE}.
	 */
	public ProfileEnforcer() {
		this.logger = LogManager.getLogger(ProfileEnforcer.class);
		Input.addGlobalProfile(BUTTON_SET_PROFILE);
	}

	@Override
	public void onPlayerConnect(Controller controller, Controller oldController) {
		ButtonSetActions actions = controller.getClass().getAnnotation(ButtonSetActions.class);
		if (actions != null) {
			// Bind press action
			try {
				if (!actions.press().equals(ButtonSetActions.NO_ACTION)) {
					ControllerButton button = (ControllerButton) controller.getClass().getField(actions.press()).get(null);
					BUTTON_SET_PROFILE.bind(Button.ACTION_BUTTON_PRESS, button);
				}
			} catch (IllegalAccessException | NoSuchFieldException | ClassCastException e) {
				logger.error("Failed to bind press action for controller", e);
			}

			// Bind back action
			try {
				if (!actions.back().equals(ButtonSetActions.NO_ACTION)) {
					ControllerButton button = (ControllerButton) controller.getClass().getField(actions.back()).get(null);
					BUTTON_SET_PROFILE.bind(Button.ACTION_BUTTON_BACK, button);
				}
			} catch (IllegalAccessException | NoSuchFieldException | ClassCastException e) {
				logger.error("Failed to bind back action for controller", e);
			}

			// Bind scroll up action
			try {
				if (!actions.scrollUp().equals(ButtonSetActions.NO_ACTION)) {
					ControllerButton button = (ControllerButton) controller.getClass().getField(actions.scrollUp()).get(null);
					BUTTON_SET_PROFILE.bind(Button.ACTION_SCROLL_UP, button);
				}
			} catch (IllegalAccessException | NoSuchFieldException | ClassCastException e) {
				logger.error("Failed to bind scroll up action for controller", e);
			}

			// Bind scroll down action
			try {
				if (!actions.scrollDown().equals(ButtonSetActions.NO_ACTION)) {
					ControllerButton button = (ControllerButton) controller.getClass().getField(actions.scrollDown()).get(null);
					BUTTON_SET_PROFILE.bind(Button.ACTION_SCROLL_DOWN, button);
				}
			} catch (IllegalAccessException | NoSuchFieldException | ClassCastException e) {
				logger.error("Failed to bind scroll down action for controller", e);
			}

			// Bind scroll left action
			try {
				if (!actions.scrollLeft().equals(ButtonSetActions.NO_ACTION)) {
					ControllerButton button = (ControllerButton) controller.getClass().getField(actions.scrollLeft()).get(null);
					BUTTON_SET_PROFILE.bind(Button.ACTION_SCROLL_LEFT, button);
				}
			} catch (IllegalAccessException | NoSuchFieldException | ClassCastException e) {
				logger.error("Failed to bind scroll left action for controller", e);
			}

			// Bind scroll right action
			try {
				if (!actions.scrollRight().equals(ButtonSetActions.NO_ACTION)) {
					ControllerButton button = (ControllerButton) controller.getClass().getField(actions.scrollRight()).get(null);
					BUTTON_SET_PROFILE.bind(Button.ACTION_SCROLL_RIGHT, button);
				}
			} catch (IllegalAccessException | NoSuchFieldException | ClassCastException e) {
				logger.error("Failed to bind scroll right action for controller", e);
			}
		}
	}

	@Override
	public void onDirectionPress(Controller controller, GamepadDirection direction) {
		if (!controller.isKeyboard()) {
			// Retrieve all currently available buttons
			ArrayList<Button> buttons = new ArrayList<Button>();
			for (ButtonSet buttonSet : Input.getCurrentButtonSets()) {
				for (Button button : buttonSet.getButtons()) {
					if (!buttons.contains(button)) {
						buttons.add(button);
					}
				}
			}

			// Get currently selected button
			Button selected = Input.getSelectedButton();
			if (selected == null || !buttons.contains(selected)) {
				for (int i = 0; i < buttons.size(); i++) {
					if (buttons.get(i).isRangeable()) {
						selected = buttons.get(i);
						Input.selectButton(selected);
						break;
					}
				}
			}

			// Select nearest button
			if (selected != null) {
				float[] selectedPos = rangeableXY(selected, direction, true);
				float selectedX = selectedPos[0];
				float selectedY = selectedPos[1];

				// Get buttons in direction
				ArrayList<Button> rangeButtons = new ArrayList<Button>();
				for (Button button : buttons) {
					if (!button.equals(selected) && button.isSelectable() && button.isRangeable()) {
						float[] buttonPos = rangeableXY(button, direction, false);
						float buttonX = buttonPos[0];
						float buttonY = buttonPos[1];
						if (direction == GamepadDirection.UP && selectedY > buttonY) {
							rangeButtons.add(button);
						} else if (direction == GamepadDirection.DOWN && selectedY < buttonY) {
							rangeButtons.add(button);
						} else if (direction == GamepadDirection.LEFT && selectedX > buttonX) {
							rangeButtons.add(button);
						} else if (direction == GamepadDirection.RIGHT && selectedX < buttonX) {
							rangeButtons.add(button);
						}
					}
				}

				// Get and select nearest button
				if (rangeButtons.size() > 0) {
					float lowestDistance = Float.MAX_VALUE;
					Button nearestButton = null;
					for (Button button : rangeButtons) {
						float[] buttonPos = rangeableXY(button, direction, false);
						float buttonX = buttonPos[0];
						float buttonY = buttonPos[1];
						float distance = (float) Math.sqrt(Math.pow(buttonX - selectedX, 2) + Math.pow(buttonY - selectedY, 2));
						if (distance < lowestDistance) {
							lowestDistance = distance;
							nearestButton = button;
						}
					}
					if (nearestButton != null) {
						Input.selectButton(nearestButton);
					}
				}
			}
		}
	}

	@Override
	public void onControllerActionPress(Controller controller, ControllerAction action) {
		if (controller.getPlayerNumber() == 0 && action.equals(Button.ACTION_BUTTON_PRESS)) {
			if (Input.getSelectedButton() != null) {
				Input.pressButton(Input.getSelectedButton(), true, true);
			}
		}
	}

	@Override
	public void onControllerActionRelease(Controller controller, ControllerAction action) {
		if (controller.getPlayerNumber() == 0 && action.equals(Button.ACTION_BUTTON_PRESS)) {
			if (Input.getSelectedButton() != null) {
				Input.pressButton(Input.getSelectedButton(), false);
			}
		}
	}
}
