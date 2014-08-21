package maykish.colin.OrbitalSim.Input;

import maykish.colin.OrbitalSim.Renderer;
import maykish.colin.OrbitalSim.Simulation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class ToolInputProcessor implements InputProcessor{

	private int startX, startY;
	
	Simulation simulation;
	Renderer renderer;	// TODO: put all this crap in a UI class, not the renderer
	OrthographicCamera camera;
	
	public ToolInputProcessor(Simulation simulation, Renderer renderer, OrthographicCamera camera) {
		this.simulation = simulation;
		this.renderer = renderer;
		this.camera = camera;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Vector3 touch = new Vector3(screenX, screenY, 0);
		camera.unproject(touch);
		
		if (!simulation.launch){
			return false;
		}
		
		renderer.start = renderer.getTopLeftCorner().add(screenX, screenY);
		renderer.end = renderer.getTopLeftCorner().add(screenX, screenY);
		
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Vector3 touch = new Vector3(screenX, screenY, 0);
		camera.unproject(touch);
		
		if (renderer.sizeRect.contains(touch.x, touch.y)) {
			Simulation.BODY_RADIUS = Simulation.BODY_RADIUS == 64 ? 8 : Simulation.BODY_RADIUS * 2;
			return true;
		}
		
		if (renderer.buttonRect.contains(touch.x, touch.y)) {
			Gdx.app.log("click", "click");
			
			simulation.launch = !simulation.launch;
			return true;
		}
		
		if (simulation.launch){
			Gdx.app.log("launch", screenX + ", " + screenY);
			
			
			simulation.addBody(renderer.start, simulation.getLaunchVelocity(renderer.start, renderer.end));
			
			renderer.start = new Vector2(Vector2.Zero);
			renderer.end = new Vector2(Vector2.Zero);
			
			return true;
		}

		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		Vector3 touch = new Vector3(screenX, screenY, 0);
		camera.unproject(touch);

		if (simulation.launch){
			renderer.end.x = touch.x;
			renderer.end.y = touch.y;
			return true;
		}
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
