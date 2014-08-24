package maykish.colin.OrbitalSim.Interface.Buttons;

import maykish.colin.OrbitalSim.Simulation;

public interface Button {
	public static final int WIDTH = 128;
	public static final int HEIGHT = 64;

	public void effect(Simulation sim);
	public float getX();
	public float getY();
	public String getText();
	public boolean isClicked();
	public void setClicked(boolean clicked);
}
