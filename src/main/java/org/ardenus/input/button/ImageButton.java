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

import java.awt.Color;
import java.awt.Image;

import org.ardenus.Deletable;
import org.ardenus.DeletedException;

/**
 * An {@link Image} based button.
 * <p>
 * These buttons are images rendered to the screen that can be clicked.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.1-SNAPSHOT
 */
public class ImageButton extends Button implements Deletable {

	private final Image image;
	private boolean deleted;

	/**
	 * Creates an {@link Image} based button.
	 * 
	 * @param x
	 *            the X position.
	 * @param y
	 *            the Y position.
	 * @param image
	 *            the image.
	 * @throws NullPointerException
	 *             if the <code>image</code> is <code>null</code>.
	 */
	public ImageButton(float x, float y, Image image) throws NullPointerException {
		super(x, y);
		if (image == null) {
			throw new NullPointerException("Image cannot be null");
		}
		this.image = image;
	}

	@Override
	public float getWidth() {
		return image.getWidth();
	}

	@Override
	public float getHeight() {
		return image.getHeight();
	}

	/**
	 * Returns the alpha.
	 * 
	 * @return the alpha.
	 */
	public float getAlpha() {
		return image.getAlpha();
	}

	/**
	 * Sets the alpha.
	 * 
	 * @param alpha
	 *            the alpha.
	 */
	public void setAlpha(float alpha) {
		if (this.isDeleted()) {
			throw new DeletedException(this);
		}
		image.setAlpha(alpha);
	}

	/**
	 * Returns the color.
	 * 
	 * @return the color.
	 */
	public Color getColor() {
		return image.getColor();
	}

	/**
	 * Sets the color of the image.
	 * 
	 * @param color
	 *            the color of the image.
	 */
	public void setColor(Color color) {
		image.setColor(color);
	}

	/**
	 * Draws the button.
	 * 
	 * @throws DeletedException
	 *             if the image button is deleted.
	 */
	public void draw() throws DeletedException {
		if (this.isDeleted()) {
			throw new DeletedException(this);
		}
		image.draw(this.getX(), this.getY());
	}

	@Override
	public boolean isDeleted() {
		return this.deleted;
	}

	@Override
	public void delete() throws DeletedException {
		if (this.isDeleted()) {
			throw new DeletedException(this);
		}
		image.delete();
		this.deleted = true;
	}

}
