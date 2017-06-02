package dev;

import javax.swing.JOptionPane;

public class Launcher {
	
	public static void main(String[] args) {
		
//		String start = JOptionPane.showInputDialog("Enter Start Coords");
//		String end = JOptionPane.showInputDialog("Enter End Coords");
//		
//		int[] s = new int[2];
//		int[] e = new int[2];
//		
//		s[0] = Integer.parseInt(start.split("\\,")[0]);
//		e[0]= Integer.parseInt(end.split("\\,")[0]);
//		s[1] = Integer.parseInt(start.split("\\,")[1]);
//		e[1]= Integer.parseInt(end.split("\\,")[1]);
		
		int[] s = new int[]{0, 0};
		int[] e = new int[]{19, 19};
		
		Main m = new Main(600, 600, s);
		m.start();
	}
	
}
