package game;

import java.awt.Color;
import java.awt.Graphics;

/**
 * This class represents a Shark in the SeaShooter game
 * It extends Polygon and implements Enemy
 * It attacks the submarine
 */
public class Shark extends Polygon implements Enemy {
    private int sharkHealth = 100;
    private final int SHARK_ATTACK = 15;  
    private boolean hasDamaged = false;
    private double speed = 0.6;
    private double verticalSpeed;

    /**
     * Shark constructor to initialize a shark using super constructor
     * 
     * @param sharkPoints
     * @param sharkPosition
     * @param sharkRotation
     */
    public Shark(Point[] sharkPoints, Point sharkPosition, double sharkRotation) {
        super(sharkPoints, sharkPosition, sharkRotation);
        this.verticalSpeed = (Math.random() * 2 - 1) * speed * 0.5; 
    }

    private final Damageable takeDamage = (damage) -> sharkHealth -= damage;

    /**
     * Implements method from Enemy interface
     */
    @Override
    public void attack() {
        System.out.println("The shark attacks!");
    }

    /**
     * Applies the damage to the shark's health
     * 
     * @param damage
     */
    @Override
    public void takesDamage(int damage) {
        takeDamage.applyDamage(damage);
    }

    /** 
     * Returns the shark's attack damage
     * 
     * @return
    */ 
    @Override
    public int getAttackDamage() {
        return SHARK_ATTACK;
    }

    /**
     * Returns the shark's current health
     * 
     * @return
     */
    public int getHealth() {
        return sharkHealth;
    }
    
    /**
     * Changes the speed of the shark to speed
     * 
     * @param speed
     */
    @Override
    public void changeSpeed(double speed) {
        this.speed = speed;
    }
    
    /**
     * Paints the shark based on its points
     * 
     * @param brush
     */
    public void paint(Graphics brush) {
        Point[] points = this.getPoints();
        int[] xPoints = new int[points.length];
        int[] yPoints = new int[points.length];

        for (int i = 0; i < points.length; i++) {
            xPoints[i] = (int) points[i].x;
            yPoints[i] = (int) points[i].y;
        }

        brush.setColor(Color.GRAY); // Sharks are red
        brush.fillPolygon(xPoints, yPoints, points.length);
    }

    /**
     * Moves the shark left based on speed
     */
    public void moveLeft() {
        this.position.x -= speed; //moves left
        this.position.y += verticalSpeed;//random angle
        if (this.position.y < 0 || this.position.y > 500) {
            verticalSpeed = -verticalSpeed; // Reverse direction if hitting the boundary
        }
    }

    /**
     * Returns the shark's x position
     * 
     * @return
     */
    public double getPosition() {
        return this.position.x;
    }

    /**
     * Checks if the shark has biten the submarine
     * 
     * @param submarine
     * @return
     */
    public boolean checkCollision(Submarine submarine) {
        // Check if the shark's location overlaps with the submarine's location, a bite
        Point sharkPosition = this.position;
        Point submarinePosition = submarine.getPosition();
        
        if (sharkPosition.x < submarinePosition.x + 60 && 
            sharkPosition.x + 60 > submarinePosition.x && 
            sharkPosition.y < submarinePosition.y + 30 && 
            sharkPosition.y + 30 > submarinePosition.y) {
            if (!hasDamaged) { // Only damage if not already damaged
                hasDamaged = true;
                return true; // Collision detected
            }
        }
        return false; // No collision or already damaged
    }
    
    /**
     * Checks if the shark has been hit by a projectile
     * 
     * @param projectile
     * @return
     */
    public boolean checkHit(Projectile projectile) {
        Point sharkPosition = this.position;
        Point projectilePosition = projectile.position;

        return projectilePosition.x < sharkPosition.x + 60 && 
               projectilePosition.x + 10 > sharkPosition.x && 
               projectilePosition.y < sharkPosition.y + 30 && 
               projectilePosition.y + 5 > sharkPosition.y;
    }
}
