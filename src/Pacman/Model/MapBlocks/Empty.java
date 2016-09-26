package Pacman.Model.MapBlocks;

import Pacman.Model.MapBlock;

/**
 * Represents empty space on the map.
 */
public final class Empty extends MapBlock {
	/**
	 * Generated by eclipse
	 */
	private static final long serialVersionUID = -2773161059564929044L;
	
	/**
	 * to make this a singleton class
	 */
	protected static Empty instance;

	/**
	 * @return Instance of this class. 
	 */
	public static Empty getInstance() {
		if (instance == null) {
			instance = new Empty();
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
	 * @return {@code false}
	 */
	@Override
		public boolean isEatable() {
			return false;
		}
}
