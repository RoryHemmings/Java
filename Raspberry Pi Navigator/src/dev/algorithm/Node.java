package dev.algorithm;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import dev.Main;

public class Node implements Comparable<Node> {

	private int x, y, x_pos, y_pos;
	private int distance = -1;
	private boolean in_path = false;
	private int classification = 0;
	private int size;

	private static final int threshold = 50;

	private int[] bounds;

	private Main main;

	public static final int node = 0, wall = 1, start = 2, end = 3;

	private ArrayList<Node> neighbors = new ArrayList<Node>();
	private Node previous_node = null;

	public Node(int x_pos, int y_pos, int x, int y, int size, int classification, int distance, Main main) {
		this.x = x;
		this.y = y;
		this.x_pos = x_pos;
		this.y_pos = y_pos;
		this.size = size;
		this.classification = classification;
		this.distance = distance;
		this.main = main;

		bounds = new int[] { x, x + size, y, y + size };
	}

	public void render(Graphics g) {
		// && !(classification == start) && !(classification == wall) &&
		// !(classification == end)
		if (in_path)
			g.setColor(Color.ORANGE);
		else if (!in_path) {
			g.setColor(Color.gray);
		}

		if (classification == start)
			g.setColor(Color.red);
		else if (classification == end)
			g.setColor(Color.blue);
		else if (classification == wall)
			g.setColor(Color.black);

		if (isHovering() == 1)
			g.setColor(new Color((g.getColor().getRed() - threshold) > 0 ? g.getColor().getRed() - threshold : 0,
					(g.getColor().getGreen() - threshold) > 0 ? g.getColor().getGreen() - threshold : 0,
					(g.getColor().getBlue() - threshold) > 0 ? g.getColor().getBlue() - threshold : 0));
		g.fillRoundRect(x, y, size, size, 25, 25);
	}

	public void init_neighbors() {
//		if (!(x_pos - 1 < 0))
//			neighbors.add(main.getNodes()[x_pos - 1][y_pos]);
//		if (!(x_pos + 1 >= Main.getDensity()))
//			neighbors.add(main.getNodes()[x_pos + 1][y_pos]);
//		if (!(y_pos - 1 < 0))
//			neighbors.add(main.getNodes()[x_pos][y_pos - 1]);
//		if (!(y_pos + 1 >= Main.getDensity()))
//			neighbors.add(main.getNodes()[x_pos][y_pos + 1]);
		for (int x = -1;x <= 1;x++) {
			for (int y = -1;y <= 1;y++) {
				if (x_pos + x >= 0 && x_pos + x < Main.getDensity() && y_pos + y >= 0 && y_pos + y < Main.getDensity())
					neighbors.add(main.getNodes()[x_pos + x][y_pos + y]);
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("X Position: " + x_pos + "||");
		sb.append("Y Position: " + y_pos + "||");
		sb.append("x: " + x + "||");
		sb.append("y: " + y + "||");
		sb.append("Type: " + classification + "||");
		sb.append("Distance: " + ((distance == Integer.MAX_VALUE) ? "infinity" : distance));
		return sb.toString();
	}

	public int isHovering() {
		int mouseX = main.getMouseManager().getMouseX();
		int mouseY = main.getMouseManager().getMouseY();
		if (mouseX > bounds[0] && mouseX < bounds[1] && mouseY > bounds[2] && mouseY < bounds[3]) {
			return 1;
		}
		return 0;
	}
	
	public int[] getBounds() {
		return bounds;
	}

	public void makeWall() {
		if (classification == wall)
			classification = node;
		else if (classification == node)
			classification = wall;
		else
			return;
		classification = wall;
	}

	public Node getPrevious_node() {
		return previous_node;
	}

	public boolean isIn_path() {
		return in_path;
	}

	public void setIn_path(boolean in_path) {
		this.in_path = in_path;
	}

	public void setPrevious_node(Node previous_node) {
		this.previous_node = previous_node;
	}

	public int getClassification() {
		return classification;
	}

	public void setClassification(int c) {
		classification = c;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getX_pos() {
		return x_pos;
	}

	public int getY_pos() {
		return y_pos;
	}

	public ArrayList<Node> getNeighbors() {
		return neighbors;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public void setNeighbors(ArrayList<Node> neighbors) {
		this.neighbors = neighbors;
	}

	@Override
	public int compareTo(Node o) {
		if (distance < o.getDistance())
			return -1;
		else if (distance > o.getDistance())
			return 1;

		return 0;
	}

}
