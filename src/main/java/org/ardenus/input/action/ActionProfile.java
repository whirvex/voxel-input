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
package org.ardenus.input.action;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.ardenus.input.device.controller.Controller;
import org.ardenus.input.device.controller.ControllerButton;

/**
 * A group of actions that can be applied to any controller, and dynamically
 * updated in real time. Action profiles account for buttons that are not
 * present on different controllers, and binds them to actions as such
 * appropriately.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.2-SNAPSHOT
 * @version 0.0.2
 */
public class ActionProfile {

	/**
	 * The profile ID.
	 */
	public final String id;

	private final Map<Action, Set<ControllerButton>> actions;
	private final Set<Controller> controllers;

	/**
	 * Constructs a new <code>ActionProfile</code>.
	 * 
	 * @param id
	 *            the profile ID.
	 * @throws NullPointerException
	 *             if <code>id</code> is <code>null</code>.
	 */
	public ActionProfile(String id) {
		this.id = Objects.requireNonNull(id, "id cannot be null");
		this.actions = new HashMap<Action, Set<ControllerButton>>();
		this.controllers = new HashSet<Controller>();
	}

	/**
	 * Updates the bindings for an action in this profile for a controller
	 * depending on the controller type and whether or not it is attached.
	 * 
	 * @param action
	 *            the action to update bindings for.
	 * @param controller
	 *            the controller to update.
	 * @throws NullPointerException
	 *             if <code>controller</code> or <code>action</code> are
	 *             <code>null</code>.
	 */
	private void updateBinds(Controller controller, Action action) {
		Objects.requireNonNull(controller, "controller cannot be null");
		Objects.requireNonNull(action, "action cannot be null");
		if (controllers.contains(controller)) {
			for (ControllerButton button : actions.get(action)) {
				if (controller.hasButton(button)) {
					controller.bind(action, button);
				}
			}
		} else {
			for (ControllerButton button : actions.get(action)) {
				if (controller.hasButton(button)) {
					controller.unbind(action, button);
				}
			}
		}
	}

	/**
	 * Updates the bindings for all controllers currently attached to this
	 * profile for an action.
	 * 
	 * @param action
	 *            the action to update bindings for.
	 * @throws NullPointerException
	 *             if <code>action</code> is <code>null</code>.
	 */
	private void updateBinds(Action action) {
		for (Controller controller : controllers) {
			this.updateBinds(controller, action);
		}
	}

	/**
	 * Updates the bindings for a controller by having this profile's actions
	 * bound/unbound depending on the controller type and whether or not it is
	 * attached.
	 * 
	 * @param controller
	 *            the controller to update.
	 * @throws NullPointerException
	 *             if <code>controller</code> is <code>null</code>.
	 */
	private void updateBinds(Controller controller) {
		for (Action action : actions.keySet()) {
			this.updateBinds(controller, action);
		}
	}

	/**
	 * Returns if an action is bound to any button within this profile.
	 * 
	 * @param action
	 *            the action.
	 * @return <code>true</code> if <code>action</code> is bound to any button
	 *         within this profile, <code>false</code> otherwise.
	 */
	public final boolean isBound(Action action) {
		return actions.containsKey(action);
	}

	/**
	 * Returns if a button is bound to an action within this profile.
	 * <p>
	 * This function is a shorthand for
	 * {@link #areBound(Action, ControllerButton...)} with <code>button</code>
	 * being the only value in the array of the second parameter. It is mainly
	 * for grammar nuts like Whirvis.
	 * 
	 * @param action
	 *            the action.
	 * @param button
	 *            the button.
	 * @return <code>true</code> if <code>button</code> is bound to
	 *         <code>action</code> for this profile, <code>false</code>
	 *         otherwise.
	 */
	public final boolean isBound(Action action, ControllerButton button) {
		return this.areBound(action, button);
	}

	/**
	 * Returns if a set of buttons are bound to an action within this profile.
	 * 
	 * @param action
	 *            the action.
	 * @param buttons
	 *            the buttons.
	 * @return <code>true</code> if all <code>buttons</code> are bound to
	 *         <code>action</code> for this profile, <code>false</code>
	 *         otherwise.
	 */
	public final boolean areBound(Action action, ControllerButton... buttons) {
		if (!actions.containsKey(action) || buttons == null) {
			return false; // Action unbound or no buttons
		}
		Set<ControllerButton> bound = actions.get(action);
		return bound.containsAll(Arrays.asList(buttons));
	}

	/**
	 * Returns all buttons that can trigger an action bound within this profile.
	 * 
	 * @param action
	 *            the action.
	 * @return an unmodifiable <code>Set</code> of the buttons which can trigger
	 *         <code>action</code>, <code>null</code> if no buttons are bound
	 *         for <code>action</code>.
	 */
	public final Set<ControllerButton> getBound(Action action) {
		Set<ControllerButton> bound = actions.get(action);
		return bound != null ? Collections.unmodifiableSet(bound) : null;
	}

	/**
	 * Binds a set of buttons to an action.
	 * <p>
	 * Any previously bound buttons will stay bound to the action. To unbind a
	 * button from an action, use {@link #unbind(Action, ControllerButton...)}.
	 * Any currently attached controllers will have their binds updated
	 * automatically. Controllers attached in the future will also reflect these
	 * changes.
	 * 
	 * @param action
	 *            the action to bind these buttons to.
	 * @param buttons
	 *            the buttons which will trigger <code>action</code>.
	 * @throws NullPointerException
	 *             if <code>action</code>, <code>buttons</code>, or a value
	 *             inside of <code>buttons</code> are <code>null</code>.
	 */
	public void bind(Action action, ControllerButton... buttons) {
		Objects.requireNonNull(action, "action cannot be null");
		Objects.requireNonNull(buttons, "buttons cannot be null");

		// Register action to internal map
		Set<ControllerButton> bound = actions.get(action);
		if (bound == null) {
			bound = new HashSet<ControllerButton>();
			actions.put(action, bound);
		}

		// Bind buttons to action
		for (ControllerButton button : buttons) {
			Objects.requireNonNull(button, "button cannot be null");
			bound.add(button);
		}
		this.updateBinds(action);
	}

	/**
	 * Unbinds a set of buttons from an action.
	 * <p>
	 * Any currently attached controllers will have their binds updated
	 * automatically.
	 * 
	 * @param action
	 *            the action to unbind these buttons from.
	 * @param buttons
	 *            the buttons to unbind from <code>action</code>.
	 * @see #bind(Action, ControllerButton...)
	 */
	public void unbind(Action action, ControllerButton... buttons) {
		if (!actions.containsKey(action) || buttons == null) {
			return; // Nothing to unbind
		}

		// Unbind buttons from action
		Set<ControllerButton> bound = actions.get(action);
		bound.removeAll(Arrays.asList(buttons));
		if (bound.isEmpty()) {
			actions.remove(action);
		}
		this.updateBinds(action);
	}

	/**
	 * Unbinds an action from the profile entirely.
	 * <p>
	 * Any currently attached controllers will have their binds updated
	 * automatically.
	 * 
	 * @param action
	 *            the action to unbind.
	 */
	public void unbind(Action action) {
		if (actions.remove(action) != null) {
			this.updateBinds(action);
		}
	}

	/**
	 * Attach a controller to the profile.
	 * <p>
	 * All current actions will be bound to this controller upon attachment.
	 * Furthermore, any future changes to this profile will be reflected
	 * automatically until it is detached via {@link #detach(Controller)}.
	 * 
	 * @param controller
	 *            the controller to attach.
	 * @throws NullPointerException
	 *             if <code>controller</code> is <code>null</code>.
	 */
	public void attach(Controller controller) {
		Objects.requireNonNull(controller, "controller cannot be null");
		if (controllers.add(controller)) {
			this.updateBinds(controller);
		}
	}

	/**
	 * Detaches a controller from the profile.
	 * <p>
	 * All current actions bound to this controller will be unbound
	 * automatically. Furthermore, any future changes to this profile will
	 * <i>not</i> be reflected unless it is re-attached via
	 * {@link #attach(Controller)}.
	 * 
	 * @param controller
	 *            the controller to detach.
	 */
	public void detach(Controller controller) {
		if (controllers.remove(controller)) {
			this.updateBinds(controller);
		}
	}

}
