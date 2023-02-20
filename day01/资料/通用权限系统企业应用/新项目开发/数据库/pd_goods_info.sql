SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `pd_goods_info`
-- ----------------------------
DROP TABLE IF EXISTS `pd_goods_info`;
CREATE TABLE `pd_goods_info` (
  `id` bigint(20) NOT NULL COMMENT '商品ID',
  `code` char(16) COLLATE utf8mb4_bin NOT NULL COMMENT '商品编码',
  `name` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '商品名称',
  `bar_code` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '国条码',
  `brand_id` bigint(20) DEFAULT NULL COMMENT '品牌表ID',
  `one_category_id` bigint(20) DEFAULT NULL COMMENT '一级分类ID',
  `two_category_id` bigint(20) DEFAULT NULL COMMENT '二级分类ID',
  `three_category_id` bigint(20) DEFAULT NULL COMMENT '三级分类ID',
  `supplier_id` bigint(20) DEFAULT NULL COMMENT '商品的供应商ID',
  `price` decimal(8,2) NOT NULL COMMENT '商品售价价格',
  `average_cost` decimal(18,2) NOT NULL COMMENT '商品加权平均成本',
  `publish_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '上下架状态:0下架，1上架',
  `audit_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '审核状态: 0未审核，1已审核',
  `weight` float DEFAULT NULL COMMENT '商品重量',
  `length` float DEFAULT NULL COMMENT '商品长度',
  `height` float DEFAULT NULL COMMENT '商品重量',
  `width` float DEFAULT NULL COMMENT '商品宽度',
  `color` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '颜色',
  `production_date` datetime NOT NULL COMMENT '生产日期',
  `shelf_life` int(11) NOT NULL COMMENT '商品有效期',
  `descript` text COLLATE utf8mb4_bin COMMENT '商品描述',
  `update_time` datetime DEFAULT NULL,
  `update_user` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='商品信息表';

-- ----------------------------
-- Records of pd_goods_info
-- ----------------------------
INSERT INTO `pd_goods_info` VALUES ('698106238453416065', '456789', '士力架', null, null, null, null, null, null, '10.00', '5.00', '1', '1', '0.1', '0.2', '0.1', '0.4', '红色', '2020-04-10 09:42:41', '5', null, '2020-04-10 09:44:58', '3', '2020-04-10 09:44:58', '3');
INSERT INTO `pd_goods_info` VALUES ('698112493741932705', '1231231', 'BabiQ', null, null, null, null, null, null, '28.00', '20.00', '0', '1', '1', '0.5', '0.1', '0.3', '红色', '2020-04-10 10:09:41', '180', null, '2020-04-10 10:09:49', '3', '2020-04-10 10:09:49', '3');