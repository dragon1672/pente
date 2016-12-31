package pente;

import pente.board.*;
import pente.player.PlayerBrain;
import pente.utils.BoardUtilities;
import utils.IntVector2D;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameManager implements BoardEventManager {
    public GameManager(PlayerBrain p1, PlayerBrain p2) {
        playerManager = new PlayerManager(p1, p2);
    }

    private static final int STRAND_LENGTH_TO_WIN = 5;

    private static final int CAPTURES_TO_WIN = 5;

    private BoardWithEvents board = new BoardWithEvents(new BoardImpl(15, 15));

    private final PlayerManager playerManager;

    void gameTick() {
        ColoredPlayer currentPlayer = playerManager.getCurrentPlayer();
        ColoredPlayer nextPlayer = playerManager.getNextPlayer();
        BoardDiff move = null;
        while (playerManager.getCurrentPlayer() != currentPlayer) {
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

        if (currentPlayer.captures > CAPTURES_TO_WIN) {
            System.out.println("Game Over");
        }
        // check for 5 in a row
    }

    private ColoredPlayer colorToPlayer(Color color) {
        //if (p1.color == color) {
        //    return p1;
        //}
        //if (p2.color == color) {
        //    return p2;
        //}
        //return null;
        return null;
    }

    private Optional<ColoredPlayer> getWinningPlayer() {
        //if (p1.captures > CAPTURES_TO_WIN) {
        //    return Optional.of(p1);
        //}
        //if (p2.captures > CAPTURES_TO_WIN) {
        //    return Optional.of(p2);
        //}
        return BoardUtilities.getStrands(board)
                // Get winning strand
                .filter(strand -> strand.size() >= STRAND_LENGTH_TO_WIN)
                // convert to working with single positions
                .flatMap(Collection::stream)
                // convert to color
                .map(board::getColor)
                // convert to ColoredPlayer
                .map(this::colorToPlayer)
                // converts to optional if they exist
                .findFirst();
    }

    @Override
    public void addOnBoardChangeListener(Consumer<BoardDiff> listener) {
        board.addOnBoardChangeListener(listener);
    }

    static class PlayerManager {
        final List<ColoredPlayer> players;
        int currentPlayer = 0;

        PlayerManager(PlayerBrain... brains) {
            Stack<Color> possibleColors = Stream.of(Color.values())
                    .filter(color -> color.isPlayer)
                    .collect(Collectors.toCollection(Stack::new));

            players = Stream.of(brains)
                    .map(brain -> new ColoredPlayer(brain, possibleColors.pop()))
                    .collect(Collectors.toList());
        }

        ColoredPlayer getCurrentPlayer() {
            return players.get(currentPlayer);
        }

        ColoredPlayer getNextPlayer() {
            return players.get((currentPlayer + 1) % players.size());
        }

        ColoredPlayer getPreviousPlayer() {
            return players.get((currentPlayer - 1) % players.size());
        }

        void setupNextPlayer() {
            currentPlayer++;
            currentPlayer %= players.size();
        }
    }

    static class ColoredPlayer {
        final PlayerBrain playerBrain;
        final Color color;
        int captures = 0;

        ColoredPlayer(PlayerBrain playerBrain, Color color) {
            this.playerBrain = playerBrain;
            this.color = color;
        }
    }
}
