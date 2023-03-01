package com.example.spring.jdbc.dao;

import com.example.spring.jdbc.model.User;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserDao {

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  public List<User> getUsersByFriendsCountMoreAndLikesCountMoreOnDate(int friendsCount, int likesCount, LocalDate date) {
    val paramMap = new HashMap<String, Object>();
    paramMap.put("timestamp", date);
    paramMap.put("likesCount", likesCount);
    paramMap.put("friendsCount", friendsCount);

    return namedParameterJdbcTemplate.query(
        """
            WITH likes_per_user AS (
                SELECT p.userId AS userid, COUNT(*) AS likes_count
                FROM posts p INNER JOIN likes l ON p.id = l.postid
                WHERE l.timestamp <= :timestamp
                GROUP BY p.userId
            ),
            friends_per_user AS (
                SELECT userid, SUM(friends_count) AS friends_count FROM (
                    SELECT userid1 AS userid, COUNT(*) AS friends_count
                    FROM friendships f
                    WHERE f.timestamp <= :timestamp
                    GROUP BY userid1
                    UNION ALL
                    SELECT userid2 AS userid, COUNT(*) AS friends_count
                    FROM friendships f
                    WHERE f.timestamp <= :timestamp
                    GROUP BY userid2
                ) AS friendships_per_user
                GROUP BY userid
            )
            SELECT u.id, u.name, u.surname, u.birthdate
            FROM users u
            INNER JOIN likes_per_user l ON u.id = l.userid
            INNER JOIN friends_per_user f ON u.id = f.userid
            WHERE l.likes_count >= :likesCount AND f.friends_count >= :friendsCount;
        """,
        paramMap,
        new BeanPropertyRowMapper<>(User.class)
    );
  }

}

