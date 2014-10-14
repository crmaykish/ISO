package maykish.colin.OrbitalSim.Interface.Buttons;

import maykish.colin.OrbitalSim.Simulation;

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
