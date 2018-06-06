--trd_loan_order表添加字段merchant_no(商户号)
alter table trd_loan_order add column merchant_no varchar(64) DEFAULT 'cjxjx'  COMMENT '商户号';


--添加提速卡id
alter table trd_loan_order add column speed_card_id varchar(32) DEFAULT ''  COMMENT '提速卡id';