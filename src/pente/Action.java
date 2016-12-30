package pente;

import utils.IntVector2D;
import utils.Tuple;
import utils.Tuple.Tuple3;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Action {
    // {position, color change from, color change to}
    public final Collection<Tuple3<IntVector2D, Color, Color>> changes;

    public Action(List<Tuple3<IntVector2D, Color, Color>> changes) {
        this.changes = Collections.unmodifiableCollection(changes);
    }

    public Action reverse() {
        return new Action(changes.stream()
                .map(change -> Tuple.of(change.getFirst(), change.getThird(), change.getSecond()))
                .collect(Collectors.toList()));
    }
}
