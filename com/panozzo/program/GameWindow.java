package com.panozzo.program;
import javax.swing.JFrame;

import com.panozzo.input.InputCaptureListener;
import com.panozzo.input.InputCapturer.InputCancellationProvider;
import com.panozzo.input.UserActionType;

public class GameWindow extends JFrame implements InputCaptureListener {

	private static final long serialVersionUID = 1L;
	
	public GameWindow(int width, int height)
	{
		super();
		this.setSize(width, height);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	@Override
	public void userActionOn(UserActionType action, InputCancellationProvider cancelation) {
		// TODO Auto-generated method stub
		System.out.println(String.format("%s ON", action.name()));
	}

	@Override
	public void userActionOff(UserActionType action) {
		// TODO Auto-generated method stub
		System.out.println(String.format("%s OFF", action.name()));
	}
	
	
}
