package driver;

import pente.gui.BoardPanel;
import pente.board.Board;
import pente.board.BoardImpl;
import pente.board.BoardWithEvents;
import pente.board.Color;
import pente.game.GameManager;
import pente.player.RandomAI;
import utils.IntVector2D;

import javax.swing.*;

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

    private static Board getPrefabBoard() {
        BoardWithEvents board = new BoardWithEvents(new BoardImpl(19,19));
        board.addOnBoardChangeListener(changes -> System.out.println(board.stringify()));
        board.placePiece(IntVector2D.create(9,9), Color.BLACK);
        board.placePiece(IntVector2D.create(9,10), Color.WHITE);
        board.placePiece(IntVector2D.create(9,11), Color.WHITE);
        board.placePiece(IntVector2D.create(9,12), Color.BLACK);
        board.undo();
        return board;
    }

    private static void basicGUI() {
        JFrame frame = new JFrame();
        frame.add(new BoardPanel(getPrefabBoard()));
        frame.setSize(900, 900);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String ... args) {
        //prefabMoveDemo();
        //randomAIGame();
        basicGUI();
    }
}
