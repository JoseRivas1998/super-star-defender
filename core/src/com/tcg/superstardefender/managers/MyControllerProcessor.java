package com.tcg.superstardefender.managers;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.PovDirection;
import com.tcg.superstardefender.MyConstants;

public class MyControllerProcessor extends ControllerAdapter {

	@Override
	public boolean buttonDown(Controller controller, int buttonIndex) {
		if(buttonIndex == MyConstants.A) {
			MyInput.setKey(true, MyInput.JUMP);
		}
		if(buttonIndex == MyConstants.B) {
			MyInput.setKey(true, MyInput.SHOOT);
		}
		if(buttonIndex == MyConstants.START) {
			MyInput.setKey(true, MyInput.START);
		}
		if(buttonIndex == MyConstants.BACK) {
			MyInput.setKey(true, MyInput.BACK);
		}
		return true;
	}

	@Override
	public boolean buttonUp(Controller controller, int buttonIndex) {
		if(buttonIndex == MyConstants.A) {
			MyInput.setKey(false, MyInput.JUMP);
		}
		if(buttonIndex == MyConstants.B) {
			MyInput.setKey(false, MyInput.SHOOT);
		}
		if(buttonIndex == MyConstants.START) {
			MyInput.setKey(false, MyInput.START);
		}
		if(buttonIndex == MyConstants.BACK) {
			MyInput.setKey(false, MyInput.BACK);
		}
		return true;
	}

	@Override
	public boolean povMoved(Controller controller, int povIndex, PovDirection value) {
		if(value == PovDirection.east || value == PovDirection.northEast || value == PovDirection.southEast) {
			MyInput.setKey(true, MyInput.RIGHT);
		} else {
			MyInput.setKey(false, MyInput.RIGHT);
		}
		if(value == PovDirection.west || value == PovDirection.northWest || value == PovDirection.southWest) {
			MyInput.setKey(true, MyInput.LEFT);
		} else {
			MyInput.setKey(false, MyInput.LEFT);
		}
		return true;
	}

	@Override
	public boolean axisMoved(Controller controller, int axisIndex, float value) {
		if(axisIndex == 1) {
			if(value > .3f) {
				MyInput.setKey(true, MyInput.RIGHT);
			} else {
				MyInput.setKey(false, MyInput.RIGHT);
			}
			if(value < -.3f) {
				MyInput.setKey(true, MyInput.LEFT);
			} else {
				MyInput.setKey(false, MyInput.LEFT);
			}
		}
		return true;
	}
}
