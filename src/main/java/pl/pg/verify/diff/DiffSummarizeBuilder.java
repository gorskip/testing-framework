package verify.diff;

import com.fasterxml.jackson.databind.JsonNode;
import json.JsonMapper;

import java.util.List;
import java.util.stream.Collectors;

public class DiffSummarizeBuilder {

    private final DiffSummarize diffSummarize;

    public DiffSummarizeBuilder(List<Diff> diffs) {
        this.diffSummarize = new DiffSummarize(diffs);
    }

    public DiffSummarizeBuilder withAdded() {
        this.diffSummarize.setAdded(getAllDiffsWithType("add"));
        return this;
    }

    public DiffSummarizeBuilder withRemoved() {
        this.diffSummarize.setRemoved(getAllDiffsWithType("remove"));
        return this;
    }

    public DiffSummarizeBuilder withMoved(List<MoveDiff> moveDiffs) {
        this.diffSummarize.setMoved(
                moveDiffs.stream()
                .filter(diff -> "move".equals(diff.getOp()))
                .collect(Collectors.toList()));
        return this;
    }

    public DiffSummarizeBuilder withChanged(String jsonDiff) {
        JsonNode root = JsonMapper.readTree(jsonDiff);
        List<ChangeDiff> changedDiffs = getAllDiffsWithType("replace").stream()
                .map(diff -> {
                    String expectedValue = root.at(diff.getPath()).asText();
                    ChangeDiff changeDiff = new ChangeDiff();
                    changeDiff.setPath(diff.getPath());
                    changeDiff.setValue(diff.getValue());
                    changeDiff.setExpectedValue(expectedValue);
                    return changeDiff;
                }).collect(Collectors.toList());
        this.diffSummarize.setChanged(changedDiffs);
        return this;
    }

    public DiffSummarize build() {
        return diffSummarize;
    }

    private List<Diff> getAllDiffsWithType(final String type) {
        return diffSummarize.getDiffs().stream()
                .filter(diff -> type.equals(diff.getOp()))
                .collect(Collectors.toList());
    }
}
