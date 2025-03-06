package game;

import java.awt.Color;
import java.awt.Graphics;

public class Submarine extends Polygon{
    private int submarineHealth = 100;
    private int submarineAttack = 10;

    public Submarine(Point[] submarinePoints, Point submarinePosition, double submarineRotation) {
        super(submarinePoints, submarinePosition , submarineRotation);
    }

    public void isDamaged(int damage) {
        submarineHealth -= damage;
    }

    public int getDamage() {
        return submarineAttack;
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
}