package pl.pg.config.mock;

import pl.pg.config.rest.Expected;
import lombok.Data;

@Data
public class RestMock {

    private String name;
    private Expected expected;
    private MockResponse response;
}
