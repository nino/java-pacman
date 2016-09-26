package Pacman;

import Pacman.Controller.GameController;
import Pacman.Model.Game;
import Pacman.Model.MapBlocks.Special;
import Pacman.View.GUI;
import Pacman.lib.GameState;

/**
 * The main class.
 * This class initializes the model, view and controller.
 * If a condition is set, it can also only initialize the model.
 */
public class PacMan {
	/**
	 * The controller of the game
	 */
	private static GameController controller;

	/**
	 * The model
	 */
	private static Game game;

	/**
	 * The view
	 */
	private static GUI gui;

	/**
	 * Getter for the game controller object
	 *
	 * @return controller The controller object
	 */
	public static GameController getController() {
		return controller;
	}

	/**
	 * Getter for the game object
	 *
	 * @return game The game object
	 */
	public static Game getGame() {
		return game;
	}
	
	/**
	 * Setter for the game object
	 *
	 * @param newGame The new value of game
	 */
	public static void setGame(Game newGame) {
		game = newGame;
	}

	/**
	 * Getter for the GUI object
	 *
	 * @return gui The window
	 */
	public static GUI getGUI() {
		return gui;
	}

	/**
	 * Setter for the controller object
	 *
	 * @param newController The new value of controller
	 */
	public static void setController(GameController newController) {
		controller = newController;
	}

    public static void main(String[] args) {
		game = new Game(); // model
		game.setGameState(GameState.MENU);
		
		if(args.length > 0) {			
			game.getMap().setPlayerSpawn(new int[]{5,5});
			game.getMap().setGhostSpawn(new int[]{ 10,5 });
			game.getMap().setBlock(new int[]{7 , 5 }, Special.getInstance());
			game.init();
			game.start();
		}else{
			gui = new GUI(); // view
			controller = new GameController(game, gui); // controller
			gui.setVisible(true);
			Thread guiThread = new Thread(gui);
			guiThread.start();
		}
    }
}
