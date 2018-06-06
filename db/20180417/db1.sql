
--预生产 trd 数据库
SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `trd_shoppping_loan_card_info`
-- ----------------------------
DROP TABLE IF EXISTS `trd_shoppping_loan_card_info`;
CREATE TABLE `trd_shoppping_loan_card_info` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '表ID',
  `shopping_product_order_id` bigint(32) NOT NULL COMMENT '商品订单号',
  `third_order_id` varchar(32) NOT NULL COMMENT '第三方订单号',
  `product_name` varchar(100) NOT NULL COMMENT '产品名称',
  `product_unit_price` int(8) NOT NULL DEFAULT '0' COMMENT '商品单价',
  `card_no` varchar(32) NOT NULL COMMENT '树鱼卡号',
  `card_password` varchar(32) NOT NULL COMMENT '树鱼卡密码',
  `card_deadline` varchar(32) NOT NULL,
  `recycle` varchar(32) NOT NULL DEFAULT '0' COMMENT '是否回收0:未回收1：已回收',
  `recycle_time` timestamp NULL DEFAULT NULL COMMENT '回收时间',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `created_user` varchar(32) DEFAULT 'sys' COMMENT '创建人',
  `updated_at` datetime NOT NULL COMMENT '更新时间',
  `updated_user` varchar(32) NOT NULL DEFAULT 'sys' COMMENT '更新人',
  `data_valid` tinyint(1) NOT NULL DEFAULT '1' COMMENT '数据有效性 0 无效  1 有效',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_card_no` (`card_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;




ALTER TABLE `trd_shopping_product_order`
ADD COLUMN `product_number`  int(11) NOT NULL DEFAULT 1 COMMENT '产品数量' AFTER `data_valid`,
ADD COLUMN `third_product_id`  varchar(32) NULL COMMENT '第三方产品id' AFTER `product_number`,
ADD COLUMN `product_unit_price`  int(8) NULL COMMENT '商品单价' AFTER `third_product_id`,
ADD COLUMN `third_purchase_price` varchar(32) DEFAULT NULL COMMENT '第三方单价' AFTER `product_unit_price`,
MODIFY COLUMN `product_price`  int(8) NOT NULL COMMENT '产品价格(与loanOrder的orderAmount 金额一致)' AFTER `product_id`;
;



ALTER TABLE `trd_shopping_loan_order`
ADD COLUMN `product_kind`  int(4) NOT NULL DEFAULT 1 COMMENT '商品种类 1：默认 2：虚拟卡' AFTER `product_category`,
ADD COLUMN `third_order_time`  datetime NULL COMMENT '虚拟卡第三方下单时间' AFTER `data_valid`;

--mqtask 数据库
USE mqtask;
insert into `tb_mq_consumer`
( `consumer_name`, `consumer_desc`, `queue_name`, `consumer_url`, `url_params`, `request_encoding`, `request_method`, `call_type`, `retry_times`, `create_time`, `last_update_time`, `version`, `status`, `is_handle`)
values
('tradeapp_sync_shopping_order_status','同步订单状态','trd_sync_virtual_order_status','http://CASHMAN-APP/service/shopping/sync-virtual-order-status',NULL,'UTF-8','POST','EUREKA','7',now(),now(),'2','7','1');

--scheduler 数据库
USE scheduler;
INSERT INTO `tb_job_desc`
(`job_name`,`job_desc`,`job_cron`,`job_url`,`url_params`,`request_encoding`,`request_method`,`call_type`,`create_time`,`last_update_time`,`version`,`job_status`,`is_handle` )
VALUES
('virtual-card-third-order-apply', '商城虚拟订单第三方下单', '0 0/5 * * * ?', 'http://TRADE-APP/service/shopping/job/batch-update-shopping-virtual-loan-status', '', 'UTF-8', 'POST', 'EUREKA', NOW(), NOW(), 1, 0, 0);

INSERT INTO `tb_job_desc`
(`job_name`,`job_desc`,`job_cron`,`job_url`,`url_params`,`request_encoding`,`request_method`,`call_type`,`create_time`,`last_update_time`,`version`,`job_status`,`is_handle` )
VALUES
('virtual-card-get', '商城虚拟订单卡密查询', '0 0/5 * * * ?', 'http://TRADE-APP/service/shopping/job/batch-get-third-order-cards', '', 'UTF-8', 'POST', 'EUREKA', NOW(), NOW(), 1, 0, 0);



