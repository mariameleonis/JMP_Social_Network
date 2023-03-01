package com.example.spring.jdbc.service;

import com.example.spring.jdbc.dao.UserDao;
import com.example.spring.jdbc.model.User;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserService {

  private final UserDao userDao;

  public List<User> getUsersByFriendsCountMoreAndLikesCountMoreOnDate(int friendsCount,
      int likesCount, LocalDate date) {
    return userDao.getUsersByFriendsCountMoreAndLikesCountMoreOnDate(friendsCount, likesCount,
        date);
  }

}
