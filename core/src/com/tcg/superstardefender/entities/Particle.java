package com.tcg.superstardefender.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Particle extends Entity {
	
	private float time, timer;
	private boolean shouldRemove;
	private Texture temp;
	private Animation anim;
	private TextureRegion currentFrame;
	private float scale;
	Color c;

	public Particle(Vector2 pos, Color c, float scale) {
		init(pos, c, scale);
	}
	
	private void init(Vector2 pos, Color c, float scale) {
		bounds = new Rectangle();
		setPosition(pos);
		
		String p = "explode.png";
		int numJRFrames = 8;
		temp = new Texture(p);
		TextureRegion[] frames = TextureRegion.split(temp, temp.getWidth() / numJRFrames, temp.getHeight())[0];
		currentFrame = frames[0];
		anim = new Animation(.05f, frames);
		
		time = 0;
		timer = anim.getAnimationDuration();
		shouldRemove = false;
		
		this.scale = scale;
		
		this.c = c;
	}
	
	public void update(float dt) {
		time += dt;
		shouldRemove = time > timer;
		currentFrame = anim.getKeyFrame(time, true);
	}

	@Override
	public void draw(ShapeRenderer sr, SpriteBatch sb, float dt) {
		sb.setColor(c);
		float width = currentFrame.getRegionWidth() * scale;
		float height = currentFrame.getRegionHeight() * scale;
		sb.draw(currentFrame, getX() - (width * .5f), getY() - (height * .5f), width, height);
		sb.setColor(Color.WHITE);
	}

	public boolean isShouldRemove() {
		return shouldRemove;
	}

	public void setShouldRemove(boolean shouldRemove) {
		this.shouldRemove = shouldRemove;
	}
	
	public void dispose() {
		temp.dispose();
	}

}
