package com.tcg.superstardefender.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.tcg.superstardefender.Game;
import com.tcg.superstardefender.MyCamera;
import com.tcg.superstardefender.MyConstants;

public class HUD {

	private String name, score, highScore, enemies, gameOver;
	
	private float nX, nY, nH, sX, sY, sH, hsX, hsY, hsH, eX, eY, goX, goY, goW, goH;
	
	public void update(int scoreint, int enemiesKilled, int enemiesNeeded) {
		name = Game.levelNames[Game.level];
		score = "Score: " + MyConstants.getScore(scoreint);
		highScore = "High Score: " + MyConstants.getScore(Game.highscore[Game.level]);
		enemies = "Enemies: " + enemiesKilled +"/" + enemiesNeeded;
		gameOver = "Game Over";
		
		nH = Game.res.getHeight("small", name, 0, Align.left, false);
		
		nX = 42;
		nY = MyConstants.WORLD_HEIGHT - 11;
		
		sH = Game.res.getHeight("small", score, 0, Align.left, false);
		
		sX = 42;
		sY = nY - nH - 10;
		
		hsH = Game.res.getHeight("small", highScore, 0, Align.left, false);
		
		hsX = 42;
		hsY = sY - sH - 10;
		
		eX = 42;
		eY = hsY - hsH - 10;
		
		goW = MyConstants.WOLRD_WIDTH - 20;
		goH = Game.res.getHeight("large", gameOver, goW, Align.center, true);
		
		goX = 10;
		goY = (MyConstants.WORLD_HEIGHT * .5f) + (goH * .5f);
	}
	public void draw(SpriteBatch sb, ShapeRenderer sr, MyCamera cam, Player p, boolean won) {
		sb.begin();
		sb.setProjectionMatrix(cam.combined);
		if(won) Game.res.getFont("small").setColor(Color.WHITE);
		Game.res.getFont("small").draw(sb, name, nX, nY);
		Game.res.getFont("small").draw(sb, score, sX, sY);
		Game.res.getFont("small").draw(sb, highScore, hsX, hsY);
		if(won) Game.res.getFont("small").setColor(MyConstants.randomColor());
		Game.res.getFont("small").draw(sb, enemies, eX, eY);
		if(won) Game.res.getFont("small").setColor(Color.WHITE);
		Game.res.getFont("large").setColor(Color.RED);
		if(!p.isAlive())Game.res.getFont("large").draw(sb, gameOver, goX, goY, goW, Align.center, true);
		Game.res.getFont("large").setColor(Color.WHITE);
		sb.end();
		
	}

}
