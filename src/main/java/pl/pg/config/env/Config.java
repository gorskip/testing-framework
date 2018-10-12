package pl.pg.config.env;

import lombok.Data;

import java.util.List;

@Data
public class Config {

    private List<DbConfig> dbConfigs;


}
