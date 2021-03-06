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
package org.ardenus.input;

import java.util.function.Consumer;

import org.ardenus.input.manager.DeviceManager;

/**
 * TODO
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.2-SNAPSHOT
 * @version 0.0.2
 */
public interface InputSystem {

	public static final long DELTA_AUTO = 0xFFFFFFFFFFFFFFFFL;

	/**
	 * Returns the maximum amount of devices that can be connected to this input
	 * system at once.
	 * 
	 * @return the maximum amount of devices that can be connected to this input
	 *         system at once, <code>-1</code> if there is no maximum.
	 */
	public int maxDevices();
	
	public void register(DeviceManager manager, Class<?> devices);
	
	public void unregister(DeviceManager manager);

	public void addListener(DeviceListener listener);

	public void removeListener(DeviceListener listener);

	public void call(Consumer<DeviceListener> consumer);

	public void update(long delta);

	public default void update() {
		this.update(DELTA_AUTO);
	}

}
