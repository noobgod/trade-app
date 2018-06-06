INSERT INTO `tb_mq_consumer` 
(`consumer_name`, `consumer_desc`, `queue_name`, `consumer_url`, `url_params`, `request_encoding`, `request_method`, `call_type`, `retry_times`, `create_time`,`version`, `status`, `is_handle`)
VALUES 
('trade_settle_request_consumer', '交易-交易交割回调', 'settle_request_queue', 'http://TRADE-APP/service/pet/event/settle', NULL, 'UTF-8', 'POST', 'EUREKA', '7', '2018-03-05 14:05:11', '2', '0', '0');