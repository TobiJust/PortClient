package info;

import java.io.Serializable;

public class Point3D implements Serializable{
	private static final long serialVersionUID = -1666335444287045879L;
	
	private float x, y, z;
	
	public Point3D(float x, float y, float z){
		this.setX(x);
		this.setY(y);
		this.setZ(z);
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

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}
	
}