package com.tcg.superstardefender.gamestates;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tcg.superstardefender.Game;
import com.tcg.superstardefender.MyCamera;
import com.tcg.superstardefender.MyConstants;
import com.tcg.superstardefender.MyConstants.States;
import com.tcg.superstardefender.entities.Star;
import com.tcg.superstardefender.managers.GameStateManager;

public class SplashState extends GameState {
	
	private MyCamera cam;
	private Viewport view;
	
	private String text;
	
	private float textX, textY, textW, textH;
	
	private Array<Star> stars;
	
	private float timer;
	private float time;

	public SplashState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	protected void init() {
		cam = new MyCamera();
		view = new StretchViewport(MyConstants.WOLRD_WIDTH, MyConstants.WORLD_HEIGHT, cam);
		
		view.apply();
		view.update((int) Game.SIZE.x, (int) Game.SIZE.y, true);
		
		time = 0;
		timer = 4.633f;
		
		if(Game.ANDROID) timer += .5f;
		
		stars = new Array<Star>();
		for(int i = 0; i < 500; i++) {
			stars.add(new Star(MyConstants.WOLRD_WIDTH, MyConstants.WORLD_HEIGHT));
		}
		
		Game.res.getMusic("splash").play();
	}

	@Override
	public void handleInput() {

	}

	@Override
	public void update(float dt) {
		
		text = "Tiny Country Games Presents";
		
		textW = cam.viewportWidth - 20;
		textH = Game.res.getHeight("large", text, textW, Align.center, true);
		
		textX = 10;
		textY = (cam.viewportHeight * .5f) + (textH * .5f);
		
		for(Star s: stars) {
			s.update(cam.viewportWidth, cam.viewportHeight);
		}
		
		time += dt;

		if(time > timer) {
			gsm.setState(States.TITLE, true);
		}
		
	}

	@Override
	public void draw(SpriteBatch sb, ShapeRenderer sr, float dt) {

		sr.begin(ShapeType.Filled);
		sr.setProjectionMatrix(cam.combined);
		for(Star s : stars) {
			s.draw(sr);
		}
		sr.end();
		
		sb.begin();
		sb.setProjectionMatrix(cam.combined);
		Game.res.getFont("large").draw(sb, text, textX, textY, textW, Align.center, true);
		sb.end();

	}

	@Override
	public void resize(int width, int height) {
		view.update(width, height, true);
	}

	@Override
	public void dispose() {
		stars.clear();
	}

}
