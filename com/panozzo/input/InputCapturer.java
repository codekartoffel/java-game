package com.panozzo.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;

import com.panozzo.input.keys.KeyCombo;
import com.panozzo.program.GameWindow;

public class InputCapturer implements KeyListener, MouseListener {
	/**
	 * List of listeners which implement {@linkplain com.panozzo.input.InputCapturer InputCapturer}
	 */
	private List<InputCaptureListener> listeners;

	/**
	 * Represents a lookup for which key combos correspond to which user actions
	 */
	private HashMap<KeyCombo, UserActionType> keyActions;
	
	/**
	 * Tracks the states of each of the user action types.
	 */
	private HashMap<UserActionType, Boolean> userActionState;
	
	/**
	 * A special object that can be used to cancel the status of an input action.
	 */
	public class InputCancellationProvider {
		private InputCapturer capturer;
		private UserActionType action;
		public InputCancellationProvider(InputCapturer capturer, UserActionType action)
		{
			this.capturer = capturer;
			this.action = action;
		}
		
		public void cancel()
		{
			this.capturer.userActionState.put(action, false);
		}
	}
	
	public InputCapturer()
	{
		listeners = new LinkedList<InputCaptureListener>();
		keyActions = new HashMap<KeyCombo, UserActionType>();
		userActionState = new HashMap<UserActionType, Boolean>();
		this.initDefaultKeyActions();
	}
	
	/**
	 * Attach the input capturer to the GUI.
	 * Note: The GUI may already be used as a listener. This is ok,
	 * since we may want other class types to be listeners.
	 */
	public void attachToGUI(GameWindow origin)
	{
		origin.getGamePanel().addKeyListener(this);
		origin.getGamePanel().addMouseListener(this);
	}
	
	/**
	 * Add a capture listener to this class.
	 * @param listener
	 */
	public void addListener(InputCaptureListener listener)
	{
		listeners.add(listener);
	}
	
	/**
	 * Remove a capture listener from this class.
	 * @param listener
	 */
	public void removeListener(InputCaptureListener listener)
	{
		listeners.remove(listener);
	}
	
	/**
	 * Remove all listeners from this class.
	 */
	public void removeAllListeners()
	{
		listeners.clear();
	}	
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		KeyCombo keyCombo = new KeyCombo(e.getKeyCode());
		UserActionType action = keyActions.get(keyCombo);
		if (action == null)
		{
			// This action is not registered. Do nothing
			return;
		}
		
		if (!userActionState.containsKey(action))
		{
			// State has not been registered
			return;
		}
		
		
		if (userActionState.get(action))
		{
			// If the action is already marked as true, do not trigger the action.
			return;
		}
		
		userActionState.put(action, true);
		for (var listener : listeners)
		{
			listener.userActionOn(action, new InputCancellationProvider(this, action));
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		KeyCombo keyCombo = new KeyCombo(e.getKeyCode());
		UserActionType action = keyActions.get(keyCombo);
		if (action == null)
		{
			// This action is not registered. Do nothing
			return;
		}
		
		if (!userActionState.containsKey(action))
		{
			// State has not been registered
			return;
		}
		
		if (!userActionState.get(action))
		{
			// If the action is already marked as false, do not trigger the action.
			return;
		}
		
		userActionState.put(action, false);
		for (var listener : listeners)
		{
			listener.userActionOff(action);
		}
	}
	
	private void initDefaultKeyActions()
	{
		keyActions.put(new KeyCombo(KeyEvent.VK_W), UserActionType.FORWARD);
		keyActions.put(new KeyCombo(KeyEvent.VK_A), UserActionType.LEFT);
		keyActions.put(new KeyCombo(KeyEvent.VK_S), UserActionType.BACK);
		keyActions.put(new KeyCombo(KeyEvent.VK_D), UserActionType.RIGHT);
		keyActions.put(new KeyCombo(KeyEvent.VK_SPACE), UserActionType.UP);
		keyActions.put(new KeyCombo(KeyEvent.VK_SHIFT), UserActionType.DOWN);
		
		userActionState.put(UserActionType.FORWARD, false);
		userActionState.put(UserActionType.LEFT, false);
		userActionState.put(UserActionType.BACK, false);
		userActionState.put(UserActionType.RIGHT, false);
		userActionState.put(UserActionType.UP, false);
		userActionState.put(UserActionType.DOWN, false);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println(String.format("Mouse %d %d", e.getX(), e.getY()));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}

