package maykish.colin.OrbitalSim;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import maykish.colin.OrbitalSim.Bodies.Body;
import maykish.colin.OrbitalSim.Bodies.Rocket;
import maykish.colin.OrbitalSim.Utils.MaxSizeList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Renderer {
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private OrthographicCamera camera;
	
	private HashMap<Integer, TextureRegion> ballTextures;
	private TextureRegion background;
	private TextureRegion rocket;
	private TextureRegion button;
	private BitmapFont font;
	private DecimalFormat decFormat;
	
	public Rectangle buttonRect;
	public Rectangle sizeRect;
	
	
	public Vector2 start;
	public Vector2 end;
	
	public Renderer(SpriteBatch batch, OrthographicCamera camera){
		this.batch = batch;
		this.camera = camera;
		
		shapeRenderer = new ShapeRenderer();
		
		loadTexturesAndFonts();
		
		buttonRect = new Rectangle(getTopLeftCorner().x, Gdx.graphics.getHeight() - 64, 64, 64);
		sizeRect = new Rectangle(getTopLeftCorner().x + 64, Gdx.graphics.getHeight() - 64, 64, 64);
		
		start = new Vector2();
		end = new Vector2();
	}
	
	public void render(Simulation sim){
		
		batch.setProjectionMatrix(camera.combined);
		shapeRenderer.setProjectionMatrix(camera.combined);
		
		batch.begin();
		renderBackground();
		batch.end();
		
		shapeRenderer.setColor(Color.WHITE);
		shapeRenderer.begin(ShapeType.Line);
		renderTrails(sim.bodies);
		renderLaunch();
		shapeRenderer.end();
		
		batch.begin();
		renderBodies(sim.bodies);
		renderDebug(sim);
		renderButtons();
		batch.end();
	}
	
	private void renderLaunch() {
		shapeRenderer.line(start, end);
		
	}

	private void renderButtons() {
		
		buttonRect.x = getTopLeftCorner().x;
		buttonRect.y = getTopLeftCorner().y + Gdx.graphics.getHeight()-64;
		sizeRect.x = getTopLeftCorner().x + 64;
		sizeRect.y = getTopLeftCorner().y + Gdx.graphics.getHeight()-64;
		batch.draw(button, buttonRect.x, buttonRect.y);
		batch.draw(button, sizeRect.x, sizeRect.y);
		
	}

	private void renderTrails(List<Body> bodies) {
		for (Body b: bodies){
			int trailsSize = b.trail.size();
			if (trailsSize >= 2) {
				for (int i = 0; i < trailsSize - 1; i++) {
					shapeRenderer.line(b.trail.get(i), b.trail.get(i + 1));
				}
			}
		}
	}

	private void renderDebug(Simulation sim){
		int fps = Gdx.graphics.getFramesPerSecond();
		font.draw(batch, "FPS: " + fps, getTopLeftCorner().x + 2, getTopLeftCorner().y + 2);
		font.draw(batch, "Bodies: " + sim.bodies.size(), getTopLeftCorner().x + 2 , getTopLeftCorner().y + 14);
		font.draw(batch, "Launch: " + sim.launch, getTopLeftCorner().x + 2 , getTopLeftCorner().y + 26);
		font.draw(batch, "Planet Size: " + Simulation.BODY_RADIUS*2, getTopLeftCorner().x + 2 , getTopLeftCorner().y + 38);
	}
	
	private void renderBackground(){
		Vector2 topLeftCorner = getTopLeftCorner();
		batch.draw(background, topLeftCorner.x, topLeftCorner.y, 0, 0, background.getRegionWidth(), background.getRegionHeight(), camera.zoom, camera.zoom, 0);
	}
	
	private void renderBodies(List<Body> bodies){
		for (Body b : bodies){
			int radius = b.getRadius();
			TextureRegion tex = b instanceof Rocket ? rocket : ballTextures.get(radius);
			batch.draw(tex, b.getPosition().x - radius, b.getPosition().y - radius);
		}
	}
	
	public Vector2 getTopLeftCorner(){
		Vector3 corner = new Vector3(0,0,0);
		camera.unproject(corner);
		return new Vector2(corner.x, corner.y);
	}
	
	private void loadTexturesAndFonts(){
		ballTextures = new HashMap<Integer, TextureRegion>();
		ballTextures.put(4, new TextureRegion(new Texture(Gdx.files.internal("ball8.png"))));
		ballTextures.put(8, new TextureRegion(new Texture(Gdx.files.internal("pPlanet16.png"))));
		ballTextures.put(16, new TextureRegion(new Texture(Gdx.files.internal("pPLanet32.png"))));
		ballTextures.put(32, new TextureRegion(new Texture(Gdx.files.internal("pPlanet64.png"))));
		ballTextures.put(64, new TextureRegion(new Texture(Gdx.files.internal("pPlanet128.png"))));
		ballTextures.put(128, new TextureRegion(new Texture(Gdx.files.internal("ball256.png"))));
		
		rocket = new TextureRegion(new Texture(Gdx.files.internal("rocket.png")));
		background = new TextureRegion(new Texture(Gdx.files.internal("starfield.png")));
		button = new TextureRegion(new Texture(Gdx.files.internal("button.png")));
		
		rocket.flip(false, true);
		background.flip(false, true);
		button.flip(false, true);
		
		font = new BitmapFont();
		font.setScale(1, -1);
		decFormat = new DecimalFormat("#.###");
	}
}

