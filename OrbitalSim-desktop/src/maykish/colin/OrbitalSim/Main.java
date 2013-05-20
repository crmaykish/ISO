package maykish.colin.OrbitalSim;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "OrbitalSim";
		cfg.useGL20 = false;
//		cfg.fullscreen = true;
		cfg.width = 1280;
		cfg.height = 720;
		
		new LwjglApplication(new OrbitalSim(), cfg);
	}
}
