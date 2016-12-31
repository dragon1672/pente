package pente.board;

import java.util.function.Consumer;

public interface BoardEventManager {
    void addOnBoardChangeListener(Consumer<BoardDiff> listener);
}
