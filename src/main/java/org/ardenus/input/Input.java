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
package org.ardenus.engine.input;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ardenus.EventList;
import org.ardenus.engine.Ardenus;
import org.ardenus.engine.graphics.Window;
import org.ardenus.engine.input.button.Button;
import org.ardenus.engine.input.button.ButtonSet;
import org.ardenus.engine.input.controller.Controller;
import org.ardenus.engine.input.controller.action.ControllerActionProfile;
import org.ardenus.engine.input.controller.keyboard.KeyboardController;
import org.ardenus.engine.input.handler.InputHandler;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;

/**
 * The input system used by the engine.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.1-SNAPSHOT
 * @see Button
 * @see ButtonSet
 * @see Controller
 * @see org.ardenus.engine.input.controller.ControllerType ControllerType
 * @see org.ardenus.engine.input.controller.axis.ControllerAxis ControllerAxis
 * @see org.ardenus.engine.input.controller.ControllerButton ControllerButton
 * @see org.ardenus.engine.input.controller.action.ControllerAction
 *      ControllerAction
 * @see InputHandler
 */
public final class Input {

	private static final Logger LOGGER = LogManager.getLogger(Input.class);
	private static final long DOUBLE_CLICK_MILLIS = 500L;
	private static final EventList<InputListener> LISTENERS = new EventList<InputListener>();
	private static final HashMap<Button, Boolean> BUTTONS = new HashMap<Button, Boolean>();
	private static final HashMap<Button, Long> PRESS_TIMES = new HashMap<Button, Long>();
	private static final ArrayList<ButtonSet> ENABLED_BUTTON_SETS = new ArrayList<ButtonSet>();
	private static final ArrayList<ControllerActionProfile> GLOBAL_PROFILES = new ArrayList<ControllerActionProfile>();

	/**
	 * The default maximum amount of controllers that can be connected at one
	 * time.
	 */
	public static final int MAX_CONTROLLERS = 16;

	private static boolean initialized;
	private static Window currentWindow;
	private static InputHandler[] inputHandlers;
	private static Controller[] controllers;
	private static boolean overrideKeyboards;
	private static Button selectedButton;

	private Input() {
		// Static class
	}

	/**
	 * Adds an {@link InputListener} to the input system.
	 * <p>
	 * Listeners are used to listen for events that occur relating to input such
	 * as player connection, button presses, and more.
	 * 
	 * @param listener
	 *            the listener to add.
	 * @throws NullPointerException
	 *             if the <code>listener</code> is <code>null</code>.
	 */
	public static void addListener(InputListener listener) throws NullPointerException {
		LISTENERS.add(listener);
		callEvent(lizener -> lizener.onListenerAdd(lizener));
	}

	/**
	 * Removes an {@link InputListener} from the input system.
	 * 
	 * @param listener
	 *            the listener to remove.
	 */
	public static void removeListener(InputListener listener) {
		LISTENERS.remove(listener);
		callEvent(lizener -> lizener.onListenerRemove(lizener));
	}

	/**
	 * Calls the specified event.
	 * 
	 * @param event
	 *            the event to be called.
	 * @throws NullPointerException
	 *             if the <code>event</code> is <code>null</code>
	 * @see InputListener
	 */
	public static void callEvent(Consumer<? super InputListener> event) {
		LISTENERS.call(event);
	}

	/**
	 * Returns whether or not the specified button is pressed.
	 * 
	 * @param button
	 *            the button.
	 * @return <code>true</code> if the button is pressed, <code>false</code>
	 *         otherwise.
	 */
	public static boolean isButtonPressed(Button button) {
		if (!BUTTONS.containsKey(button)) {
			BUTTONS.put(button, false);
		}
		return BUTTONS.get(button);
	}

	/**
	 * Sets the press state of the specified button.
	 * 
	 * @param button
	 *            the button of the button press state to change.
	 * @param pressed
	 *            <code>true</code> if the button should be pressed,
	 *            <code>false</code>otherwise.
	 * @param forceNotification
	 *            <code>true</code> if notification should be forcibly sent,
	 *            <code>false</code> otherwise.
	 * @throws NullPointerException
	 *             if the <code>button</code> is <code>null</code>.
	 */
	public static void pressButton(Button button, boolean pressed, boolean forceNotification) throws NullPointerException {
		if (button == null) {
			throw new NullPointerException("Button cannot be null");
		}
		boolean wasPressed = isButtonPressed(button);
		BUTTONS.put(button, pressed);
		if ((wasPressed == false && pressed == true) || forceNotification == true) {
			callEvent(listener -> listener.onButtonPress(button));

			// Check for double press
			long currentTime = System.currentTimeMillis();
			if (PRESS_TIMES.containsKey(button)) {
				if (currentTime - PRESS_TIMES.get(button) <= DOUBLE_CLICK_MILLIS) {
					callEvent(listener -> listener.onButtonDoublePress(button));
				}
			}
			PRESS_TIMES.put(button, currentTime);
		} else if ((wasPressed == true && pressed == false) || forceNotification == true) {
			callEvent(listener -> listener.onButtonUnpress(button));
		}
	}

	/**
	 * Sets the press state of the specified button.
	 * 
	 * @param button
	 *            the button of the button press state to change.
	 * @param pressed
	 *            <code>true</code> if the button should be pressed,
	 *            <code>false</code> otherwise.
	 * @throws NullPointerException
	 *             if the <code>button</code> is <code>null</code>.
	 */
	public static void pressButton(Button button, boolean pressed) throws NullPointerException {
		pressButton(button, pressed, false);
	}

	/**
	 * Returns whether or not the button set is enabled.
	 * 
	 * @param buttonSet
	 *            the button set.
	 * @return <code>true</code> if the button set is enabled,
	 *         <code>false</code> otherwise.
	 */
	public static boolean isButtonSetEnabled(ButtonSet buttonSet) {
		return ENABLED_BUTTON_SETS.contains(buttonSet);
	}

	/**
	 * Returns the currently enabled button sets.
	 * 
	 * @return the currently enabled button sets.
	 */
	public static ButtonSet[] getCurrentButtonSets() {
		return ENABLED_BUTTON_SETS.toArray(new ButtonSet[ENABLED_BUTTON_SETS.size()]);
	}

	/**
	 * Enables the specified button set.
	 * 
	 * @param buttonSet
	 *            the button set to enable.
	 * @throws NullPointerException
	 *             if the <code>buttonSet</code> is <code>null</code>.
	 */
	public static void enableButtonSet(ButtonSet buttonSet) throws NullPointerException {
		if (buttonSet == null) {
			throw new NullPointerException("Button set cannot be null");
		} else if (!ENABLED_BUTTON_SETS.contains(buttonSet)) {
			ENABLED_BUTTON_SETS.add(buttonSet);
			callEvent(listener -> listener.onButtonSetEnable(buttonSet));
		}
	}

	/**
	 * Disables the specified button set.
	 * 
	 * @param buttonSet
	 *            the button set to disable.
	 */
	public static void disableButtonSet(ButtonSet buttonSet) {
		if (buttonSet != null) {
			if (isButtonSetEnabled(buttonSet)) {
				for (Button button : buttonSet.getButtons()) {
					if (button.isPressed()) {
						pressButton(button, false);
					}
					if (button.isSelectable()) {
						unselectButton();
					}
				}
				ENABLED_BUTTON_SETS.remove(buttonSet);
				callEvent(listener -> listener.onButtonSetDisable(buttonSet));
			}
		}
	}

	/**
	 * Adds the specified global controller action profile.
	 * <p>
	 * All controllers that are currently connected will have the profile
	 * applied to it automatically. Controllers that are connected afterwards
	 * will also have the profile attached to them once they connect.
	 * 
	 * @param profile
	 *            the profile to add.
	 * @throws NullPointerException
	 *             if the <code>profile</code> is <code>null</code>.
	 */
	public static void addGlobalProfile(ControllerActionProfile profile) throws NullPointerException {
		if (profile == null) {
			throw new NullPointerException("Global profile cannot be null");
		} else if (!GLOBAL_PROFILES.contains(profile)) {
			GLOBAL_PROFILES.add(profile);
			for (Controller controller : getControllers()) {
				profile.attach(controller);
			}
			LOGGER.debug("Added global controller action profile with ID \"" + profile.getId() + "\"");
		}
	}

	/**
	 * Removes the specified controller action profile.
	 * <p>
	 * All controllers that are currently connected will have the profile
	 * unapplied from them automatically.
	 * 
	 * @param profile
	 *            the profile to remove.
	 */
	public static void removeGlobalProfile(ControllerActionProfile profile) {
		if (GLOBAL_PROFILES.contains(profile)) {
			GLOBAL_PROFILES.remove(profile);
			for (Controller controller : getControllers()) {
				profile.detach(controller);
			}
			LOGGER.debug("Removed global controller action profile with ID \"" + profile.getId() + "\"");
		}
	}

	/**
	 * Returns whether or not the input manager is initialized.
	 * 
	 * @return <code>true</code> if the input manager is initialized,
	 *         <code>false</code> otherwise.
	 */
	public static boolean isInitialized() {
		return initialized;
	}

	/**
	 * Initializes the input manager.
	 * 
	 * @param maxControllers
	 *            the maximum amount of controllers that can be connected at
	 *            once.
	 * @param window
	 *            the window the input manager will get input data from.
	 * @param handlers
	 *            the input handlers.
	 * @throws IllegalStateException
	 *             if the input manager has already been initialized.
	 * @throws IllegalArgumentException
	 *             if the <code>maxControllers</code> less than <code>1</code>
	 *             or the same input handler is specified more than once.
	 * @throws NullPointerException
	 *             if the <code>window</code>, <code>handlers</code> or one of
	 *             the input handlers are <code>null</code>.
	 */
	public static void init(int maxControllers, Window window, InputHandler... handlers) throws IllegalStateException, IllegalArgumentException, NullPointerException {
		if (initialized == true) {
			throw new IllegalStateException("Input manager has already been initialized");
		} else if (maxControllers < 1) {
			throw new IllegalArgumentException("Max controller count cannot be less than 1");
		} else if (window == null) {
			throw new NullPointerException("Window cannot be null");
		} else if (handlers == null) {
			throw new NullPointerException("Input handlers cannot be null");
		}

		// Validate input listeners
		ArrayList<Class<? extends InputHandler>> handlerClazzes = new ArrayList<Class<? extends InputHandler>>();
		for (InputHandler handler : handlers) {
			if (handler == null) {
				throw new NullPointerException("Input handler cannot be null");
			} else if (handlerClazzes.contains(handler.getClass())) {
				throw new IllegalArgumentException("Cannot have two handlers of the same type");
			}
			handlerClazzes.add(handler.getClass());
		}
		handlerClazzes.clear();

		// Set key callback for window
		GLFW.glfwSetKeyCallback(window.getWindowId(), new GLFWKeyCallbackI() {

			/**
			 * The key was released.
			 */
			private static final int ACTION_RELEASE = 0x00;

			/**
			 * The key was pressed.
			 */
			private static final int ACTION_PRESS = 0x01;

			/**
			 * The key is being held.
			 */
			private static final int ACTION_HOLD = 0x02;

			@Override
			public void invoke(long windowId, int key, int scancode, int action, int mode) {
				if (action == ACTION_PRESS || action == ACTION_HOLD) {
					callEvent(listener -> listener.onKeyPress(key, scancode, mode));
				} else if (action == ACTION_RELEASE) {
					callEvent(listener -> listener.onKeyRelease(key, scancode, mode));
				}
			}
		});

		// Finish initialization
		currentWindow = window;
		inputHandlers = handlers;
		controllers = new Controller[maxControllers];
		overrideKeyboards = true;
		initialized = true;
		Input.addListener(new ProfileEnforcer());
		LOGGER.info("Initialized input");
	}

	/**
	 * Initializes the input manager with the maximum amount of controllers
	 * being set to {@value #MAX_CONTROLLERS}.
	 * 
	 * @param window
	 *            the window the input manager will get input data from.
	 * @param handlers
	 *            the input handlers.
	 * @throws IllegalStateException
	 *             if the input manager has already been initialized.
	 * @throws IllegalArgumentException
	 *             if the same input handler is specified more than once.
	 * @throws NullPointerException
	 *             if the <code>window</code>, <code>handlers</code> or one of
	 *             the input handlers are <code>null</code>.
	 */
	public static void init(Window window, InputHandler... handlers) throws IllegalStateException, IllegalArgumentException, NullPointerException {
		init(MAX_CONTROLLERS, window, handlers);
	}

	/**
	 * Initializes the input manager.
	 * 
	 * @param maxControllers
	 *            the maximum amount of controllers that can be connected at
	 *            once.
	 * @param window
	 *            the window the input manager will get input data from.
	 * @param handlers
	 *            the input handlers classes which all must have a public
	 *            default constructor.
	 * @throws IllegalStateException
	 *             if the input manager has already been initialized.
	 * @throws IllegalArgumentException
	 *             if the same input handler is specified more than once or does
	 *             not have a public default constructor.
	 * @throws NullPointerException
	 *             if the <code>window</code>, <code>handlers</code> or one of
	 *             the input handlers classes are <code>null</code>.
	 */
	@SafeVarargs
	public static void init(int maxControllers, Window window, Class<? extends InputHandler>... handlers)
			throws IllegalStateException, IllegalArgumentException, NullPointerException {
		if (handlers == null) {
			throw new NullPointerException("Input handler classes cannot be null");
		}
		InputHandler[] instantiatedHandlers = new InputHandler[handlers.length];
		for (int i = 0; i < instantiatedHandlers.length; i++) {
			try {
				if (handlers[i] == null) {
					throw new NullPointerException("Input handler class cannot be null");
				}
				instantiatedHandlers[i] = handlers[i].getDeclaredConstructor().newInstance();
			} catch (NoSuchMethodException e) {
				throw new IllegalArgumentException("Input handler class must have default constructor");
			} catch (IllegalAccessException e) {
				throw new IllegalArgumentException("Input handler default cosntructor must be public");
			} catch (InvocationTargetException | InstantiationException e) {
				if (e.getCause() != null) {
					e.getCause().printStackTrace();
				} else {
					e.printStackTrace();
				}
			}
		}
		init(maxControllers, window, instantiatedHandlers);
	}

	/**
	 * Initializes the input manager with the maximum amount of controllers
	 * being set to {@value #MAX_CONTROLLERS}.
	 * 
	 * @param window
	 *            the window the input manager will get input data from.
	 * @param handlers
	 *            the input handlers classes which all must have a public
	 *            default constructor.
	 * @throws IllegalStateException
	 *             if the input manager has already been initialized.
	 * @throws IllegalArgumentException
	 *             if the same input handler is specified more than once or does
	 *             not have a public default constructor.
	 * @throws NullPointerException
	 *             if the <code>window</code>, <code>handlers</code> or one of
	 *             the input handlers classes are <code>null</code>.
	 */
	@SafeVarargs
	public static void init(Window window, Class<? extends InputHandler>... handlers) throws IllegalStateException, IllegalArgumentException, NullPointerException {
		init(MAX_CONTROLLERS, window, handlers);
	}

	/**
	 * Returns the window the input manager gets input data from.
	 * 
	 * @return the window the input manager gets input data from.
	 */
	public static Window getCurrentWindow() {
		return currentWindow;
	}

	/**
	 * Returns whether or not the keyboard should be ignored when calling
	 * {@link #getEmptyPlayerSlot()}.
	 * 
	 * @return <code>true</code> if the keyboard will be ignored when calling
	 *         {@link #getEmptyPlayerSlot()}, <code>false</code> otherwise.
	 */
	public static boolean shouldOverrideKeyboards() {
		return overrideKeyboards;
	}

	/**
	 * Sets whether or not the keyboard should should be ignored when calling
	 * {@link #getEmptyPlayerSlot()}.
	 * 
	 * @param override
	 *            <code>true</code> if the keyboard should be ignored when
	 *            calling {@link #getEmptyPlayerSlot()}, <code>false</code>
	 *            otherwise.
	 */
	public static void setShouldOverrideKeyboards(boolean override) {
		overrideKeyboards = override;
	}

	/**
	 * Returns the maximum amount of controllers that can be connected at one
	 * time.
	 * 
	 * @return the maximum amount of controllers that can be connected at one
	 *         time.
	 */
	public static int getMaxControllers() {
		return controllers.length;
	}

	/**
	 * Returns the next empty player slot.
	 * 
	 * @return the next empty player slot, <code>-1</code> if there are none.
	 */
	public static int getEmptyPlayerSlot() {
		for (int i = 0; i < controllers.length; i++) {
			if (controllers[i] == null) {
				return i; // No controller
			} else if (controllers[i].isKeyboard() && shouldOverrideKeyboards()) {
				return i; // Keyboards are overridden
			}
		}
		return -1;
	}

	/**
	 * Returns whether or not there are any available player slots.
	 * 
	 * @return <code>true</code> if there are any available player slots,
	 *         <code>false</code> otherwise.
	 */
	public static boolean hasEmptyPlayerSlot() {
		return getEmptyPlayerSlot() >= 0;
	}

	/**
	 * Returns the currently connected controllers that belong to the specified
	 * input handler.
	 * 
	 * @param handler
	 *            the handler.
	 * @return the currently connected controllers that belong to the specified
	 *         input handler.
	 */
	public static Controller[] getControllers(InputHandler handler) {
		if (handler == null) {
			return null;
		}
		ArrayList<Controller> handlerControllers = new ArrayList<Controller>();
		Arrays.stream(controllers).filter(controller -> controller != null).filter(controller -> controller.getHandler().equals(handler))
				.forEach(controller -> handlerControllers.add(controller));
		return handlerControllers.toArray(new Controller[handlerControllers.size()]);
	}

	/**
	 * Returns the currently connected controllers.
	 * 
	 * @return the currently connected controllers.
	 */
	public static Controller[] getControllers() {
		ArrayList<Controller> connectedControllers = new ArrayList<Controller>();
		Arrays.stream(controllers).filter(controller -> controller != null).forEach(controller -> connectedControllers.add(controller));
		return connectedControllers.toArray(new Controller[connectedControllers.size()]);
	}

	/**
	 * Returns whether or not the specified controller is connected.
	 * 
	 * @param controller
	 *            the controller.
	 * @return <code>true</code> if the controller is connected,
	 *         <code>false</code> otherwise.
	 */
	public static boolean hasController(Controller controller) {
		if (controller != null) {
			for (Controller connected : controllers) {
				if (controller.equals(connected)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Returns whether or not there is a controller currently connected to the
	 * specified player slot.
	 * 
	 * @param player
	 *            the player slot.
	 * @return <code>true</code> if there is a controller currently connected to
	 *         the specified player slot, <code>false</code> otherwise.
	 */
	public static boolean hasController(int player) {
		if (player < 0) {
			return false;
		}
		return controllers[player] != null;
	}

	/**
	 * Returns whether or not the controller connected to the specified player
	 * slot is of or extended from the given controller class type.
	 * 
	 * @param player
	 *            the player slot.
	 * @param controllerClazz
	 *            the controller class.
	 * @return <code>true</code> if the controller connected to the player slot
	 *         is of or extended from the controller class type,
	 *         <code>false</code> otherwise.
	 */
	public static boolean hasController(int player, Class<? extends Controller> controllerClazz) {
		if (controllerClazz == null || !hasController(player)) {
			return false;
		}
		return controllers[player].getClass().isAssignableFrom(controllerClazz);
	}

	/**
	 * Returns the controller currently connected to the specified player slot.
	 * 
	 * @param player
	 *            the player slot.
	 * @return the controller currently connected to the specified player slot.
	 */
	public static Controller getController(int player) {
		if (player < 0 || player >= controllers.length) {
			return null;
		}
		return controllers[player];
	}

	/**
	 * Returns whether or not the controller in the specified player slot
	 * belongs to the given handler.
	 * 
	 * @param player
	 *            the player slot.
	 * @param handler
	 *            the handler.
	 * @return <code>true</code> if the controller in the specified player slot
	 *         belongs to the given handler, <code>false</code> otherwise.
	 */
	public static boolean isHandlerController(int player, InputHandler handler) {
		Controller controller = getController(player);
		if (controller != null) {
			return controller.getHandler().equals(handler);
		}
		return false;
	}

	/**
	 * Connects the given controller to the specified player slot.
	 * 
	 * @param player
	 *            the player slot.
	 * @param controller
	 *            the controller to connect.
	 * @throws IndexOutOfBoundsException
	 *             if the <code>player</code> slot is negative or is greater
	 *             than or equal to the maximum amount of controllers.
	 * @throws NullPointerException
	 *             if the <code>controller</code> is <code>null</code>.
	 */
	public static void connectController(int player, Controller controller) throws IndexOutOfBoundsException, NullPointerException {
		if (player < 0) {
			throw new IndexOutOfBoundsException("Player slot cannot be negative");
		} else if (player >= controllers.length) {
			throw new IndexOutOfBoundsException("Player slot must be less than " + controllers.length);
		} else if (controller == null) {
			throw new NullPointerException("Controller cannot be null");
		} else if (!controller.equals(controllers[player])) {
			if (player == 0) {
				Input.unselectButton();
			}

			// Connect controller and attach global profiles
			Controller oldController = controllers[player];
			controllers[player] = controller;
			controller.setPlayerNumber(player);
			for (ControllerActionProfile profile : GLOBAL_PROFILES) {
				if (oldController != null) {
					profile.detach(oldController);
				}
				profile.attach(controller);
			}
			callEvent(listener -> listener.onPlayerConnect(controller, oldController));
			LOGGER.info("Connected controller for player #" + (player + 1) + " with controller type " + controller.getType().getName());
		}
	}

	/**
	 * Connects the specified controller to the next empty player slot.
	 * 
	 * @param controller
	 *            the controller to connect.
	 * @throws NullPointerException
	 *             if the <code>controller</code> is <code>null</code>.
	 */
	public static void connectController(Controller controller) throws NullPointerException {
		connectController(getEmptyPlayerSlot(), controller);
	}

	/**
	 * Disconnects the controller currently connected to the specified player
	 * slot.
	 * 
	 * @param player
	 *            the player slot.
	 */
	public static void disconnectController(int player) {
		Controller controller = getController(player);
		if (controller != null) {
			controllers[player] = null;
			for (ControllerActionProfile profile : GLOBAL_PROFILES) {
				profile.detach(controller);
			}
			callEvent(listener -> listener.onPlayerDisconnect(controller));
			LOGGER.info("Disconnected player #" + (player + 1));
		}
	}

	/**
	 * Disconnects the specified controller.
	 * 
	 * @param controller
	 *            the controller to disconnect.
	 */
	public static void disconnectController(Controller controller) {
		if (hasController(controller)) {
			disconnectController(controller.getPlayerNumber());
		}
	}

	/**
	 * Updates the input manager.
	 */
	public static void update() {
		if (!currentWindow.isFocused() && !Ardenus.isIdeMode()) {
			/*
			 * We do not update input if the window is not focused and the
			 * engine is not in IDE mode. If the engine is in IDE mode, we will
			 * assume that the developer will be wanting to change code while
			 * retaining game input recognization even though another window
			 * might be in focus. However, we do check if the current controller
			 * is a keyboard controller. The reason we do this is because if the
			 * controller is a keyboard controller and the window is not
			 * selected, buttons will still be considered hovered over even if
			 * the window is not selected. However, this is not an issue with
			 * traditional controllers.
			 */
			return;
		}

		/*
		 * If the controller is a keyboard, we need to do manual checking for
		 * each individual button set to see if one of their buttons has been
		 * hovered over.
		 */
		if (Input.hasController(0, KeyboardController.class)) {
			KeyboardController keyboard = (KeyboardController) Input.getController(0);
			double mouseX = keyboard.getMouseX();
			double mouseY = keyboard.getMouseY();
			for (ButtonSet buttonSet : ENABLED_BUTTON_SETS) {
				for (Button button : buttonSet.getButtons()) {
					if (mouseX >= button.getX() && mouseX <= button.getX() + button.getWidth() && mouseY >= button.getY() && mouseY <= button.getY() + button.getHeight()
							&& button.isSelectable()) {
						if (!Input.isButtonSelected(button)) {
							Input.selectButton(button);
						}
					} else {
						Input.unselectButton(button);
					}
				}
			}
		}

		// Unselect current button if it is no longer selectable
		if (selectedButton != null) {
			if (!selectedButton.isSelectable()) {
				selectedButton = null;
			}
		}

		// Update input handlers and their associated controllers
		for (InputHandler handler : inputHandlers) {
			Controller[] controllers = getControllers(handler);
			handler.onUpdate(currentWindow, controllers);
			for (Controller controller : controllers) {
				controller.update();
			}
		}
	}

	/**
	 * Returns whether or not the specified button is selected.
	 * 
	 * @param button
	 *            the button.
	 * @return <code>true</code> if the button is selected, <code>false</code>
	 *         otherwise.
	 */
	public static boolean isButtonSelected(Button button) {
		if (selectedButton == null) {
			return false;
		}
		return selectedButton.equals(button);
	}

	/**
	 * Returns the currently selected button.
	 * 
	 * @return the currently selected button, <code>null</code> if no button is
	 *         selected.
	 */
	public static Button getSelectedButton() {
		return selectedButton;
	}

	/**
	 * Selects the button.
	 * 
	 * @param button
	 *            the button select.
	 * @param forceNotification
	 *            <code>true</code> if notification should be forcibly sent,
	 *            <code>false</code> otherwise.
	 */
	public static void selectButton(Button button, boolean forceNotification) {
		if (!button.isSelectable()) {
			throw new IllegalArgumentException("Button must be selectable");
		}
		boolean wasSelected = isButtonSelected(button);
		selectedButton = button;
		if (wasSelected == false || forceNotification == true) {
			callEvent(listener -> listener.onButtonSelect(button));
		}
	}

	/**
	 * Selects the button.
	 * 
	 * @param button
	 *            the button select.
	 */
	public static void selectButton(Button button) {
		selectButton(button, false);
	}

	/**
	 * Unselects the currently selected button.
	 */
	public static void unselectButton() {
		Button lastSelectedButton = selectedButton;
		selectedButton = null;
		if (lastSelectedButton != null) {
			callEvent(listener -> listener.onButtonUnselect(lastSelectedButton));
		}
	}

	/**
	 * Unselects the currently selected button if it is currently the button.
	 * 
	 * @param button
	 *            the button to unselect if it is selected.
	 */
	public static void unselectButton(Button button) {
		if (isButtonSelected(button)) {
			unselectButton();
		}
	}

}
