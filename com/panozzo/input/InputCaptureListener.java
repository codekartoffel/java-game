package com.panozzo.input;

import com.panozzo.input.InputCapturer.InputCancellationProvider;

public interface InputCaptureListener {
	public void userActionOn(UserActionType action, InputCancellationProvider cancelation);
	public void userActionOff(UserActionType action);
}
