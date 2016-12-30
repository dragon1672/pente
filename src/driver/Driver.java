package driver;

import pente.Board;
import pente.BoardImpl;
import pente.Color;
import utils.IntVector2D;

public class Driver {
    public static void main(String ... args) {
        Board board = new BoardImpl(19,19);
        board.placePiece(IntVector2D.create(9,9), Color.BLACK);
        board.placePiece(IntVector2D.create(9,10), Color.WHITE);
        board.placePiece(IntVector2D.create(9,11), Color.WHITE);
        System.out.println(board.stringify());
        board.placePiece(IntVector2D.create(9,12), Color.BLACK);
        System.out.println(board.stringify());
        board.undo();
        System.out.println(board.stringify());
    }
}
