package dev.algorithm;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import dev.Main;

public class Node {
	
	private int x, y, x_pos, y_pos;
	private int distance = -1;
	private boolean checked = false; 
	private int classification = 0;
	private int size;
	
	private Main main;
	
	public static final int node = 0, wall = 1, start = 2, end = 3;
	
	private ArrayList<Node> neighbors = new ArrayList<Node>();
	
	public Node(int x_pos, int y_pos, int x, int y, int size, int classification, int distance, Main main){
		this.x = x;
		this.y = y;
		this.x_pos = x_pos;
		this.y_pos = y_pos;
		this.size = size;
		this.classification = classification;
		this.distance = distance;
		this.main = main;
	}
	
	public void render(Graphics g){
		if(classification == start)
			g.setColor(Color.red);
		else if(classification == end)
			g.setColor(Color.blue);
		else if(classification == wall)
			g.setColor(Color.black);
		else if(classification == node)
			g.setColor(Color.gray);
		g.fillRoundRect(x, y, size, size, 25, 25);
	}
	
	public void init_neighbors(){	
		if(!(x_pos - 1 < 0))
			neighbors.add(main.getNodes()[x_pos - 1][y_pos]);
		if(!(x_pos + 1 >= Main.getDensity()))
			neighbors.add(main.getNodes()[x_pos + 1][y_pos]);
		if(!(y_pos - 1 < 0))
			neighbors.add(main.getNodes()[x_pos][y_pos - 1]);
		if(!(y_pos + 1 >= Main.getDensity()))
			neighbors.add(main.getNodes()[x_pos][y_pos + 1]);
		if(x_pos == 0 && y_pos == 3)
			for(Node n: neighbors)
				System.out.println(n.getX_pos() + "||" + n.getY_pos());
				
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("X Position: " + x_pos +"||");
		sb.append("Y Position: " + y_pos +"||");
		sb.append("x: " + x +"||");
		sb.append("y: " + y +"||");
		sb.append("Type: " + classification + "||");
		sb.append("Distance: " + ((distance == -1) ? "infinity" : distance));
		sb.append(neighbors.get(1));
		return sb.toString();
	}

	public int getClassification(){
		return classification;
	}
	
	public void setClassification(int c){
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
	
}
