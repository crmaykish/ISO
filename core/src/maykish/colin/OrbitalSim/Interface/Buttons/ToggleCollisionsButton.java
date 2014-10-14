package maykish.colin.OrbitalSim.Interface.Buttons;

import maykish.colin.OrbitalSim.CollisionState;
import maykish.colin.OrbitalSim.Simulation;

public class ToggleCollisionsButton extends AbstractButton {

	public ToggleCollisionsButton(float x, float y) {
		super(x, y);
	}

	@Override
	public void effect(Simulation sim) {
		switch (Simulation.COLLISIONS) {
		case ABSORB:
			Simulation.COLLISIONS = CollisionState.NOCOLLIDE;
			break;
		case RICOCHET:
			Simulation.COLLISIONS = CollisionState.ABSORB;
			break;
		case NOCOLLIDE:
			Simulation.COLLISIONS = CollisionState.RICOCHET;
			break;
		}
	}

	@Override
	public String getText() {
		String text = "Collide: ";
		switch (Simulation.COLLISIONS) {
		case ABSORB:
			text += "Absorb";
			break;
		case RICOCHET:
			text += "Ricochet";
			break;
		case NOCOLLIDE:
			text += "Off";
			break;
		}
		return text;
	}

}
