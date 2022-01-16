CREATE database IF NOT exists `backend_res` CHARACTER SET utf8 COLLATE utf8_general_ci;
use `backend_res`;

-- ----------------------------
-- Table structure for house_info
-- ----------------------------
DROP TABLE IF EXISTS `house_info`;
CREATE TABLE `house_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '唯一标志',
  `crawl_date` date DEFAULT NULL COMMENT '爬取日期',
  `province` varchar(64) DEFAULT NULL COMMENT '省份',
  `city` varchar(64) DEFAULT NULL COMMENT '城市',
  `village_name` varchar(500) DEFAULT NULL COMMENT '小区名称',
  `price` decimal(12,2) DEFAULT NULL COMMENT '价格',
  `price_unit` varchar(16) DEFAULT NULL COMMENT '价格单位',
  `rooms` varchar(200) DEFAULT NULL COMMENT '几居',
  `area` varchar(500) DEFAULT NULL COMMENT '面积',
  `address` varchar(500) DEFAULT NULL COMMENT '地址',
  `district` varchar(500) DEFAULT NULL COMMENT '行政区',
  `sale` varchar(4) DEFAULT NULL COMMENT '是否在售：0->不在售;1->不在售',
  `detail_url` varchar(512) DEFAULT NULL COMMENT '详情页面url',
  `house_type` int(1) DEFAULT NULL COMMENT '房屋类型：1->新房；2->二手房；',
  `usage` int(1) DEFAULT 0 COMMENT '用途：0->购房；1->租房',
  `origin_type` int(1) DEFAULT 0 COMMENT '数据来源：0->WEB',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_time` datetime DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP COMMENT '修改时间',
  unique index(`crawl_date`, `province` ,`city`,`village_name`),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='房屋信息表';