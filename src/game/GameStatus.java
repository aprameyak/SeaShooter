package game;

import java.awt.Graphics;

/**
 * This interface is used for GameStatus
 * It is used as anonymous class to 
 * display the status of the game
 */
public interface GameStatus {
	/**
	 * Displays the status on the game
	 * 
	 * @param status
	 * @param g
	 */
	 void gameStatus(String status, Graphics g);
}
