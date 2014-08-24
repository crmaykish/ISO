package maykish.colin.OrbitalSim.Interface;

import java.util.ArrayList;
import java.util.List;

import maykish.colin.OrbitalSim.Simulation;
import maykish.colin.OrbitalSim.Interface.Buttons.AbstractButton;
import maykish.colin.OrbitalSim.Interface.Buttons.Clickable;
import maykish.colin.OrbitalSim.Interface.Buttons.ResetSimulationButton;
import maykish.colin.OrbitalSim.Interface.Buttons.ToggleCollisionsButton;
import maykish.colin.OrbitalSim.Interface.Buttons.ToggleInterbodyGravityButton;
import maykish.colin.OrbitalSim.Interface.Buttons.ToggleTrailsButton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class UserInterface {

	Simulation sim;

	int height, width;

	List<AbstractButton> buttons;
	List<Status> statuses;

	public UserInterface(Simulation sim) {
		this.sim = sim;

		height = Gdx.graphics.getHeight();
		width = Gdx.graphics.getWidth();

		buttons = new ArrayList<AbstractButton>();

		buttons.add(new ToggleTrailsButton());
		buttons.add(new ToggleCollisionsButton());
		buttons.add(new ToggleInterbodyGravityButton());
		buttons.add(new ResetSimulationButton());

	}

	public List<AbstractButton> getButtons() {
		return buttons;
	}

	// if there's button being clicked, handled it and return true,
	// otherwise, false
	public boolean buttonClickUp(float x, float y){
		for (Clickable b : buttons){
			if (b.getRectangle().contains(x, y)){
				b.effect(sim);
				b.setClicked(false);
				return true;
			}
		}
		return false;
	}
	
	public boolean buttonClickDown(float x, float y){
		for (Clickable b : buttons){
			if (b.getRectangle().contains(x, y)){
				b.setClicked(true);
				return true;
			}
		}
		return false;
	}
	
}
