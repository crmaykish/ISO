package com.cmaykish.com.orbit.Interface.Buttons;

import com.cmaykish.com.orbit.Simulation;

public class PlanetSizeButton extends AbstractButton{

	public PlanetSizeButton(float x, float y) {
		super(x, y);
	}

	@Override
	public void effect(Simulation sim) {
		if (Simulation.BODY_RADIUS < 32){
			Simulation.BODY_RADIUS *= 2;
		} else {
			Simulation.BODY_RADIUS = 8;
		}
	}

	@Override
	public String getText() {
		return "Planet Size: " + Simulation.BODY_RADIUS;
	}

}
