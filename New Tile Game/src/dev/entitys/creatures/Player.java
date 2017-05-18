package dev.entitys.creatures;

import java.awt.Graphics;

import dev.Game;
import dev.gfx.Assets;

public class Player extends Creature{
	
	private Game game;

	public Player(Game game, float x, float y) {
		super(x, y);
		this.game = game;
	}

	@Override
	public void tick() {
		x += 10;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.player_1, (int) x, (int) y, null);
	}

}
