package game;
@FunctionalInterface

/**
 * This is the interface for Damageable
 * It represents an object that can 
 * be damaged
 */
public interface Damageable {
	
	/**
	 * Applies the damage based on damage
	 * 
	 * @param damage
	 */
	void applyDamage(int damage);
}

