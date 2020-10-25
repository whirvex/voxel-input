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

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * TODO
 * 
 * @author Trent Summerlin
 * @since Ardenus Input v0.0.2-SNAPSHOT
 * @version 0.0.2
 */
public class UIButtonSet implements Set<UIButton> {
	
	private final Set<UIButton> buttons;
	
	public UIButtonSet() {
		this.buttons = new HashSet<UIButton>();
	}

	@Override
	public int size() {
		return buttons.size();
	}

	@Override
	public boolean isEmpty() {
		return buttons.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return buttons.contains(o);
	}

	@Override
	public Iterator<UIButton> iterator() {
		return buttons.iterator();
	}

	@Override
	public Object[] toArray() {
		return buttons.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return buttons.toArray(a);
	}

	@Override
	public boolean add(UIButton e) {
		return buttons.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return buttons.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return buttons.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends UIButton> c) {
		return buttons.addAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return buttons.retainAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return buttons.removeAll(c);
	}

	@Override
	public void clear() {
		buttons.clear();
	}

}
