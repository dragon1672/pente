package pente.utils;

import pente.board.ReadOnlyBoard;
import utils.IntVector2D;
import utils.Tuple;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BoardUtilities {

    private static final Set<IntVector2D> POSITIVE_ORDINAL_DIRECTIONS = IntVector2D.ORDINAL_DIRECTIONS.stream()
            .map(dir -> IntVector2D.create(Math.abs(dir.X()),Math.abs(dir.Y())))
            .collect(Collectors.toSet());

    public static Stream<Set<IntVector2D>> getStrands(ReadOnlyBoard board) {
        Set<IntVector2D> consideredPositions = new HashSet<>();
        return IntVector2D.getRangeXY(0, board.getWidth(), 0, board.getHeight())
                .filter(pos -> !consideredPositions.contains(pos))
                .flatMap(pos -> POSITIVE_ORDINAL_DIRECTIONS.stream().map(dir -> Tuple.of(pos, dir)))
                .map(posDir -> {
                    Set<IntVector2D> strand = new HashSet<>();
                    IntVector2D current = posDir.getFirst();
                    while (board.getColor(current) == board.getColor(posDir.getFirst())) {
                        consideredPositions.add(current);
                        strand.add(current);
                        current = current.add(posDir.getSecond());
                    }
                    return strand;
                })
                .filter(strand -> strand.size() > 1);
    }
}
