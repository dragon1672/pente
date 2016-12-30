package pente;

import utils.IntVector2D;
import utils.Tuple;
import utils.Tuple.Tuple3;

import java.util.*;
import java.util.stream.Stream;

public class BoardImpl implements Board {
    private final int width;
    private final int height;
    private Color[][] board;

    private Stack<Action> actionHistory = new Stack<>();

    public BoardImpl(int width, int height) {
        this.width = width;
        this.height = height;
        board = new Color[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                setColor(IntVector2D.create(x,y),Color.EMPTY);
            }
        }
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
        return board[pos.X()][pos.Y()];
    }

    private void setColor(IntVector2D pos, Color toSet) {
        validatePos(pos);
        board[pos.X()][pos.Y()] = toSet;
    }

    private void validatePlaceMove(IntVector2D pos) {
        validatePos(pos);
        if(getColor(pos) != Color.EMPTY) {
            throw new IllegalArgumentException(String.format("valid placement pos (%s) Pieces can only be placed on empty squares, square already %s",pos,getColor(pos)));
        }
    }

    private Action queryMove(IntVector2D pos, Color colorToPlace) {
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
                    return firstPosColor != Color.EMPTY
                            && secondPosColor == firstPosColor
                            && thirdPosColor == colorToPlace;
                }).flatMap(positions -> Stream.of(positions.getFirst(), positions.getSecond()));
        capturedPositions
                .map(capturedPos -> Tuple.of(capturedPos,getColor(capturedPos), Color.EMPTY))
                .forEach(changesToApply::add);
        return new Action(changesToApply);
    }

    @Override
    public Action placePiece(IntVector2D pos, Color colorToPlace) {
        Action action = queryMove(pos,colorToPlace);
        actionHistory.add(action);
        applyAction(action);
        return action;
    }

    /**
     * Will not verify that the second param of the action is set correctly to the current board state
     * @param toApply change to apply
     */
    private void applyAction(Action toApply) {
        toApply.changes.forEach(change -> setColor(change.getFirst(),change.getThird()));
    }

    @Override
    public void undo() {
        if(actionHistory.size() > 0) {
            Action lastAction = actionHistory.pop();
            applyAction(lastAction.reverse());
        }
    }
}
