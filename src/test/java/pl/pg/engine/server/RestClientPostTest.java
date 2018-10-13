package pl.pg.engine.server;

import org.junit.BeforeClass;
import org.junit.Test;
import pl.pg.engine.AbstractTest;
import pl.pg.client.rest.Response;
import pl.pg.client.rest.RestClient;
import pl.pg.client.rest.RestClientBuilder;
import pl.pg.engine.config.DefaultParamsMapper;
import pl.pg.engine.config.ResourceConfigProvider;
import pl.pg.engine.config.Story;
import pl.pg.engine.config.TestCase;
import pl.pg.engine.config.rest.Expected;
import pl.pg.engine.config.rest.Rest;

public class RestClientPostTest extends AbstractTest {

    private static Story story;

    @BeforeClass
    public static void setup() {
        Story rawStory = new ResourceConfigProvider("z_other/rest.post.json").getStory();
        story = new DefaultParamsMapper().map(rawStory);
    }

    @Test
    public void Should_Create_New_Object() {
        TestCase testCase = story.getTests().get(0);

        RestClient restClient = new RestClientBuilder().build();

        Rest rest = testCase.getRest();
        Expected expected = rest.getExpected();

        Response response = restClient.execute(rest.getRequest());

        verifyIf.given(response)
                .contains(expected).body();
    }
}
