package maykish.colin.OrbitalSim;

import com.badlogic.gdx.math.Vector2;

public class Physics {
	
	public static float G = 0.1f;
	
	// Calculate the velocity vector required to put a body into a circular orbit
	// around the given parent
	public static Vector2 calculateCircularOrbitVelocity(float x, float y, Body parent){
		Vector2 pos = new Vector2(x, y);
		Vector2 radius = pos.cpy().sub(parent.getCenter());
		float velocity_mag = (float) Math.sqrt((G * parent.getMass()) / radius.len());
		Vector2 unit = radius.cpy().nor();
		Vector2 totalVel = unit.scl(velocity_mag);
		Vector2 rotatedVelocity = new Vector2(totalVel.y, -totalVel.x);	// Initial velocity to put body into circular orbit
		return rotatedVelocity;
	}
	
}
