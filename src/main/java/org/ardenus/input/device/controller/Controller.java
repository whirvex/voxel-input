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
package org.ardenus.input.device.controller;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.ardenus.input.action.Action;
import org.ardenus.input.action.ActionBinds;
import org.ardenus.input.action.ActionProfile;
import org.ardenus.input.action.ActionState;
import org.ardenus.input.button.Button;
import org.ardenus.input.button.PressableState;
import org.ardenus.input.device.InputDevice;

/**
 * Represents a game controller input device.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.2-SNAPSHOT
 * @version 0.0.2
 */
public abstract class Controller implements InputDevice, ActionBinds {

	public static final int ANALOG_LEFT = 0, ANALOG_RIGHT = 1,
			ANALOG_TRIGGER_LEFT = 2, ANALOG_TRIGGER_RIGHT = 3;

	private final Map<ControllerButton, PressableState> buttons;
	private final Map<Action, ActionState> actions;
	private final Map<String, ActionProfile> profiles;

	/**
	 * Constructs a new <code>Controller</code>.
	 */
	public Controller() {
		this.buttons = new HashMap<ControllerButton, PressableState>();
		this.actions = new HashMap<Action, ActionState>();
		this.profiles = new HashMap<String, ActionProfile>();
		this.grabButtons();
	}
	
	public static void main(String[] args) {
		HashMap<String, ActionProfile> p = new HashMap<String, ActionProfile>();
		ActionProfile a = new ActionProfile("a");
		ActionProfile b = new ActionProfile("b");
		ActionProfile c = new ActionProfile("c");
		p.put("a", a);
		p.put("b", b);
		p.put("c", c);
		
		ControllerButton cb1 = new ControllerButton("cb", Controller.class) {
		};
	}

	/**
	 * Returns if this controller has a button present.
	 * 
	 * @param button
	 *            the button.
	 * @return <code>true</code> if this controller has <code>button</code>
	 *         present, <code>false</code> otherwise.
	 */
	public final boolean hasButton(ControllerButton button) {
		return buttons.containsKey(button);
	}

	/**
	 * Checks for the presence of a set of buttons for this controller.
	 * 
	 * @param buttons
	 *            the buttons to check.
	 * @throws NullPointerException
	 *             if one of the values within <code>buttons</code> is
	 *             <code>null</code>.
	 * @throws IllegalArgumentException
	 *             if one of the values within <code>buttons</code> is not
	 *             present for this controller.
	 */
	private final void checkPresence(ControllerButton... buttons) {
		if (buttons != null) {
			for (ControllerButton button : buttons) {
				if (button == null) {
					throw new NullPointerException("button cannot be null");
				} else if (!this.hasButton(button)) {
					throw new IllegalArgumentException(
							"button not present for this controller");
				}
			}
		}
	}

	/**
	 * 
	 */
	private final void grabButtons() {
		if (!buttons.isEmpty()) {
			throw new IllegalStateException("buttons already grabbed");
		}
		for (Field field : this.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(ButtonPresent.class)) {
				int modifiers = field.getModifiers();
				if (!Modifier.isStatic(modifiers)) {
					throw new RuntimeException(""); // TODO
				}

				// Instantiate and store button
				try {
					buttons.put((ControllerButton) field.get(null),
							new PressableState());
				} catch (IllegalAccessException e) {
					throw new RuntimeException(""); // TODO
				}
			}
		}
	}

	/**
	 * Binds a set of buttons to an action.
	 * 
	 * @param action
	 *            the action to bind to.
	 * @param buttons
	 *            the buttons to bind to <code>action</code>.
	 * @throws NullPointerException
	 *             if <code>action</code>, <code>buttons</code>, or any values
	 *             within <code>buttons</code> are <code>null</code>.
	 */
	public final void bind(Action action, ControllerButton... buttons) {
		Objects.requireNonNull(action, "action cannot be null");
		Objects.requireNonNull(buttons, "buttons cannot be null");
		if (buttons.length <= 0) {
			return; // No buttons to bind
		}
		this.checkPresence(buttons);

		// Instantiate action if needed
		ActionState state = actions.get(action);
		if (state == null) {
			state = new ActionState();
			actions.put(action, state);
		}

		// Bind buttons
		Set<Button> bound = state.buttons;
		for (ControllerButton button : buttons) {
			bound.add(button);
		}
	}

	/**
	 * Unbinds a set of buttons from an action.
	 * <p>
	 * If no more buttons are bound to the action as a result of calling this
	 * method, the action will be considered unbound.
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
	public void unbind(Action action, ControllerButton... buttons) {
		Objects.requireNonNull(action, "action cannot be null");
		Objects.requireNonNull(buttons, "buttons cannot be null");
		if (!actions.containsKey(action) || buttons.length <= 0) {
			return; // Action not bound or no buttons
		}
		this.checkPresence(buttons);

		// Unbind buttons from action
		Set<Button> bound = actions.get(action).buttons;
		for (ControllerButton button : buttons) {
			bound.remove(button);
		}
		if (bound.isEmpty()) {
			this.unbind(action);
		}
	}

	/**
	 * Unbinds an action entirely.
	 * 
	 * @param action
	 *            the action.
	 * @throws NullPointerException
	 *             if the <code>action</code> is <code>null</code>.
	 */
	public void unbind(Action action) throws NullPointerException {
		if (action == null) {
			throw new NullPointerException("Action cannot be null");
		}
		actions.remove(action);
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
		// TODO: Odd, this looks like it was copy pasted from the
		// ActionProfile class? Gotta fix this implementation later.
		// controllers.add(controller);
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
		// TODO: Odd, this looks like it was copy pasted from the
		// ActionProfile class? Gotta fix this implementation later.
		// controllers.remove(controller);
	}

	@Override
	public boolean isBound(Action action) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean areBound(Action action, Button... buttons) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<Button> getBound(Action action) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void bind(Action action, Button... buttons) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unbind(Action action, Button... buttons) {
		// TODO Auto-generated method stub
		
	}

}
