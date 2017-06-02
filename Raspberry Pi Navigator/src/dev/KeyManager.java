package dev;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

	private Main main;

	public KeyManager(Main main) {
		this.main = main;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_A) {
			main.update_map("a");
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			main.update_map("s");
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			main.update_map("d");
		} else if (e.getKeyCode() == KeyEvent.VK_W) {
			main.update_map("w");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

}
