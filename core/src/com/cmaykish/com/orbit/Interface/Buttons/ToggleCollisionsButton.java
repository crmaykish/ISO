package com.cmaykish.com.orbit.Interface.Buttons;

import com.cmaykish.com.orbit.CollisionState;
import com.cmaykish.com.orbit.Simulation;

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
