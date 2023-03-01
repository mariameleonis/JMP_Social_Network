package com.example.spring.jdbc;

import com.example.spring.jdbc.config.AppConfig;
import com.example.spring.jdbc.model.User;
import com.example.spring.jdbc.service.SqlScriptRunner;
import com.example.spring.jdbc.service.UserService;
import java.io.IOException;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Application {

  public static void main(String[] args) throws IOException {
    val context =
        new AnnotationConfigApplicationContext(AppConfig.class);
    val sqlScriptRunner = context.getBean(SqlScriptRunner.class);
    val userService = context.getBean(UserService.class);

    createTables(sqlScriptRunner);
    populateTables(sqlScriptRunner);
    printUserNames(userService);

    context.close();
  }

  private static void createTables(SqlScriptRunner sqlScriptRunner)
      throws IOException {

    log.info("Creating tables");

    sqlScriptRunner.executeCreateTablesScript();

    log.info("All tables created successfully!");
  }

  public static void populateTables(SqlScriptRunner sqlScriptRunner)
      throws IOException {
    log.info("Start to populate tables with test data");

    sqlScriptRunner.executeInsertDataScript();

    log.info("All tables have been populated successfully!");
  }

  public static void printUserNames(UserService userService) {
    val users = userService.getUsersByFriendsCountMoreAndLikesCountMoreOnDate(150, 150,
        LocalDate.of(2025, 3, 31));

    log.info("Users with more than 150 friends and 150 likes in March 2025:");
    for (User user : users) {
      log.info(user.toString());
    }
  }
}