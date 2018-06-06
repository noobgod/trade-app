
alter table trd_loan_order add column interest_amount int DEFAULT 0 COMMENT '借款利息';

alter table trd_loan_order add column loan_usage varchar(128) DEFAULT '' COMMENT '借款用途';

alter table trd_loan_order add column loan_time datetime DEFAULT NULL COMMENT '实际放款时间';

alter table trd_loan_order add column review_fail_time datetime DEFAULT NULL COMMENT '审核失败时间';