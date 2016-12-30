package game.player;

import pente.Color;
import pente.ReadOnlyBoard;
import utils.IntVector2D;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class RandomAI implements Player {

    private static final Random rand = new Random();

    private static Stream<IntVector2D> getAllPositions(ReadOnlyBoard board) {
        return IntStream.range(0,board.getWidth()).mapToObj(x ->
                IntStream.range(0,board.getHeight()).mapToObj(y->
                        IntVector2D.create(x,y)))
                .flatMap(stream->stream);
    }

    private static Stream<IntVector2D> getValidMoves(ReadOnlyBoard board) {
        return getAllPositions(board)
                .filter(pos -> !board.getColor(pos).isPlayer);
    }

    private static <T> T getRandomInList(List<T> list) {
        return list.get(rand.nextInt(list.size()));
    }

    @Override
    public IntVector2D placePiece(ReadOnlyBoard board, Color pieceToPlace) {
        return getRandomInList(getValidMoves(board).collect(Collectors.toList()));
    }
}
