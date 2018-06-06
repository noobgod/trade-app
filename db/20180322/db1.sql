--trd_loan_order表添加字段merchant_no(商户号)
alter table trd_loan_order add column merchant_no varchar(64) DEFAULT ''  COMMENT '商户号';

--添加是否购买提速卡
alter table trd_loan_order add column buy_card varchar(10) DEFAULT ''  COMMENT '是否购买提速卡';

--添加是否购买提速卡
alter table trd_loan_order add column speed_card_id varchar(32) DEFAULT ''  COMMENT '提速卡id';