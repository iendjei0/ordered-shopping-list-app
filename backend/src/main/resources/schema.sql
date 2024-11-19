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

INSERT INTO CURRENT_INGREDIENTS (name, count) VALUES
('Tomato', 10),
('Cucumber', 15),
('Onion', 8),
('Lettuce', 12),
('Carrot', 20),
('Garlic', 25),
('Bell Pepper', 7),
('Spinach', 30);

INSERT INTO SAVED_INGREDIENTS (name, order_value) VALUES
('Tomato', 1),
('Cucumber', 2),
('Onion', 3),
('Lettuce', 4),
('Carrot', 5);