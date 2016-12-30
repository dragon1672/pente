package pente;

import java.util.function.Consumer;

public interface BoardEventManager {
    void addOnChangeListener(Consumer<Action> listener);
}
