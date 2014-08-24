package maykish.colin.OrbitalSim.Interface.Buttons;

public abstract class AbstractButton implements Clickable {
	private boolean clicked;

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

}
