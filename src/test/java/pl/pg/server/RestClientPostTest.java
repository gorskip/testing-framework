package pl.pg.server;

import client.RestClient;
import client.RestClientBuilder;
import client.mapper.RestResponse;
import config.ParamsMapper;
import config.ResourceConfigProvider;
import config.TestCase;
import config.Story;
import config.rest.Expected;
import config.rest.Rest;
import org.junit.BeforeClass;
import org.junit.Test;
import pl.pg.AbstractTest;

public class RestClientPostTest extends AbstractTest {

    private static Story story;

    @BeforeClass
    public static void setup() {
        Story rawStory = new ResourceConfigProvider("rest.post.json").getStory();
        story = new ParamsMapper().map(rawStory);
    }

    @Test
    public void Should_Create_New_Object() {
        TestCase testCase = story.getTests().get(0);

        RestClient restClient = new RestClientBuilder().build();

        Rest rest = testCase.getRest();
        Expected expected = rest.getExpected();

        RestResponse response = restClient.execute(rest.getRequest());

        verifyIf.given(response)
                .contains(expected).body();
    }
}
