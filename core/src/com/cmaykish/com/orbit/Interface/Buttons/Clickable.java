package com.cmaykish.com.orbit.Interface.Buttons;

import com.badlogic.gdx.math.Rectangle;
import com.cmaykish.com.orbit.Simulation;

public interface Clickable {
	void effect(Simulation sim);
//	float getX();
//	float getY();
	float getWidth();
	float getHeight();
	Rectangle getRectangle();
	boolean isClicked();
	void setClicked(boolean clicked);
}
