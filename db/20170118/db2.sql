--订单确认成功后推mq到cashman-app落订单和落还款计划数据
insert into `tb_mq_consumer` ( `consumer_name`, `consumer_desc`, `queue_name`, `consumer_url`, `url_params`, `request_encoding`, `request_method`, `call_type`, `retry_times`, `create_time`, `last_update_time`, `version`, `status`, `is_handle`)
values('order_confirm_success_queue','订单确认，同步','trd_order_confirm_success_queue','http://CASHMAN-APP/service/event/trd-confirm-loan-success',NULL,'UTF-8','POST','EUREKA','7',now(),now(),'2','7','1');


