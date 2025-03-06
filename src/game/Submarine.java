package game;

public class Submarine extends Polygon{
    private final Point[] submarinePoints = {new Point(3.0, 0.0), new Point(3.0, 3.0), new Point(0.0, 3.0), new Point(0.0, 0.0)};
    private Point submarinePosition = new Point(0.0, 0.0);
    private double submarineRotation = 0.0;
    private int submarineHealth = 100;
    private int submarineAttack = 10;

    public Submarine(Point[] submarinePoints, Point submarinePosition, double submarineRotation) {
        super(submarinePoints, submarinePosition , submarineRotation);
    }

    public void changePosition(Point newPosition) {
        submarinePosition = newPosition;
    }

    public void changeRotation(double newRotation) {
        submarineRotation = newRotation;
    }

    public void isDamaged(int damage) {
        submarineHealth -= damage;
    }

    public int getDamage() {
        return submarineAttack;
    }
}