import java.awt.*;

public class BarShape extends AbstractPiece {
    public BarShape(int r, int c, Grid g) {

        super(g);
        // Create the squares
        square[0] = new Square(g, r, c - 1, Color.CYAN, true);
        square[1] = new Square(g, r, c, Color.CYAN, true);
        square[2] = new Square(g, r, c + 1, Color.CYAN, true);
        square[3] = new Square(g, r, c + 2, Color.CYAN, true);
    }
}

