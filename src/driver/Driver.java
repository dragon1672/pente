package driver;

import pente.board.BoardImpl;
import pente.board.BoardWithEvents;
import pente.board.Color;
import utils.IntVector2D;

public class Driver {
    public static void main(String ... args) {
        BoardWithEvents board = new BoardWithEvents(new BoardImpl(19,19));
        board.addOnBoardChangeListener(changes -> System.out.println(board.stringify()));
        board.placePiece(IntVector2D.create(9,9), Color.BLACK);
        board.placePiece(IntVector2D.create(9,10), Color.WHITE);
        board.placePiece(IntVector2D.create(9,11), Color.WHITE);
        board.placePiece(IntVector2D.create(9,12), Color.BLACK);
        board.undo();
        board.redo();
    }
}
