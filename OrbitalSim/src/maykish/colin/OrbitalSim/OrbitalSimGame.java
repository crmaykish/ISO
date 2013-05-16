package maykish.colin.OrbitalSim;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class OrbitalSimGame implements ApplicationListener, InputProcessor {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private BitmapFont font;
	
	private Rectangle collisionButton;
	private Rectangle startButton;
	
	private String collisionsStatus;
	
	private Simulation sim;
	
	@Override
	public void create() {
		camera = new OrthographicCamera();
		camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setScale(1, -1);
		
		collisionButton = new Rectangle(100, 200, 100, 20);
		startButton = new Rectangle(100, 400, 100, 20);
		
		sim = new Simulation(camera, batch);
		
		
		Gdx.input.setInputProcessor(this);
		
		
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		
		if (sim.isRunning()){
			sim.render();
		}
		else {
			
			collisionsStatus = sim.getCollide() ? "On" : "Off";
			
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			font.draw(batch, "Inaccurate Simulator of Orbits", 100, 100);
			font.draw(batch, "Collisions: " + collisionsStatus, collisionButton.x, collisionButton.y);
			font.draw(batch, "START", startButton.x, startButton.y);
			batch.end();
			
		}
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (collisionButton.contains(screenX, screenY)){
			sim.toggleCollide();
		}
		
		
		
		if (startButton.contains(screenX, screenY)){
			sim.initialize();
			sim.setRunning();
		}

		return false;
	}
	
	@Override
	public void dispose() {
	}
	@Override
	public void resize(int width, int height) {
	}
	@Override
	public void pause() {
	}
	@Override
	public void resume() {
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
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
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
