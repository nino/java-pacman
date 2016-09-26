package Pacman.Model;

import Pacman.lib.Vector;

/**
 * Interface that specifies collision behaviour of moving game characters. 
 *
 */
public interface GameCharacterInterface {	
	/**
	 * Getter method for Position.
	 * 
	 * @return position Vector
	 */
	public Vector getPosition();
	
	/**
	 * Getter for {@link GameCharacter#speed}
	 *
	 * @return the speed
	 */
	public double getSpeed();
}
