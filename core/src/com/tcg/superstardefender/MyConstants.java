package com.tcg.superstardefender;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class MyConstants {

	public static enum States {
		SPLASH, TITLE, LEVELSELECT, CREDITS, PLAY
	}
	
	public static String saveFile = "super_star_defender.sav";
	
	public static int LEFT = 0;
	public static int RIGHT = 1;

	public static int NUM_TOUCHES = 20;
	
	public static final int WOLRD_WIDTH = 800;
	public static final int WORLD_HEIGHT = 450;
	
	//XBox Mappings
	public static final int A = 0;
	public static final int B = 1;
	public static final int X = 2;
	public static final int Y = 3;
	public static final int LB = 4;
	public static final int RB = 5;
	public static final int BACK = 6;
	public static final int START = 7;
	public static final int L3 = 8;
	public static final int R3 = 9;
	
	public static Vector2 randomVelocity(float speed) {
		float radians = MathUtils.random(MathUtils.PI2);
		float x = MathUtils.cos(radians) * speed;
		float y = MathUtils.sin(radians) * speed;
		return new Vector2(x, y);
	}
	
	public static int randomDirection() {
		if(MathUtils.randomBoolean()) {
			return MyConstants.RIGHT;
		} else {
			return MyConstants.LEFT;
		}
	}
	
	public static float distance(float x1,float y1,float x2,float y2) {
		float a = (x2 - x1);
		float b = (y2 - y1);
		return (float) Math.sqrt((Math.pow(a, 2)) + (Math.pow(b, 2)));
	}
	
	public static float distance(Vector2 p1, Vector2 p2) {
		return distance(p1.x, p1.y, p2.x, p2.y);
	}
	
	public static Color randomColor() {
		return new Color(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1);
	}
	
	public static Color rgba(float r, float g, float b, float a) {
		return new Color(r / 255f, g / 255f, b / 255f, a / 255f);
	}

	public static void drawTextBg(ShapeRenderer sr, float x, float y, float w, float h, MyCamera cam) {
		sr.begin(ShapeType.Filled);
		sr.setProjectionMatrix(cam.combined);
		Color bL = MyConstants.rgba(45, 50, 107, 255);
		Color bR = MyConstants.rgba(41, 42, 65, 255);
		Color tR = MyConstants.rgba(45, 50, 107, 255);
		Color tL = MyConstants.rgba(112, 118, 176, 255);
		sr.rect(x, y, w, h, bL, bR, tR, tL);
		sr.end();
		sr.begin(ShapeType.Line);
		sr.setProjectionMatrix(cam.combined);
		sr.setColor(Color.WHITE);
		sr.rect(x, y, w, h);
		sr.end();
	}
	
	public static boolean booleanArrayContains(boolean b, boolean[] array) {
		for(boolean bool : array) {
			if(bool == b) return true;
		}
		return false;
	}
	
	public static float clamp(float n, float min, float max) {
		float r = n;
		if(r < min) {
			r = min;
		}
		if(r > max) {
			r = max;
		}
		return r;
	}
	
	public static int clamp(int n, int min, int max) {
		int r = n;
		if(r < min) {
			r = min;
		}
		if(r > max) {
			r = max;
		}
		return r;
	}
	
	public static boolean wouldWrap(float n, float min, float max) {
		return n < min || n > max;
	}
	
	public static boolean wouldWrap(int n, int min, int max) {
		return n < min || n > max;
	}
	
	public static float wrap(float n, float min, float max) {
		float r = n;
		if(r < min) {
			r = max;
		}
		if(r > max) {
			r = min;
		}
		return r;
	}
	
	public static float wrap(int n, int min, int max) {
		int r = n;
		if(r < min) {
			r = max;
		}
		if(r > max) {
			r = min;
		}
		return r;
	}

	public static String getScore(int score) {
		if(score < 10) {
			return "0000000" + score;
		} else if(score < 100) {
			return "000000" + score;
		} else if(score < 1000) {
			return "00000" + score;
		} else if(score < 10000) {
			return "0000" + score;
		} else if(score < 100000) {
			return "000" + score;
		} else if(score < 1000000) {
			return "00" + score;
		} else if(score < 10000000) {
			return "0" + score;
		} else {
			return "" + score;
		}
	}
	
	public static String getTime(float currentTime) {
		float seconds = currentTime;
		float minutes = seconds / 60;
		float remainingSeconds = seconds % 60;
		float fmilliseconds = (float) (seconds - Math.floor(seconds));
		fmilliseconds *= 10000;
		int intmilliseconds = (int) fmilliseconds;
		String timeS;
		if(remainingSeconds >= 10) {
			timeS = (int) minutes + ":" + (int) remainingSeconds;
		} else {
			if(seconds < 0) {
				timeS = "-" + (int) minutes + ":0" + (int) Math.abs(remainingSeconds);
			} else {
				timeS = (int) minutes + ":0" + (int) remainingSeconds;
			}
		}
		timeS = timeS + "." + intmilliseconds;
		return timeS;
	}
	
	public static float sixteenNineResolution(float width) {
		return (width * 9f) / 16f;
	}
	
	public static int sixteenNineResolution(int width) {
		return (int) ((width * 9f) / 16f);
	}
	
}
