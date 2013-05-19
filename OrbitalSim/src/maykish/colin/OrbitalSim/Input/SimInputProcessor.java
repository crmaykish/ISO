package maykish.colin.OrbitalSim.Input;

import maykish.colin.OrbitalSim.Simulation;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class SimInputProcessor implements InputProcessor{

	Simulation sim;
	OrthographicCamera camera;
	
	public SimInputProcessor(Simulation sim, OrthographicCamera camera) {
		this.sim = sim;
		this.camera = camera;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		float x = screenX + camera.position.x - camera.viewportWidth / 2;
		float y = screenY + camera.position.y - camera.viewportHeight / 2;

		sim.addCircle(x, y);

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
