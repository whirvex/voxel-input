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

import org.ardenus.Deletable;
import org.ardenus.DeletedException;
import org.ardenus.graphics.glyph.Glyph;

/**
 * A {@link Glyph} based button.
 * <p>
 * These buttons are text rendered to the screen by a {@link Glyph} that can be
 * clicked.
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.1-SNAPSHOT
 */
public class GlyphButton extends Button implements Deletable {

	private final Glyph glyph;
	private float fontSize;
	private String text;
	private float alpha;
	private Color color;
	private boolean deleted;

	/**
	 * Creates a {@link Glyph} based button.
	 * 
	 * @param x
	 *            the X position.
	 * @param y
	 *            the Y position.
	 * @param glyph
	 *            the glyph.
	 * @param fontSize
	 *            the fontSize.
	 * @param text
	 *            the text.
	 * @throws NullPointerException
	 *             if the <code>glyph</code> is <code>null</code>.
	 * @throws IllegalArgumentException
	 *             if the <code>fontSize</code> is negative.
	 */
	public GlyphButton(float x, float y, Glyph glyph, float fontSize, String text) throws NullPointerException, IllegalArgumentException {
		super(x, y);
		if (glyph == null) {
			throw new NullPointerException("Glyph cannot be null");
		} else if (fontSize < 0.0F) {
			throw new IllegalArgumentException("Font size cannot be negative");
		}
		this.glyph = glyph;
		this.fontSize = fontSize;
		this.text = text;
		this.alpha = 1.0F;
		this.color = Color.WHITE;
	}

	/**
	 * Returns the font size.
	 * 
	 * @return the font size.
	 */
	public float getFontSize() {
		return this.fontSize;
	}

	/**
	 * Sets the font size.
	 * 
	 * @param fontSize
	 *            the font size.
	 * @throws IllegalArgumentException
	 *             if the <code>fontSize</code> is negative.
	 */
	public void setFontSize(float fontSize) throws IllegalArgumentException {
		if (fontSize < 0.0F) {
			throw new IllegalArgumentException("Font size cannot be negative");
		}
		this.fontSize = fontSize;
	}

	/**
	 * Returns whether or not the button has text.
	 * 
	 * @return <code>true</code> if the button has text, <code>false</code>
	 *         otherwise.
	 */
	public boolean hasText() {
		return text != null;
	}

	/**
	 * Returns the text.
	 * 
	 * @return the text.
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * Sets the text.
	 * 
	 * @param text
	 *            the text.
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Returns the alpha.
	 * 
	 * @return the alpha.
	 */
	public float getAlpha() {
		return this.alpha;
	}

	/**
	 * Sets the alpha.
	 * 
	 * @param alpha
	 *            the alpha.
	 */
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	/**
	 * Returns the color.
	 * 
	 * @return the color.
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * Sets the color.
	 * 
	 * @param color
	 *            the color.
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Draws the button.
	 * 
	 * @throws DeletedException
	 *             if the glyph button has been deleted.
	 */
	public void draw() throws DeletedException {
		if (this.isDeleted()) {
			throw new DeletedException(this);
		} else if (text != null) {
			glyph.draw(this.getX(), this.getY(), text, fontSize, alpha, color);
		}
	}

	@Override
	public float getWidth() {
		if (text == null) {
			return 0.0F;
		}
		return glyph.getWidth(text, fontSize);
	}

	@Override
	public float getHeight() {
		if (text == null) {
			return 0.0F;
		}
		return glyph.getHeight(text, fontSize);
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
		glyph.delete();
		this.deleted = true;
	}

	@Override
	public String toString() {
		return "GlyphButton [fontSize=" + fontSize + ", text=" + text + ", alpha=" + alpha + ", color=" + color + ", deleted=" + deleted + "]";
	}

}
