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
package org.ardenus.input.device.controller.axis;

/**
 * TODO
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.2-SNAPSHOT
 * @version 0.0.2
 */
public class SwitchAxis extends ControllerAxis {

	private final float min;
	private final float max;
	private final float halfway;

	public SwitchAxis(float min, float max) throws IllegalArgumentException {
		if (min * 100 % 2 != 0) {
			throw new IllegalArgumentException("min must be even");
		} else if (max * 100 % 2 != 0) {
			throw new IllegalArgumentException("max must be even");
		} else if (min > max) {
			throw new IllegalArgumentException("min must be lower than max");
		}
		this.min = min;
		this.max = max;
		this.halfway = (max - min) / 2;
	}

	@Override
	public float getAxis(float axis) {
		if (axis < min) {
			axis = min;
		} else if (axis > max) {
			axis = max;
		}
		float converted = axis - min - halfway;
		return ((converted / halfway) * (converted <= halfway ? 1.0F : -1.0F));
	}

}
