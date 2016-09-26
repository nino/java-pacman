package Pacman.Model;

import java.util.LinkedList;

import Pacman.PacMan;
import Pacman.Model.MapBlocks.Empty;
import Pacman.lib.Direction;
import Pacman.lib.Vector;

/**
 * The player class. It contains information about the player's position and motion states, among others.
 */
public final class Player extends GameCharacter {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8763302004734240183L;

	/**
	 * Default constructor. This method initializes the player's thread (see {@link GameCharacter#thread})
	 */
	public Player() {
		thread = new Thread(this);
		active = false;
		nextTurn = null;
		direction = new Vector(1.0, 0.0);
		speed = 500;
	}

	/**
	 * The {@code run} method for the {@link Runnable} interface.
	 */
	@Override
	public void run() {
		while (this.getActive()) {
			this.step();
			try {
				Thread.sleep(1000 / this.speed);
			} catch (Exception e) {
				System.err.println("Player thread interrupted");
			}
		}
	}

	/**
	 * @return the nextTurn
	 */
	public Direction getNextTurn() {
		return nextTurn;
	}

	/**
	 * @param nextTurn
	 *            the nextTurn to set
	 */
	public void setNextTurn(Direction nextTurn) {
		this.nextTurn = nextTurn;
	}

	/**
	 * kill player
	 */
	void die() {
		PacMan.getGame().gameOver(); // no extra-lives
	}

	/**
	 * Sets active to true and signals the thread to start.
	 */
	@Override void startCharacter() {
		active = true;
		thread = new Thread(this);
		thread.start();
	}

	/**
	 * Moves the player and eats dots if necessary
	 */
	@Override
	boolean step() {
		// If direction has changed, the Player goes in the original direction
		// until he reaches the center of the mapblock and then goes the rest in
		// the new direction.
		LinkedList<Vector> result = this.turn(this.getPosition(), nextTurn);
		Vector newPosition = result.get(0).constrainToMap();
		this.direction = result.get(1);
		if (result.get(2) != null)
			this.nextTurn = result.get(2).getDirection();
	
		double offset = newPosition.distance(this.position); // distance to go
																// in the
																// original
																// direction
	
		// if Player reaches center of MapBlock, check collision of next block
		if (this.mapCollisionNextBlock(newPosition, this.direction))
			return false;
	
		newPosition = new Vector(newPosition.add(
				direction.scale(stepSize - offset)).constrainToMap());
	
		// Map collision
		if (this.mapCollision(newPosition))
			return false;
	
		// Ghost collision
		if (this.ghostCollision())
			return false;
	
		this.position = newPosition;
	
		postStepEffects();
		if (PacMan.getGame().getMap().getDotsLeft() == 0) {
			PacMan.getGame().gameOver();
			return false;
		}
	
		return true;
	}

	/**
	 * To check if there will be a collision on the next step, a proxyPlayer is generated to simulate the step
	 * without the actual player actually moving.
	 */
	@Override
	boolean simulateStep(Vector position, Vector newDirection) {
		// simulate step for collision control
		Vector newPosition = PacMan.getGame().getMap()
				.getMapBlockCentre(position, this.direction.getDirection()); // set
																				// proxyPlayer
																				// to
																				// center
																				// of
																				// MapBlock
																				// to
																				// avoid
																				// problems
																				// with
																				// passMapBlockCentre()
																				// called
																				// in
																				// proxyPlayer.step().
																				// Not
																				// needed
																				// with
																				// current
																				// implementation
																				// of
																				// passMapBlockCentre().
		Player proxyPlayer = new Player();
		proxyPlayer.setPosition(newPosition);
		proxyPlayer.setDirection(newDirection);
	
		return proxyPlayer.step();
	}

	/**
	 * Sets active to false so the thread doesn't keep moving the player
	 */
	@Override void stop() {
		active = false; // thread will stop itself, once it notices
						// the changed state
	}

	/**
	 * Checks if the player is currently colliding with another
	 * game character
	 */
	private boolean collision(GameCharacter collider) {
		return (this.position.distance(collider.getPosition()) < 0.8);
	}

	/**
	 * Eating dots and adding points to {@link Game#score}.
	 */
	private void postStepEffects() {
		// eat & check if special item activates
		Map map = PacMan.getGame().getMap();
		Game game = PacMan.getGame();
		if (this == game.getPlayer()) { // in case we're the proxyPlayer
			if (map.special(position)) {
				PacMan.getGame().enableSpecialItem();
			}
			if (map.eat(position)) {
				map.setBlock(map.getGridPosition(position), Empty.getInstance());
				map.computeDotsLeft();
				if (game.getSpecialItemMode())
					game.addScore(4);
				else
					game.addScore(1);
				System.out.println("New game score: " + game.getScore());
			}
		}
	}

	/**
	 * Checks if the player is currently colliding with a ghost.
	 * If that is the case and special item mode is active,
	 * the ghost dies. If they collide and special mode is inactive,
	 * the player dies and the game is over.
	 */
	private boolean ghostCollision() {
		// Ghost collision
		if (this == PacMan.getGame().getPlayer()) { // only if this is the
													// Player which the user
													// controls. Otherwise this
													// is a virtual Player for
													// collision control that
													// should not interact with
													// ghosts.
													
			Ghost[] ghosts = PacMan.getGame().getGhosts();
			for (int i = 0; i < PacMan.getGame().getGhosts().length; i++) {
				if ((ghosts[i].getDead() == false) && this.collision(ghosts[i])) {
					if (PacMan.getGame().getSpecialItemMode()) {
						PacMan.getGame().getGhosts()[i].die();
						System.out.println("Ghost " + i + " has died.");
					} else {
						this.die();
					}
					return true;
				}
			}
		}
		return false;
	}

}
