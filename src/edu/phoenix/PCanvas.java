package edu.phoenix;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Vector;

public class PCanvas extends Component {

	private int width = 595;
	private int height = 410;
	private double factor = 5.0;
	private Vector<Drawer> drawers = new Vector<>();
	
	private static final long serialVersionUID = 1L;

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(this.width, this.height);
	}
	
	private int toXCoord(double x) {
		return (int)(this.factor * x + 297);
	}
	
	private int toYCoord(double y) {
		return (int)(this.factor * y + 205);
	}
	
	private int toFieldLength(double length) {
		return (int)(this.factor * length);
	}
	
//	private int getDeltaX(double direction, double length) {
//		return (int)(factor * length * Math.cos(Math.toRadians(direction)));
//	}
//	
//	private int getDeltaY(double direction, double length) {
//		return (int)(factor * length * Math.sin(Math.sin(Math.toRadians(direction))));
//	}
	
	public void drawVisualSensor(double x0, double y0, double direction, int aperture, Graphics g) {
		int x = this.toXCoord(x0);
		int y = this.toYCoord(y0);
		int side = this.toFieldLength(60.0);
		int startAngle = (int)(direction - aperture / 2);
		g.setColor(Color.YELLOW);
		g.drawArc(x - side, y - side, 2 * side, 2 * side, -1 * startAngle, -1 * aperture);
		int xs = x + (int)(side * Math.cos(Math.toRadians(startAngle)));
		int ys = y + (int)(side * Math.sin(Math.toRadians(startAngle)));
		g.drawLine(x, y, xs, ys);
		int xf = x + (int)(side * Math.cos(Math.toRadians(startAngle + aperture)));
		int yf = y + (int)(side * Math.sin(Math.toRadians(startAngle + aperture)));
		g.drawLine(x, y, xf, yf);
	}
	
	public void drawRectangle(double x0, double y0, double x1, double y1, Color lineColor, Color fillColor, Graphics g) {
		int x = this.toXCoord(x0);
		int y = this.toYCoord(y0);
		int width = this.toXCoord(x1) - x;
		int height = this.toYCoord(y1) - y;
		g.setColor(lineColor);
		g.drawRect(x, y, width, height);
		g.setColor(fillColor);
		g.fillRect(x + 1, y + 1, width - 2, height - 2);
	}
	
	public void drawLine(double x0, double y0, double x1, double y1, Color color, Graphics g) {
		int xo = this.toXCoord(x0);
		int yo = this.toYCoord(y0);
		int xf = this.toXCoord(x1);
		int yf = this.toYCoord(y1);
		g.setColor(color);
		g.drawLine(xo, yo, xf, yf);
	}
	
	public void drawCircle(double x, double y, double r, Color lineColor, Color fillColor, Graphics g) {
		int x0 = this.toXCoord(x) - this.toFieldLength(r);
		int y0 = this.toYCoord(y) - this.toFieldLength(r);
		int w = 2 * this.toFieldLength(r);
		int h = 2 * this.toFieldLength(r);
		g.setColor(lineColor);
		g.drawOval(x0, y0, w, h);
		g.setColor(fillColor);
		g.fillOval(x0, y0, w - 2, h - 2);
	}
	
	public void drawText(String text, int x, int y, Graphics g) {
		g.setColor(Color.BLACK);
		g.drawString(text, x, y);
	}
	
	private void drawField(Graphics g) {
		this.drawRectangle(-59.5, -41.0, 59.5, 41.0, new Color(31, 160, 31), new Color(31, 160, 31), g);
		this.drawRectangle(-52.5, -34.0, 52.5, 34.0, Color.WHITE, new Color(31, 160, 31), g);
		this.drawLine(0.0, -34.0, 0.0, 34.0, Color.WHITE, g);
		this.drawCircle(0.0, 0.0, 9.0, Color.WHITE, new Color(31, 160, 31), g);
	}
	
	@Override
	public void paint(Graphics g) {
		this.drawField(g);
		for (Drawer drawer : this.drawers) {
			drawer.draw(g);
		}
	}
	
	public void registerDrawer(Drawer drawer) {
		this.drawers.add(drawer);
	}
	
}
