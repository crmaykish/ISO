package maykish.colin.OrbitalSim;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import maykish.colin.OrbitalSim.Bodies.Body;

public class Simulation{
	
	// TODO: store this in the UI class too
	public boolean launch = false;
	
	
	// Toggles
	public static boolean COLLIDE = true;
	public static boolean INTERBODY_GRAVITY = true;
	public static boolean SHOW_TRAILS = true;
	
	// Physics Constants
	public static final float G = 0.0001f;
	public static final float SOLAR_MASS = 100000000f;
	
	// Physics Bodies
	public ArrayList<Body> bodies;
	public ArrayList<Body> stars;
	
	// Trail Frame Gap
	private final int FRAME_GAP = 5;
	private int currentFrame = 0;
	
	// Tool Settings
	public static int BRUSH_SIZE = 2;
	public static int BODY_RADIUS = 16;
	public static int BODY_MASS = 100000;
	
	public Simulation(){
		setUpBodies();
	}
	
	private void setUpBodies(){
		bodies = new ArrayList<Body>();
		stars = new ArrayList<Body>();
		
		Body star = new Body(SOLAR_MASS, 64, new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2), new Vector2(0,0.0f));
		star.fixed = true;
		bodies.add(star);
		stars.add(star);
		
	}
	
	public void addBody(Vector2 pos, Vector2 vel){
		bodies.add(new Body(BODY_MASS, BODY_RADIUS, pos, vel));
	}
	
	public Vector2 getLaunchVelocity(Vector2 start, Vector2 end){
		return (end.cpy().sub(start)).scl(0.02f);
	}
	
	public void update(){
		updateGravity();
		updateCollisions();
		updateTrails();
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
		if (COLLIDE){
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
	}
	
	// Place a circle of bodies into a circular orbit around the sun
	// Note: this will give all the bodies in the circle the same initial velocity
	// Rather than calculating a circular orbit for each of them, it will
	// use the calculated orbit of the center body
	public void addCircle(float x, float y){
		createCircle(new Vector2(x, y), calculateCircularOrbitVelocity(x, y, stars.get(0)));
	}

	private void createCircle(Vector2 center, Vector2 velocity){
		for (int i = -BRUSH_SIZE / 2; i < BRUSH_SIZE / 2; i++){
			for (int j = -BRUSH_SIZE / 2; j < BRUSH_SIZE / 2; j++){
				if (new Vector2(i, j).dst(0, 0) < BRUSH_SIZE / 2){
					Vector2 pos = new Vector2(center.x + 2*i*BODY_RADIUS, center.y + 2*j*BODY_RADIUS);
					bodies.add(new Body(BODY_MASS, BODY_RADIUS, pos, velocity.cpy()));
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
	
}
