package maykish.colin.OrbitalSim.Screens;

import maykish.colin.OrbitalSim.OrbitalSim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MainMenuScreen extends AbstractScreen{

	TextureRegion logo;
	
	public MainMenuScreen(OrbitalSim game) {
		super(game);
	}

	@Override
	public void show() {
		super.show();
		
		logo = new TextureRegion(new Texture(Gdx.files.local("logo.png")));
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		
		if (Gdx.input.justTouched()){
			game.setScreen(new SimScreen(game));
		}
		
		batch.begin();
		
		batch.draw(logo, 100f, 100f);
		
		batch.end();
	}

	@Override
	public void dispose() {
		super.dispose();
	}
	
}
