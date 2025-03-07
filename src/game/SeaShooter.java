package game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

class SeaShooter extends Game implements KeyListener {
	static int counter = 0;
	private Submarine submarine;
    private ArrayList<Shark> sharks;
    private ArrayList<Squid> squids;
	public boolean up, down, right, left;
	private int wave =1;

	public SeaShooter() {
		super("SeaShooter!", 800, 600);
		this.setFocusable(true);
		this.requestFocus();
		Point[] submarinePoints = { new Point(60.0, 0.0), new Point(60.0, 30.0), new Point(0.0, 30.0), new Point(0.0, 0.0) };
		submarine = new Submarine(submarinePoints, new Point(300.0, 300.0), 0.0);
		 // Create sharks and squids
        sharks = new ArrayList<>();
        squids = new ArrayList<>();

       spawnEnemies();
		this.addKeyListener(this);
	}
	 private void spawnEnemies() {
	        Random rand = new Random();

	        // Spawn 3 sharks and squids for now
	        for (int i = 0; i < 3; i++) {
	            // Shark
	            Point[] sharkPoints = { new Point(60.0, 0.0), new Point(60.0, 30.0), new Point(0.0, 30.0), new Point(0.0, 0.0) };
	            Point sharkPosition = new Point(800.0, rand.nextInt(height)); // Random Y-coordinate
	            sharks.add(new Shark(sharkPoints, sharkPosition, 0.0));
	            if (wave==2) {
	            sharks.get(i).changeSpeed(0.5);
	            }

	            // Squid
	            Point[] squidPoints = { new Point(60.0, 0.0), new Point(60.0, 30.0), new Point(0.0, 30.0), new Point(0.0, 0.0) };
	            Point squidPosition = new Point(800.0, rand.nextInt(height)); // Random Y-coordinate
	            squids.add(new Squid(squidPoints, squidPosition, 0.0));
	            if (wave==2) {
		            squids.get(i).changeSpeed(0.5);
		            }
	        }
	    }

	@Override
	public void paint(Graphics brush) {
	    brush.setColor(Color.black);
	    brush.fillRect(0, 0, width, height);

	    counter++;
	    brush.setColor(Color.white);
	    brush.drawString("Counter is " + counter, 10, 10);

	    // Draw submarine
	    submarine.paint(brush);
	    submarine.move(up, down, right, left);

	    // Draw sharks
	    for (Shark shark : sharks) {
	        shark.paint(brush);
	        shark.moveLeft(); //advance left
	        if (shark.checkCollision(submarine)) {
                submarine.applyDamage(shark.getAttackDamage()); // Apply damage to submarine
                System.out.println("Submarine hit. Remaining health: " + submarine.getHealth());
            }
	    }

	    // Draw squids
	    for (Squid squid : squids) {
	        squid.paint(brush);
	        squid.moveLeft(); //advance left
	    }
	    if (sharks.isEmpty() && squids.isEmpty() && wave == 1) {
	        wave++;
	    	spawnEnemies(); // Second wave
	        System.out.print("Second Wave!");
	    }
	    else if (sharks.isEmpty() && squids.isEmpty() && wave == 1) {
	    	System.out.print("You Win!!");
	    }
	    if (submarine.getHealth() <= 0) {
	        on = false;
	    }
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
