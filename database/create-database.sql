CREATE SCHEMA `tripletclothes` ;
use tripletclothes;
CREATE TABLE `Users` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(255) NOT NULL,
	`email` VARCHAR(255) NOT NULL UNIQUE,
	`avatar` VARCHAR(255) NOT NULL,
	`phone` VARCHAR(255) NOT NULL,
	`address` VARCHAR(255) NOT NULL,
	`password_digest` VARCHAR(255) NOT NULL,
	`role` BINARY NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `Products` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(255) NOT NULL,
	`description` VARCHAR(255) NOT NULL,
	`image` VARCHAR(255) NOT NULL,
	`quantity` INT NOT NULL,
	`price` INT NOT NULL,
	`category_id` INT NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `Orders` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`user_id` INT NOT NULL,
	`receiver_name` VARCHAR(255) NOT NULL,
	`receiver_address` VARCHAR(255) NOT NULL,
	`receiver_phone` VARCHAR(255) NOT NULL,
	`status` INT NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `Categories` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(255) NOT NULL,
	`parent_id` INT NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `OrderItems` (
	`id` INT NOT NULL,
	`order_id` INT NOT NULL,
	`product_id` INT NOT NULL,
	`quantity` INT NOT NULL,
	`price_unit` INT NOT NULL,
	`price_total` INT NOT NULL
);

CREATE TABLE `Rates` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`rating` INT NOT NULL,
	`product_id` INT NOT NULL,
	`content` VARCHAR(255) NOT NULL,
	`user_id` INT NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `Suggests` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(255) NOT NULL,
	`image` VARCHAR(255) NOT NULL,
	`description` VARCHAR(255) NOT NULL,
	`price` INT NOT NULL,
	`status` INT NOT NULL,
	`user_id` INT NOT NULL,
	PRIMARY KEY (`id`)
);

ALTER TABLE `Products` ADD CONSTRAINT `Products_fk0` FOREIGN KEY (`category_id`) REFERENCES `Categories`(`id`);

ALTER TABLE `Orders` ADD CONSTRAINT `Orders_fk0` FOREIGN KEY (`user_id`) REFERENCES `Users`(`id`);

ALTER TABLE `OrderItems` ADD CONSTRAINT `OrderItems_fk0` FOREIGN KEY (`order_id`) REFERENCES `Orders`(`id`);

ALTER TABLE `OrderItems` ADD CONSTRAINT `OrderItems_fk1` FOREIGN KEY (`product_id`) REFERENCES `Products`(`id`);

ALTER TABLE `Rates` ADD CONSTRAINT `Rates_fk0` FOREIGN KEY (`product_id`) REFERENCES `Products`(`id`);

ALTER TABLE `Rates` ADD CONSTRAINT `Rates_fk1` FOREIGN KEY (`user_id`) REFERENCES `Users`(`id`);

ALTER TABLE `Suggests` ADD CONSTRAINT `Suggests_fk0` FOREIGN KEY (`user_id`) REFERENCES `Users`(`id`);

