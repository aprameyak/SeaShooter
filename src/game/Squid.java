package game;

public class Squid extends Polygon implements Enemy {
    private Point[] squidPoints = {new Point(3.0, 0.0), new Point(3.0, 3.0), new Point(0.0, 3.0), new Point(0.0, 0.0)};
    private Point squidPosition = new Point(0.0, 0.0);
    private double squidRotation = 0.0;
    private int squidHealth = 100;
    private int squidAttack = 10;

    @Override
    public void attack() {
        System.out.println("The squid attacks!");
    }
    public Squid(Point[] squidPoints, Point squidPosition, double squidRotation) {
        super(squidPoints, squidPosition , squidRotation);
    }

    public void changePosition(Point newPosition) {
        squidPosition = newPosition;
    }

    public void changeRotation(double newRotation) {
        squidRotation = newRotation;
    }

    public void isDamaged(int damage) {
        squidHealth -= damage;
    }

    public int getDamage() {
        return squidAttack;
    }
}