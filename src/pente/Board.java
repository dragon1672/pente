package pente;

import utils.IntVector2D;

import java.util.Optional;

public interface Board extends ReadOnlyBoard {
    /**
     * This should follow all the rules of Pente for capturing pieces and return an action to represent the changes to the board
     * @param pos position to place piece
     * @param colorToPlace what color to place, the board doesn't track player's turn
     * @return action representing board changes
     */
    Action placePiece(IntVector2D pos, Color colorToPlace);

    /**
     * revert the board state and return an action representing changes,
     * no-op if there are no changes to revert
     * @return action representing board changes
     */
    Optional<Action> undo();

    /**
     * allows the redo if undo was just called, otherwise no-op
     * @return action representing board changes
     */
    Optional<Action> redo();
}
