package pente.player;

import pente.board.Color;
import pente.board.ReadOnlyBoard;
import utils.IntVector2D;

public interface PlayerBrain {
    IntVector2D placePiece(ReadOnlyBoard board, Color pieceToPlace);
}
