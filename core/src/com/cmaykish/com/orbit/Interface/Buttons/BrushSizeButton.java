package com.cmaykish.com.orbit.Interface.Buttons;

import com.cmaykish.com.orbit.Simulation;

public class BrushSizeButton extends AbstractButton {

	public BrushSizeButton(float x, float y) {
		super(x, y);
	}

	@Override
	public void effect(Simulation sim) {
		if (Simulation.BRUSH_SIZE < 6) {
			Simulation.BRUSH_SIZE++;
		} else {
			Simulation.BRUSH_SIZE = 0;
		}
	}

	@Override
	public String getText() {
		return "Brush Size: " + (Simulation.BRUSH_SIZES[Simulation.BRUSH_SIZE] - 1);
	}

}
