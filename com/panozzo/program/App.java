package com.panozzo.program;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import com.panozzo.core.GameManager;
import com.panozzo.core.GameManagerListener;
import com.panozzo.core.GameState;
import com.panozzo.input.InputCapturer;

public class App {
	
	static class AppState {

		public boolean keepGameAlive;
		public double deltaTime;
		public long lastTimeNs;
		public double updatesPerSecond;
		public boolean shouldTick;
		
		public AppState()
		{
			keepGameAlive = true;
			this.lastTimeNs = System.nanoTime();
			this.deltaTime = 0;
			this.updatesPerSecond = 60.0;
			this.shouldTick = false;
		}
		
		public void registerIteration()
		{
			long nowNs = System.nanoTime();
			double nsRatio = 1E9 / updatesPerSecond;
			// Accumulate delta time factored to the rate of how many nanoseconds per update
			// The alternative is checking deltaTime raw to a 60th of a second.
			deltaTime += (nowNs - lastTimeNs) / nsRatio;
			lastTimeNs = nowNs;
			
			if (deltaTime >= 1) {
				// More than one frame has passed
				this.shouldTick = true;
			}
		}
		
		public void registerTick()
		{
			this.shouldTick = false;
		}
		
	}
	
	// use a camelCase notation, not PascalCase
	public static void main(String[] args) {	
		GameWindow win = new GameWindow(500,500);
		GameManager game = new GameManager(win.getRenderSurface());
		InputCapturer inputCapture = new InputCapturer();
		AppState appState = new AppState();
		
		
		inputCapture.addListener(win);
		inputCapture.attachToGUI(win);
		
		win.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int result = JOptionPane.showConfirmDialog(
						null,
						"Are you sure you want to exit?",
						null,
						JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					win.setVisible(false);
					// TODO: Perform any game disposal operations here too
					win.dispose();
					System.exit(0);
				}
			}
		});
		
		win.setVisible(true);
		game.start();
		
		// Create Game loop
		while (appState.keepGameAlive)
		{
			if (!appState.shouldTick)
			{
				// We do not need to tick yet
				appState.registerIteration();
				continue;
			}
			
			// We need to tick
			appState.registerTick();
			
			// Tell the game manager to tick, request graphics, and draw
			game.tick();
			game.render();
		}
	}

}

