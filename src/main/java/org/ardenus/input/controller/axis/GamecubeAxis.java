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

/**
 * Used to convert the axes of Nintendo GameCube controllers to the standardized
 * <code>-1.0F</code> to <code>1.0F</code> scale.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.1-SNAPSHOT
 */
public class GamecubeAxis extends ControllerAxis {

	private final int min;
	private final int max;
	private final int halfway;

	/**
	 * Creates a Nintendo GameCube controller axis.
	 * 
	 * @param min
	 *            the minimum value of the axis.
	 * @param max
	 *            the maximum value of the axis.
	 * @throws IllegalArgumentException
	 *             if the <code>min</code> axis or <code>max</code> axis are not
	 *             even or the <code>min</code> axis is less than the
	 *             <code>max</code> axis.
	 */
	public GamecubeAxis(int min, int max) throws IllegalArgumentException {
		if (min % 2 != 0) {
			throw new IllegalArgumentException("Minimum axis must be even");
		} else if (max % 2 != 0) {
			throw new IllegalArgumentException("Maximum axis must be even");
		} else if (min > max) {
			throw new IllegalArgumentException("Minimum axis cannot be lower than maximum axis");
		}
		this.min = min;
		this.max = max;
		this.halfway = (max - min) / 2;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws IllegalArgumentException
	 *             if the <code>axis</code> is not a whole integer.
	 */
	@Override
	public float getAxis(float axis) throws IllegalArgumentException {
		if (Math.floor(axis) != axis) {
			throw new IllegalArgumentException("Axis must be a whole integer");
		} else if (axis < min) {
			axis = min;
		} else if (axis > max) {
			axis = max;
		}
		float converted = ((int) axis) - min - halfway;
		return ((converted / halfway) * (converted <= halfway ? 1.0F : -1.0F));
	}

}
