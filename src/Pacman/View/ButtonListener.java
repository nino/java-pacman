package Pacman.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Pacman.PacMan;
import Pacman.lib.EditorTool;
import Pacman.lib.GameState;

/**
 * This class handles the button events and calls the corresponding methods of the controller.
 */
class ButtonListener implements ActionListener {
	/**
	 * To store a reference to the {@link GUI} instance.
	 */
	private GUI gui;

	/**
	 * Constructor
	 * @param gui The GUI. 
	 */
	public ButtonListener(GUI gui) {
		this.gui = gui;
	}

	/**
	 * Handles an event.
	 * @param event The event.
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == gui.getStartButton()) {
			if(PacMan.getGame().getGameState() == GameState.GAME_RUNNING)
				PacMan.getController().pauseAction();
			else if (PacMan.getGame().getGameState() == GameState.GAME_FINISHED) {
				PacMan.getController().stopAction();
				PacMan.getController().startAction();
			}
			else
				PacMan.getController().startAction();
			return;
		}
		if (event.getSource() == gui.getStopButton()) {
			PacMan.getController().stopAction();
			return;
		}
		if (event.getSource() == gui.getEditorButton()) {
			PacMan.getController().toggleEditorAction();
			return;
		}
		if (event.getSource() == gui.getToolButtonDot()) {
			PacMan.getController().setToolAction(EditorTool.DOT);
			return;
		}
		if (event.getSource() == gui.getToolButtonSpecial()) {
			PacMan.getController().setToolAction(EditorTool.SPECIAL);
			return;
		}
		if (event.getSource() == gui.getToolButtonWall()) {
			PacMan.getController().setToolAction(EditorTool.WALL);
			return;
		}
		if (event.getSource() == gui.getToolButtonEmpty()) {
			PacMan.getController().setToolAction(EditorTool.EMPTY);
			return;
		}
		if (event.getSource() == gui.getToolButtonGhostSpawn()) {
			PacMan.getController().setToolAction(EditorTool.GHOST_SPAWN);
			return;
		}
		if (event.getSource() == gui.getToolButtonPlayerSpawn()) {
			PacMan.getController().setToolAction(EditorTool.PLAYER_SPAWN);
			return;
		}
		if (event.getSource() == gui.getSaveButton()) {
			switch(PacMan.getGame().getGameState()) {
				case EDITOR:
					PacMan.getController().saveLevelAction();
					break;
				case GAME_PAUSED:
				case MENU:
					PacMan.getController().saveGameAction();
					break;
				default:
					break;
			}
			return;
		}
		if (event.getSource() == gui.getLoadButton()) {
			switch(PacMan.getGame().getGameState()) {
				case EDITOR:
					PacMan.getController().loadLevelAction();
					break;
				case GAME_PAUSED:
				case MENU:
					PacMan.getController().loadGameAction();
					break;
				default:
					break;
			
			}
			return;
		}
	}

}
