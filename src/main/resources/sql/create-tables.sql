DROP TABLE IF EXISTS likes;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS friendships;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
                       id INT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       surname VARCHAR(255) NOT NULL,
                       birthdate DATE NOT NULL);

CREATE TABLE friendships (
                             userid1 INT,
                             userid2 INT,
                             timestamp TIMESTAMP,
                             FOREIGN KEY (userid1) REFERENCES Users(id),
                             FOREIGN KEY (userid2) REFERENCES Users(id),
                             PRIMARY KEY (userid1, userid2));

CREATE TABLE posts (
                       id INT PRIMARY KEY,
                       userId INT,
                       text VARCHAR(255) NOT NULL,
                       timestamp TIMESTAMP,
                       FOREIGN KEY (userId) REFERENCES Users(id));

CREATE TABLE IF NOT EXISTS likes (
                                     postid INT,
                                     userid INT,
                                     timestamp TIMESTAMP,
                                     FOREIGN KEY (postid) REFERENCES Posts(id),
    FOREIGN KEY (userid) REFERENCES Users(id),
    PRIMARY KEY (postid, userid, timestamp));