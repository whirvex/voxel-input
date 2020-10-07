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

import com.whirvex.event.callback.CallbackConsumer;

public class StdInputSystem implements InputSystem {

	private final int maxDevices;
	private final CallbackConsumer<InputListener> callback;
	
	public StdInputSystem(int maxDevices) {
		this.maxDevices = maxDevices;
		this.callback = CallbackConsumer.std();
	}
	
	@Override
	public int maxDevices() {
		return this.maxDevices;
	}

	@Override
	public void addListener(InputListener listener) {
		callback.register(listener);
	}

	@Override
	public void removeListener(InputListener listener) {
		callback.unregister(listener);
	}

	@Override
	public void call(Consumer<InputListener> consumer) {
		callback.call(consumer);
	}

}
