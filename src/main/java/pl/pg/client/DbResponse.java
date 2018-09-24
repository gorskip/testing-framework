package pl.pg.client;

import lombok.ToString;

import java.util.List;

@ToString
public class DbResponse<T> {

    private final List<T> records;
    private long executionTime;

    public DbResponse(List<T> records) {
        this.records = records;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setExecutionTime(long executionTime) {
        executionTime = executionTime;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public boolean hasRecords() {
        return !records.isEmpty();
    }

    public T getFirstRecord() {
        return records.stream().findFirst().get();
    }
}
