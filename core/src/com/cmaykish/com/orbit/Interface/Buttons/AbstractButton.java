package com.cmaykish.com.orbit.Interface.Buttons;

import com.badlogic.gdx.math.Rectangle;

public abstract class AbstractButton implements Clickable {
	private boolean clicked;

	private float x, y;
	
	public AbstractButton(float x, float y){
		this.setX(x);
		this.setY(y);
	}
	
	public abstract String getText();
	
	@Override
	public boolean isClicked() {
		return clicked;
	}

	@Override
	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}

	@Override
	public float getWidth() {
		return 128;
	}

	@Override
	public float getHeight() {
		return 64;
	}
	
	@Override
	public Rectangle getRectangle() {
		return new Rectangle(getX(), getY(), getWidth(), getHeight());
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

}
