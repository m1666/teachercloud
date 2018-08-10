/*
Navicat MySQL Data Transfer

Source Server         : UserM
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : teachercloud

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-07-29 08:45:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for class_course
-- ----------------------------
DROP TABLE IF EXISTS `class_course`;
CREATE TABLE `class_course` (
  `id` int(255) NOT NULL AUTO_INCREMENT COMMENT '自增长ID',
  `course_id` int(255) DEFAULT NULL COMMENT '课程ID',
  `class_id` bigint(20) DEFAULT NULL COMMENT '班级ID',
  `create_time` datetime DEFAULT NULL COMMENT '开课时间',
  `end_time` datetime DEFAULT NULL COMMENT '结课时间',
  `status` int(10) DEFAULT NULL COMMENT '开课状态，0：开课、1：停课',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_10` (`class_id`),
  KEY `FK_Reference_9` (`course_id`),
  CONSTRAINT `FK_Reference_10` FOREIGN KEY (`class_id`) REFERENCES `class_item` (`class_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Reference_9` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of class_course
-- ----------------------------
INSERT INTO `class_course` VALUES ('1', '1001', '5163002641', '2018-03-01 00:00:00', '2018-07-20 00:00:00', '0');
INSERT INTO `class_course` VALUES ('2', '1002', '5163002641', '2018-03-01 00:00:00', '2018-07-20 00:00:00', '0');
INSERT INTO `class_course` VALUES ('3', '1003', '5163002641', '2018-03-01 00:00:00', '2018-07-20 00:00:00', '0');
INSERT INTO `class_course` VALUES ('4', '1004', '5163002641', '2018-03-01 00:00:00', '2018-07-20 00:00:00', '0');
INSERT INTO `class_course` VALUES ('5', '1005', '5163002641', '2018-03-01 00:00:00', '2018-07-20 00:00:00', '0');
INSERT INTO `class_course` VALUES ('6', '1006', '5163002641', '2018-03-20 00:00:00', '2018-07-20 00:00:00', '0');
INSERT INTO `class_course` VALUES ('7', '1007', '5163002641', '2018-03-01 00:00:00', '2018-07-20 00:00:00', '0');

-- ----------------------------
-- Table structure for class_item
-- ----------------------------
DROP TABLE IF EXISTS `class_item`;
CREATE TABLE `class_item` (
  `class_id` bigint(20) NOT NULL COMMENT '班级ID',
  `class_name` varchar(50) DEFAULT NULL COMMENT '班级名称',
  `status` int(10) DEFAULT NULL COMMENT '班级状态，1：毕业班级、0：应届班级',
  PRIMARY KEY (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of class_item
-- ----------------------------
INSERT INTO `class_item` VALUES ('5163002641', '软件工程', '0');

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `course_id` int(255) NOT NULL COMMENT '课程ID',
  `name` varchar(100) DEFAULT NULL COMMENT '课程名称',
  `course_info` text COMMENT '课程信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(10) DEFAULT NULL COMMENT '课程状态，0：开课、1：停课',
  PRIMARY KEY (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('1001', '高等数学', '高等数学（理工）', '2018-07-20 12:00:00', '2018-07-20 12:00:00', '0');
INSERT INTO `course` VALUES ('1002', '大学英语', '高等英语（理工）', '2018-07-20 12:00:00', '2018-07-20 12:00:00', '0');
INSERT INTO `course` VALUES ('1003', '数据结构', '数据结构从懵懂到跑路', '2018-07-20 00:00:00', '2018-07-20 00:00:00', '0');
INSERT INTO `course` VALUES ('1004', 'C语言', 'c从入门到精通', '2018-07-20 00:00:00', '2018-07-20 00:00:00', '0');
INSERT INTO `course` VALUES ('1005', 'java程序设计', 'java从入门到精通', '2018-07-20 00:00:00', '2018-07-20 00:00:00', '0');
INSERT INTO `course` VALUES ('1006', 'c++程序设计', 'c++从入门到精通', '2018-07-20 00:00:00', '2018-07-20 00:00:00', '0');
INSERT INTO `course` VALUES ('1007', '数据库程序设计', '数据库从入门到精通', '2018-07-20 00:00:00', '2018-07-20 00:00:00', '0');

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file` (
  `file_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '文件ID自增长',
  `user_id` int(255) DEFAULT NULL COMMENT '上传用户的ID',
  `course_id` int(255) DEFAULT NULL COMMENT '文件所对应的课程ID',
  `class_id` bigint(20) DEFAULT NULL COMMENT '文件所对应的班级ID',
  `name` varchar(100) DEFAULT NULL COMMENT '文件名称',
  `detail` varchar(500) DEFAULT NULL COMMENT '文件描述200字',
  `main_image` varchar(500) DEFAULT NULL COMMENT '文件主图',
  `status` int(10) DEFAULT NULL COMMENT '文件状态，0：可浏览下载、1：放入回收站',
  `type` int(10) NOT NULL COMMENT '文件类型，1：课件、2：视频、3、讲义',
  `downloads` int(255) DEFAULT NULL COMMENT '下载次数',
  `count` int(255) DEFAULT NULL COMMENT '浏览次数',
  `create_time` datetime DEFAULT NULL COMMENT '上传时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`file_id`),
  KEY `FK_Reference_4` (`class_id`),
  KEY `FK_Reference_5` (`user_id`),
  KEY `FK_Reference_6` (`course_id`),
  CONSTRAINT `FK_Reference_4` FOREIGN KEY (`class_id`) REFERENCES `class_item` (`class_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `FK_Reference_5` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Reference_6` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of file
-- ----------------------------

-- ----------------------------
-- Table structure for file_item
-- ----------------------------
DROP TABLE IF EXISTS `file_item`;
CREATE TABLE `file_item` (
  `id` int(255) NOT NULL AUTO_INCREMENT COMMENT '自增长ID',
  `file_id` bigint(20) DEFAULT NULL COMMENT '文件ID',
  `user_id` int(255) DEFAULT NULL COMMENT '浏览用户的ID',
  `view_time` datetime DEFAULT NULL COMMENT '用户浏览时间',
  PRIMARY KEY (`id`),
  KEY `file_id` (`file_id`),
  CONSTRAINT `file_item_ibfk_1` FOREIGN KEY (`file_id`) REFERENCES `file` (`file_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of file_item
-- ----------------------------

-- ----------------------------
-- Table structure for sign
-- ----------------------------
DROP TABLE IF EXISTS `sign`;
CREATE TABLE `sign` (
  `sign_id` bigint(20) NOT NULL COMMENT '签到ID',
  `course_id` int(255) DEFAULT NULL COMMENT '课程ID',
  `user_id` int(255) DEFAULT NULL COMMENT '创建签到的用户ID',
  `count` int(255) DEFAULT NULL COMMENT '总签到人数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `end_time` datetime DEFAULT NULL COMMENT '完成时间',
  `close_time` datetime DEFAULT NULL COMMENT '关闭时间(可设置默认值）',
  PRIMARY KEY (`sign_id`),
  KEY `FK_Reference_2` (`course_id`),
  KEY `FK_Reference_3` (`user_id`),
  CONSTRAINT `FK_Reference_2` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `FK_Reference_3` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sign
-- ----------------------------

-- ----------------------------
-- Table structure for sign_item
-- ----------------------------
DROP TABLE IF EXISTS `sign_item`;
CREATE TABLE `sign_item` (
  `id` int(255) NOT NULL AUTO_INCREMENT COMMENT '自增长ID',
  `sign_id` bigint(20) NOT NULL COMMENT '签到ID',
  `user_id` int(255) DEFAULT NULL COMMENT '签到用户的ID',
  `user_ip` varchar(500) DEFAULT NULL COMMENT '用户签到设备的IP',
  `sign_time` datetime DEFAULT NULL COMMENT '签到时间',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_1` (`sign_id`),
  CONSTRAINT `FK_Reference` FOREIGN KEY (`sign_id`) REFERENCES `sign` (`sign_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sign_item
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(255) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(32) DEFAULT NULL COMMENT '用户名',
  `password` varchar(32) DEFAULT NULL COMMENT '用户密码',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `status` int(10) DEFAULT NULL COMMENT '用户状态，1：在校、2：离校、3：在职、4：离职',
  `question` varchar(100) DEFAULT '问题' COMMENT '问题（默认为''问题‘）',
  `answer` varchar(100) DEFAULT '000000' COMMENT '答案（默认’000000‘）',
  `role` int(10) DEFAULT NULL COMMENT '用户角色，0：管理员、1：教师、2学生',
  `create_time` datetime DEFAULT NULL COMMENT '用户创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', 'BD1734A0C001B6C5407AE2A4F7C41AA0', 'admin', null, 'admin的问题', 'admin的答案', '0', '2018-07-19 11:39:00', '2018-07-19 11:39:00');
INSERT INTO `user` VALUES ('2', 'teacher_1', 'BCB26DBBB50E661BF99371D2775C47FE', '张三', '3', '问题', '000000', '1', '2018-07-19 14:38:14', '2018-07-19 14:38:14');
INSERT INTO `user` VALUES ('3', 'student_1', 'BCB26DBBB50E661BF99371D2775C47FE', 'student_1', '1', '问题', '000000', '2', '2018-07-19 15:33:10', '2018-07-19 15:33:10');

-- ----------------------------
-- Table structure for user_class
-- ----------------------------
DROP TABLE IF EXISTS `user_class`;
CREATE TABLE `user_class` (
  `id` int(255) NOT NULL AUTO_INCREMENT COMMENT '自增长ID',
  `user_id` int(255) DEFAULT NULL COMMENT '用户ID',
  `class_id` bigint(20) DEFAULT NULL COMMENT '班级ID',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_7` (`user_id`),
  KEY `FK_Reference_8` (`class_id`),
  CONSTRAINT `FK_Reference_7` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Reference_8` FOREIGN KEY (`class_id`) REFERENCES `class_item` (`class_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_class
-- ----------------------------
