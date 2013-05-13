package maykish.colin.OrbitalSim;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Body {
	private Texture texture;
	private float mass;
	private Vector2 position;
	private Vector2 velocity;
	private Body parent;

	private float G = 0.1f;

	public Body(Texture texture, float mass, Body parent, Vector2 initPosition, Vector2 initVelocity) {
		this.texture = texture;
		this.mass = mass;
		this.parent = parent;
		this.position = initPosition;
		this.velocity = initVelocity;
		
	}

	public void update() {
		if (parent != null){
			
			// get acceleration due to parent
			Vector2 radius = parent.getCenter().sub(position);	//changed
			float accel = (G * parent.getMass()) / radius.len2();
			Vector2 netAccel = radius.nor().scl(accel);
			
			// if body has a grandparent, get its acceleration
			if (parent.getParent() != null){
				Vector2 gradius = parent.getParent().getCenter().sub(position); // changed
				float gaccel = (G * parent.getParent().getMass()) / radius.len2();
				Vector2 gnetAccel = radius.nor().scl(accel);
				
				velocity = velocity.add(gnetAccel);
			}
			
			velocity = velocity.add(netAccel);
		}
		
//		position = position.add(velocity);
		setCenter(getCenter().add(velocity)); //changed
	}
	
	public boolean checkCollision(Body otherBody){
		float distance = otherBody.getCenter().dst(getCenter());
		
		if (distance < getRadius() + otherBody.getRadius()){
			return true;
		}
		return false;
	}
	
	public void reactToCollision(Body otherBody){
		
		Vector2 difference = getCenter().cpy().sub(otherBody.getCenter());
		float d = difference.cpy().len();
		
		Vector2 mtd = difference.cpy().scl(((getRadius() + otherBody.getRadius()) - d) / d);
		
		float im1 = 1 / mass;
		float im2 = 1 / otherBody.getMass();
		
//		position = position.add(mtd.cpy().scl(im1 / (im1 + im2)));
//		otherBody.setPosition(otherBody.getPosition().sub(mtd.cpy().scl(im2 / (im1 + im2))));
		
		Vector2 v = velocity.cpy().sub(otherBody.getVelocity());
        Vector2 mtdN = mtd.cpy();
        mtdN = mtdN.nor();
        float vn = v.cpy().dot(mtdN);

        if (vn > 0.0f)
        {
            return;
        }

        float i = (-(1.0f + 1.0f) * vn) / (im1 + im2);
        Vector2 impulse = mtdN.cpy().scl(i);

        if (parent != null){
        	velocity = velocity.add(impulse.cpy().scl(im1));
        }
        if (otherBody.getParent() != null){
        	otherBody.setVelocity(otherBody.getVelocity().sub(impulse.cpy().scl(im2)));
        }
		
	}
	
	public void draw(SpriteBatch sb){
		sb.draw(texture, position.x, position.y);
	}
	
//	public Vector2 getPosition(){
//		return new Vector2(position.x+getRadius(), position.y+getRadius());
//	}
	
	public Vector2 getVelocity(){
		return velocity.cpy();
	}
	
	public float getMass(){
		return mass;
	}
	
	public Vector2 getCenter(){
		int size = texture.getWidth();
		return new Vector2(position.x + size / 2, position.y + size / 2);
	}
	
	public int getRadius(){
		return texture.getWidth() / 2;
	}
	
//	public void setPosition(Vector2 position){
//		this.position = position;
//	}
	
	public void setCenter(Vector2 center){
		this.position.x = center.x - getRadius();
		this.position.y = center.y - getRadius();
	}
	
	public void setVelocity(Vector2 velocity){
		this.velocity = velocity;
	}
	
	public Body getParent(){
		return parent;
	}
	
	public void addMass(float mass){
		this.mass += mass;
	}
}
