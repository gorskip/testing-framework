package pl.pg.config;

import config.StoryProvider;
import config.Story;
import json.JsonMapper;
import config.ParamsMapper;
import org.junit.Test;
import pl.pg.AbstractTest;

public class ParamsMapperTest extends AbstractTest {


    @Test
    public void Should_FillTestSuiteConfigWithParamsValues() {
        StoryProvider provider = getProvider("provider.test.json");

        Story story = new ParamsMapper().map(provider.getStory());

        provider = getProvider("expected.parametrized.json");

        Story expectedSuite = provider.getStory();

        assert JsonMapper.toJson(story).equals(JsonMapper.toJson(expectedSuite));


    }
    @Test
    public void Should_FillTestSuiteConfigWithParamsValues2() {

        StoryProvider provider = getProvider("raw.test.suite.json");
        Story story = new ParamsMapper().map(provider.getStory());

        provider = getProvider("parametrized.test.suite.json");
        Story expectedSuite = provider.getStory();

        assert JsonMapper.toJson(story).equals(JsonMapper.toJson(expectedSuite));
    }
}
