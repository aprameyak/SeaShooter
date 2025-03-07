package game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class SeaShooter extends Game implements KeyListener {
	static int counter = 0;
	private Submarine submarine;
	public boolean up, down, right, left;

	public SeaShooter() {
		super("SeaShooter!", 800, 600);
		this.setFocusable(true);
		this.requestFocus();
		Point[] submarinePoints = { new Point(60.0, 0.0), new Point(60.0, 30.0), new Point(0.0, 30.0), new Point(0.0, 0.0) };
		submarine = new Submarine(submarinePoints, new Point(300.0, 300.0), 0.0);
		this.addKeyListener(this);
	}

	@Override
	public void paint(Graphics brush) {
		brush.setColor(Color.black);
		brush.fillRect(0, 0, width, height);

		// sample code for printing message for debugging
		// counter is incremented and this message printed
		// each time the canvas is repainted
		counter++;
		brush.setColor(Color.white);
		brush.drawString("Counter is " + counter, 10, 10);
		submarine.paint(brush);
		submarine.move(up, down, right, left);

		if (submarine.getHealth() <= 0) {
			on = false;
		}
		repaint(); // continuous movement updates
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			up = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			down = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			left = true;
		}
		repaint(); 
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			up = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			down = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			left = false;
		}
		repaint(); 
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	public static void main(String[] args) {
		SeaShooter a = new SeaShooter();
		a.repaint();
	}
}
