package game;

public class Shark extends Polygon implements Enemy {
    private int sharkHealth = 100;
    private int sharkAttack = 10;    

    @Override
    public void attack() {
        System.out.println("The shark attacks!");
    }
    public Shark(Point[] sharkPoints, Point sharkPosition, double sharkRotation) {
        super(sharkPoints, sharkPosition , sharkRotation);
    }

    private final Damageable takeDamage = (damage) -> sharkHealth -= damage;

    public void applyDamage(int damage) {
        takeDamage.applyDamage(damage);
    }


    public int getDamage() {
        return sharkHealth;
    }
}