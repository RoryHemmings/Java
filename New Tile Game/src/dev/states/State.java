package dev.states;

import java.awt.Graphics;

import dev.Game;

public abstract class State {
	private static State currentState;
	protected Game game;
	
	public State(Game game){
		this.game = game;
	}
	
	public static State getCurrentState(){
		return currentState;
	}
	
	public static void setCurrentState(State s){
		currentState = s;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
}
