import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public interface Piece {
	
	/**
	 * Draws the piece on the given Graphics context
	 */
	void draw(Graphics g);

	/**
	 * Moves the piece if possible Freeze the piece if it cannot move down
	 * anymore
	 * 
	 * @param direction
	 *            the direction to move
	 */
	void move(Direction direction);
	
	/**
	 * Rotates the piece clockwise by 90 degrees
	 */
	void rotate();
	/**
	 * Returns the (row,col) grid coordinates occupied by this Piece
	 * 
	 * @return an Array of (row,col) Points
	 */
	Point[] getLocations();

	/**
	 * Return the color of this piece
	 */
	Color getColor();

	/**
	 * Returns if this piece can move in the given direction
	 * 
	 */
	public boolean canMove(Direction direction);
	
	/*
	 * Returns true if this piece can rotate 90 degrees clockwise
	 */
	public boolean canRotate();
}
