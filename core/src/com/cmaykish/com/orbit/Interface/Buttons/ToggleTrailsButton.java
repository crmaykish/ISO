package com.cmaykish.com.orbit.Interface.Buttons;

import com.cmaykish.com.orbit.Simulation;

public class ToggleTrailsButton extends AbstractButton {
	
	public ToggleTrailsButton(float x, float y) {
		super(x, y);
	}

	@Override
	public void effect(Simulation sim) {
		sim.toggleTrails();
	}

	@Override
	public String getText() {
		return "Trails: " + (Simulation.SHOW_TRAILS ? "On" : "Off");
	}

}
