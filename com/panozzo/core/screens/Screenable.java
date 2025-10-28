package com.panozzo.core.screens;

public interface Screenable {
	public final int SCREEN_ERROR_NONE = 0x0;
	public final int SCREEN_ERROR_IO = 0x1;
	public final int SCREEN_TERMINATED = 0x2;
	/**
	 * Poll the Screenable object to see if its done showing its contents
	 * @return
	 */
	public boolean checkIfDone();
	
	/**
	 * Tell the Screenable that it must cease all activities
	 */
	public void terminate();
}
