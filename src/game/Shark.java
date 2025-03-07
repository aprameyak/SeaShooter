package game;

import java.awt.Color;
import java.awt.Graphics;

public class Shark extends Polygon implements Enemy {
    private int sharkHealth = 100;
    private int sharkAttack = 10;  
    private boolean hasDamaged = false;

    @Override
    public void attack() {
        System.out.println("The shark attacks!");
    }
    public Shark(Point[] sharkPoints, Point sharkPosition, double sharkRotation) {
        super(sharkPoints, sharkPosition , sharkRotation);
    }

    private final Damageable takeDamage = (damage) -> sharkHealth -= damage;

    public void takesDamage(int damage) {
        takeDamage.applyDamage(damage);
    }


    public int getAttackDamage() {
        return sharkAttack;
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
    public void moveLeft() {
        this.position.x -= 0.2; //moves left
    }
    public boolean checkCollision(Submarine submarine) {
        // Check if the shark's location overlaps with the submarine's location
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
}