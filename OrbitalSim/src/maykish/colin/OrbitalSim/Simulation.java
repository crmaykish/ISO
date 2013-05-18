package maykish.colin.OrbitalSim;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Simulation implements InputProcessor{

	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private InputMultiplexer multiplexer;
	
	private BitmapFont font;
	
	private DecimalFormat decFormat;
	
	private int zoom = 100;

	private TextureRegion ball256;
	private TextureRegion ball128;
	private TextureRegion ball64;
	private TextureRegion ball32;
	private TextureRegion ball16;
	private TextureRegion ball8;
	private TextureRegion background;
	
	private Rocket rocket;
	
	private Body star;
	private ArrayList<Body> bodies;
	private ArrayList<Body> stars;
	
	// Settings
	private boolean collide = true;
	private boolean interbodyGravity = false;
	private int G = 4;
	private float[] Gs = {0.0000001f, 0.000001f, 0.00001f, 0.0001f, 0.001f, 0.01f, 0.1f, 1.0f};
	private float solarMass = 100000f;
	private boolean fixedSun = true;
	private int brushSize = 1;
	private boolean smallBodySize = true;
	
	private boolean running = false;
	
	public Simulation(OrthographicCamera camera, SpriteBatch batch){
		this.camera = camera;
		this.batch = batch;
	}
	
	public void initialize(){
		// Load textures and fonts
		ball256 = new TextureRegion(new Texture(Gdx.files.internal("ball256.png")));
		ball128 = new TextureRegion(new Texture(Gdx.files.internal("ball128.png")));
		ball64 = new TextureRegion(new Texture(Gdx.files.internal("ball64.png")));
		ball32 = new TextureRegion(new Texture(Gdx.files.internal("ball32.png")));
		ball16 = new TextureRegion(new Texture(Gdx.files.internal("ball16.png")));
		ball8 = new TextureRegion(new Texture(Gdx.files.internal("ball8.png")));
		ball256.flip(false, true);
		ball128.flip(false, true);
		ball64.flip(false, true);
		ball32.flip(false, true);
		ball16.flip(false, true);
		ball8.flip(false, true);
		
		background = new TextureRegion(new Texture(Gdx.files.internal("starfield.png")));
		background.flip(false, true);
		
		font = new BitmapFont();
		font.setScale(1, -1);
		
		decFormat = new DecimalFormat("#.###");
		
		// Initialize bodies and create a star at the center of the screen
		bodies = new ArrayList<Body>();
		stars = new ArrayList<Body>();
		star = new Body(ball256, solarMass, new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2), new Vector2(0,0.0f), fixedSun);
		bodies.add(star);
		stars.add(star);
		
		rocket = new Rocket(star);
		
		// Set up input processors
		CameraInputProcessor cameraProcessor = new CameraInputProcessor(camera);
		multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(cameraProcessor);
		multiplexer.addProcessor(this);
		Gdx.input.setInputProcessor(multiplexer);
	}
	
	public void render(){
		
		// TODO : consolidate these two functions
		if (interbodyGravity){
			// Inter-body gravitation
			for (int i = 0; i < bodies.size(); i++){
				Body b = bodies.get(i);
				for (int j = 0; j < bodies.size(); j++){
					if (i != j){
						Body other = bodies.get(j);
						Vector2 radius = other.getPosition().sub(b.getPosition());
						float accel = (Gs[G] * other.getMass()) / radius.len2();
						Vector2 net = radius.nor().scl(accel);
						b.addVelocity(net);
					}
				}
				b.updatePosition();
			}
		}
		else {
			// Bodies only attracted to stars
			for (int i = 0; i < bodies.size(); i++){
				Body b = bodies.get(i);
				for (int j = 0; j < stars.size(); j++){
					Body star = stars.get(j);
					Vector2 radius = star.getPosition().sub(b.getPosition());
					float accel = (Gs[G] * star.getMass()) / radius.len2();
					Vector2 net = radius.nor().scl(accel);
					b.addVelocity(net);
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
		
		
		rocket.update();
		
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		batch.draw(background, getTopLeftCorner(0,0).x, getTopLeftCorner(0,0).y);
		
		for (Body b : bodies){
			b.draw(batch);
		}
		
		rocket.draw(batch);
		
		font.drawMultiLine(batch, "Solar Mass: " + star.getMass() + "\n"
								+ "System Mass: " + getTotalSystemMass() + "\n"
								+ "Bodies: " + bodies.size() + "\n"
								+ "G: " + Gs[G] + "\n"
								+ "Velocity: " + decFormat.format(rocket.getVelocity()) +"\n"
								+ "Altitude: " + decFormat.format(rocket.getAltitude()) + "\n"
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
	private void addCircle(float x, float y){
		createCircle(new Vector2(x, y), calculateCircularOrbitVelocity(x, y, stars.get(0)));
	}
	
	// Create a circle of bodies
	private void createCircle(Vector2 center, Vector2 velocity){
		for (int i=0; i < 2*brushSize; i++){
			for (int j=0; j< 2*brushSize; j++){
				if (new Vector2(i,j).dst(brushSize, brushSize) < brushSize){
					TextureRegion tex = smallBodySize ? ball8 : ball16;
					int size = smallBodySize ? 8 : 16;
					bodies.add(new Body(tex, 1000, new Vector2(center.x-(brushSize*size) + size*i, center.y-(brushSize*size) + size*j), velocity.cpy(), false));
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
		float velocity_mag = (float) Math.sqrt((Gs[G] * target.getMass()) / radius.len());
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
	
	public void toggleFixedSun(){
		fixedSun = fixedSun ? false : true;
	}
	
	public boolean getFixedSun(){
		return fixedSun;
	}
	
	public void toggleSolarMass(){
		if (solarMass == 100000000f){
			solarMass = 1000f;
		}
		else {
			solarMass *= 10f;
		}
	}
	
	public int getSolarMass(){
		return (int) solarMass;
	}
	
	public void toggleG(){
		if (G == 7){
			G = 0;
		}
		else {
			G++;
		}
	}
	
	public float getG(){
		return Gs[G];
	}
	
	public void toggleInterbodyGravity(){
		interbodyGravity = interbodyGravity ? false : true;
	}
	
	public boolean getInterbodyGravity(){
		return interbodyGravity;
	}
	
	public void toggleBrushSize(){
		if (brushSize == 25){
			brushSize = 1;
		}
		else {
			brushSize += 2;
		}
	}
	
	public int getBrushSize(){
		return brushSize;
	}
	
	public void toggleSmallBody(){
		smallBodySize = smallBodySize ? false : true;
	}
	
	public boolean getSmallBody(){
		return smallBodySize;
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
		
		addCircle(x, y);
		
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
		zoom -= amount;
		Gdx.app.log("zoom", zoom + "");
		
		return false;
	}
}
