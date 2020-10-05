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
package org.ardenus.engine.input.button;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * If a {@link org.ardenus.engine.input.controller.Controller Controller} class
 * has this annotation, it will allow for the default buttons needed by
 * {@link ButtonSet} to be added to the global {@link ButtonSet} profile when a
 * controller of its type is connected.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.1-SNAPSHOT
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ButtonSetActions {

	/**
	 * The button does nothing.
	 */
	public static final String NO_ACTION = "";

	/**
	 * Returns the name of the
	 * {@link org.ardenus.engine.input.controller.ControllerButton
	 * ControllerButton} field for pressing buttons in the class that has the
	 * annotation.
	 * 
	 * @return the name of the
	 *         {@link org.ardenus.engine.input.controller.ControllerButton
	 *         ControllerButton} field for pressing buttons in the class that
	 *         has the annotation.
	 */
	String press() default NO_ACTION;

	/**
	 * Returns the name of the
	 * {@link org.ardenus.engine.input.controller.ControllerButton
	 * ControllerButton} field for going back in the class that has the
	 * annotation.
	 * 
	 * @return the name of the
	 *         {@link org.ardenus.engine.input.controller.ControllerButton
	 *         ControllerButton} field for going back in the class that has the
	 *         annotation.
	 */
	String back() default NO_ACTION;

	/**
	 * Returns the name of the
	 * {@link org.ardenus.engine.input.controller.ControllerButton
	 * ControllerButton} field for scrolling up in the class that has the
	 * annotation.
	 * 
	 * @return the name of the
	 *         {@link org.ardenus.engine.input.controller.ControllerButton
	 *         ControllerButton} field for scrolling up in the class that has
	 *         the annotation.
	 */
	String scrollUp() default NO_ACTION;

	/**
	 * Returns the name of the
	 * {@link org.ardenus.engine.input.controller.ControllerButton
	 * ControllerButton} field for scrolling down in the class that has the
	 * annotation.
	 * 
	 * @return the name of the
	 *         {@link org.ardenus.engine.input.controller.ControllerButton
	 *         ControllerButton} field for scrolling down in the class that has
	 *         the annotation.
	 */
	String scrollDown() default NO_ACTION;

	/**
	 * Returns the name of the
	 * {@link org.ardenus.engine.input.controller.ControllerButton
	 * ControllerButton} field for scrolling left in the class that has the
	 * annotation.
	 * 
	 * @return the name of the
	 *         {@link org.ardenus.engine.input.controller.ControllerButton
	 *         ControllerButton} field for scrolling left in the class that has
	 *         the annotation.
	 */
	String scrollLeft() default NO_ACTION;

	/**
	 * Returns the name of the
	 * {@link org.ardenus.engine.input.controller.ControllerButton
	 * ControllerButton} field for scrolling right in the class that has the
	 * annotation.
	 * 
	 * @return the name of the
	 *         {@link org.ardenus.engine.input.controller.ControllerButton
	 *         ControllerButton} field for scrolling right in the class that has
	 *         the annotation.
	 */
	String scrollRight() default NO_ACTION;

}
