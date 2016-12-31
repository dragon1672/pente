package driver;

import pente.board.BoardImpl;
import pente.board.BoardWithEvents;
import pente.board.Color;
import pente.game.GameManager;
import pente.player.RandomAI;
import utils.IntVector2D;

public class Driver {
    private static void randomAIGame() {
        GameManager gameManager = new GameManager(new RandomAI(),new RandomAI());
        gameManager.addOnBoardChangeListener(changes-> System.out.println(gameManager.getBoard().stringify()));
        gameManager.run();
    }

    private static void prefabMoveDemo() {
        BoardWithEvents board = new BoardWithEvents(new BoardImpl(19,19));
        board.addOnBoardChangeListener(changes -> System.out.println(board.stringify()));
        board.placePiece(IntVector2D.create(9,9), Color.BLACK);
        board.placePiece(IntVector2D.create(9,10), Color.WHITE);
        board.placePiece(IntVector2D.create(9,11), Color.WHITE);
        board.placePiece(IntVector2D.create(9,12), Color.BLACK);
        board.undo();
        board.redo();
    }

    public static void main(String ... args) {
        //prefabMoveDemo();
        randomAIGame();
    }
}
