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

import java.util.Objects;

/**
 * TODO
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.2-SNAPSHOT
 * @version 0.0.2
 */
public class DeviceType {

	/**
	 * The standard keyboard and mouse used by the computer.
	 */
	public static final DeviceType KEYBOARD = of("Keyboard & Mouse");
	
	/**
	 * A Microsoft XBOX One/XBOX 360 controller.
	 */
	public static final DeviceType XBOX = of("Microsoft XBOX One/XBOX 360 controller");

	/**
	 * Creates a new type of input device.
	 *
	 * @param name
	 *            the name of the device type.
	 * @throws NullPointerException
	 *             if <code>name</code> is <code>null</code>.
	 */
	private static DeviceType of(String name) {
		return new DeviceType(name);
	}
	
	/**
	 * The name of the device.
	 */
	public final String name;

	/**
	 * Constructs a new <code>DeviceType</code>.
	 *
	 * @param name
	 *            the name of the device type.
	 * @throws NullPointerException
	 *             if <code>name</code> is <code>null</code>.
	 */
	public DeviceType(String name) {
		this.name = Objects.requireNonNull(name, "name cannot be null");
	}

}
