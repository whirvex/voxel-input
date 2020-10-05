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
package org.ardenus.input.controller.keyboard;

import static com.logitech.gaming.LogiLED.*;
import static org.lwjgl.glfw.GLFW.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ardenus.graphics.Camera;
import org.ardenus.graphics.texture.Texture;
import org.ardenus.input.Input;
import org.ardenus.input.button.ButtonSetActions;
import org.ardenus.input.controller.Controller;
import org.ardenus.input.controller.ControllerType;
import org.ardenus.input.handler.InputHandler;
import org.ardenus.input.handler.KeyboardHandler;

/**
 * A keyboard and mouse that can be used for input.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.1-SNAPSHOT
 */
@ButtonSetActions(press = "BUTTON_MOUSE_LEFT", scrollUp = "SCROLL_MOUSE_UP", scrollDown = "SCROLL_MOUSE_DOWN", scrollLeft = "SCROLL_MOUSE_LEFT", scrollRight = "SCROLL_MOUSE_RIGHT")
public class KeyboardController extends Controller {

	private static final Logger LOGGER = LogManager.getLogger(KeyboardController.class);

	/**
	 * There is no LogiLED key.
	 */
	private static final int LOGI_LED_NONE = -1;

	/**
	 * The amount of LogiLED G keys on the keyboard.
	 */
	public static final int LOGI_LED_G_KEY_COUNT = 11;

	/**
	 * The left mouse button.
	 */
	public static final KeyboardKey BUTTON_MOUSE_LEFT = new KeyboardKey(GLFW_MOUSE_BUTTON_LEFT, LOGI_LED_NONE, "Left Mouse");

	/**
	 * The right mouse button.
	 */
	public static final KeyboardKey BUTTON_MOUSE_RIGHT = new KeyboardKey(GLFW_MOUSE_BUTTON_RIGHT, LOGI_LED_NONE, "Right Mouse");

	/**
	 * The middle mouse button.
	 */
	public static final KeyboardKey BUTTON_MOUSE_MIDDLE = new KeyboardKey(GLFW_MOUSE_BUTTON_MIDDLE, LOGI_LED_NONE, "Middle Mouse");

	/**
	 * The mouse scrolling up.
	 */
	public static final KeyboardKey SCROLL_MOUSE_UP = new KeyboardKey(GLFW_MOUSE_BUTTON_LAST + 1, LOGI_LED_NONE, "Mouse Scroll Up");

	/**
	 * The mouse scrolling down.
	 */
	public static final KeyboardKey SCROLL_MOUSE_DOWN = new KeyboardKey(GLFW_MOUSE_BUTTON_LAST + 2, LOGI_LED_NONE, "Mouse Scroll Down");

	/**
	 * The mouse scrolling left.
	 */
	public static final KeyboardKey SCROLL_MOUSE_LEFT = new KeyboardKey(GLFW_MOUSE_BUTTON_LAST + 3, LOGI_LED_NONE, "Mouse Scroll Left");

	/**
	 * The mouse scrolling right.
	 */
	public static final KeyboardKey SCROLL_MOUSE_RIGHT = new KeyboardKey(GLFW_MOUSE_BUTTON_LAST + 4, LOGI_LED_NONE, "Mouse Scroll Right");

	/**
	 * The space key.
	 */
	public static final KeyboardKey KEY_SPACE = new KeyboardKey(GLFW_KEY_SPACE, SPACE, "Space");

	/**
	 * The apostrophe key.
	 */
	public static final KeyboardKey KEY_APOSTROPHE = new KeyboardKey(GLFW_KEY_APOSTROPHE, APOSTROPHE, "\'");

	/**
	 * The comma key.
	 */
	public static final KeyboardKey KEY_COMMA = new KeyboardKey(GLFW_KEY_COMMA, COMMA, ",");

	/**
	 * The minus key.
	 */
	public static final KeyboardKey KEY_MINUS = new KeyboardKey(GLFW_KEY_MINUS, MINUS, "-");

	/**
	 * The period key.
	 */
	public static final KeyboardKey KEY_PERIOD = new KeyboardKey(GLFW_KEY_PERIOD, PERIOD, ".");

	/**
	 * The slash key.
	 */
	public static final KeyboardKey KEY_SLASH = new KeyboardKey(GLFW_KEY_SLASH, FORWARD_SLASH, "/");

	/**
	 * The zero key.
	 */
	public static final KeyboardKey KEY_ZERO = new KeyboardKey(GLFW_KEY_0, ZERO, "0");

	/**
	 * The one key.
	 */
	public static final KeyboardKey KEY_ONE = new KeyboardKey(GLFW_KEY_1, ONE, "1");

	/**
	 * The two key.
	 */
	public static final KeyboardKey KEY_TWO = new KeyboardKey(GLFW_KEY_2, TWO, "2");

	/**
	 * The three key.
	 */
	public static final KeyboardKey KEY_THREE = new KeyboardKey(GLFW_KEY_3, THREE, "3");

	/**
	 * The four key.
	 */
	public static final KeyboardKey KEY_FOUR = new KeyboardKey(GLFW_KEY_4, FOUR, "4");

	/**
	 * The five key.
	 */
	public static final KeyboardKey KEY_FIVE = new KeyboardKey(GLFW_KEY_5, FIVE, "5");

	/**
	 * The six key.
	 */
	public static final KeyboardKey KEY_SIX = new KeyboardKey(GLFW_KEY_6, SIX, "6");

	/**
	 * The seven key.
	 */
	public static final KeyboardKey KEY_SEVEN = new KeyboardKey(GLFW_KEY_7, SEVEN, "7");

	/**
	 * The eight key.
	 */
	public static final KeyboardKey KEY_EIGHT = new KeyboardKey(GLFW_KEY_8, EIGHT, "8");

	/**
	 * The nine key.
	 */
	public static final KeyboardKey KEY_NINE = new KeyboardKey(GLFW_KEY_9, NINE, "9");

	/**
	 * The semicolon key.
	 */
	public static final KeyboardKey KEY_SEMICOLON = new KeyboardKey(GLFW_KEY_SEMICOLON, SEMICOLON, ";");

	/**
	 * The equals key.
	 */
	public static final KeyboardKey KEY_EQUALS = new KeyboardKey(GLFW_KEY_EQUAL, EQUALS, "=");

	/**
	 * The A key.
	 */
	public static final KeyboardKey KEY_A = new KeyboardKey(GLFW_KEY_A, A, "A");

	/**
	 * The B key.
	 */
	public static final KeyboardKey KEY_B = new KeyboardKey(GLFW_KEY_B, B, "B");

	/**
	 * The C key.
	 */
	public static final KeyboardKey KEY_C = new KeyboardKey(GLFW_KEY_C, C, "C");

	/**
	 * The D key.
	 */
	public static final KeyboardKey KEY_D = new KeyboardKey(GLFW_KEY_D, D, "D");

	/**
	 * The E key.
	 */
	public static final KeyboardKey KEY_E = new KeyboardKey(GLFW_KEY_E, E, "E");

	/**
	 * The F key.
	 */
	public static final KeyboardKey KEY_F = new KeyboardKey(GLFW_KEY_F, F, "F");

	/**
	 * The G key.
	 */
	public static final KeyboardKey KEY_G = new KeyboardKey(GLFW_KEY_G, G, "G");

	/**
	 * The H key.
	 */
	public static final KeyboardKey KEY_H = new KeyboardKey(GLFW_KEY_H, H, "H");

	/**
	 * The I key.
	 */
	public static final KeyboardKey KEY_I = new KeyboardKey(GLFW_KEY_I, I, "I");

	/**
	 * The J key.
	 */
	public static final KeyboardKey KEY_J = new KeyboardKey(GLFW_KEY_J, J, "J");

	/**
	 * The K key.
	 */
	public static final KeyboardKey KEY_K = new KeyboardKey(GLFW_KEY_K, K, "K");

	/**
	 * The L key.
	 */
	public static final KeyboardKey KEY_L = new KeyboardKey(GLFW_KEY_L, L, "L");

	/**
	 * The M key.
	 */
	public static final KeyboardKey KEY_M = new KeyboardKey(GLFW_KEY_M, M, "M");

	/**
	 * The N key.
	 */
	public static final KeyboardKey KEY_N = new KeyboardKey(GLFW_KEY_N, N, "N");

	/**
	 * The O key.
	 */
	public static final KeyboardKey KEY_O = new KeyboardKey(GLFW_KEY_O, O, "O");

	/**
	 * The P key.
	 */
	public static final KeyboardKey KEY_P = new KeyboardKey(GLFW_KEY_P, P, "P");

	/**
	 * The Q key.
	 */
	public static final KeyboardKey KEY_Q = new KeyboardKey(GLFW_KEY_Q, Q, "Q");

	/**
	 * The R key.
	 */
	public static final KeyboardKey KEY_R = new KeyboardKey(GLFW_KEY_R, R, "R");

	/**
	 * The S key.
	 */
	public static final KeyboardKey KEY_S = new KeyboardKey(GLFW_KEY_S, S, "S");

	/**
	 * The T key.
	 */
	public static final KeyboardKey KEY_T = new KeyboardKey(GLFW_KEY_T, T, "T");

	/**
	 * The U key.
	 */
	public static final KeyboardKey KEY_U = new KeyboardKey(GLFW_KEY_U, U, "U");

	/**
	 * The V key.
	 */
	public static final KeyboardKey KEY_V = new KeyboardKey(GLFW_KEY_V, V, "V");

	/**
	 * The W key.
	 */
	public static final KeyboardKey KEY_W = new KeyboardKey(GLFW_KEY_W, W, "W");

	/**
	 * The X key.
	 */
	public static final KeyboardKey KEY_X = new KeyboardKey(GLFW_KEY_X, X, "X");

	/**
	 * The Y key.
	 */
	public static final KeyboardKey KEY_Y = new KeyboardKey(GLFW_KEY_Y, Y, "Y");

	/**
	 * The Z key.
	 */
	public static final KeyboardKey KEY_Z = new KeyboardKey(GLFW_KEY_Z, Z, "Z");

	/**
	 * The left bracket key.
	 */
	public static final KeyboardKey KEY_LEFT_BRACKET = new KeyboardKey(GLFW_KEY_LEFT_BRACKET, OPEN_BRACKET, "[");

	/**
	 * The backslash key.
	 */
	public static final KeyboardKey KEY_BACKSLASH = new KeyboardKey(GLFW_KEY_BACKSLASH, BACKSLASH, "\\");

	/**
	 * The right bracket key.
	 */
	public static final KeyboardKey KEY_RIGHT_BRACKET = new KeyboardKey(GLFW_KEY_RIGHT_BRACKET, CLOSE_BRACKET, "]");

	/**
	 * The grave accent key.
	 */
	public static final KeyboardKey KEY_GRAVE_ACCENT = new KeyboardKey(GLFW_KEY_GRAVE_ACCENT, TILDE, "`");

	/**
	 * The escape key.
	 */
	public static final KeyboardKey KEY_ESCAPE = new KeyboardKey(GLFW_KEY_ESCAPE, ESC, "Escape");

	/**
	 * The enter key.
	 */
	public static final KeyboardKey KEY_ENTER = new KeyboardKey(GLFW_KEY_ENTER, ENTER, "Enter");

	/**
	 * The tab key.
	 */
	public static final KeyboardKey KEY_TAB = new KeyboardKey(GLFW_KEY_TAB, TAB, "Tab");

	/**
	 * The backspace key.
	 */
	public static final KeyboardKey KEY_BACKSPACE = new KeyboardKey(GLFW_KEY_BACKSPACE, BACKSPACE, "Backspace");

	/**
	 * The insert key.
	 */
	public static final KeyboardKey KEY_INSERT = new KeyboardKey(GLFW_KEY_INSERT, INSERT, "Insert");

	/**
	 * The delete key.
	 */
	public static final KeyboardKey KEY_DELETE = new KeyboardKey(GLFW_KEY_DELETE, KEYBOARD_DELETE, "Delete");

	/**
	 * The right key.
	 */
	public static final KeyboardKey KEY_RIGHT = new KeyboardKey(GLFW_KEY_RIGHT, ARROW_RIGHT, "Right");

	/**
	 * The left key.
	 */
	public static final KeyboardKey KEY_LEFT = new KeyboardKey(GLFW_KEY_LEFT, ARROW_LEFT, "Left");

	/**
	 * The up key.
	 */
	public static final KeyboardKey KEY_UP = new KeyboardKey(GLFW_KEY_UP, ARROW_UP, "Up");

	/**
	 * The down key.
	 */
	public static final KeyboardKey KEY_DOWN = new KeyboardKey(GLFW_KEY_DOWN, ARROW_DOWN, "Down");

	/**
	 * The page up key.
	 */
	public static final KeyboardKey KEY_PAGE_UP = new KeyboardKey(GLFW_KEY_PAGE_UP, PAGE_UP, "Page Up");

	/**
	 * The page down key.
	 */
	public static final KeyboardKey KEY_PAGE_DOWN = new KeyboardKey(GLFW_KEY_PAGE_UP, PAGE_UP, "Page Down");

	/**
	 * The home key.
	 */
	public static final KeyboardKey KEY_HOME = new KeyboardKey(GLFW_KEY_HOME, HOME, "Home");

	/**
	 * The end key.
	 */
	public static final KeyboardKey KEY_END = new KeyboardKey(GLFW_KEY_END, END, "End");

	/**
	 * The caps lock key.
	 */
	public static final KeyboardKey KEY_CAPS_LOCK = new KeyboardKey(GLFW_KEY_CAPS_LOCK, CAPS_LOCK, "Caps Lock");

	/**
	 * The scroll lock key.
	 */
	public static final KeyboardKey KEY_SCROLL_LOCK = new KeyboardKey(GLFW_KEY_SCROLL_LOCK, SCROLL_LOCK, "Scroll Lock");

	/**
	 * The num lock key.
	 */
	public static final KeyboardKey KEY_NUM_LOCK = new KeyboardKey(GLFW_KEY_NUM_LOCK, NUM_LOCK, "Num Lock");

	/**
	 * The print screen key.
	 */
	public static final KeyboardKey KEY_PRINT_SCREEN = new KeyboardKey(GLFW_KEY_PRINT_SCREEN, PRINT_SCREEN, "Print Screen");

	/**
	 * The key pause key.
	 */
	public static final KeyboardKey KEY_KEY_PAUSE = new KeyboardKey(GLFW_KEY_PAUSE, PAUSE_BREAK, "Pause");

	/**
	 * The F1 key.
	 */
	public static final KeyboardKey KEY_F1 = new KeyboardKey(GLFW_KEY_F1, F1, "F1");

	/**
	 * The F2 key.
	 */
	public static final KeyboardKey KEY_F2 = new KeyboardKey(GLFW_KEY_F2, F2, "F2");

	/**
	 * The F3 key.
	 */
	public static final KeyboardKey KEY_F3 = new KeyboardKey(GLFW_KEY_F3, F3, "F3");

	/**
	 * The F4 key.
	 */
	public static final KeyboardKey KEY_F4 = new KeyboardKey(GLFW_KEY_F4, F4, "F4");

	/**
	 * The F5 key.
	 */
	public static final KeyboardKey KEY_F5 = new KeyboardKey(GLFW_KEY_F5, F5, "F5");

	/**
	 * The F6 key.
	 */
	public static final KeyboardKey KEY_F6 = new KeyboardKey(GLFW_KEY_F6, F6, "F6");

	/**
	 * The F7 key.
	 */
	public static final KeyboardKey KEY_F7 = new KeyboardKey(GLFW_KEY_F7, F7, "F7");

	/**
	 * The F8 key.
	 */
	public static final KeyboardKey KEY_F8 = new KeyboardKey(GLFW_KEY_F8, F8, "F8");

	/**
	 * The F9 key.
	 */
	public static final KeyboardKey KEY_F9 = new KeyboardKey(GLFW_KEY_F9, F9, "F9");

	/**
	 * The F10 key.
	 */
	public static final KeyboardKey KEY_F10 = new KeyboardKey(GLFW_KEY_F10, F10, "F10");

	/**
	 * The F11 key.
	 */
	public static final KeyboardKey KEY_F11 = new KeyboardKey(GLFW_KEY_F11, F11, "F11");

	/**
	 * The F12 key.
	 */
	public static final KeyboardKey KEY_F12 = new KeyboardKey(GLFW_KEY_F12, F12, "F12");

	/**
	 * The F13 key.
	 */
	public static final KeyboardKey KEY_F13 = new KeyboardKey(GLFW_KEY_F13, LOGI_LED_NONE, "F13");

	/**
	 * The F14 key.
	 */
	public static final KeyboardKey KEY_F14 = new KeyboardKey(GLFW_KEY_F14, LOGI_LED_NONE, "F14");

	/**
	 * The F15 key.
	 */
	public static final KeyboardKey KEY_F15 = new KeyboardKey(GLFW_KEY_F15, LOGI_LED_NONE, "F15");

	/**
	 * The F16 key.
	 */
	public static final KeyboardKey KEY_F16 = new KeyboardKey(GLFW_KEY_F16, LOGI_LED_NONE, "F16");

	/**
	 * The F17 key.
	 */
	public static final KeyboardKey KEY_F17 = new KeyboardKey(GLFW_KEY_F17, LOGI_LED_NONE, "F17");

	/**
	 * The F18 key.
	 */
	public static final KeyboardKey KEY_F18 = new KeyboardKey(GLFW_KEY_F18, LOGI_LED_NONE, "F18");

	/**
	 * The F19 key.
	 */
	public static final KeyboardKey KEY_F19 = new KeyboardKey(GLFW_KEY_F19, LOGI_LED_NONE, "F19");

	/**
	 * The F20 key.
	 */
	public static final KeyboardKey KEY_F20 = new KeyboardKey(GLFW_KEY_F20, LOGI_LED_NONE, "F20");

	/**
	 * The F21 key.
	 */
	public static final KeyboardKey KEY_F21 = new KeyboardKey(GLFW_KEY_F21, LOGI_LED_NONE, "F21");

	/**
	 * The F22 key.
	 */
	public static final KeyboardKey KEY_F22 = new KeyboardKey(GLFW_KEY_F22, LOGI_LED_NONE, "F22");

	/**
	 * The F23 key.
	 */
	public static final KeyboardKey KEY_F23 = new KeyboardKey(GLFW_KEY_F23, LOGI_LED_NONE, "F23");

	/**
	 * The F24 key.
	 */
	public static final KeyboardKey KEY_F24 = new KeyboardKey(GLFW_KEY_F24, LOGI_LED_NONE, "F24");

	/**
	 * The F25 key.
	 */
	public static final KeyboardKey KEY_F25 = new KeyboardKey(GLFW_KEY_F25, LOGI_LED_NONE, "F25");

	/**
	 * The keypad zero key.
	 */
	public static final KeyboardKey KEY_KEYPAD_ZERO = new KeyboardKey(GLFW_KEY_KP_0, NUM_ZERO, "Num 0");

	/**
	 * The keypoad one key.
	 */
	public static final KeyboardKey KEY_KEYPAD_ONE = new KeyboardKey(GLFW_KEY_KP_1, NUM_ONE, "Num 1");

	/**
	 * The keypad two key.
	 */
	public static final KeyboardKey KEY_KEYPAD_TWO = new KeyboardKey(GLFW_KEY_KP_2, NUM_TWO, "Num 2");

	/**
	 * The keypad three key.
	 */
	public static final KeyboardKey KEY_KEYPAD_THREE = new KeyboardKey(GLFW_KEY_KP_3, NUM_THREE, "Num 3");

	/**
	 * The keypad four key.
	 */
	public static final KeyboardKey KEY_KEYPAD_FOUR = new KeyboardKey(GLFW_KEY_KP_4, NUM_FOUR, "Num 4");

	/**
	 * The keypad five key.
	 */
	public static final KeyboardKey KEY_KEYPAD_FIVE = new KeyboardKey(GLFW_KEY_KP_5, NUM_FIVE, "Num 5");

	/**
	 * The keypad six key.
	 */
	public static final KeyboardKey KEY_KEYPAD_SIX = new KeyboardKey(GLFW_KEY_KP_6, NUM_SIX, "Num 6");

	/**
	 * The keypad seven key.
	 */
	public static final KeyboardKey KEY_KEYPAD_SEVEN = new KeyboardKey(GLFW_KEY_KP_7, NUM_SEVEN, "Num 7");

	/**
	 * The keypad eight key.
	 */
	public static final KeyboardKey KEY_KEYPAD_EIGHT = new KeyboardKey(GLFW_KEY_KP_8, NUM_EIGHT, "Num 8");

	/**
	 * The keypad nine key.
	 */
	public static final KeyboardKey KEY_KEYPAD_NINE = new KeyboardKey(GLFW_KEY_KP_9, NUM_NINE, "Num 9");

	/**
	 * The keypad decimal key.
	 */
	public static final KeyboardKey KEY_KEYPAD_DECIMAL = new KeyboardKey(GLFW_KEY_KP_DECIMAL, NUM_PERIOD, "Num .");

	/**
	 * The keypad divide key.
	 */
	public static final KeyboardKey KEY_KEYPAD_DIVIDE = new KeyboardKey(GLFW_KEY_KP_DIVIDE, NUM_SLASH, "Num /");

	/**
	 * The keypad multiply key.
	 */
	public static final KeyboardKey KEY_KEYPAD_MULTIPLY = new KeyboardKey(GLFW_KEY_KP_MULTIPLY, NUM_ASTERISK, "Num *");

	/**
	 * The keypad subtract key.
	 */
	public static final KeyboardKey KEY_KEYPAD_SUBTRACT = new KeyboardKey(GLFW_KEY_KP_SUBTRACT, NUM_MINUS, "Num -");

	/**
	 * The keypad add key.
	 */
	public static final KeyboardKey KEY_KEYPAD_ADD = new KeyboardKey(GLFW_KEY_KP_ADD, NUM_PLUS, "Num +");

	/**
	 * The keypad enter key.
	 */
	public static final KeyboardKey KEY_KEYPAD_ENTER = new KeyboardKey(GLFW_KEY_KP_ENTER, NUM_ENTER, "Num Enter");

	/**
	 * The keypad equals key.
	 */
	public static final KeyboardKey KEY_KEYPAD_EQUALS = new KeyboardKey(GLFW_KEY_KP_EQUAL, LOGI_LED_NONE, "Num =");

	/**
	 * The left shift key.
	 */
	public static final KeyboardKey KEY_LEFT_SHIFT = new KeyboardKey(GLFW_KEY_LEFT_SHIFT, LEFT_SHIFT, "Left Shift");

	/**
	 * The left control key.
	 */
	public static final KeyboardKey KEY_LEFT_CONTROL = new KeyboardKey(GLFW_KEY_LEFT_CONTROL, LEFT_CONTROL, "Left Control");

	/**
	 * The left alt key.
	 */
	public static final KeyboardKey KEY_LEFT_ALT = new KeyboardKey(GLFW_KEY_LEFT_ALT, LEFT_ALT, "Left Alt");

	/**
	 * The left super key.
	 */
	public static final KeyboardKey KEY_LEFT_SUPER = new KeyboardKey(GLFW_KEY_LEFT_SUPER, LEFT_WINDOWS, "Left Windows");

	/**
	 * The right shift key.
	 */
	public static final KeyboardKey KEY_RIGHT_SHIFT = new KeyboardKey(GLFW_KEY_RIGHT_SHIFT, RIGHT_SHIFT, "Right Shift");

	/**
	 * The right control key.
	 */
	public static final KeyboardKey KEY_RIGHT_CONTROL = new KeyboardKey(GLFW_KEY_RIGHT_CONTROL, RIGHT_CONTROL, "Right Control");

	/**
	 * The right alt key.
	 */
	public static final KeyboardKey KEY_RIGHT_ALT = new KeyboardKey(GLFW_KEY_RIGHT_ALT, RIGHT_ALT, "Right Alt");

	/**
	 * The right super key.
	 */
	public static final KeyboardKey KEY_RIGHT_SUPER = new KeyboardKey(GLFW_KEY_RIGHT_SUPER, RIGHT_WINDOWS, "Right Windows");

	/**
	 * The menu key.
	 */
	public static final KeyboardKey KEY_MENU = new KeyboardKey(GLFW_KEY_MENU, LOGI_LED_NONE, "Menu");

	/**
	 * The Logitech Orion G1 key.
	 */
	public static final KeyboardKey KEY_ORION_G1 = new KeyboardKey(GLFW_KEY_LAST + 1, G_1, "G1");

	/**
	 * The Logitech Orion G2 key.
	 */
	public static final KeyboardKey KEY_ORION_G2 = new KeyboardKey(GLFW_KEY_LAST + 2, G_2, "G2");

	/**
	 * The Logitech Orion G3 key.
	 */
	public static final KeyboardKey KEY_ORION_G3 = new KeyboardKey(GLFW_KEY_LAST + 3, G_3, "G3");

	/**
	 * The Logitech Orion G4 key.
	 */
	public static final KeyboardKey KEY_ORION_G4 = new KeyboardKey(GLFW_KEY_LAST + 4, G_4, "G4");

	/**
	 * The Logitech Orion G5 key.
	 */
	public static final KeyboardKey KEY_ORION_G5 = new KeyboardKey(GLFW_KEY_LAST + 5, G_5, "G5");

	/**
	 * The Logitech Orion G6 key.
	 */
	public static final KeyboardKey KEY_ORION_G6 = new KeyboardKey(GLFW_KEY_LAST + 6, G_6, "G6");

	/**
	 * The Logitech Orion G7 key.
	 */
	public static final KeyboardKey KEY_ORION_G7 = new KeyboardKey(GLFW_KEY_LAST + 7, G_7, "G7");

	/**
	 * The Logitech Orion G8 key.
	 */
	public static final KeyboardKey KEY_ORION_G8 = new KeyboardKey(GLFW_KEY_LAST + 8, G_8, "G8");

	/**
	 * The Logitech Orion G9 key.
	 */
	public static final KeyboardKey KEY_ORION_G9 = new KeyboardKey(GLFW_KEY_LAST + 9, G_9, "G9");

	/**
	 * The Logitech Orion logo.
	 */
	public static final KeyboardKey KEY_ORION_LOGO = new KeyboardKey(GLFW_KEY_LAST + 10, G_LOGO, "G910 Logo");

	/**
	 * The Logitech Orion badge.
	 */
	public static final KeyboardKey KEY_ORION_BADGE = new KeyboardKey(GLFW_KEY_LAST + 11, G_BADGE, "G910 Badge");

	/**
	 * Converts the specified RGB channel value from a <code>0-255</code> scale
	 * to a <code>0-100</code> scale.
	 * 
	 * @param a
	 *            the RGB channel.
	 * @return the channel value on a <code>0-100</code> scale.
	 */
	private static int channel(int a) {
		return (int) (((float) a / 255.0F) * 100);
	}

	/**
	 * Sets all of the keys on the keyboard to the specified RGB channel values.
	 * 
	 * @param red
	 *            the red channel.
	 * @param green
	 *            the green channel.
	 * @param blue
	 *            the blue channel.
	 */
	@OrionMethod
	public static void setKeyboardColor(int red, int green, int blue) {
		if (hasLogiled == true) {
			LogiLedSetLighting(channel(red), channel(green), channel(blue));
		}
	}

	/**
	 * Sets all of the keys on the keyboard to the specified color.
	 * 
	 * @param color
	 *            the color.
	 */
	@OrionMethod
	public static void setKeyboardColor(Color color) {
		if (color != null) {
			setKeyboardColor(color.getRed(), color.getGreen(), color.getBlue());
		} else {
			setKeyboardColor(0x00, 0x00, 0x00);
		}
	}

	/**
	 * Sets all of the keys on the keyboard to the specified color.
	 * 
	 * @param color
	 *            the color.
	 */
	@OrionMethod
	public static void setKeyboardColor(int color) {
		setKeyboardColor(new Color(color));
	}

	/**
	 * Sets all of the keys on the keyboard to the colors based on the specified
	 * bitmap and given G key colors.
	 * 
	 * @param bitmap
	 *            the bitmap.
	 * @param gKeys
	 *            the colors that should be set for the G keys. The order of the
	 *            keys goes as {@link KEY_ORION_G1}, {@link KEY_ORION_G2},
	 *            {@link KEY_ORION_G3}, {@link KEY_ORION_G4},
	 *            {@link KEY_ORION_G5}, {@link KEY_ORION_G6},
	 *            {@link KEY_ORION_G9}, {@link KEY_ORION_G8},
	 *            {@link KEY_ORION_LOGO}, and {@link KEY_ORION_BADGE}.
	 * @throws NullPointerException
	 *             if the <code>bitmap</code> is <code>null</code>.
	 */
	@OrionMethod
	public static void setKeyboardColor(byte[] bitmap, Color... gKeys) throws NullPointerException {
		if (bitmap == null) {
			throw new NullPointerException("Bitmap cannot be null");
		}
		LogiLedSetLightingFromBitmap(bitmap);
		if (gKeys != null) {
			for (int i = 0; i < LOGI_LED_G_KEY_COUNT; i++) {
				if (i < gKeys.length) {
					if (i == 0) {
						setKeyColor(KeyboardController.KEY_ORION_G1, gKeys[i]);
					} else if (i == 1) {
						setKeyColor(KeyboardController.KEY_ORION_G2, gKeys[i]);
					} else if (i == 2) {
						setKeyColor(KeyboardController.KEY_ORION_G3, gKeys[i]);
					} else if (i == 3) {
						setKeyColor(KeyboardController.KEY_ORION_G4, gKeys[i]);
					} else if (i == 4) {
						setKeyColor(KeyboardController.KEY_ORION_G5, gKeys[i]);
					} else if (i == 5) {
						setKeyColor(KeyboardController.KEY_ORION_G6, gKeys[i]);
					} else if (i == 6) {
						setKeyColor(KeyboardController.KEY_ORION_G7, gKeys[i]);
					} else if (i == 7) {
						setKeyColor(KeyboardController.KEY_ORION_G8, gKeys[i]);
					} else if (i == 8) {
						setKeyColor(KeyboardController.KEY_ORION_G9, gKeys[i]);
					} else if (i == 9) {
						setKeyColor(KeyboardController.KEY_ORION_LOGO, gKeys[i]);
					} else if (i == 10) {
						setKeyColor(KeyboardController.KEY_ORION_BADGE, gKeys[i]);
					}
				} else {
					if (i == 0) {
						setKeyColor(KeyboardController.KEY_ORION_G1, Color.BLUE);
					} else if (i == 1) {
						setKeyColor(KeyboardController.KEY_ORION_G2, Color.BLUE);
					} else if (i == 2) {
						setKeyColor(KeyboardController.KEY_ORION_G3, Color.BLUE);
					} else if (i == 3) {
						setKeyColor(KeyboardController.KEY_ORION_G4, Color.BLUE);
					} else if (i == 4) {
						setKeyColor(KeyboardController.KEY_ORION_G5, Color.BLUE);
					} else if (i == 5) {
						setKeyColor(KeyboardController.KEY_ORION_G6, Color.BLUE);
					} else if (i == 6) {
						setKeyColor(KeyboardController.KEY_ORION_G7, Color.BLUE);
					} else if (i == 7) {
						setKeyColor(KeyboardController.KEY_ORION_G8, Color.BLUE);
					} else if (i == 8) {
						setKeyColor(KeyboardController.KEY_ORION_G9, Color.BLUE);
					} else if (i == 9) {
						setKeyColor(KeyboardController.KEY_ORION_LOGO, Color.BLUE);
					} else if (i == 10) {
						setKeyColor(KeyboardController.KEY_ORION_BADGE, Color.BLUE);
					}
				}
			}
		} else {
			for (int i = 0; i < LOGI_LED_G_KEY_COUNT; i++) {
				if (i == 0) {
					setKeyColor(KeyboardController.KEY_ORION_G1, Color.BLUE);
				} else if (i == 1) {
					setKeyColor(KeyboardController.KEY_ORION_G2, Color.BLUE);
				} else if (i == 2) {
					setKeyColor(KeyboardController.KEY_ORION_G3, Color.BLUE);
				} else if (i == 3) {
					setKeyColor(KeyboardController.KEY_ORION_G4, Color.BLUE);
				} else if (i == 4) {
					setKeyColor(KeyboardController.KEY_ORION_G5, Color.BLUE);
				} else if (i == 5) {
					setKeyColor(KeyboardController.KEY_ORION_G6, Color.BLUE);
				} else if (i == 6) {
					setKeyColor(KeyboardController.KEY_ORION_G7, Color.BLUE);
				} else if (i == 7) {
					setKeyColor(KeyboardController.KEY_ORION_G8, Color.BLUE);
				} else if (i == 8) {
					setKeyColor(KeyboardController.KEY_ORION_G9, Color.BLUE);
				} else if (i == 9) {
					setKeyColor(KeyboardController.KEY_ORION_LOGO, Color.BLUE);
				} else if (i == 10) {
					setKeyColor(KeyboardController.KEY_ORION_BADGE, Color.BLUE);
				}
			}
		}
	}

	/**
	 * Sets all of the keys on the keyboard to the colors based on the specified
	 * bitmap and given G key colors.
	 * 
	 * @param bitmap
	 *            the bitmap.
	 * @param gKeys
	 *            the colors that should be set for the G keys. The order of the
	 *            keys goes as {@link KEY_ORION_G1}, {@link KEY_ORION_G2},
	 *            {@link KEY_ORION_G3}, {@link KEY_ORION_G4},
	 *            {@link KEY_ORION_G5}, {@link KEY_ORION_G6},
	 *            {@link KEY_ORION_G9}, {@link KEY_ORION_G8},
	 *            {@link KEY_ORION_LOGO}, and {@link KEY_ORION_BADGE}.
	 * @throws NullPointerException
	 *             if the <code>bitmap</code> is <code>null</code>.
	 */
	@OrionMethod
	public static void setKeyboardColor(byte[] bitmap, int... gKeys) throws NullPointerException {
		Color[] gKeysColor = new Color[gKeys.length];
		if (gKeys != null) {
			for (int i = 0; i < gKeysColor.length; i++) {
				gKeysColor[i] = new Color(gKeys[i]);
			}
		}
		setKeyboardColor(bitmap, gKeysColor.length > 0 ? gKeysColor : null);
	}

	/**
	 * Sets all of the keys on the keyboard color from the specified colors and
	 * given G key colors.
	 * 
	 * @param colors.
	 *            the colors.
	 * @param gKeys
	 *            the colors that should be set for the G keys. The order of the
	 *            keys goes as {@link KEY_ORION_G1}, {@link KEY_ORION_G2},
	 *            {@link KEY_ORION_G3}, {@link KEY_ORION_G4},
	 *            {@link KEY_ORION_G5}, {@link KEY_ORION_G6},
	 *            {@link KEY_ORION_G9}, {@link KEY_ORION_G8},
	 *            {@link KEY_ORION_LOGO}, and {@link KEY_ORION_BADGE}.
	 * @throws NullPointerException
	 *             if the <code>colors</code> are <code>null</code>.
	 * @throws RuntimeException
	 *             if an I/O error occurs despite the fact it should never
	 *             happen.
	 */
	@OrionMethod
	public static void setKeyboardColor(Color[] colors, Color... gKeys) throws NullPointerException, RuntimeException {
		if (colors == null) {
			throw new NullPointerException("Colors cannot be null");
		}
		try {
			ByteArrayOutputStream rgbBytes = new ByteArrayOutputStream();
			DataOutputStream rgbStream = new DataOutputStream(rgbBytes);
			for (int i = 0; i < colors.length; i++) {
				if (colors[i] != null) {
					rgbStream.writeByte(colors[i].getBlue());
					rgbStream.writeByte(colors[i].getGreen());
					rgbStream.writeByte(colors[i].getRed());
					rgbStream.writeByte(colors[i].getAlpha());
				} else {
					rgbStream.writeInt(0x00000000);
				}
			}
			setKeyboardColor(rgbBytes.toByteArray(), gKeys);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Sets all of the keys on the keyboard color from the specified colors and
	 * given G key colors.
	 * 
	 * @param colors.
	 *            the colors.
	 * @param gKeys
	 *            the colors that should be set for the G keys. The order of the
	 *            keys goes as {@link KEY_ORION_G1}, {@link KEY_ORION_G2},
	 *            {@link KEY_ORION_G3}, {@link KEY_ORION_G4},
	 *            {@link KEY_ORION_G5}, {@link KEY_ORION_G6},
	 *            {@link KEY_ORION_G9}, {@link KEY_ORION_G8},
	 *            {@link KEY_ORION_LOGO}, and {@link KEY_ORION_BADGE}.
	 * @throws NullPointerException
	 *             if the <code>colors</code> are <code>null</code>.
	 * @throws RuntimeException
	 *             if an I/O error occurs despite the fact it should never
	 *             happen.
	 */
	@OrionMethod
	public static void setKeyboardColor(int[] colors, Color... gKeys) throws NullPointerException, RuntimeException {
		if (colors == null) {
			throw new NullPointerException("Colors cannot be null");
		}
		Color[] rgba = new Color[colors.length];
		for (int i = 0; i < colors.length; i++) {
		}
		setKeyboardColor(rgba, gKeys);
	}

	/**
	 * Sets all of the keys on the keyboard color from the specified colors and
	 * given G key colors.
	 * 
	 * @param colors
	 *            the colors.
	 * @param gKeys
	 *            the colors that should be set for the G keys. The order of the
	 *            keys goes as {@link KEY_ORION_G1}, {@link KEY_ORION_G2},
	 *            {@link KEY_ORION_G3}, {@link KEY_ORION_G4},
	 *            {@link KEY_ORION_G5}, {@link KEY_ORION_G6},
	 *            {@link KEY_ORION_G9}, {@link KEY_ORION_G8},
	 *            {@link KEY_ORION_LOGO}, and {@link KEY_ORION_BADGE}.
	 */
	@OrionMethod
	public static void setKeyboardColor(int[] colors, int... gKeys) {
		Color[] gKeysRgba = new Color[gKeys.length];
		for (int i = 0; i < gKeysRgba.length; i++) {
			gKeysRgba[i] = new Color(gKeys[i]);
		}
		setKeyboardColor(colors, gKeysRgba);
	}

	/**
	 * Sets all of the keys on the keyboard color from the specified image and
	 * given G key colors.
	 * <p>
	 * This image should be used sparingly, as converting a
	 * {@link BufferedImage} to its scaled counterpart is very cost performant.
	 * 
	 * @param image
	 *            the image.
	 * @param gKeys
	 *            the colors that should be set for the G keys. The order of the
	 *            keys goes as {@link KEY_ORION_G1}, {@link KEY_ORION_G2},
	 *            {@link KEY_ORION_G3}, {@link KEY_ORION_G4},
	 *            {@link KEY_ORION_G5}, {@link KEY_ORION_G6},
	 *            {@link KEY_ORION_G9}, {@link KEY_ORION_G8},
	 *            {@link KEY_ORION_LOGO}, and {@link KEY_ORION_BADGE}.
	 * @throws NullPointerException
	 *             if the <code>image</code> is <code>null</code>.
	 */
	@OrionMethod
	public static void setKeyboardColor(BufferedImage image, Color... gKeys) {
		// Scale image if required
		if (image.getWidth() != LOGI_LED_BITMAP_WIDTH || image.getHeight() != LOGI_LED_BITMAP_HEIGHT) {
			Image scaled = image.getScaledInstance(LOGI_LED_BITMAP_WIDTH, LOGI_LED_BITMAP_HEIGHT, Image.SCALE_FAST);
			BufferedImage bufferedScaled = new BufferedImage(LOGI_LED_BITMAP_WIDTH, LOGI_LED_BITMAP_HEIGHT, BufferedImage.TYPE_INT_ARGB);
			Graphics2D scaledGraphics = bufferedScaled.createGraphics();
			scaledGraphics.drawImage(scaled, 0, 0, null);
			scaledGraphics.dispose();
			image = bufferedScaled;
		}

		// Convert image to bitmap and set lighting
		ByteBuffer buffer = Texture.getBuffer(image);
		byte[] bitmap = new byte[buffer.capacity()];
		buffer.get(bitmap);
		setKeyboardColor(bitmap, gKeys);
	}

	/**
	 * Sets all of the keys on the keyboard color from the specified image and
	 * given G key colors.
	 * <p>
	 * This image should be used sparingly, as converting a
	 * {@link BufferedImage} to its scaled counterpart is very cost performant.
	 * 
	 * @param image
	 *            the image.
	 * @param gKeys
	 *            the colors that should be set for the G keys. The order of the
	 *            keys goes as {@link KEY_ORION_G1}, {@link KEY_ORION_G2},
	 *            {@link KEY_ORION_G3}, {@link KEY_ORION_G4},
	 *            {@link KEY_ORION_G5}, {@link KEY_ORION_G6},
	 *            {@link KEY_ORION_G9}, {@link KEY_ORION_G8},
	 *            {@link KEY_ORION_LOGO}, and {@link KEY_ORION_BADGE}.
	 * @throws NullPointerException
	 *             if the <code>image</code> is <code>null</code>.
	 */
	@OrionMethod
	public static void setKeyboardColor(BufferedImage image, int... gKeys) throws NullPointerException {
		if (image == null) {
			throw new NullPointerException("Image cannot be null");
		}

		// Scale image if required
		if (image.getWidth() != LOGI_LED_BITMAP_WIDTH || image.getHeight() != LOGI_LED_BITMAP_HEIGHT) {
			Image scaled = image.getScaledInstance(LOGI_LED_BITMAP_WIDTH, LOGI_LED_BITMAP_HEIGHT, Image.SCALE_FAST);
			BufferedImage bufferedScaled = new BufferedImage(LOGI_LED_BITMAP_WIDTH, LOGI_LED_BITMAP_HEIGHT, BufferedImage.TYPE_INT_ARGB);
			Graphics2D scaledGraphics = bufferedScaled.createGraphics();
			scaledGraphics.drawImage(scaled, 0, 0, null);
			scaledGraphics.dispose();
			image = bufferedScaled;
		}

		// Convert image to bitmap and set lighting
		ByteBuffer buffer = Texture.getBuffer(image);
		byte[] bitmap = new byte[buffer.capacity()];
		buffer.get(bitmap);
		setKeyboardColor(bitmap, gKeys);
	}

	/**
	 * Returns a new {@link BufferedImage} of the type
	 * {@link BufferedImage#TYPE_INT_ARGB TYPE_INT_ARGB} type with the dimension
	 * that an image would be resized to if it did not have the normal bitmap
	 * dimensions of a keyboard lighting layout.
	 * 
	 * @return a bitmap image suitable for setting the keyboard lighting layout.
	 */
	public static BufferedImage createKeyboardBitmapImage() {
		return new BufferedImage(LOGI_LED_BITMAP_WIDTH, LOGI_LED_BITMAP_HEIGHT, BufferedImage.TYPE_INT_ARGB);
	}

	/**
	 * Returns a new <code>byte[]</code> with the size that a bitmap would
	 * normally be when inputting to {@link #setKeyboardColor(byte[], int...)}.
	 * 
	 * @return a bitmap suitable for setting the keyboard lighting layout.
	 */
	public static byte[] createKeyboardBitmap() {
		return new byte[LOGI_LED_BITMAP_WIDTH * LOGI_LED_BITMAP_HEIGHT * 4];
	}

	/**
	 * Flashes all of the keys on the keyboard to the specified RGB channel
	 * values at the given duration and specified interval.
	 * 
	 * @param red
	 *            the red channel.
	 * @param green
	 *            the green channel.
	 * @param blue
	 *            the blue channel.
	 * @param duration
	 *            how long the keys should flash.
	 * @param interval
	 *            how long the keys should stay lit and unlit.
	 */
	@OrionMethod
	public static void flashKeyboardColor(int red, int green, int blue, int duration, int interval) {
		if (hasLogiled == true) {
			LogiLedSaveCurrentLighting();
			LogiLedFlashLighting(channel(red), channel(green), channel(blue), duration, interval);
		}
	}

	/**
	 * Flashes all of the keys on the keyboard to the specified color at the
	 * given duration and specified interval.
	 * 
	 * @param color
	 *            the color.
	 * @param duration
	 *            how long the keys should flash.
	 * @param interval
	 *            how long the keys should stay lit and unlit.
	 * @throws NullPointerException
	 *             if the <code>color</code> is <code>null</code>.
	 */
	@OrionMethod
	public static void flashKeyboardColor(Color color, int duration, int interval) throws NullPointerException {
		if (color == null) {
			throw new NullPointerException("Color cannot be null");
		}
		flashKeyboardColor(color.getRed(), color.getGreen(), color.getBlue(), duration, interval);
	}

	/**
	 * Flashes all of the keys on the keyboard to the specified color at the
	 * given duration and specified interval.
	 * 
	 * @param color
	 *            the color.
	 * @param duration
	 *            how long the keys should flash.
	 * @param interval
	 *            how long the keys should stay lit and unlit.
	 */
	@OrionMethod
	public static void flashKeyboardColor(int color, int duration, int interval) {
		flashKeyboardColor(new Color(color), duration, interval);
	}

	/**
	 * Pulses all of the keys on the keyboard to the specified RGB channel
	 * values at the given duration and specified interval.
	 * 
	 * @param red
	 *            the red channel.
	 * @param green
	 *            the green channel.
	 * @param blue
	 *            the blue channel.
	 * @param duration
	 *            how long the keys should pulse.
	 * @param interval
	 *            how long it should take for the keys to fade in to the color
	 *            and fade back out.
	 */
	@OrionMethod
	public static void pulseKeyboardColor(int red, int green, int blue, int duration, int interval) {
		if (hasLogiled == true) {
			LogiLedSaveCurrentLighting();
			LogiLedPulseLighting(channel(red), channel(green), channel(blue), duration, interval);
		}
	}

	/**
	 * Pulses all of the keys on the keyboard to the specified color at the
	 * given duration and specified interval.
	 * 
	 * @param color
	 *            the color.
	 * @param duration
	 *            how long the keys should pulse.
	 * @param interval
	 *            how long it should take for the keys to fade in to the color
	 *            and fade back out.
	 * @throws NullPointerException
	 *             if the <code>color</code> is <code>null</code>.
	 */
	@OrionMethod
	public static void pulseKeyboardColor(Color color, int duration, int interval) throws NullPointerException {
		if (color == null) {
			throw new NullPointerException("Color cannot be null");
		}
		pulseKeyboardColor(color.getRed(), color.getGreen(), color.getBlue(), duration, interval);
	}

	/**
	 * Pulses all of the keys on the keyboard to the specified color at the
	 * given duration and specified interval.
	 * 
	 * @param color
	 *            the color.
	 * @param duration
	 *            how long the keys should pulse.
	 * @param interval
	 *            how long it should take for the keys to fade in to the color
	 *            and fade back out.
	 */
	@OrionMethod
	public static void pulseKeyboardColor(int color, int duration, int interval) {
		pulseKeyboardColor(new Color(color), duration, interval);
	}

	/**
	 * Pulses all of the keys on the keyboard to and from the RGB channel values
	 * at the given duration and specified interval.
	 * 
	 * @param startRed
	 *            the red channel to start with.
	 * @param startGreen
	 *            the green channel to start with.
	 * @param startBlue
	 *            the blue channel to start with.
	 * @param finishRed
	 *            the red channel to finish with.
	 * @param finishGreen
	 *            the green channel to finish with.
	 * @param finishBlue
	 *            the blue channel to finish with.
	 * @param duration
	 *            how long the keys should pulse.
	 * @param infinite
	 *            <code>true</code> if the keyboard keys should keep pulsing
	 *            after the first pulse, <code>false</code> otherwise.
	 */
	@OrionMethod
	public static void pulseKeyboardColor(int startRed, int startGreen, int startBlue, int finishRed, int finishGreen, int finishBlue, int duration, boolean infinite) {
		if (hasLogiled == true) {
			LogiLedSaveCurrentLighting();
			for (int key = 0; key < NUM_PERIOD; key++) {
				LogiLedPulseSingleKey(key, channel(startRed), channel(startGreen), channel(startBlue), channel(finishRed), channel(finishGreen), channel(finishBlue), duration,
						infinite);
			}
		}
	}

	/**
	 * Pulses all of the keys on the keyboard to and from the specified colors
	 * at the given duration and specified interval.
	 * 
	 * @param startColor
	 *            the color to start with.
	 * @param finishColor
	 *            the color to finish with.
	 * @param duration
	 *            how long the keys should pulse.
	 * @param infinite
	 *            <code>true</code> if the keyboard keys should keep pulsing
	 *            after the first pulse, <code>false</code> otherwise.
	 */
	@OrionMethod
	public static void pulseKeyboardColor(Color startColor, Color finishColor, int duration, boolean infinite) {
		pulseKeyboardColor(startColor.getRed(), startColor.getGreen(), startColor.getBlue(), finishColor.getRed(), finishColor.getGreen(), finishColor.getBlue(), duration,
				infinite);
	}

	/**
	 * Pulses all of the keys on the keyboard to and from the specified colors
	 * at the given duration and specified interval.
	 * 
	 * @param startColor
	 *            the color to start with.
	 * @param finishColor
	 *            the color to finish with.
	 * @param duration
	 *            how long the keys should pulse.
	 * @param infinite
	 *            <code>true</code> if the keyboard keys should keep pulsing
	 *            after the first pulse, <code>false</code> otherwise.
	 */
	@OrionMethod
	public static void pulseKeyboardColor(int startColor, int finishColor, int duration, boolean infinite) {
		pulseKeyboardColor(new Color(startColor), new Color(finishColor), duration, infinite);
	}

	/**
	 * Resets all of the keyboard colors.
	 */
	@OrionMethod
	public static void resetKeyboardColor() {
		if (hasLogiled == true) {
			setKeyboardColor(0x00, 0x00, 0x00);
		}
	}

	/**
	 * Resets all of the keyboard effects and restores the keyboard colors.
	 */
	@OrionMethod
	public static void stopKeyboardEffects() {
		if (hasLogiled == true) {
			LogiLedStopEffects();
			LogiLedRestoreLighting();
		}
	}

	/**
	 * Sets the color of the specified key on the keyboard to the given RGB
	 * channel values.
	 * 
	 * @param key
	 *            the key.
	 * @param red
	 *            the red channel.
	 * @param green
	 *            the green channel.
	 * @param blue
	 *            the blue channel.
	 * @throws NullPointerException
	 *             if the <code>key</code> is <code>null</code>.
	 */
	@OrionMethod
	public static void setKeyColor(KeyboardKey key, int red, int green, int blue) throws NullPointerException {
		if (key == null) {
			throw new NullPointerException("Key cannot be null");
		} else if (hasLogiled == true) {
			LogiLedSetLightingForKeyWithKeyName(key.getLogiledId(), channel(red), channel(green), channel(blue));
		}
	}

	/**
	 * Sets the color of the specified key on the keyboard to the given color.
	 * 
	 * @param key
	 *            the key.
	 * @param color
	 *            the color.
	 * @throws NullPointerException
	 *             if the <code>key</code> is <code>null</code>.
	 */
	@OrionMethod
	public static void setKeyColor(KeyboardKey key, Color color) throws NullPointerException {
		if (color != null) {
			setKeyColor(key, color.getRed(), color.getGreen(), color.getBlue());
		} else {
			setKeyColor(key, 0x00, 0x00, 0x00);
		}
	}

	/**
	 * Sets the color of the specified key on the keyboard to the given color.
	 * 
	 * @param key
	 *            the key.
	 * @param color
	 *            the color.
	 * @throws NullPointerException
	 *             if the <code>key</code> is <code>null</code>.
	 */
	@OrionMethod
	public static void setKeyColor(KeyboardKey key, int color) throws NullPointerException {
		setKeyColor(key, new Color(color));
	}

	/**
	 * Flashes the specified key on the keyboard to the specified RGB channel
	 * values at the given duration and specified interval.
	 * 
	 * @param key
	 *            the key.
	 * @param red
	 *            the red channel.
	 * @param green
	 *            the green channel.
	 * @param blue
	 *            the blue channel.
	 * @param duration
	 *            how long the key should flash.
	 * @param interval
	 *            how long the key should stay lit and unlit.
	 * @throws NullPointerException
	 *             if the <code>key</code> is <code>null</code>.
	 */
	@OrionMethod
	public static void flashKeyColor(KeyboardKey key, int red, int green, int blue, int duration, int interval) throws NullPointerException {
		if (key == null) {
			throw new NullPointerException("Key cannot be null");
		} else if (hasLogiled == true) {
			LogiLedSaveLightingForKey(key.getLogiledId());
			LogiLedFlashSingleKey(key.getLogiledId(), channel(red), channel(green), channel(blue), duration, interval);
		}
	}

	/**
	 * Flashes the specified key on the keyboard to the specified color at the
	 * given duration and specified interval.
	 * 
	 * @param key
	 *            the key.
	 * @param color
	 *            the color.
	 * @param duration
	 *            how long the key should flash.
	 * @param interval
	 *            how long the key should stay lit and unlit.
	 * @throws NullPointerException
	 *             if the <code>key</code> is <code>null</code>.
	 */
	@OrionMethod
	public static void flashKeyColor(KeyboardKey key, Color color, int duration, int interval) throws NullPointerException {
		if (color != null) {
			flashKeyColor(key, color.getRed(), color.getGreen(), color.getBlue(), duration, interval);
		} else {
			flashKeyColor(key, 0x00, 0x00, 0x00, duration, interval);
		}
	}

	/**
	 * Flashes the specified key on the keyboard to the specified color at the
	 * given duration and specified interval.
	 * 
	 * @param key
	 *            the key.
	 * @param color
	 *            the color.
	 * @param duration
	 *            how long the key should flash.
	 * @param interval
	 *            how long the key should stay lit and unlit.
	 * @throws NullPointerException
	 *             if the <code>key</code> is <code>null</code>.
	 */
	@OrionMethod
	public static void flashKeyColor(KeyboardKey key, int color, int duration, int interval) throws NullPointerException {
		flashKeyColor(key, new Color(color), duration, interval);
	}

	/**
	 * Pulses the specified key on the keyboard to and from the RGB channel
	 * values at the given duration and specified interval.
	 * 
	 * @param key
	 *            the key.
	 * @param startRed
	 *            the red channel to start with.
	 * @param startGreen
	 *            the green channel to start with.
	 * @param startBlue
	 *            the blue channel to start with.
	 * @param finishRed
	 *            the red channel to finish with.
	 * @param finishGreen
	 *            the green channel to finish with.
	 * @param finishBlue
	 *            the blue channel to finish with.
	 * @param duration
	 *            how long the key should pulse.
	 * @param infinite
	 *            <code>true</code> if the keyboard key should keep pulsing
	 *            after the first pulse, <code>false</code> otherwise.
	 * @throws NullPointerException
	 *             if the <code>key</code> is <code>null</code>.
	 */
	@OrionMethod
	public static void pulseKeyColor(KeyboardKey key, int startRed, int startGreen, int startBlue, int finishRed, int finishGreen, int finishBlue, int duration, boolean infinite)
			throws NullPointerException {
		if (key == null) {
			throw new NullPointerException("Key cannot be null");
		} else if (hasLogiled == true) {
			LogiLedSaveLightingForKey(key.getLogiledId());
			LogiLedPulseSingleKey(key.getLogiledId(), channel(startRed), channel(startGreen), channel(startBlue), channel(finishRed), channel(finishGreen), channel(finishBlue),
					duration, infinite);
		}
	}

	/**
	 * Pulses the specified key on the keyboard to and from the RGB channel
	 * values at the given duration and specified interval.
	 * 
	 * @param key
	 *            the key.
	 * @param startColor
	 *            the color to start with.
	 * @param finishColor
	 *            the color to finish with.
	 * @param duration
	 *            how long the key should pulse.
	 * @param infinite
	 *            <code>true</code> if the keyboard key should keep pulsing
	 *            after the first pulse, <code>false</code> otherwise.
	 * @throws NullPointerException
	 *             if the <code>key</code> is <code>null</code>.
	 */
	@OrionMethod
	public static void pulseKeyColor(KeyboardKey key, Color startColor, Color finishColor, int duration, boolean infinite) throws NullPointerException {
		if (startColor == null && finishColor == null) {
			pulseKeyColor(key, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, duration, infinite);
		} else if (startColor == null) {
			pulseKeyColor(key, 0x00, 0x00, 0x00, finishColor.getRed(), finishColor.getGreen(), finishColor.getBlue(), duration, infinite);
		} else if (finishColor == null) {
			pulseKeyColor(key, startColor.getRed(), startColor.getGreen(), startColor.getBlue(), 0x00, 0x00, 0x00, duration, infinite);
		} else {
			pulseKeyColor(key, startColor.getRed(), startColor.getGreen(), startColor.getBlue(), finishColor.getRed(), finishColor.getGreen(), finishColor.getBlue(), duration,
					infinite);
		}
	}

	/**
	 * Pulses the specified key on the keyboard to and from the RGB channel
	 * values at the given duration and specified interval.
	 * 
	 * @param key
	 *            the key.
	 * @param startColor
	 *            the color to start with.
	 * @param finishColor
	 *            the color to finish with.
	 * @param duration
	 *            how long the key should pulse.
	 * @param infinite
	 *            <code>true</code> if the keyboard key should keep pulsing
	 *            after the first pulse, <code>false</code> otherwise.
	 * @throws NullPointerException
	 *             if the <code>key</code> is <code>null</code>.
	 */
	@OrionMethod
	public static void pulseKeyColor(KeyboardKey key, int startColor, int finishColor, int duration, boolean infinite) throws NullPointerException {
		pulseKeyColor(key, new Color(startColor), new Color(finishColor), duration, infinite);
	}

	/**
	 * Resets the color of specified key.
	 * 
	 * @param key
	 *            the key to reset.
	 * @throws NullPointerException
	 *             if the <code>key</code> is <code>null</code>.
	 */
	@OrionMethod
	public static void resetKeyColor(KeyboardKey key) throws NullPointerException {
		if (key == null) {
			throw new NullPointerException("Key cannot be null");
		} else if (hasLogiled == true) {
			setKeyColor(key, 0x00, 0x00, 0x00);
		}
	}

	/**
	 * Resets the effects and restores the color of the specified key.
	 * 
	 * @param key
	 *            the key to reset.
	 * @throws NullPointerException
	 *             if the <code>key</code> is <code>null</code>.
	 */
	@OrionMethod
	public static void resetKeyEffects(KeyboardKey key) throws NullPointerException {
		if (key == null) {
			throw new NullPointerException("Key cannot be null");
		} else if (hasLogiled == true) {
			LogiLedStopEffectsOnKey(key.getLogiledId());
			LogiLedRestoreLightingForKey(key.getLogiledId());
		}
	}

	/**
	 * Excludes the speicified keys from the effects of the
	 * {@link #setKeyboardColor(BufferedImage, int...)} and
	 * {@link #setKeyboardColor(BufferedImage, Color...)} methods.
	 * 
	 * @param keys
	 *            the keys to exclude.
	 * @throws NullPointerException
	 *             if the <code>keys</code> or one of the keys are
	 *             <code>null</code>.
	 */
	@OrionMethod
	public static void excludeKeysFromBitmap(KeyboardKey... keys) {
		if (keys == null) {
			throw new NullPointerException("Keys cannot be null");
		} else if (hasLogiled == true) {
			int[] convertedKeys = new int[keys.length];
			for (int i = 0; i < convertedKeys.length; i++) {
				if (keys[i] == null) {
					throw new NullPointerException("Key cannot be null");
				}
				convertedKeys[i] = keys[i].getLogiledId();
			}
			LogiLedExcludeKeysFromBitmap(convertedKeys);
		}
	}

	private static boolean hasLogiled = false;

	private Camera camera;

	/**
	 * Creates a keyboard and mouse controller.
	 * 
	 * @param handler
	 *            the input handler.
	 * @throws NullPointerException
	 *             if the input <code>handler</code> is <code>null</code>.
	 */
	public KeyboardController(InputHandler handler) throws NullPointerException {
		super(handler);
		this.setButtons(KEY_SPACE, KEY_APOSTROPHE, KEY_COMMA, KEY_MINUS, KEY_PERIOD, KEY_SLASH, KEY_ZERO, KEY_ONE, KEY_TWO, KEY_THREE, KEY_FOUR, KEY_FIVE, KEY_SIX, KEY_SEVEN,
				KEY_EIGHT, KEY_NINE, KEY_SEMICOLON, KEY_EQUALS, KEY_A, KEY_B, KEY_C, KEY_D, KEY_E, KEY_F, KEY_G, KEY_H, KEY_I, KEY_J, KEY_K, KEY_L, KEY_M, KEY_N, KEY_O, KEY_P,
				KEY_Q, KEY_R, KEY_S, KEY_T, KEY_U, KEY_V, KEY_W, KEY_X, KEY_Y, KEY_Z, KEY_LEFT_BRACKET, KEY_BACKSLASH, KEY_RIGHT_BRACKET, KEY_GRAVE_ACCENT, KEY_ESCAPE, KEY_ENTER,
				KEY_TAB, KEY_BACKSPACE, KEY_INSERT, KEY_DELETE, KEY_RIGHT, KEY_LEFT, KEY_UP, KEY_DOWN, KEY_PAGE_UP, KEY_PAGE_DOWN, KEY_HOME, KEY_END, KEY_CAPS_LOCK,
				KEY_SCROLL_LOCK, KEY_NUM_LOCK, KEY_PRINT_SCREEN, KEY_KEY_PAUSE, KEY_F1, KEY_F2, KEY_F3, KEY_F4, KEY_F5, KEY_F6, KEY_F7, KEY_F8, KEY_F9, KEY_F10, KEY_F11, KEY_F12,
				KEY_F13, KEY_F14, KEY_F15, KEY_F16, KEY_F17, KEY_F18, KEY_F19, KEY_F20, KEY_F21, KEY_F22, KEY_F23, KEY_F24, KEY_F25, KEY_KEYPAD_ZERO, KEY_KEYPAD_ONE,
				KEY_KEYPAD_TWO, KEY_KEYPAD_THREE, KEY_KEYPAD_FOUR, KEY_KEYPAD_FIVE, KEY_KEYPAD_SIX, KEY_KEYPAD_SEVEN, KEY_KEYPAD_EIGHT, KEY_KEYPAD_NINE, KEY_KEYPAD_DECIMAL,
				KEY_KEYPAD_DIVIDE, KEY_KEYPAD_MULTIPLY, KEY_KEYPAD_SUBTRACT, KEY_KEYPAD_ADD, KEY_KEYPAD_ENTER, KEY_KEYPAD_EQUALS, KEY_LEFT_SHIFT, KEY_LEFT_CONTROL, KEY_LEFT_ALT,
				KEY_LEFT_SUPER, KEY_RIGHT_SHIFT, KEY_RIGHT_CONTROL, KEY_RIGHT_ALT, KEY_RIGHT_SUPER, KEY_MENU, BUTTON_MOUSE_LEFT, BUTTON_MOUSE_RIGHT, BUTTON_MOUSE_MIDDLE,
				SCROLL_MOUSE_UP, SCROLL_MOUSE_DOWN, SCROLL_MOUSE_LEFT, SCROLL_MOUSE_RIGHT, KEY_ORION_G1, KEY_ORION_G2, KEY_ORION_G3, KEY_ORION_G4, KEY_ORION_G5, KEY_ORION_G6,
				KEY_ORION_G7, KEY_ORION_G8, KEY_ORION_G9, KEY_ORION_LOGO, KEY_ORION_BADGE);
		this.setDirectionalButtons(KEY_UP, KEY_DOWN, KEY_LEFT, KEY_RIGHT);

		// Initialize LogiLED if possible
		boolean enableLogiled = ((KeyboardHandler) handler).shouldEnableLogiled();
		if (enableLogiled == true && hasLogiled == false) {
			hasLogiled = LogiLedInit();
			if (hasLogiled == true) {
				LogiLedSetTargetDevice(LOGI_DEVICETYPE_ALL);
			}
			LOGGER.info("Initialized LogiLED");
		} else if (enableLogiled == false) {
			LOGGER.info("LogiLED was not initialized. To initialize it, set " + KeyboardHandler.ENABLE_LOGILED_NAME + " to " + Boolean.TRUE + " in the Ardenus manifest");
		}
	}

	/**
	 * Returns whether or not the camera has been set.
	 * 
	 * @return <code>true</code> if the camera has been set, <code>false</code>
	 *         otherwise.
	 */
	public boolean hasCamera() {
		return camera != null;
	}

	/**
	 * Sets the camera to be used in the {@link #getMouseX()} and
	 * {@link #getMouseY()} functions.
	 * 
	 * @param camera
	 *            the camera.
	 * @throws NullPointerException
	 *             if the <code>camera</code> is <code>null</code>.
	 * @throws IllegalStateException
	 *             if the camera has already been set.
	 */
	public void setCamera(Camera camera) throws NullPointerException, IllegalStateException {
		if (camera == null) {
			throw new NullPointerException("Camera cannot be null");
		} else if (this.camera != null) {
			throw new IllegalStateException("Camera already set");
		}
		this.camera = camera;
	}

	/**
	 * Returns the keyboard key based on the specified ID.
	 * 
	 * @param key
	 *            the key code.
	 * @return the keyboard key based on the specified ID.
	 */
	public KeyboardKey getKey(int key) {
		return (KeyboardKey) this.getButton(key);
	}

	/**
	 * Returns the mouse X position based on the original window width and game
	 * camera.
	 * 
	 * @return the mouse X position based on the original window width and game
	 *         camera.
	 */
	public double getMouseX() {
		double mouseX = Input.getCurrentWindow().getMouseX();
		if (camera != null) {
			mouseX += camera.getX();
		}
		return mouseX;
	}

	/**
	 * Returns the mouse Y position based on the original window height and game
	 * camera.
	 * 
	 * @return the mouse Y position based on the original window height and game
	 *         camera.
	 */
	public double getMouseY() {
		double mouseY = Input.getCurrentWindow().getMouseY();
		if (camera != null) {
			mouseY += camera.getY();
		}
		return mouseY;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This method will always return <code>0.0F</code>, since the keyboard and
	 * mouse do not have a left analog stick.
	 */
	@Override
	public float getLeftXAxis() {
		return 0.0F;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This method will always return <code>0.0F</code>, since the keyboard and
	 * mouse do not have a left analog stick.
	 */
	@Override
	public float getLeftYAxis() {
		return 0.0F;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This method will always return <code>0.0F</code>, since the keyboard and
	 * mouse do not have a right analog stick.
	 */
	@Override
	public float getRightXAxis() {
		return 0.0F;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This method will always return <code>0.0F</code>, since the keyboard and
	 * mouse do not have a right analog stick.
	 */
	@Override
	public float getRightYAxis() {
		return 0.0F;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This method will always return <code>0.0F</code>, since the keyboard and
	 * mouse do not have a left trigger.
	 */
	@Override
	public float getLeftTriggerZAxis() {
		return 0.0F;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This method will always return <code>0.0F</code>, since the keyboard and
	 * mouse do not have a right trigger.
	 */
	@Override
	public float getRightTriggerZAxis() {
		return 0.0F;
	}

	@Override
	public ControllerType getType() {
		return ControllerType.KEYBOARD;
	}

}
