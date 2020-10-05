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
package org.ardenus.engine.input.handler;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import javax.usb.UsbDevice;
import javax.usb.UsbException;
import javax.usb.UsbInterface;
import javax.usb.UsbInterfacePolicy;
import javax.usb.UsbPipe;
import javax.usb.event.UsbPipeDataEvent;
import javax.usb.event.UsbPipeErrorEvent;
import javax.usb.event.UsbPipeListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ardenus.USB;
import org.ardenus.engine.graphics.Window;
import org.ardenus.engine.input.Input;
import org.ardenus.engine.input.controller.Controller;
import org.ardenus.engine.input.controller.GameCubeController;

/**
 * An input handler that can be used to listen for input from Nintendo GameCube
 * controllers using the Nintendo Wii U or Nintendo Switch Super Smash Bros.
 * adapter.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.1-SNAPSHOT
 */
public class GameCubeHandler extends InputHandler implements Closeable {

	/**
	 * Read data from the Wii U Smash Bros. GameCube adapters on a separate
	 * thread for better performance.
	 * 
	 * @author Trent Summerlin
	 * @since Ardenus Input v0.0.1-SNAPSHOT
	 */
	private static class GameCubeAdapterThread extends Thread {

		private final GameCubeHandler handler;
		private final HashMap<UsbDevice, byte[]> adapterIns;
		private final HashMap<UsbDevice, byte[]> adapterOuts;
		private final HashMap<UsbDevice, byte[]> adapterOutLast;
		private final HashMap<UsbDevice, UsbPipeListener> pipeListeners;
		private final HashMap<UsbDevice, UsbException> adapterExceptions;

		/**
		 * Allocates a GameCube adapter thread.
		 * 
		 * @param handler
		 *            the handler to send adapter data to.
		 * @throws NullPointerException
		 *             if the <code>handler</code> is <code>null</code>.
		 */
		public GameCubeAdapterThread(GameCubeHandler handler) throws NullPointerException {
			if (handler == null) {
				throw new NullPointerException("Handler cannot be null");
			}
			this.handler = handler;
			this.adapterIns = new HashMap<UsbDevice, byte[]>();
			this.adapterOuts = new HashMap<UsbDevice, byte[]>();
			this.adapterOutLast = new HashMap<UsbDevice, byte[]>();
			this.pipeListeners = new HashMap<UsbDevice, UsbPipeListener>();
			this.adapterExceptions = new HashMap<UsbDevice, UsbException>();
			this.setName("GameCube Adapter Thread");
		}

		/**
		 * Returns the latest data from the specified USB device.
		 * 
		 * @param device
		 *            the device.
		 * @return the latest data from the specified USB device.
		 */
		public byte[] getData(UsbDevice device) {
			return adapterIns.remove(device);
		}

		/**
		 * Sends data to the specified USB device.
		 * 
		 * @param device
		 *            the device to send the data to.
		 * @param data
		 *            the data to send to the device.
		 * @throws NullPointerException
		 *             if the USB <code>device</code> or <code>data</code> are
		 *             <code>null</code>.
		 */
		public void sendData(UsbDevice device, byte[] data) throws NullPointerException {
			if (device == null) {
				throw new NullPointerException("USB device cannot be null");
			} else if (data == null) {
				throw new NullPointerException("Data cannot be null");
			}
			adapterOuts.put(device, data);
		}

		/**
		 * Returns the last exception caused by the specified USB device.
		 * 
		 * @param device
		 *            the device.
		 * @return the last exception caused by the device, <code>null</code> if
		 *         there is none.
		 */
		public UsbException getException(UsbDevice device) {
			return adapterExceptions.remove(device);
		}

		/**
		 * Called when an USB error occurs in an adapter.
		 * 
		 * @param adapter
		 *            the adapter that caused the error.
		 * @param e
		 *            the USXB error that occurred.
		 */
		private void onException(UsbDevice adapter, UsbException e) {
			UsbPipeListener pipeListener = pipeListeners.remove(adapter);
			handler.adapterIns.get(adapter).removeUsbPipeListener(pipeListener);
			adapterExceptions.put(adapter, e);
		}

		@Override
		public void run() {
			while (!this.isInterrupted()) {
				try {
					Thread.sleep(0, 1); // Save CPU usage
				} catch (InterruptedException e) {
					this.interrupt(); // Interrupted during sleep
				}

				// Get adapter data from each connected adapter
				Iterator<UsbDevice> adapterI = handler.adapters.iterator();
				while (adapterI.hasNext()) {
					UsbDevice adapter = adapterI.next();
					try {
						// Read latest adapter data
						if (handler.adapterIns.containsKey(adapter)) {
							UsbPipe adapterIn = handler.adapterIns.get(adapter);
							if (!pipeListeners.containsKey(adapter)) {
								UsbPipeListener pipeListener = new UsbPipeListener() {

									@Override
									public void dataEventOccurred(UsbPipeDataEvent arg0) {
										adapterIns.put(adapter, arg0.getData());
									}

									@Override
									public void errorEventOccurred(UsbPipeErrorEvent arg0) {
										onException(adapter, arg0.getUsbException());
									}

								};
								adapterIn.addUsbPipeListener(pipeListener);
								pipeListeners.put(adapter, pipeListener);
							}
							if (!adapterIns.containsKey(adapter)) {
								USB.readDataAsync(adapterIn, CONTROLLER_DATA_PAYLOAD_LENGTH);
							}
						}

						// Send latest data to adapter
						if (handler.adapterOuts.containsKey(adapter)) {
							UsbPipe adapterOut = handler.adapterOuts.get(adapter);
							byte[] adapterOutData = adapterOuts.remove(adapter);
							if (adapterOutData != null && !Arrays.equals(adapterOutData, adapterOutLast.get(adapter))) {
								adapterOutLast.put(adapter, adapterOutData);
								USB.sendDataAsync(adapterOut, adapterOutData);
							}
						}
					} catch (UsbException e) {
						this.onException(adapter, e);
					}
				}
			}
			adapterIns.clear();
			adapterOuts.clear();
			adapterOutLast.clear();
			pipeListeners.clear();
			adapterExceptions.clear();
		}
	}

	/**
	 * The maximum amount of GameCube controllers that can be connected to one
	 * adapter at a time.
	 */
	public static final int MAX_GAMECUBE_CONTROLLERS = 4;

	/**
	 * The vendor ID for products manufactured by Nintendo.
	 */
	public static final short VENDOR_ID = 0x057E;

	/**
	 * The product ID for the Super Smash Bros. Nintendo GameCube controller
	 * adapter.
	 */
	public static final short PRODUCT_ID = 0x0337;

	/**
	 * The configuration to use for the Super Smash Bros. Nintendo GameCube
	 * controller adapter.
	 */
	public static final byte CONFIGURATION = 0x00;

	/**
	 * The endpoint to receive data from the Super Smash Bros. Nintendo GameCube
	 * controller adapter.
	 */
	public static final byte ENDPOINT_IN = (byte) 0x81;

	/**
	 * The endpoint to send data to the Super Smash Bros. Nintendo GameCube
	 * controller adapter.
	 */
	public static final byte ENDPOINT_OUT = (byte) 0x02;

	/**
	 * The initialize packet to send to the adapter in order to initialize it.
	 */
	public static final byte INITIALIZE = 0x13;

	/**
	 * The header for controller rumble packets.
	 * <p>
	 * Rumble packets consist of the following format:
	 * <ul>
	 * <li>1. Rumble header (<code>byte</code>)</li>
	 * <li>2. Controller #1 rumble (<code>byte</code>)</li>
	 * <li>3. Controller #2 rumble (<code>byte</code>)</li>
	 * <li>4. Controller #3 rumble (<code>byte</code>)</li>
	 * <li>5. Controller #4 rumble (<code>byte</code>)</li>
	 * </ul>
	 * Each <code>byte</code> for the controller rumble is a
	 * <code>boolean</code> of whether or not the controller should rumble. A
	 * value of <code>0x01</code> will have the controller begin to rumble,
	 * while a value of <code>0x00</code> will have the controller stop
	 * rumbling.
	 */
	public static final byte RUMBLE_HEADER = 0x11;

	/**
	 * The amount of <code>byte</code>s to read to receive a full packet of
	 * controller information from the adapter.
	 */
	public static final int CONTROLLER_DATA_PAYLOAD_LENGTH = 37;

	private final Logger logger;
	private final UsbInterfacePolicy adapterClaimPolicy;
	private final GameCubeAdapterThread adapterThread;
	private final ArrayList<UsbDevice> adapters;
	private final ArrayList<UsbInterface> previousInterfaces;
	private final HashMap<UsbDevice, UsbInterface> adapterInterfaces;
	private final HashMap<UsbDevice, UsbPipe> adapterIns;
	private final HashMap<UsbDevice, UsbPipe> adapterOuts;
	private long lastAdapterSearch;

	/**
	 * Creates a Nintendo GameCube controller adapter handler.
	 * 
	 * @throws SecurityException
	 *             if a security error occurs.
	 * @throws UsbException
	 *             if a USB error occurs.
	 */
	public GameCubeHandler() throws SecurityException, UsbException {
		this.logger = LogManager.getLogger(GameCubeHandler.class);
		this.adapterClaimPolicy = new UsbInterfacePolicy() {

			/**
			 * {@inheritDoc}
			 * <p>
			 * Adapters will only be forcibly claimed if they have been claimed
			 * before and are currently unclaimed.
			 */
			@Override
			public boolean forceClaim(UsbInterface usbInterface) {
				for (UsbInterface adapterInterface : previousInterfaces) {
					if (adapterInterface != null) {
						if (adapterInterface.equals(usbInterface) && !adapterInterfaces.containsValue(adapterInterface)) {
							return true;
						}
					}
				}
				return false;
			}

		};
		this.adapterThread = new GameCubeAdapterThread(this);
		this.adapters = new ArrayList<UsbDevice>();
		this.previousInterfaces = new ArrayList<UsbInterface>();
		this.adapterInterfaces = new HashMap<UsbDevice, UsbInterface>();
		this.adapterIns = new HashMap<UsbDevice, UsbPipe>();
		this.adapterOuts = new HashMap<UsbDevice, UsbPipe>();
		adapterThread.start();

		// Initiate adapters
		for (UsbDevice adapter : USB.getDevices(VENDOR_ID, PRODUCT_ID)) {
			this.initAdapter(adapter);
		}
		this.lastAdapterSearch = System.currentTimeMillis();
	}

	/**
	 * Initializes the specified adapter.
	 * 
	 * @param adapter
	 *            the adapter.
	 * @throws NullPointerException
	 *             if the <code>adapter</code> is <code>null</code>.
	 */
	private void initAdapter(UsbDevice adapter) throws NullPointerException {
		if (adapter == null) {
			throw new NullPointerException("Adapter cannot be null");
		} else if (!adapters.contains(adapter)) {
			try {
				// Add adapter, claim interfaces, and open endpoints
				adapters.add(adapter);
				adapterInterfaces.put(adapter, USB.claimInterface(adapter, CONFIGURATION, adapterClaimPolicy));
				adapterIns.put(adapter, USB.openPipe(adapterInterfaces.get(adapter), ENDPOINT_IN));
				adapterOuts.put(adapter, USB.openPipe(adapterInterfaces.get(adapter), ENDPOINT_OUT));
				previousInterfaces.add(adapterInterfaces.get(adapter));

				// Initialize adapter
				USB.sendData(adapterOuts.get(adapter), INITIALIZE);
				logger.debug("Initialized Super Smash Bros. Wii U/Switch Nintendo GameCube controller adapter");
			} catch (SecurityException | UsbException e) {
				this.killAdapter(adapter, true);
			}
		}
	}

	/**
	 * Kills the specified adapter.
	 * 
	 * @param adapter
	 *            the adapter.
	 * @param remove
	 *            <code>true</code> if the adapter should be removed from the
	 *            adapters list, <code>false</code> otherwise.
	 * @throws NullPointerException
	 *             if the <code>adapter</code> is <code>null</code>.
	 */
	private void killAdapter(UsbDevice adapter, boolean remove) throws NullPointerException {
		if (adapter == null) {
			throw new NullPointerException("Adapter cannot be null");
		} else if (remove == true) {
			adapters.remove(adapter);
		}
		adapterInterfaces.remove(adapter);
		adapterIns.remove(adapter);
		adapterOuts.remove(adapter);
		logger.debug("Killed Super Smash Bros. Wii U/Switch Nintendo GameCube controller adapter");
	}

	@Override
	public void onUpdate(Window window, Controller[] controllers) {
		// Search for adapters
		if (System.currentTimeMillis() - lastAdapterSearch >= 1000L) {
			try {
				UsbDevice[] devices = USB.getDevices(VENDOR_ID, PRODUCT_ID);
				if (devices.length != adapters.size()) {
					for (UsbDevice adapter : devices) {
						if (!adapters.contains(adapter)) {
							this.initAdapter(adapter);
						}
					}
				}
			} catch (UsbException e) {
				logger.warn("Failed to initialize adapter", e);
			}
			this.lastAdapterSearch = System.currentTimeMillis();
		}

		// Get controller data from each connected adapter
		GameCubeController.AdapterPacket[] buttonData = new GameCubeController.AdapterPacket[adapters.size() * MAX_GAMECUBE_CONTROLLERS];
		Iterator<UsbDevice> adapterI = adapters.iterator();
		for (int adapterIndex = 0; adapterI.hasNext(); adapterIndex++) {
			UsbDevice adapter = adapterI.next();
			byte[] data = adapterThread.getData(adapter);

			// Update controller data
			if (data != null) {
				for (int adapterSlot = 0; adapterSlot < MAX_GAMECUBE_CONTROLLERS; adapterSlot++) {
					int playerSlot = adapterSlot + (adapterIndex * MAX_GAMECUBE_CONTROLLERS);
					int type = data[1 + (9 * adapterSlot)] >> 4;
					if (type >= GameCubeController.STATE_CONNECTED) {
						// Parse adapter data
						int buttonData0 = data[1 + (9 * adapterSlot) + 1];
						int buttonData1 = data[1 + (9 * adapterSlot) + 2];
						int leftAnalogStickX = (data[1 + (9 * adapterSlot) + 3] & 0xFF);
						int leftAnalogStickY = (data[1 + (9 * adapterSlot) + 4] & 0xFF);
						int rightAnalogStickX = (data[1 + (9 * adapterSlot) + 5] & 0xFF);
						int rightAnalogStickY = (data[1 + (9 * adapterSlot) + 6] & 0xFF);
						int leftAnalogTriggerZ = (data[1 + (9 * adapterSlot) + 7] & 0xFF);
						int rightAnalogTriggerZ = (data[1 + (9 * adapterSlot) + 8] & 0xFF);
						buttonData[playerSlot] = new GameCubeController.AdapterPacket(buttonData0, buttonData1, leftAnalogStickX, leftAnalogStickY, rightAnalogStickX,
								rightAnalogStickY, leftAnalogTriggerZ, rightAnalogTriggerZ);

						// Connect controller if it shows activity
						if ((buttonData0 > 0 || buttonData1 > 0 || GameCubeController.LEFT_ANALOG_STICK_X_AXIS.isAxisPressed(leftAnalogStickX)
								|| GameCubeController.LEFT_ANALOG_STICK_Y_AXIS.isAxisPressed(leftAnalogStickY)) && !Input.isHandlerController(playerSlot, this)
								&& playerSlot < Input.getMaxControllers()) {
							Input.connectController(playerSlot, new GameCubeController(this, type == GameCubeController.STATE_WIRELESS));
						}
					} else if (type <= GameCubeController.STATE_DISCONNECTED && Input.isHandlerController(playerSlot, this)) {
						Input.disconnectController(playerSlot);
					}
				}
			}

			// Send rumble data to adapter
			byte[] rumbleData = new byte[1 + MAX_GAMECUBE_CONTROLLERS];
			rumbleData[0] = RUMBLE_HEADER;
			for (int adapterSlot = 1; adapterSlot < MAX_GAMECUBE_CONTROLLERS; adapterSlot++) {
				Controller controller = Input.getController(adapterSlot + (adapterIndex * MAX_GAMECUBE_CONTROLLERS));
				boolean isRumbling = false;
				if (controller instanceof GameCubeController) {
					isRumbling = controller.isRumbling();
				}
				rumbleData[adapterSlot + 1] = isRumbling ? (byte) 0x01 : (byte) 0x00;
			}
			adapterThread.sendData(adapter, rumbleData);

			// Check for any adapter issues
			if (adapterThread.getException(adapter) != null) {
				// Disconnect controls associated with the adapter
				for (int adapterSlot = 0; adapterSlot < MAX_GAMECUBE_CONTROLLERS; adapterSlot++) {
					int playerSlot = adapterSlot + (adapterIndex * MAX_GAMECUBE_CONTROLLERS);
					if (Input.isHandlerController(playerSlot, this)) {
						Input.disconnectController(playerSlot);
					}
				}

				// Release adapter
				this.killAdapter(adapter, false);
				adapterI.remove();
				adapterIndex--;
			}
		}

		// Update connected controllers
		for (int i = 0; i < controllers.length; i++) {
			GameCubeController controller = (GameCubeController) controllers[i];
			if (buttonData[i] != null) {
				controller.updateAdapterData(buttonData[controller.getPlayerNumber()]);
			}
		}
	}

	@Override
	public void close() throws IOException {
		try {
			adapterThread.interrupt();
			Iterator<UsbDevice> adapterI = adapters.iterator();
			while (adapterI.hasNext()) {
				this.killAdapter(adapterI.next(), false);
				adapterI.remove();
			}
			previousInterfaces.clear();
			Iterator<UsbInterface> usbInterfaceI = adapterInterfaces.values().iterator();
			while (usbInterfaceI.hasNext()) {
				usbInterfaceI.next().release();
				usbInterfaceI.remove();
			}
			Iterator<UsbPipe> inI = adapterIns.values().iterator();
			while (inI.hasNext()) {
				inI.next().close();
				inI.remove();
			}
			Iterator<UsbPipe> outI = adapterOuts.values().iterator();
			while (outI.hasNext()) {
				outI.next().close();
				outI.remove();
			}
			logger.info("Shutdown Nintendo GameCube controller handler");
		} catch (UsbException e) {
			throw new IOException(e);
		}
	}

}
