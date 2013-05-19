package maykish.colin.OrbitalSim;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import maykish.colin.OrbitalSim.Bodies.Body;
import maykish.colin.OrbitalSim.Bodies.Rocket;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Renderer {
	private SpriteBatch batch;
	private OrthographicCamera camera;
	
	private HashMap<Integer, TextureRegion> ballTextures;
	private TextureRegion background;
	private TextureRegion rocket;
	private BitmapFont font;
	private DecimalFormat decFormat;
	
	public Renderer(SpriteBatch batch, OrthographicCamera camera){
		this.batch = batch;
		this.camera = camera;
		
		loadTexturesAndFonts();
	}
	
	public void render(Simulation sim){
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		renderBackground();
		renderBodies(sim.bodies);
		
		batch.end();
	}
	
	private void renderBackground(){
		Vector2 topLeftCorner = getTopLeftCorner();
		batch.draw(background, topLeftCorner.x, topLeftCorner.y);
	}
	
	private void renderBodies(List<Body> bodies){
		for (Body b : bodies){
			int radius = b.radius;
			TextureRegion tex = b instanceof Rocket ? rocket : ballTextures.get(radius);
			batch.draw(tex, b.position.x - radius, b.position.y - radius);
		}
	}
	
	private Vector2 getTopLeftCorner(){
		return new Vector2(camera.position.x - Gdx.graphics.getWidth() / 2, camera.position.y - Gdx.graphics.getHeight() / 2);
	}
	
	private void loadTexturesAndFonts(){
		ballTextures = new HashMap<Integer, TextureRegion>();
		ballTextures.put(4, new TextureRegion(new Texture(Gdx.files.internal("ball8.png"))));
		ballTextures.put(8, new TextureRegion(new Texture(Gdx.files.internal("ball16.png"))));
		ballTextures.put(16, new TextureRegion(new Texture(Gdx.files.internal("ball32.png"))));
		ballTextures.put(32, new TextureRegion(new Texture(Gdx.files.internal("ball64.png"))));
		ballTextures.put(64, new TextureRegion(new Texture(Gdx.files.internal("ball128.png"))));
		ballTextures.put(128, new TextureRegion(new Texture(Gdx.files.internal("ball256.png"))));
		
		rocket = new TextureRegion(new Texture(Gdx.files.internal("rocket.png")));
		background = new TextureRegion(new Texture(Gdx.files.internal("starfield.png")));
		
		rocket.flip(false, true);
		background.flip(false, true);
		
		font = new BitmapFont();
		font.setScale(1, -1);
		decFormat = new DecimalFormat("#.###");
	}
}

