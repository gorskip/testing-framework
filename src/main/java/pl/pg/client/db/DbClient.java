package pl.pg.client.db;

import org.apache.commons.dbutils.QueryRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import pl.pg.engine.config.DbConfig;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class DbClient {

    private String driverClassName;
    private String url;
    private String dbUsername;
    private String dbPassword;
    private JdbcTemplate jdbcTemplate;
    private  QueryRunner queryRunner;
    private final DataSource dataSource;

    public DbClient(String driverClassName, String url, String dbUsername, String dbPassword) {
        this.driverClassName = driverClassName;
        this.url = url;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
        this.jdbcTemplate  = new JdbcTemplate(createDataSource());
        this.dataSource = createDataSource();
        this.queryRunner = new QueryRunner(dataSource);
    }

    public DbClient(DbConfig dbConfig) {
        this(   dbConfig.getDriverClassName(),
                dbConfig.getUrl(),
                dbConfig.getUsername(),
                dbConfig.getPassword());
    }

    public DbClient(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.queryRunner = new QueryRunner(dataSource);
    }

    public <T> List<T> query(String query, RowMapper mapper) {
        return jdbcTemplate.query(query, mapper);
    }

    private DataSource createDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);
        return dataSource;
    }

    public void closeConnection() throws SQLException {
        jdbcTemplate.getDataSource().getConnection().close();
    }

    public <T> List<T> query(String query, Class<T> clazz) throws SQLException {
        return new QueryExecutor<T>(queryRunner)
                .execute(query, clazz);
    }

    public <T> DbResponse<T> execute(String query, Class<T> clazz) throws SQLException {
        return new QueryExecutor<T>(queryRunner)
                .getResponse(query, clazz);
    }
}
