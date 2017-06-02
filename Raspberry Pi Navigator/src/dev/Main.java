package dev;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.List;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;

import dev.algorithm.Dijkstras;
import dev.algorithm.Node;

public class Main implements Runnable {

	private int width, height;
	private boolean running = false;

	private int size;
	private static final int density = 20;

	private Node[][] nodes;
	
	private MouseManager mm;

	private JFrame frame;
	private Canvas canvas;
	private Graphics g;
	private BufferStrategy bs;

	private ArrayList<Node> shortest_path = new ArrayList<Node>();
	private Dijkstras dijkstras;

	private int[] start_coords, end_coords;

	private Thread thread;

	public Main(int width, int height, int[] start_coords) {
		this.width = width;
		this.height = height;

		this.start_coords = start_coords;
		end_coords = new int[]{density - 1, density - 1};

		thread = new Thread(this);
		dijkstras = new Dijkstras();

		createGUI();
	}

	public void init() {
		mm = new MouseManager(this);
		frame.addMouseListener(mm);
		frame.addMouseMotionListener(mm);
		canvas.addMouseListener(mm);
		canvas.addMouseMotionListener(mm);
		
		nodes = new Node[density][density];
		if (width <= height)
			size = (width / density);
		else
			size = (height / density);

		for (int x = 0; x < nodes.length; x++) {
			for (int y = 0; y < nodes[0].length; y++) {
				if (x == start_coords[0] && y == start_coords[1]) {
					nodes[x][y] = new Node(x, y, x * size, y * size, size, Node.start, 0, this);
				} else if (x == end_coords[0] && y == end_coords[1])
					nodes[x][y] = new Node(x, y, x * size, y * size, size, Node.end, Integer.MAX_VALUE, this);
				else
					nodes[x][y] = new Node(x, y, x * size, y * size, size, Node.node, Integer.MAX_VALUE, this);
				// System.out.println(nodes[x][y]);
			}
		}

		for (int x = 0; x < nodes.length; x++) {
			for (int y = 0; y < nodes[0].length; y++) {
				nodes[x][y].init_neighbors();
			}
		}
		
		shortest_path = dijkstras.findShortestPath(nodes, 1, this);
		for (Node n : shortest_path) {
			nodes[n.getX_pos()][n.getY_pos()].setIn_path(true);
		}
	}

	public void tick() {

	}

	public void render() {
		if (bs == null)
			canvas.createBufferStrategy(3);
		bs = canvas.getBufferStrategy();
		g = bs.getDrawGraphics();
		g.clearRect(0, 0, width, height);

		// Render
		renderNodes();
		//

		bs.show();
		g.dispose();
	}

	public void renderNodes() {
		for (int x = 0; x < nodes.length; x++) {
			for (int y = 0; y < nodes[0].length; y++) {
				nodes[x][y].render(g);
			}
		}
	}

	public void update_map(int evx, int evy) {
		for (int x = 0; x < nodes.length; x++) {
			for (int y = 0; y < nodes[0].length; y++) {
				if(evx > nodes[x][y].getBounds()[0] && evx < nodes[x][y].getBounds()[1] && evy > nodes[x][y].getBounds()[2] && evy < nodes[x][y].getBounds()[3]) {
					nodes[x][y].makeWall();
				}
			}
		}
		
		for (int x = 0; x < nodes.length; x++) {
			for (int y = 0; y < nodes[0].length; y++) {
				nodes[x][y].setIn_path(false);
				nodes[x][y].setPrevious_node(null);
				if(nodes[x][y].getClassification() != Node.start)
					nodes[x][y].setDistance(Integer.MAX_VALUE);
			}
		}
		
		shortest_path = dijkstras.findShortestPath(nodes, 1, this);
		for (Node n : shortest_path) {
			nodes[n.getX_pos()][n.getY_pos()].setIn_path(true);
		}
	}

	@Override
	public void run() {
		init();

		int fps = 60;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();

		while (running) {
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			lastTime = now;

			if (delta >= 1) {
				tick();
				render();
				delta--;
			}
		}

		stop();
	}

	public void createGUI() {
		canvas = new Canvas();
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setFocusable(false);

		frame = new JFrame("Navigation");
		frame.setSize(width, height);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.add(canvas);
		frame.pack();
	}

	public void start() {
		if (!running) {
			thread.start();
			running = true;
		}
	}

	public void stop() {
		if (running) {
			try {
				thread.join();
				running = false;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public MouseManager getMouseManager() {
		return mm;
	}

	public Node[][] getNodes() {
		return nodes;
	}

	public static int getDensity() {
		return density;
	}

	public int[] getStart_coords() {
		return start_coords;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isRunning() {
		return running;
	}

}
