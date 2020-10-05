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
package org.ardenus.engine.input.controller.keyboard;

import org.ardenus.engine.input.controller.ControllerButton;

/**
 * A keyboard key.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.1-SNAPSHOT
 */
public class KeyboardKey extends ControllerButton {

	private final int logiledId;
	private final KeyCharacter keyCharacter;

	/**
	 * Creates a keyboard key.
	 * 
	 * @param id
	 *            the ID of the key.
	 * @param logiledId
	 *            the ID of the LogiLED key.
	 * @param name
	 *            the name of the keyboard key.
	 */
	public KeyboardKey(int id, int logiledId, String name) {
		super(id, name, KeyboardController.class);
		this.logiledId = logiledId;
		this.keyCharacter = KeyCharacter.getByKey(this);
	}

	/**
	 * Returns the ID used by LogiLED to set the color of the key on the
	 * keyboard.
	 * 
	 * @return the ID used by LogiLED to set the color of the key on the
	 *         keyboard.
	 */
	public int getLogiledId() {
		return this.logiledId;
	}

	/**
	 * Returns whether or not there is a {@link KeyCharacter} for the keyboard
	 * key.
	 * 
	 * @return <code>true</code> if there is a {@link KeyCharacter} for the
	 *         keyboard key, <code>false</code> otherwise.
	 */
	public boolean hasKeyCharacter() {
		return keyCharacter != null;
	}

	/**
	 * Returns the key character of the key on the keyboard.
	 * 
	 * @return the key character of the key on the keyboard.
	 */
	public KeyCharacter getKeyCharacter() {
		return this.keyCharacter;
	}

}
