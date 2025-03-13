package game;

/**
 * This interface is used for Enemy
 * It is the target for submarine
 * and it attacks submarine
 */
interface Enemy {

    /**
     * Attack submarine
     */
    void attack();

    /**
     * Takes damage based on damage
     * 
     * @param damage
     */
    void takesDamage(int damage);

    /**
     * Returns the attack damage
     * 
     * @return
     */
    int getAttackDamage();

    /**
     * Changes the speed to speed
     * 
     * @param speed
     */
    void changeSpeed(double speed);
}
