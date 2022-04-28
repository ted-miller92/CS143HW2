import java.awt.Color;
import java.awt.Graphics;

/**
 * One Square on our Tetris Grid or one square in our Tetris game piece
 * 
 * @author CSC 143
 */
public class Square {
	private Grid grid; // the environment where this Square is

	private int row, col; // the grid location of this Square

	private boolean ableToMove; // true if this Square can move

	private Color color; // the color of this Square

	public enum RotationDirection {
		RIGHTandDOWN, DOWNandLEFT, LEFTandUP, UPandRIGHT
	}

	// possible move directions are defined by the Game class

	// dimensions of a Square
	public static final int WIDTH = 20;

	public static final int HEIGHT = 20;

	/**
	 * Creates a square
	 * 
	 * @param g
	 *            the Grid for this Square
	 * @param row
	 *            the row of this Square in the Grid
	 * @param col
	 *            the column of this Square in the Grid
	 * @param c
	 *            the Color of this Square
	 * @param mobile
	 *            true if this Square can move
	 * 
	 * @throws IllegalArgumentException
	 *             if row and col not within the Grid
	 */
	public Square(Grid g, int row, int col, Color c, boolean mobile) {
		if (row < 0 || row > Grid.HEIGHT - 1)
			throw new IllegalArgumentException("Invalid row =" + row);
		if (col < 0 || col > Grid.WIDTH - 1)
			throw new IllegalArgumentException("Invalid column  = " + col);

		// initialize instance variables
		grid = g;
		this.row = row;
		this.col = col;
		color = c;
		ableToMove = mobile;
	}

	/**
	 * Returns the row for this Square
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Returns the column for this Square
	 */
	public int getCol() {
		return col;
	}

	/**
	 * Returns true if this Square can move 1 spot in direction d
	 * 
	 * @param direction
	 *            the direction to test for possible move
	 */
	public boolean canMove(Direction direction) {
		if (!ableToMove)
			return false;

		boolean move = true;
		// if the given direction is blocked, we can't move
		// remember to check the edges of the grid
		
		switch (direction) {
		case UP:
			if (row == (Grid.HEIGHT + 1) || grid.isSet(row - 1, col))
				move = true;
			break;
		case DOWN:
			if (row == (Grid.HEIGHT - 1) || grid.isSet(row + 1, col))
				move = false;
			break;
		case DROP:
			if (row == (Grid.HEIGHT - 1) || grid.isSet(row + 1, col))
				move = false;
			break;
		case LEFT:
			if (col == 0 || grid.isSet(row,  col - 1))
				move = false;
			break;
		case RIGHT:
			if (col == (Grid.WIDTH - 1) || grid.isSet(row, col + 1))
				move = false;
			break;
		}
		return move;
	}

	/**
	 * moves this square in the given direction if possible.
	 * 
	 * The square will not move if the direction is blocked, or if the square is
	 * unable to move.
	 * 
	 * If it attempts to move DOWN and it can't, the square is frozen and cannot
	 * move anymore
	 * 
	 * @param direction
	 *            the direction to move
	 */
	public void move(Direction direction) {
		if (canMove(direction)) {
			switch (direction) {
			case UP:
				row = row - 1;
				break;
			case DOWN:
				// edit this to bring piece as far down as it can go
				row = row + 1;
				break;
			// added following cases on Apr 13, 2022
			case DROP:
				row = row + 1;				
				break;
			case LEFT:
				col--;
				break;
			case RIGHT:
				col++;
				break;
			}
		}
	}
	public boolean canRotate(Square center) {

		boolean move = true;
		// if all squares between (r1, c1) and (r2, c2) is "free" or "clear"
		// for each square in the piece, check that it can move left/right/up/down to the end location

		// check that destination square is in the grid
		int colTranslate = center.getCol() - this.getCol();
		int rowTranslate = this.getRow() - center.getRow();

		int destinationCol = center.getCol() + colTranslate;
		int destinationRow = center.getRow() + rowTranslate;

		// set rotation directions
		RotationDirection rotation = RotationDirection.RIGHTandDOWN;

		if (colTranslate > 0) {
			if (rowTranslate > 0) {
				rotation = RotationDirection.RIGHTandDOWN;	// positive horizontal & positive vertical movement
			} else {
				rotation = RotationDirection.UPandRIGHT;	// negative horizontal & positive vertical movement
			}
		} else if (colTranslate < 0) {
			if (rowTranslate > 0) {
				rotation = RotationDirection.DOWNandLEFT;
			} else {
				rotation = RotationDirection.LEFTandUP;
			}
		}
		if ((destinationCol >= (Grid.WIDTH - 1)) || (destinationCol <= 0)) {
			move = false;
		} else if ((destinationRow <= 0) || (destinationRow > (Grid.HEIGHT))){
			move = false;
		} else{
			switch (rotation) {
				case RIGHTandDOWN:
					for (int j = 0; j < colTranslate; j++) {    //loop horizontal path (col index)
						if (!this.canMove(Direction.RIGHT))
							move = false;
						break;
					}
					for (int k = 0; k < rowTranslate; k++) {    //loop vertical path (row index)
						if (!this.canMove(Direction.DOWN))
							move = false;
						break;
					}
				case UPandRIGHT:
					for (int j = 0; j < colTranslate; j++) {
						if (!this.canMove(Direction.RIGHT))
							move = false;
						break;
					}
					for (int k = this.getRow(); k > rowTranslate; k--) {
						if (!this.canMove(Direction.UP))
							move = false;
						break;
					}
				case DOWNandLEFT:
					for (int j = this.getCol(); j > destinationCol; j--) {
						if (!this.canMove(Direction.LEFT))
							move = false;
						break;
					}
					for (int k = 0; k < rowTranslate; k++) {
						if (!this.canMove(Direction.DOWN))
							move = false;
						break;
					}
				case LEFTandUP:
					for (int j = this.getCol(); j > destinationCol; j--) {
						if (!this.canMove(Direction.LEFT))
							move = false;
						break;
					}
					for (int k = this.getRow(); k > destinationRow; k--) {
						if (!this.canMove(Direction.UP))
							move = false;
						break;
					}
				default:
					move = true;
					break;
				}
			}return move;
		}

	/*
	 * This method rotates a square 90 degrees about the center square of a piece
	 * takes the center square (square[1]) as an argument
	 */
	public void rotate(Square center) {
		if(canRotate(center)){
			// temp variables to store translations of each square
			int colTranslate = center.getCol() - this.getCol();
			int rowTranslate = this.getRow() - center.getRow();

			// assigning new values relative to center
			this.row = center.getRow() - colTranslate;
			this.col = center.getCol() - rowTranslate;
		}
	}
	
	/**
	 * Changes the color of this square
	 * 
	 * @param c
	 *            the new color
	 */
	public void setColor(Color c) {
		color = c;
	}

	/**
	 * Gets the color of this square
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Draws this square on the given graphics context
	 */
	public void draw(Graphics g) {

		// calculate the upper left (x,y) coordinate of this square
		int actualX = Grid.LEFT + col * WIDTH;
		int actualY = Grid.TOP + row * HEIGHT;
		g.setColor(color);
		g.fillRect(actualX, actualY, WIDTH, HEIGHT);
		// black border (if not empty)
		if (!color.equals(Grid.EMPTY)) {
			g.setColor(Color.BLACK);
			g.drawRect(actualX, actualY, WIDTH, HEIGHT);
		}
	}
}
