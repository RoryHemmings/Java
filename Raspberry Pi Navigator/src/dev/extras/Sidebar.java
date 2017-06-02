package dev.extras;

import java.awt.Color;
import java.awt.Graphics;

import dev.Main;

public class Sidebar {
	
	private int x, width, height;
	private int max_distance;
	private Color bgcolor;
	
	
	public Sidebar(int x, int max_distance, Color bgcolor, Main main) {
		this.max_distance = max_distance;
		this.x = x;
		this.bgcolor = bgcolor;
		
		width = main.getWidth() - x;
		height = main.getHeight();
	}
	
	public void render(Graphics g) {
		g.setColor(bgcolor);
		g.fillRect(x, 0, width, height);
		
		g.setColor(Color.black);
		g.drawRect(x, 0, width, height);
		
		g.drawString("Distance from start to end:", x+5, 250);
		g.drawString(max_distance+"", x+65, 280);
	}

	public int getMax_distance() {
		return max_distance;
	}

	public void setMax_distance(int max_distance) {
		this.max_distance = max_distance;
	}

	public Color getBgcolor() {
		return bgcolor;
	}

	public void setBgcolor(Color bgcolor) {
		this.bgcolor = bgcolor;
	}

	public int getX() {
		return x;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
