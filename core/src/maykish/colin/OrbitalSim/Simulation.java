package maykish.colin.OrbitalSim;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;

import maykish.colin.OrbitalSim.Bodies.Body;

public class Simulation{
	
	// TODO: store this in the UI class too
	public boolean launch = false;
	
	// Game State
	public static boolean RUNNING = true;
	
	// Toggles
	public static CollisionState COLLISIONS = CollisionState.RICOCHET;
	public static boolean INTERBODY_GRAVITY = false;
	public static boolean SHOW_TRAILS = true;
	
	// Physics Constants
	public static final float G = 0.00001f;
	
	// Physics Bodies
	public ArrayList<Body> bodies;
	public ArrayList<Body> stars;
	
	// Trail Frame Gap
	private final int FRAME_GAP = 5;
	private int currentFrame = 0;
	
	// Tool Settings
	public static int BRUSH_SIZE = 0;
	public static int BRUSH_SIZES[] = { 2, 6, 11, 16, 21, 26, 31 };
	public static int BODY_RADIUS = 8;
//	public static int BODY_MASS = 10000;
	
	public Simulation(){
		bodies = new ArrayList<Body>();
		stars = new ArrayList<Body>();
		
		reset();
	}
	
	private int bodyMass(int radius){
		return (int) (Math.pow(radius, 3) * 1000);
	}
	
	public void addBody(Vector2 pos, Vector2 vel){
		bodies.add(new Body(bodyMass(BODY_RADIUS), BODY_RADIUS, pos, vel));
	}
	
	public Vector2 getLaunchVelocity(Vector2 start, Vector2 end){
		return (end.cpy().sub(start)).scl(0.02f);
	}
	
	public void update(){
		
		if(Gdx.input.isKeyPressed(Keys.ESCAPE)){
			Gdx.app.exit();
		}
		
		if (RUNNING){
			updateGravity();
			updateCollisions();
			updateTrails();
		}
	}
	
	private void updateTrails() {
		if (SHOW_TRAILS) {
			if (currentFrame == FRAME_GAP) {
				for (Body b : bodies) {
					b.trail.add(b.getPosition().cpy());
					currentFrame = 0;
				}
			}
			currentFrame++;
		}
	}
	
	private void updateGravity() {
		// Should we compare all the bodies with eachother or just the stars?
		List<Body> compareList = INTERBODY_GRAVITY ? bodies : stars;

		for (int i = 0; i < bodies.size(); i++) {
			Body bodyI = bodies.get(i);
			for (int j = 0; j < compareList.size(); j++) {
				Body bodyJ = bodies.get(j);
				if (!bodyI.equals(bodyJ)) {
					incrementBodyVelocity(bodies.get(i), compareList.get(j));
				}
			}
			bodies.get(i).incrementPositionByVelocity();
		}
	}
	
	private void incrementBodyVelocity(Body body, Body other){
		Vector2 radius = other.getPosition().cpy().sub(body.getPosition());
		float accel = (G * other.getMass()) / radius.len2();
		Vector2 net = radius.nor().scl(accel);
		body.incrementVelocity(net);
	}
	
	private void updateCollisions(){
		if (COLLISIONS != CollisionState.NOCOLLIDE){
			for (int i = 0; i < bodies.size(); i++)
	        {
	            for (int j = i + 1; j < bodies.size(); j++)
	            {
	                if (bodies.get(i).checkCollision(bodies.get(j)))
	                {
	                	bodies.get(i).reactToCollision(bodies.get(j));
	                	
	                	if (COLLISIONS == CollisionState.ABSORB){
		                	Body biggerBody;
		                	Body smallerBody; 
		                	int deleteIndex;
		                	
		                	if(bodies.get(i).getMass() >= bodies.get(j).getMass()){
		                		biggerBody = bodies.get(i);
		                		smallerBody = bodies.get(j);
		                		deleteIndex = j;
		                	} else{
		                		biggerBody = bodies.get(j);
		                		smallerBody = bodies.get(i);
		                		deleteIndex = i;
		                	}
		                	
		                	biggerBody.addMass(smallerBody.getMass());
		                	bodies.remove(deleteIndex);
	                	}
	                }
	            }
	        }
		}
	}
	
	// Place a circle of bodies into a circular orbit around the sun
	// Note: this will give all the bodies in the circle the same initial velocity
	// Rather than calculating a circular orbit for each of them, it will
	// use the calculated orbit of the center body
	public void addCircle(float x, float y){
		createCircle(new Vector2(x, y), calculateCircularOrbitVelocity(x, y, stars.get(0)));
	}

	private void createCircle(Vector2 center, Vector2 velocity){
		for (int i = -BRUSH_SIZES[BRUSH_SIZE] / 2; i < BRUSH_SIZES[BRUSH_SIZE] / 2; i++){
			for (int j = -BRUSH_SIZES[BRUSH_SIZE] / 2; j < BRUSH_SIZES[BRUSH_SIZE] / 2; j++){
				if (new Vector2(i, j).dst(0, 0) < BRUSH_SIZES[BRUSH_SIZE] / 2){
					Vector2 pos = new Vector2(center.x + 2*i*BODY_RADIUS, center.y + 2*j*BODY_RADIUS);
					bodies.add(new Body(bodyMass(BODY_RADIUS), BODY_RADIUS, pos, velocity.cpy()));
				}
			}
		}
	}
	
	private Vector2 calculateCircularOrbitVelocity(float x, float y, Body target){
		Vector2 pos = new Vector2(x, y);
		Vector2 radius = pos.cpy().sub(target.getPosition());
		float velocity_mag = (float) Math.sqrt((G * target.getMass()) / radius.len());
		Vector2 unit = radius.cpy().nor();
		Vector2 totalVel = unit.scl(velocity_mag);
		Vector2 rotatedVelocity = new Vector2(totalVel.y, -totalVel.x);	// Initial velocity to put body into circular orbit
		return rotatedVelocity;
	}	
	
	// tools and effects to be called by the UI
	public void toggleTrails(){
		SHOW_TRAILS = !SHOW_TRAILS;
		for (Body b : bodies){
			b.trail.clear();
		}
	}
	
	public void reset(){
		bodies.clear();
		stars.clear();
		
		Body star = new Body(bodyMass(64), 64, new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2), new Vector2(0,0.0f));
		star.fixed = true;
		bodies.add(star);
		stars.add(star);
	}
	
}
