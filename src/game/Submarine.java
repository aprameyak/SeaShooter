package game;

import java.awt.Color;
import java.awt.Graphics;

public class Submarine extends Polygon{
    private int submarineHealth = 100;
    private final int SUBMARINE_ATTACK = 10;
    public final int MOVE_AMOUNT = 1;
    public final int ROTATE_AMOUNT = 1;
    
    public Submarine(Point[] submarinePoints, Point submarinePosition, double submarineRotation) {
        super(submarinePoints, submarinePosition , submarineRotation);
    }

    private final Damageable takeDamage = (damage) -> submarineHealth -= damage;

    public void applyDamage(int damage) {
        takeDamage.applyDamage(damage);
    }

    public int getDamage() {
        return SUBMARINE_ATTACK;
    }

    public int getHealth() {
        return submarineHealth;
    }

    public void paint(Graphics brush) {
        Point[] points = this.getPoints();
        int[] xPoints = new int[points.length];
        int[] yPoints = new int[points.length];

        for (int i = 0; i < points.length; i++) {
            xPoints[i] = (int) points[i].x;
            yPoints[i] = (int) points[i].y;
        }

        brush.setColor(Color.MAGENTA);
        brush.fillPolygon(xPoints, yPoints, points.length);
    }

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
    public Point getPosition() {
        return this.position;
    }
    public double getRotation() {
    	return this.rotation;
    }
}