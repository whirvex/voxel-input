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
package org.ardenus.input.controller;

/**
 * Signals an invalid {@link ControllerButton} has been given to a
 * {@link Controller} that does not support it.
 * <p>
 * An example of this would be a Sony PlayStation 4 cross button to the
 * {@link Controller#bind(org.ardenus.input.controller.action.ControllerAction, ControllerButton...)
 * bindAction(ControllerAction, ControllerButton...)} method of a Microsoft XBOX
 * One/XBOX 360 controller.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.1-SNAPSHOT
 */
public class IllegalControllerButtonException extends IllegalArgumentException {

	private static final long serialVersionUID = 2649914288154936078L;

	private final Controller controller;
	private final ControllerButton button;

	/**
	 * Constructs a <code>IllegalControllerButtonException</code>.
	 * 
	 * @param controller
	 *            the controller that was given the invalid button.
	 * @param button
	 *            the invalid button that was given.
	 * @throws NullPointerException
	 *             if the <code>controller</code> or <code>button</code> are
	 *             <code>null</code>.
	 */
	public IllegalControllerButtonException(Controller controller, ControllerButton button) throws NullPointerException {
		super("Button " + button.getName() + " of " + button.getControllerClass().getSimpleName() + " is not applicable to " + controller.getClass().getSimpleName());
		this.button = button;
		this.controller = controller;
	}

	/**
	 * Returns the controller that was given the invalid button and consequently
	 * threw the exception.
	 * 
	 * @return the controller that was given the invalid button and consequently
	 *         threw the exception.
	 */
	public Controller getController() {
		return this.controller;
	}

	/**
	 * Returns the invalid button that was given.
	 * 
	 * @return the invalid button that was given.
	 */
	public ControllerButton getButton() {
		return this.button;
	}

}
