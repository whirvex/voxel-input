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
package org.ardenus.input.controller.action;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ardenus.input.controller.Controller;
import org.ardenus.input.controller.ControllerButton;

/**
 * A set of actions that can be applied to any controller dynamically and
 * updated in real time.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.1-SNAPSHOT
 */
public class ControllerActionProfile {

	private final Logger logger;
	private final String id;
	private final HashMap<ControllerAction, ControllerButton[]> actions;
	private final ArrayList<Controller> controllers;

	/**
	 * Creates a controller action profile.
	 * 
	 * @param id
	 *            the ID of the profile.
	 * @throws NullPointerException
	 *             if the <code>id</code> is <code>null</code>.
	 */
	public ControllerActionProfile(String id) throws NullPointerException {
		if (id == null) {
			throw new NullPointerException("ID cannot be null");
		}
		this.logger = LogManager.getLogger(ControllerActionProfile.class.getSimpleName() + ":" + id);
		this.id = id;
		this.actions = new HashMap<ControllerAction, ControllerButton[]>();
		this.controllers = new ArrayList<Controller>();
	}

	/**
	 * Returns the ID of the profile.
	 * 
	 * @return the ID of the profile.
	 */
	public String getId() {
		return this.id;
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
	 * Binds the specified action to the given buttons.
	 * <p>
	 * All currently attached controllers will also be updated.
	 * 
	 * @param action
	 *            the action.
	 * @param buttons
	 *            the buttons that trigger the action.
	 * @throws NullPointerException
	 *             if the <code>action</code>, <code>buttons</code>, or one of
	 *             the buttons are <code>null</code>.
	 */
	public void bind(ControllerAction action, ControllerButton... buttons) throws NullPointerException {
		if (action == null) {
			throw new NullPointerException("Action cannot be null");
		} else if (buttons == null) {
			throw new NullPointerException("Buttons cannot be null");
		}

		// Get old buttons and add new buttons
		ArrayList<ControllerButton> actionButtonList = new ArrayList<ControllerButton>();
		if (actions.containsKey(action)) {
			for (ControllerButton button : actions.get(action)) {
				actionButtonList.add(button);
			}
		}
		for (ControllerButton button : buttons) {
			if (button == null) {
				throw new NullPointerException("Button cannot be null");
			} else if (!actionButtonList.contains(button)) {
				actionButtonList.add(button);
			}
		}

		// Update action and controllers
		actions.put(action, actionButtonList.toArray(new ControllerButton[actionButtonList.size()]));
		for (Controller controller : controllers) {
			ArrayList<ControllerButton> buttonList = new ArrayList<ControllerButton>();
			for (ControllerButton button : actions.get(action)) {
				if (button.getControllerClass().isAssignableFrom(controller.getClass())) {
					buttonList.add(button);
				}
			}
			controller.bind(action, buttonList.toArray(new ControllerButton[buttonList.size()]));
		}
		logger.debug("Bound action " + action.getName() + " (" + action.getId() + ") to " + actionButtonList.size() + " buttons ");
	}

	/**
	 * Unbinds the specified action from the given buttons.
	 * <p>
	 * All currently attached controllers will also be updated.
	 * 
	 * @param action
	 *            the action.
	 * @param buttons
	 *            the buttons that trigger the action.
	 * @throws NullPointerException
	 *             if the <code>buttons</code> are <code>null</code>.
	 */
	public void unbind(ControllerAction action, ControllerButton... buttons) throws NullPointerException {
		if (buttons == null) {
			throw new NullPointerException("Buttons cannot be null");
		} else if (actions.containsKey(action)) {
			// Determine which buttons to remove
			ArrayList<ControllerButton> actionButtonList = new ArrayList<ControllerButton>();
			for (ControllerButton button : actions.get(action)) {
				if (!ArrayUtils.contains(buttons, button)) {
					actionButtonList.add(button);
				}
			}

			// Update action and controllers
			if (actionButtonList.size() > 0) {
				actions.put(action, actionButtonList.toArray(new ControllerButton[actionButtonList.size()]));
				for (Controller controller : controllers) {
					ArrayList<ControllerButton> buttonList = new ArrayList<ControllerButton>();
					for (ControllerButton button : actions.get(action)) {
						if (button.getControllerClass().isAssignableFrom(controller.getClass())) {
							buttonList.add(button);
						}
					}
					controller.unbind(action, buttonList.toArray(new ControllerButton[buttonList.size()]));
				}
				logger.debug("Unbound action " + action.getName() + " ( " + action.getId() + ") from " + actionButtonList.size() + "buttons");
			} else {
				actions.remove(action);
				for (Controller controller : controllers) {
					controller.unbind(action);
				}
				logger.debug("Unbound action " + action.getName() + " (" + action.getId() + ") from profile");
			}
		}
	}

	/**
	 * Unbinds the specified action from the profile entirely.
	 * 
	 * @param action
	 *            the action.
	 */
	public void unbind(ControllerAction action) {
		if (actions.containsKey(action)) {
			this.unbind(action, actions.get(action));
		}
	}

	/**
	 * Attaches the specified controller to the profile.
	 * <p>
	 * All current actions will be bound to the controller upon attachment.
	 * 
	 * @param controller
	 *            the controller to attach.
	 * @throws NullPointerException
	 *             if the <code>controller</code> is <code>null</code>.
	 */
	public void attach(Controller controller) {
		if (controller == null) {
			throw new NullPointerException("Controller cannot be null");
		}
		if (!controllers.contains(controller)) {
			controllers.add(controller);
			for (ControllerAction action : actions.keySet()) {
				ArrayList<ControllerButton> buttonList = new ArrayList<ControllerButton>();
				for (ControllerButton button : actions.get(action)) {
					if (button.getControllerClass().isAssignableFrom(controller.getClass())) {
						buttonList.add(button);
					}
				}
				controller.bind(action, buttonList.toArray(new ControllerButton[buttonList.size()]));
			}
			logger.debug("Attached controller to profile");
		}
	}

	/**
	 * Detaches the specified controller from the profile.
	 * <p>
	 * All current actions will be unbound from the controller upon detachment.
	 * 
	 * @param controller
	 *            the controller to detach.
	 */
	public void detach(Controller controller) {
		if (controllers.contains(controller)) {
			controllers.remove(controller);
			for (ControllerAction action : actions.keySet()) {
				ArrayList<ControllerButton> buttonList = new ArrayList<ControllerButton>();
				for (ControllerButton button : actions.get(action)) {
					if (button.getControllerClass().isAssignableFrom(controller.getClass())) {
						buttonList.add(button);
					}
				}
				controller.unbind(action, buttonList.toArray(new ControllerButton[buttonList.size()]));
			}
			logger.debug("Detached controller from profile");
		}
	}

	@Override
	public String toString() {
		return "ControllerActionProfile [id=" + id + "]";
	}

}
