package pente;

import java.util.function.Consumer;

public interface BoardEventManager {
    void addOnBoardChangeListener(Consumer<Action> listener);
}