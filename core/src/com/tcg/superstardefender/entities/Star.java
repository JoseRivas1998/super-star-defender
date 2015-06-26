package com.tcg.superstardefender.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.tcg.superstardefender.MyConstants;

public class Star {

	Vector2 pos, vel;
	
	public Star(float worldWidth, float worldHeight) {
		pos = new Vector2();
		vel = new Vector2();
		reset(worldWidth, worldHeight);
	}
	
	private void reset(float worldWidth, float worldHeight) {
		float speed = MathUtils.random(5, 8);
		pos.set(MathUtils.random(worldWidth), MathUtils.random(worldHeight));
		float dx, dy;
		dx = pos.x - (worldWidth * .5f);
		dy = pos.y - (worldHeight * .5f);
		float r = MathUtils.atan2(dy, dx);
		vel.x = MathUtils.cos(r) * speed;
		vel.y = MathUtils.sin(r) * speed;
	}
	
	public void update(float worldWidth, float worldHeight) {
		pos.x += vel.x;
		pos.y += vel.y;
		if(MyConstants.wouldWrap(pos.x, 0, worldWidth) || MyConstants.wouldWrap(pos.y, 0, worldHeight)) {
			reset(worldWidth, worldHeight);
		}
	}
	
	public void draw(ShapeRenderer sr) {
		sr.setColor(MyConstants.randomColor());
		sr.rect(pos.x, pos.y, 1, 1);
		sr.setColor(Color.WHITE);
	}

}
