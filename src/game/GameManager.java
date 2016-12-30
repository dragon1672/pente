package game;

import game.player.Player;
import pente.*;
import utils.IntVector2D;

import java.util.function.Consumer;

public class GameManager implements BoardEventManager {
    public GameManager(ColoredPlayer p1, ColoredPlayer p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.currentPlayer = p1;
    }

    private static final int CAPTURES_TO_WIN = 5;

    private BoardWithEvents board = new BoardWithEvents(new BoardImpl(15,15));

    private final ColoredPlayer p1;
    private final ColoredPlayer p2;
    private ColoredPlayer currentPlayer;

    private ColoredPlayer getNextPlayer() {
        return currentPlayer == p1 ? p2 : p1;
    }

    void gameTick() {
        ColoredPlayer movingPlayer = currentPlayer;
        ColoredPlayer nextPlayer = getNextPlayer();
        BoardDiff move = null;
        while(currentPlayer != nextPlayer) {
            try {
                IntVector2D moveToMake = currentPlayer.player.placePiece(board,currentPlayer.color);
                move = board.placePiece(moveToMake,currentPlayer.color);
                if(move.changes.size() > 0) {
                    currentPlayer = nextPlayer;
                }
            } catch (IllegalArgumentException e) {
                // player tried invalid move :(
            }
        }
        assert move != null; // loop cannot continue otherwise

        if(move.changes.size() > 1) {
            movingPlayer.captures++;
        }
        if(movingPlayer.captures > CAPTURES_TO_WIN) {
            System.out.println("Game Over");
        }
        // check for 5 in a row
    }

    @Override
    public void addOnBoardChangeListener(Consumer<BoardDiff> listener) {
        board.addOnBoardChangeListener(listener);
    }


    static class ColoredPlayer {
        final Player player;
        final Color color;
        int captures = 0;

        ColoredPlayer(Player player, Color color) {
            this.player = player;
            this.color = color;
        }
    }
}
