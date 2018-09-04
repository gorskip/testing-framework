package verify.diff;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class DiffSummarize {

    @JsonIgnore
    private final List<Diff> diffs;
    private List<ChangeDiff> changed;
    private List<Diff> removed;
    private List<Diff> added;
    private List<MoveDiff> moved;

    public DiffSummarize(List<Diff> diffs) {
        this.diffs = diffs;
    }

    public boolean hasDiffs() {
        return !diffs.isEmpty();
    }

    public boolean hasRemoved() {
        return !removed.isEmpty();
    }

    public boolean hasChanged() {
        return !changed.isEmpty();
    }

    public boolean hasMoved() { return !moved.isEmpty(); }
}
