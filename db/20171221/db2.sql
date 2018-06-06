--获取人工审核通过的订单,发送给支付中心放款,定时任务

INSERT INTO  scheduler.`tb_job_desc` (`job_name`, `job_desc`, `job_cron`, `job_url`, `request_encoding`, `request_method`, `call_type`, `create_time`,`version`, `job_status`, `is_handle`) VALUES (
'HandlerFetchManualReviewSuccessAndPaymentJob', '获取人工审核通过的订单,发送给支付中心放款', '0 0/5 * * * ?', 'http://TRADE-APP/service/review/fetch-mannual-review-success', 'UTF-8', 'POST', 'EUREKA', now(),'2', '0', '0');



---mqapp 脚本
INSERT INTO `tb_mq_consumer` (`consumer_name`, `consumer_desc`, `queue_name`, `consumer_url`, `url_params`, `request_encoding`, `request_method`, `call_type`, `retry_times`, `create_time`, `last_update_time`, `version`, `status`, `is_handle`)
VALUES ('trade_app_loan_result_queue', '放款结果消息', 'loan_result_queue', 'http://TRADE-APP/service/event/pay-callback', NULL, 'UTF-8', 'POST', 'EUREKA', '7', now(), now(), '2', '0', '0');


insert into `tb_mq_consumer` (`consumer_name`, `consumer_desc`, `queue_name`, `consumer_url`, `url_params`, `request_encoding`, `request_method`, `call_type`, `retry_times`, `create_time`, `last_update_time`, `version`, `status`, `is_handle`)
values('cashman_app_trade_success_queue','放款成功通知cashman-app','trd_success_queue','http://CASHMAN-APP/service/event/trd-loan-success',NULL,'UTF-8','POST','EUREKA','7',now(),now(),'2','0','0');

insert into `tb_mq_consumer` ( `consumer_name`, `consumer_desc`, `queue_name`, `consumer_url`, `url_params`, `request_encoding`, `request_method`, `call_type`, `retry_times`, `create_time`, `last_update_time`, `version`, `status`, `is_handle`)
values('cashman_return_quota','归还额度消息','trd_return_quota','http://super.xianjinxia.com/refactoruser/recoverlimit',NULL,'UTF-8','POST','HTTP','7',now(),now(),'2','0','0');

insert into `tb_mq_consumer` ( `consumer_name`, `consumer_desc`, `queue_name`, `consumer_url`, `url_params`, `request_encoding`, `request_method`, `call_type`, `retry_times`, `create_time`, `last_update_time`, `version`, `status`, `is_handle`)
values('paymentcenter_trade_queue','放款处理队列','trd_payment_queue','http://PAYMENTCENTER/api/payment/loan',NULL,'UTF-8','POST','EUREKA','7',now(),now(),'1','0','0');


insert into `tb_mq_consumer` ( `consumer_name`, `consumer_desc`, `queue_name`, `consumer_url`, `url_params`, `request_encoding`, `request_method`, `call_type`, `retry_times`, `create_time`, `last_update_time`, `version`, `status`, `is_handle`)
values('cashman_confirm_loan_queue','插入risk风控消息','trd_confirm_loan_small_queue','http://super.xianjinxia.com/refactorloan/loanrisk',NULL,'UTF-8','POST','HTTP','7',now(),now(),'1','0','0');


insert into `tb_mq_consumer` ( `consumer_name`, `consumer_desc`, `queue_name`, `consumer_url`, `url_params`, `request_encoding`, `request_method`, `call_type`, `retry_times`, `create_time`, `last_update_time`, `version`, `status`, `is_handle`)
values('confirm_order_big_risk','大额确认借款，风控接收','trd_confirm_order_big_risk_queue','http://RISK-APP/riskapp/creditfast/credit_loan',NULL,'UTF-8','POST','EUREKA','7',now(),now(),'1','0','0');

insert into `tb_mq_consumer` (`consumer_name`, `consumer_desc`, `queue_name`, `consumer_url`, `url_params`, `request_encoding`, `request_method`, `call_type`, `retry_times`, `create_time`, `last_update_time`, `version`, `status`, `is_handle`)
values('trade_app_risk_return_big_borrow_order_queue','接收风控审核结果','risk_return_big_borrow_order_queue','http://TRADE-APP/service/event/recive-risk-data',NULL,'UTF-8','POST','EUREKA','7',now(),now(),'2','0','0');

