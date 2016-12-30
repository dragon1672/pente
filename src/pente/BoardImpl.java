package pente;

import utils.IntVector2D;

public class BoardImpl implements Board {
    private final int width;
    private final int height;
    private Color[][] board;

    public BoardImpl(int width, int height) {
        this.width = width;
        this.height = height;
        board = new Color[width][height];
    }

    private boolean validPos(IntVector2D toCheck) {
        return 0 < toCheck.X() && toCheck.X() < getWidth()
                && 0 < toCheck.Y() && toCheck.Y() < getHeight();
    }

    private void validatePos(IntVector2D toCheck) {
        if(!validPos(toCheck)) {
            throw new IllegalArgumentException(String.format("pos(%s) is invalid",toCheck));
        }
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public Color getColor(IntVector2D pos) {
        validPos(pos);
        return board[pos.X()][pos.Y()];
    }

    private void setColor(IntVector2D pos, Color toSet) {
        validPos(pos);
        board[pos.X()][pos.Y()] = toSet;
    }

    @Override
    public void placePiece(IntVector2D pos) {
        validPos(pos);

    }
}
