package dev;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import dev.display.Display;
import dev.gfx.Assets;
import dev.input.KeyManager;
import dev.states.GameState;
import dev.states.MenuState;
import dev.states.State;

public class Game implements Runnable{
	
	private Display display;
	
	private String title;
	private int width, height;
	
	private Thread thread;
	
	private boolean running = false;
	
	private BufferStrategy bs;
	private Graphics g;
	
	private State gameState;
	private State menuState;
	
	private KeyManager km;
	
	public Game(String title, int width, int height) {
		this.width = width;
		this.height = height;
		this.title = title;
		
		km = new KeyManager();
	}
	
	private void init(){
		display = new Display(title, width, height);
		display.getFrame().addKeyListener(km);
		gameState = new GameState(this);
		menuState = new MenuState(this);
		State.setCurrentState(gameState);
		Assets.init();
	}
	
	private void tick(){
		if(State.getCurrentState() != null)
			State.getCurrentState().tick();
	}
	
	private void render(){
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null){
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		g.clearRect(0, 0, width, height);
		//draw
		if(State.getCurrentState() != null)
			State.getCurrentState().render(g);
		//stop drawing
		bs.show();
		g.dispose();
		
	}
	
	public void run(){
		init();
		
		int fps = 60;
		double timePerTick = 1000000000/fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		
		while(running){
			now = System.nanoTime();
			delta += (now-lastTime)/timePerTick;
			lastTime = now;
			
			if(delta >=1){
				tick();
				render();
				delta--;
			}
		}
		
		stop();
	}
	
	public synchronized void start(){
		if(running)
			return;
			running = true;
			thread = new Thread(this);
			thread.start();
	}
	
	public synchronized void stop(){
		if(!running)
			return;
		try {
			thread.join();
			running = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public KeyManager getKeyManager(){
		return km;
	}
}
