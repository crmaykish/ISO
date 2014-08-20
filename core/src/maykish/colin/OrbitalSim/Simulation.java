package maykish.colin.OrbitalSim;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import maykish.colin.OrbitalSim.Bodies.Body;
import maykish.colin.OrbitalSim.Utils.ColinsQueue;

public class Simulation{

	// Physics Bodies
	public ArrayList<Body> bodies;
	public ArrayList<Body> stars;
	
	public ColinsQueue<Vector2> trails;
	
	// Settings
	public boolean collide = true;
	public boolean interbodyGravity = true;
	public boolean showTrails = true;
	public int maxTrails = 100000;
	public float G = 0.00001f;
	public float solarMass = 100000000f;
	
	// Tool Settings
	public int brushSize = 6;
	public int bodyRadius = 8;
	public int bodyMass = 100000;
	
	public Simulation(){
		setUpBodies();
	}
	
	private void setUpBodies(){
		bodies = new ArrayList<Body>();
		stars = new ArrayList<Body>();
		
		Body star = new Body(solarMass, 64, new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2), new Vector2(0,0.0f));
		star.fixed = true;
		bodies.add(star);
		stars.add(star);
		
		trails = new ColinsQueue<Vector2>(maxTrails);
	}
	
	public void update(){
		updateGravity();
		updateCollisions();
		updateTrails();
	}
	
	int frameGap = 5;
	int currentFrame = 0;
	
	private void updateTrails() {
		if (showTrails) {
			if (currentFrame == frameGap) {
				for (Body b : bodies) {
					trails.add(b.getPosition().cpy());
					currentFrame = 0;
				}
			}
			currentFrame++;
		}
	}
	
	private void updateGravity() {
		// Should we compare all the bodies with eachother or just the stars?
		List<Body> compareList = interbodyGravity ? bodies : stars;

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
	}
	
	// Place a circle of bodies into a circular orbit around the sun
	// Note: this will give all the bodies in the circle the same initial velocity
	// Rather than calculating a circular orbit for each of them, it will
	// use the calculated orbit of the center body
	public void addCircle(float x, float y){
		createCircle(new Vector2(x, y), calculateCircularOrbitVelocity(x, y, stars.get(0)));
	}

	private void createCircle(Vector2 center, Vector2 velocity){
		for (int i = -brushSize / 2; i < brushSize / 2; i++){
			for (int j = -brushSize / 2; j < brushSize / 2; j++){
				if (new Vector2(i, j).dst(0, 0) < brushSize / 2){
					Vector2 pos = new Vector2(center.x + 2*i*bodyRadius, center.y + 2*j*bodyRadius);
					bodies.add(new Body(bodyMass, bodyRadius, pos, velocity.cpy()));
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
