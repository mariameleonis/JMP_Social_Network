package com.example.spring.jdbc.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SqlScriptRunner {

  @Value("${create.tables.script}")
  private String createTablesScript;

  @Value("${insert.data.script}")
  private String insertDataScript;

  public static final String SQL_RESOURCE_DIRECTORY = "classpath:sql/";
  private final JdbcTemplate jdbcTemplate;
  private final ResourceLoader resourceLoader;

  public void executeCreateTablesScript() throws IOException {
    executeSqlFromFile(createTablesScript);
  }

  public void executeInsertDataScript() throws IOException {
    executeSqlFromFile(insertDataScript);
  }

  private void executeSqlFromFile(String filename) throws IOException {
    val resource = resourceLoader.getResource(SQL_RESOURCE_DIRECTORY + filename);
    val sql = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

    log.info("Start executing sql script from file " + filename);

    jdbcTemplate.execute(sql);

    log.info("Finish executing sql script from file " + filename);
  }

}
