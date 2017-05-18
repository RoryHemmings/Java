package dev.states;

import java.awt.Graphics;

import dev.Game;
import dev.gfx.Assets;

public class MenuState extends State{
	
	public MenuState(Game game){
		super(game);
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.player_2, 0, 0, null);
	}

}
