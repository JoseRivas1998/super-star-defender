package com.tcg.superstardefender.gamestates;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tcg.superstardefender.Game;
import com.tcg.superstardefender.MyCamera;
import com.tcg.superstardefender.MyConstants;
import com.tcg.superstardefender.MyConstants.States;
import com.tcg.superstardefender.managers.GameStateManager;
import com.tcg.superstardefender.managers.MyInput;

public class LevelSelectState extends GameState {
	
	private MyCamera cam;
	private Viewport view;
	
	private Texture[] levelImages;
	
	private Texture locked;
	
	private boolean levelLocked;
	
	private String top, level, levelName, highScore;
	
	private float topX, topY, topW, topH, lX, lY, lW, lH, lnX, lnY, lnW, lnH, hsX, hsY, hsW;

	public LevelSelectState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	protected void init() {
		cam = new MyCamera();
		view = new StretchViewport(MyConstants.WOLRD_WIDTH, MyConstants.WORLD_HEIGHT, cam);
		
		view.apply();
		view.update((int) Game.SIZE.x, (int) Game.SIZE.y, true);
		
		String path = "maps/map";
		String png = ".png";
		
		levelImages = new Texture[4]; 
		for(int i = 0; i < 4; i++) {
			try{
				levelImages[i] = new Texture(path + i + png);
			} catch(Exception e) {
				System.out.println(e.toString());
				break;
			}
		}
		
		locked = new Texture("maps/locked.png");
		
		setValues();
		
		Game.res.getMusic("levelselect").play();
		
	}
	
	private void setValues() {
		Game.level = MyConstants.clamp(Game.level, 0, 3);
		
		levelLocked = Game.level + 1 > Game.levelsUnlocked;
		
		top = "Level Select";
		
		level = "Level " + (Game.level + 1);
		
		levelName = Game.levelNames[Game.level];
		
		highScore = "High Score: " + MyConstants.getScore(Game.highscore[Game.level]);
		
		topW = MyConstants.WOLRD_WIDTH - 20;
		topH = Game.res.getHeight("large", top, topW, Align.center, true);
		
		topX = 10;
		topY = (MyConstants.WORLD_HEIGHT * .8f) + (topH * .5f);
		
		lW = MyConstants.WOLRD_WIDTH - 20;
		lH = Game.res.getHeight("main", level, lW, Align.center, true);
		
		lnW = MyConstants.WOLRD_WIDTH - 20;
		lnH = Game.res.getHeight("main", levelName, lnW, Align.center, true);
		
		hsW = MyConstants.WOLRD_WIDTH - 20;
		
		lX = 10;
		lY = (MyConstants.WORLD_HEIGHT * .25f) - 10;
		
		lnX = 10;
		lnY = lY - lH - 10;
		
		hsX = 10;
		hsY = lnY - lnH - 10;
	}

	@Override
	public void handleInput() {
		if(MyInput.keyPressed(MyInput.LEFT)) {
			Game.level--;
		}
		if(MyInput.keyPressed(MyInput.RIGHT)) {
			Game.level++;
		}
		if(MyInput.keyPressed(MyInput.BACK)) {
			gsm.setState(States.TITLE, true);
		}
		if(MyInput.keyPressed(MyInput.START) || MyInput.keyPressed(MyInput.JUMP)) {
			select();
		}
	}

	private void select() {
		if(levelLocked) {
			
		} else {
			gsm.setState(States.PLAY, true);
		}
	}
	
	@Override
	public void update(float dt) {

		setValues();
		
	}

	@Override
	public void draw(SpriteBatch sb, ShapeRenderer sr, float dt) {

		sb.begin();
		sb.setProjectionMatrix(cam.combined);
		try {
			sb.draw(levelImages[Game.level], MyConstants.WOLRD_WIDTH * .25f, MyConstants.WORLD_HEIGHT * .25f, MyConstants.WOLRD_WIDTH * .5f, MyConstants.WORLD_HEIGHT * .5f);
		} catch(Exception e){}
		if(levelLocked) {
			sb.draw(locked, MyConstants.WOLRD_WIDTH * .25f, MyConstants.WORLD_HEIGHT * .25f, MyConstants.WOLRD_WIDTH * .5f, MyConstants.WORLD_HEIGHT * .5f);
		}
		Game.res.getFont("large").draw(sb, top, topX, topY, topW, Align.center, true);
		Game.res.getFont("main").draw(sb, level, lX, lY, lW, Align.center, true);
		Game.res.getFont("main").draw(sb, levelName, lnX, lnY, lnW, Align.center, true);
		Game.res.getFont("main").draw(sb, highScore, hsX, hsY, hsW, Align.center, true);
		sb.end();
		
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.WHITE);
		if(Game.level > 0)sr.triangle((MyConstants.WOLRD_WIDTH * .25f) - 10, (MyConstants.WORLD_HEIGHT * .5f) - (31f * .5f), (MyConstants.WOLRD_WIDTH * .25f) - 35, (MyConstants.WORLD_HEIGHT * .5f), (MyConstants.WOLRD_WIDTH * .25f) - 10, (MyConstants.WORLD_HEIGHT * .5f) + (31f * .5f));
		if(Game.level < 3)sr.triangle((MyConstants.WOLRD_WIDTH * .75f) + 10, (MyConstants.WORLD_HEIGHT * .5f) - (31f * .5f), (MyConstants.WOLRD_WIDTH * .75f) + 35, (MyConstants.WORLD_HEIGHT * .5f), (MyConstants.WOLRD_WIDTH * .75f) + 10, (MyConstants.WORLD_HEIGHT * .5f) + (31f * .5f));
		sr.end();
	}

	@Override
	public void resize(int width, int height) {
		view.update(width, height, true);
	}

	@Override
	public void dispose() {
		for(Texture t : levelImages) {
			try {
				t.dispose();
			} catch(Exception e) {
				System.out.println(e.toString());
				break;
			}
		}
		locked.dispose();
	}

}
