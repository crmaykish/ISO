package com.cmaykish.com.orbit;

import com.badlogic.gdx.Game;
import com.cmaykish.com.orbit.Screens.MainMenuScreen;

public class OrbitalSim extends Game{

	@Override
	public void create() {
		setScreen(new MainMenuScreen(this));
	}

}
