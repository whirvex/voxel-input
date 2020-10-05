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
package org.ardenus.engine.input.controller.action;

import java.util.HashMap;

import org.ardenus.engine.input.controller.Controller;
import org.ardenus.engine.input.controller.ControllerButton;
import org.ardenus.engine.input.controller.GameCubeController;
import org.ardenus.engine.input.controller.GamepadDirection;
import org.ardenus.engine.input.controller.keyboard.KeyboardController;
import org.ardenus.engine.input.controller.wireless.PegaController;
import org.ardenus.engine.input.controller.wireless.PlayStationController;
import org.ardenus.engine.input.controller.wireless.SwitchController;
import org.ardenus.engine.input.controller.wireless.XboxController;

/**
 * A group of actions that must be completed in a sequence.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.1-SNAPSHOT
 */
public class ControllerActionSequence {

	/**
	 * The A button.
	 */
	public static final ControllerAction BUTTON_A = new ControllerAction("A");

	/**
	 * The B button.
	 */
	public static final ControllerAction BUTTON_B = new ControllerAction("B");

	/**
	 * The Konami code profile necessary for the {@link #KONAMI_CODE} sequence
	 * to be completed by the built in controllers.
	 */
	public static final ControllerActionProfile KONAMI_CODE_PROFILE = new ControllerActionProfile("konami_code");

	// Bind Konami code profile actions
	static {
		KONAMI_CODE_PROFILE.bind(BUTTON_A, KeyboardController.KEY_A, XboxController.BUTTON_A, PlayStationController.BUTTON_CROSS, SwitchController.BUTTON_A,
				PegaController.BUTTON_A, GameCubeController.BUTTON_A);
		KONAMI_CODE_PROFILE.bind(BUTTON_B, KeyboardController.KEY_B, XboxController.BUTTON_B, PlayStationController.BUTTON_TRIANGLE, SwitchController.BUTTON_B,
				PegaController.BUTTON_B, GameCubeController.BUTTON_B);
	}

	/**
	 * The Konami code.
	 */
	public static final ControllerActionSequence KONAMI_CODE = new ControllerActionSequence(GamepadDirection.UP.getAction(), GamepadDirection.UP.getAction(),
			GamepadDirection.DOWN.getAction(), GamepadDirection.DOWN.getAction(), GamepadDirection.LEFT.getAction(), GamepadDirection.RIGHT.getAction(),
			GamepadDirection.LEFT.getAction(), GamepadDirection.RIGHT.getAction(), BUTTON_B, BUTTON_A, BUTTON_B, BUTTON_A);

	private final ControllerAction[] actions;
	private final HashMap<Integer, Integer> indexes;
	private final HashMap<Integer, Integer> completions;

	/**
	 * Creates a controller action sequence.
	 * 
	 * @param actions
	 *            the actions that must be completed. These actions must be done
	 *            in the order that they are specified here.
	 * @throws NullPointerException
	 *             if the <code>actions</code> or one of the actions are
	 *             <code>null</code>.
	 * @throws IllegalArgumentException
	 *             if no actions are specified.
	 */
	public ControllerActionSequence(ControllerAction... actions) throws NullPointerException, IllegalArgumentException {
		if (actions == null) {
			throw new NullPointerException("Actions cannot be null");
		} else if (actions.length <= 0) {
			throw new IllegalArgumentException("At least one action must be specified");
		}
		for (ControllerAction action : actions) {
			if (action == null) {
				throw new NullPointerException("Action cannot be null");
			}
		}
		this.actions = actions;
		this.indexes = new HashMap<Integer, Integer>();
		this.completions = new HashMap<Integer, Integer>();
	}

	/**
	 * Returns the actions that must be pressed in the order that they must be
	 * pressed.
	 * 
	 * @return the actions that must be pressed in the order that they must be
	 *         pressed.
	 */
	public ControllerAction[] getActions() {
		return this.actions;
	}

	/**
	 * Returns the next action the specified player must press in order to
	 * progress the sequence.
	 * 
	 * @param player
	 *            the player slot.
	 * @return the next action the specified player must press in order to
	 *         progress the sequence, <code>null</code> if the
	 *         <code>player</code> slot is negative or the sequence has been
	 *         completed.
	 */
	public ControllerAction getNextAction(int player) {
		int index = this.getIndex(player);
		if (index >= 0 && index < actions.length) {
			return this.actions[index];
		}
		return null;
	}

	/**
	 * Returns the next action the player of the specified controller must press
	 * in order to progress the sequence.
	 * 
	 * @param controller
	 *            the controller.
	 * @return the next action the player of the specified controller must press
	 *         in order to progress the sequence, <code>null</code> if the
	 *         <code>controller</code> is <code>null</code> or the sequence has
	 *         been completed.
	 */
	public ControllerAction getNextAction(Controller controller) {
		if (controller == null) {
			return null;
		}
		return this.getNextAction(controller.getPlayerNumber());
	}

	/**
	 * Returns the current index of the specified player.
	 * 
	 * @param player
	 *            the player slot.
	 * @return the current index of the specified player, <code>-1</code> if the
	 *         <code>player</code> slot is negative.
	 */
	public int getIndex(int player) {
		if (player < 0) {
			return -1;
		} else if (!indexes.containsKey(player)) {
			indexes.put(player, 0);
		}
		return indexes.get(player);
	}

	/**
	 * Returns the current index of the player of the specified controller.
	 * 
	 * @param controller
	 *            the controller.
	 * @return the current index of the player of the specified controller,
	 *         <code>-1</code> if the <code>controller</code> is
	 *         <code>null</code>.
	 */
	public int getIndex(Controller controller) {
		if (controller == null) {
			return -1;
		}
		return this.getIndex(controller.getPlayerNumber());
	}

	/**
	 * Updates the current index of the specified player.
	 * 
	 * @param player
	 *            the player slot.
	 * @param autoReset
	 *            <code>true</code> if the sequence should be automatically
	 *            reset for the specified player if the sequence has been
	 *            completed, <code>false</code> otherwise.
	 * @return <code>true</code> if the sequence has been completed by the
	 *         specified player, <code>false</code> otherwise.
	 * @throws IndexOutOfBoundsException
	 *             if the <code>player</code> slot is negative.
	 */
	private boolean updateIndex(int player, boolean autoReset) throws IndexOutOfBoundsException {
		if (player < 0) {
			throw new IndexOutOfBoundsException("Player slot cannot be negative");
		}
		int index = this.getIndex(player);
		indexes.put(player, index + 1);
		if (index + 1 >= actions.length) {
			if (autoReset == true) {
				this.reset(player);
			}
			this.bumpCompletions(player);
			return true;
		}
		return false;
	}

	/**
	 * Returns whether or not the specified player has completed the sequence.
	 * 
	 * @param player
	 *            the player slot.
	 * @return <code>true</code> if the specified player has completed the
	 *         sequence, <code>false</code> otherwise.
	 */
	public boolean isCompleted(int player) {
		return this.getIndex(player) >= actions.length;
	}

	/**
	 * Returns whether or not the player of the specified controller has
	 * completed the sequence.
	 * 
	 * @param controller
	 *            the controller.
	 * @return <code>true</code> if the player of the specified controller has
	 *         completed the sequence, <code>false</code> otherwise.
	 */
	public boolean isCompleted(Controller controller) {
		if (controller == null) {
			return false;
		}
		return this.isCompleted(controller.getPlayerNumber());
	}

	/**
	 * Returns the amount of times the specified player has completed the
	 * sequence.
	 * 
	 * @param player
	 *            the player slot.
	 * @return the amount of times the specified player has completed the
	 *         sequence, <code>-1</code> if the <code>player</code> slot is
	 *         negative.
	 */
	public int getCompletions(int player) {
		if (player < 0) {
			return -1;
		} else if (!completions.containsKey(player)) {
			completions.put(player, 0);
		}
		return completions.get(player);
	}

	/**
	 * Returns the amount of times the player of the specified controller has
	 * completed the sequence.
	 * 
	 * @param controller
	 *            the controller.
	 * @return the amount of times the player of the specified controller has
	 *         completed the sequence, <code>-1</code> if the
	 *         <code>controller</code> is <code>null</code>.
	 */
	public int getCompletions(Controller controller) {
		if (controller == null) {
			return -1;
		}
		return this.getCompletions(controller.getPlayerNumber());
	}

	/**
	 * Bumps the amount of times the specified player has completed the sequence
	 * by one.
	 * 
	 * @param player
	 *            the player slot.
	 * @throws IndexOutOfBoundsException
	 *             if the <code>player</code> slot is negative.
	 */
	private void bumpCompletions(int player) throws IndexOutOfBoundsException {
		if (player < 0) {
			throw new IndexOutOfBoundsException("Player slot cannot be negative");
		}
		int count = this.getCompletions(player);
		completions.put(player, count + 1);
	}

	/**
	 * Updates the sequence for the player of the specified controller with the
	 * given button.
	 * 
	 * @param controller
	 *            the controller.
	 * @param button
	 *            the button.
	 * @param autoReset
	 *            <code>true</code> if the sequence should be automatically
	 *            reset for the player of the specified controller if the
	 *            sequence has been completed, <code>false</code> otherwise.
	 * @return <code>true</code> if the sequence has been completed by the
	 *         player of the specified controller, <code>false</code> otherwise.
	 * @throws NullPointerException
	 *             if the <code>controller</code> or <code>button</code> are
	 *             <code>null</code>.
	 */
	public boolean update(Controller controller, ControllerButton button, boolean autoReset) throws NullPointerException {
		if (controller == null) {
			throw new IndexOutOfBoundsException("Controller cannot be null");
		} else if (button == null) {
			throw new NullPointerException("Button cannot be null");
		} else if (!this.isCompleted(controller)) {
			if (controller.isBound(this.getNextAction(controller), button)) {
				return this.updateIndex(controller.getPlayerNumber(), autoReset);
			}
			this.reset(controller);
		}
		return false;
	}

	/**
	 * Updates the sequence for the player of the specified controller with the
	 * given button.
	 * 
	 * @param controller
	 *            the controller.
	 * @param button
	 *            the button.
	 * @return <code>true</code> if the sequence has been completed by the
	 *         player of the specified controller, <code>false</code> otherwise.
	 * @throws NullPointerException
	 *             if the <code>controller</code> or <code>button</code> are
	 *             <code>null</code>.
	 */
	public boolean update(Controller controller, ControllerButton button) throws NullPointerException {
		return this.update(controller, button, false);
	}

	/**
	 * Updates the sequence for the player of the specified controller with the
	 * given direction.
	 * 
	 * @param controller
	 *            the controller.
	 * @param direction
	 *            the direction.
	 * @param autoReset
	 *            <code>true</code> if the sequence should be automatically
	 *            reset for the player of the specified controller if the
	 *            sequence has been completed, <code>false</code> otherwise.
	 * @return <code>true</code> if the sequence has been completed by the
	 *         player of the specified controller, <code>false</code> otherwise.
	 * @throws NullPointerException
	 *             if the <code>controller</code> or <code>direction</code> are
	 *             <code>null</code>.
	 */
	public boolean update(Controller controller, GamepadDirection direction, boolean autoReset) throws NullPointerException {
		if (controller == null) {
			throw new NullPointerException("Controller cannot be null");
		} else if (direction == null) {
			throw new NullPointerException("Direction cannot be null");
		} else if (!this.isCompleted(controller)) {
			if (direction.getAction().equals(this.getNextAction(controller))) {
				return this.updateIndex(controller.getPlayerNumber(), autoReset);
			}
			this.reset(controller);
		}
		return false;
	}

	/**
	 * Updates the sequence for the player of the specified controller with the
	 * given direction.
	 * 
	 * @param controller
	 *            the controller.
	 * @param direction
	 *            the direction.
	 * @return <code>true</code> if the sequence has been completed by the
	 *         player of the specified controller, <code>false</code> otherwise.
	 * @throws NullPointerException
	 *             if the <code>controller</code> or <code>direction</code> are
	 *             <code>null</code>.
	 */
	public boolean update(Controller controller, GamepadDirection direction) throws NullPointerException {
		return this.update(controller, direction, false);
	}

	/**
	 * Resets the sequence for the specified player, allowing the sequence to be
	 * completed again or restarted.
	 * 
	 * @param player
	 *            the player slot.
	 * @throws IndexOutOfBoundsException
	 *             if the <code>player</code> slot is negative.
	 */
	public void reset(int player) throws IndexOutOfBoundsException {
		if (player < 0) {
			throw new IndexOutOfBoundsException("Player slot cannot be negative");
		}
		indexes.put(player, 0);
	}

	/**
	 * Resets the sequence for the player of the specified controller, allowing
	 * the sequence to be completed again or restarted.
	 * 
	 * @param controller
	 *            the controller.
	 * @throws NullPointerException
	 *             if the <code>controller</code> is <code>null</code>.
	 */
	public void reset(Controller controller) throws NullPointerException {
		if (controller == null) {
			throw new NullPointerException("Controller cannot be null");
		}
		this.reset(controller.getPlayerNumber());
	}

}
