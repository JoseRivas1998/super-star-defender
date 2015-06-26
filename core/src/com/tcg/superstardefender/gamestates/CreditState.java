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
import com.tcg.superstardefender.managers.MyInput;

public class CreditState extends GameState {
	
	private MyCamera cam;
	private Viewport view;
	
	private Array<Star> stars;
	
	private String developed, jose, powered, libgdx, special, names;
	
	private float dX, dY, dW, dH, jX, jY, jW, jH, pX, pY, pW, lX, lY, lW, lH, sX, sY, sW, nX, nY, nW, nH;

	public CreditState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	protected void init() {
		
		cam = new MyCamera();
		view = new StretchViewport(MyConstants.WOLRD_WIDTH, MyConstants.WORLD_HEIGHT, cam);
		
		view.apply();
		view.update((int) Game.SIZE.x, (int) Game.SIZE.y, true);
		
		stars = new Array<Star>();
		for(int i = 0; i < 500; i++) {
			stars.add(new Star(MyConstants.WOLRD_WIDTH, MyConstants.WORLD_HEIGHT));
		}
		
		setValues();
		
		Game.res.getMusic("credits").play();
	}

	@Override
	public void handleInput() {
		if(MyInput.keyPressed(MyInput.BACK) || MyInput.keyPressed(MyInput.START) || MyInput.keyPressed(MyInput.LEFT) || MyInput.keyPressed(MyInput.RIGHT) || MyInput.keyPressed(MyInput.JUMP) || MyInput.keyPressed(MyInput.SHOOT)) {
			gsm.setState(States.TITLE, true);
		}
	}
	
	private void setValues() {
		
		developed = "Developed by:";
		jose = "Jose Rodriguez-Rivas";
		
		powered = "Powered by:";
		libgdx = "libGDX";
		
		special = "Special Thanks:";
		names = "Alex Abrams, Charli Anne Hanna-Baker, Mark Sheinberg";
		
		dW = MyConstants.WOLRD_WIDTH - 20;
		dH = Game.res.getHeight("large", developed, dW, Align.center, true);
		
		dX = 10;
		dY = (MyConstants.WORLD_HEIGHT * .75f) + (dH * .5f);
		
		jW = MyConstants.WOLRD_WIDTH - 20;
		jH = Game.res.getHeight("main", jose, jW, Align.center, true);
		
		jX = 10;
		jY = dY - (jH * 2.5f);
		
		pW = MyConstants.WOLRD_WIDTH - 20;
		
		pX = 10;
		pY = (MyConstants.WORLD_HEIGHT * .5f) + (dH * .5f);
		
		lW = MyConstants.WOLRD_WIDTH - 20;
		lH = Game.res.getHeight("main", libgdx, lW, Align.center, true);
		
		lX = 10;
		lY = pY - (lH * 2.5f);
		
		sW = MyConstants.WOLRD_WIDTH - 20;
		
		sX = 10;
		sY = (MyConstants.WORLD_HEIGHT * .25f) + (dH * .5f);
		
		nW = MyConstants.WOLRD_WIDTH - 20;
		nH = Game.res.getHeight("main", names, nW, Align.center, true);
		
		nX = 10;
		nY = sY - (nH * 2.5f);
		
	}

	@Override
	public void update(float dt) {
		
		setValues();
	
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
		Game.res.getFont("large").draw(sb, developed, dX, dY, dW, Align.center, true);
		Game.res.getFont("main").draw(sb, jose, jX, jY, jW, Align.center, true);
		Game.res.getFont("large").draw(sb, powered, pX, pY, pW, Align.center, true);
		Game.res.getFont("main").draw(sb, libgdx, lX, lY, lW, Align.center, true);
		Game.res.getFont("large").draw(sb, special, sX, sY, sW, Align.center, true);
		Game.res.getFont("main").draw(sb, names, nX, nY, nW, Align.center, true);
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
