package dev;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;

import dev.algorithm.Dijkstras;
import dev.algorithm.Node;
import dev.extras.Sidebar;

public class Main implements Runnable {

	private int width, height;
	private boolean running = false;

	private int size;
	private static int density = 20;

	private Node[][] nodes;
	//private int[][] randomnodes;
	//private int randomamount = 100;
	private boolean diag;

	private MouseManager mm;
	private KeyManager km;

	private JFrame frame;
	private Canvas canvas;
	private Graphics g;
	private BufferStrategy bs;

	private ArrayList<Node> shortest_path = new ArrayList<Node>();
	private Dijkstras dijkstras;

	private Sidebar sidebar;

	private int[] start_coords, end_coords;

	private Thread thread;

	public Main(int height, int[] start_coords, boolean diag, int density) {
		this.height = height;
		this.diag = diag;
		Main.density = density;
		width = height + 150;

		this.start_coords = start_coords;
		end_coords = new int[] { density - 1, density - 1 };

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

		km = new KeyManager(this);
		frame.addKeyListener(km);
		canvas.addKeyListener(km);

		nodes = new Node[density][density];
		//randomnodes = new int[randomamount][2];
		//Random r = new Random();

		if (width <= height)
			size = (width / density);
		else
			size = (height / density);

		for (int x = 0; x < nodes.length; x++) {
			for (int y = 0; y < nodes[0].length; y++) {
				if (x == start_coords[0] && y == start_coords[1]) {
					nodes[x][y] = new Node(x, y, x * size, y * size, size, Node.start, 0, diag, this);
				} else if (x == end_coords[0] && y == end_coords[1])
					nodes[x][y] = new Node(x, y, x * size, y * size, size, Node.end, Integer.MAX_VALUE, diag, this);
				else
					nodes[x][y] = new Node(x, y, x * size, y * size, size, Node.node, Integer.MAX_VALUE, diag, this);
			}
		}

		for (int x = 0; x < nodes.length; x++) {
			for (int y = 0; y < nodes[0].length; y++) {
				nodes[x][y].init_neighbors();
			}
		}

		// for (int[] num : randomnodes) {
		// num = new int[]{r.nextInt(density - 2) + 2, r.nextInt(density - 2) +
		// 2};
		// nodes[num[0]][num[1]].setClassification(Node.wall);
		// }

		shortest_path = dijkstras.findShortestPath(nodes, 1, this);
		sidebar = new Sidebar(width - 150, shortest_path.get(0).getDistance(), Color.LIGHT_GRAY, this);
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
		sidebar.render(g);
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
				if (evx > nodes[x][y].getBounds()[0] && evx < nodes[x][y].getBounds()[1]
						&& evy > nodes[x][y].getBounds()[2] && evy < nodes[x][y].getBounds()[3]) {
					nodes[x][y].makeWall();
				}
			}
		}

		for (int x = 0; x < nodes.length; x++) {
			for (int y = 0; y < nodes[0].length; y++) {
				nodes[x][y].setIn_path(false);
				nodes[x][y].setPrevious_node(null);
				if (nodes[x][y].getClassification() != Node.start)
					nodes[x][y].setDistance(Integer.MAX_VALUE);
			}
		}

		shortest_path = dijkstras.findShortestPath(nodes, 1, this);
		sidebar.setMax_distance(shortest_path.get(0).getDistance());
		for (Node n : shortest_path) {
			nodes[n.getX_pos()][n.getY_pos()].setIn_path(true);
		}
	}
	
	public void update_map(String direction) {
		switch (direction) {
			case "a":
				if(start_coords[0]-1 < 0)
					break;
				nodes[start_coords[0]][start_coords[1]].setClassification(Node.node);
				nodes[start_coords[0]-1][start_coords[1]].setClassification(Node.start);
				nodes[start_coords[0]-1][start_coords[1]].setDistance(0);
				start_coords[0] = start_coords[0] - 1;
				break;
			case "s":
				if(start_coords[1]+1 > density - 1)
					break;
				nodes[start_coords[0]][start_coords[1]].setClassification(Node.node);
				nodes[start_coords[0]][start_coords[1]+1].setClassification(Node.start);
				nodes[start_coords[0]][start_coords[1]+1].setDistance(0);
				start_coords[1] = start_coords[1] + 1;
				break;
			case "d":
				if(start_coords[0]+1 > density - 1)
					break;
				nodes[start_coords[0]][start_coords[1]].setClassification(Node.node);
				nodes[start_coords[0]+1][start_coords[1]].setClassification(Node.start);
				nodes[start_coords[0]+1][start_coords[1]].setDistance(0);
				start_coords[0] = start_coords[0] + 1;
				break;
			case "w":
				if(start_coords[1]-1 < 0)
					break;
				nodes[start_coords[0]][start_coords[1]].setClassification(Node.node);
				nodes[start_coords[0]][start_coords[1]-1].setClassification(Node.start);
				nodes[start_coords[0]][start_coords[1]-1].setDistance(0);
				start_coords[1] = start_coords[1] - 1;
				break;
		}
		for (int x = 0; x < nodes.length; x++) {
			for (int y = 0; y < nodes[0].length; y++) {
				nodes[x][y].setIn_path(false);
				nodes[x][y].setPrevious_node(null);
				if (nodes[x][y].getClassification() != Node.start)
					nodes[x][y].setDistance(Integer.MAX_VALUE);
			}
		}

		shortest_path = dijkstras.findShortestPath(nodes, 1, this);
		sidebar.setMax_distance(shortest_path.get(0).getDistance());
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
