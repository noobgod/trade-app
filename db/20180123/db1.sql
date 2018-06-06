-- auto-generated definition
CREATE TABLE trd_shopping_loan_order (
  id                     BIGINT(32) AUTO_INCREMENT
  COMMENT '表id'
    PRIMARY KEY,
  product_freeze_no      VARCHAR(32)                     NULL
  COMMENT '商品系统冻结编号',
  order_amount           INT                             NOT NULL
  COMMENT '订单金额，单位为分',
  fee_amount             INT                             NOT NULL
  COMMENT '订单服务费',
  interest_amount        INT                             NULL
  COMMENT '订单利息',
  payment_amount         INT DEFAULT '0'                 NULL
  COMMENT '实际到账金额，单位为分',
  repayment_amount       INT DEFAULT '0'                 NOT NULL
  COMMENT '应还金额',
  term                   INT(8)                          NOT NULL
  COMMENT '期数',
  term_unit              VARCHAR(20)                     NULL
  COMMENT '期数单位',
  term_rate              DECIMAL(10, 4) DEFAULT '0.0000' NOT NULL
  COMMENT '期利率',
  product_id             BIGINT(32)                      NOT NULL
  COMMENT '产品id',
  product_category       INT(4)                          NOT NULL
  COMMENT '产品类型  (1:小额   2:大额  3:商城)',
  is_depository          VARCHAR(2) DEFAULT '1'          NULL
  COMMENT '是否存管',
  user_type              TINYINT(1) DEFAULT '0'          NOT NULL
  COMMENT '是否是老用户：0、新用户；1；老用户',
  status                 VARCHAR(32) DEFAULT '0'         NOT NULL,
  trace_no               VARCHAR(32)                     NOT NULL
  COMMENT '订单系统追踪号',
  user_id                BIGINT(32)                      NOT NULL
  COMMENT '用户id',
  user_idcard_type       VARCHAR(20)                     NULL
  COMMENT '证件类型',
  user_idcard_no         VARCHAR(32)                     NULL
  COMMENT '证件号',
  user_bank_card_id      BIGINT(32)                      NULL
  COMMENT '用户银行卡号id',
  bank_name              VARCHAR(32)                     NULL
  COMMENT '银行名称',
  bank_card_no_last_four VARCHAR(32)                     NULL
  COMMENT '银行卡号后4位',
  user_phone             VARCHAR(11)                     NOT NULL
  COMMENT '用户手机号',
  user_name              VARCHAR(255)                    NOT NULL
  COMMENT '真实姓名',
  source                 VARCHAR(2) DEFAULT '1'          NOT NULL
  COMMENT '订单来源：01、商城系统',
  terminal               VARCHAR(2)                      NOT NULL
  COMMENT '终端类型 01 ios ,02 android, 03 h5',
  uk_token               VARCHAR(32)                     NOT NULL
  COMMENT '防并发的token',
  created_at             DATETIME                        NOT NULL
  COMMENT '创建时间(立即申请时间)',
  created_user           VARCHAR(32) DEFAULT 'sys'       NULL
  COMMENT '创建人',
  updated_at             DATETIME                        NOT NULL
  COMMENT '更新时间',
  updated_user           VARCHAR(32) DEFAULT 'sys'       NULL
  COMMENT '更新人',
  specification_json     VARCHAR(50)                     NULL
  COMMENT '商品规格json,如 {11:24, 12:28, 13:32}',
  specification_desc     VARCHAR(50)                     NOT NULL
  COMMENT '商品规格json描述||如土豪金||128GB||港版',
  remark                 VARCHAR(2000)                   NULL
  COMMENT '备注',
  data_valid             TINYINT(1) DEFAULT '1'          NOT NULL
  COMMENT '数据有效性 0 无效  1 有效',
  CONSTRAINT idx_loan_order_tno
  UNIQUE (trace_no)
)
  COMMENT '金融商品订单表';


-- auto-generated definition
CREATE TABLE trd_shopping_logistics_order (
  id                     BIGINT(32) AUTO_INCREMENT
  COMMENT '表id'
    PRIMARY KEY,
  shopping_loan_order_id BIGINT(32)                NOT NULL
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
  logistics_company      VARCHAR(32)               NULL
  COMMENT '物流公司名称',
  logistics_no           VARCHAR(32)               NULL
  COMMENT '物流编号',
  logistics_phone        VARCHAR(11)               NULL
  COMMENT '物流公司电话',
  status                 VARCHAR(32) DEFAULT '0'   NOT NULL,
  created_at             DATETIME                  NOT NULL
  COMMENT '创建时间',
  created_user           VARCHAR(32) DEFAULT 'sys' NULL
  COMMENT '创建人',
  updated_at             DATETIME                  NOT NULL
  COMMENT '更新时间',
  updated_user           VARCHAR(32) DEFAULT 'sys' NULL
  COMMENT '更新人',
  data_valid             TINYINT(1) DEFAULT '1'    NOT NULL
  COMMENT '数据有效性 0 无效  1 有效',
  send_province          VARCHAR(32)               NULL
  COMMENT '发货人省份',
  send_city              VARCHAR(32)               NULL
  COMMENT '发货人城市',
  send_area              VARCHAR(32)               NULL
  COMMENT '发货人区县',
  send_addr              VARCHAR(32)               NULL
  COMMENT '发货人详细地址',
  send_post_no           VARCHAR(32)               NULL
  COMMENT '发货人邮政编码',
  send_username          VARCHAR(32)               NULL
  COMMENT '发货人姓名',
  send_mobile            VARCHAR(11)               NULL
  COMMENT '发货人手机号',
  order_type             VARCHAR(32)               NULL
  COMMENT '物流订单类型',
  logistics_price        INT                       NULL
  COMMENT '邮递费用',
  send_time              TIMESTAMP                 NULL
  COMMENT '发货时间'
)
  COMMENT '物流订单表';
-- auto-generated definition
CREATE TABLE trd_shopping_product_order (
  id                     BIGINT(32) AUTO_INCREMENT
  COMMENT '表id'
    PRIMARY KEY,
  shopping_loan_order_id BIGINT(32)                NOT NULL
  COMMENT '商品商品系统订单号',
  product_id             VARCHAR(32)               NOT NULL
  COMMENT '产品编号',
  product_price          INT(8)                    NOT NULL
  COMMENT '产品价格',
  product_name           VARCHAR(100)              NOT NULL
  COMMENT '产品名称',
  product_category       VARCHAR(32)               NOT NULL
  COMMENT '产品类型  (1:金融产品)',
  status                 VARCHAR(32) DEFAULT '0'   NOT NULL,
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

-- auto-generated definition
CREATE TABLE trd_shopping_user_addr (
  id          BIGINT(32) AUTO_INCREMENT
  COMMENT '表id'
    PRIMARY KEY,
  user_id     BIGINT(32)             NOT NULL
  COMMENT '用户ID',
  province    VARCHAR(32)            NOT NULL
  COMMENT '省份',
  city        VARCHAR(32)            NOT NULL
  COMMENT '城市',
  area        VARCHAR(32)            NOT NULL
  COMMENT '区县',
  detail_addr VARCHAR(128)           NOT NULL
  COMMENT '详细地址',
  post_no     VARCHAR(32)            NULL
  COMMENT '邮政编码',
  username    VARCHAR(32)            NOT NULL
  COMMENT '姓名',
  mobile      VARCHAR(11)            NOT NULL
  COMMENT '手机号',
  is_default  TINYINT(1) DEFAULT '1' NOT NULL
  COMMENT '是否默认地址',
  created_at  DATETIME               NOT NULL
  COMMENT '创建时间',
  updated_at  DATETIME               NOT NULL
  COMMENT '更新时间',
  remark      VARCHAR(2000)          NULL
  COMMENT '备注',
  data_valid  TINYINT(1) DEFAULT '1' NOT NULL
  COMMENT '数据有效性 0 无效  1 有效'
)
  COMMENT '商城用户收货地址表';
