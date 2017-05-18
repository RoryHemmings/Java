package dev.entitys.creatures;

import dev.entitys.Entity;

public abstract class Creature extends Entity{
	
	protected int health;
	
	public Creature(float x, float y){
		super(x, y);
		
		health = 10;
	}
}
