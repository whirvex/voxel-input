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

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.ArrayUtils;
import org.ardenus.engine.input.Input;
import org.ardenus.engine.input.controller.action.ControllerAction;
import org.ardenus.engine.input.controller.keyboard.KeyboardController;
import org.ardenus.engine.input.handler.InputHandler;

/**
 * A controller.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.1-SNAPSHOT
 */
public abstract class Controller {

	/**
	 * How far an analog stick must be pressed in order for it to be considered
	 * pressed like a button.
	 */
	public static final float DIRECTIONAL_PRESS = 2.0F / 3.0F;

	private final InputHandler handler;
	private int playerNumber;
	private float leftMotorIntensity;
	private float rightMotorIntensity;
	private final HashMap<ControllerButton, Boolean> buttonStates;
	private final HashMap<ControllerButton, Long> buttonHoldTimes;
	private final HashMap<ControllerButton, Long> buttonHoldPressTimes;
	private final HashMap<GamepadDirection, ControllerButton> directionButtons;
	private final HashMap<GamepadDirection, Boolean> directionStates;
	private final HashMap<GamepadDirection, Long> directionHoldTimes;
	private final HashMap<GamepadDirection, Long> directionHoldPressTimes;
	private final HashMap<ControllerAction, ControllerButton[]> actions;
	private final HashMap<ControllerAction, Boolean> actionStates;
	private final HashMap<ControllerAction, Long> actionHoldTimes;
	private final HashMap<ControllerAction, Long> actionHoldPressTimes;

	/**
	 * Creates a controller.
	 * 
	 * @param handler
	 *            the input handler the controller belongs to.
	 * @throws NullPointerException
	 *             if the input <code>handler</code> is <code>null</code>.
	 */
	public Controller(InputHandler handler) throws NullPointerException {
		if (handler == null) {
			throw new NullPointerException("Input handler cannot be null");
		}
		this.handler = handler;
		this.playerNumber = -1;
		this.buttonStates = new HashMap<ControllerButton, Boolean>();
		this.buttonHoldTimes = new HashMap<ControllerButton, Long>();
		this.buttonHoldPressTimes = new HashMap<ControllerButton, Long>();
		this.directionButtons = new HashMap<GamepadDirection, ControllerButton>();
		this.directionStates = new HashMap<GamepadDirection, Boolean>();
		this.directionHoldTimes = new HashMap<GamepadDirection, Long>();
		this.directionHoldPressTimes = new HashMap<GamepadDirection, Long>();
		this.actions = new HashMap<ControllerAction, ControllerButton[]>();
		this.actionStates = new HashMap<ControllerAction, Boolean>();
		this.actionHoldTimes = new HashMap<ControllerAction, Long>();
		this.actionHoldPressTimes = new HashMap<ControllerAction, Long>();
	}

	/**
	 * Returns whether or not the controller is a keyboard and mouse.
	 * 
	 * @return <code>true</code> if the controller is a keyboard and mouse,
	 *         <code>false</code> otherwise.
	 */
	public final boolean isKeyboard() {
		return this instanceof KeyboardController;
	}

	/**
	 * Returns the {@link InputHandler} the controller belongs to.
	 * 
	 * @return the {@link InputHandler} the controller belongs to.
	 */
	public final InputHandler getHandler() {
		return this.handler;
	}

	/**
	 * Returns the name of the class of the {@link InputHandler} the controller
	 * belongs to.
	 * 
	 * @return the name of the class of the {@link InputHandler} the controller
	 *         belongs to.
	 */
	public final String getHandlerClassName() {
		return this.getHandler().getClass().getName();
	}

	/**
	 * Returns the player number of the controller.
	 * 
	 * @return the player number, <code>-1</code> if it has not yet been set.
	 */
	public final int getPlayerNumber() {
		return this.playerNumber;
	}

	/**
	 * Sets the player number of the controller.
	 * 
	 * @param playerNumber
	 *            the player number of the controller.
	 * @throws IllegalStateException
	 *             if the player number of the controller has already been set.
	 */
	public final void setPlayerNumber(int playerNumber) throws IllegalStateException {
		if (this.playerNumber > -1) {
			throw new IllegalStateException("Player number has already been set");
		}
		this.playerNumber = playerNumber;
	}

	/**
	 * Sets the rumbling intensity.
	 * 
	 * @param leftMotor
	 *            the rumble intensity for the left motor.
	 * @param rightMotor
	 *            the rumble intensity for the right motor.
	 */
	public final void setRumbleIntensity(float leftMotor, float rightMotor) {
		this.leftMotorIntensity = leftMotor;
		this.rightMotorIntensity = rightMotor;
	}

	/**
	 * Sets the rumbling intensity.
	 * 
	 * @param intensity
	 *            the rumbling intensity.
	 */
	public final void setRumbleIntensity(float intensity) {
		this.setRumbleIntensity(1.0F, 1.0F);
	}

	/**
	 * Sets whether or not the controller should rumble.
	 * 
	 * @param rumbling
	 *            <code>true</code> if the controller should rumble,
	 *            <code>false</code> otherwise.
	 */
	public final void setRumbling(boolean rumbling) {
		this.setRumbleIntensity(rumbling ? 1.0F : 0.0F);
	}

	/**
	 * Returns whether or not the controller is rumbling.
	 * 
	 * @return <code>true</code> if the controller is rumbling,
	 *         <code>false</code> otherwise.
	 */
	public final boolean isRumbling() {
		return this.leftMotorIntensity > 0.0F || this.rightMotorIntensity > 0.0F;
	}

	/**
	 * Returns the rumble intensity for the left motor.
	 * 
	 * @return the rumble intensity for the left motor.
	 */
	public final float getLeftMotorIntensity() {
		return this.leftMotorIntensity;
	}

	/**
	 * Returns the rumble intensity for the right motor.
	 * 
	 * @return the rumble intensity for the right motor.
	 */
	public final float getRightMotorIntensity() {
		return this.rightMotorIntensity;
	}

	/**
	 * Sets the controller buttons.
	 * 
	 * @param buttons
	 *            the buttons the controller can use.
	 * @throws NullPointerException
	 *             if the <code>buttons</code> or one of the buttons are
	 *             <code>null</code>.
	 * @throws IllegalStateException
	 *             if the buttons have already been set.
	 * @throws IllegalArgumentException
	 *             if no buttons were specified or the class of one of the
	 *             buttons does not belong to the controller.
	 */
	protected final void setButtons(ControllerButton... buttons) throws NullPointerException, IllegalStateException, IllegalArgumentException {
		if (buttons == null) {
			throw new NullPointerException("Buttons cannot be null");
		} else if (buttons.length <= 0) {
			throw new IllegalArgumentException("No buttons specified");
		} else if (buttonStates.size() > 0) {
			throw new IllegalStateException("Buttons have already been set");
		}
		for (int i = 0; i < buttons.length; i++) {
			if (buttons[i] == null) {
				throw new NullPointerException("Button cannot be null");
			} else if (!buttons[i].getControllerClass().isAssignableFrom(this.getClass())) {
				throw new IllegalArgumentException("Button class must be class or child class of " + this.getClass().getName());
			}
			buttonStates.put(buttons[i], false);
			buttonHoldTimes.put(buttons[i], -1L);
			buttonHoldPressTimes.put(buttons[i], -1L);
		}
	}

	/**
	 * Returns the controller buttons.
	 * 
	 * @return the controller buttons.
	 */
	public final ControllerButton[] getButtons() {
		return buttonStates.keySet().toArray(new ControllerButton[buttonStates.size()]);
	}

	/**
	 * Returns the button with the specified ID.
	 * 
	 * @param id
	 *            the button ID.
	 * @return the button with the specified ID, <code>null</code> if it doe
	 *         snot exist.
	 */
	public final ControllerButton getButton(int id) {
		for (ControllerButton button : buttonStates.keySet()) {
			if (button.getId() == id) {
				return button;
			}
		}
		return null;
	}

	/**
	 * Returns whether or not the specified button is pressed.
	 * 
	 * @param button
	 *            the button to check.
	 * @return <code>true</code> if the button is pressed, <code>false</code>
	 *         otherwise.
	 */
	public final boolean isButtonPressed(ControllerButton button) {
		if (buttonStates.containsKey(button)) {
			for (ControllerButton registeredButton : buttonStates.keySet()) {
				if (registeredButton.getId() == button.getId()) {
					return buttonStates.get(button) == true;
				}
			}
		}
		return false;
	}

	/**
	 * Sets the directional buttons of the controller.
	 * 
	 * @param upButton
	 *            the up button.
	 * @param downButton
	 *            the down button.
	 * @param leftButton
	 *            the left button.
	 * @param rightButton
	 *            the right button.
	 * @throws NullPointerException
	 *             if the <code>upButton</code>, <code>downButton</code>,
	 *             <code>leftButton</code>, or <code>rightButton</code> are
	 *             <code>null</code>.
	 * @throws IllegalStateException
	 *             if the controller buttons have not yet been set via the
	 *             {@link #setButtons(ControllerButton...)} method or the
	 *             directional pad buttons have already been set.
	 * @throws IllegalControllerButtonException
	 *             if the <code>upButton</code>, <code>downButton</code>,
	 *             <code>leftButton</code>, or <code>rightButton</code> do not
	 *             belong to the controller.
	 */
	protected final void setDirectionalButtons(ControllerButton upButton, ControllerButton downButton, ControllerButton leftButton, ControllerButton rightButton)
			throws NullPointerException, IllegalStateException, IllegalControllerButtonException {
		if (upButton == null) {
			throw new NullPointerException("Up button cannot be null");
		} else if (downButton == null) {
			throw new NullPointerException("Down button cannot be null");
		} else if (leftButton == null) {
			throw new NullPointerException("Left button cannot be null");
		} else if (rightButton == null) {
			throw new NullPointerException("Right button cannot be null");
		} else if (buttonStates.size() < 0) {
			throw new IllegalStateException("Buttons have not yet been set");
		} else if (directionButtons.size() > 0) {
			throw new RuntimeException("Direcitonal buttons have already been set");
		}
		directionButtons.put(GamepadDirection.UP, upButton);
		directionButtons.put(GamepadDirection.DOWN, downButton);
		directionButtons.put(GamepadDirection.LEFT, leftButton);
		directionButtons.put(GamepadDirection.RIGHT, rightButton);
		for (GamepadDirection direction : directionButtons.keySet()) {
			directionStates.put(direction, false);
			ControllerButton directionButton = directionButtons.get(direction);
			if (!buttonStates.containsKey(directionButton)) {
				throw new IllegalControllerButtonException(this, directionButton);
			}
		}
	}

	/**
	 * Returns the directional buttons of the controller.
	 * 
	 * @return the directional buttons of the controller.
	 */
	public final ControllerButton[] getDirectionalButtons() {
		return directionButtons.values().toArray(new ControllerButton[directionButtons.size()]);
	}

	/**
	 * Returns the direction belonging to the specified button.
	 * 
	 * @param button
	 *            the button.
	 * @return the direction belonging to the button, <code>null</code> if no
	 *         direction belongs to it.
	 */
	public final GamepadDirection getDirection(ControllerButton button) {
		if (button != null) {
			for (GamepadDirection direction : directionButtons.keySet()) {
				if (directionButtons.get(direction).equals(button)) {
					return direction;
				}
			}
		}
		return null;
	}

	/**
	 * Returns whether or not the specified direction is pressed.
	 * 
	 * @param direction
	 *            the direction.
	 * @return <code>true</code> if the direction is pressed, <code>false</code>
	 *         otherwise.
	 */
	public final boolean isDirectionPressed(GamepadDirection direction) {
		return directionStates.get(direction);
	}

	/**
	 * Returns whether or not the specified direction is pressed.
	 * 
	 * @param button
	 *            the button of the direction.
	 * @return <code>true</code> if the direction is pressed, <code>false</code>
	 *         otherwise.
	 */
	public final boolean isDirectionPressed(ControllerButton button) {
		GamepadDirection direction = this.getDirection(button);
		if (direction == null) {
			return false;
		}
		return this.isDirectionPressed(direction);
	}

	/**
	 * Returns whether or not the specified direction is pressed.
	 * <p>
	 * Same as {@link #isDirectionPressed(ControllerButton)} with the exception
	 * that this method will also check the right analog stick.
	 * 
	 * @param direction
	 *            the direction.
	 * @return <code>true</code> if the direction is pressed, <code>false</code>
	 *         otherwise.
	 */
	public final boolean isRightDirectionPressed(GamepadDirection direction) {
		if (this.isDirectionPressed(direction)) {
			return true;
		} else if (direction == GamepadDirection.UP) {
			return this.getRightYAxis() >= DIRECTIONAL_PRESS;
		} else if (direction == GamepadDirection.DOWN) {
			return this.getRightYAxis() <= -DIRECTIONAL_PRESS;
		} else if (direction == GamepadDirection.LEFT) {
			return this.getRightXAxis() <= -DIRECTIONAL_PRESS;
		} else if (direction == GamepadDirection.RIGHT) {
			return this.getRightXAxis() >= DIRECTIONAL_PRESS;
		}
		return false;
	}

	/**
	 * Returns whether or not the the specified action has all of the given
	 * buttons bound to it.
	 * 
	 * @param action
	 *            the action.
	 * @param buttons
	 *            the buttons.
	 * @return <code>true</code> if the action has all of the buttons bound to
	 *         it, <code>false</code> otherwise.
	 */
	public final boolean isBound(ControllerAction action, ControllerButton... buttons) {
		if (actions.containsKey(action)) {
			ControllerButton[] bound = actions.get(action);
			for (ControllerButton button : buttons) {
				if (!ArrayUtils.contains(bound, button)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Returns whether or not the specified action has any buttons bound to it.
	 * 
	 * @param action
	 *            the action.
	 * @return <code>true</code> if the action has any buttons bound to it,
	 *         <code>false</code> otherwise.
	 */
	public final boolean isBound(ControllerAction action) {
		return actions.containsKey(action);
	}

	/**
	 * Returns the buttons that can trigger the specified action.
	 * 
	 * @param action
	 *            the action.
	 * @return the buttons that can trigger the specified action,
	 *         <code>null</code> if the action is not bound.
	 */
	public final ControllerButton[] getBound(ControllerAction action) {
		return actions.get(action);
	}

	/**
	 * Binds the givens buttons to the specified action.
	 * <p>
	 * If buttons are already bound to the action, they will stay bound. To
	 * unbind a button from an action, use the
	 * {@link #unbind(ControllerAction, ControllerButton...)} method. To unbind
	 * all buttons from an action, use the {@link #unbind(ControllerAction)}
	 * method.
	 * 
	 * @param action
	 *            the action to bind the buttons to.
	 * @param buttons
	 *            the buttons to bind to the action.
	 * @throws NullPointerException
	 *             if the <code>action</code>, <code>buttons</code> or one of
	 *             the buttons are <code>null</code>.
	 * @throws IllegalControllerButtonException
	 *             if one of the buttons does not belong to the controller.
	 */
	public final void bind(ControllerAction action, ControllerButton... buttons) throws NullPointerException, IllegalControllerButtonException {
		if (action == null) {
			throw new NullPointerException("Action cannot be null");
		} else if (buttons == null) {
			throw new NullPointerException("Buttons cannot be null");
		} else if (buttons.length > 0) {
			for (ControllerButton button : buttons) {
				if (!buttonStates.containsKey(button)) {
					throw new IllegalControllerButtonException(this, button);
				}
			}

			// Get old buttons and add new buttons
			ArrayList<ControllerButton> actionButtonList = new ArrayList<ControllerButton>();
			if (actions.containsKey(action)) {
				for (ControllerButton button : actions.get(action)) {
					actionButtonList.add(button);
				}
			}
			for (ControllerButton button : buttons) {
				if (!actionButtonList.contains(button)) {
					actionButtonList.add(button);
				}
			}

			// Update action
			actions.put(action, actionButtonList.toArray(new ControllerButton[actionButtonList.size()]));
			if (!actionStates.containsKey(action)) {
				actionStates.put(action, false);
				actionHoldTimes.put(action, -1L);
				actionHoldPressTimes.put(action, -1L);
			}
		}
	}

	/**
	 * Unbinds the given buttons from the specified action.
	 * <p>
	 * If no more buttons are bound to the action as a result of calling this
	 * method, the {@link #unbind(ControllerAction)} method will be called as
	 * well.
	 * 
	 * @param action
	 *            the action to unbind the buttons from.
	 * @param buttons
	 *            the buttons to unbind from the action.
	 * @throws NullPointerException
	 *             if the <code>action</code>, <code>buttons</code> or one of
	 *             the buttons are <code>null</code>.
	 * @throws IllegalControllerButtonException
	 *             if one of the buttons does not belong to the controller.
	 */
	public void unbind(ControllerAction action, ControllerButton... buttons) {
		if (action == null) {
			throw new NullPointerException("Action cannot be null");
		} else if (buttons == null) {
			throw new NullPointerException("Buttons cannot be null");
		} else if (buttons.length > 0 && actions.containsKey(action)) {
			for (ControllerButton button : buttons) {
				if (!buttonStates.containsKey(button)) {
					throw new IllegalControllerButtonException(this, button);
				}
			}

			// Determine which buttons to remove
			ArrayList<ControllerButton> actionButtonList = new ArrayList<ControllerButton>();
			for (ControllerButton button : actions.get(action)) {
				if (!ArrayUtils.contains(buttons, button)) {
					actionButtonList.add(button);
				}
			}

			// Update action
			actions.put(action, actionButtonList.toArray(new ControllerButton[actionButtonList.size()]));
			if (actionButtonList.size() <= 0) {
				this.unbind(action);
			}
		}
	}

	/**
	 * Unbinds the specified action entirely.
	 * 
	 * @param action
	 *            the action.
	 * @throws NullPointerException
	 *             if the <code>action</code> is <code>null</code>.
	 */
	public void unbind(ControllerAction action) throws NullPointerException {
		if (action == null) {
			throw new NullPointerException("Action cannot be null");
		}
		actions.remove(action);
		actionStates.remove(action);
		actionHoldTimes.remove(action);
		actionHoldPressTimes.remove(action);
	}

	/**
	 * Returns the actions that the specified button is bound to.
	 * 
	 * @param button
	 *            the button.
	 * @return the actions that the button is bound to.
	 */
	public final ControllerAction[] getAction(ControllerButton button) {
		ArrayList<ControllerAction> actionList = new ArrayList<ControllerAction>();
		if (button != null) {
			for (ControllerAction action : actions.keySet()) {
				for (ControllerButton actionButton : actions.get(action)) {
					if (actionButton.equals(button)) {
						actionList.add(action);
					}
				}
			}
		}
		return actionList.toArray(new ControllerAction[actionList.size()]);
	}

	/**
	 * Returns whether or not the specified action is pressed.
	 * 
	 * @param action
	 *            the action.
	 * @return <code>true</code> if the action is pressed, <code>false</code>
	 *         otherwise.
	 */
	public final boolean isActionPressed(ControllerAction action) {
		if (!actionStates.containsKey(action)) {
			return false;
		}
		return actionStates.get(action);
	}

	/**
	 * Updates the status of the specified button.
	 * 
	 * @param button
	 *            the button to update.
	 * @param isPressed
	 *            <code>true</code> if the button is pressed, <code>false</code>
	 *            otherwise.
	 * @throws NullPointerException
	 *             if the <code>button</code> is <code>null</code>.
	 * @throws IllegalControllerButtonException
	 *             if the <code>button</code> does not belong to the controller.
	 */
	public final void updateButtonStatus(ControllerButton button, boolean isPressed) throws NullPointerException, IllegalControllerButtonException {
		if (button == null) {
			throw new NullPointerException("Button cannot be null");
		} else if (!buttonStates.containsKey(button)) {
			throw new IllegalControllerButtonException(this, button);
		}
		boolean wasPressed = buttonStates.get(button);
		buttonStates.put(button, isPressed);
		if (wasPressed != isPressed) {
			long currentTime = System.currentTimeMillis();
			buttonHoldTimes.put(button, isPressed ? currentTime : -1L);
			buttonHoldPressTimes.put(button, isPressed ? currentTime : -1L);
		}
		if (isPressed == true && wasPressed == false) {
			Input.callEvent(listener -> listener.onControllerButtonPress(this, button));
		} else if (isPressed == false && wasPressed == true) {
			Input.callEvent(listener -> listener.onControllerButtonRelease(this, button));
		}
	}

	/**
	 * Updates the specified direction.
	 * 
	 * @param direction
	 *            the direction.
	 * @param axisPressed
	 *            <code>true</code>if the axis is pressed, <code>false</code>
	 *            otherwise.
	 * @throws NullPointerException
	 *             if the <code>direction</code> is <code>null</code>.
	 * @throws IllegalGamepadDirectionException
	 *             if the direction does not belong to the controller.
	 */
	private final void updateDirectionalStatus(GamepadDirection direction, boolean axisPressed) throws NullPointerException, IllegalGamepadDirectionException {
		if (direction == null) {
			throw new NullPointerException("Direction cannot be null");
		} else if (!directionStates.containsKey(direction)) {
			throw new IllegalGamepadDirectionException(this, direction);
		}
		boolean isPressed = this.isButtonPressed(directionButtons.get(direction)) || axisPressed == true;
		boolean wasPressed = directionStates.get(direction);
		directionStates.put(direction, isPressed);
		if (wasPressed != isPressed) {
			long currentTime = System.currentTimeMillis();
			directionHoldTimes.put(direction, isPressed ? currentTime : -1L);
			directionHoldPressTimes.put(direction, isPressed ? currentTime : -1L);
		}
		if (isPressed == true && wasPressed == false) {
			Input.callEvent(listener -> listener.onDirectionPress(this, direction));
		} else if (isPressed == false && wasPressed == true) {
			Input.callEvent(listener -> listener.onDirectionRelease(this, direction));
		}
	}

	/**
	 * Updates the controller.
	 */
	public final void update() {
		long currentTime = System.currentTimeMillis();

		// Update directional status
		this.updateDirectionalStatus(GamepadDirection.UP, this.getLeftYAxis() >= DIRECTIONAL_PRESS);
		this.updateDirectionalStatus(GamepadDirection.DOWN, this.getLeftYAxis() <= -DIRECTIONAL_PRESS);
		this.updateDirectionalStatus(GamepadDirection.LEFT, this.getLeftXAxis() <= -DIRECTIONAL_PRESS);
		this.updateDirectionalStatus(GamepadDirection.RIGHT, this.getLeftXAxis() >= DIRECTIONAL_PRESS);

		// Check for buttons that are being held
		for (ControllerButton button : buttonStates.keySet()) {
			if (buttonStates.get(button) && currentTime - buttonHoldTimes.get(button) >= 500L) {
				if (currentTime - buttonHoldPressTimes.get(button) >= 100L) {
					Input.callEvent(listener -> listener.onControllerButtonPress(this, button));
					buttonHoldPressTimes.put(button, currentTime);
				}
			}
		}

		// Check for directions that are being held
		for (GamepadDirection direction : directionStates.keySet()) {
			if (directionStates.get(direction) && currentTime - directionHoldTimes.get(direction) >= 500L) {
				if (currentTime - directionHoldPressTimes.get(direction) >= 100L) {
					Input.callEvent(listener -> listener.onDirectionPress(this, direction));
					directionHoldPressTimes.put(direction, currentTime);
				}
			}
		}

		// Update actions
		for (ControllerAction action : actions.keySet()) {
			boolean isPressed = false;
			for (ControllerButton button : actions.get(action)) {
				if (this.isButtonPressed(button)) {
					isPressed = true;
					break;
				}
			}
			boolean wasPressed = actionStates.get(action);

			// Update action state
			actionStates.put(action, isPressed);
			if (isPressed == true && wasPressed == false) {
				Input.callEvent(listener -> listener.onControllerActionPress(this, action));
			} else if (isPressed == false && wasPressed == true) {
				Input.callEvent(listener -> listener.onControllerActionRelease(this, action));
			}

			// Reset hold status if necessary
			if (wasPressed != isPressed) {
				actionHoldTimes.put(action, isPressed ? currentTime : -1L);
				actionHoldPressTimes.put(action, isPressed ? currentTime : -1L);
			}

			// Check if action is being held
			if (actionStates.get(action) && currentTime - actionHoldTimes.get(action) >= 500L) {
				if (currentTime - actionHoldPressTimes.get(action) >= 100L) {
					Input.callEvent(listener -> listener.onControllerActionPress(this, action));
					actionHoldPressTimes.put(action, currentTime);
				}
			}
		}
	}

	/**
	 * Returns whether or not the left analog stick is pressed upward.
	 * 
	 * @return <code>true</code> if the left analog stick is pressed upward,
	 *         <code>false</code> otherwise.
	 */
	public final boolean isLeftAnalogUp() {
		return this.getLeftYAxis() >= DIRECTIONAL_PRESS;
	}

	/**
	 * Returns whether or not the left analog stick is pressed downward.
	 * 
	 * @return <code>true</code> if the left analog stick is pressed downward,
	 *         <code>false</code> otherwise.
	 */
	public final boolean isLeftAnalogDown() {
		return this.getLeftYAxis() <= -DIRECTIONAL_PRESS;
	}

	/**
	 * Returns whether or not the left analog stick is pressed to the left.
	 * 
	 * @return <code>true</code> if the left analog stick is pressed to the
	 *         left, <code>false</code> otherwise.
	 */
	public final boolean isLeftAnalogLeft() {
		return this.getLeftXAxis() <= -DIRECTIONAL_PRESS;
	}

	/**
	 * Returns whether or not the left analog stick is pressed to the right.
	 * 
	 * @return <code>true</code> if the left analog stick is pressed to the
	 *         right, <code>false</code> otherwise.
	 */
	public final boolean isLeftAnalogRight() {
		return this.getLeftXAxis() >= DIRECTIONAL_PRESS;
	}

	/**
	 * Returns whether or not the right analog stick is pressed upward.
	 * 
	 * @return <code>true</code> if the right analog stick is pressed upward,
	 *         <code>false</code> otherwise.
	 */
	public final boolean isRightAnalogUp() {
		return this.getRightYAxis() >= DIRECTIONAL_PRESS;
	}

	/**
	 * Returns whether or not the right analog stick is pressed downward.
	 * 
	 * @return <code>true</code> if the right analog stick is pressed downward,
	 *         <code>false</code> otherwise.
	 */
	public final boolean isRightAnalogDown() {
		return this.getRightYAxis() <= -DIRECTIONAL_PRESS;
	}

	/**
	 * Returns whether or not the right analog stick is pressed to the left.
	 * 
	 * @return <code>true</code> if the right analog stick is pressed to the
	 *         left, <code>false</code> otherwise.
	 */
	public final boolean isRightAnalogLeft() {
		return this.getRightXAxis() <= -DIRECTIONAL_PRESS;
	}

	/**
	 * Returns whether or not the right analog stick is pressed to the right.
	 * 
	 * @return <code>true</code> if the right analog stick is pressed to the
	 *         right, <code>false</code> otherwise.
	 */
	public final boolean isRightAnalogRight() {
		return this.getRightXAxis() >= DIRECTIONAL_PRESS;
	}

	/**
	 * Returns the left analog X stick axis.
	 * 
	 * @return the left analog X stick axis.
	 */
	public abstract float getLeftXAxis();

	/**
	 * Returns the left analog Y stick axis.
	 * 
	 * @return the left analog Y stick axis.
	 */
	public abstract float getLeftYAxis();

	/**
	 * Returns the right analog X stick axis.
	 * 
	 * @return the right analog X stick axis.
	 */
	public abstract float getRightXAxis();

	/**
	 * Returns the right analog stick Y axis.
	 * 
	 * @return the right analog stick Y axis.
	 */
	public abstract float getRightYAxis();

	/**
	 * Returns the left analog trigger Z axis.
	 * 
	 * @return the left analog trigger Z axis.
	 */
	public abstract float getLeftTriggerZAxis();

	/**
	 * Returns the right analog trigger Z axis.
	 * 
	 * @return the right analog trigger Z axis.
	 */
	public abstract float getRightTriggerZAxis();

	/**
	 * Returns the controller type.
	 * 
	 * @return the controller type.
	 */
	public abstract ControllerType getType();

	@Override
	public final String toString() {
		return "Controller [playerNumber=" + playerNumber + ", leftMotorIntensity=" + leftMotorIntensity + ", rightMotorIntensity=" + rightMotorIntensity + ", isKeyboard()="
				+ isKeyboard() + ", getHandlerClassName()=" + getHandlerClassName() + ", isRumbling()=" + isRumbling() + ", isLeftAnalogUp()=" + isLeftAnalogUp()
				+ ", isLeftAnalogDown()=" + isLeftAnalogDown() + ", isLeftAnalogLeft()=" + isLeftAnalogLeft() + ", isLeftAnalogRight()=" + isLeftAnalogRight()
				+ ", isRightAnalogUp()=" + isRightAnalogUp() + ", isRightAnalogDown()=" + isRightAnalogDown() + ", isRightAnalogLeft()=" + isRightAnalogLeft()
				+ ", isRightAnalogRight()=" + isRightAnalogRight() + ", getLeftXAxis()=" + getLeftXAxis() + ", getLeftYAxis()=" + getLeftYAxis() + ", getRightXAxis()="
				+ getRightXAxis() + ", getRightYAxis()=" + getRightYAxis() + ", getLeftTriggerZAxis()=" + getLeftTriggerZAxis() + ", getRightTriggerZAxis()="
				+ getRightTriggerZAxis() + ", getType()=" + getType() + "]";
	}

}
