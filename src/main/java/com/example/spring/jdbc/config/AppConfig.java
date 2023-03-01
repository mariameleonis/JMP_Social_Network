package com.example.spring.jdbc.config;

import java.util.Objects;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan("com.example.spring.jdbc")
public class AppConfig {

  public static final String JDBC_URL = System.getenv("JDBC_URL");
  public static final String USER = System.getenv("JDBC_USER");
  public static final String PASSWORD = System.getenv("JDBC_PASSWORD");

  @Bean
  public DataSource dataSource(Environment env) {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(
        Objects.requireNonNull(env.getProperty("spring.datasource.driver-class-name")));
    dataSource.setUrl(JDBC_URL);
    dataSource.setUsername(USER);
    dataSource.setPassword(PASSWORD);
    return dataSource;
  }

  @Bean
  public JdbcTemplate jdbcTemplate(DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

  @Bean
  public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
    return new NamedParameterJdbcTemplate(dataSource);
  }

  @Bean
  public ResourceLoader resourceLoader() {
    return new DefaultResourceLoader();
  }

}
