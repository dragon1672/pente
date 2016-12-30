package pente;

import java.util.function.Consumer;

public interface BoardWithEvents extends Board {
    void addOnChangeListener(Consumer<Action> listener);
}
