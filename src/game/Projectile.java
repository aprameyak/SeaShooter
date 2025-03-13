package game;

import java.awt.Graphics;
import java.awt.Color;

/**
 * This class represents a Projectile
 * It is used for moving objects that 
 * inflict damage on collisions
 */
public abstract class Projectile {
    protected Point position;
    protected double angle; // Direction of the projectile
    protected int speed; 
    protected int damage; 

    /**
     * This constructor initializes a projectile by settings its fields
     * 
     * @param position
     * @param angle
     * @param speed
     * @param damage
     */
    public Projectile(Point position, double angle, int speed, int damage) {
        this.position = position;
        this.angle = angle;
        this.speed = speed;
        this.damage = damage;
    }

    /**
     * Paints the projectile 
     * 
     * @param brush
     */
    public abstract void paint(Graphics brush); 
    
    /**
     * Moves the projectile by changing its x and y position values
     */
    public void move() {
        // advance the projectile across 
        this.position.x += speed * Math.cos(Math.toRadians(angle));
        this.position.y += speed * Math.sin(Math.toRadians(angle));
    }

    /**
     * Checks if the projectile collides with a target
     * 
     * @param target
     * @return
     */
    public boolean checkCollision(Polygon target) {
        // check if the projectile collides with a target
        return target.contains(this.position);
    }
    
    /**
     * Returns the damage of the projectile
     * 
     * @return
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Returns the current position of the projectile
     * 
     * @return
     */
    public Point getPosition() {
        return position;
    }
}
