package ua.com.alevel.impl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ua.com.alevel.DataSource;
import ua.com.alevel.PropertiesReader;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DataSourceImpl implements DataSource {
    private static final HikariConfig config;
    private static final HikariDataSource dataSource;
    private static final PropertiesReader reader = new PropertiesReaderImpl();
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Properties properties = reader.readProperties();
        config = new HikariConfig();
        config.setDataSourceProperties(properties);
        config.setJdbcUrl(properties.getProperty("url"));
        config.setUsername(properties.getProperty("username"));
        config.setPassword(properties.getProperty("password"));
        dataSource = new HikariDataSource(config);
    }
    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
