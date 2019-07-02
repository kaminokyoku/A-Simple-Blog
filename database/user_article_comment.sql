DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS article;
DROP TABLE IF EXISTS user;
CREATE TABLE IF NOT EXISTS user
(
  userId          INT            NOT NULL AUTO_INCREMENT,
  userName        VARCHAR(64)    NOT NULL,
  birth_year      INT            NOT NULL,
  birth_month     INT            NOT NULL,
  birth_date      INT            NOT NULL,
  country         VARCHAR(30)    NOT NULL,
  description     VARCHAR(255)   NOT NULL,
  firstname       VARCHAR(20)    NOT NULL,
  lastname        VARCHAR(20)    NOT NULL,
  email           VARCHAR(128)   NOT NULL,
  avatarName      VARCHAR(255)   NOT NULL,
  hashed_password VARCHAR(255)   NOT NULL,
  salt            VARBINARY(999) NOT NULL,
  salt_length     INT            NOT NULL,
  iteration       INT            NOT NULL,
  token           VARCHAR(255),
  PRIMARY KEY (userId)
);

ALTER TABLE user
  AUTO_INCREMENT = 1000;

CREATE TABLE IF NOT EXISTS article
(
  articleId INT         NOT NULL AUTO_INCREMENT,
  title     VARCHAR(64) NOT NULL,
  date      DATETIME    NOT NULL,
  content   TEXT        NOT NULL,
  isShown   BOOLEAN     NOT NULL,
  userId    INT         NOT NULL,
  PRIMARY KEY (articleId),
  FOREIGN KEY (userId) REFERENCES user (userId)
);


CREATE TABLE IF NOT EXISTS comment
(
  commentId INT          NOT NULL AUTO_INCREMENT,
  parentId  INT,
  content   VARCHAR(128) NOT NULL,
  date      DATETIME     NOT NULL,
  articleId INT          NOT NULL,
  userId    INT          NOT NULL,
  isShown   BOOLEAN      NOT NULL,
  userName  VARCHAR(64)  NOT NULL,
  userAvatar VARCHAR(255) NOT NULL,
  PRIMARY KEY (commentId),
  FOREIGN KEY (articleId) REFERENCES article (articleId) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (userId) REFERENCES user (userId) ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO user (userId, userName, birth_year, birth_month, birth_date, country, description, firstname, lastname,
                  email, avatarName, hashed_password, salt, salt_length, iteration, token)
VALUES (1, 'Wade723', 1989, 6, 1, 'china', 'aaaaaaaaaaa', 'wade', 'lu', 'wade@gmail.com', 'abc', '122341343432432431',
        '123', '1', '2', '122342'),
       (2, 'asu', 1994, 12, 20, 'china', 'bbbbbbbbbbb', 'teresa', 'su', 'su@gmail.com', 'def', 'e9xxQJiKVuLc/dEW7bQvNogHGxNKgSAkJTNctQP6QH5RlSRkIZh7a7VQl3KUQSR/4GoAuULKX07BiJW74KGgUg==',
        '0xff3f', '2', '4', '152342'),
       (3, 'amit', 2010, 7, 3, 'china', 'ccccccccccc', 'amit', 'sth', 'su@gmail.com', 'hij', 'yftsmgZ8eCAz8YwvFdIQqKuffrYx3SeG3x2p+0rvbB18+vXdaO5YWElXs0smoR6nJudYbGee7TiCubO2rYdThw==',
        '0x13', '1', '4', '126342');

INSERT INTO article (title, date, content, isShown, userId)
VALUES ('Auckland', '2019-6-3 03:07:30', 'djgnsljfkljfowsd', true, 1),
       ('Beijing', '2019-6-3 03:11:21', 'djgnsljfkljfsfgwsd', true, 2),
       ('Dubai', '2019-6-3 15:10:01', 'djgnsljfdfgkljfowsd', true, 3),
       ('A good article', '2019-6-7 10:50:31', 'Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of "de Finibus Bonorum et Malorum" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, "Lorem ipsum dolor sit amet..", comes from a line in section 1.10.32.', true, 1),
       ('I think our project is good', '2019-6-7 10:52:01', 'It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using ''Content here, content here'', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for ''lorem ipsum'' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).', true, 2),
       ('chernobyl is a good dark tourism destination', '2019-6-7 10:55:3', 'There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don''t look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn''t anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc.', true, 3);

/*INSERT INTO comment (parentId, content, date, articleId, userId, isShown, userName, userAvatar)
VALUES (null, 'It is a good article','2019-6-3', 1, 1, true, 'Wade723', 'abc'),
       (null, 'Cool','2019-6-3', 1, 2, true),
       (2, 'I agree','2019-6-3', 1, 1, true),
       (1, 'I don\'t think so','2019-6-3', 1, 3, true);*/




