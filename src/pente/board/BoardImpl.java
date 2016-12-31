package pente.board;

import utils.IntVector2D;
import utils.Tuple;
import utils.Tuple.Tuple3;

import java.util.*;
import java.util.stream.Stream;

public class BoardImpl implements Board {
    private final int width;
    private final int height;
    private Map<IntVector2D, Color> board = new HashMap<>();

    private Stack<BoardDiff> boardDiffHistory = new Stack<>();
    private Stack<BoardDiff> redoCache = new Stack<>();

    public BoardImpl(int width, int height) {
        this.width = width;
        this.height = height;
    }

    private boolean validPos(IntVector2D toCheck) {
        return 0 <= toCheck.X() && toCheck.X() < getWidth()
                && 0 <= toCheck.Y() && toCheck.Y() < getHeight();
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
        validatePos(pos);
        return board.getOrDefault(pos,Color.EMPTY);
    }

    @Override
    public Stream<IntVector2D> getPieceLocations() {
        return board.keySet().stream();
    }

    /**
     * Will just update the board only checking valid dimensions, contains no game logic
     * @param pos position to update
     * @param toSet color to set
     */
    private void setColor(IntVector2D pos, Color toSet) {
        validatePos(pos);
        if(toSet == Color.EMPTY) {
            board.remove(pos); //  no need to have useless cruft
        } else {
            board.put(pos, toSet);
        }
    }

    private void validatePlaceMove(IntVector2D pos) {
        validatePos(pos);
        if(getColor(pos) != Color.EMPTY) {
            throw new IllegalArgumentException(String.format("valid placement pos (%s) Pieces can only be placed on empty squares, square already %s",pos,getColor(pos)));
        }
    }

    private BoardDiff queryMove(IntVector2D pos, Color colorToPlace) {
        validatePlaceMove(pos);
        if (colorToPlace == Color.EMPTY) {
            throw new IllegalArgumentException("Color to place cannot be empty");
        }
        List<Tuple3<IntVector2D,Color,Color>> changesToApply = new ArrayList<>();
        // q up initial placement
        changesToApply.add(Tuple.of(pos,Color.EMPTY,colorToPlace));
        // check for captures
        Stream<IntVector2D> capturedPositions = IntVector2D.ORDINAL_DIRECTIONS.stream()
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
                    return firstPosColor.isPlayer
                            && secondPosColor == firstPosColor
                            && thirdPosColor == colorToPlace;
                }).flatMap(positions -> Stream.of(positions.getFirst(), positions.getSecond()));
        capturedPositions
                .map(capturedPos -> Tuple.of(capturedPos,getColor(capturedPos), Color.EMPTY))
                .forEach(changesToApply::add);
        return new BoardDiff(changesToApply);
    }

    @Override
    public BoardDiff placePiece(IntVector2D pos, Color colorToPlace) {
        redoCache.clear();
        BoardDiff boardDiff = queryMove(pos,colorToPlace);
        applyBoardDiff(boardDiff,boardDiffHistory);
        return boardDiff;
    }

    private void applyBoardDiff(BoardDiff toApply, Collection<BoardDiff> historyToAppendTo) {
        toApply.changes.stream()
                .peek(change -> {
                    if(getColor(change.getFirst()) != change.getSecond()) {
                        throw new IllegalArgumentException("Provided BoardDiff doesn't map current board state");
                    }
                })
                .forEach(change -> setColor(change.getFirst(),change.getThird()));
        historyToAppendTo.add(toApply);
    }

    @Override
    public Optional<BoardDiff> undo() {
        if(boardDiffHistory.size() > 0) {
            BoardDiff lastBoardDiffReversed = boardDiffHistory.pop().reverse();
            applyBoardDiff(lastBoardDiffReversed,redoCache);
            return Optional.of(lastBoardDiffReversed);
        }
        return Optional.empty();
    }

    @Override
    public Optional<BoardDiff> redo() {
        if(redoCache.size() > 0) {
            BoardDiff lastBoardDiffReversed = redoCache.pop().reverse();
            applyBoardDiff(lastBoardDiffReversed,boardDiffHistory);
            return Optional.of(lastBoardDiffReversed);
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        return stringify();
    }
}
