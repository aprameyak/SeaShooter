package game;

import java.awt.Color;
import java.awt.Graphics;

public class Squid extends Polygon implements Enemy {
    private int squidHealth = 100;
    private int squidAttack = 10;

    @Override
    public void attack() {
        System.out.println("The squid attacks!");
    }
    public Squid(Point[] squidPoints, Point squidPosition, double squidRotation) {
        super(squidPoints, squidPosition , squidRotation);
    }

    private final Damageable takeDamage = (damage) -> squidHealth -= damage;

    public void applyDamage(int damage) {
        takeDamage.applyDamage(damage);
    }

    public int getDamage() {
        return squidAttack;
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
}