package pente;

import utils.IntVector2D;

public interface Board extends ReadOnlyBoard {
    void placePiece(IntVector2D pos);
}
