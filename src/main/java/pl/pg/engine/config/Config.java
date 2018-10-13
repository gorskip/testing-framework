package pl.pg.engine.config;

import lombok.Data;

import java.util.List;

@Data
public class Config {

    private List<DbConfig> dbConfigs;
}
