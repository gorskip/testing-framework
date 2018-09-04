package config.mock;

import config.rest.Expected;
import lombok.Data;

@Data
public class RestMock {

    private String name;
    private Expected expected;
    private MockResponse response;
}
