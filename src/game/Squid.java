package game;

import java.awt.Color;
import java.awt.Graphics;

public class Squid extends Polygon implements Enemy {
    private int squidHealth = 100;
    private int squidAttack = 10;
    private double speed = 0.2;
    private SeaShooter seaShooter;
    private long lastInkShotTime = 0;  // Time of the last ink shot
    private int inkCooldown = 1000;  // Cooldown period 

    
    public void attack() {
    	long currentTime = System.currentTimeMillis();  // Get the current time 
        
        // Check if enough time has passed since the last ink shot
        if (currentTime - lastInkShotTime >= inkCooldown) {
          
            Point inkStartPosition = new Point(this.position.x, this.position.y); // Start position of the ink at the squid

            // Create a new SquidInk and add it to the ink list
            SquidInk squidInk = new SquidInk(inkStartPosition, 0);
            seaShooter.addInk(squidInk);  // Add the ink to the SeaShooter's ink list
            
            // Update the last ink shot time to the current time
            lastInkShotTime = currentTime;
        }
    }
    public Squid(Point[] squidPoints, Point squidPosition, double squidRotation,SeaShooter seaShooter ) {
        super(squidPoints, squidPosition , squidRotation);
        this.seaShooter = seaShooter;
    }

    private final Damageable takeDamage = (damage) -> squidHealth -= damage;

    public void takesDamage(int damage) {
        takeDamage.applyDamage(damage);
    }

    public int getAttackDamage() {
        return squidAttack;
    }
    public int getHealth() {
        return squidHealth;
    }
    public void changeSpeed(double speed) {
    	this.speed = speed;
    }
    public void paint(Graphics brush) {
        Point[] points = this.getPoints();
        int[] xPoints = new int[points.length];
        int[] yPoints = new int[points.length];

        for (int i = 0; i < points.length; i++) {
            xPoints[i] = (int) points[i].x;
            yPoints[i] = (int) points[i].y;
        }

        brush.setColor(Color.CYAN); // Squids are cyan
        brush.fillPolygon(xPoints, yPoints, points.length);
    }
    public void moveLeft() {
        this.position.x -= speed; //moves left
        
    }
    public void fireInk() {
        attack();  // Fire ink without affecting squid movement
    }
    public boolean checkHit(Projectile projectile) {
        Point squidPosition = this.position;
        Point projectilePosition = projectile.position;

        if (projectilePosition.x < squidPosition.x + 60 && 
            projectilePosition.x + 10 > squidPosition.x && 
            projectilePosition.y < squidPosition.y + 30 && 
            projectilePosition.y + 5 > squidPosition.y) {
            return true; // Collision detected
        }
        return false; // No collision
    }
}