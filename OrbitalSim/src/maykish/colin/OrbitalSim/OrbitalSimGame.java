package maykish.colin.OrbitalSim;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class OrbitalSimGame implements ApplicationListener, InputProcessor {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private BitmapFont font;
	
	private Texture ball32;
	private Texture ball16;
	private Texture ball8;
	private Texture background;
	
	private Body star;
	private ArrayList<Body> bodies;
	
	// Flags
	private boolean collide = true;
	private boolean blackholeSun = true;
	
	@Override
	public void create() {		
		camera = new OrthographicCamera();
		camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		
		// Load textures and fonts
		ball32 = new Texture(Gdx.files.internal("ball32.png"));
		ball16 = new Texture(Gdx.files.internal("ball16.png"));
		ball8 = new Texture(Gdx.files.internal("ball8.png"));
		background = new Texture(Gdx.files.internal("starfield.png"));
		font = new BitmapFont();
		font.setScale(1, -1);
		
		// Initialize bodies and create a star at the center of the screen
		bodies = new ArrayList<Body>();
		star = new Body(ball32, 10000, null, new Vector2(Gdx.graphics.getWidth() / 2 - 16, Gdx.graphics.getHeight() / 2 - 16), new Vector2(0,0.0f));
		bodies.add(star);

		// Set up input processors
		CameraInputProcessor cameraProcessor = new CameraInputProcessor(camera);
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(cameraProcessor);
		multiplexer.addProcessor(this);
		Gdx.input.setInputProcessor(multiplexer);
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		
		// If a body collides with the star, remove it, otherwise, update it
		for (int i = 0; i < bodies.size(); i++){
			if (bodies.get(i).checkCollision(star) && bodies.get(i).getParent() != null){
				if (blackholeSun){	// Won't you come...
					star.addMass(bodies.get(i).getMass());
				}
				bodies.remove(i);	// And wash away the rain...
			}
			else {
				bodies.get(i).update();
			}
		}
		
		// Check if each body is colliding with any other body
		// The speed of this scales very poorly as the number of bodies grows
		// Need to make some changes to this for efficiency
		if (collide){
			for (int i = 0; i < bodies.size(); i++)
	        {
	            for (int j = i + 1; j < bodies.size(); j++)
	            {
	                if (bodies.get(i).checkCollision(bodies.get(j)))
	                {
	                    bodies.get(i).reactToCollision(bodies.get(j));
	                }
	            }
	        }
		}
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		batch.draw(background, getTopLeftCorner(0,0).x, getTopLeftCorner(0,0).y);
		
		for (Body b : bodies){
			b.draw(batch);
		}
		
		font.drawMultiLine(batch, "Solar Mass: " + star.getMass() + "\n" + "Bodies: " + bodies.size(), getTopLeftCorner(10,10).x, getTopLeftCorner(10, 10).y);
		
		batch.end();
	}

	// Place a circle of bodies into a circular orbit around the sun
	// Note: this will give all the bodies in the circle the same initial velocity
	// Rather than calculating a circular orbit for each of them, it will
	// use the calculated orbit of the center body
	private void addCircle(float x, float y, int radius){
		createCircle(radius, new Vector2(x, y), Physics.calculateCircularOrbitVelocity(x, y, star));
	}
	
	// Create a circle of bodies
	private void createCircle(int radius, Vector2 center, Vector2 velocity){
		for (int i=0; i < 2*radius; i++){
			for (int j=0; j< 2*radius; j++){
				if (new Vector2(i,j).dst(radius, radius) < radius){
					bodies.add(new Body(ball8, 80, star, new Vector2(center.x-(radius*8) + 8*i, center.y-(radius*8) + 8*j), velocity.cpy()));
				}
			}
		}
	}
	
	// return the position of the top left corner of the screen independent of resolution and camera position
	private Vector2 getTopLeftCorner(int offsetX, int offsetY){
		return new Vector2( offsetX + camera.position.x - Gdx.graphics.getWidth() / 2, offsetY + camera.position.y - Gdx.graphics.getHeight() / 2);
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {

		float x = screenX + camera.position.x - camera.viewportWidth / 2;
		float y = screenY + camera.position.y - camera.viewportHeight / 2;
		
		addCircle(x, y, 10);
		
		return false;
	}
	
	@Override
	public void dispose() {
	}
	@Override
	public void resize(int width, int height) {
	}
	@Override
	public void pause() {
	}
	@Override
	public void resume() {
	}
	@Override
	public boolean keyDown(int keycode) {
		return false;
	}
	@Override
	public boolean keyUp(int keycode) {
		return false;
	}
	@Override
	public boolean keyTyped(char character) {
		return false;
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}
	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
