package maykish.colin.orbital.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

import maykish.colin.OrbitalSim.OrbitalSim;

public class DesktopLauncher {
	public static void main (String[] arg) {
		new LwjglApplication(new OrbitalSim(), "Orbital Sim", 1280, 800);
	}
}
