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
package org.ardenus.engine.input.controller.keyboard;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Contains the characters on a keyboard key and the output it should give based
 * on modifiers.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.1-SNAPSHOT
 */
public enum KeyCharacter {

	/**
	 * The space key.
	 */
	SPACE(GLFW_KEY_SPACE, '\u0020'),

	/**
	 * The apostrophe key.
	 */
	APOSOTROPHE(GLFW_KEY_APOSTROPHE, '\'', '\"'),

	/**
	 * The comma key.
	 */
	COMMA(GLFW_KEY_COMMA, ',', '<'),

	/**
	 * The minus key.
	 */
	MINUS(GLFW_KEY_MINUS, '-', '_'),

	/**
	 * The period key.
	 */
	PERIOD(GLFW_KEY_PERIOD, '.', '>'),

	/**
	 * The slash key.
	 */
	SLASH(GLFW_KEY_SLASH, '/', '?'),

	/**
	 * The 0 key.
	 */
	ZERO(GLFW_KEY_0, '0', ')'),

	/**
	 * The 1 key.
	 */
	ONE(GLFW_KEY_1, '1', '!'),

	/**
	 * The 2 key.
	 */
	TWO(GLFW_KEY_2, '2', '@'),

	/**
	 * The 3 key.
	 */
	THREE(GLFW_KEY_3, '3', '#'),

	/**
	 * The 4 key.
	 */
	FOUR(GLFW_KEY_4, '4', '$'),

	/**
	 * The 5 key.
	 */
	FIVE(GLFW_KEY_5, '5', '%'),

	/**
	 * The 6 key.
	 */
	SIX(GLFW_KEY_6, '6', '^'),

	/**
	 * The 7 key.
	 */
	SEVEN(GLFW_KEY_7, '7', '&'),

	/**
	 * The 8 key.
	 */
	EIGHT(GLFW_KEY_8, '8', '*'),

	/**
	 * The 9 key.
	 */
	NINE(GLFW_KEY_9, '9', '('),

	/**
	 * The semicolon key.
	 */
	SEMICOLON(GLFW_KEY_SEMICOLON, ';', ':'),

	/**
	 * The equal key.
	 */
	EQUAL(GLFW_KEY_EQUAL, '=', '+'),

	/**
	 * The A key.
	 */
	A(GLFW_KEY_A, 'a', 'A'),

	/**
	 * The B key.
	 */
	B(GLFW_KEY_B, 'b', 'B'),

	/**
	 * The C key.
	 */
	C(GLFW_KEY_C, 'c', 'C'),

	/**
	 * The D key.
	 */
	D(GLFW_KEY_D, 'd', 'D'),

	/**
	 * The E key.
	 */
	E(GLFW_KEY_E, 'e', 'E'),

	/**
	 * The F key.
	 */
	F(GLFW_KEY_F, 'f', 'F'),

	/**
	 * The G key.
	 */
	G(GLFW_KEY_G, 'g', 'G'),

	/**
	 * The H key.
	 */
	H(GLFW_KEY_H, 'h', 'H'),

	/**
	 * The I key.
	 */
	I(GLFW_KEY_I, 'i', 'I'),

	/**
	 * The J key.
	 */
	J(GLFW_KEY_J, 'j', 'J'),

	/**
	 * The K key.
	 */
	K(GLFW_KEY_K, 'k', 'K'),

	/**
	 * The L key.
	 */
	L(GLFW_KEY_L, 'l', 'L'),

	/**
	 * The M key.
	 */
	M(GLFW_KEY_M, 'm', 'M'),

	/**
	 * The N key.
	 */
	N(GLFW_KEY_N, 'n', 'N'),

	/**
	 * The O key.
	 */
	O(GLFW_KEY_O, 'o', 'O'),

	/**
	 * The P key.
	 */
	P(GLFW_KEY_P, 'p', 'P'),

	/**
	 * The Q key.
	 */
	Q(GLFW_KEY_Q, 'q', 'Q'),

	/**
	 * The R key.
	 */
	R(GLFW_KEY_R, 'r', 'R'),

	/**
	 * The S key.
	 */
	S(GLFW_KEY_S, 's', 'S'),

	/**
	 * The T key.
	 */
	T(GLFW_KEY_T, 't', 'T'),

	/**
	 * The U key.
	 */
	U(GLFW_KEY_U, 'u', 'U'),

	/**
	 * The V key.
	 */
	V(GLFW_KEY_V, 'v', 'V'),

	/**
	 * The W key.
	 */
	W(GLFW_KEY_W, 'w', 'W'),

	/**
	 * The X key.
	 */
	X(GLFW_KEY_X, 'x', 'X'),

	/**
	 * The Y key.
	 */
	Y(GLFW_KEY_Y, 'y', 'Y'),

	/**
	 * The Z key.
	 */
	Z(GLFW_KEY_Z, 'z', 'Z'),

	/**
	 * The left bracket key.
	 */
	LEFT_BRACKET(GLFW_KEY_LEFT_BRACKET, '[', '{'),

	/**
	 * The backslash key.
	 */
	BACKSLASH(GLFW_KEY_BACKSLASH, '\\', '|'),

	/**
	 * The right bracket key.
	 */
	RIGHT_BRACKET(GLFW_KEY_RIGHT_BRACKET, ']', '}'),

	/**
	 * The grave accent key.
	 */
	GRAVE_ACCENT(GLFW_KEY_GRAVE_ACCENT, '`', '~'),

	/**
	 * The tab key.
	 */
	TAB(GLFW_KEY_TAB, '\t');

	private final int key;
	private final char zeroMode;
	private final char shiftMode;
	private final char controlMode;
	private final char altMode;
	private final char superMode;

	/**
	 * Constructs a <code>KeyCharacter</code>.
	 * 
	 * @param key
	 *            the key.
	 * @param zeroMode
	 *            the character to use with no modifications.
	 * @param shiftMode
	 *            the character to use with the shift modification.
	 * @param controlMode
	 *            the character to use with the control modification.
	 * @param altMode
	 *            the character to use with the alt modification.
	 * @param superMode
	 *            the character to use with the super modification.
	 */
	private KeyCharacter(int key, char zeroMode, char shiftMode, char controlMode, char altMode, char superMode) {
		this.key = key;
		this.zeroMode = zeroMode;
		this.shiftMode = shiftMode;
		this.controlMode = controlMode;
		this.altMode = altMode;
		this.superMode = superMode;
	}

	/**
	 * Constructs a <code>KeyCharacter</code>.
	 * 
	 * @param key
	 *            the key.
	 * @param zeroMode
	 *            the character to use with no modifications.
	 * @param shiftMode
	 *            the character to use with the shift modification.
	 */
	private KeyCharacter(int key, char zeroMode, char shiftMode) {
		this(key, zeroMode, shiftMode, zeroMode, zeroMode, zeroMode);
	}

	/**
	 * Constructs a <code>KeyCharacter</code>.
	 * 
	 * @param key
	 *            the key.
	 * @param zeroMode
	 *            the character to use with no modifications.
	 */
	private KeyCharacter(int key, char zeroMode) {
		this(key, zeroMode, zeroMode);
	}

	/**
	 * Returns the key.
	 * 
	 * @return the key.
	 */
	public int getKey() {
		return this.key;
	}

	/**
	 * Returns the character belonging to he key based on the specified mode.
	 * 
	 * @param mode.
	 *            the mode.
	 * @return the character belonging to the key based on the specified mode.
	 */
	public char getChar(int mode) {
		if ((mode & GLFW_MOD_SHIFT) >= GLFW_MOD_SHIFT) {
			return this.shiftMode;
		} else if ((mode & GLFW_MOD_CONTROL) >= GLFW_MOD_CONTROL) {
			return this.controlMode;
		} else if ((mode & GLFW_MOD_ALT) >= GLFW_MOD_ALT) {
			return this.altMode;
		} else if ((mode & GLFW_MOD_SUPER) >= GLFW_MOD_SUPER) {
			return this.superMode;
		}
		return this.zeroMode;
	}

	/**
	 * Returns the key character with the specified key.
	 * 
	 * @param key
	 *            the key.
	 * @return the key character with the specified key, <code>null</code> if
	 *         non exists.
	 */
	public static KeyCharacter getByKey(int key) {
		for (KeyCharacter keyCharacter : KeyCharacter.values()) {
			if (keyCharacter.getKey() == key) {
				return keyCharacter;
			}
		}
		return null;
	}

	/**
	 * Returns the key character with the specified key.
	 * 
	 * @param key
	 *            the key.
	 * @return the key character with the specified key, <code>null</code> if
	 *         none exists.
	 */
	public static KeyCharacter getByKey(KeyboardKey key) {
		if (key == null) {
			return null;
		}
		return getByKey(key.getId());
	}

}
