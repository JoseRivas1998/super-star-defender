package com.tcg.superstardefender.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.tcg.superstardefender.MyConstants;

public class Bullet extends Entity {

	private float distance;
	
	public final static float destroyDistance = 20 * 10;
	
	public Bullet(int dir, Vector2 pos) {
		super();
		distance = 0;
		if(dir == MyConstants.RIGHT) {
			setPosition(pos.x - 2, pos.y + 30);
			vel.set(20, 0);
		}
		if(dir == MyConstants.LEFT) {
			setPosition(pos.x + 22, pos.y + 30);
			vel.set(-20, 0);
		}
		bounds.width = 5;
		bounds.height = 2;
	}

	public void update() {
		bounds.x += vel.x;
		bounds.y += vel.y;
		distance += Math.abs(vel.x);
	}
	
	@Override
	public void draw(ShapeRenderer sr, SpriteBatch sb, float dt) {
		sr.setColor(Color.RED);
		sr.rect(getX(), getY(), getWidth(), getHeight());
	}

	@Override
	public void dispose() {}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

}
