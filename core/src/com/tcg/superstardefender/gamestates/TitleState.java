package com.tcg.superstardefender.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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

public class TitleState extends GameState {
	
	private MyCamera cam;
	private Viewport view;
	
	private Array<Star> stars;
	
	private String currentOption;
	
	private float currentOptionX, currentOptionY, currentOptionW, currentOptionH;
	
	private int currentItem;
	
	private float titleX, titleY, titleW, titleH;
	
	private float nX, nY, nW, nH;
	
	private String jose;
	
	private float jX, jY, jH;

	public TitleState(GameStateManager gsm) {
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
		
		currentItem = 0;
		
		setValues();
		
		Game.res.getMusic("title").play();
	}

	@Override
	public void handleInput() {
		if(MyInput.keyPressed(MyInput.BACK)) {
			Gdx.app.exit();
		}
		if(MyInput.keyPressed(MyInput.LEFT)) {
			currentItem--;
			Game.res.getSound("select").play(.5f);
		}
		if(MyInput.keyPressed(MyInput.RIGHT)) {
			currentItem++;
			Game.res.getSound("select").play(.5f);
		}
		if(MyInput.keyPressed(MyInput.START) || MyInput.keyPressed(MyInput.JUMP)) {
			select();
		}
	}
	
	private void select() {
		if(currentItem == 0) {
			gsm.setState(States.LEVELSELECT, true);
		}
		if(currentItem == 1) {
			gsm.setState(States.CREDITS, true);
		}
		if(currentItem == 2) {
			Gdx.app.exit();
		}
	}

	private void setValues() {
		
		currentItem = MyConstants.clamp(currentItem, 0, 2);
		
		jose = "Created by Jose Rodriguez-Rivas";
		
		titleW = cam.viewportWidth - 20;
		titleH = Game.res.getHeight("large", Game.TITLE, cam.viewportWidth - 20, Align.center, true);
		
		titleX = 10;
		titleY = (cam.viewportHeight * .75f) + (titleH * .5f);

		currentOption = "";

		if(currentItem == 0) {
			currentOption = "Play";
		}
		if(currentItem == 1) {
			currentOption = "Credits";
		}
		if(currentItem == 2) {
			currentOption = "Quit";
		}
		
		
		currentOptionW = cam.viewportWidth - 20;
		currentOptionH = Game.res.getHeight("mItems", currentOption, currentOptionW, Align.center, true);
		
		currentOptionX = 10;
		currentOptionY = (cam.viewportHeight * .45f) + (currentOptionH * .5f);

		jH = Game.res.getHeight("small", jose, 0, Align.bottomLeft, false);
		jX = 10;
		jY = jH + 10;
		
		nH = Game.res.getHeight("small", Game.NEWS, 0, Align.bottomRight, false);
		nW = Game.res.getWidth("small", Game.NEWS, 0, Align.bottomRight, false);
		nX = cam.viewportWidth - nW - 10;
		nY = nH + 10;
		
	}

	@Override
	public void update(float dt) {

		setValues();
		
		for(Star s: stars) {
			s.update(cam.viewportWidth, cam.viewportHeight);
		}
	}

	@Override
	public void draw(SpriteBatch sb, ShapeRenderer sr, float dt) {


		float coW = Game.res.getWidth("mItems", currentOption, currentOptionW, Align.center, true);
		float bgx = (cam.viewportWidth * .5f) - (coW * .5f) - 10;
		float bgy = (currentOptionY - currentOptionH) -10;
		float bgw = coW + 20;
		float bgh = currentOptionH + 20;
		
		sr.begin(ShapeType.Filled);
		sr.setProjectionMatrix(cam.combined);
		for(Star s : stars) {
			s.draw(sr);
		}
		sr.setColor(Color.WHITE);
		if(currentItem > 0) {
			sr.triangle(bgx - 30, bgy + (bgh * .5f), bgx - 5, bgy + (bgh * .75f), bgx - 5, bgy + (bgh * .25f));
		}
		if(currentItem < 2) {
			sr.triangle(bgx + bgw + 30, bgy + (bgh * .5f), bgx + bgw + 5, bgy + (bgh * .75f), bgx + bgw + 5, bgy + (bgh * .25f));
		}
		sr.end();
		
		MyConstants.drawTextBg(sr, bgx, bgy, bgw, bgh, cam);
		
		sb.begin();
		sb.setProjectionMatrix(cam.combined);
		Game.res.getFont("large").draw(sb, Game.TITLE, titleX, titleY, titleW, Align.center, true);
		Game.res.getFont("mItems").draw(sb, currentOption, currentOptionX, currentOptionY, currentOptionW, Align.center, true);
		Game.res.getFont("small").draw(sb, Game.NEWS, nX, nY, 0, Align.bottomLeft, false);
		Game.res.getFont("small").draw(sb, jose, jX, jY, 0, Align.bottomLeft, false);
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
