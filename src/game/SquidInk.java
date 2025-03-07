package game;

import java.awt.Color;
import java.awt.Graphics;

public class SquidInk extends Projectile {

	public SquidInk(Point position, double angle) {
        super(position, angle, 5, 20); // Speed 5, Damage 10 
    }

    @Override
    public void paint(Graphics brush) {
        brush.setColor(Color.BLUE); // blue
        brush.fillRect((int) position.x, (int) position.y, 10, 5); 
    }
    public int getDamage() {
        return damage;
    }

}
