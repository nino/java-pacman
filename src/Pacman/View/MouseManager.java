package Pacman.View;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Pacman.PacMan;

/**
 * This class handles the user input from the mouse and calls the corresponding methods of the controller.
 */
public class MouseManager implements MouseListener {
	
	/**
	 * Handles mouse events and calls {@link Pacman.Controller.GameController#clickPanelAction(int[])} at the respective position. 
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		int[] gridPosition = { arg0.getX(), arg0.getY() };
		PacMan.getController().clickPanelAction(gridPosition); 
	}
	
	/**
	 * Handles mouse events and calls {@link Pacman.Controller.GameController#clickPanelAction(int[])} at the respective position. 
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {
		int[] gridPosition = { arg0.getX(), arg0.getY() };
		PacMan.getController().clickPanelAction(gridPosition); 
	}
	
	/**
	 * Must be implemented. No reaction for this event.
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	/**
	 * Must be implemented. No reaction for this event.
	 */
	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	

	/**
	 * Must be implemented. No reaction for this event.
	 */
	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

}
