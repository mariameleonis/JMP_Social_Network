package com.example.spring.jdbc.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
  private long id;
  private String name;
  private String surname;
  private LocalDate birthdate;

}

