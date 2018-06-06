
--cashmanapp 数据库
--cashman
DELETE FROM contract where product_id = 2;
INSERT INTO contract (`contract_name`, `contract_type`,`product_id`, `contract_url`,`created_time`)
VALUES ("分期还款协议","SHOPPING_AGREEMENT","2","http://large-installment.xianjinxia.com/#/installagreement", NOW());

--scheduler 数据库
USE scheduler;
INSERT INTO `tb_job_desc`
(`job_name`,`job_desc`,`job_cron`,`job_url`,`url_params`,`request_encoding`,`request_method`,`call_type`,`create_time`,`last_update_time`,`version`,`job_status`,`is_handle` )
VALUES
('shopping-contract-upload', '商城订单上传合同', '0 0/5 * * * ?', 'http://CASHMAN-APP/service/job/upload-shopping-contract', '', 'UTF-8', 'POST', 'EUREKA', NOW(), NOW(), 1, 0, 0);
