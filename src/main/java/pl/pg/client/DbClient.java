package pl.pg.client;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import pl.pg.mapper.DefaultMapper;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class DbClient {

    private String driverClassName;
    private String url;
    private String dbUsername;
    private String dbPassword;
    private JdbcTemplate jdbcTemplate;

    public DbClient(String driverClassName, String url, String dbUsername, String dbPassword) {
        this.driverClassName = driverClassName;
        this.url = url;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
        this.jdbcTemplate  = new JdbcTemplate(createDataSource());
    }

    public <T> List<T> query(String query, Class<T> clazz) {
        return jdbcTemplate.query(query, new DefaultMapper<>(clazz));
    }

    public <T> List<T> query(String query, RowMapper mapper) {
        return jdbcTemplate.query(query, mapper);
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
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
}
