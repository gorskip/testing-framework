package pl.pg.config;

import org.junit.Test;
import pl.pg.AbstractTest;
import pl.pg.json.JsonMapper;

public class DefaultParamsMapperTest extends AbstractTest {


    @Test
    public void Should_FillTestSuiteConfigWithParamsValues() {
        StoryProvider provider = getProvider("provider.test.json");

        Story story = new DefaultParamsMapper().map(provider.getStory());

        provider = getProvider("expected.parametrized.json");

        Story expectedSuite = provider.getStory();

        System.out.println(JsonMapper.toJson(story));

        assert JsonMapper.toJson(story).equals(JsonMapper.toJson(expectedSuite));


    }
    @Test
    public void Should_FillTestSuiteConfigWithParamsValues2() {

        StoryProvider provider = getProvider("raw.test.suite.json");
        Story story = new DefaultParamsMapper().map(provider.getStory());

        provider = getProvider("parametrized.test.suite.json");
        Story expectedSuite = provider.getStory();

        assert JsonMapper.toJson(story).equals(JsonMapper.toJson(expectedSuite));
    }

    @Test
    public void Should_FillTestSuiteConfigWithParamsValues3() {
        StoryProvider provider = getProvider("to.be.parametrized.json");

        Story story = new ParamsMapper().map(provider.getStory());

        provider = getProvider("expected.to.be.parametrized.json");

        Story expectedSuite = provider.getStory();

        assert story.equals(expectedSuite);
        assert JsonMapper.toJson(story).equals(JsonMapper.toJson(expectedSuite));


    }
}
