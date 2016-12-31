package utils;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Immutable 2d Int Vector
 */
@SuppressWarnings("WeakerAccess")
public class IntVector2D {
    private static final Map<Integer,Map<Integer, IntVector2D>> cache = new HashMap<>();
    private final int x;
    private final int y;

    public static final Collection<IntVector2D> CARDINAL_DIRECTIONS = Collections.unmodifiableList(Arrays.asList(
            IntVector2D.create( 0, 1),
            IntVector2D.create( 0,-1),
            IntVector2D.create( 1, 0),
            IntVector2D.create(-1, 0)
    ));

    public static final Collection<IntVector2D> ORDINAL_DIRECTIONS; static {
        List<IntVector2D> tmp = new ArrayList<>();
        for (int i = -1; i < 1; i++) {
            for (int j = -1; j < 1; j++) {
                if(i != -1 || j != -1) {
                    tmp.add(IntVector2D.create(i,j));
                }
            }
        }
        ORDINAL_DIRECTIONS = Collections.unmodifiableList(tmp);
    }

    public static Stream<IntVector2D> getRangeXY(int xFrom, int xTo, int yFrom, int yTo) {
        int xSize = Math.abs(xTo - xFrom);
        int xDir = (xTo - xFrom) /xSize;
        int ySize = Math.abs(yTo - yFrom);
        int yDir = (yTo - yFrom) / ySize;

        return IntStream.range(0,xSize).mapToObj(x ->
                IntStream.range(0,ySize).mapToObj(y->
                        IntVector2D.create(x * xDir,y * yDir)))
                .flatMap(stream->stream);
    }

    public static Stream<IntVector2D> getRangeYX(int xFrom, int xTo, int yFrom, int yTo) {
        //noinspection SuspiciousNameCombination
        return getRangeXY(yFrom,yTo,xFrom,xTo)
                .map(pos -> IntVector2D.create(pos.Y(),pos.X()));
    }

    private IntVector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int X() {return x;}
    public int Y() {return y;}
    public int getX() {return x;}
    public int getY() {return y;}

    public static IntVector2D create(int x, int y) {
        return cache.computeIfAbsent(x,integer -> new HashMap<>()).computeIfAbsent(y,integer -> new IntVector2D(x,y));
    }

    static IntVector2D add(IntVector2D left, IntVector2D right){
        return create(left.x + right.x,left.y + right.y);
    }
    public IntVector2D add(IntVector2D that){
        return add(this,that);
    }

    static IntVector2D sub(IntVector2D left, IntVector2D right){
        return create(left.x - right.x,left.y - right.y);
    }
    IntVector2D sub(IntVector2D that){
        return sub(this,that);
    }

    static IntVector2D mul(IntVector2D intVector2D, float value){
        return create((int)(intVector2D.x * value),(int)(intVector2D.y * value));
    }
    IntVector2D mul(float value){
        return mul(this,value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IntVector2D that = (IntVector2D) o;

        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return String.format("{%d,%d}",x,y);
    }
}
