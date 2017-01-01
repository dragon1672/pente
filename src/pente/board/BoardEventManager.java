package pente.board;

import java.util.function.Consumer;

public interface BoardEventManager {
    class BoardChangeEvent {
        public final BoardDiff boardDiff;
        public final ReadOnlyBoard board;

        BoardChangeEvent(BoardDiff boardDiff, ReadOnlyBoard board) {
            this.boardDiff = boardDiff;
            this.board = board;
        }

        @Override
        public String toString() {
            return "BoardChangeEvent{" +
                    "boardDiff=" + boardDiff +
                    ", board=" + board +
                    '}';
        }
    }
    void addOnBoardChangeListener(Consumer<BoardChangeEvent> listener);
}
