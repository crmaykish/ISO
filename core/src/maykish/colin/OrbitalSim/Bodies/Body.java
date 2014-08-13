package maykish.colin.OrbitalSim.Bodies;

import com.badlogic.gdx.math.Vector2;

public class Body{
	public float mass;
	public final int radius;
	public Vector2 position;
	public Vector2 velocity;
	public boolean fixed;
	
	protected float elasticity = 0.6f; // elasticity of collisions - 1.0 is completely elastic, 0.0 is completely inelastic, theoretically anyway

	public Body(float mass, int radius, Vector2 initPosition, Vector2 initVelocity) {
		this.mass = mass;
		this.radius = radius;
		this.position = initPosition;
		this.velocity = initVelocity;
		this.fixed = false;
	}
	
	public boolean checkCollision(Body otherBody){
		float distance = otherBody.position.dst(position);
		
		if (distance < radius + otherBody.radius){
			return true;
		}
		return false;
	}
	
	public void reactToCollision(Body otherBody) {
		Vector2 difference = position.cpy().sub(otherBody.position);
		float d = difference.cpy().len();

		Vector2 mtd = difference.cpy().scl(
				(radius + otherBody.radius - d) / d);

		float im1 = 1 / mass;
		float im2 = 1 / otherBody.mass;

		if (!fixed)
			position = position.cpy().add(mtd.cpy().scl(im1 / (im1 + im2)));
		if (!otherBody.fixed)
			otherBody.position = otherBody.position.cpy().sub(mtd.cpy().scl(im2 / (im1 + im2)));

		Vector2 v = velocity.cpy().sub(otherBody.velocity);
		Vector2 mtdN = mtd.cpy();
		mtdN = mtdN.nor();
		float vn = v.cpy().dot(mtdN);

		if (vn > 0.0f) {
			return;
		}

		float i = (-(1.0f + elasticity) * vn) / (im1 + im2);
		Vector2 impulse = mtdN.cpy().scl(i);

		if (!fixed)
			velocity = velocity.cpy().add(impulse.cpy().scl(im1));
		if (!otherBody.fixed)
			otherBody.velocity = otherBody.velocity.cpy().sub(impulse.cpy().scl(im2));
	}
	
	@Override
	public boolean equals(Object obj) {
		Body other = (Body) obj;
		if (position.x == other.position.x &&
			position.y == other.position.y &&
			velocity.x == other.velocity.x &&
			velocity.y == other.velocity.y &&
			mass == other.mass &&
			radius == other.radius){
			return true;
		}
		return false;
	}

	
}
