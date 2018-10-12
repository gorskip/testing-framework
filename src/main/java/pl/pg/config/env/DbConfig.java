package pl.pg.config.env;

import lombok.Data;

@Data
public class DbConfig {

    private String driverClassName;
    private String url;
    private String dbUsername;
    private String dbPassword;
}
