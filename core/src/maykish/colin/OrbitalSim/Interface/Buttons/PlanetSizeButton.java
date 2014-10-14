package maykish.colin.OrbitalSim.Interface.Buttons;

import maykish.colin.OrbitalSim.Simulation;

public class PlanetSizeButton extends AbstractButton{

	public PlanetSizeButton(float x, float y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void effect(Simulation sim) {
		if (Simulation.BODY_RADIUS < 32){
			Simulation.BODY_RADIUS *= 2;
		} else {
			Simulation.BODY_RADIUS = 8;
		}
		
	}

	@Override
	public String getText() {
		return "Planet Size: " + Simulation.BODY_RADIUS;
	}

}
