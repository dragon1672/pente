package game.player;

import pente.Color;
import pente.ReadOnlyBoard;
import utils.IntVector2D;

public interface Player {
    IntVector2D placePiece(ReadOnlyBoard board, Color pieceToPlace);
}
