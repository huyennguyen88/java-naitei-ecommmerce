DROP SCHEMA IF EXISTS `tripletclothes`;
CREATE SCHEMA `tripletclothes` ;
use tripletclothes;
CREATE TABLE `Users` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(255) NOT NULL,
	`email` VARCHAR(255) NOT NULL UNIQUE,
	`avatar` TEXT,
	`phone` VARCHAR(255),
	`address` TEXT,
	`password_digest` VARCHAR(255) NOT NULL,
	`role` ENUM('ADMIN','USER') NOT NULL,
    `create_time` timestamp default current_timestamp,
    `update_time` timestamp default current_timestamp on update current_timestamp,
	`delete_time` timestamp null,
	PRIMARY KEY (`id`)
);

CREATE TABLE `Products` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(255) NOT NULL,
	`description` TEXT,
	`image` TEXT NOT NULL,
	`quantity` INT NOT NULL,
	`price` decimal(15,0) NOT NULL,
	`category_id` INT NOT NULL,
	`rate_avg` double,
    `create_time` timestamp default current_timestamp,
    `update_time` timestamp default current_timestamp on update current_timestamp,
    `delete_time` timestamp null,
	PRIMARY KEY (`id`)
);

CREATE TABLE `Orders` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`user_id` INT NOT NULL,
	`receiver_name` VARCHAR(255) NOT NULL,
	`receiver_address` VARCHAR(255) NOT NULL,
	`receiver_phone` VARCHAR(255) NOT NULL,
	`status` ENUM('PENDING','ACCEPTED','REJECTED','CANCELED') NOT NULL DEFAULT 'PENDING',
    `create_time` timestamp default current_timestamp,
    `update_time` timestamp default current_timestamp on update current_timestamp,
	PRIMARY KEY (`id`)
);

CREATE TABLE `Categories` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(255) NOT NULL,
	`parent_id` INT NULL,
    `create_time` timestamp default current_timestamp,
    `update_time` timestamp default current_timestamp on update current_timestamp,
    `delete_time` timestamp null,
	PRIMARY KEY (`id`)
);

CREATE TABLE `OrderItems` (
	`id` INT NOT NULL,
	`order_id` INT NOT NULL,
	`product_id` INT NOT NULL,
	`quantity` INT NOT NULL,
	`price_unit` decimal(15,0) NOT NULL,
	`price_total` decimal(15,0) NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `Rates` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`value` ENUM('ONE','TWO','THREE','FOUR','FIVE') NOT NULL,
	`product_id` INT NOT NULL,
	`content` TEXT,
	`user_id` INT NOT NULL,
    `create_time` timestamp default current_timestamp,
    `update_time` timestamp default current_timestamp on update current_timestamp,
	PRIMARY KEY (`id`)
);

CREATE TABLE `Suggests` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(255) NOT NULL,
	`image` VARCHAR(255),
	`description` TEXT,
	`price` decimal(15,0) NOT NULL,
	`status` ENUM('PENDING','ACCEPTED','REJECTED','CANCELED') NOT NULL,
    `create_time` timestamp default current_timestamp,
    `update_time` timestamp default current_timestamp on update current_timestamp,
	`user_id` INT NOT NULL,
	PRIMARY KEY (`id`)
);

ALTER TABLE `Products` ADD CONSTRAINT `Products_fk0` FOREIGN KEY (`category_id`) REFERENCES `Categories`(`id`) on delete cascade;

ALTER TABLE `Categories` ADD CONSTRAINT `Categories_fk0` FOREIGN KEY (`parent_id`) REFERENCES `Categories`(`id`) on delete cascade;

ALTER TABLE `Orders` ADD CONSTRAINT `Orders_fk0` FOREIGN KEY (`user_id`) REFERENCES `Users`(`id`);

ALTER TABLE `OrderItems` ADD CONSTRAINT `OrderItems_fk0` FOREIGN KEY (`order_id`) REFERENCES `Orders`(`id`);

ALTER TABLE `OrderItems` ADD CONSTRAINT `OrderItems_fk1` FOREIGN KEY (`product_id`) REFERENCES `Products`(`id`);

ALTER TABLE `Rates` ADD CONSTRAINT `Rates_fk0` FOREIGN KEY (`product_id`) REFERENCES `Products`(`id`);

ALTER TABLE `Rates` ADD CONSTRAINT `Rates_fk1` FOREIGN KEY (`user_id`) REFERENCES `Users`(`id`);

ALTER TABLE `Suggests` ADD CONSTRAINT `Suggests_fk0` FOREIGN KEY (`user_id`) REFERENCES `Users`(`id`);
