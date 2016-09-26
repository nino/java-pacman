package Pacman.lib;

import java.io.Serializable;
import Pacman.Model.Map;
import Pacman.lib.Direction;

/**
 * Two dimensional vector
 * 
 * This class can be used to store absolute positions and relative motion.
 */
public class Vector implements Serializable {
	/**
	 * Generated by Eclipse
	 */
	private static final long serialVersionUID = -5253684877246610442L;
	
	/**
	 * x coordinate
	 */
	private double x;
	
	/**
	 * y coordinate
	 */
	private double y;

	/**
	 * Default constructor
	 * 
	 * Uses x and y coordinates as separate variables.
	 * 
	 * @param x
	 *            X value
	 * @param y
	 *            Y value
	 */
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Alternate constructor #1
	 * 
	 * Takes x and y as a double array
	 * 
	 * @param xy
	 *            The array
	 */
	public Vector(double[] xy) {
		this(xy[0], xy[1]);
	}


	/**
	 * Clone constructor
	 * 
	 * Clones a vector
	 */
	public Vector(Vector v) {
		this(v.x, v.y);
	}
	
	/**
	 * Creates a unit vector pointing in a given direction
	 * @param direction The direction in which the vector should point.
	 */
	public Vector(Direction direction) {
		this(0, 0);
		this.setByDirection(direction);
	}

	/**
	 * Makes the vector a unit vector pointing in a given direction
	 * 
	 * @param direction
	 *            The direction where the vector will point
	 */
	public void setByDirection(Direction direction) {
		switch (direction) {
		case LEFT:
			this.x = -1;
			this.y = 0;
			break;
		case RIGHT:
			this.x = 1;
			this.y = 0;
			break;
		case UP:
			this.x = 0;
			this.y = -1; // the y-axis of our coordinate system points in the
							// DOWN direction
			break;
		case DOWN:
			this.x = 0;
			this.y = 1;
			break;
		}
	}

	/**
	 * 
	 * @return The direction in which the vector points. Returns {@code null}
	 *         when vector is not normed or not parallel to X- or Y-axis.
	 */
	public Direction getDirection() {
		if (this.x == 0) {
			if (this.y == 1)
				return Direction.DOWN;
			if (this.y == -1)
				return Direction.UP;
		}

		if (this.y == 0) {
			if (this.x == 1)
				return Direction.RIGHT;
			if (this.x == -1)
				return Direction.LEFT;
		}
		return null;
	}

	/**
	 * @return The X coordinate
	 */
	public double getX() {
		return this.x;
	}

	/**
	 * @return The Y coordinate
	 */
	public double getY() {
		return this.y;
	}

	/**
	 * Sets {@link Vector#x}
	 */
	public void setX(double newX) {
		this.x = newX;
	}

	/**
	 * Sets {@link Vector#y}
	 */
	public void setY(double newY) {
		this.y = newY;
	}

	/**
	 * Add two vectors
	 * 
	 * @param o The vector that is to be added to {@code this}
	 */
	public Vector add(Vector o) {
		return (new Vector(this.x + o.x, this.y + o.y));
	}

	/**
	 * Subtract two vectors
	 * 
	 * @param o The vector that is to be subtracted from {@code this}
	 */
	public Vector subtract(Vector o) {
		return (new Vector(this.x - o.x, this.y - o.y));
	}

	/**
	 * Scales the vector, so the length changes, but not the direction.
	 */
	public Vector scale(double s) {
		return new Vector(this.x * s, this.y * s);
	}

	/**
	 * Scales the vector so the length is 1.
	 */
	public Vector normalize() {
		return this.scale(1.0 / this.abs());
	}

	/**
	 * Return absolute length of the vector.
	 */
	public double abs() {
		return Math.sqrt(this.x * this.x + this.y * this.y);
	}

	/**
	 * Calculates the distance between this vector and the vector {@code o}.
	 * @param o
	 * @return distance
	 */
	public double distance(Vector o) {
		return this.subtract(o).abs();
	}
	
	/**
	 * If this vector represents a point outside the map, it wraps around.
	 * So if a moving object leaves the map, it will appear again on the opposite site of the map.
	 * @return The resulting vector to the new position.
	 */
	public Vector constrainToMap() {
		Vector constrained = new Vector(this);
		if (Math.ceil(constrained.getX()) >= Map.mapSizeX) {
			constrained.setX(0);
		}
		if (Math.ceil(constrained.getY()) >= Map.mapSizeY) {
			constrained.setY(0);
		}
		if (constrained.getX() < 0) {
			constrained.setX(Map.mapSizeX - 1);
		}
		if (constrained.getY() < 0) {
			constrained.setY(Map.mapSizeY - 1);
		}

		return constrained;
	}

	/**
	 * Compares the vector with an Object.
	 * 
	 * @param obj
	 *            Object to compare the vector with
	 * @return true if obj is a Vector with same x and y values like this vector
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null)
			return false;
		if (obj.getClass() != this.getClass())
			return false;
		Vector objVec;
		objVec = (Vector) obj;
		if (objVec.getX() == this.x && objVec.getY() == this.y)
			return true;
		return false;
	}
}