package com.tcg.superstardefender.managers;

public class MyInput {

	private static boolean[] keys;
	private static boolean[] pkeys;
	
	private static final int NUM_KEYS = 6;
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int JUMP = 2;
	public static final int SHOOT = 3;
	public static final int BACK = 4;
	public static final int START = 5;
	
	static { 
		keys = new boolean[NUM_KEYS];
		pkeys = new boolean[NUM_KEYS];
	}
	
	public static void update() {
		for(int i = 0; i < NUM_KEYS; i++) {
			pkeys[i] = keys[i];
		}
	}
	
	public static void setKey(boolean b, int key) {
		keys[key] = b;
	}
	
	public static boolean keyDown(int key) {
		return keys[key];
	}
	
	public static boolean keyPressed(int key) {
		return keys[key] && !pkeys[key];
	}
	
	
}
