package com.tcg.superstardefender.managers;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;

public class MyInputProcessor extends InputAdapter {

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.LEFT || keycode == Keys.A) {
			MyInput.setKey(true, MyInput.LEFT);
		}
		if(keycode == Keys.RIGHT || keycode == Keys.D) {
			MyInput.setKey(true, MyInput.RIGHT);
		}
		if(keycode == Keys.SPACE || keycode == Keys.Z) {
			MyInput.setKey(true, MyInput.JUMP);
		}
		if(keycode == Keys.X || keycode == Keys.M) {
			MyInput.setKey(true, MyInput.SHOOT);
		}
		if(keycode == Keys.ENTER) {
			MyInput.setKey(true, MyInput.START);
		}
		if(keycode == Keys.BACK || keycode == Keys.ESCAPE) {
			MyInput.setKey(true, MyInput.BACK);
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.LEFT || keycode == Keys.A) {
			MyInput.setKey(false, MyInput.LEFT);
		}
		if(keycode == Keys.RIGHT || keycode == Keys.D) {
			MyInput.setKey(false, MyInput.RIGHT);
		}
		if(keycode == Keys.SPACE || keycode == Keys.Z) {
			MyInput.setKey(false, MyInput.JUMP);
		}
		if(keycode == Keys.X || keycode == Keys.M) {
			MyInput.setKey(false, MyInput.SHOOT);
		}
		if(keycode == Keys.ENTER) {
			MyInput.setKey(false, MyInput.START);
		}
		if(keycode == Keys.BACK || keycode == Keys.ESCAPE) {
			MyInput.setKey(false, MyInput.BACK);
		}
		return true;
	}

}
