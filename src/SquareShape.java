import java.awt.*;

public class SquareShape extends AbstractPiece {
    public SquareShape(int r, int c, Grid g) {

        super(g);
        // Create the squares
        square[0] = new Square(g, r, c - 1, Color.GRAY, true);
        square[1] = new Square(g, r, c, Color.GRAY, true);
        square[2] = new Square(g, r + 1, c - 1, Color.GRAY, true);
        square[3] = new Square(g, r + 1, c, Color.GRAY, true);
    }

    /**
     * Square shape should not rotate
     */
    @Override
    public boolean canRotate(){
        return false;
    }
}