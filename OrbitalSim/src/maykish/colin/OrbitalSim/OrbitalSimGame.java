package maykish.colin.OrbitalSim;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class OrbitalSimGame implements ApplicationListener, InputProcessor {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private BitmapFont font;
	
	private Texture ball32;
	private Texture ball16;
	private Texture ball8;
//	private Texture background;
	
	private Body star;
	
	private ArrayList<Body> bodies;
	
	private boolean collide = false;
	
	@Override
	public void create() {		
		camera = new OrthographicCamera();
		camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		
		font = new BitmapFont();
		font.setScale(1, -1);
		
		ball32 = new Texture(Gdx.files.internal("ball32.png"));
		ball16 = new Texture(Gdx.files.internal("ball16.png"));
		ball8 = new Texture(Gdx.files.internal("ball8.png"));
//		background = new Texture(Gdx.files.internal("starfield.png"));
		
		bodies = new ArrayList<Body>();
		star = new Body(ball32, 10000, null, new Vector2(Gdx.graphics.getWidth() / 2 - 16, Gdx.graphics.getHeight() / 2 - 16), new Vector2(0,0.0f));
		bodies.add(star);
//		createSquare(30, new Vector2(100, 100), new Vector2(0.0f, 0.8f));
		
		CameraInputProcessor cameraProcessor = new CameraInputProcessor(camera);
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(cameraProcessor);
		multiplexer.addProcessor(this);
		Gdx.input.setInputProcessor(multiplexer);
	}
	
	private void createSquare(int length, Vector2 center, Vector2 velocity){
		for (int i=0; i < length; i++){
			for (int j=0; j< length; j++){
				bodies.add(new Body(ball16, 4000, star, new Vector2(center.x-(length*8) + 20*i, center.y-(length*8) + 20*j), velocity.cpy()));
			}
		}
	}

	private void createCircle(int radius, Vector2 center, Vector2 velocity){
		for (int i=0; i < 2*radius; i++){
			for (int j=0; j< 2*radius; j++){
				if (new Vector2(i,j).dst(radius, radius) < radius){
					bodies.add(new Body(ball8, 80, star, new Vector2(center.x-(radius*8) + 8*i, center.y-(radius*8) + 8*j), velocity.cpy()));
				}
			}
		}
	}
	
	@Override
	public void dispose() {
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		
		for (int i = 0; i < bodies.size(); i++){
			if (bodies.get(i).checkCollision(star) && bodies.get(i).getParent() != null){
				star.addMass(bodies.get(i).getMass());
				bodies.remove(i);
			}
			else {
				bodies.get(i).update();
			}
		}
		
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
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
//		batch.draw(background, 0, 0);
		
		for (Body b : bodies){
			b.draw(batch);
		}
		
		font.drawMultiLine(batch, "Solar Mass: " + star.getMass() + "\n" + "Bodies: " + bodies.size(), camera.position.x + 10 - Gdx.graphics.getWidth() / 2, camera.position.y + 10 - Gdx.graphics.getHeight() / 2);
		
		batch.end();
	}

	private void addBody(float f, float g){
		Vector2 pos = new Vector2(f - 8 , g - 8);
		Vector2 radius = pos.cpy().sub(star.getCenter()); //changed
		float velocity_mag = (float) Math.sqrt((0.1 * star.getMass())/radius.len());
		
		Vector2 unit = radius.cpy().nor();
		
		Vector2 totalVel = unit.scl(velocity_mag);
		
		Vector2 rotated = new Vector2(totalVel.y, -totalVel.x);
		
//		bodies.add(new Body(ball16, 1000, star, pos, rotated));
		
		createCircle(12, pos, rotated);
	}
	
	
	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		return false;
		
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {

		float x = screenX + camera.position.x - camera.viewportWidth / 2;
		float y = screenY + camera.position.y - camera.viewportHeight / 2;
		
		addBody(x, y);
		
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
