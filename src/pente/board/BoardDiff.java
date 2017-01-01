package pente.board;

import utils.IntVector2D;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BoardDiff {

    static class SingleDif {
        final IntVector2D pos;
        final Color replacedColor;
        final Color newColor;

        public SingleDif(IntVector2D pos, Color replacedColor, Color newColor) {
            this.pos = pos;
            this.replacedColor = replacedColor;
            this.newColor = newColor;
        }

        @Override
        public String toString() {
            return "SingleDif{" +
                    "pos=" + pos +
                    ", replacedColor=" + replacedColor +
                    ", newColor=" + newColor +
                    '}';
        }
    }

    // {position, color change from, color change to}
    public final Collection<SingleDif> changes;

    public BoardDiff(List<SingleDif> changes) {
        this.changes = Collections.unmodifiableCollection(changes);
    }

    public BoardDiff reverse() {
        return new BoardDiff(changes.stream()
                .map(change -> new SingleDif(change.pos, change.newColor, change.replacedColor))
                .collect(Collectors.toList()));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("BoardDiff{");
        sb.append("changes=[");
        changes.forEach(sb::append);
        sb.append("])");
        return sb.toString();
    }
}
