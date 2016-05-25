package com.cmaykish.com.orbit.Input;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.cmaykish.com.orbit.Simulation;

public class BodyInputProcessor implements InputProcessor{

	Simulation sim;
	OrthographicCamera camera;
	
	public BodyInputProcessor(Simulation sim, OrthographicCamera camera) {
		this.sim = sim;
		this.camera = camera;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Vector3 touch = new Vector3(screenX, screenY, 0);
		camera.unproject(touch);
		sim.addCircle(touch.x, touch.y);

		return true;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		return false;
	}
	@Override
	public boolean keyUp(int keycode) {
		return false;
	}
	@Override
	public boolean keyTyped(char character) {
		return false;
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}
	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
