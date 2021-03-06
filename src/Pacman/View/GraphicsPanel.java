package Pacman.View;

import Pacman.PacMan;
import Pacman.Model.Ghost;
import Pacman.Model.Map;
import Pacman.Model.MapBlock;
import Pacman.Model.Player;
import Pacman.lib.GameState;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
/**
 * The graphics panel. Shows game board and draws all game objects such as MapBlocks and GameCharacters. 
 */
public class GraphicsPanel extends JPanel {

	// user input managers
	/**
	 * Handles user input on keyboard.
	 */
	private KeyboardListener keyboardManager;
	
	/**
	 * Handles user input with mouse.
	 */
	private MouseManager    mouseManager;

	/**
	 * Generated by eclipse.
	 */
	private static final long serialVersionUID = 1320488970618481999L;

	/**
	 * Constructor.
	 */
	public GraphicsPanel() {
		setBackground(Color.black);

		keyboardManager = new KeyboardListener();
		mouseManager    = new MouseManager();

		addKeyListener(keyboardManager);
		addMouseListener(mouseManager);

	}

	/**
	 * Paints the game board and all the elements present in the game
	 */
	public void paint(Graphics g) {
		super.paintComponent(g);
		
		// paint standard-stuff independent of state
		GUI gui = PacMan.getGUI();
		MapBlock[][] mapBlocks = PacMan.getGame().getMap().getBlocks();
		for (int x = 0; x < Map.mapSizeX; x++) {
			for (int y = 0; y < Map.mapSizeY; y++) {
				g.drawImage(gui.getMapBlockImage(mapBlocks[x][y]), x * GUI.gridSize, y * GUI.gridSize, null);
			}
		}

		// paint state-specific stuff
		if (PacMan.getGame().getGameState() == GameState.EDITOR) {
			paintEditor(g);
		}
		else {
			paintGame(g);
		}
	}

	/** 
	 * Paints state specific objects when the game is running, paused or stopped, e.g. player and ghosts are painted
	 */
	private void paintGame(Graphics g) {
		GUI gui = PacMan.getGUI();
		Ghost[] ghosts = PacMan.getGame().getGhosts();
		for (int i = 0; i < ghosts.length; i++) {
			Ghost ghost = ghosts[i];
			int x = (int)(ghost.getPosition().getX() * GUI.gridSize);
			int y = (int)(ghost.getPosition().getY() * GUI.gridSize);
			if (ghost.getDead()) {
				g.drawImage(gui.getGhostImageDead(), x, y, null);
			}
			else if (PacMan.getGame().getSpecialItemMode() == false)
				g.drawImage(gui.getGhostImage()[ghost.getGhostType()], x, y, null);
			else
				g.drawImage(gui.getGhostImageSpecial(), x, y, null);
		}

		Player player = PacMan.getGame().getPlayer();
		int x = (int)(player.getPosition().getX() * GUI.gridSize);
		int y = (int)(player.getPosition().getY() * GUI.gridSize);
		
		AffineTransform transform = new AffineTransform();
		transform.translate(x, y); //position of image
		//rotate image so that pacman looks in the direction of moving
		transform.rotate(player.getDirection().getX(), player.getDirection().getY(), gui.getPlayerImage().getWidth()/2, gui.getPlayerImage().getHeight()/2);
		
		Graphics2D g2d = (Graphics2D) g; 
		g2d.drawImage(gui.getPlayerImage(),transform,null);

		// draw message (game over, greeting in the beginning, ...)
		g.setColor(Color.white);
		g.drawString(PacMan.getController().getMessage(), 30, 170);
	}

	/**
	 * Paints state specific objects when editor is active. Player and a ghost are painted at their respective spawn-point. 
	 */
	private void paintEditor(Graphics g) {
		Map map = PacMan.getGame().getMap();
		GUI gui = PacMan.getGUI();
		int x, y;

		// draw ghost at spawn point
		x = (int)map.getGhostSpawnVector().getX() * GUI.gridSize;
		y = (int)map.getGhostSpawnVector().getY() * GUI.gridSize;
		g.drawImage(gui.getGhostImage()[0], x, y, null);

		// draw player at spawn point
		x = (int)map.getPlayerSpawnVector().getX() * GUI.gridSize;
		y = (int)map.getPlayerSpawnVector().getY() * GUI.gridSize;
		g.drawImage(gui.getPlayerImage(), x, y, null);
	}

}
