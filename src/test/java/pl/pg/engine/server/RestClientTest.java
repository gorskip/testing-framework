package pl.pg.engine.server;

import org.junit.BeforeClass;
import org.junit.Test;
import pl.pg.engine.AbstractTest;
import pl.pg.client.entity.Insight;
import pl.pg.client.rest.Response;
import pl.pg.client.rest.RestClient;
import pl.pg.client.rest.RestClientBuilder;
import pl.pg.engine.config.DefaultParamsMapper;
import pl.pg.engine.config.ResourceConfigProvider;
import pl.pg.engine.config.Story;
import pl.pg.engine.config.TestCase;
import pl.pg.engine.config.rest.Expected;
import pl.pg.engine.config.rest.Request;
import pl.pg.engine.config.rest.Rest;
import pl.pg.json.JsonMapper;

public class RestClientTest extends AbstractTest {

    private static Story story;

    @BeforeClass
    public static void setup() {
        Story rawStory = new ResourceConfigProvider("restclient.json").getStory();
        story = new DefaultParamsMapper().map(rawStory);
    }

    @Test
    public void Should_Return_Valid_Object_By_GET_Method() {
        RestClient restClient = new RestClientBuilder().build();

        Request request = story.getTests().get(0).getRest().getRequest();
        System.out.println(JsonMapper.toJson(request));

        Response<Insight> response = restClient.execute(request, Insight.class);

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

        Response<Insight> response = restClient.execute(request, Insight.class);

        Expected expected = rest.getExpected();
        assert expected.getBodyAs(Insight.class).equals(response.getBody());

    }

    @Test
    public void Should_Validate_Response_Using_Own_Verifier() {
        RestClient restClient = new RestClientBuilder().build();

        TestCase test = story.getTests().get(0);
        Rest rest = test.getRest();
        Request request = rest.getRequest();

        Response<Insight> response = restClient.execute(request, Insight.class);
        Expected expected = rest.getExpected();

        verifyIf.given(response)
                .has(expected).status()
                .and(expected).body()
                .and()
                .contains(expected);
    }

}
