CREATE SCHEMA IF NOT EXISTS `ttbs`;
USE `ttbs`;

CREATE TABLE `users` (
                         `user_id`     INT          NOT NULL AUTO_INCREMENT,
                         `first_name`  VARCHAR(50)  NOT NULL,
                         `last_name`   VARCHAR(50)  NOT NULL,
                         `birth_date`  DATE         NULL,
                         `email`       VARCHAR(100) NOT NULL UNIQUE,
                         `password`    VARCHAR(255) NOT NULL,
                         PRIMARY KEY (`user_id`)
) ENGINE=InnoDB CHARSET=utf8mb4;

CREATE TABLE `routes` (
                          `route_id`            INT       NOT NULL AUTO_INCREMENT,
                          `train_name`          VARCHAR(100) NOT NULL,
                          `source_station`      VARCHAR(100) NOT NULL,
                          `destination_station` VARCHAR(100) NOT NULL,
                          `departure_time`      DATETIME  NOT NULL,
                          `arrival_time`        DATETIME  NOT NULL,
                          PRIMARY KEY (`route_id`)
) ENGINE=InnoDB CHARSET=utf8mb4;

CREATE TABLE `seats` (
                         `seat_id`     INT         NOT NULL AUTO_INCREMENT,
                         `seat_number` VARCHAR(10) NOT NULL,
                         PRIMARY KEY (`seat_id`)
) ENGINE=InnoDB CHARSET=utf8mb4;

CREATE TABLE `route_seats` (
                               `route_id`    INT        NOT NULL,
                               `seat_id`     INT        NOT NULL,
                               `is_available` TINYINT(1) NOT NULL DEFAULT 1,
                               PRIMARY KEY (`route_id`,`seat_id`),
                               FOREIGN KEY (`route_id`) REFERENCES `routes`(`route_id`) ON DELETE CASCADE,
                               FOREIGN KEY (`seat_id`)  REFERENCES `seats`(`seat_id`)  ON DELETE CASCADE
) ENGINE=InnoDB CHARSET=utf8mb4;

CREATE TABLE `tickets` (
                           `ticket_id`    INT       NOT NULL AUTO_INCREMENT,
                           `user_id`      INT       NOT NULL,
                           `route_id`     INT       NOT NULL,
                           `seat_id`      INT       NOT NULL,
                           `booking_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           PRIMARY KEY (`ticket_id`),
                           FOREIGN KEY (`user_id`)   REFERENCES `users`(`user_id`)   ON DELETE CASCADE,
                           FOREIGN KEY (`route_id`,`seat_id`)
                               REFERENCES `route_seats`(`route_id`,`seat_id`)
                               ON DELETE RESTRICT
) ENGINE=InnoDB CHARSET=utf8mb4;

CREATE TABLE `payments` (
                            `payment_id`     INT             NOT NULL AUTO_INCREMENT,
                            `ticket_id`      INT             NOT NULL UNIQUE,
                            `amount`         DECIMAL(10,2)   NOT NULL,
                            `payment_method` VARCHAR(50)     NOT NULL,
                            `payment_status` ENUM('PENDING','COMPLETED','FAILED') NOT NULL
                    DEFAULT 'PENDING',
                            `payment_time`   TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            PRIMARY KEY (`payment_id`),
                            FOREIGN KEY (`ticket_id`) REFERENCES `tickets`(`ticket_id`) ON DELETE CASCADE
) ENGINE=InnoDB CHARSET=utf8mb4;

ALTER TABLE `users`
    ADD `is_admin` TINYINT(1) NOT NULL DEFAULT 0;


INSERT INTO `users` (`first_name`, `last_name`, `email`, `password`, `is_admin`)
VALUES ('Admin', 'User', 'admin@gmail.com', 'admin', 1);
