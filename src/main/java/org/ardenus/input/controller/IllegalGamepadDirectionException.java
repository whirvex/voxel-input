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

/**
 * Signals an invalid {@link GamepadDirection} has been given to a
 * {@link Controller} that does not support it.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.1-SNAPSHOT
 */
public class IllegalGamepadDirectionException extends IllegalArgumentException {

	private static final long serialVersionUID = 3143228246204467498L;

	private final Controller controller;
	private final GamepadDirection direction;

	/**
	 * Constructs a <code>IllegalGamepadDirectionException</code>.
	 * 
	 * @param controller
	 *            the controller that was given the invalid direction.
	 * @param direction
	 *            the invalid direction.
	 * @throws NullPointerException
	 *             if the <code>controller</code> or <code>direction</code> are
	 *             <code>null</code>.
	 */
	public IllegalGamepadDirectionException(Controller controller, GamepadDirection direction) throws NullPointerException {
		super("Direction " + direction.getName() + " is not applicable to " + controller.getClass().getSimpleName());
		this.controller = controller;
		this.direction = direction;
	}

	/**
	 * Returns the controller that was given the invalid direction and
	 * consequently threw the exception.
	 * 
	 * @return the controller that was given the invalid direction and
	 *         consequently threw the exception.
	 */
	public Controller getController() {
		return this.controller;
	}

	/**
	 * Returns the invalid direction that was given.
	 * 
	 * @return the invalid direction that was given.
	 */
	public GamepadDirection getDirection() {
		return this.direction;
	}

}
