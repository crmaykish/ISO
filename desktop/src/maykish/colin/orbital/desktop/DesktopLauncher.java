package maykish.colin.orbital.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import maykish.colin.OrbitalSim.OrbitalSim;

public class DesktopLauncher {
	public static void main (String[] arg) {
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Orbit Sim";
		config.width = 1920;
		config.height = 1080;
		config.fullscreen = true;
		
		new LwjglApplication(new OrbitalSim(), config);
	}
}
