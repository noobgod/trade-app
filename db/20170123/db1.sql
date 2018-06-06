-- -- auto-generated definition
-- CREATE TABLE products_fee_config (
--   id           BIGINT(32) AUTO_INCREMENT
--     PRIMARY KEY,
--   data_valid   INT(1)         NOT NULL,
--   created_user VARCHAR(64)    NOT NULL,
--   created_time DATETIME       NOT NULL,
--   updated_user VARCHAR(64)    NOT NULL,
--   updated_time DATETIME       NOT NULL,
--   fee_name     VARCHAR(32)    NOT NULL
--   COMMENT '费用名称',
--   fee_type     VARCHAR(32)    NOT NULL
--   COMMENT '费用类型',
--   fee_rate     DECIMAL(10, 4) NOT NULL
--   COMMENT '费率',
--   description  VARCHAR(64)    NULL
--   COMMENT '费用描述',
--   product_id   BIGINT(32)     NOT NULL
--   COMMENT '产品id',
--   periods      INT            NULL
--   COMMENT '期数'
-- )
--   COMMENT '产品费率配置表';
-- CREATE INDEX idx_created_updated_time
--   ON products_fee_config (created_time, updated_time);
-- CREATE INDEX idx_products_fee_config_ut
--   ON products_fee_config (updated_time);
-- CREATE INDEX idx_products_id_fee_type
--   ON products_fee_config (product_id, fee_type);


-- auto-generated definition
CREATE TABLE trd_shopping_logistics_order (
  id                     BIGINT(32) AUTO_INCREMENT
  COMMENT '表id'
    PRIMARY KEY,
  shopping_loan_order_id VARCHAR(32)               NOT NULL
  COMMENT '商品商品系统订单号',
  receive_province       VARCHAR(32)               NOT NULL
  COMMENT '收货人省份',
  receive_city           VARCHAR(32)               NOT NULL
  COMMENT '收货人城市',
  receive_area           VARCHAR(32)               NOT NULL
  COMMENT '收货人区县',
  receive_addr           VARCHAR(32)               NOT NULL
  COMMENT '收货人详细地址',
  receive_post_no        VARCHAR(32)               NOT NULL
  COMMENT '收货人邮政编码',
  receive_username       VARCHAR(32)               NOT NULL
  COMMENT '收货人姓名',
  receive_mobile         VARCHAR(11)               NOT NULL
  COMMENT '收货人手机号',
  logistics_company      VARCHAR(32)               NOT NULL
  COMMENT '物流公司名称',
  logistics_no           VARCHAR(32)               NOT NULL
  COMMENT '物流编号',
  logistics_phone        VARCHAR(11)               NOT NULL
  COMMENT '物流公司电话',
  status                 VARCHAR(32) DEFAULT '0'   NOT NULL
  COMMENT '状态, 包含了内部物流订单的状态、外部物流订单的状态，是一个串行关系',
  created_at             DATETIME                  NOT NULL
  COMMENT '创建时间',
  created_user           VARCHAR(32) DEFAULT 'sys' NULL
  COMMENT '创建人',
  updated_at             DATETIME                  NOT NULL
  COMMENT '更新时间',
  updated_user           VARCHAR(32) DEFAULT 'sys' NULL
  COMMENT '更新人',
  data_valid             TINYINT(1) DEFAULT '1'    NOT NULL
  COMMENT '数据有效性 0 无效  1 有效'
)
  COMMENT '物流订单表';


-- auto-generated definition
CREATE TABLE trd_shopping_product_order (
  id                     BIGINT(32) AUTO_INCREMENT
  COMMENT '表id'
    PRIMARY KEY,
  shopping_loan_order_id VARCHAR(32)               NOT NULL
  COMMENT '商品商品系统订单号',
  product_id             VARCHAR(32)               NOT NULL
  COMMENT '产品编号',
  product_price          INT(8)                    NOT NULL
  COMMENT '产品价格',
  product_name           VARCHAR(32)               NOT NULL
  COMMENT '产品名称',
  product_category       VARCHAR(32)               NOT NULL
  COMMENT '产品类型  (1:金融产品)',
  status                 VARCHAR(32) DEFAULT '0'   NOT NULL
  COMMENT '状态, 01：待审核，02：审核通过，03：审核拒绝，04：待还款，05：已结清',
  created_at             DATETIME                  NOT NULL
  COMMENT '创建时间',
  created_user           VARCHAR(32) DEFAULT 'sys' NULL
  COMMENT '创建人',
  updated_at             DATETIME                  NOT NULL
  COMMENT '更新时间',
  updated_user           VARCHAR(32) DEFAULT 'sys' NULL
  COMMENT '更新人',
  data_valid             TINYINT(1) DEFAULT '1'    NOT NULL
  COMMENT '数据有效性 0 无效  1 有效'
)
  COMMENT '商品订单表';
