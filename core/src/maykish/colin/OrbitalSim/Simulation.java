package maykish.colin.OrbitalSim;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import maykish.colin.OrbitalSim.Bodies.Body;

public class Simulation{

	// Physics Bodies
	public ArrayList<Body> bodies;
	public ArrayList<Body> stars;
	
	// Settings
	public boolean collide = true;
	public boolean interbodyGravity = false;
	public int G = 5;
	public final float[] Gs = {0.0000001f, 0.000001f, 0.00001f, 0.0001f, 0.001f, 0.01f, 0.1f, 1.0f};
	public float solarMass = 1000000f;
	
	// Tool Settings
	public int brushSize = 10;
	public int bodyRadius = 8;
	public int bodyMass = 1000;
	
	public Simulation(){
		setUpBodies();
	}
	
	private void setUpBodies(){
		bodies = new ArrayList<Body>();
		stars = new ArrayList<Body>();
		
		Body star = new Body(solarMass, 128, new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2), new Vector2(0,0.0f));
		star.fixed = true;
		bodies.add(star);
		stars.add(star);
	}
	
	public void update(){
		updateGravity();
		updateCollisions();
	}
	
	private void updateGravity(){
		// TODO : consolidate these two functions
		if (interbodyGravity){
			// Inter-body gravitation
			for (int i = 0; i < bodies.size(); i++){
				Body b = bodies.get(i);
				for (int j = 0; j < bodies.size(); j++){
					if (i != j){
						Body other = bodies.get(j);
						Vector2 radius = other.position.cpy().sub(b.position);
						float accel = (Gs[G] * other.mass) / radius.len2();
						Vector2 net = radius.nor().scl(accel);
						b.velocity.add(net);
					}
				}
				b.position.add(b.velocity);
			}
		}
		else {
			// Bodies only attracted to stars
			for (int i = 0; i < bodies.size(); i++){
				Body b = bodies.get(i);
				for (int j = 0; j < stars.size(); j++){
					Body star = stars.get(j);
					if (!b.equals(star)){
						Vector2 radius = star.position.cpy().sub(b.position);
						float accel = (Gs[G] * star.mass) / radius.len2();
						Vector2 net = radius.nor().scl(accel);
						b.velocity.add(net);
					}
				}
				b.position.add(b.velocity);
			}
		}
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
		Vector2 radius = pos.cpy().sub(target.position);
		float velocity_mag = (float) Math.sqrt((Gs[G] * target.mass) / radius.len());
		Vector2 unit = radius.cpy().nor();
		Vector2 totalVel = unit.scl(velocity_mag);
		Vector2 rotatedVelocity = new Vector2(totalVel.y, -totalVel.x);	// Initial velocity to put body into circular orbit
		return rotatedVelocity;
	}	
	
}
