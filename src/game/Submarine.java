package game;

import java.awt.Color;
import java.awt.Graphics;

/** 
 * This class represents a submarine in the SeaShooter game
 * It extends the Polygon class and aims to defeat enemies
*/
public class Submarine extends Polygon{
    private int submarineHealth = 100;
    private final int SUBMARINE_ATTACK = 10;
    private final int MOVE_AMOUNT = 1;
    private final int ROTATE_AMOUNT = 1;
    
    /**
     * Submarine constructor to initalize using super constructor
     * 
     * @param submarinePoints
     * @param submarinePosition
     * @param submarineRotation
     */
    public Submarine(Point[] submarinePoints, Point submarinePosition, double submarineRotation) {
        super(submarinePoints, submarinePosition , submarineRotation);
    }

    private final Damageable takeDamage = (damage) -> submarineHealth -= damage;

    /**
     * Applies damage to the submarine's health
     * 
     * @param damage
     */
    public void applyDamage(int damage) {
        takeDamage.applyDamage(damage);
    }

    /**
     * Returns the value of the submarine's attack
     * 
     * @return
     */
    public int getDamage() {
        return SUBMARINE_ATTACK;
    }

    /**
     * Returns the value of the submarine's current health
     * 
     * @return
     */
    public int getHealth() {
        return submarineHealth;
    }

    /**
     * Paints the submarine based on its points
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

        brush.setColor(Color.WHITE);
        brush.fillPolygon(xPoints, yPoints, points.length);
    }

    /**
     * Moves the submarine based on the given values
     * 
     * @param forward
     * @param backward
     * @param right
     * @param left
     */
    public void move(boolean forward,boolean backward, boolean right, boolean left) {
        if(forward) {
            this.position.x += MOVE_AMOUNT * Math.cos(Math.toRadians(this.rotation));
            this.position.y += MOVE_AMOUNT * Math.sin(Math.toRadians(this.rotation));
        }
        if(backward) {
            this.position.x -= MOVE_AMOUNT * Math.cos(Math.toRadians(this.rotation));
            this.position.y -= MOVE_AMOUNT * Math.sin(Math.toRadians(this.rotation));
        }
        if(right) {
            this.rotation += ROTATE_AMOUNT;
        }
        if(left) {
            this.rotation -= ROTATE_AMOUNT;
        }
    }

    /**
     * Returns the current position of the submarine
     * 
     * @return
     */
    public Point getPosition() {
        return this.position;
    }

    /**
     * Returns the current rotation of the submarine
     * 
     * @return
     */
    public double getRotation() {
    	return this.rotation;
    }
}
