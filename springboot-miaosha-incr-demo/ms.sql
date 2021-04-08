/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : 127.0.0.1:3306
 Source Schema         : ms

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : 65001

 Date: 08/04/2021 14:34:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for codeant_product
-- ----------------------------
DROP TABLE IF EXISTS `codeant_product`;
CREATE TABLE `codeant_product`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `num` int(11) NULL DEFAULT NULL,
  `price` decimal(10, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of codeant_product
-- ----------------------------
INSERT INTO `codeant_product` VALUES (1, '显卡', 1000, 2000.00);

-- ----------------------------
-- Table structure for codeant_product_log
-- ----------------------------
DROP TABLE IF EXISTS `codeant_product_log`;
CREATE TABLE `codeant_product_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `product_id` int(11) NULL DEFAULT NULL,
  `num` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
