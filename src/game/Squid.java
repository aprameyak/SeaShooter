package game;

public class Squid extends Polygon implements Enemy {
    private int squidHealth = 100;
    private int squidAttack = 10;

    @Override
    public void attack() {
        System.out.println("The squid attacks!");
    }
    public Squid(Point[] squidPoints, Point squidPosition, double squidRotation) {
        super(squidPoints, squidPosition , squidRotation);
    }

    private final Damageable takeDamage = (damage) -> squidHealth -= damage;

    public void applyDamage(int damage) {
        takeDamage.applyDamage(damage);
    }

    public int getDamage() {
        return squidAttack;
    }
}