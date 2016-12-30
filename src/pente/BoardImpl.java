package pente;

import utils.IntVector2D;
import utils.Tuple;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class BoardImpl implements Board {
    private final int width;
    private final int height;
    private Color[][] board;

    public BoardImpl(int width, int height) {
        this.width = width;
        this.height = height;
        board = new Color[width][height];
    }

    private boolean validPos(IntVector2D toCheck) {
        return 0 < toCheck.X() && toCheck.X() < getWidth()
                && 0 < toCheck.Y() && toCheck.Y() < getHeight();
    }

    private void validatePos(IntVector2D toCheck) {
        if(!validPos(toCheck)) {
            throw new IllegalArgumentException(String.format("pos(%s) is invalid",toCheck));
        }
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public Color getColor(IntVector2D pos) {
        validPos(pos);
        return board[pos.X()][pos.Y()];
    }

    private void setColor(IntVector2D pos, Color toSet) {
        validPos(pos);
        board[pos.X()][pos.Y()] = toSet;
    }

    private void validatePlaceMove(IntVector2D pos) {
        validPos(pos);
        if(getColor(pos) != Color.EMPTY) {
            throw new IllegalArgumentException("Pieces can only be placed on empty squares");
        }
    }

    private static final Collection<IntVector2D> directions; static {
        List<IntVector2D> tmp = new ArrayList<>();
        for (int i = -1; i < 1; i++) {
            for (int j = -1; j < 1; j++) {
                if(i != -1 || j != -1) {
                    tmp.add(IntVector2D.create(i,j));
                }
            }
        }

        directions = Collections.unmodifiableList(tmp);
    }

    @Override
    public MoveImplications placePiece(IntVector2D pos, Color colorToPlace) {
        validatePlaceMove(pos);
        if (colorToPlace == Color.EMPTY) {
            throw new IllegalArgumentException("Color to place cannot be empty");
        }
        // check for captures
        Stream<IntVector2D> capturedPositions = directions.stream()
                .map(dir -> {
                    IntVector2D firstPosToCheck = pos.add(dir);
                    IntVector2D secondPosToCheck = firstPosToCheck.add(dir);
                    IntVector2D thirdPosToCheck = secondPosToCheck.add(dir);
                    return Tuple.of(firstPosToCheck, secondPosToCheck, thirdPosToCheck);
                })
                .filter(positions -> {
                    Color firstPosColor = validPos(positions.getFirst()) ? getColor(positions.getFirst()) : Color.EMPTY;
                    Color secondPosColor = validPos(positions.getSecond()) ? getColor(positions.getSecond()) : Color.EMPTY;
                    Color thirdPosColor = validPos(positions.getThird()) ? getColor(positions.getThird()) : Color.EMPTY;
                    return firstPosColor != Color.EMPTY
                            && secondPosColor == firstPosColor
                            && thirdPosColor == colorToPlace;
                }).flatMap(positions -> Stream.of(positions.getFirst(), positions.getSecond()));


        return MoveImplications.NOTHING_SPECIAL;
    }
}
