package com.panozzo.program;

import com.panozzo.input.InputCapturer;

public class App {
	// use a camelCase notation, not PascalCase
	public static void main(String[] args) {	
		GameWindow win = new GameWindow(500,500);
		InputCapturer inputCapture = new InputCapturer();
		inputCapture.addListener(win);
		inputCapture.attachToGUI(win);		
		win.setVisible(true);
	}

}

