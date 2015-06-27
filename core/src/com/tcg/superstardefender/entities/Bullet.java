package com.tcg.superstardefender.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.tcg.superstardefender.MyConstants;

public class Bullet extends Entity {

	public Bullet(int dir, Vector2 pos) {
		super();
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
	}
	
	@Override
	public void draw(ShapeRenderer sr, SpriteBatch sb, float dt) {
		sr.setColor(Color.RED);
		sr.rect(getX(), getY(), getWidth(), getHeight());
	}

	@Override
	public void dispose() {}

}
