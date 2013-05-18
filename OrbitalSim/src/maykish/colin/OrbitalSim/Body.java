package maykish.colin.OrbitalSim;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Body {
	private TextureRegion texture;
	private float mass;
	private Vector2 position;
	private Vector2 velocity;
	private boolean destroyed;
	private boolean fixed;
	
	private float elasticity = 0.6f; // elasticity of collisions - 1.0 is completely elastic, 0.0 is completely inelastic, theoretically anyway

	public Body(TextureRegion texture, float mass, Vector2 initPosition, Vector2 initVelocity, boolean fixed) {
		this.texture = texture;
		this.mass = mass;
		this.position = initPosition;
		this.velocity = initVelocity;
		this.fixed = fixed;
		this.destroyed = false;
	}

	public void addVelocity(Vector2 vel){
		if (!fixed){
			velocity = velocity.add(vel);
		}
	}
	
	public void updatePosition(){
		if (!fixed){
			position = position.add(velocity);
		}
	}
	
	public boolean checkCollision(Body otherBody){
		float distance = otherBody.getPosition().dst(getPosition());
		
		if (distance < getRadius() + otherBody.getRadius()){
			return true;
		}
		return false;
	}
	
	public void reactToCollision(Body otherBody){
		Vector2 difference = getPosition().cpy().sub(otherBody.getPosition());
		float d = difference.cpy().len();
		
		Vector2 mtd = difference.cpy().scl(((getRadius() + otherBody.getRadius()) - d) / d);
		
		float im1 = 1 / mass;
		float im2 = 1 / otherBody.getMass();
		
		if (!fixed){
			position = position.add(mtd.cpy().scl(im1 / (im1 + im2)));
		}
		otherBody.setPosition(otherBody.getPosition().sub(mtd.cpy().scl(im2 / (im1 + im2))));
		
		Vector2 v = velocity.cpy().sub(otherBody.getVelocity());
        Vector2 mtdN = mtd.cpy();
        mtdN = mtdN.nor();
        float vn = v.cpy().dot(mtdN);

        if (vn > 0.0f)
        {
            return;
        }

        float i = (-(1.0f + elasticity) * vn) / (im1 + im2);
        Vector2 impulse = mtdN.cpy().scl(i);

        if (!fixed){
        	velocity = velocity.add(impulse.cpy().scl(im1));
        }
        if (!otherBody.getFixed()){
        	otherBody.setVelocity(otherBody.getVelocity().sub(impulse.cpy().scl(im2)));
        }
	}
	
	public void draw(SpriteBatch sb){
		sb.draw(texture, position.x - texture.getRegionWidth() / 2, position.y - texture.getRegionHeight() / 2);
	}
	
	public Vector2 getVelocity(){
		return velocity.cpy();
	}
	public void setVelocity(Vector2 velocity){
		if (!fixed){
			this.velocity = velocity;
		}
	}
	public float getMass(){
		return mass;
	}
	public void addMass(float mass){
		this.mass += mass;
	}
	public Vector2 getPosition(){
		return position.cpy();
	}
	public void setPosition(Vector2 pos){
		if (!fixed){
			position = pos;
		}
	}
	public int getRadius(){
		return texture.getRegionWidth() / 2;
	}
	public boolean getFixed(){
		return fixed;
	}
	
	public boolean isDestroyed(){
		return destroyed;
	}
	
	public void setDestroyed(){
		destroyed = true;
	}
}
