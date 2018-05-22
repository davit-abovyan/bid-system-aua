CREATE TABLE user(
  id INT AUTO_INCREMENT PRIMARY KEY ,
  email VARCHAR(255) NOT NULL UNIQUE ,
  password VARCHAR(255) NOT NULL,
  user_name VARCHAR(100) DEFAULT "User"
);

CREATE TABLE items (
  id VARCHAR(5) PRIMARY KEY ,
  name VARCHAR(255) not NULL,
  description TEXT,
  price DOUBLE DEFAULT 0.0,
  picture_url VARCHAR(255) DEFAULT "/img/items/default_image.jpg"
);

CREATE TABLE history(
  id INT AUTO_INCREMENT PRIMARY KEY ,
  item_id VARCHAR(5) NOT NULL,
  user_id int NOT NULL,
  sale_date DATE DEFAULT now(),
  CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES user(id),
  CONSTRAINT item_id_fk FOREIGN KEY (item_id) REFERENCES items(id)
);
