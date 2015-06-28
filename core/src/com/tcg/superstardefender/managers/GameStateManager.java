package com.tcg.superstardefender.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tcg.superstardefender.Game;
import com.tcg.superstardefender.MyCamera;
import com.tcg.superstardefender.MyConstants;
import com.tcg.superstardefender.MyConstants.States;
import com.tcg.superstardefender.gamestates.*;

public class GameStateManager {

	private GameState gs;
	private SpriteBatch sb;
	private ShapeRenderer sr;
	
	private Rectangle left, right, start, leftSt, rightSt, startSt;
	private Ellipse shoot, shootSt, jump, jumpSt;
	private boolean[] lefts, rights, shoots, jumps, starts;
	
	private MyCamera cam;
	private Viewport view;
	
	public GameStateManager(States s) {
		sb = new SpriteBatch();
		sr = new ShapeRenderer();
		setState(s, true);
		left = new Rectangle();
		right = new Rectangle();
		shoot = new Ellipse();
		jump = new Ellipse();
		start = new Rectangle();
		leftSt = new Rectangle();
		rightSt = new Rectangle();
		shootSt = new Ellipse();
		jumpSt = new Ellipse();
		startSt = new Rectangle();
		lefts = new boolean[MyConstants.NUM_TOUCHES];
		rights = new boolean[MyConstants.NUM_TOUCHES];
		shoots = new boolean[MyConstants.NUM_TOUCHES];
		jumps = new boolean[MyConstants.NUM_TOUCHES];
		starts = new boolean[MyConstants.NUM_TOUCHES];
		
		cam = new MyCamera();
		view = new StretchViewport(MyConstants.WOLRD_WIDTH, MyConstants.WORLD_HEIGHT, cam);
		view.apply(true);
		view.update((int)Game.SIZE.x, (int)Game.SIZE.y, true);
	}
	
	public void setState(States s, boolean stopSound) {
		if(stopSound) Game.res.stopAllSound();
		if(gs != null) gs.dispose();
		if(s == States.SPLASH) {
			gs = new SplashState(this);
		}
		if(s == States.TITLE) {
			gs = new TitleState(this);
		}
		if(s == States.CREDITS) {
			gs = new CreditState(this);
		}
		if(s == States.LEVELSELECT) {
			gs = new LevelSelectState(this);
		}
		if(s == States.PLAY) {
			gs = new PlayState(this);
		}
	}
	
	public void handleInput() {
		if(Game.ANDROID) androidInput(); 
		gs.handleInput();
	}
	
	public void update(float dt) {
		gs.update(dt);
	}
	
	public void draw(float dt) {
		gs.draw(sb, sr, dt);
		if(Game.ANDROID && !(gs instanceof SplashState)) androidControlDraw();
	}
	
	public void resize(int width, int height) {
		gs.resize(width, height);
		view.update(width, height, true);
	}
	
	public void dispose() {
		gs.dispose();
	}

	private void androidInput() {
		
		left.width = 75;
		right.width = 75;
		shoot.width = 75;
		jump.width = 75;
		start.width = 75;

		leftSt.width = Game.res.getWidth("small", "Left", 0, Align.bottomLeft, false);
		rightSt.width = Game.res.getWidth("small", "Right", 0, Align.bottomLeft, false);
		shootSt.width = Game.res.getWidth("small", "Shoot", 0, Align.bottomLeft, false);;
		jumpSt.width = Game.res.getWidth("small", "Jump", 0, Align.bottomLeft, false);
		startSt.width = Game.res.getWidth("small", "Start", 0, Align.bottomLeft, false);

		leftSt.height = Game.res.getHeight("small", "Up", 0, Align.bottomLeft, false);
		rightSt.height = Game.res.getHeight("small", "Down", 0, Align.bottomLeft, false);
		shootSt.height = Game.res.getHeight("small", "Shoot", 0, Align.bottomLeft, false);
		jumpSt.height = Game.res.getHeight("small", "Jump", 0, Align.bottomLeft, false);
		startSt.height = Game.res.getHeight("small", "Start", 0, Align.bottomLeft, false);
		
		left.height = 50;
		right.height = 50;
		shoot.height = 75;
		jump.height = 75;
		start.height = 50;
		
		start.x = 10;
		start.y = MyConstants.WORLD_HEIGHT - start.height - 10;

		startSt.x = (start.x + (start.width * .5f)) - (startSt.width * .5f);
		startSt.y = (start.y + (start.height * .5f)) + (startSt.height * .5f);
		
		jump.x = (MyConstants.WOLRD_WIDTH) - (jump.width * .5f) - 10;
		jump.y = (jump.height * .5f) + 10;
		
		jumpSt.x = jump.x - (jumpSt.width * .5f);
		jumpSt.y = jump.y + (jumpSt.height * .5f);
		
		shoot.x = (jump.x) - jump.width - 15;
		shoot.y = (shoot.height * .5f) + 10;

		shootSt.x = shoot.x - (shootSt.width * .5f);
		shootSt.y = shoot.y + (shootSt.height * .5f);
		
		left.x = 10;
		left.y = 10;

		rightSt.x = (right.x + (right.width * .5f)) - (rightSt.width * .5f);
		rightSt.y = (right.y + (right.height * .5f)) + (rightSt.height * .5f);
		
		right.x = 10 + left.width + 5;
		right.y = 10;

		leftSt.x = (left.x + (left.width * .5f)) - (leftSt.width * .5f);
		leftSt.y = (left.y + (left.height * .5f)) + (leftSt.height * .5f);
		
		for(int i = 0; i < MyConstants.NUM_TOUCHES; i++) {
			if(Gdx.input.isTouched(i)) {
				if(left.contains(Gdx.input.getX(i) * (MyConstants.WOLRD_WIDTH / Game.SIZE.x), MyConstants.WORLD_HEIGHT - (Gdx.input.getY(i) * (MyConstants.WORLD_HEIGHT / Game.SIZE.y)))) {
					lefts[i] = true;
				} else {
					lefts[i] = false;
				}
				if(right.contains(Gdx.input.getX(i) * (MyConstants.WOLRD_WIDTH / Game.SIZE.x), MyConstants.WORLD_HEIGHT - (Gdx.input.getY(i) * (MyConstants.WORLD_HEIGHT / Game.SIZE.y)))) {
					rights[i] = true;
				} else {
					rights[i] = false;
				}
				if(shoot.contains(Gdx.input.getX(i) * (MyConstants.WOLRD_WIDTH / Game.SIZE.x), MyConstants.WORLD_HEIGHT - (Gdx.input.getY(i) * (MyConstants.WORLD_HEIGHT / Game.SIZE.y)))) {
					shoots[i] = true;
				} else {
					shoots[i] = false;
				}
				if(jump.contains(Gdx.input.getX(i) * (MyConstants.WOLRD_WIDTH / Game.SIZE.x), MyConstants.WORLD_HEIGHT - (Gdx.input.getY(i) * (MyConstants.WORLD_HEIGHT / Game.SIZE.y)))) {
					jumps[i] = true;
				} else {
					jumps[i] = false;
				}
				if(start.contains(Gdx.input.getX(i) * (MyConstants.WOLRD_WIDTH / Game.SIZE.x), MyConstants.WORLD_HEIGHT - (Gdx.input.getY(i) * (MyConstants.WORLD_HEIGHT / Game.SIZE.y)))) {
					starts[i] = true;
				} else {
					starts[i] = false;
				}
			} else {
				lefts[i] = false;
				rights[i] = false;
				shoots[i] = false;
				jumps[i] = false;
				starts[i] = false;
			}
		}

		MyInput.setKey(MyConstants.booleanArrayContains(true, lefts), MyInput.LEFT);
		MyInput.setKey(MyConstants.booleanArrayContains(true, rights), MyInput.RIGHT);
		MyInput.setKey(MyConstants.booleanArrayContains(true, shoots), MyInput.SHOOT);
		MyInput.setKey(MyConstants.booleanArrayContains(true, jumps), MyInput.JUMP);
		MyInput.setKey(MyConstants.booleanArrayContains(true, starts), MyInput.START);
		
	}

	private void androidControlDraw() {
		Gdx.gl.glEnable(GL20.GL_BLEND);
	    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	    sr.begin(ShapeType.Filled);
	    sr.setProjectionMatrix(cam.combined);
	    sr.setColor(MyConstants.rgba(255, 0, 0, 168));
	    sr.ellipse((shoot.x - (shoot.width * .5f)), (shoot.y - (shoot.height * .5f)), shoot.width, shoot.height);
	    sr.setColor(MyConstants.rgba(0, 128, 0, 168));
	    sr.ellipse((jump.x - (jump.width * .5f)), (jump.y - (jump.height * .5f)), jump.width, jump.height);
	    sr.setColor(MyConstants.rgba(128, 128, 128, 168));
	    sr.rect(start.x, start.y, start.width, start.height);
	    sr.rect(left.x, left.y, left.width, left.height);
	    sr.rect(right.x, right.y, right.width, right.height);
	    sr.end();
	    sr.begin(ShapeType.Line);
	    sr.setProjectionMatrix(cam.combined);
	    sr.setColor(Color.WHITE);
	    sr.rect(start.x, start.y, start.width, start.height);
	    sr.ellipse((shoot.x - (shoot.width * .5f)), (shoot.y - (shoot.height * .5f)), shoot.width, shoot.height);
	    sr.ellipse((jump.x - (jump.width * .5f)), (jump.y - (jump.height * .5f)), jump.width, jump.height);
	    sr.rect(left.x, left.y, left.width, left.height);
	    sr.rect(right.x, right.y, right.width, right.height);
	    sr.setColor(MyConstants.rgba(210, 210, 210, 255));
	    if(MyInput.keyDown(MyInput.START)) {
		    for(int i = 0; i < 5; i++) {
		    	sr.rect(start.x + i, start.y + i, start.width - (i * 2), start.height - (i * 2));
		    }
	    }
	    if(MyInput.keyDown(MyInput.LEFT)) {
		    for(int i = 0; i < 5; i++) {
		    	sr.rect(left.x + i, left.y + i, left.width - (i * 2), left.height - (i * 2));
		    }
	    }
	    if(MyInput.keyDown(MyInput.RIGHT)) {
		    for(int i = 0; i < 5; i++) {
		    	sr.rect(right.x + i, right.y + i, right.width - (i * 2), right.height - (i * 2));
		    }
	    }
	    if(MyInput.keyDown(MyInput.JUMP)) {
		    for(int i = 0; i < 5; i++) {
		    	sr.ellipse((jump.x - (jump.width * .5f)) + i, (jump.y - (jump.height * .5f)) + i, jump.width - (i * 2), jump.height - (i * 2));
		    }
	    }
	    if(MyInput.keyDown(MyInput.SHOOT)) {
		    for(int i = 0; i < 5; i++) {
		    	sr.ellipse((shoot.x - (shoot.width * .5f)) + i, (shoot.y - (shoot.height * .5f)) + i, shoot.width - (i * 2), shoot.height - (i * 2));
		    }
	    }

	    if(MyInput.keyPressed(MyInput.START)) Gdx.input.vibrate(35);
	    if(MyInput.keyPressed(MyInput.LEFT)) Gdx.input.vibrate(35);
	    if(MyInput.keyPressed(MyInput.RIGHT)) Gdx.input.vibrate(35);
	    if(MyInput.keyPressed(MyInput.JUMP)) Gdx.input.vibrate(35);
	    if(MyInput.keyPressed(MyInput.SHOOT)) Gdx.input.vibrate(35);
	    sr.end();
	    sb.begin();
	    sb.setProjectionMatrix(cam.combined);
	    Game.res.getFont("small").draw(sb, "Start", startSt.x, startSt.y, 0, Align.bottomLeft, false);
	    Game.res.getFont("small").draw(sb, "Shoot", shootSt.x, shootSt.y, 0, Align.bottomLeft, false);
	    Game.res.getFont("small").draw(sb, "Jump", jumpSt.x, jumpSt.y, 0, Align.bottomLeft, false);
	    Game.res.getFont("small").draw(sb, "Right", rightSt.x, rightSt.y, 0, Align.bottomLeft, false);
	    Game.res.getFont("small").draw(sb, "Left", leftSt.x, leftSt.y, 0, Align.bottomLeft, false);
	    sb.end();
	    Gdx.gl.glDisable(GL20.GL_BLEND);
	}
	
}
