package dev;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseManager implements MouseListener, MouseMotionListener {
	
	private int mouseX = 0, mouseY = 0;
	private boolean mousePressed = false;
	private boolean valid = true;
	
	private Main main;
	
	public MouseManager(Main main) {this.main = main;}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
//		if(valid) {
//			mousePressed = true;
//			valid = false;
//		}
//		else
//			mousePressed = false;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
//		mousePressed = false;
//		valid = true;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		main.update_map(e.getX(), e.getY());
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
	
	@Override
	public void mouseDragged(MouseEvent e) {}

	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

//	public boolean isMousePressed() {
//		return mousePressed;
//	}

}
