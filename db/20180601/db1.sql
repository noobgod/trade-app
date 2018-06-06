-- 活动商品订单表
DROP TABLE IF EXISTS `trd_activity_sku_order`;
CREATE TABLE `trd_activity_sku_order` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `biz_seq_no` varchar(32) NOT NULL COMMENT '业务流水号',
  `user_id` bigint(32) NOT NULL COMMENT '用户id',
  `user_phone` varchar(11) NOT NULL COMMENT '用户手机号',
  `user_name` varchar(255) NOT NULL COMMENT '用户姓名',

  `product_id` INT NOT NULL COMMENT '商品编号',
  `product_name` varchar(255) NOT NULL COMMENT '商品名称',
  `product_specification` varchar(255) NOT NULL COMMENT '商品规格',
  `product_price` int(10) NOT NULL COMMENT '商品价格',
  `product_notice` varchar(255) COMMENT '商品须知',

  `receive_username` varchar(32) NOT NULL COMMENT '收货人姓名',
  `receive_phone` varchar(11) NOT NULL COMMENT '收货人手机号',
  `receive_city` varchar(32) NOT NULL COMMENT '收货人城市',
  `receive_address` varchar(124) NOT NULL COMMENT '收货人详细地址',
  `logistics_company` varchar(32) DEFAULT NULL COMMENT '物流商名称',
  `logistics_no` varchar(32) DEFAULT NULL COMMENT '物流单号',
  `vender_name` varchar(32) DEFAULT NULL COMMENT '供应商名称',
  `send_time` timestamp NULL DEFAULT NULL COMMENT '发货时间',

  `resp_order_id` varchar(32) NOT NULL DEFAULT '' COMMENT '支付请求返回流水号',
  `resp_time` datetime DEFAULT NULL COMMENT '支付请求返回时间',
  `resp_msg` varchar(1000) DEFAULT NULL COMMENT '支付请求返回信息',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '订单状态(1 待支付 2 支付中 3 待发货(支付成功) 4 支付失败 5 已发货)',

  `data_valid` tinyint(1) NOT NULL DEFAULT '1' COMMENT '数据有效性 0 无效  1 有效',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单创建时间' ,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '订单更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_biz_seq_no_uk` (`biz_seq_no`),
  KEY `idx_user_id_status` (`user_id`,`status`)
) ENGINE=InnoDB AUTO_INCREMENT=666665 DEFAULT CHARSET=utf8 COMMENT='活动商品订单表';

-- 新增实例数据
INSERT INTO `trd`.`trd_activity_sku_order`
  (`biz_seq_no`, `user_id`, `user_phone`, `user_name`,
   `product_id`, `product_name`, `product_specification`, `product_price`,
   `receive_username`, `receive_phone`, `receive_city`, `receive_address`,
   `logistics_company`, `logistics_no`, `send_time`, `status`,
   `data_valid`, `create_time`, `update_time`)
VALUES ('123456', '563', '17621539523', '卓海琛',
        '9999', '防雾霾防尘透气pm2.5海绵时尚明星同款口罩', '3枚/包', '1990',
        '卓海琛', '17621539523', '上海', '长宁区明基商业广商D座6F',
        '顺丰', '62235899224', NULL, '0',
        '1', '2018-05-26 16:57:08', '2018-05-28 14:21:51');