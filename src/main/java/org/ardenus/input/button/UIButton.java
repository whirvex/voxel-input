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
package org.ardenus.input.button;

/**
 * Represents a button on a user interface that can be interacted with within a
 * 2D space.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.2-SNAPSHOT
 * @version 0.0.2
 * @see UIButtonSet
 */
public abstract class UIButton extends Button implements Selectable {

	protected float x;
	protected float y;
	private boolean selectable;
	private boolean directional;

	/**
	 * Constructs a new <code>UIButton</code>.
	 * 
	 * @param x
	 *            the position of the button on the X-axis.
	 * @param y
	 *            the position of the button on the Y-axis.
	 */
	public UIButton(float x, float y) {
		this.x = x;
		this.y = y;
		this.selectable = true;
		this.directional = true;
	}

	@Override
	public boolean isSelectable() {
		return this.selectable;
	}

	@Override
	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}

	/**
	 * Returns if this button can be selected (if currently selectable) via
	 * using directional presses through an input device.
	 * 
	 * @return <code>true</code> if this button can be selected (if currently
	 *         selectable) via using directional presses through an input
	 *         device, <code>false</code> otherwise.
	 */
	public boolean isDirectional() {
		return this.directional;
	}

	/**
	 * Sets whether or not this button can be selected (if currently selectable)
	 * via using directional presses through an input device.
	 * 
	 * @param directional
	 *            <code>true</code> this button should be selectable via using
	 *            directional presses through an input device,
	 *            <code>false</code> otherwise.
	 */
	public void setDirectional(boolean directional) {
		this.directional = directional;
	}

	/**
	 * Returns the width of the button.
	 * 
	 * @return the width of the button.
	 */
	public abstract float getWidth();

	/**
	 * Returns the height of the button.
	 * 
	 * @return the height of the button.
	 */
	public abstract float getHeight();

}
