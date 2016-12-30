package pente;

import utils.IntVector2D;
import utils.MyStringUtils;

public interface ReadOnlyBoard {
    int getWidth();
    int getHeight();
    Color getColor(IntVector2D pos);

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
