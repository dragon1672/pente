package pente.board;

import utils.IntVector2D;
import utils.MyStringUtils;

import java.util.stream.Stream;

public interface ReadOnlyBoard {
    /**
     * Get Board's width
     * @return width of board
     */
    int getWidth();

    /**
     * Get Board's height
     * @return height of board
     */
    int getHeight();

    /**
     * Get the color of the given position in the board.
     * An Empty board should return Color.EMPTY for all valid positions
     * @param pos position to check
     * @return color at given position
     * @throws IllegalArgumentException if position is not within board dimensions
     */
    Color getColor(IntVector2D pos);

    /**
     * Returns a stream of all valid positions (positions not moves) in the current board
     * @return a stream of locations
     */
    default Stream<IntVector2D> getAllBoardPositions() {
        return IntVector2D.getRangeXY(0,getWidth(),0,getHeight());
    }

    /**
     * Restricts the provided positions to the provided color.
     * @param ofColor color to restrict by
     * @return a stream of locations
     */
    default Stream<IntVector2D> getPieceLocations(Color ofColor) {
        return getAllBoardPositions().filter(pos -> getColor(pos) == ofColor);
    }

    /**
     * Gets all the piece locations currently on the board. Should not return locations that have Color.EMPTY
     * @return a stream of locations
     */
    default Stream<IntVector2D> getPieceLocations() {
        return getAllBoardPositions().filter(pos -> getColor(pos) != Color.EMPTY);
    }

    default String stringify() {
        StringBuilder sb = new StringBuilder((getWidth()+3) * (getHeight()+2));

        sb.append(MyStringUtils.repeatChar('-',getWidth()+2,'+'));
        sb.append('\n');

        for (int y = 0; y < getHeight(); y++) {
            sb.append('|');
            for (int x = 0; x < getWidth(); x++) {
                IntVector2D pos = IntVector2D.create(x,y);
                Color color = getColor(pos);
                char ch = color.toChar();
                sb.append(ch);
            }
            sb.append('|');
            sb.append('\n');
        }

        sb.append(MyStringUtils.repeatChar('-',getWidth()+2,'+'));
        return sb.toString();
    }
}
