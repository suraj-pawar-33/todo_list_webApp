
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema todo_list
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `todo_list` ;

-- -----------------------------------------------------
-- Schema todo_list
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `todo_list` DEFAULT CHARACTER SET utf8 ;
USE `todo_list` ;


-- -----------------------------------------------------
-- Table `todo_list`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `todo_list`.`user` ;

CREATE TABLE IF NOT EXISTS `todo_list`.`user` (
 `user_key` INT NOT NULL AUTO_INCREMENT,
 `user_name` VARCHAR(255) NOT NULL,
 `user_id` VARCHAR(255) NOT NULL,
 `user_pass` VARCHAR(255) NOT NULL,
 `creation_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 `last_modified_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 PRIMARY KEY (`user_key`)
 );

ALTER TABLE `todo_list`.`user` AUTO_INCREMENT=4646;

-- -----------------------------------------------------
-- Table `todo_list`.`list`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `todo_list`.`list` ;

CREATE TABLE IF NOT EXISTS `todo_list`.`list` (
 `list_key` INT NOT NULL AUTO_INCREMENT,
 `position` INT NOT NULL DEFAULT 1,
 `list_name` VARCHAR(255) NOT NULL,
 `user_key` INT NOT NULL,
 `star` BIT(1) DEFAULT 0,
 `creation_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 `last_modified_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 PRIMARY KEY (`list_key`),
 CONSTRAINT `user_key` FOREIGN KEY (`user_key`) REFERENCES `todo_list`.`user` (`user_key`) ON DELETE RESTRICT ON UPDATE RESTRICT
 );

ALTER TABLE `todo_list`.`list` AUTO_INCREMENT=2121;

-- -----------------------------------------------------
-- Table `todo_list`.`todo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `todo_list`.`todo` ;

CREATE TABLE IF NOT EXISTS `todo_list`.`todo` (
 `todo_key` INT NOT NULL AUTO_INCREMENT,
 `position` INT NOT NULL DEFAULT 1,
 `todo_text` VARCHAR(255) NOT NULL,
 `list_key` INT NOT NULL,
 `star` BIT(1) DEFAULT 0,
 `selected` BIT(1) DEFAULT 0,
 `creation_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 `last_modified_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 PRIMARY KEY (`todo_key`),
 CONSTRAINT `list_key` FOREIGN KEY (`list_key`) REFERENCES `todo_list`.`list` (`list_key`) ON DELETE RESTRICT ON UPDATE RESTRICT
 );

ALTER TABLE `todo_list`.`todo` AUTO_INCREMENT=11111;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
