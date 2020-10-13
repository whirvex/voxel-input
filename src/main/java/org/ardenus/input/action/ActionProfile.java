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

import org.ardenus.input.button.Button;

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
public class ActionProfile implements ActionBinds {

	/**
	 * The profile ID.
	 */
	public final String id;

	private final Map<Action, Set<Button>> actions;

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
		this.actions = new HashMap<Action, Set<Button>>();
	}

	@Override
	public final boolean isBound(Action action) {
		return actions.containsKey(action);
	}

	@Override
	public final boolean areBound(Action action, Button... buttons) {
		if (!actions.containsKey(action) || buttons == null) {
			return false; // Action not bound, or no buttons
		}
		Set<Button> bound = actions.get(action);
		return bound.containsAll(Arrays.asList(buttons));
	}

	@Override
	public final Set<Button> getBound(Action action) {
		Set<Button> bound = actions.get(action);
		return bound != null ? Collections.unmodifiableSet(bound) : null;
	}

	@Override
	public void bind(Action action, Button... buttons) {
		Objects.requireNonNull(action, "action cannot be null");
		Objects.requireNonNull(buttons, "buttons cannot be null");

		// Register action to internal map
		Set<Button> bound = actions.get(action);
		if (bound == null) {
			bound = new HashSet<Button>();
			actions.put(action, bound);
		}

		// Bind buttons to action
		for (Button button : buttons) {
			Objects.requireNonNull(button, "button cannot be null");
			bound.add(button);
		}
	}

	@Override
	public void unbind(Action action, Button... buttons) {
		if (!actions.containsKey(action) || buttons == null) {
			return; // Nothing to unbind
		}

		// Unbind buttons from action
		Set<Button> bound = actions.get(action);
		bound.removeAll(Arrays.asList(buttons));
		if (bound.isEmpty()) {
			actions.remove(action);
		}
	}

	@Override
	public void unbind(Action action) {
		actions.remove(action);
	}

}
