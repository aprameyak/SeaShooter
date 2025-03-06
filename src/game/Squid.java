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

    public void isDamaged(int damage) {
        squidHealth -= damage;
    }

    public int getDamage() {
        return squidAttack;
    }
}