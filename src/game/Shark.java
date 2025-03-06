package game;

public class Shark extends Polygon implements Enemy {
    @Override
    public void attack() {
        System.out.println("The shark attacks!");
    }
    public Shark() {
        super(new Point[0], new Point(0.0, 0.0) , 0.0);
    }
}