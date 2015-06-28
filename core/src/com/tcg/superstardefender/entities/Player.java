package com.tcg.superstardefender.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.tcg.superstardefender.Game;
import com.tcg.superstardefender.MyCamera;
import com.tcg.superstardefender.MyConstants;
import com.tcg.superstardefender.managers.MyInput;

public class Player extends Entity {

	private Rectangle ls, rs, ts, bs;
	private boolean touchingG;
	
	private Animation l, r, il, ir;
	private float stateTime;
	private TextureRegion currentFrame;
	private int dir;
	
	private Texture wltemp, wrtemp, irtemp, iltemp;
	
	private Array<Bullet> bullets;
	
	private boolean alive;

	public Player() {
		super();
		setPosition(384, 256);
		ls = new Rectangle();
		rs = new Rectangle();
		ts = new Rectangle();
		bs = new Rectangle();
		dir = MyConstants.RIGHT;
		bullets = new Array<Bullet>();
		alive = true;
		initializeAnimations();
	}
	
	private void initializeAnimations() {
		stateTime = 0;
		
		String p = "entities/player/";
		int numWlFrames = 4;
		wltemp = new Texture(p + "walk - left.png");
		TextureRegion[] wlframes = TextureRegion.split(wltemp, wltemp.getWidth() / numWlFrames, wltemp.getHeight())[0];

		int numWRFrames = 4;
		wrtemp = new Texture(p + "walk - right.png");
		TextureRegion[] wrframes = TextureRegion.split(wrtemp, wrtemp.getWidth() / numWRFrames, wrtemp.getHeight())[0];

		int numILFrames = 1;
		iltemp = new Texture(p + "idle - left.png");
		TextureRegion[] ilframes = TextureRegion.split(iltemp, iltemp.getWidth() / numILFrames, iltemp.getHeight())[0];

		int numIRFrames = 1;
		irtemp = new Texture(p + "idle - right.png");
		TextureRegion[] irframes = TextureRegion.split(irtemp, irtemp.getWidth() / numIRFrames, irtemp.getHeight())[0];
		
		float frameDur = .1f;
		l = new Animation(frameDur, wlframes);
		r = new Animation(frameDur, wrframes);
		il = new Animation(frameDur, ilframes);
		ir = new Animation(frameDur, irframes);
		
		currentFrame = irframes[0];
	}
	
	public void handleInput() {
		if(MyInput.keyDown(MyInput.LEFT)) {
			vel.x = -5;
			dir = MyConstants.LEFT;
		} else if(MyInput.keyDown(MyInput.RIGHT)) {
			vel.x = 5;
			dir = MyConstants.RIGHT;
		} else {
			vel.x = 0;
		}
		if(touchingG) {
			vel.y = 0;
			if(MyInput.keyPressed(MyInput.JUMP)) {
				Game.res.getSound("jump").play(.5f);
				vel.y = 17.5f;
			}
		}
		if(MyInput.keyPressed(MyInput.SHOOT)) {
			if(bullets.size < 10) {
				//TODO play sound
				bullets.add(new Bullet(dir, getPosition()));
			}
		}
	}

	public void update(World w, MyCamera cam) {
		if(alive) {
			bounds.width = 32;
			bounds.height = 48;
			
			if(!touchingG && vel.y > -10) {
				vel.y--;
			}
			
			bounds.x += vel.x;
			bounds.y += vel.y;
			
			bounds.y = MyConstants.clamp(bounds.y, -1000, MyConstants.WORLD_HEIGHT); //TODO min will be -1000 so that the death can process
			
			if(getTop() < -500) { //TODO enemy collision
				alive = false;
			}
			
			resetBounds();
			collisions(w);
			resetBounds();
		}
		updateBullets(w, cam);
	}
	
	private void updateBullets(World w, MyCamera cam) {
		for(Bullet b : bullets) {
			b.update();
			for(Rectangle r : w.getBounds()) {
				if(b.collidingWith(r)) {
					bullets.removeValue(b, true);
					return;
				}
			}
			if(!cam.inView(b.getCenter())) {
				bullets.removeValue(b, true);
			}
		}
	}
	
	private void resetBounds() {
		ls.width = 2;
		rs.width = 2;
		ts.width = 4;
		bs.width = 4;
		ls.height = 4;
		rs.height = 4;
		ts.height = 2;
		bs.height = 2;
		
		ls.x = bounds.x;
		ls.y = getCenter().y - (ls.height * .5f);
		rs.x = getRight() - rs.width;
		rs.y = ls.y;
		bs.x = getCenter().x - (bs.width * .5f);
		bs.y = bounds.y;
		ts.x = bs.x;
		ts.y = getTop() - ts.height;
	}
	
	private void collisionGround(World w) {
		for(Rectangle r : w.getBounds()) {
			if(bs.overlaps(r)) {
				bounds.y = r.y + r.height - 8;
				touchingG = true;
				break;
			} else {
				touchingG = false;
			}
		}
		resetBounds();
		for(Rectangle r : w.getBounds()) {
			if(rs.overlaps(r)) {
				bounds.x = r.x - bounds.width;
				break;
			} 
		}
		resetBounds();
		for(Rectangle r : w.getBounds()) {
			if(ls.overlaps(r)) {
				bounds.x = r.x + bounds.width;
				break;
			}
		}
		resetBounds();
		for(Rectangle r : w.getBounds()) {
			if(ts.overlaps(r)) {
				bounds.y = r.y - bounds.height;
				vel.y = 0;
				break;
			}
		}
		resetBounds();
		for(Rectangle r : w.getBounds()) {
			if(bs.overlaps(r) && ts.overlaps(r)) {
				bounds.y = r.y + r.height - 4;
				break;
			} 
		}
	}
	
	private void collisions(World w) {
		collisionGround(w);
		
//		collisionGround(w, tutorial); TODO after enemy collision
	}
	
	@Override
	public void draw(ShapeRenderer sr, SpriteBatch sb, float dt) {

		stateTime +=  dt;
		
		if(vel.x == 0) {
			if(dir == MyConstants.LEFT) {
				currentFrame = il.getKeyFrame(stateTime, true);
			}
			if(dir == MyConstants.RIGHT) {
				currentFrame = ir.getKeyFrame(stateTime, true);
			}
		} else {
			if(dir == MyConstants.LEFT) {
				currentFrame = l.getKeyFrame(stateTime, true);
			}
			if(dir == MyConstants.RIGHT) {
				currentFrame = r.getKeyFrame(stateTime, true);
			}
		}
		
		if(alive) sb.draw(currentFrame, getX(), getY());
		
	}
	
	public void drawBullets(ShapeRenderer sr, SpriteBatch sb, float dt) {
		for(Bullet b : bullets) {
			b.draw(sr, sb, dt);
		}
	}

	@Override
	public void dispose() {
		wltemp.dispose();
		wrtemp.dispose();
		irtemp.dispose();
		iltemp.dispose();
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

}
