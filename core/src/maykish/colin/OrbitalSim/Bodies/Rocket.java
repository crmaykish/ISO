package maykish.colin.OrbitalSim.Bodies;

import com.badlogic.gdx.math.Vector2;

public class Rocket extends Body{

//	private TextureRegion flames;
	
	private Vector2 acceleration;
	private float rotation;
	private float scale = 0.5f;
	
	private boolean showFlames = false;
	
	public Rocket(Vector2 initPosition, Vector2 initVelocity){
		super(100, 16, initPosition, initVelocity);
		
//		flames = new TextureRegion(new Texture(Gdx.files.internal("flame.png")));
//		flames.flip(false, true);
		
//		acceleration = new Vector2(0, 0);
//		rotation = 0.0f;
	}
	
//	@Override
//	public void updatePosition(){
//		super.updatePosition();
//		
//		if (Gdx.input.isKeyPressed(Keys.A)){
//			rotation -= 1.5f;
//		}
//		if (Gdx.input.isKeyPressed(Keys.D)){
//			rotation += 1.5f;
//		}
//		
//		if (Gdx.input.isKeyPressed(Keys.W)){
//			showFlames = true;
//			acceleration.x = 0.001f * (float) Math.sin(Math.toRadians(rotation));
//			acceleration.y = 0.001f * -(float) Math.cos(Math.toRadians(rotation));
//			velocity.add(acceleration);
//		}
//		else {
//			showFlames = false;
//		}
//	}
	
}
