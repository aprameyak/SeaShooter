package game;

import java.awt.Color;
import java.awt.Graphics;

public class SquidInk extends Projectile {
    private final int INK_WIDTH = 10; 
    private final int INK_HEIGHT = 5;  
    private final int INK_SPEED = 2;
    private int damage = 5;
   
    public SquidInk(Point position, double angle) {
        super(position, angle, 2, 10); // Speed, Damage 
        this.damage = 10; // Set the damage value
    }

    // Get the damage value of the squid ink
    @Override   
    public int getDamage() {
        return damage;
    }

    // Paint the squid ink on the screen (draw it as a rectangle)
    @Override
    public void paint(Graphics brush) {
        brush.setColor(Color.BLUE); // Squid ink is purple/magenta

        // Draw the squid ink
        brush.fillRect((int) position.x, (int) position.y, INK_WIDTH, INK_HEIGHT);
    }

    // Move the squid ink projectile
    @Override
    public void move() {
        this.position.x -= INK_SPEED; 
    }

    // Check if the squid ink hits the submarine
    public boolean checkHit(Submarine target) {
        if (target instanceof Submarine) {
            Submarine submarine = (Submarine) target;

            // Check if the rectangle of the squid ink intersects with the submarine's rectangle
            return this.position.x < submarine.getPosition().x + 60 &&
                   this.position.x + INK_WIDTH > submarine.getPosition().x &&
                   this.position.y < submarine.getPosition().y + 30 &&
                   this.position.y + INK_HEIGHT > submarine.getPosition().y;
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
