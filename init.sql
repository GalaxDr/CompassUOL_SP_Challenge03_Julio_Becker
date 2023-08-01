CREATE DATABASE IF NOT EXISTS auth;
USE auth;

CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255),
    password VARCHAR(255),
    role INT
);

INSERT INTO users (username, password, role)
VALUES ('admin', '$2a$10$EYTmILwlfKymf0nmvBXy0Oxf9N36EAS35j5nnFW/TBiqhvK4UlduW', 0);

CREATE DATABASE IF NOT EXISTS orders;
CREATE DATABASE IF NOT EXISTS products;