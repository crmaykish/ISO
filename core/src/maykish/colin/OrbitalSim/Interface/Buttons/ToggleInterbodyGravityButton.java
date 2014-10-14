package maykish.colin.OrbitalSim.Interface.Buttons;

import maykish.colin.OrbitalSim.Simulation;

public class ToggleInterbodyGravityButton extends AbstractButton {

	public ToggleInterbodyGravityButton(float x, float y) {
		super(x, y);
	}

	@Override
	public void effect(Simulation sim) {
		Simulation.INTERBODY_GRAVITY = !Simulation.INTERBODY_GRAVITY;
	}

	@Override
	public String getText() {
		return "Interbody Grav: " + (Simulation.INTERBODY_GRAVITY ? "On" : "Off");
	}


}
