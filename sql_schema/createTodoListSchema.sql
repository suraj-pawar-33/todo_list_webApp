
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema sp_database
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `sp_database` ;

-- -----------------------------------------------------
-- Schema sp_database
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `sp_database` DEFAULT CHARACTER SET utf8 ;
USE `sp_database` ;


-- -----------------------------------------------------
-- Table `sp_database`.`service_users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sp_database`.`service_users` ;

CREATE TABLE IF NOT EXISTS `sp_database`.`service_users` (
 `user_key` INT NOT NULL AUTO_INCREMENT,
 `user_name` VARCHAR(255) UNIQUE NOT NULL,
 `user_id` VARCHAR(255) NOT NULL,
 `user_pass` VARCHAR(255) NOT NULL,
 `creation_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 `last_modified_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 PRIMARY KEY (`user_key`)
 );

ALTER TABLE `sp_database`.`service_users` AUTO_INCREMENT=4646;

insert into `sp_database`.`service_users` (`user_name`, `user_id`, `user_pass`) values ('Admin', 'admin', 'admin');
insert into `sp_database`.`service_users` (`user_name`, `user_id`, `user_pass`) values ('Suraj', 'suraj', 'suraj');

-- -----------------------------------------------------
-- Table `sp_database`.`list`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sp_database`.`list` ;

CREATE TABLE IF NOT EXISTS `sp_database`.`list` (
 `list_key` INT NOT NULL AUTO_INCREMENT,
 `position` INT NOT NULL DEFAULT 1,
 `list_name` VARCHAR(255) NOT NULL,
 `user_key` INT NOT NULL,
 `star` BIT(1) DEFAULT 0,
 `status` BIT(1) DEFAULT 0,
 `creation_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 `last_modified_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 PRIMARY KEY (`list_key`),
 UNIQUE `unique_index`(`list_name`, `user_key`),
 CONSTRAINT `user_key` FOREIGN KEY (`user_key`) REFERENCES `sp_database`.`service_users` (`user_key`) ON DELETE RESTRICT ON UPDATE RESTRICT
 );

ALTER TABLE `sp_database`.`list` AUTO_INCREMENT=2121;

-- -----------------------------------------------------
-- Table `sp_database`.`todo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sp_database`.`todo` ;

CREATE TABLE IF NOT EXISTS `sp_database`.`todo` (
 `todo_key` INT NOT NULL AUTO_INCREMENT,
 `position` INT NOT NULL DEFAULT 1,
 `todo_note` VARCHAR(255) NOT NULL,
 `list_key` INT NOT NULL,
 `star` BIT(1) DEFAULT 0,
 `status` BIT(1) DEFAULT 0,
 `selected` BIT(1) DEFAULT 0,
 `creation_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 `last_modified_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 PRIMARY KEY (`todo_key`),
 CONSTRAINT `list_key` FOREIGN KEY (`list_key`) REFERENCES `sp_database`.`list` (`list_key`) ON DELETE RESTRICT ON UPDATE RESTRICT
 );

ALTER TABLE `sp_database`.`todo` AUTO_INCREMENT=11111;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
