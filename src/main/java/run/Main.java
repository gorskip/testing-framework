package run;

import client.RestClient;
import client.RestClientBuilder;
import client.mapper.Response;
import verify.VerifyIf;
import config.ResourceConfigProvider;
import config.TestCase;
import config.TestSuite;
import config.rest.Expected;
import config.rest.Rest;
import json.ParamsMapper;

public class Main {

    public static void main(String[] args) {

        TestSuite rawTestSuite = new ResourceConfigProvider("restclient.json").getTestSuite();
        TestSuite testSuite = new ParamsMapper().map(rawTestSuite);

        VerifyIf verifyIf = new VerifyIf();

        RestClient restClient = new RestClientBuilder().build();

        TestCase testCase = testSuite.getTests().get(1);
        Rest rest = testCase.getRest();

        Response response = restClient.execute(rest.getRequest());

        Expected expected = rest.getExpected();

        verifyIf.given(response)
                .has(expected).status()
                .contains(expected).body();


    }
}