package info;

import java.io.Serializable;

public class VesselInfo implements Serializable{
	private static final long serialVersionUID = 7390383594644431841L;
	public enum Type{PASSENGER}

	private Point3D coordinates;
	private String name;
	private Type type;
	
	public VesselInfo(String name, double x, double y, double z, Type type){
		this.setCoordinates(new Point3D(x, y, z));
		this.setName(name);
		this.setType(type);
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

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
}
