package com.cmaykish.com.orbit.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.cmaykish.com.orbit.Input.BodyInputProcessor;
import com.cmaykish.com.orbit.Input.CameraInputProcessor;
import com.cmaykish.com.orbit.Input.UserInterfaceInputProcess;
import com.cmaykish.com.orbit.Interface.UserInterface;
import com.cmaykish.com.orbit.OrbitalSim;
import com.cmaykish.com.orbit.Renderer;
import com.cmaykish.com.orbit.Simulation;

public class SimScreen extends AbstractScreen{

	private OrthographicCamera camera;
	
	private Simulation sim;
	private UserInterface ui;
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
		ui = new UserInterface(sim, camera);
		renderer = new Renderer(batch, camera);
		
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(new UserInterfaceInputProcess(ui));
		multiplexer.addProcessor(new CameraInputProcessor(camera));
		multiplexer.addProcessor(new BodyInputProcessor(sim, camera));
		Gdx.input.setInputProcessor(multiplexer);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		
		camera.update();
		
		sim.update();
		renderer.render(sim, ui);
	}

	@Override
	public void dispose() {
		super.dispose();
	}

}