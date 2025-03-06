package game;

public class Shark extends Polygon implements Enemy {
    private Point[] sharkPoints = {new Point(3.0, 0.0), new Point(3.0, 3.0), new Point(0.0, 3.0), new Point(0.0, 0.0)};
    private Point sharkPosition = new Point(0.0, 0.0);
    private double sharkRotation = 0.0;
    private int sharkHealth = 100;
    private int sharkAttack = 10;    

    @Override
    public void attack() {
        System.out.println("The shark attacks!");
    }
    public Shark(Point[] sharkPoints, Point sharkPosition, double sharkRotation) {
        super(sharkPoints, sharkPosition , sharkRotation);
    }

    public void changePosition(Point newPosition) {
        sharkPosition = newPosition;
    }

    public void changeRotation(double newRotation) {
        sharkRotation = newRotation;
    }

    public void isDamaged(int damage) {
        sharkHealth -= damage;
    }

    public int getDamage() {
        return sharkHealth;
    }
}