alter table trd_loan_order add column is_depository varchar(2) NOT NULL default '1' comment '是否存管，1表示是 2表示否';

alter table trd_loan_order add column term_unit varchar(20) NOT NULL default '' comment '期数单位（01表示天 02表示月）';

alter table trd_loan_order add column term_rate decimal(10,4) NOT NULL default '0.0000' comment '期利率';



ALTER TABLE trd_loan_order DROP INDEX idx_loan_order_tno;

create unique index idx_loan_order_tno on trd_loan_order(trace_no);