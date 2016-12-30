package pente;

import utils.IntVector2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class BoardWithEvents implements BoardEventManager, Board {
    private final List<Consumer<Action>> listeners = new ArrayList<>();
    private final Board backbone;

    public BoardWithEvents(Board backbone) {
        this.backbone = backbone;
    }

    @Override
    public void addOnChangeListener(Consumer<Action> listener) {
        listeners.add(listener);
    }

    private Action emitEvent(Action toEmit) {
        listeners.forEach(listener->listener.accept(toEmit));
        return toEmit;
    }
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private Optional<Action> emitEvent(Optional<Action> toEmit) {
        toEmit.ifPresent(this::emitEvent);
        return toEmit;
    }

    @Override
    public int getWidth() {
        return backbone.getWidth();
    }

    @Override
    public int getHeight() {
        return backbone.getHeight();
    }

    @Override
    public Color getColor(IntVector2D pos) {
        return backbone.getColor(pos);
    }

    @Override
    public Action placePiece(IntVector2D pos, Color colorToPlace) {
        return emitEvent(backbone.placePiece(pos,colorToPlace));
    }

    @Override
    public Optional<Action> undo() {
        return emitEvent(backbone.undo());
    }

    @Override
    public Optional<Action> redo() {
        return emitEvent(backbone.redo());
    }
}
