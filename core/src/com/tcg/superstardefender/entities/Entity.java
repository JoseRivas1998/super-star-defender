package com.tcg.superstardefender.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {

	protected Rectangle bounds;
	protected Vector2 vel;
	
	protected Entity() {
		bounds = new Rectangle();
		vel = new Vector2();
	}
	
	public abstract void draw(ShapeRenderer sr, SpriteBatch sb, float dt);
	
	public abstract void dispose();
	
	
	public float getX() {
		return bounds.x;
	}

	public void setX(float x) {
		bounds.x = x;
	}

	public float getY() {
		return bounds.y;
	}

	public void setY(float y) {
		bounds.y = y;
	}

	public float getWidth() {
		return bounds.width;
	}

	public void setWidth(float width) {
		bounds.width = width;
	}

	public float getHeight() {
		return bounds.height;
	}

	public void setHeight(float height) {
		bounds.height = height;
	}

	public Vector2 getPosition() {
		return new Vector2(bounds.x, bounds.y);
	}
	
	public void setPosition(float x, float y) {
		bounds.x = x;
		bounds.y = y;
	}
	
	public void setPosition(Vector2 point) {
		setPosition(point.x, point.y);
	}
	
	public Vector2 getDimensions() {
		return new Vector2(bounds.width, bounds.height);
	}
	
	public void setDimensions(float width, float height) {
		bounds.width = width;
		bounds.height = height;
	}
	
	public void setDimensions(Vector2 dim) {
		setDimensions(dim.x, dim.y);
	}
	
	public Vector2 getCenter() {
		float x = bounds.x + (bounds.width * .5f);
		float y = bounds.y + (bounds.height * .5f);
		return new Vector2(x, y);
	}
	
	public float getRight() {
		return bounds.x + bounds.width;
	}
	
	public float getTop() {
		return bounds.y + bounds.height;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public Vector2 getVel() {
		return vel;
	}

	public void setVel(Vector2 vel) {
		this.vel = vel;
	}
	
	public boolean collidingWith(Entity e) {
		return bounds.overlaps(e.getBounds());
	}
	
	public boolean collidingWith(Rectangle r) {
		return bounds.overlaps(r);
	}
	
}
