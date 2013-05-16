package maykish.colin.OrbitalSim;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Simulation implements InputProcessor{

	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private InputMultiplexer multiplexer;
	
	private BitmapFont font;
	
	private Texture ball32;
	private Texture ball16;
	private Texture ball8;
	private Texture background;
	
	private Body star;
	private ArrayList<Body> bodies;
	
	// Settings
	private boolean collide = true;
//	private boolean interbodyGravity = true;
	private float G = 0.001f;
	private float solarMass = 1000000;
	private boolean fixedSun = true;
	
	private boolean running = false;
	
	public Simulation(OrthographicCamera camera, SpriteBatch batch){
		this.camera = camera;
		this.batch = batch;
	}
	
	public void initialize(){
		// Load textures and fonts
		ball32 = new Texture(Gdx.files.internal("ball32.png"));
		ball16 = new Texture(Gdx.files.internal("ball16.png"));
		ball8 = new Texture(Gdx.files.internal("ball8.png"));
		background = new Texture(Gdx.files.internal("starfield.png"));
		font = new BitmapFont();
		font.setScale(1, -1);
		
		// Initialize bodies and create a star at the center of the screen
		bodies = new ArrayList<Body>();
		star = new Body(ball32, solarMass, new Vector2(Gdx.graphics.getWidth() / 2 - 16, Gdx.graphics.getHeight() / 2 - 16), new Vector2(0,0.0f), fixedSun);
		bodies.add(star);
		
		// Set up input processors
		CameraInputProcessor cameraProcessor = new CameraInputProcessor(camera);
		multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(cameraProcessor);
		multiplexer.addProcessor(this);
		Gdx.input.setInputProcessor(multiplexer);
	}
	
	public void render(){
		for (int i = 0; i < bodies.size(); i++){
			Body b = bodies.get(i);
			
			if (b.isDestroyed()){
				bodies.remove(i);
			}
			else {
				for (int j = 0; j < bodies.size(); j++){
					if (i != j){
						Body other = bodies.get(j);
						
						Vector2 radius = other.getPosition().sub(b.getPosition());
						float accel = (G * other.getMass()) / radius.len2();
						Vector2 net = radius.nor().scl(accel);
						
						b.addVelocity(net);
					}
				}
				b.updatePosition();
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
		
		font.drawMultiLine(batch, "Solar Mass: " + star.getMass() + "\n"
								+ "System Mass: " + getTotalSystemMass() + "\n"
								+ "Bodies: " + bodies.size() + "\n"
								+ "G: " + G + "\n"
								, getTopLeftCorner(10,10).x, getTopLeftCorner(10, 10).y);
		
		batch.end();
	}
	
	private float getTotalSystemMass(){
		float total = 0.0f;
		for (Body b : bodies){
			total += b.getMass();
		}
		return total;
	}
	
	// Place a circle of bodies into a circular orbit around the sun
	// Note: this will give all the bodies in the circle the same initial velocity
	// Rather than calculating a circular orbit for each of them, it will
	// use the calculated orbit of the center body
	private void addCircle(float x, float y, int radius){
		createCircle(radius, new Vector2(x, y), calculateCircularOrbitVelocity(x, y, bodies.get(0)));
//		createCircle(radius, new Vector2(x, y), new Vector2(0,0));
	}
	
	// Create a circle of bodies
	private void createCircle(int radius, Vector2 center, Vector2 velocity){
		for (int i=0; i < 2*radius; i++){
			for (int j=0; j< 2*radius; j++){
				if (new Vector2(i,j).dst(radius, radius) < radius){
					bodies.add(new Body(ball16, 1000, new Vector2(center.x-(radius*16) + 16*i, center.y-(radius*16) + 16*j), velocity.cpy(), false));
				}
			}
		}
	}
	
	// return the position of the top left corner of the screen independent of resolution and camera position
	private Vector2 getTopLeftCorner(int offsetX, int offsetY){
		Vector2 corner = new Vector2( offsetX + camera.position.x - Gdx.graphics.getWidth() / 2, offsetY + camera.position.y - Gdx.graphics.getHeight() / 2);
		return corner;
	}
	
	public Vector2 calculateCircularOrbitVelocity(float x, float y, Body target){
		Vector2 pos = new Vector2(x, y);
		Vector2 radius = pos.cpy().sub(target.getPosition());
		float velocity_mag = (float) Math.sqrt((G * target.getMass()) / radius.len());
		Vector2 unit = radius.cpy().nor();
		Vector2 totalVel = unit.scl(velocity_mag);
		Vector2 rotatedVelocity = new Vector2(totalVel.y, -totalVel.x);	// Initial velocity to put body into circular orbit
		return rotatedVelocity;
	}
	
	public void toggleCollide(){
		collide = collide ? false : true;
	}
	
	public boolean getCollide(){
		return collide;
	}
	
	public boolean isRunning(){
		return running;
	}
	
	public void setRunning(){
		running = true;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {

		float x = screenX + camera.position.x - camera.viewportWidth / 2;
		float y = screenY + camera.position.y - camera.viewportHeight / 2;
		
		addCircle(x, y, 1);
		
		return false;
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
