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
		case ROTATE:
				move = true;
			break;
		case DOWN:
			if (row == (Grid.HEIGHT - 1) || grid.isSet(row + 1, col))
				move = false;
			break;

		// added the following cases on Apr 13, 2022
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
			case ROTATE:
				rotate(this);
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
		
		// if all squares between (r1, c1) and (r2, c2) is "free" or "clear"
		// for each square in the piece, check that it can move left/right/up/down to the end location
		for (int i = 0; i < 4; i++) {
			// skip square[1]
			// check if the square can move at all
			
			// target destination
			int targetRow = center.row + (center.col - this.col);
			int targetCol = center.col + (this.row - center.row);
			System.out.println("targetRow = " + targetRow);
			System.out.println("targetCol = " + targetCol);
			
			// two loops, one for row "path" and one for column "path"
			
			for (int j = 0; j < targetRow; j++) {
				if (!this.canMove(Direction.LEFT) || !this.canMove(Direction.RIGHT) || !this.canMove(Direction.DOWN) || !this.canMove(Direction.UP)) {
					return false;
				}
			}
			for (int k = 0; k < targetCol; k++) {
				if (!this.canMove(Direction.LEFT) || !this.canMove(Direction.RIGHT) || !this.canMove(Direction.DOWN) || !this.canMove(Direction.UP))  {
					return false;
				}
			}
		}
		return true;
		
		// also if rotation doesn't take square out of bounds of grid
	}
	
	/*
	 * This method rotates a square 90 degrees about the center square of a piece
	 * takes the center square (square[1]) as an argument
	 */
	public void rotate(Square center) {
		if (canRotate(center)) {
			// temp variables to store translations of each square
			int colTranslate = center.getCol() - this.getCol();
			int rowTranslate = this.getRow() - center.getRow();
			
			// assigning new values relative to center
			this.row = center.getRow() + colTranslate;
			this.col = center.getCol() + rowTranslate;
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
