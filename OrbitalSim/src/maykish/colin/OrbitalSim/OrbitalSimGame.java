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
	
	private Rectangle interbodyGravButton;
	private Rectangle collisionButton;
	private Rectangle fixedSunButton;
	private Rectangle solarMassButton;
	private Rectangle gButton;
	private Rectangle brushSizeButton;
	private Rectangle smallBodyButton;
	private Rectangle startButton;
	
	private String interbodyGravStatus;
	private String collisionsStatus;
	private String fixedSunStatus;
	private int solarMassStatus;
	private float gStatus;
	private int brushSizeStatus;
	private String smallBodyStatus;
	
	private Simulation sim;
	
	@Override
	public void create() {
		camera = new OrthographicCamera();
		camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setScale(1, -1);
		
		interbodyGravButton = new Rectangle(100, 100, 100, 20);
		collisionButton = new Rectangle(100, 150, 100, 20);
		fixedSunButton = new Rectangle(100, 200, 100, 20);
		solarMassButton = new Rectangle(100, 250, 100, 20);
		gButton = new Rectangle(100, 300, 300, 20);
		brushSizeButton = new Rectangle(100, 350, 100, 20);
		smallBodyButton = new Rectangle(220, 350, 100, 20);
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
			
			interbodyGravStatus = sim.getInterbodyGravity() ? "On" : "Off";
			collisionsStatus = sim.getCollide() ? "On" : "Off";
			fixedSunStatus = sim.getFixedSun() ? "On" : "Off";
			solarMassStatus = sim.getSolarMass();
			gStatus = sim.getG();
			brushSizeStatus = sim.getBrushSize();
			smallBodyStatus = sim.getSmallBody() ? "Small" : "Large";
			
			
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			font.draw(batch, "Inaccurate Simulator of Orbits", 100, 50);
			font.draw(batch, "--------------------------------------", 100, 66);
			font.draw(batch, "Interbody Gravity: " + interbodyGravStatus, interbodyGravButton.x, interbodyGravButton.y);
			font.draw(batch, "Collisions: " + collisionsStatus, collisionButton.x, collisionButton.y);
			font.draw(batch, "Fixed Sun: " + fixedSunStatus, fixedSunButton.x, fixedSunButton.y);
			font.draw(batch, "Solar Mass: " + solarMassStatus, solarMassButton.x, solarMassButton.y);
			font.draw(batch, "G Constant: " + gStatus, gButton.x, gButton.y);
			font.draw(batch, "Brush Size: " + brushSizeStatus, brushSizeButton.x, brushSizeButton.y);
			font.draw(batch, "Body Size: " + smallBodyStatus, smallBodyButton.x, smallBodyButton.y);
			font.draw(batch, "START", startButton.x, startButton.y);
			batch.end();
			
		}
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (interbodyGravButton.contains(screenX, screenY)){
			sim.toggleInterbodyGravity();
		}
		
		if (collisionButton.contains(screenX, screenY)){
			sim.toggleCollide();
		}
		
		if (fixedSunButton.contains(screenX, screenY)){
			sim.toggleFixedSun();
		}
		
		if (solarMassButton.contains(screenX, screenY)){
			sim.toggleSolarMass();
		}
		
		if (gButton.contains(screenX, screenY)){
			sim.toggleG();
		}
		
		if (brushSizeButton.contains(screenX, screenY)){
			sim.toggleBrushSize();
		}
		
		if (smallBodyButton.contains(screenX, screenY)){
			sim.toggleSmallBody();
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
