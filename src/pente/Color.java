package pente;

public enum Color {
    WHITE, BLACK, EMPTY;

    public char toChar() {
        switch (this) {
            case BLACK:
                return 'B';
            case WHITE:
                return 'W';
            case EMPTY:
                return ' ';
        }
        throw new UnsupportedOperationException("Enum Not Mapped");
    }
}
