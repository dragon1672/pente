package pente;

import utils.IntVector2D;

public interface Board extends ReadOnlyBoard {
    MoveImplications placePiece(IntVector2D pos, Color colorToPlace);
}
