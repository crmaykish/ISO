package com.cmaykish.com.orbit.Input;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class CameraInputProcessor implements InputProcessor {

	OrthographicCamera camera;

	private int startX, startY;
	private Vector2 cameraStart;

	public CameraInputProcessor(OrthographicCamera camera) {
		this.camera = camera;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		startX = screenX;
		startY = screenY;

		cameraStart = new Vector2(screenX, screenY).add(camera.position.x
				- camera.viewportWidth / 2, camera.position.y
				- camera.viewportHeight / 2);

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (startX == screenX && startY == screenY) {
			return false;
		}

		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (cameraStart != null){
			camera.position.x = -screenX + camera.viewportWidth / 2 + cameraStart.x;
			camera.position.y = -screenY + camera.viewportHeight / 2 + cameraStart.y;
		}
		return false;
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
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
//		float level = (float)amount / 2;
//		if (camera.zoom + level >= 1.0f && camera.zoom + level < 20.0f)
//			camera.zoom += level;
		return false;
	}

}
