package maykish.colin.OrbitalSim.Interface.Buttons;

import maykish.colin.OrbitalSim.Simulation;

public class ToggleInterbodyGravityButton extends AbstractButton {

	@Override
	public void effect(Simulation sim) {
		Simulation.INTERBODY_GRAVITY = !Simulation.INTERBODY_GRAVITY;
	}

	@Override
	public String getText() {
		return "Interbody Grav: " + (Simulation.INTERBODY_GRAVITY ? "On" : "Off");
	}

	@Override
	public float getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getY() {
		// TODO Auto-generated method stub
		return 64;
	}

}
