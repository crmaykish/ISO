package com.cmaykish.com.orbit.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cmaykish.com.orbit.OrbitalSim;

public abstract class AbstractScreen implements Screen {

	OrbitalSim game;
	
	SpriteBatch batch;
	
	public AbstractScreen(OrbitalSim game) {
		this.game = game;
		batch = new SpriteBatch();
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void resize(int width, int height) {
	}
	@Override
	public void hide() {
	}
	@Override
	public void pause() {
	}
	@Override
	public void resume() {
	}
	
}
