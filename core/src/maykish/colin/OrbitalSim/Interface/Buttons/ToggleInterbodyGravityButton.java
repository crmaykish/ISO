package maykish.colin.OrbitalSim.Interface.Buttons;

import maykish.colin.OrbitalSim.Simulation;

public class ToggleInterbodyGravityButton extends AbstractButton {

	@Override
	public void effect(Simulation sim) {
		Simulation.INTERBODY_GRAVITY = !Simulation.INTERBODY_GRAVITY;
	}

	@Override
	public float getX() {
		return 0;
	}

	@Override
	public float getY() {
		return 128;
	}

	@Override
	public String getText() {
		return "Interbody Grav: " + (Simulation.INTERBODY_GRAVITY ? "On" : "Off");
	}

}
