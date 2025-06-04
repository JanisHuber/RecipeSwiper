USE recipeswiper;

DROP TABLE IF EXISTS recipesToGroup;
DROP TABLE IF EXISTS selectedRecipes;
DROP TABLE IF EXISTS userToGroup;
DROP TABLE IF EXISTS recipe_votes;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS group_recipes;
DROP TABLE IF EXISTS recipes;
DROP TABLE IF EXISTS `groups`;

CREATE TABLE `groups`
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    group_token VARCHAR(255) NOT NULL UNIQUE,
    name        VARCHAR(255) NOT NULL
);

CREATE TABLE users
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    username   VARCHAR(255) NOT NULL,
    user_token VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE userToGroup
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    user_id  INT NOT NULL,
    group_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (group_id) REFERENCES `groups` (id)
);

CREATE TABLE recipes
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    title        VARCHAR(255) NOT NULL,
    description  TEXT,
    ingredients  TEXT,
    instructions TEXT,
    image_url    VARCHAR(255)
);

CREATE TABLE group_recipes
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    recipe_id INT NOT NULL,
    group_id  INT NOT NULL,
    FOREIGN KEY (recipe_id) REFERENCES recipes (id),
    FOREIGN KEY (group_id) REFERENCES `groups` (id)
);

CREATE TABLE recipe_votes
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    user_id   INT                      NOT NULL,
    recipe_id INT                      NOT NULL,
    group_id  INT                      NOT NULL,
    vote      VARCHAR (10) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (recipe_id) REFERENCES recipes (id),
    FOREIGN KEY (group_id) REFERENCES `groups` (id)
);