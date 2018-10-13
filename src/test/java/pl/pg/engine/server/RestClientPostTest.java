package pl.pg.server;

import org.junit.BeforeClass;
import org.junit.Test;
import pl.pg.AbstractTest;
import pl.pg.client.rest.Response;
import pl.pg.client.rest.RestClient;
import pl.pg.client.rest.RestClientBuilder;
import pl.pg.config.DefaultParamsMapper;
import pl.pg.config.ResourceConfigProvider;
import pl.pg.config.Story;
import pl.pg.config.TestCase;
import pl.pg.config.rest.Expected;
import pl.pg.config.rest.Rest;

public class RestClientPostTest extends AbstractTest {

    private static Story story;

    @BeforeClass
    public static void setup() {
        Story rawStory = new ResourceConfigProvider("rest.post.json").getStory();
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
