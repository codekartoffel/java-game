package com.panozzo.input.keys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Represents one or multiple key codes with one or multiple modifiers
 * 
 * This class will use the same key codes and key modifier codes as present in java KeyEvents
 * @see java.awt.event.KeyEvent
 */
public class KeyCombo implements Cloneable {
	private List<Integer> keys;
	private List<Integer> modifiers;
	
	/**
	 * Construct a new {@link com.panozzo.input.keys.KeyCombo} object.
	 * The object will have no keys or modifiers registered.
	 */
	public KeyCombo()
	{
		keys = new ArrayList<Integer>();
		modifiers = new ArrayList<Integer>();
	}
	
	public KeyCombo(KeyCombo other)
	{
		this.keys = new ArrayList<Integer>(other.keys);
		this.modifiers = new ArrayList<Integer>(other.modifiers);
	}
	
	/**
	 * Construct a new {@link com.panozzo.input.keys.KeyCombo} object.
	 * @param key Key code
	 */
	public KeyCombo(int key)
	{
		this();
		this.addKey(key);
	}
	
	/**
	 * Construct a new {@link com.panozzo.input.keys.KeyCombo} object.
	 * @param key Array of key codes
	 * @param modifiers Array of key modifiers
	 */
	public KeyCombo(int key, int[] modifiers)
	{
		this();
		this.addKey(key, modifiers);
	}
	
	/**
	 * Construct a new {@link com.panozzo.input.keys.KeyCombo} object.
	 * @param keys Array of key codes
	 * @param modifiers Array of key modifiers
	 */
	public KeyCombo(int[] keys, int[] modifiers)
	{
		this();
		this.addKeys(keys, modifiers);
	}
	
	/**
	 * Adds a key to the key combination.
	 * @param key Key code
	 */
	public void addKey(int key)
	{
		keys.add(key);
	}
	
	/**
	 * Add a key to the key combination.
	 * @param key Key code
	 * @param modifiers
	 */
	public void addKey(int key, int[] modifiers)
	{
		this.keys.add(key);
		if (modifiers == null || modifiers.length == 0)
		{
			return;
		}
		
		this.modifiers.addAll(Arrays.stream(modifiers).boxed().toList());
	}
	
	/**
	 * Add keys to the key combination.
	 * @param keys - Array of key codes.
	 * @param modifiers - Array of modifiers.
	 */
	public void addKeys(int[] keys, int[] modifiers)
	{
		if (keys == null || keys.length == 0)
		{
			return;
		}
		
		this.keys.addAll(Arrays.stream(keys).boxed().toList());
		if (modifiers == null || modifiers.length == 0)
		{
			return;
		}
		
		this.modifiers.addAll(Arrays.stream(modifiers).boxed().toList());
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (!(o instanceof KeyCombo))
		{
			return false;
		}
		
		KeyCombo casted = (KeyCombo) o;
		
		// We must ensure the lists are in the same order before checking
		// equality
		this.keys.sort(null);
		this.modifiers.sort(null);
		if (!this.keys.equals(casted.keys) || !this.modifiers.equals(casted.modifiers))
		{
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(keys, modifiers);
	}
}
