package game;

interface Enemy {
    void attack();
    void takesDamage(int damage);
    int getAttackDamage();
    void changeSpeed(double speed);
}