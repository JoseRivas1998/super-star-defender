package com.tcg.superstardefender.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
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
	
	private Array<Particle> part;
	
	private Player p;
	
	private Array<Enemy> enemies;
	
	private int score;
	
	private int enemiesKilled;
	
	private int enemiesNeeded;
	
	private float enemyChance;
	
	private boolean started;
	
	private float startTime, startTimer;
	
	private boolean won;
	
	private boolean highScoreBeat;
	
	private boolean firstTime;
	
	private boolean gop;
	
	private float gameOverTime, gameOverTimer;
	
	private String gameOver;

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
		
		part = new Array<Particle>();
		
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
		
		enemies = new Array<Enemy>();
		
		gameOverTime = 0;
		gameOverTimer = 1f;
		
		gameOver = "";
		
		gop = false;
		
		score = 0;
		
		enemiesKilled = 0;
		enemiesNeeded = (int)(((Game.level + 1f) / 4f) * 20f);
		
		enemyChance = enemiesNeeded / 1000f;
		
		hud = new HUD();
		
		won = false;
		highScoreBeat = false;
		firstTime = Game.highscore[Game.level] == 0;
		
		started = false;
		
		startTime = 0;
		startTimer = 2;
		
		Game.res.getMusic("level" + Game.level).play();

	}

	@Override
	public void handleInput() {
		if(MyInput.keyPressed(MyInput.BACK)) {
			if(gop) {
				if((Game.levelsUnlocked < (Game.level + 1) + 1) && won) {
					Game.levelsUnlocked = (Game.level + 1) + 1;
					Game.levelsUnlocked = MyConstants.clamp(Game.levelsUnlocked, 0, 4);
				}
			}
			gsm.setState(States.LEVELSELECT, true);
		}
		p.handleInput();
	}

	@Override
	public void update(float dt) {

		p.update(w, cam, enemies);
		
		if(started) {
			if(MathUtils.randomBoolean(enemyChance)) {
				enemies.add(new Enemy());
			}
		} else {
			startTime += dt;
			if(startTime >= startTimer) {
				startTime = 0;
				started = true;
			}
		}
		
		for(Enemy e : enemies) {
			e.update(w, cam);
			if(e.getY() < -500) {
				enemies.removeValue(e, true);
			}
			for(Bullet b : p.getBullets()) {
				if(e.collidingWith(b)) {
					p.getBullets().removeValue(b, true);
					Game.res.getSound("death").play(Game.VOLUME * .5f);
					part.add(new Particle(e.getCenter(), MyConstants.randomColor(), 1));
					enemies.removeValue(e, true);
					score += 100;
					enemiesKilled++;
				}
			}
		}
		
		for(Star s: stars) {
			s.update(cam.viewportWidth, cam.viewportHeight);
		}
		
		if(score > Game.highscore[Game.level]) {
			if(!firstTime) {
				highScoreBeat = true;
			}
			Game.highscore[Game.level] = score;
		}
		
		if(!p.isAlive()) {
			if(!gop) {
				Game.res.getMusic("level" + Game.level).stop();
				if((won && firstTime) || (won && highScoreBeat)) {
					Game.res.getMusic("victory").play();
					gameOver = "Victory!";
					gameOverTimer = 3.175f;
				} else {
					Game.res.getMusic("gameover").play();
					gameOver = "Game Over";
					gameOverTimer = 7.818f;
				}
				Gdx.input.vibrate(700);
				gop = true;
			} 
			gameOverTime += dt;
			if(gameOverTime > gameOverTimer) {
				gameOverTime = 0;
				if((Game.levelsUnlocked < (Game.level + 1) + 1) && won) {
					Game.levelsUnlocked = (Game.level + 1) + 1;
					Game.levelsUnlocked = MyConstants.clamp(Game.levelsUnlocked, 0, 4);
				}
				gsm.setState(States.LEVELSELECT, true);
			}
		}
		
		for(Particle par : part) {
			par.update(dt);
			if(par.isShouldRemove()) {
				part.removeValue(par, true);
			}
		}
		
		won = enemiesKilled >= enemiesNeeded;
		
		hud.update(score, enemiesKilled, enemiesNeeded, gameOver);
		
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
		for(Enemy e : enemies) {
			e.draw(sr, sb, dt);
		}
		sb.end();
		
		sr.begin(ShapeType.Filled);
		sr.setProjectionMatrix(cam.combined);
		p.drawBullets(sr, sb, dt);
		sr.end();
		
		sb.begin();
		for(Particle par : part) {
			par.draw(sr, sb, dt);
		}
		sb.end();
		
		hud.draw(sb, sr, cam, p, won, highScoreBeat);
		
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
		for(Enemy e : enemies) {
			e.dispose();
		}
		enemies.clear();
		for(Particle par : part) {
			par.dispose();
		}
		part.clear();
	}

}
