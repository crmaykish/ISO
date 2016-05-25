package com.cmaykish.com.orbit.Interface;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.cmaykish.com.orbit.Interface.Buttons.*;
import com.cmaykish.com.orbit.Simulation;

public class UserInterface {

	Simulation sim;

	int height, width;

	List<AbstractButton> buttons;
	List<Status> statuses;

	public UserInterface(Simulation sim, OrthographicCamera camera) {
		this.sim = sim;

		height = Gdx.graphics.getHeight();
		width = Gdx.graphics.getWidth();

		buttons = new ArrayList<>();

		buttons.add(new ToggleTrailsButton(0, 0));
		buttons.add(new ToggleCollisionsButton(0, 64));
		buttons.add(new ToggleInterbodyGravityButton(0, 128));
		buttons.add(new BrushSizeButton(0, 192));
		buttons.add(new PlanetSizeButton(0, 256));
		buttons.add(new ResetSimulationButton(camera, 0, 320));
		buttons.add(new ToggleGameStateButton(0, 384));
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
