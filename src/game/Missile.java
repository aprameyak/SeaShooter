package game;

import java.awt.Graphics;
import java.awt.Color;

public class Missile extends Projectile {

    public Missile(Point position, double angle) {
        super(position, angle, 5, 20); // Speed 5, Damage 10 
    }

    @Override
    public void paint(Graphics brush) {
        brush.setColor(Color.RED); // red
        brush.fillRect((int) position.x, (int) position.y, 10, 5); 
    }
}
