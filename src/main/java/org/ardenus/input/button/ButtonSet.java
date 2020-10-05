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

import java.util.ArrayList;

import org.ardenus.input.Input;
import org.ardenus.input.InputListener;

/**
 * A set of buttons that can used to group buttons together and manage them
 * collectively.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.1-SNAPSHOT
 */
public class ButtonSet {

	/**
	 * Notifies button sets when they are enabled or disabled.
	 * <p>
	 * The reason for this class being here is to allow for the
	 * {@link ButtonSet#onEnable()} and {@link ButtonSet#onDisable()} functions
	 * to be private so that they can not be called by other parts of code
	 * mistakingly.
	 * 
	 * @author Trent Summerlin
	 * @since Ardenus Input v0.0.1-SNAPSHOT
	 */
	private static class ButtonSetEnableListener implements InputListener {

		private static ButtonSetEnableListener listener;

		/**
		 * Initializes the enable/disable listener for the button sets.
		 * <p>
		 * If the enable/disable listener has already been initialized, this
		 * method will do nothing.
		 */
		public static void init() {
			if (listener == null) {
				listener = new ButtonSetEnableListener();
				Input.addListener(listener);
			}
		}

		@Override
		public void onButtonSetEnable(ButtonSet buttonSet) {
			buttonSet.onEnable();
		}

		@Override
		public void onButtonSetDisable(ButtonSet buttonSet) {
			buttonSet.onDisable();
		}

	}

	private final ArrayList<Button> buttons;

	/**
	 * Creates a button set from the specified buttons.
	 * 
	 * @param buttons
	 *            the buttons.
	 * @throws NullPointerException
	 *             if the <code>buttons</code> or one of the buttons are
	 *             <code>null</code>.
	 */
	public ButtonSet(Button... buttons) throws NullPointerException {
		if (buttons == null) {
			throw new NullPointerException("Buttons cannot be null");
		}
		ButtonSetEnableListener.init();
		this.buttons = new ArrayList<Button>();
		for (Button button : buttons) {
			this.addButton(button);
		}
	}

	/**
	 * Returns whether or not the button set is enabled.
	 * 
	 * @return <code>true</code> if the button set is enabled,
	 *         <code>false</code> otherwise.
	 */
	public boolean isEnabled() {
		return Input.isButtonSetEnabled(this);
	}

	/**
	 * Enables the button set.
	 */
	public void enable() {
		Input.enableButtonSet(this);
	}

	/**
	 * Disables the button set.
	 */
	public void disable() {
		Input.disableButtonSet(this);
	}

	/**
	 * Called when the button set is enabled.
	 * <p>
	 * When a button set is enabled, it will go through all of its buttons and
	 * check if they are an {@link InputListener}. If they are an input
	 * listener, they will be added as one automatically.
	 */
	private void onEnable() {
		for (Button button : buttons) {
			if (button instanceof InputListener) {
				Input.addListener((InputListener) button);
			}
		}
	}

	/**
	 * Called when the button set is disabled.
	 * <p>
	 * When a button set is disabled, it will go through all of its buttons and
	 * check if they are an {@link InputListener}. If they are an input
	 * listener, they will be removed automatically.
	 */
	private void onDisable() {
		for (Button button : buttons) {
			if (button instanceof InputListener) {
				boolean remove = true;
				for (ButtonSet buttonSet : Input.getCurrentButtonSets()) {
					if (!buttonSet.equals(this) && buttonSet.hasButton(button)) {
						remove = false;
						break;
					}
				}
				if (remove == true) {
					Input.removeListener((InputListener) button);
				}
			}
		}
	}

	/**
	 * Returns the buttons that belong to the button set.
	 * 
	 * @return the buttons that belong to the button set.
	 */
	public Button[] getButtons() {
		return buttons.toArray(new Button[buttons.size()]);
	}

	/**
	 * Adds the specified button to the button set.
	 * 
	 * @param button
	 *            the button.
	 * @throws NullPointerException
	 *             if the <code>button</code> is <code>null</code>.
	 */
	public void addButton(Button button) throws NullPointerException {
		if (button == null) {
			throw new NullPointerException("Button cannot be null");
		} else if (!buttons.contains(button)) {
			buttons.add(button);
			if (this.isEnabled() && button instanceof InputListener) {
				Input.addListener((InputListener) button);
			}
		}
	}

	/**
	 * Returns whether or not the button set has the specified button.
	 * 
	 * @param button
	 *            the button.
	 * @return <code>true</code> if the button belongs to the button set,
	 *         <code>false</code> otherwise.
	 */
	public boolean hasButton(Button button) {
		return buttons.contains(button);
	}

	/**
	 * Removes the button from the button set.
	 * 
	 * @param button
	 *            the button.
	 */
	public void removeButton(Button button) {
		buttons.remove(button);
		if (this.isEnabled()) {
			// Check if button is used anywhere else
			boolean remove = true;
			for (ButtonSet buttonSet : Input.getCurrentButtonSets()) {
				if (!buttonSet.equals(this) && buttonSet.hasButton(button)) {
					remove = false;
					break;
				}
			}

			// Unselect button if it used nowhere else
			if (remove == true) {
				Input.unselectButton(button);
				if (button instanceof InputListener) {
					Input.removeListener((InputListener) button);
				}
			}
		}
	}

}
