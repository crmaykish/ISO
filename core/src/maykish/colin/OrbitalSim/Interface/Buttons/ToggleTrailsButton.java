package maykish.colin.OrbitalSim.Interface.Buttons;

import maykish.colin.OrbitalSim.Simulation;

public class ToggleTrailsButton extends AbstractButton {
	
	@Override
	public void effect(Simulation sim) {
		sim.toggleTrails();
	}

	@Override
	public String getText() {
		return "Trails: " + (Simulation.SHOW_TRAILS ? "On" : "Off");
	}

	@Override
	public float getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getY() {
		// TODO Auto-generated method stub
		return 128;
	}

}
