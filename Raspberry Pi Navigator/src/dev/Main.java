package dev;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import dev.algorithm.Node;

public class Main implements Runnable {

	private int width, height;
	private boolean running = false;

	private int size;
	private static final int density = 10;
	private static final int whitespace = 5;
	private Node[][] nodes;

	private JFrame frame;
	private Canvas canvas;
	private Graphics g;
	private BufferStrategy bs;

	private int[] start_coords, end_coords;

	private Thread thread;

	public Main(int width, int height, int[] start_coords, int[] end_coords) {
		this.width = width;
		this.height = height;

		this.start_coords = start_coords;
		this.end_coords = end_coords;

		thread = new Thread(this);

		createGUI();
	}

	public void init() {
		nodes = new Node[density][density];
		if(width <= height)
			size = (width/density);
		else
			size = (height/density);
		
		for(int x = 0;x < nodes.length;x++) {
			for(int y = 0;y < nodes[0].length;y++) {
				if(x == start_coords[0] && y == start_coords[1])
					nodes[x][y] = new Node(x, y, x*size, y*size, size, Node.start, 0, this);
				else if(x == end_coords[0] && y == end_coords[1])
					nodes[x][y] = new Node(x, y, x*size, y*size, size, Node.end, -1, this);
				else
					nodes[x][y] = new Node(x, y, x*size, y*size, size, Node.node, -1, this);
				//System.out.println(nodes[x][y]);
			}
		}
		
		for(int x = 0;x < nodes.length;x++) {
			for(int y = 0;y < nodes[0].length;y++) {
				nodes[x][y].init_neighbors();
			}
		}
	}

	public static int getDensity() {
		return density;
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

	@Override
	public void run() {
		System.out.println("Game Loop Started");
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

	public Node[][] getNodes() {
		return nodes;
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
