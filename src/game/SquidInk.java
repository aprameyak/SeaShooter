package game;

import java.awt.Color;
import java.awt.Graphics;

public class SquidInk extends Projectile {
    private int damage;  // Damage value for the squid ink
    private int width = 10; 
    private int height = 5;  
    private int speed = 2;
   
    public SquidInk(Point position, double angle) {
        super(position, angle, 2, 10); // Speed, Damage 
        this.damage = 10; // Set the damage value
    }

    // Get the damage value of the squid ink
    public int getDamage() {
        return damage;
    }

    // Paint the squid ink on the screen (draw it as a rectangle)
    @Override
    public void paint(Graphics brush) {
        brush.setColor(Color.BLUE); // Squid ink is purple/magenta

        // Draw the squid ink
        brush.fillRect((int) position.x, (int) position.y, width, height);
    }

    // Move the squid ink projectile
    public void move() {
        this.position.x -= speed; 
    }

    // Check if the squid ink hits the submarine
    public boolean checkHit(Submarine target) {
        if (target instanceof Submarine) {
            Submarine submarine = (Submarine) target;

            // Check if the rectangle of the squid ink intersects with the submarine's rectangle
            return this.position.x < submarine.getPosition().x + 60 &&
                   this.position.x + width > submarine.getPosition().x &&
                   this.position.y < submarine.getPosition().y + 30 &&
                   this.position.y + height > submarine.getPosition().y;
        }
        return false;
    }

    // Apply damage when the squid ink hits the submarine
    public void onHit(Damageable target) {
        if (target instanceof Submarine) {
            Submarine submarine = (Submarine) target;
            submarine.applyDamage(this.getDamage());  // Apply damage to the submarine
        }
    }
}
