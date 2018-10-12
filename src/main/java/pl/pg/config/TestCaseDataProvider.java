package pl.pg.config;

import java.util.Map;
import java.util.stream.Collectors;

public class TestCaseDataProvider {

    private final Story story;
    private final Map<String, TestCase> testCaseMap;

    public TestCaseDataProvider(Story story) {
        this.story = story;
        this.testCaseMap = story.getTests().stream()
                .collect(Collectors.toMap(k -> k.getName(), v -> v));
    }

    public TestCase getTestCase(String name) {
        return testCaseMap.get(name);
    }


}
