package com.tcg.superstardefender.gamestates;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.tcg.superstardefender.managers.GameStateManager;

public class EmptyState extends GameState {

	public EmptyState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	protected void init() {

	}

	@Override
	public void handleInput() {

	}

	@Override
	public void update(float dt) {

	}

	@Override
	public void draw(SpriteBatch sb, ShapeRenderer sr, float dt) {

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void dispose() {

	}

}
