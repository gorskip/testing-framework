package run;

import client.RestClient;
import client.RestClientBuilder;
import client.mapper.Response;
import config.TestCase;
import config.TestSuite;
import config.rest.Expected;
import config.rest.Request;
import config.rest.Rest;
import json.JsonMapper;

import java.util.List;

public class TestRunner {

    private final RestClient restClient;

    public TestRunner() {
        this.restClient = new RestClientBuilder().build();
    }

    public void run(TestSuite testSuite) {
        System.out.println("Running Test Suite: " + testSuite.getName());
        runTests(testSuite.getTests());

    }

    public void runTests(List<TestCase> tests) {
        tests.forEach(test -> run(test));
    }

    private void run(TestCase test) {
        System.out.println("Test Case: " + test.getName());
        runRest(test.getRest());
    }

    private void runRest(Rest rest) {
        Request request = rest.getRequest();
        Expected expected = rest.getExpected();

        System.out.println("Execute REST request: " + JsonMapper.toJson(request));

        Response response = restClient.execute(request);

        System.out.println("Response body: " + JsonMapper.toJson(response.getBody()));

        System.out.println("Expected: " + JsonMapper.toJson(expected));

        new TestVerificator(expected).verify(response);

    }
}
