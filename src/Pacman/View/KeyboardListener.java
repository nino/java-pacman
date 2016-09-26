package Pacman.View;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Pacman.PacMan;
import Pacman.lib.Direction;

/**
 * This class handles the user input from the keyboard and calls the corresponding methods of the controller.
 */
class KeyboardListener implements KeyListener {
	/**
	 * Constructor
	 */
	public KeyboardListener() {
	}

	/**
	 * When user presses an arrow key, this method calls the {@link Pacman.Controller.GameController#navigateAction(Direction)} method for the respective direction.
	 */
	@Override
	public void keyPressed(KeyEvent event) {
		switch(event.getKeyCode()) {
			case KeyEvent.VK_UP:
				PacMan.getController().navigateAction(Direction.UP);
				break;
			case KeyEvent.VK_DOWN:
				PacMan.getController().navigateAction(Direction.DOWN);
				break;
			case KeyEvent.VK_LEFT:
				PacMan.getController().navigateAction(Direction.LEFT);
				break;
			case KeyEvent.VK_RIGHT:
				PacMan.getController().navigateAction(Direction.RIGHT);
				break;
		}
	}

	/**
	 * Must be implemented. No reaction for this event.
	 */
	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	/**
	 * Must be implemented. No reaction for this event.
	 */
	@Override
	public void keyTyped(KeyEvent arg0) {
	}
}
