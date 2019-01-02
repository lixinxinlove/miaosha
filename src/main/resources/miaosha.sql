/*
Navicat MySQL Data Transfer

Source Server         : 127
Source Server Version : 50710
Source Host           : localhost:3306
Source Database       : miaosha

Target Server Type    : MYSQL
Target Server Version : 50710
File Encoding         : 65001

Date: 2019-01-02 17:32:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL DEFAULT '',
  `price` double(10,2) NOT NULL DEFAULT '0.00',
  `description` varchar(500) NOT NULL DEFAULT '',
  `sales` int(11) NOT NULL DEFAULT '0',
  `img_url` varchar(255) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for item_stock
-- ----------------------------
DROP TABLE IF EXISTS `item_stock`;
CREATE TABLE `item_stock` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stock` int(11) NOT NULL DEFAULT '0',
  `item_id` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
  `id` varchar(255) NOT NULL DEFAULT '' COMMENT '订单id',
  `user_id` int(11) NOT NULL DEFAULT '0',
  `item_id` int(11) NOT NULL DEFAULT '0',
  `item_price` double(10,2) NOT NULL DEFAULT '0.00',
  `amount` int(11) NOT NULL DEFAULT '0',
  `order_price` double(10,2) NOT NULL DEFAULT '0.00',
  `promo_id` int(11) NOT NULL DEFAULT '0' COMMENT '秒杀商品id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for promo
-- ----------------------------
DROP TABLE IF EXISTS `promo`;
CREATE TABLE `promo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `promo_name` varchar(255) NOT NULL,
  `start_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `item_id` int(11) NOT NULL DEFAULT '0',
  `promo_item_price` double(10,2) NOT NULL DEFAULT '0.00',
  `end_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sequence_info
-- ----------------------------
DROP TABLE IF EXISTS `sequence_info`;
CREATE TABLE `sequence_info` (
  `name` varchar(255) NOT NULL,
  `current_value` int(11) NOT NULL DEFAULT '0',
  `step` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '用户名',
  `gender` tinyint(4) NOT NULL COMMENT '性别 0 女 1男',
  `age` int(11) NOT NULL DEFAULT '0' COMMENT '年龄',
  `telphone` varchar(255) NOT NULL DEFAULT '' COMMENT '电话号码',
  `register_mode` varchar(255) NOT NULL DEFAULT '' COMMENT '注册类型',
  `third_party_id` varchar(64) NOT NULL DEFAULT '' COMMENT '三方平台id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `telphone_unique_index` (`telphone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_password
-- ----------------------------
DROP TABLE IF EXISTS `user_password`;
CREATE TABLE `user_password` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `encrpt_password` varchar(255) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
