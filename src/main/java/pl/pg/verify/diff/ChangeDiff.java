package pl.pg.verify.diff;

import lombok.Data;

@Data
public class ChangeDiff extends Diff {

    private String expectedValue;

    @Override
    public String toString() {
        return "Diff{" +
                "op='" + getOp() + '\'' +
                ", path='" + getPath() + '\'' +
                ", actualValue='" + expectedValue + '\'' +
                ", value='" + getValue() + '\'' +
                '}';
    }
}
