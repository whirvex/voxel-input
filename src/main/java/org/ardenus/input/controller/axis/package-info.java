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
/**
 * Components used to convert axis info given from controllers into information
 * usable by the engine and subsequently the game.
 * <p>
 * Typically, all these converter classes do is convert axis information from a
 * proprietary scale to a <code>-1.0-1.0F</code> scale. In this case,
 * <code>-1.0F</code> is left or bottom, while <code>1.0F</code> is right or
 * top.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.1-SNAPSHOT
 * @see org.ardenus.engine.input.controller.axis.ControllerAxis ControllerAxis
 */
package org.ardenus.engine.input.controller.axis;