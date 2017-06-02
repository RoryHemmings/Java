package dev;

import java.io.File;

import dev.io.ConfigManager;

public class Launcher {
	
	public static void main(String[] args) {
		
		int[] s = new int[]{0, 0};
		
		ConfigManager cm = new ConfigManager(new File("config.dat"));
		
		Main m = new Main(cm.getHeight(), s, cm.isDiag(), cm.getDensity());
		m.start();
	}
	
}
