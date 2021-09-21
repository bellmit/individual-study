-- ----------------------------
-- Table structure for `id_strategy`
-- ----------------------------
DROP TABLE IF EXISTS `id_strategy`;
CREATE TABLE `id_strategy` (
     `id` bigint auto_increment primary key COMMENT '唯一标识',
     `biz_tag` varchar(255) DEFAULT NULL COMMENT '业务标识',
     `max_id` bigint DEFAULT -1 COMMENT '当前最大ID值',
     `step` int DEFAULT -1 COMMENT '当前步长',
     `desc` varchar(255) DEFAULT NULL COMMENT 'ID 业务描述',
     `modified_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;