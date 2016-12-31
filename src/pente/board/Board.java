package pente.board;

import utils.IntVector2D;

import java.util.Optional;

public interface Board extends ReadOnlyBoard {
    /**
     * This should follow all the rules of Pente for capturing pieces and return an board diff to represent the changes to the board
     * @param pos position to place piece
     * @param colorToPlace what color to place, the board doesn't track player's turn
     * @return board diff representing board changes
     */
    BoardDiff placePiece(IntVector2D pos, Color colorToPlace);

    /**
     * revert the board state and return an board diff representing changes,
     * no-op if there are no changes to revert
     * @return board diff representing board changes
     */
    Optional<BoardDiff> undo();

    /**
     * allows the redo if undo was just called, otherwise no-op
     * @return BoardDiff representing board changes
     */
    Optional<BoardDiff> redo();
}
