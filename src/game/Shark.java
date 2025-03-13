package game;

import java.awt.Color;
import java.awt.Graphics;

public class Shark extends Polygon implements Enemy {
    private int sharkHealth = 100;
    private final int SHARK_ATTACK = 15;  
    private boolean hasDamaged = false;
    private double speed = 0.6;
    private double verticalSpeed;

    @Override
    public void attack() {
        System.out.println("The shark attacks!");
    }

    public Shark(Point[] sharkPoints, Point sharkPosition, double sharkRotation) {
        super(sharkPoints, sharkPosition, sharkRotation);
        this.verticalSpeed = (Math.random() * 2 - 1) * speed * 0.5; 
    }

    private final Damageable takeDamage = (damage) -> sharkHealth -= damage;

    @Override
    public void takesDamage(int damage) {
        takeDamage.applyDamage(damage);
    }

    @Override
    public int getAttackDamage() {
        return SHARK_ATTACK;
    }

    public int getHealth() {
        return sharkHealth;
    }
    
    @Override
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

        brush.setColor(Color.GRAY); // Sharks are red
        brush.fillPolygon(xPoints, yPoints, points.length);
    }

    public void moveLeft() {
        this.position.x -= speed; //moves left
        this.position.y += verticalSpeed;//random angle
        if (this.position.y < 0 || this.position.y > 500) {
            verticalSpeed = -verticalSpeed; // Reverse direction if hitting the boundary
        }
    }

    public double getPosition() {
        return this.position.x;
    }

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
    
    public boolean checkHit(Projectile projectile) {
        Point sharkPosition = this.position;
        Point projectilePosition = projectile.position;

        return projectilePosition.x < sharkPosition.x + 60 && 
               projectilePosition.x + 10 > sharkPosition.x && 
               projectilePosition.y < sharkPosition.y + 30 && 
               projectilePosition.y + 5 > sharkPosition.y;
    }
}