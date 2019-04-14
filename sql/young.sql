/*
Navicat MySQL Data Transfer

Source Server         : MyEclipse
Source Server Version : 50720
Source Host           : 127.0.0.1:3306
Source Database       : young

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2019-04-14 10:41:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `article_id` varchar(100) NOT NULL,
  `seller_id` varchar(100) NOT NULL,
  `article_title` varchar(100) NOT NULL,
  `article_introl` varchar(100) NOT NULL,
  `article_img_path` varchar(100) NOT NULL,
  `article_type` varchar(100) NOT NULL,
  `article_label` varchar(100) NOT NULL,
  `article_view_num` varchar(100) NOT NULL DEFAULT '0',
  `article_time` varchar(100) NOT NULL,
  PRIMARY KEY (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES ('1351313', '213', '养生堂', '好喝', '/pic/food/base/food_default.png', '美食', '美味', '0', '');

-- ----------------------------
-- Table structure for collect
-- ----------------------------
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect` (
  `collect_id` varchar(255) NOT NULL,
  `collect_user_id` varchar(255) NOT NULL,
  `collect_collector_id` varchar(255) NOT NULL,
  `collect_type` varchar(255) NOT NULL,
  PRIMARY KEY (`collect_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of collect
-- ----------------------------

-- ----------------------------
-- Table structure for early_clock
-- ----------------------------
DROP TABLE IF EXISTS `early_clock`;
CREATE TABLE `early_clock` (
  `early_clock_id` varchar(100) NOT NULL,
  `user_id` varchar(100) NOT NULL,
  `early_clock_time` varchar(100) NOT NULL COMMENT '时间错',
  PRIMARY KEY (`early_clock_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of early_clock
-- ----------------------------
INSERT INTO `early_clock` VALUES ('2', '7824', '8752872');
INSERT INTO `early_clock` VALUES ('20181009180445982', '123', '1539079485982');
INSERT INTO `early_clock` VALUES ('3', '45641', '1538800011');

-- ----------------------------
-- Table structure for food
-- ----------------------------
DROP TABLE IF EXISTS `food`;
CREATE TABLE `food` (
  `food_id` varchar(100) NOT NULL,
  `seller_id` varchar(100) NOT NULL,
  `food_name` varchar(100) NOT NULL,
  `food_img_path` varchar(100) NOT NULL,
  `food_introl` varchar(100) NOT NULL,
  `food_calorie` varchar(100) NOT NULL,
  `food_type` varchar(100) NOT NULL,
  `food_label` varchar(100) NOT NULL,
  `food_price` decimal(10,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`food_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of food
-- ----------------------------
INSERT INTO `food` VALUES ('123', '213', '麻婆豆腐', '/pic/food/base/food_default.png', '好吃', '12312', '123', '麻辣，美味', '0.00');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `message_id` varchar(100) NOT NULL,
  `receiver_id` varchar(100) NOT NULL,
  `sender_id` varchar(100) NOT NULL,
  `message_type` varchar(100) NOT NULL,
  `message_title` varchar(100) NOT NULL,
  `message_introl` varchar(100) NOT NULL,
  `message_img_path` varchar(100) NOT NULL,
  `message_state` varchar(100) NOT NULL DEFAULT '0',
  `message_time` varchar(100) NOT NULL COMMENT '时间撮',
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of message
-- ----------------------------

-- ----------------------------
-- Table structure for seller
-- ----------------------------
DROP TABLE IF EXISTS `seller`;
CREATE TABLE `seller` (
  `seller_id` varchar(100) NOT NULL,
  `seller_type_id` varchar(100) NOT NULL,
  `seller_name` varchar(100) NOT NULL,
  `seller_address` varchar(100) NOT NULL,
  `seller_introl` varchar(100) NOT NULL,
  `seller_label` varchar(100) NOT NULL,
  `seller_img_path` varchar(100) NOT NULL,
  `seller_tel` varchar(100) NOT NULL,
  PRIMARY KEY (`seller_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of seller
-- ----------------------------
INSERT INTO `seller` VALUES ('213', '1', '中国报社', '中国', '专门生产文章', '中国', 'ad', '600000-030303');

-- ----------------------------
-- Table structure for seller_type
-- ----------------------------
DROP TABLE IF EXISTS `seller_type`;
CREATE TABLE `seller_type` (
  `seller_type_id` varchar(100) NOT NULL,
  `seller_type_name` varchar(100) NOT NULL,
  PRIMARY KEY (`seller_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of seller_type
-- ----------------------------
INSERT INTO `seller_type` VALUES ('1', '餐饮');
INSERT INTO `seller_type` VALUES ('2', '健身');
INSERT INTO `seller_type` VALUES ('3', '养生');

-- ----------------------------
-- Table structure for sports_coach
-- ----------------------------
DROP TABLE IF EXISTS `sports_coach`;
CREATE TABLE `sports_coach` (
  `sports_coach_id` varchar(100) NOT NULL,
  `seller_id` varchar(100) NOT NULL,
  `sports_coach_name` varchar(100) NOT NULL,
  `sports_coach_sex` varchar(100) NOT NULL,
  `sports_coach_birthday` varchar(100) NOT NULL,
  `sports_coach_label` varchar(100) NOT NULL,
  `sports_coach_type` varchar(100) NOT NULL,
  `sports_coach_img_path` varchar(100) NOT NULL,
  `sports_coach_introl` varchar(100) NOT NULL DEFAULT '',
  `sport_coach_price` decimal(10,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`sports_coach_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sports_coach
-- ----------------------------

-- ----------------------------
-- Table structure for sport_club
-- ----------------------------
DROP TABLE IF EXISTS `sport_club`;
CREATE TABLE `sport_club` (
  `sport_club_id` varchar(100) NOT NULL,
  `sport_club_header_id` varchar(100) NOT NULL,
  `sport_club_title` varchar(100) NOT NULL,
  `sport_club_start_time` varchar(100) NOT NULL,
  `sport_club_end_time` varchar(100) NOT NULL,
  `sport_club_begin_time` varchar(100) NOT NULL,
  `sport_club_over_time` varchar(100) NOT NULL,
  `sport_club_people_num` varchar(100) NOT NULL,
  `sport_club_menbers_id` varchar(100) NOT NULL,
  `sport_club_state` varchar(100) NOT NULL DEFAULT '-1' COMMENT '正在进行0未开始-1结束1取消2',
  `sport_club_address` varchar(100) NOT NULL,
  PRIMARY KEY (`sport_club_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sport_club
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` varchar(100) NOT NULL,
  `user_password` varchar(100) NOT NULL,
  `user_name` varchar(100) NOT NULL,
  `user_sex` varchar(100) NOT NULL DEFAULT '',
  `user_birthday` varchar(100) NOT NULL DEFAULT '',
  `user_address` varchar(100) NOT NULL DEFAULT '',
  `user_type` varchar(100) NOT NULL DEFAULT '',
  `user_img_path` varchar(100) NOT NULL DEFAULT '',
  `user_integral` varchar(100) NOT NULL DEFAULT '0',
  `user_state` varchar(100) NOT NULL DEFAULT '0' COMMENT '0离线1在线',
  `user_label` varchar(100) NOT NULL DEFAULT '',
  `user_tel` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('123', 'cae5208c40435ef55c231bd5aebe2304', '123', '', '', '', '', '', '0', '0', '', '');
