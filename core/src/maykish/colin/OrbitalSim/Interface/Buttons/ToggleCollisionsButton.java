package maykish.colin.OrbitalSim.Interface.Buttons;

import maykish.colin.OrbitalSim.Simulation;

public class ToggleCollisionsButton extends AbstractButton {

	@Override
	public void effect(Simulation sim) {
		Simulation.COLLIDE = !Simulation.COLLIDE;
	}

	@Override
	public float getX() {
		return 0;
	}

	@Override
	public float getY() {
		return 64;
	}

	@Override
	public String getText() {
		return "Collisions: " + (Simulation.COLLIDE ? "On" : "Off");
	}

}
