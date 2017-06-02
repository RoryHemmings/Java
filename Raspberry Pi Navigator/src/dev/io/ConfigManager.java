package dev.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigManager {
	
	private BufferedReader input;
	private FileReader fw;
	
	private boolean diag;
	private int density, height;
	
	public ConfigManager(File in) {
		try {
			fw = new FileReader(in);
			input = new BufferedReader(fw);
		} catch (FileNotFoundException e) {
			System.err.println("File not Found");;
		}
		
		try {
			density = Integer.parseInt(input.readLine().split(":")[1]);
			height = Integer.parseInt(input.readLine().split(":")[1]);
			diag = Boolean.parseBoolean(input.readLine().split(":")[1]);
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			fw.close();
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isDiag() {
		return diag;
	}

	public int getDensity() {
		return density;
	}

	public int getHeight() {
		return height;
	}
}
