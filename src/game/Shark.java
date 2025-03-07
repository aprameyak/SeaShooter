package game;

import java.awt.Color;
import java.awt.Graphics;

public class Shark extends Polygon implements Enemy {
    private int sharkHealth = 100;
    private int sharkAttack = 10;    

    @Override
    public void attack() {
        System.out.println("The shark attacks!");
    }
    public Shark(Point[] sharkPoints, Point sharkPosition, double sharkRotation) {
        super(sharkPoints, sharkPosition , sharkRotation);
    }

    private final Damageable takeDamage = (damage) -> sharkHealth -= damage;

    public void applyDamage(int damage) {
        takeDamage.applyDamage(damage);
    }


    public int getDamage() {
        return sharkHealth;
    }
    public void paint(Graphics brush) {
        Point[] points = this.getPoints();
        int[] xPoints = new int[points.length];
        int[] yPoints = new int[points.length];

        for (int i = 0; i < points.length; i++) {
            xPoints[i] = (int) points[i].x;
            yPoints[i] = (int) points[i].y;
        }

        brush.setColor(Color.RED); // Sharks are red
        brush.fillPolygon(xPoints, yPoints, points.length);
    }
}