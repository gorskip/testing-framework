package pl.pg.engine.config;

import org.junit.Test;
import pl.pg.engine.AbstractTest;
import pl.pg.json.JsonMapper;

public class DefaultParamsMapperTest extends AbstractTest {


    @Test
    public void Should_FillTestSuiteConfigWithParamsValues() {
        StoryProvider provider = getProvider("z_other/provider.test.json");

        Story story = new DefaultParamsMapper().map(provider.getStory());

        provider = getProvider("z_other/expected.parametrized.json");

        Story expectedSuite = provider.getStory();

        System.out.println(JsonMapper.toJson(story));

        assert JsonMapper.toJson(story).equals(JsonMapper.toJson(expectedSuite));


    }
    @Test
    public void Should_FillTestSuiteConfigWithParamsValues2() {

        StoryProvider provider = getProvider("z_other/raw.test.suite.json");
        Story story = new DefaultParamsMapper().map(provider.getStory());

        provider = getProvider("z_other/parametrized.test.suite.json");
        Story expectedSuite = provider.getStory();

        assert JsonMapper.toJson(story).equals(JsonMapper.toJson(expectedSuite));
    }

    @Test
    public void Should_FillTestSuiteConfigWithParamsValues3() {
        StoryProvider provider = getProvider("z_other/to.be.parametrized.json");

        Story story = new ParamsMapper().map(provider.getStory());

        provider = getProvider("z_other/expected.to.be.parametrized.json");

        Story expectedSuite = provider.getStory();
        System.out.println(JsonMapper.toJson(story));

        System.out.println(JsonMapper.toJson(expectedSuite));

        assert story.equals(expectedSuite);
        assert JsonMapper.toJson(story).equals(JsonMapper.toJson(expectedSuite));


    }
}
