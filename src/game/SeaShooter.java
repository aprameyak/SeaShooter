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
	private int wave = 1;
	private ArrayList<Projectile> projectiles;

	public SeaShooter() {
		super("SeaShooter!", 800, 600);
		this.setFocusable(true);
		this.requestFocus();
		Point[] submarinePoints = { new Point(60.0, 0.0), new Point(60.0, 30.0), new Point(0.0, 30.0),
				new Point(0.0, 0.0) };
		// initialize submarine and its missiles
		submarine = new Submarine(submarinePoints, new Point(300.0, 300.0), 0.0);
		projectiles = new ArrayList<>();
		// initialize sharks and squids
		sharks = new ArrayList<>();
		squids = new ArrayList<>();
		spawnEnemies(); //populate
	
		this.addKeyListener(this);
	}

	private void spawnEnemies() {
		Random rand = new Random();
		// Spawn 4 sharks and squids
		for (int i = 0; i < 4; i++) {
			// Shark
			Point[] sharkPoints = { new Point(60.0, 0.0), new Point(60.0, 30.0), new Point(0.0, 30.0),
					new Point(0.0, 0.0) };
			Point sharkPosition = new Point(800.0, rand.nextInt(height-30)); // Right edge, random Y-coordinate
			sharks.add(new Shark(sharkPoints, sharkPosition, 0.0));
			if (wave == 2) { //second wave is faster
				sharks.get(i).changeSpeed(0.75);
			}

			// Squid
			Point[] squidPoints = { new Point(60.0, 0.0), new Point(60.0, 30.0), new Point(0.0, 30.0),
					new Point(0.0, 0.0) };
			Point squidPosition = new Point(800.0, rand.nextInt(height-30)); // Right edge, random Y-coordinate
			squids.add(new Squid(squidPoints, squidPosition, 0.0));
			if (wave == 2) { //second wave is faster
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
		
		// Draw projectiles, use while loop to avoid errors when list is modified
		int i = 0;
		while (i < projectiles.size()) {
		    Projectile projectile = projectiles.get(i);
		    projectile.paint(brush);
		    projectile.move();

		    // check if the missile hits sharks
		    int j = 0;
		    while (j < sharks.size()) {
		        Shark shark = sharks.get(j);
		        if (shark.checkHit(projectile)) {
		            shark.takesDamage(projectile.getDamage());
		            projectiles.remove(i); // remove the projectile after collision
		            break; 
		        } else {
		            j++; // only increment if no removal happened
		        }
		    }

		    // check for collisions with squids
		    int k = 0;
		    while (k < squids.size()) {
		        Squid squid = squids.get(k);
		        if (squid.checkHit(projectile)) {
		            squid.takesDamage(projectile.getDamage());
		            projectiles.remove(i); // remove the projectile after collision
		            break; 
		        } else {
		            k++; // only increment if no removal happened
		        }
		    }

		    // move to the next projectile
		    if (projectiles.contains(projectile)) {
		        i++;
		    }
		}
		
		
		// Draw sharks
		for (Shark shark : sharks) {
			shark.paint(brush);
			
			if (shark.getHealth() <= 0) { //check if dead
				sharks.remove(shark); // remove the shark from the list if health is 0
				break;
			}
			if (shark.checkCollision(submarine)) { //check id the shark is hit
				submarine.applyDamage(shark.getAttackDamage()); // Apply damage to submarine
				System.out.println("Submarine hit. Remaining health: " + submarine.getHealth());
			}
			
			shark.moveLeft(); // advance left
		}

		// Draw squids
		for (Squid squid : squids) {
			squid.paint(brush);
			if (squid.getHealth() <= 0) { //check if the squid is dead
				squids.remove(squid); // remove the squid from the list if health is 0
				break; 
			}
			squid.moveLeft(); // advance left
		}
		
		
		//handle when all enemies are dead
		if (sharks.isEmpty() && squids.isEmpty() && wave == 1) {
			wave++;
			spawnEnemies(); // Second wave
			System.out.print("Second Wave!");
		} else if (sharks.isEmpty() && squids.isEmpty() && wave == 2) {
			System.out.print("You Win!!"); // wins after two waves
			on = false;
		}
		
		//handle when submarine is dead
		if (submarine.getHealth() <= 0) {
			System.out.print("You Lose :(");
			on = false; //ends game
		}
	}

	private void shootMissile() {

		double missileAngle = submarine.getRotation(); // submarine's rotation angle
		Point missileStartPosition = new Point(submarine.getPosition().x + 30, submarine.getPosition().y); // adjust for front of sub																								
		Projectile missile = new Missile(missileStartPosition, missileAngle); //create new missile, add to list
		projectiles.add(missile);
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
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			shootMissile(); // shoot missile on space key press
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
