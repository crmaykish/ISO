package maykish.colin.OrbitalSim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Rocket{

	private TextureRegion texture;
	private TextureRegion flames;
	private TextureRegion trajectory;
	private Vector2 position;
	private Vector2 velocity;
	private Vector2 acceleration;
	private float mass;
	private float rotation;
	private float scale = 0.25f;
	
	private boolean showFlames = false;
	
	private Body parent;
	
	public Rocket(Body parent){
		texture = new TextureRegion(new Texture(Gdx.files.internal("rocket.png")));
		texture.flip(false, true);
		flames = new TextureRegion(new Texture(Gdx.files.internal("flame.png")));
		flames.flip(false, true);
		trajectory = new TextureRegion(new Texture(Gdx.files.internal("trajectory.png")));
		
		position = new Vector2(300, 300);
		velocity = new Vector2(0, 0);
		acceleration = new Vector2(0, 0);
		mass = 100;
		rotation = 0.0f;
		
		this.parent = parent;
	}
	
	private Vector2 getPosAfter(int steps){
		
		Vector2 runningVelocity = velocity.cpy();
		Vector2 runningPos = position.cpy();
		
		for (int i=0; i<steps; i++){
			Vector2 radius = parent.getPosition().sub(runningPos);
			float accel = (0.001f * parent.getMass()) / radius.len2();
			Vector2 net = radius.nor().scl(accel);
			runningVelocity.add(net);
			runningPos.add(runningVelocity);
		}
		
		return runningPos;
	}
	
	public void update(){
		
		Vector2 radius = parent.getPosition().sub(position);
		float accel = (0.001f * parent.getMass()) / radius.len2();
		Vector2 net = radius.nor().scl(accel);
		velocity = velocity.add(net);
		
		position.add(velocity);
		
		if (Gdx.input.isKeyPressed(Keys.A)){
			rotation -= 1.5f;
		}
		if (Gdx.input.isKeyPressed(Keys.D)){
			rotation += 1.5f;
		}
		
		if (Gdx.input.isKeyPressed(Keys.W)){
			showFlames = true;
			acceleration.x = 0.001f * (float) Math.sin(Math.toRadians(rotation));
			acceleration.y = 0.001f * -(float) Math.cos(Math.toRadians(rotation));
			velocity.add(acceleration);
		}
		else {
			showFlames = false;
		}
	}
	
	public void draw(SpriteBatch batch){
		
		for (int i = 0; i < 100; i++){
			Vector2 pos = getPosAfter(i*50);
			batch.draw(trajectory, pos.x-4, pos.y-4);
		}
		
		batch.draw(texture, position.x - 64, position.y - 128, 64, 128, 128, 256, scale, scale, rotation);
		if (showFlames){
			batch.draw(flames, position.x - 64, position.y - 128, 64, 128, 128, 512, scale, scale, rotation);
		}
		
	}
	
	public float getVelocity(){
		return velocity.len();
	}
	
	public float getAltitude(){
		return position.cpy().dst(parent.getPosition());
	}
	
}
