package game;

public class Squid extends Polygon implements Enemy {
    @Override
    public void attack() {
        System.out.println("The squid attacks!");
    }
    public Squid() {
        super(new Point[0], new Point(0.0, 0.0) , 0.0);
    }
}