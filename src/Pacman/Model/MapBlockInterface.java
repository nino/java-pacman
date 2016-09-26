package Pacman.Model;

/**
 * Interface to define behaviour of MapBlock items
 */
interface MapBlockInterface {
    boolean canCollide();
    boolean isEatable();
}
