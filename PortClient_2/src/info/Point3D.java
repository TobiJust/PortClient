package info;

import java.io.Serializable;

public class Point3D implements Serializable{
	private static final long serialVersionUID = -1666335444287045879L;
	
	private double x, y, z;
	
	public Point3D(double x, double y, double z){
		this.setX(x);
		this.setY(y);
		this.setZ(z);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}
	
}