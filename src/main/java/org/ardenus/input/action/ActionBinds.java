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

import java.util.Set;

import org.ardenus.input.button.Button;
import org.ardenus.input.device.controller.ControllerButton;

/**
 * Indicates that a class can bind a {@link Button} to a {@link Action}.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.2-SNAPSHOT
 * @version 0.0.2
 */
public interface ActionBinds {

	/**
	 * Returns if an action is bound to any buttons.
	 * 
	 * @param action
	 *            the action.
	 * @return <code>true</code> if <code>action</code> is bound to any buttons,
	 *         <code>false</code> otherwise.
	 */
	public boolean isBound(Action action);

	/**
	 * Returns if a button is bound to an action.
	 * <p>
	 * This function is a shorthand for {@link #areBound(Action, Button...)}
	 * with <code>button</code> being the only value in the array of the second
	 * parameter. It is mainly for grammar nuts like Whirvis.
	 * 
	 * @param action
	 *            the action.
	 * @param button
	 *            the button.
	 * @return <code>true</code> if <code>button</code> is bound to
	 *         <code>action</code>, <code>false</code> otherwise.
	 */
	public default boolean isBound(Action action, Button button) {
		return this.areBound(action, button);
	}

	/**
	 * Returns if a set of buttons are bound to an action.
	 * 
	 * @param action
	 *            the action.
	 * @param buttons
	 *            the buttons.
	 * @return <code>true</code> if all <code>buttons</code> are bound to
	 *         <code>action</code>, <code>false</code> otherwise.
	 */
	public boolean areBound(Action action, Button... buttons);

	/**
	 * Returns all buttons that can trigger an action.
	 * 
	 * @param action
	 *            the action.
	 * @return an unmodifiable <code>Set</code> of the buttons which can trigger
	 *         <code>action</code>, <code>null</code> if no buttons are bound
	 *         for <code>action</code>.
	 */
	public Set<Button> getBound(Action action);

	/**
	 * Binds a set of buttons to an action.
	 * <p>
	 * Any previously bound buttons will stay bound to the action. To unbind a
	 * button from an action, use {@link #unbind(Action, ControllerButton...)}.
	 * 
	 * @param action
	 *            the action to bind these buttons to.
	 * @param buttons
	 *            the buttons which will trigger <code>action</code>.
	 * @throws NullPointerException
	 *             if <code>action</code>, <code>buttons</code>, or a value
	 *             inside of <code>buttons</code> are <code>null</code>.
	 */
	public void bind(Action action, Button... buttons);

	/**
	 * Unbinds a set of buttons from an action.
	 * 
	 * @param action
	 *            the action to unbind these buttons from.
	 * @param buttons
	 *            the buttons to unbind from <code>action</code>.
	 * @see #bind(Action, Button...)
	 */
	public void unbind(Action action, Button... buttons);

	/**
	 * Unbinds an action entirely.
	 * 
	 * @param action
	 *            the action to unbind.
	 */
	public void unbind(Action action);

}
