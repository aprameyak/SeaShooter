package game;

import java.awt.Graphics;
import java.awt.Color;

public abstract class Projectile {
    protected Point position;
    protected double angle; // Direction of the projectile
    protected int speed; 
    protected int damage; 

    public Projectile(Point position, double angle, int speed, int damage) {
        this.position = position;
        this.angle = angle;
        this.speed = speed;
        this.damage = damage;
    }

    public abstract void paint(Graphics brush); 
    
    public void move() {
        // advance the projectile across 
        this.position.x += speed * Math.cos(Math.toRadians(angle));
        this.position.y += speed * Math.sin(Math.toRadians(angle));
    }

    public boolean checkCollision(Polygon target) {
        // check if the projectile collides with a target
        return target.contains(this.position);
    }
    public int getDamage() {
        return damage;
    }

    public Point getPosition() {
        return position;
    }
}
