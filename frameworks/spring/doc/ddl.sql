/*
Navicat MySQL Data Transfer

Source Server         : win7
Source Server Version : 50719
Source Host           : localhost:3306

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

*/

-- ----------------------------
-- Table datastructure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `oid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL ,
  `age` int(3) DEFAULT 0,
  `created_time` datetime DEFAULT current_timestamp ,
  `updated_time` datetime default current_timestamp ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table datastructure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `oid` int(11) NOT NULL AUTO_INCREMENT,
  `roleName` varchar(50) DEFAULT NULL,
  `created_time` datetime DEFAULT current_timestamp ,
  `updated_time` datetime default current_timestamp ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;