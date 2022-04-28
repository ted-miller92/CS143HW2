import java.awt.*;

public class TShape extends AbstractPiece {
    public TShape(int r, int c, Grid g) {

        super(g);
        // Create the squares
        square[0] = new Square(g, r, c - 1, Color.YELLOW, true);
        square[1] = new Square(g, r, c, Color.YELLOW, true);
        square[2] = new Square(g, r, c + 1, Color.YELLOW, true);
        square[3] = new Square(g, r + 1, c, Color.YELLOW, true);
    }
}
