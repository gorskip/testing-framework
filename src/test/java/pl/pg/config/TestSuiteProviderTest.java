package pl.pg.pg.config;

import pl.pg.config.StoryProvider;
import pl.pg.config.TestCase;
import pl.pg.config.Story;
import pl.pg.config.rest.Rest;
import pl.pg.config.rest.Request;
import pl.pg.config.db.Db;
import org.junit.Test;

public class TestSuiteProviderTest extends AbstractTest {


    @Test
    public void configProviderTest() {
       StoryProvider provider = getProvider("provider.test.json");

        Story story = provider.getStory();
        assert "Test Suite 1".equals(story.getName());
        assert 2 == story.getTests().size();
        assert 5 == story.getParams().entrySet().size();


        TestCase testCase = story.getTests().get(0);
        assert "Name of test 1".equals(testCase.getName());
        assert "simple".equals(testCase.getTag());
        assert 2 == testCase.getParams().entrySet().size();
        assert "mapperName".equals(testCase.getMapper());

        Rest rest = testCase.getRest();
        assert 2 == rest.getParams().entrySet().size();

        Request request = rest.getRequest();
        assert "GET".equals(request.getMethod());
        assert ":apiParam :param_4 :param_3 :suite_param_1".equals(request.getUrl());
        assert 1 == request.getHeaders().entrySet().size();

        Db db = testCase.getDb();

        assert "select * from :table_name".equals(db.getQuery());
        assert null == db.getParams();

        TestCase testCase2 = story.getTests().get(1);
        assert "Name of test 2".equals(testCase2.getName());
        assert "complex".equals(testCase2.getTag());
        assert 1 == testCase2.getParams().entrySet().size();

        Rest rest2 = testCase2.getRest();
        assert null == rest2.getParams();

        Request request2 = rest2.getRequest();
        assert "POST".equals(request2.getMethod());
        assert ":test_param :suite_param_2 :api_param".equals(request2.getUrl());
        assert 2 == request2.getHeaders().entrySet().size();
        assert null != request2.getBody();

        Db db2 = testCase2.getDb();
        assert ":test_param :suite_param_2".equals(db2.getQuery());
        assert 1 == db2.getParams().entrySet().size();
    }


}
