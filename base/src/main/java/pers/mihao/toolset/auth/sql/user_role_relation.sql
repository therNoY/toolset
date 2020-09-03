/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.43.121
 Source Server Type    : MySQL
 Source Server Version : 50644
 Source Host           : 192.168.43.121:3306
 Source Schema         : ancient_empire

 Target Server Type    : MySQL
 Target Server Version : 50644
 File Encoding         : 65001

 Date: 14/11/2019 19:39:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `user_role_relation`;
CREATE TABLE `user_role_relation`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户Id',
  `role_id` int(11) NULL DEFAULT 2 COMMENT '角色Id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户和角色表' ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
