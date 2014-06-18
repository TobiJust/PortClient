package info;

import java.io.Serializable;

public class VesselInfo implements Serializable{
	private static final long serialVersionUID = 7390383594644431841L;

	private Point3D coordinates;
	private double course, speed;
	private int mmsi;
	private String name;
	private String type;
	
	public VesselInfo(int mmsi, String name, float x, float y, float z, String type, double course, double speed){
		this.setMmsi(mmsi);
		this.setCoordinates(new Point3D(x, y, z));
		this.setName(name);
		this.setType(type);
		this.setCourse(course);
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public double getCourse() {
		return this.course;
	}
	
	public void setCourse(double course){
		this.course = course;
	}

	public int getMmsi() {
		return mmsi;
	}

	public void setMmsi(int mmsi) {
		this.mmsi = mmsi;
	}
}
