package info;

import java.io.Serializable;

public class PlayerInfo implements Serializable{
	private static final long serialVersionUID = 7390383594644431841L;
	public enum State{FLYING, WALKING}

	private Point3D coordinates;
	private String name;
	private State state;
	
	public PlayerInfo(String name, double x, double y, double z, State state){
		this.setCoordinates(new Point3D(x, y, z));
		this.setName(name);
		this.setState(state);
	}
	
	public Point3D getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Point3D coordinates) {
		this.coordinates = coordinates;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

}
