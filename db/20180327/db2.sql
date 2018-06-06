INSERT INTO  scheduler.`tb_job_desc` (`job_name`, `job_desc`, `job_cron`, `job_url`, `request_encoding`, `request_method`, `call_type`, `create_time`,`version`, `job_status`, `is_handle`) VALUES (
'HandlerLoanOrderStateChangeJob', '批量更新商城订单状态,配送中更新为已确认收货', '0 0/5 * * * ?', 'http://TRADE-APP/service/review/batch-update-shopping-loan-status', 'UTF-8', 'POST', 'EUREKA', now(),'2', '0', '0');

insert into `tb_mq_consumer` ( `consumer_name`, `consumer_desc`, `queue_name`, `consumer_url`, `url_params`, `request_encoding`, `request_method`, `call_type`, `retry_times`, `create_time`, `last_update_time`, `version`, `status`, `is_handle`)
values('cashmanapp_sync_shopping_order_status','同步订单状态','cashmanapp_sync_orderstatus_to_trade_queue','http://TRADE-APP//service/shopping/event/sync-order-status',NULL,'UTF-8','POST','EUREKA','7',now(),now(),'2','7','1');
