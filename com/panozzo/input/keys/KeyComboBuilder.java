package com.panozzo.input.keys;

import java.util.List;

public class KeyComboBuilder {
	private KeyCombo instance;
	
	
	public KeyComboBuilder()
	{
		instance = new KeyCombo();
		
	}
	
	/**
	 * Add a key to the key combination
	 * @param key - Integer key code as seen in a {@link java.awt.event.KeyEvent}
	 * @return KeyComboBuilder
	 */
	public KeyComboBuilder addKey(int key)
	{
		this.addKey(key, null);
		return this;
	}
	
	/**
	 * Add a key to the key combination
	 * @param key - Integer key code as seen in a {@link java.awt.event.KeyEvent}
	 * @param modifiers - Integer array specifying key modifiers, as seen in a {@link java.awt.event.KeyEvent}
	 * @return KeyComboBuilder
	 */
	public KeyComboBuilder addKey(int key, int[] modifiers)
	{
		instance.addKey(key, modifiers);
		return this;
	}
	
	/**
	 * Builds the key combination.
	 * @return A new {@linkplain com.panozzo.input.keys.KeyCombo KeyCombo} object.
	 */
	public KeyCombo build()
	{
		KeyCombo buildCopy = new KeyCombo(instance);
		instance = new KeyCombo();
		return buildCopy;
	}
}

