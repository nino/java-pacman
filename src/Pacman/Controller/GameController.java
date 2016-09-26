package Pacman.Controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import Pacman.PacMan;
import Pacman.Model.Game;
import Pacman.Model.Map;
import Pacman.lib.GameState;
import Pacman.lib.EditorTool;
import Pacman.lib.Direction;
import Pacman.lib.DialogType;
import Pacman.Model.MapBlocks.*;
import Pacman.View.GUI;

/**
 * The controller part of the model-view-controller-system. This class communicates with the model and the view to manage the game.
 */
public class GameController implements ControllerInterface {
	// global variables
	
	/**
	 * The game (model) object
	 */
	private Game game;

	/**
	 * When the map-editor is active, the controller needs to know the active tool to manipulate map-blocks
	 */
	private EditorTool activeTool;

	/**
	 * The class to manage the main window
	 */
	private GUI gui;

	/**
	 * Initialize model, view with objects that are provided; default value for activeTool
	 *
	 * @param game The provided game object
	 * @param gui  The provided gui object
	 */
	public GameController(Game game, GUI gui) {
		this.game = game;
		this.activeTool = EditorTool.DOT;
		this.gui = gui;
	}
	
	
	/**
	 * This method is triggered when the user clicks the "Save Level" button in the main window.
	 * It then saves the level ({@link Map})
	 */
	public void saveLevelAction() {
		if(game.getGameState() != GameState.EDITOR) {
			gui.alert("Speichern zur Zeit nicht möglich!", "Hinweis");
			return;
		}
		
		String filename = gui.getFilename(DialogType.SAVE_MAP);
		if(filename == "") return;
		
		try {
			OutputStream os = new FileOutputStream(filename);
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(game.getMap());
			oos.close ();
		}
			catch(java.io.FileNotFoundException e) {
				gui.alert("Die Datei wurde nicht gefunden.","Fehler");
				System.err.println(e);
			}
			catch (IOException e) {
				gui.alert("Es ist ein Fehler beim Speichern des Levels aufgetreten.", "Fehler");
				System.err.println(e);
			}	
	}
	
	/**
	 * This method is triggered when the user clicks the "Save Game" button in the main window.
	 * It then saves the {@link Game}
	 */
	public void saveGameAction() {
		if(game.getGameState() != GameState.GAME_PAUSED && game.getGameState() != GameState.MENU) {
			gui.alert("Speichern zur Zeit nicht möglich!", "Hinweis");
			return;
		}
		String filename = gui.getFilename(DialogType.SAVE_GAME);
		if(filename == "") return;
		
		try {
			OutputStream os = new FileOutputStream(filename);
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(game);
			oos.close ();
		}
			catch(java.io.FileNotFoundException e) {
				gui.alert("Die Datei wurde nicht gefunden.","Fehler");
				System.err.println(e);
			}
			catch (IOException e) {
				gui.alert("Es ist ein Fehler beim Speichern des Spielstandes aufgetreten.", "Fehler");
				System.err.println(e);
			}	
	}

	/**
	 * This method is triggered when the user clicks the "Load Level" button in the main window.
	 * It then loads the level ({@link Map})
	 */
	public void loadLevelAction() {
		if(game.getGameState() != GameState.EDITOR) {
			gui.alert("Laden zur Zeit nicht möglich!", "Hinweis");
			return;
		}
		
		String filename = gui.getFilename(DialogType.OPEN_MAP);
		if(filename == "") return;
		
		try {
			InputStream is = new FileInputStream(filename );
			ObjectInputStream ois =	new ObjectInputStream(is);
			Map map = (Map) ois.readObject ();
			ois.close();
			game.setMap(map);
		}
			catch(java.io.InvalidClassException e) {
				gui.alert("Das Level konnte nicht geladen werden. Möglicherweise ist die Datei veraltet.", "Fehler");
				System.err.println(e);
			}
			catch(java.io.FileNotFoundException e) {
				gui.alert("Die Datei wurde nicht gefunden.","Fehler");
				System.err.println(e);
			}
			catch (Exception e) {
				gui.alert("Es ist ein Fehler beim Laden des Levels aufgetreten.", "Fehler");
				System.err.println(e);
			}
	}
	
	/**
	 * This method is triggered when the user clicks the "Load Level" button in the main window.
	 * It then loads the level ({@link Map})
	 */
	public void loadGameAction() {
		if(game.getGameState() != GameState.GAME_PAUSED && game.getGameState() != GameState.MENU) {
			gui.alert("Laden zur Zeit nicht möglich!", "Hinweis");
			return;
		}
		String filename = gui.getFilename(DialogType.OPEN_GAME);
		if(filename == "") return;
		
		try {
			InputStream is = new FileInputStream(filename );
			ObjectInputStream ois =	new ObjectInputStream(is);
			Game newGame = (Game) ois.readObject ();
			ois.close();
			PacMan.setGame(newGame);
			this.game = PacMan.getGame();
		}
			catch(java.io.InvalidClassException e) {
				gui.alert("Der Spielstand konnte nicht geladen werden. Möglicherweise ist die Datei veraltet.", "Fehler");
				System.err.println(e);
			}
			catch(java.io.FileNotFoundException e) {
				gui.alert("Die Datei wurde nicht gefunden.","Fehler");
				System.err.println(e);
			}
			catch (Exception e) {
				gui.alert("Es ist ein Fehler beim Laden des Spielstandes aufgetreten.", "Fehler");
				System.err.println(e);
			}		
	}

	/**
	 * This method is triggered when the user clicks the "Start" button in the main window.
	 * It starts the game.
	 */
	public void startAction() {
		game.start();
	}

	/**
	 * This method is triggered when the user clicks the "Pause" button in the main window.
	 * It pauses the game.
	 */
	public void pauseAction() {
		game.stop();
	}
	
	/**
	 * This method is triggered when the user clicks the "Stop" button in the main window.
	 * It stops (ends) the game and resets the board.
	 */
	public void stopAction() {
		game.stop();
		game.init();
		PacMan.getGame().setGameState(GameState.MENU);
	}

	/**
	 * This method is triggered when the user clicks the "Editor" button in the main window.
	 * The the map-editor-mode is toggled on and off.
	 */
	public void toggleEditorAction() {
		game.setOriginalMap(null);
		game.init();
		if (game.getGameState() == GameState.EDITOR) {
			PacMan.getGame().setGameState(GameState.MENU);
		}
		else {
			PacMan.getGame().setGameState(GameState.EDITOR);
		}
	}

	/**
	 * This method is triggered when the user clicks one of the tool buttons, while the map editor is active.
	 * The activeTool instance variable is set.
	 */
	public void setToolAction(EditorTool tool) {
		activeTool = tool;
	}

	/**
	 * This method is triggered when the user clicks inside the graphicspanel.
	 * If editor-mode is active, the mapblocks are manipulated.
	 */
	public void clickPanelAction(int[] relativePosition) {
		if (game.getGameState() == GameState.EDITOR) {
			int[] gridPosition = new int[2];
			gridPosition[0] = relativePosition[0] / GUI.gridSize;
			gridPosition[1] = relativePosition[1] / GUI.gridSize;
			switch (activeTool) {
				case DOT:
					game.getMap().setBlock(gridPosition, Dot.getInstance());
					break;
				case SPECIAL:
					game.getMap().setBlock(gridPosition, Special.getInstance());
					break;
				case WALL:
					game.getMap().setBlock(gridPosition, Wall.getInstance());
					break;
				case EMPTY:
					game.getMap().setBlock(gridPosition, Empty.getInstance());
					break;
				case GHOST_SPAWN:
					game.getMap().setGhostSpawn(gridPosition);
					game.init();
					break;
				case PLAYER_SPAWN:
					game.getMap().setPlayerSpawn(gridPosition);
					game.init();
					break;
			}
		}
	}


	
	/**
	 * Is called by GUI when user wants to change the Player's direction.
	 * @param direction The direction
	 */
	public void navigateAction(Direction direction) {
		if(game.getGameState() == GameState.GAME_RUNNING) {
			game.getPlayer().setNextTurn(direction);
		}
	}

	/**
	 * This message will be displayed as text in the graphics panel.
	 * It is used for game-over messages and displaying the score at the end.
	 */
	public String getMessage() {
		Game game = PacMan.getGame();
		if (game.getGameState() == GameState.GAME_FINISHED) {
			if (game.getMap().getDotsLeft() == 0)
				return "You won! Final Score: " + game.getScore();
			else
				return "Game Over. Final Score: " + game.getScore();
		}
		else return "";
	}
	

}
