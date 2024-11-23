CREATE TABLE IF NOT EXISTS CURRENT_INGREDIENTS(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    count INT NOT NULL
);

CREATE TABLE IF NOT EXISTS SAVED_INGREDIENTS(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    order_value INT NOT NULL
);

CREATE TABLE IF NOT EXISTS USERS(
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

INSERT INTO USERS (username, password) VALUES ('iendjei', '$2a$10$WT2PM4WDTB9XFPo.iyFbHeJ7dPbALpLttzqbfDukaPy9zV96huEbC');

INSERT INTO CURRENT_INGREDIENTS (name, count) VALUES
('Cucumber', 6),
('Lettuce', 12),
('Onion', 8),
('Cucumber', 9),
('Tomato', 10);

INSERT INTO SAVED_INGREDIENTS (name, order_value) VALUES
('Tomato', 1),
('Cucumber', 2),
('Onion', 3),
('Lettuce', 4),
('Carrot', 5);