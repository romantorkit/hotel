DROP TABLE IF EXISTS booking_additional_service;
DROP TABLE IF EXISTS additional_service;
DROP TABLE IF EXISTS booking;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS room;

CREATE TABLE public.user (
  user_id BIGINT AUTO_INCREMENT NOT NULL,
  user_name VARCHAR(20) NOT NULL,
  password VARCHAR(120) NOT NULL,
  first_name VARCHAR(30),
  last_name VARCHAR(30),
  registered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (user_id)
);

CREATE TABLE public.room (
  room_id BIGINT AUTO_INCREMENT NOT NULL,
  room_number INT NOT NULL,
  category VARCHAR(20),
  price INT NOT NULL,
  PRIMARY KEY (room_id)
);

CREATE TABLE public.booking (
  booking_id BIGINT AUTO_INCREMENT NOT NULL,
  start TIMESTAMP,
  end TIMESTAMP,
  amount INT,
  room_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  PRIMARY KEY (booking_id),
  FOREIGN KEY (room_id) REFERENCES room (room_id),
  FOREIGN KEY (user_id) REFERENCES user (user_id)
);

CREATE TABLE additional_service (
  service_id BIGINT AUTO_INCREMENT NOT NULL,
  service_name VARCHAR(10) NOT NULL,
  price INT NOT NULL,
  charge_period VARCHAR(10) NOT NULL,
  PRIMARY KEY (service_id)
);

CREATE TABLE booking_additional_service (
  booking_id BIGINT NOT NULL,
  service_id BIGINT NOT NULL,
  CONSTRAINT FK_BOOKINGS_ID FOREIGN KEY (booking_id) REFERENCES booking (booking_id),
  CONSTRAINT FK_SERVICES_ID FOREIGN KEY (service_id) REFERENCES additional_service (service_id)
);

INSERT INTO room (room_number, category, price) VALUES (1, 'SINGLE', 100000);
INSERT INTO room (room_number, category, price) VALUES (2, 'DOUBLE', 200000);
INSERT INTO room (room_number, category, price) VALUES (3, 'FAMILY', 300000);
INSERT INTO room (room_number, category, price) VALUES (4, 'PRESIDENT', 400000);

INSERT INTO user (user_name, password, first_name, last_name) VALUES ('tort', '$2a$10$fkdCewu6ob/KEHnmeRHGpegT3UQDHet3.uflaXtk7LQlUjpZgNZYa', 'Roman', 'Torkit');

INSERT INTO additional_service (service_name, price, charge_period) VALUES ('Breakfast', 15000, 'DAILY');
INSERT INTO additional_service (service_name, price, charge_period) VALUES ('Cleaning', 10000, 'DAILY');
INSERT INTO additional_service (service_name, price, charge_period) VALUES ('Laundry', 5000, 'ONE_TIME');

