package com.tcg.superstardefender.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tcg.superstardefender.Game;
import com.tcg.superstardefender.MyCamera;
import com.tcg.superstardefender.MyConstants;
import com.tcg.superstardefender.MyConstants.States;
import com.tcg.superstardefender.entities.*;
import com.tcg.superstardefender.managers.GameStateManager;
import com.tcg.superstardefender.managers.MyInput;

public class PlayState extends GameState {
	
	private MyCamera cam;
	private Viewport view;
	
	private World w;
	
	private Array<Star> stars;
	
	private Player p;
	
	private int score;
	
	private int enemiesKilled;
	
	private int enemiesNeeded;
	
	private boolean won;
	
	private boolean gop;
	
	private float gameOverTime, gameOverTimer;

	private HUD hud;
	
	public PlayState(GameStateManager gsm) {
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
		
		try {
			w = new World();
		} catch (LevelDoesNotExist e) {
			e.printStackTrace();
		}
		
		p = new Player();
		
		gameOverTime = 0;
		gameOverTimer = 7.818f;
		
		gop = false;
		
		score = 0;
		
		enemiesKilled = 0;
		enemiesNeeded = (int)(((Game.level + 1f) / 4f) * 20f);
		
		hud = new HUD();
		
		won = false;
		
		Game.res.getMusic("level" + Game.level).play();

	}

	@Override
	public void handleInput() {
		if(MyInput.keyPressed(MyInput.BACK)) {
			gsm.setState(States.LEVELSELECT, true);
		}
		p.handleInput();
	}

	@Override
	public void update(float dt) {

		p.update(w, cam);
		
		for(Star s: stars) {
			s.update(cam.viewportWidth, cam.viewportHeight);
		}
		
		if(score > Game.highscore[Game.level]) {
			Game.highscore[Game.level] = score;
		}
		
		if(!p.isAlive()) {
			if(!gop) {
				Game.res.getMusic("level" + Game.level).stop();
				Game.res.getMusic("gameover").play();
				Gdx.input.vibrate(700);
				gop = true;
			} 
			gameOverTime += dt;
			if(gameOverTime > gameOverTimer) {
				gameOverTime = 0;
				gsm.setState(States.LEVELSELECT, true);
			}
		}
		
		won = enemiesKilled >= enemiesNeeded;
		
		hud.update(score, enemiesKilled, enemiesNeeded);
		
	}

	@Override
	public void draw(SpriteBatch sb, ShapeRenderer sr, float dt) {

		sr.begin(ShapeType.Filled);
		sr.setProjectionMatrix(cam.combined);
		for(Star s : stars) {
			s.draw(sr);
		}
		sr.end();
		
		w.render(cam);
		
		sb.begin();
		sb.setProjectionMatrix(cam.combined);
		p.draw(sr, sb, dt);
		sb.end();
		
		sr.begin(ShapeType.Filled);
		sr.setProjectionMatrix(cam.combined);
		p.drawBullets(sr, sb, dt);
		sr.end();
		
		hud.draw(sb, sr, cam, p, won);
		
	}

	@Override
	public void resize(int width, int height) {
		view.update(width, height, true);
	}

	@Override
	public void dispose() {
		w.dispose();
		stars.clear();
		p.dispose();
	}

}
