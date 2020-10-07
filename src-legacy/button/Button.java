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

import org.ardenus.input.Input;
import org.ardenus.input.controller.action.ControllerAction;

/**
 * Represents a button that can be interacted with.
 * <p>
 * Buttons that implement the {@link org.ardenus.input.InputListener
 * InputListener} interface will automatically be added as input listeners when
 * one of the button sets they belong to are enabled. When the last button set
 * that the button belongs to is disabled, then it will be automatically
 * removed.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.1-SNAPSHOT
 */
public abstract class Button {

	/**
	 * The button press action.
	 */
	public static final ControllerAction ACTION_BUTTON_PRESS = new ControllerAction("button_press");

	/**
	 * The button back action.
	 */
	public static final ControllerAction ACTION_BUTTON_BACK = new ControllerAction("button_back");

	/**
	 * The scroll up action.
	 */
	public static final ControllerAction ACTION_SCROLL_UP = new ControllerAction("scroll_up");

	/**
	 * The scroll down action.
	 */
	public static final ControllerAction ACTION_SCROLL_DOWN = new ControllerAction("scroll_down");

	/**
	 * The scroll left action.
	 */
	public static final ControllerAction ACTION_SCROLL_LEFT = new ControllerAction("scroll_left");

	/**
	 * The scroll right action.
	 */
	public static final ControllerAction ACTION_SCROLL_RIGHT = new ControllerAction("scroll_right");

	private float x;
	private float y;
	private boolean selectable;
	private boolean rangeable;

	/**
	 * Creates a button.
	 * 
	 * @param x
	 *            the X position of the button.
	 * @param y
	 *            the Y position of the button.
	 */
	public Button(float x, float y) {
		this.x = x;
		this.y = y;
		this.selectable = true;
		this.rangeable = true;
	}

	/**
	 * Creates a button that will have its X and Y position default to
	 * {@value Float#NEGATIVE_INFINITY}.
	 */
	public Button() {
		this(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
	}

	/**
	 * Returns the X position of the button.
	 * 
	 * @return the X position of the button.
	 */
	public final float getX() {
		return this.x;
	}

	/**
	 * Sets the X position of the button.
	 * 
	 * @param x
	 *            the X position.
	 */
	public final void setX(float x) {
		this.x = x;
	}

	/**
	 * Adds to the X position of the button.
	 * 
	 * @param x
	 *            the amount that will be added to the X position.
	 */
	public final void addX(float x) {
		this.x += x;
	}

	/**
	 * Subtracts from the X position of the button.
	 * 
	 * @param x
	 *            the amount that will be subtracted from the X position.
	 */
	public final void subX(float x) {
		this.x -= x;
	}

	/**
	 * Returns the Y position of the button.
	 * 
	 * @return the Y position of the button.
	 */
	public final float getY() {
		return this.y;
	}

	/**
	 * Sets the Y position of the button.
	 * 
	 * @param y
	 *            the Y position.
	 */
	public final void setY(float y) {
		this.y = y;
	}

	/**
	 * Adds to the Y position of the button.
	 * 
	 * @param y
	 *            the amount that will be added to the Y position.
	 */
	public final void addY(float y) {
		this.y += y;
	}

	/**
	 * Subtracts from the Y position of the button.
	 * 
	 * @param y
	 *            the amount that will be subtracted from the Y position.
	 */
	public final void subY(float y) {
		this.y -= y;
	}

	/**
	 * Sets the position of the button.
	 * 
	 * @param x
	 *            the X position.
	 * @param y
	 *            the Y position.
	 */
	public final void setPosition(float x, float y) {
		this.setX(x);
		this.setY(y);
	}

	/**
	 * Adds to the position of the button.
	 * 
	 * @param x
	 *            the amount that will be added to the X position.
	 * @param y
	 *            the amount that will be added to the Y position.
	 */
	public final void addPosition(float x, float y) {
		this.addX(x);
		this.addY(y);
	}

	/**
	 * Subtracts from the position of the button.
	 * 
	 * @param x
	 *            the amount that will be subtracted from the X position.
	 * @param y
	 *            the amount that will be subtracted from the Y position.
	 */
	public final void subPosition(float x, float y) {
		this.subX(x);
		this.subY(y);
	}

	/**
	 * Returns whether or not the button is selectable.
	 * 
	 * @return <code>true</code> if the button is selectable, <code>false</code>
	 *         otherwise.
	 */
	public final boolean isSelectable() {
		return this.selectable;
	}

	/**
	 * Sets whether or not the button is selectable.
	 * 
	 * @param selectable
	 *            <code>true</code> if the button is selectable,
	 *            <code>false</code> otherwise.
	 */
	public final void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}

	/**
	 * Returns whether or not the button is rangeable.
	 * 
	 * @return <code>true</code> if the button is rangeable, <code>false</code>
	 *         otherwise.
	 */
	public final boolean isRangeable() {
		return this.rangeable;
	}

	/**
	 * Sets whether or not the button is rangeable.
	 * <p>
	 * In this sense, rangeable means that the button can be selected with a
	 * controller using the analog sticks or D-pad.
	 * 
	 * @param rangeable
	 *            <code>true</code> if the button is rangeable,
	 *            <code>false</code> otherwise.
	 */
	public final void setRangeable(boolean rangeable) {
		this.rangeable = rangeable;
	}

	/**
	 * Returns whether or not the button is selected.
	 * 
	 * @return <code>true</code> if the button is selected, <code>false</code>
	 *         otherwise.
	 */
	public final boolean isSelected() {
		return Input.isButtonSelected(this);
	}

	/**
	 * Returns whether or not the button is pressed.
	 * 
	 * @return <code>true</code> if the button is pressed, <code>false</code>
	 *         otherwise.
	 */
	public final boolean isPressed() {
		return Input.isButtonPressed(this);
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

	@Override
	public String toString() {
		return "Button [x=" + x + ", y=" + y + ", selectable=" + selectable + ", rangeable=" + rangeable + ", getWidth()=" + getWidth() + ", getHeight()=" + getHeight() + "]";
	}

}
