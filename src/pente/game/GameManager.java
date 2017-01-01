package pente.game;

import pente.board.*;
import pente.player.PlayerBrain;
import pente.utils.BoardUtilities;
import utils.IntVector2D;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class GameManager implements BoardEventManager {
    private static final int STRAND_LENGTH_TO_WIN = 5;

    private static final int CAPTURES_TO_WIN = 5;

    private final BoardWithEvents board = new BoardWithEvents(new BoardImpl(15, 15));

    private final PlayerManager playerManager;

    public GameManager(PlayerBrain p1, PlayerBrain p2) {
        playerManager = PlayerManager.createFromBrains(p1, p2);
    }

    /*
    This code stink and should be cleaned up, the return should provide more info, ditto on the board update method
     */
    private boolean gameTick() {
        Player currentPlayer = playerManager.getCurrentPlayer();
        BoardDiff move = null;
        while (playerManager.getCurrentPlayer() == currentPlayer) {
            try {
                IntVector2D moveToMake = currentPlayer.playerBrain.placePiece(board, currentPlayer.color);
                move = board.placePiece(moveToMake, currentPlayer.color);
                if (move.changes.size() > 0) {
                    playerManager.setupNextPlayer();
                }
            } catch (IllegalArgumentException e) {
                // playerBrain tried invalid move :(
            }
        }
        assert move != null; // loop cannot continue otherwise

        if (move.changes.size() > 1) {
            currentPlayer.captures++;
        }

        // Do win check stuff (this code is bad and should be improved)

        Optional<Player> winner = getWinningPlayer();

        winner.ifPresent(player -> System.out.printf("Game Over, player %s has won\n", playerManager.players.indexOf(player) + 1));

        return winner.isPresent();
    }

    private Optional<Player> getWinningPlayer() {
        Optional<Player> playerWithCaptureWin = playerManager.players.stream()
                .filter(player -> player.captures > CAPTURES_TO_WIN)
                .findFirst();
        if(playerWithCaptureWin.isPresent()) {
            return playerWithCaptureWin;
        }
        return BoardUtilities.getStrands(board)
                // Get winning strand
                .filter(strand -> strand.size() >= STRAND_LENGTH_TO_WIN)
                // convert to working with single positions
                .flatMap(Collection::stream)
                // convert to color
                .map(board::getColor)
                // convert to Player
                .map(playerManager::getPlayerFromColor)
                // flatten optional
                .flatMap(o -> o.map(Stream::of).orElseGet(Stream::empty))
                // converts to optional if they exist
                .findFirst();
    }

    public void run() {
        //noinspection StatementWithEmptyBody
        while(!gameTick());
    }

    @Override
    public void addOnBoardChangeListener(Consumer<BoardChangeEvent> listener) {
        board.addOnBoardChangeListener(listener);
    }

    public ReadOnlyBoard getBoard() {
        return board;
    }
}
