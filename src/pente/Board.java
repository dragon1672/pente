package pente;

import utils.IntVector2D;

public interface Board extends ReadOnlyBoard {
    Action placePiece(IntVector2D pos, Color colorToPlace);
    void undo();
}
