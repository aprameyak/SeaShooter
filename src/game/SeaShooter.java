package game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class represents the SeaShooter game
 * It extends Game and implements KeyListener 
 */
class SeaShooter extends Game implements KeyListener {
	static int counter = 0;
	private Submarine submarine;
	private ArrayList<Shark> sharks;
	private ArrayList<Squid> squids;
	public boolean up, down, right, left;
	private int wave = 1;
	private ArrayList<Projectile> projectiles;
	private ArrayList<SquidInk> inks;
	private int messageLength = 5000;
	private Color messageColor = Color.black;

	private GameStatus gameStatus = new GameStatus() {
		/**
		 * Displays the game status on the game screen
		 * 
		 * @param status
		 * @param g
		 */
		@Override
		public void gameStatus(String status, Graphics g) {

			int top = 250;
			g.setColor(messageColor);
			g.fillRect(0, 0, width, height);
			g.setColor(Color.white);
			Font font = new Font("Arial", Font.BOLD, 24);
			g.setFont(font);
			String[] lines = status.split("\n");
			FontMetrics metrics = g.getFontMetrics(font);

			if (wave == 1) {
				for (String line : lines) {
					g.drawString(line, 75, top);
					top += 30; // move down for the next line
				}
			} else if (wave >= 5) {
				g.drawString(status, 305, 300);
				on = false;
			} else {
				g.drawString(status, 305, 300);
			}
		}
	};
	private long messageDisplayTime = 0;
	private String currentMessage = null;

	/**
	 * SeaShooter constructor to initialize SeaShooter using super constructor
	 */
	public SeaShooter() {
		super("SeaShooter!", 800, 600);
		this.setFocusable(true);
		this.requestFocus();
		Point[] submarinePoints = { new Point(-15.0, 20.0), new Point(5.0, 0.0), new Point(50.0, 0.0),
				new Point(70.0, 20.0), new Point(50.0, 40.0), new Point(5.0, 40.0), new Point(-15.0, 20.0) };
		// initialize submarine and its missiles
		submarine = new Submarine(submarinePoints, new Point(300.0, 300.0), 0.0);
		projectiles = new ArrayList<>();
		// initialize sharks and squids
		sharks = new ArrayList<>();
		squids = new ArrayList<>();
		inks = new ArrayList<>();
		spawnEnemies(); // populate

		this.addKeyListener(this);

	}

	/**
	 * Spawns the enemies based on the given points
	 */
	private void spawnEnemies() {
		Random rand = new Random();
		// Spawn 4 sharks and squids
		for (int i = 0; i < 4; i++) {
			// Shark
			Point[] sharkPoints = { new Point(-15.0, 20.0), new Point(5.0, 0.0), new Point(30.0, 10.0),
					new Point(60.0, 0.0), new Point(50.0, 20.0), new Point(60.0, 40.0), new Point(30.0, 30.0),
					new Point(5.0, 40.0), new Point(-15.0, 20.0) };
			Point sharkPosition = new Point(800.0, rand.nextInt(height - 40)); // Right edge, random Y-coordinate
			sharks.add(new Shark(sharkPoints, sharkPosition, 0.0));

			// Squid
			Point[] squidPoints = { new Point(-20.0, 20.0), new Point(-5.0, 5.0), new Point(20.0, 10.0),
					new Point(50.0, 0.0), new Point(20.0, 20.0), new Point(50.0, 40.0), new Point(20.0, 30.0),
					new Point(-5.0, 35.0), new Point(-20.0, 20.0), };
			Point squidPosition = new Point(800.0, rand.nextInt(height - 40)); // Right edge, random Y-coordinate
			Squid squid = new Squid(squidPoints, squidPosition, 0.0, this);
			squids.add(squid);
			if (wave == 1) {
				currentMessage = "Sea Shooter: You are a submarine. \nUse Arrows to move and hit Space to shoot missiles.\nShoot the Sharks and Squids before they reach the edge. \nAvoid Sharks and Ink Blasts!";
				messageDisplayTime = System.currentTimeMillis();
			} else if (wave == 2) { // second wave is faster
				sharks.get(i).changeSpeed(0.8);
				squids.get(i).changeSpeed(0.65);

			} else if (wave == 3) {
				sharks.get(i).changeSpeed(1);
				squids.get(i).changeSpeed(0.85);
			}

		}
	}

	/**
	 * Paints the SeaShooter game based on current frame
	 * 
	 * @param brush
	 */
	@Override
	public void paint(Graphics brush) {
		if (currentMessage != null && System.currentTimeMillis() - messageDisplayTime < messageLength) {
			gameStatus.gameStatus(currentMessage, brush);
			return;
		} else {
			currentMessage = null;
		}

		brush.setColor(Color.BLUE);
		brush.fillRect(0, 0, width, height);
		counter++;
		brush.setColor(Color.white);
		brush.drawString("Counter is " + counter, 10, 10);

		// Draw submarine
		submarine.paint(brush);
		submarine.move(up, down, right, left);

		// draw projectiles, use while loop to avoid errors when list is modified
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

			if (shark.getHealth() <= 0) { // check if dead
				sharks.remove(shark); // remove the shark from the list if health is 0
				break;
			}
			if (shark.checkCollision(submarine)) { // check if the shark is hit
				submarine.applyDamage(shark.getAttackDamage()); // Apply damage to submarine

			}

			shark.moveLeft(); // advance left
			if (shark.getPosition() <= 0) { // check if shark hits the left side
				currentMessage = "You Lose :(";
				messageColor = Color.RED;
				wave += 5;
				messageDisplayTime = System.currentTimeMillis();

				break;
			}
		}

		// Draw squids
		for (Squid squid : squids) {
			squid.paint(brush);
			if (squid.getHealth() <= 0) { // check if the squid is dead
				squids.remove(squid); // remove the squid from the list if health is 0
				break;
			}
			squid.moveLeft(); // advance left
			squid.fireInk(); // shoot
			if (squid.getPosition() <= 0) { // Check if squid hits the left side
				wave += 5;
				currentMessage = "You Lose :(";
				messageColor = Color.RED;
				messageDisplayTime = System.currentTimeMillis();

				break;
			}
		}
		// draw ink projectiles
		for (int j = 0; j < inks.size(); j++) {
			SquidInk ink = inks.get(j);
			ink.paint(brush); // draw the ink
			ink.move(); // move the ink

			// check if the ink hits the submarine
			if (ink.checkHit(submarine)) {
				submarine.applyDamage(ink.getDamage()); // apply damage to submarine

				inks.remove(j); // remove ink after collision
				j--; // adjust index after removal to avoid skipping elements
			}
		}
		brush.setColor(Color.RED);
		brush.setFont(new Font("Arial", Font.BOLD, 18));
		brush.drawString("Submarine Health: " + submarine.getHealth(), 10, 30);

		// handle when all enemies are dead
		if (sharks.isEmpty() && squids.isEmpty()) {
			wave++;
			if (wave == 2) {
				spawnEnemies(); // Second wave
				messageLength = 2000;
				currentMessage = "Second Wave!";

				messageDisplayTime = System.currentTimeMillis();
			} else if (wave == 3) {
				spawnEnemies(); // third wave
				currentMessage = "Third Wave!"; // third wave
				messageLength = 2000;
				messageDisplayTime = System.currentTimeMillis();
			} else if (wave == 4) {
				currentMessage = "You Win!!"; // wins after three waves
				messageColor = Color.PINK;
				messageDisplayTime = System.currentTimeMillis();
				on = false;

			}

		}
		// handle when submarine is dead
		if (submarine.getHealth() <= 0) {
			wave += 5;
			currentMessage = "You Lose :(";
			messageColor = Color.RED;
			messageDisplayTime = System.currentTimeMillis();

		}
	}

	/**
	 * Shoots a submarine missle
	 */
	private void shootMissile() {
		double missileAngle = submarine.getRotation(); // submarine's rotation angle
		Point missileStartPosition = new Point(submarine.getPosition().x + 30, submarine.getPosition().y); // adjust for
																											// front
		Projectile missile = new Missile(missileStartPosition, missileAngle); // create new missile, add to list
		projectiles.add(missile);
	}

	/**
	 * Adds ink to inks 
	 * 
	 * @param ink
	 */
	public void addInk(SquidInk ink) {
		inks.add(ink);
	}

	/**
	 * Sets movement booleans true based on key events
	 * 
	 * @param e
	 */
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

	/**
	 * Sets movement booleans false based on key events
	 * Shoots missiles based on key event
	 * 
	 * @param e
	 */
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

	/**
	 * Implements method from KeyListener
	 */
	@Override
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * This class represents a Missile used by submarine
	 * It extends Projectile and is used to attack enemies
	 */
	private class Missile extends Projectile {
		/**
		 * Missile constructor to initialize Missile using super constructor
		 * 
		 * @param position
		 * @param angle
		 */
		public Missile(Point position, double angle) {
			super(position, angle, 5, 20); // Speed 5, Damage 10
		}

		/**
	 	* Paints the Missile based on current frame
		 * 
	 	* @param brush
	 	*/
		@Override
		public void paint(Graphics brush) {
			brush.setColor(Color.RED); // red
			brush.fillRect((int) position.x, (int) position.y, 10, 5);
		}

		/**
		 * Returns the damage of the Missile
		 */
		public int getDamage() {
			return damage;
		}
	}

	/**
	 * This class represents SquidInk used by squids
	 * It extends Projectile and is used to attack the submarine
	 */
	public class SquidInk extends Projectile {
		private final int INK_WIDTH = 10;
		private final int INK_HEIGHT = 5;
		private final int INK_SPEED = 2;
		private int damage = 5;

		/** 
		 * SquidInk constructor to initialize SquidInk using super constructor
		 *
		 * @param position
		 * @param angle
		 */
		public SquidInk(Point position, double angle) {
			super(position, angle, 2, 10); // Speed, Damage
			this.damage = 10; // Set the damage value
		}

		/**
		 * Returns the damage of the SquidInk
		 */
		@Override
		public int getDamage() {
			return damage;
		}

/**
	 	* Paints the SquidInk based on current frame
		* 
	 	* @param brush
	 	*/		
		@Override
		public void paint(Graphics brush) {
			brush.setColor(Color.BLACK); // Squid ink is purple/magenta

			// Draw the squid ink
			brush.fillRect((int) position.x, (int) position.y, INK_WIDTH, INK_HEIGHT);
		}

		/**
		 * Moves the SquidInk to the left
		 */
		@Override
		public void move() {
			this.position.x -= INK_SPEED;
		}

		/**
		 * Checks if SquidInk has hit the submarine
		 * 
		 * @param target
		 * @return
		 */
		public boolean checkHit(Submarine target) {
			if (target instanceof Submarine) {
				Submarine submarine = (Submarine) target;

				// check if the rectangle of the squid ink intersects with the submarine's
				// rectangle
				return this.position.x < submarine.getPosition().x + 60
						&& this.position.x + INK_WIDTH > submarine.getPosition().x
						&& this.position.y < submarine.getPosition().y + 30
						&& this.position.y + INK_HEIGHT > submarine.getPosition().y;
			}
			return false;
		}

		/** 
		 * Checks if the SquidInk has hit a submarine
   		 *
   		 * @param target
		 */ 
		public void onHit(Damageable target) {
			if (target instanceof Submarine) {
				Submarine submarine = (Submarine) target;
				submarine.applyDamage(this.getDamage()); // Apply damage to the submarine
			}
		}

	}

	public static void main(String[] args) {
		SeaShooter a = new SeaShooter();
		a.repaint();
	}
}
