package maykish.colin.OrbitalSim;

import maykish.colin.OrbitalSim.Screens.MainMenuScreen;

import com.badlogic.gdx.Game;

public class OrbitalSim extends Game{

	@Override
	public void create() {
		setScreen(new MainMenuScreen(this));
	}
	
}
