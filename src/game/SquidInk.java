package game;

import java.awt.Color;
import java.awt.Graphics;

public class SquidInk extends Projectile {
    private int damage;  // Damage value for the squid ink
    private int width = 10;  // Width of the squid ink (rectangle)
    private int height = 5;  // Height of the squid ink (rectangle)

    // Constructor for SquidInk, defining position, angle, and damage
    public SquidInk(Point position, double angle) {
        super(position, angle, 2, 10); // Speed 5, Damage 20 (can adjust as needed)
        this.damage = 10; // Set the damage value
    }

    // Get the damage value of the squid ink
    public int getDamage() {
        return damage;
    }

    // Paint the squid ink on the screen (draw it as a rectangle)
    @Override
    public void paint(Graphics brush) {
        brush.setColor(Color.MAGENTA); // Squid ink is purple/magenta

        // Draw the squid ink as a rectangle based on its current position and size
        brush.fillRect((int) position.x, (int) position.y, width, height);
    }

    // Move the squid ink projectile (moving left)
    public void move() {
        this.position.x -= 5; // Move left by 5 units each time
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
