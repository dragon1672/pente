package pente;

public enum Color {
    WHITE(true),
    BLACK(true),
    EMPTY(false);

    final boolean isPlayer;

    Color(boolean isPlayer) {
        this.isPlayer = isPlayer;
    }

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
