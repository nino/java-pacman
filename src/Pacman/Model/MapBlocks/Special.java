package Pacman.Model.MapBlocks;

import Pacman.Model.MapBlock;

/**
 * Represents special item on the map. When it's eaten by the player, for some time it is able to kill ghosts 
 * and score more point when eating dots.
 */
public final class Special extends MapBlock {

	/**
	 * Generated by eclipse
	 */
	private static final long serialVersionUID = -5243281327066647892L;

	/**
	 * to make this a singleton class
	 */
	protected static Special instance;

	/**
	 * @return Instance of this class. 
	 */
	public static Special getInstance() {
		if (instance == null) {
			instance = new Special();
		}
		return instance;
	}

	/**
	 * @return {@code false}
	 */
    @Override
    public boolean canCollide() {
        return false;
    }

    /**
	 * @return {@code true}
	 */
    @Override
    public boolean isEatable() {
        return true;
    }
}
