package Pacman.Controller;
import Pacman.lib.Direction;
import Pacman.lib.EditorTool;

/**
 * Interface for the controller. Defines some functions for the controller
 */
public interface ControllerInterface {
	// Actions

	/**
	 * See {@link GameController#saveGameAction()}
	 */
	public void saveGameAction();

	/**
	 * See {@link GameController#loadGameAction()}
	 */
	public void loadGameAction();

	/**
	 * See {@link GameController#saveLevelAction()}
	 */
	public void saveLevelAction();

	/**
	 * See {@link GameController#loadLevelAction()}
	 */
	public void loadLevelAction();

	/**
	 * See {@link GameController#startAction()}
	 */
	public void startAction();

	/**
	 * See {@link GameController#stopAction()}
	 */
	public void stopAction();

	/**
	 * See {@link GameController#clickPanelAction(int[])}
	 */
	public void clickPanelAction(int[] relativePosition);

	/**
	 * See {@link GameController#clickPanelAction(int[])}
	 */
	public void navigateAction(Direction direction);
	
	/**
	 * See {@link GameController#toggleEditorAction()}
	 */
	public void toggleEditorAction();
	
	/**
	 * See {@link GameController#setToolAction(EditorTool)}
	 */
	public void setToolAction(EditorTool tool);
	
	/**
	 * See {@link GameController#getMessage()}
	 */
	public String getMessage();
	
	/**
	 * See {@link GameController#pauseAction()}
	 */
	public void pauseAction();
}
