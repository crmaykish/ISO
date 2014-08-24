package maykish.colin.OrbitalSim.Interface.Buttons;

import maykish.colin.OrbitalSim.Simulation;

public class ToggleTrailsButton extends AbstractButton {
	
	@Override
	public void effect(Simulation sim) {
		sim.toggleTrails();
	}

	@Override
	public float getX() {
		return 0;
	}

	@Override
	public float getY() {
		return 0;
	}

	@Override
	public String getText() {
		return "Trails: " + (Simulation.SHOW_TRAILS ? "On" : "Off");
	}

}
