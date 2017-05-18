package dev.states;

import java.awt.Graphics;

import dev.Game;
import dev.entitys.creatures.Player;

public class GameState extends State{
	
	Player player;
	
	public GameState(Game game){
		super(game);
		player = new Player(game, 16, 16);
	}

	@Override
	public void tick() {
		player.tick();
	}

	@Override
	public void render(Graphics g) {
		player.render(g);
	}
	
}
