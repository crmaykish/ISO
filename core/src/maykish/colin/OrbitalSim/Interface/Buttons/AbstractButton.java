package maykish.colin.OrbitalSim.Interface.Buttons;

public abstract class AbstractButton implements Button{

	private boolean clicked;
	
	@Override
	public boolean isClicked() {
		return clicked;
	}

	@Override
	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}

}
