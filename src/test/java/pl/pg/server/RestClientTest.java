package pl.pg.server;

import org.junit.BeforeClass;
import org.junit.Test;
import pl.pg.AbstractTest;
import pl.pg.client.RestClient;
import pl.pg.client.RestClientBuilder;
import pl.pg.client.entity.Insight;
import pl.pg.client.mapper.RestResponse;
import pl.pg.config.ParamsMapper;
import pl.pg.config.ResourceConfigProvider;
import pl.pg.config.Story;
import pl.pg.config.TestCase;
import pl.pg.config.rest.Expected;
import pl.pg.config.rest.Request;
import pl.pg.config.rest.Rest;
import pl.pg.json.JsonMapper;

public class RestClientTest extends AbstractTest {

    private static Story story;

    @BeforeClass
    public static void setup() {
        Story rawStory = new ResourceConfigProvider("restclient.json").getStory();
        story = new ParamsMapper().map(rawStory);
    }

    @Test
    public void Should_Return_Valid_Object_By_GET_Method() {
        RestClient restClient = new RestClientBuilder().build();

        Request request = story.getTests().get(0).getRest().getRequest();
        System.out.println(JsonMapper.toJson(request));

        RestResponse<Insight> response = restClient.execute(request, Insight.class);

        assert 200 == response.getStatus();
        String header = response.getHeaders().getFirst("Content-Type");
        assert header.contains("application/pl.pg.json;charset=UTF-8");
        Insight insight = response.getBody();
//        assert 1 == insight.getId();
        assert "First stupid insight".equals(insight.getMessage());
        assert "gorskip".equals(insight.getAuthor());
    }

    @Test
    public void Should_Validate_Response_Against_Expected_Value() {
        RestClient restClient = new RestClientBuilder().build();

        TestCase test = story.getTests().get(0);
        Rest rest = test.getRest();
        Request request = rest.getRequest();

        RestResponse<Insight> response = restClient.execute(request, Insight.class);

        Expected expected = rest.getExpected();
        assert expected.getBodyAs(Insight.class).equals(response.getBody());

    }

    @Test
    public void Should_Validate_Response_Using_Own_Verifier() {
        RestClient restClient = new RestClientBuilder().build();

        TestCase test = story.getTests().get(0);
        Rest rest = test.getRest();
        Request request = rest.getRequest();

        RestResponse<Insight> response = restClient.execute(request, Insight.class);
        Expected expected = rest.getExpected();

        verifyIf.given(response)
                .has(expected).status()
                .and(expected).body()
                .and()
                .contains(expected);
    }

}
