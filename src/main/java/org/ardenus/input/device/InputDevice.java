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
package org.ardenus.input.device;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.ardenus.input.action.Action;
import org.ardenus.input.action.ActionBinds;
import org.ardenus.input.action.ActionProfile;
import org.ardenus.input.action.ActionProfiling;
import org.ardenus.input.action.ActionState;
import org.ardenus.input.button.Button;
import org.ardenus.input.button.ButtonPresent;
import org.ardenus.input.button.PressableState;
import org.ardenus.input.device.controller.ControllerButton;

/**
 * An input device, either virtual or physical.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.2-SNAPSHOT
 * @version 0.0.2
 */
public abstract class InputDevice implements ActionBinds, ActionProfiling {

	private final Map<Button, PressableState> buttons;
	private final Map<Action, ActionState> actions;
	private final Map<String, ActionProfile> profiles;

	/**
	 * Constructs a new <code>InputDevice</code>.
	 * <p>
	 * TODO: Mention {@link #grabButtons()}.
	 */
	public InputDevice() {
		this.buttons = new HashMap<Button, PressableState>();
		this.actions = new HashMap<Action, ActionState>();
		this.profiles = new HashMap<String, ActionProfile>();
		this.grabButtons();
	}

	/**
	 * Returns if this input device has a button present.
	 * 
	 * @param button
	 *            the button.
	 * @return <code>true</code> if this input device has <code>button</code>
	 *         present, <code>false</code> otherwise.
	 */
	public boolean hasButton(Button button) {
		return buttons.containsKey(button);
	}

	/**
	 * Checks for the presence of a set of buttons for this input device.
	 * 
	 * @param buttons
	 *            the buttons to check.
	 * @throws NullPointerException
	 *             if one of the values within <code>buttons</code> is
	 *             <code>null</code>.
	 * @throws IllegalArgumentException
	 *             if one of the values within <code>buttons</code> is not
	 *             present for this input device.
	 */
	protected final void checkPresence(Collection<Button> buttons) {
		if (buttons != null) {
			for (Button button : buttons) {
				if (button == null) {
					throw new NullPointerException("button cannot be null");
				} else if (!this.hasButton(button)) {
					throw new IllegalArgumentException("button not present");
				}
			}
		}
	}

	/**
	 * TODO: Document what this does
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

	@Override
	public boolean isBound(Action action) {
		return actions.containsKey(action);
	}

	@Override
	public boolean areBound(Action action, Collection<Button> buttons) {
		ActionState state = actions.get(action);
		if (state != null) {
			return state.buttons.containsAll(buttons);
		}
		return false;
	}

	@Override
	public Set<Button> getBound(Action action) {
		ActionState state = actions.get(action);
		if (state != null) {
			return Collections.unmodifiableSet(state.buttons);
		}
		return null;
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
	 * @throws IllegalArgumentException
	 *             if one of the values within <code>buttons</code> is not
	 *             present for this input device.
	 */
	@Override
	public final void bind(Action action, Collection<Button> buttons) {
		Objects.requireNonNull(action, "action cannot be null");
		Objects.requireNonNull(buttons, "buttons cannot be null");
		if (buttons.size() <= 0) {
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
		for (Button button : buttons) {
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
	 *            the action being unbound.
	 * @param buttons
	 *            the buttons to unbind from <code>action</code>.
	 * @throws NullPointerException
	 *             if <code>action</code>, <code>buttons</code>, or any values
	 *             within <code>buttons</code> are <code>null</code>.
	 * @throws IllegalArgumentException
	 *             if a button is not present for this input device.
	 */
	@Override
	public final void unbind(Action action, Collection<Button> buttons) {
		Objects.requireNonNull(action, "action cannot be null");
		Objects.requireNonNull(buttons, "buttons cannot be null");
		if (!actions.containsKey(action) || buttons.size() <= 0) {
			return; // Action not bound or no buttons
		}
		this.checkPresence(buttons);

		// Unbind buttons from action
		Set<Button> bound = actions.get(action).buttons;
		for (Button button : buttons) {
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
	 *             if <code>action</code> is <code>null</code>.
	 */
	@Override
	public final void unbind(Action action) {
		Objects.requireNonNull(action, "action cannot be null");
		actions.remove(action);
	}

	@Override
	public boolean areAttached(Collection<ActionProfile> profiles) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void attach(ActionProfile profile) {
		// TODO Auto-generated method stub
	}

	@Override
	public void detach(ActionProfile profile) {
		// TODO Auto-generated method stub
	}

}
