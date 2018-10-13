package pl.pg.engine.config.mock;

import pl.pg.engine.config.rest.Expected;
import lombok.Data;

@Data
public class RestMock {

    private String name;
    private Expected expected;
    private MockResponse response;
}
