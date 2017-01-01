package pente.board;

import utils.IntVector2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class BoardWithEvents implements BoardEventManager, Board {
    private final List<Consumer<BoardChangeEvent>> listeners = new ArrayList<>();
    private final Board backbone;

    public BoardWithEvents(Board backbone) {
        this.backbone = backbone;
    }

    @Override
    public void addOnBoardChangeListener(Consumer<BoardChangeEvent> listener) {
        listeners.add(listener);
    }

    private BoardDiff emitEvent(BoardDiff toEmit) {
        listeners.forEach(listener->listener.accept(new BoardChangeEvent(toEmit, this)));
        return toEmit;
    }
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private Optional<BoardDiff> emitEvent(Optional<BoardDiff> toEmit) {
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
    public BoardDiff placePiece(IntVector2D pos, Color colorToPlace) {
        return emitEvent(backbone.placePiece(pos,colorToPlace));
    }

    @Override
    public Optional<BoardDiff> undo() {
        return emitEvent(backbone.undo());
    }

    @Override
    public Optional<BoardDiff> redo() {
        return emitEvent(backbone.redo());
    }
}
