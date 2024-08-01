CREATE TABLE IF NOT EXISTS CURRENT_INGREDIENTS(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    count INT NOT NULL
);

CREATE TABLE IF NOT EXISTS SAVED_INGREDIENTS(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    orderValue INT NOT NULL
    -- FOREIGN KEY (previousId) REFERENCES SAVED_INGREDIENTS(id)
);

INSERT INTO CURRENT_INGREDIENTS (name, count) VALUES ('egg', 1);
INSERT INTO CURRENT_INGREDIENTS (name, count) VALUES ('sugar', 3);
INSERT INTO CURRENT_INGREDIENTS (name, count) VALUES ('butter', 1);
INSERT INTO CURRENT_INGREDIENTS (name, count) VALUES ('flour', 5);

INSERT INTO SAVED_INGREDIENTS (name, orderValue) VALUES ('egg', 1);
INSERT INTO SAVED_INGREDIENTS (name, orderValue) VALUES ('flour', 2);
INSERT INTO SAVED_INGREDIENTS (name, orderValue) VALUES ('milk', 3);
INSERT INTO SAVED_INGREDIENTS (name, orderValue) VALUES ('sugar', 4);
INSERT INTO SAVED_INGREDIENTS (name, orderValue) VALUES ('butter', 5);
INSERT INTO SAVED_INGREDIENTS (name, orderValue) VALUES ('salt', 6);
INSERT INTO SAVED_INGREDIENTS (name, orderValue) VALUES ('pepper', 7);


