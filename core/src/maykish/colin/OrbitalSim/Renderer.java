package maykish.colin.OrbitalSim;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import maykish.colin.OrbitalSim.Bodies.Body;
import maykish.colin.OrbitalSim.Bodies.Rocket;
import maykish.colin.OrbitalSim.Interface.UserInterface;
import maykish.colin.OrbitalSim.Interface.Buttons.Button;
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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class Renderer {
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private OrthographicCamera camera;
	
	private HashMap<Integer, TextureRegion> ballTextures;
	private TextureRegion background;
	private TextureRegion rocket;
	private TextureRegion button;
	private TextureRegion buttonClicked;
	private BitmapFont font;
	private DecimalFormat decFormat;
	
	public Vector2 start;
	public Vector2 end;
	
	
	public Renderer(SpriteBatch batch, OrthographicCamera camera){
		this.batch = batch;
		this.camera = camera;
		
		shapeRenderer = new ShapeRenderer();
		
		loadTexturesAndFonts();
		
		start = new Vector2();
		end = new Vector2();
		
	}
	
	public void render(Simulation sim, UserInterface ui){
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
		
		renderButtons(ui.getButtons());
		
		batch.end();
	}
	
	private void renderLaunch() {
		shapeRenderer.line(start, end);
		
	}

	private void renderButtons(List<Button> buttons) {
		
		for (Button b : buttons){
			Vector2 realPos = getTopLeftCorner().cpy().add(b.getX(), b.getY());
			batch.draw((b.isClicked() ? buttonClicked : button), realPos.x, realPos.y);
			font.draw(batch, b.getText(), realPos.x + 2, realPos.y + 26);
		}
		
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

	private void renderBackground(){
		Vector2 topLeftCorner = getTopLeftCorner();
		batch.draw(background, topLeftCorner.x, topLeftCorner.y, 0, 0, background.getRegionWidth(), background.getRegionHeight(), camera.zoom, camera.zoom, 0);
	}
	
	private void renderBodies(List<Body> bodies){
		for (Body b : bodies){
			int radius = b.getRadius();
			batch.draw(ballTextures.get(radius), b.getPosition().x - radius, b.getPosition().y - radius);
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
		
//		rocket = new TextureRegion(new Texture(Gdx.files.internal("rocket.png")));
		background = new TextureRegion(new Texture(Gdx.files.internal("starfield.png")));
		
		button = new TextureRegion(new Texture(Gdx.files.internal("button.png")));
		buttonClicked = new TextureRegion(new Texture(Gdx.files.internal("button-clicked.png")));
		
//		rocket.flip(false, true);
		background.flip(false, true);
		button.flip(false, true);
		buttonClicked.flip(false, true);
		
		font = new BitmapFont();
		font.setScale(1, -1);
		decFormat = new DecimalFormat("#.###");
	}
}

