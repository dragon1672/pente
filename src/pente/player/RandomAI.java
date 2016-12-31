package pente.player;

import pente.board.Color;
import pente.board.ReadOnlyBoard;
import utils.IntVector2D;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RandomAI implements Player {

    private static final Random rand = new Random();

    private static <T> T getRandomInList(List<T> list) {
        return list.get(rand.nextInt(list.size()));
    }

    @Override
    public IntVector2D placePiece(ReadOnlyBoard board, Color pieceToPlace) {
        List<IntVector2D> possibleMoves = board.getAllBoardPositions()
                .filter(pos -> board.getColor(pos) == Color.EMPTY)
                .collect(Collectors.toList());
        return getRandomInList(possibleMoves);
    }
}
