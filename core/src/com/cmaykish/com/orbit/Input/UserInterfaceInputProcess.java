package com.cmaykish.com.orbit.Input;

import com.badlogic.gdx.InputProcessor;
import com.cmaykish.com.orbit.Interface.UserInterface;

/*
 * Deals with button clicks and other UI events
 */
public class UserInterfaceInputProcess implements InputProcessor {

	boolean touching = false;

	UserInterface ui;

	public UserInterfaceInputProcess(UserInterface ui) {
		this.ui = ui;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		boolean isButton = ui.buttonClickDown(screenX, screenY);
		
		if (isButton){
			touching = true;
		}
		
		return isButton;

		// renderer.start = renderer.getTopLeftCorner().add(screenX, screenY);
		// renderer.end = renderer.getTopLeftCorner().add(screenX, screenY);

	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		boolean isButton = ui.buttonClickUp(screenX, screenY);

		if (isButton){
			touching = false;
		}
		
		return isButton;
				
		// if (renderer.sizeRect.contains(touch.x, touch.y)) {
		// Simulation.BODY_RADIUS = Simulation.BODY_RADIUS == 64 ? 8 :
		// Simulation.BODY_RADIUS * 2;
		// return true;
		// }
		//
		// if (renderer.buttonRect.contains(touch.x, touch.y)) {
		// Gdx.app.log("click", "click");
		//
		// simulation.launch = !simulation.launch;
		// return true;
		// }

		// Gdx.app.log("launch", screenX + ", " + screenY);

		// simulation.addBody(renderer.start,
		// simulation.getLaunchVelocity(renderer.start, renderer.end));
		//
		// renderer.start = new Vector2(Vector2.Zero);
		// renderer.end = new Vector2(Vector2.Zero);

	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return touching;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
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

}
