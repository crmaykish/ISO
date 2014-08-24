package maykish.colin.OrbitalSim.Interface.Buttons;

import com.badlogic.gdx.math.Rectangle;

import maykish.colin.OrbitalSim.Simulation;

public interface Clickable {
	public void effect(Simulation sim);
	public float getX();
	public float getY();
	public float getWidth();
	public float getHeight();
	public Rectangle getRectangle();
	public boolean isClicked();
	public void setClicked(boolean clicked);
}
