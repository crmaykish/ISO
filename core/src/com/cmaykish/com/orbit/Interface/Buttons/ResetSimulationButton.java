package com.cmaykish.com.orbit.Interface.Buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.cmaykish.com.orbit.Simulation;

public class ResetSimulationButton extends AbstractButton{

	OrthographicCamera camera;
	
	public ResetSimulationButton(OrthographicCamera camera, float x, float y) {
		super(x, y);
		this.camera = camera;
	}
	
	@Override
	public void effect(Simulation sim) {
		sim.reset();
		camera.position.x = Gdx.graphics.getWidth() / 2;
		camera.position.y = Gdx.graphics.getHeight() / 2;
	}

	@Override
	public String getText() {
		return "Reset Sim";
	}

}
