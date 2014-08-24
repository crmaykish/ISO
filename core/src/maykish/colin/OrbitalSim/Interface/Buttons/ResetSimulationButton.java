package maykish.colin.OrbitalSim.Interface.Buttons;

import maykish.colin.OrbitalSim.Simulation;

public class ResetSimulationButton extends AbstractButton{

	@Override
	public void effect(Simulation sim) {
		sim.reset();
	}

	@Override
	public float getX() {
		return 0;
	}

	@Override
	public float getY() {
		return 192;
	}

	@Override
	public String getText() {
		return "Reset Sim";
	}

}
