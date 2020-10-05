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
package org.ardenus.engine.input.controller.axis;

import org.ardenus.engine.input.controller.Controller;

/**
 * Used for converting axis information to the standardized <code>-1.0F</code>
 * to <code>1.0F</code> scale for easy usage.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.1-SNAPSHOT
 */
public abstract class ControllerAxis {

	/**
	 * Converts the specified axis to the proper <code>-1.0F</code> to
	 * <code>1.0F</code> scale.
	 * 
	 * @param axis
	 *            the current axis.
	 * @return the converted axis.
	 */
	public abstract float getAxis(float axis);

	/**
	 * Returns whether or not the specified axis pressed in any direction.
	 * 
	 * @param axis
	 *            the current axis.
	 * @return <code>true</code> if the axis is pressed, <code>false</code>
	 *         otherwise.
	 */
	public final boolean isAxisPressed(float axis) {
		return Math.abs(getAxis(axis)) >= Controller.DIRECTIONAL_PRESS;
	}

}
