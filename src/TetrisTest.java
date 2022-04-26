import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.Test;

public class TetrisTest {

	@Test
	void testCheckRows() {
		// create a special grid
		// all squares filled except rows 5 and 15
		Grid g = new Grid();
		for (int row = 0; row < Grid.HEIGHT; row++) {
			for (int col = 0; col < Grid.WIDTH; col++) {
				if (row != 5 && row != 15 || col == 4 || col == 5) {
					g.set(row, col, Color.BLUE);
				}
				
			}
		}
		g.checkRows();
		
		// now check if grid looks like it should
		// Grid[HEIGHT - 1][4] and Grid[HEIGHT - 1][5] are set AND 
		// Grid[HEIGHT - 2][4] and Grid[HEIGHT - 2][5] are set
		for (int row = 0; row < Grid.HEIGHT; row++) {
			for (int col = 0; col < Grid.WIDTH; col++){
				if ((row == Grid.HEIGHT - 1 || row == Grid.HEIGHT -2) && 
						(col == 4 || col == 5)) {
					assertTrue(g.isSet(row, col));
				} else {
					assertFalse(g.isSet(row, col));
				}
			}
		}
	}
	@Test
	void testCanMove() {
		Grid g = new Grid();
		
		// set squares around Grid[4][1] to limit all movement
		for (int row = 0; row < Grid.HEIGHT; row++) {
			for (int col = 0; col < Grid.WIDTH; col++) {
				if (row == 5 || col == 0 || col == 2) {
					g.set(row, col, Color.BLUE);
				}
				
			}
		}
		
		// Square s should be unable to move any direction
		Square s = new Square(g, 4, 1, Color.blue, true);
		
		assertFalse(s.canMove(Direction.DOWN));
		assertFalse(s.canMove(Direction.RIGHT));
		assertFalse(s.canMove(Direction.LEFT));
		
		// Square d should be able to move right and down but not left
		Square d = new Square(g, 7, 3, Color.blue, true);
		
		assertTrue(d.canMove(Direction.DOWN));
		assertTrue(d.canMove(Direction.RIGHT));
		assertFalse(d.canMove(Direction.LEFT));
	}
	
}
