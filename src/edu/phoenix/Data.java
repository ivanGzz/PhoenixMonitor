package edu.phoenix;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

/**
* @author Nelson Gonzalez
* @version 1.0
*/
public class Data implements Drawer {

	/**
	* Vector of vector of positions
	*/
	private Vector<Vector<Position>> positions = new Vector<>();
	/**
	* Vector of observer positions
	*/
	private Vector<Position> observers = new Vector<>();
	/**
	* Current frame
	*/
	private int pointer = 0;
	/**
	* Reference to Loader object
	*/
	private Loader loader;
	/**
	* Reference to PCanvas object
	*/
	private PCanvas canvas;
	/**
	* Array of colors
	*/
	private Color[] colors = new Color[5];
	
	/**
	* Data constructor
	* @param canvas PCanvas object
	* @param loader Loader object
	* @see PCanvas
	* @see Loader
	*/
	public Data(PCanvas canvas, Loader loader) {
		this.canvas = canvas;
		this.canvas.registerDrawer(this);
		this.loader = loader;
		colors[0] = Color.BLACK;
		colors[1] = Color.BLUE;
		colors[2] = Color.CYAN;
		colors[3] = Color.YELLOW;
		colors[4] = Color.MAGENTA;
	}
	
	/**
	* {@inheritDoc}
	*/
	@Override
	public void draw(Graphics g) {
		if (this.positions.size() == 0) return;
		for (Position position : this.positions.get(this.pointer)) {
			if (position.inSight) {
				this.canvas.drawCircle(position.x, position.y, 1.0, this.colors[position.id % 5], this.colors[position.id % 5], g);
			} else {
				this.canvas.drawCircle(position.x, position.y, 1.0, Color.RED, Color.RED, g);
			}
		}
		Position observer = this.observers.get(this.pointer);
		this.canvas.drawCircle(observer.x, observer.y, 1.0, Color.BLUE, Color.BLUE, g);
		this.canvas.drawVisualSensor(observer.x, observer.y, observer.d, 60, g);
		this.canvas.drawText(Integer.toString(this.pointer + 1), 1, 10, g);
	}
	
	/**
	* Callback for forward button
	*/
	public void onForward() {
		if (this.positions.size() == 0) {
			if (this.loader.loadNextCycle()) {
				this.positions.add(this.loader.getNewPositionList());
				this.observers.add(this.loader.getNewObserverPosition());
				this.canvas.repaint();
			}
		} else if (this.pointer < this.positions.size() - 1) {
			this.pointer++;
			this.canvas.repaint();
		} else {
			if (this.loader.loadNextCycle()) {
				this.positions.add(this.loader.getNewPositionList());
				this.observers.add(this.loader.getNewObserverPosition());
				this.pointer++;
				this.canvas.repaint();
			}
		}
	}
	
	/**
	* Callback for backwards button
	*/
	public void onBackward() {
		if (this.positions.size() == 0) return;
		if (this.pointer > 0) {
			this.pointer--;
			this.canvas.repaint();
		}
	}
	
}
