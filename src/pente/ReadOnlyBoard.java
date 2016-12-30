package pente;

import utils.IntVector2D;

public interface ReadOnlyBoard {
    int getWidth();
    int getHeight();
    Color getColor(IntVector2D pos);

    default String stringify() {
        StringBuilder sb = new StringBuilder((getWidth()+1) * getHeight());
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                IntVector2D pos = IntVector2D.create(x,y);
                Color color = getColor(pos);
                char ch = color.toChar();
                sb.append(ch);
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
