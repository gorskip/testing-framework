package pl.pg.engine.config;

import lombok.Data;

@Data
public class DbConfig {

    private String name;
    private String driverClassName;
    private String url;
    private String username;
    private String password;

}
