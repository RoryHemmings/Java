package dev.gfx;

import java.awt.image.BufferedImage;

public class Assets {
	
	private static final int width = 256, height = 256;
	private static SpriteSheet sheet;
	public static BufferedImage player_1, player_2, floor_tile, pit_tile, background, sky, menu_button;
	
	public static void init(){
		sheet = new SpriteSheet(ImageLoader.loadImage("/textures/Sheet.png"));
		
		player_1 = sheet.crop(0, 0, width, height);
		player_2 = sheet.crop(width, 0, width, height);
		floor_tile = sheet.crop(width*2, 0, width, height);
	}
}
