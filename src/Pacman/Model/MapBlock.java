package Pacman.Model;

import java.io.Serializable;

/**
 * MapBlock class. For use in the map object. 
 * I think here is a good place to mention, that all mapblocks (i.e. instances of this class's subclasses) should be used as
 * singleton objects to save memory.
 * 
 * subclasses override the collision() and eat() methods.
 */
public abstract class MapBlock implements MapBlockInterface, Serializable {

    /**
	 * Generated by eclipse
	 */
	private static final long serialVersionUID = 2708862156608964881L;

	/**
     * Returns {@code true} if the block is a collision object.
     * @return {@code false} as default
     */
    public boolean canCollide() {
        return false;
    }

    /**
     * Returns {@code true} if the Pacman-Man can eat this block type.
     * @return {@code false} as default
     */
    public boolean isEatable() {
        return false;
    }
}
