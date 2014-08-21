package maykish.colin.OrbitalSim.Screens;


import maykish.colin.OrbitalSim.OrbitalSim;
import maykish.colin.OrbitalSim.Renderer;
import maykish.colin.OrbitalSim.Simulation;
import maykish.colin.OrbitalSim.Input.CameraInputProcessor;
import maykish.colin.OrbitalSim.Input.BodyInputProcessor;
import maykish.colin.OrbitalSim.Input.ToolInputProcessor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class SimScreen extends AbstractScreen{

	private OrthographicCamera camera;
	
	private Simulation sim;
	private Renderer renderer;
	
	public SimScreen(OrbitalSim game) {
		super(game);
	}

	@Override
	public void show() {
		super.show();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		sim = new Simulation();
		renderer = new Renderer(batch, camera);
		
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(new ToolInputProcessor(sim, renderer, camera));
		multiplexer.addProcessor(new CameraInputProcessor(camera));
		multiplexer.addProcessor(new BodyInputProcessor(sim, camera));
		Gdx.input.setInputProcessor(multiplexer);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		
		camera.update();
		
		sim.update();
		renderer.render(sim);
	}

	@Override
	public void dispose() {
		super.dispose();
	}

}