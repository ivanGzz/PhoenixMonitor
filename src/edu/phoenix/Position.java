package edu.phoenix;

public class Position {

	public double x = 0.0;
	public double y = 0.0;
	public double d = 0.0;
	public boolean inSight = false;
	public int id = -1;
	
	public Position() {
		
	}
	
	public Position(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Position(double x, double y, double d) {
		this.x = x;
		this.y = y;
		this.d = d;
	}
	
}
