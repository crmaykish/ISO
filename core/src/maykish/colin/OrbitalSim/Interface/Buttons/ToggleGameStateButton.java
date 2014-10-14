package maykish.colin.OrbitalSim.Interface.Buttons;

import maykish.colin.OrbitalSim.Simulation;

public class ToggleGameStateButton extends AbstractButton {

	public ToggleGameStateButton(float x, float y) {
		super(x, y);
	}

	@Override
	public void effect(Simulation sim) {
		Simulation.RUNNING = !Simulation.RUNNING;
	}

	@Override
	public String getText() {
		return Simulation.RUNNING ? "Pause" : "Resume";
	}

}
