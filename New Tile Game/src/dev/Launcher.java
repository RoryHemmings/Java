package dev;

import dev.display.Display;

public class Launcher {
	public static void main(String[] args){
		Game game = new Game("Game", 900, 600);
		game.start();
	}
}
