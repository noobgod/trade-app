CREATE TABLE
    trd_sale_order_request
    (
        id bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
        sale_no VARCHAR(64) COMMENT '出售单序列号',
        user_id bigint COMMENT '用户id',
        user_name VARCHAR(50) COMMENT '用户名称',
        pet_id bigint COMMENT '宠物id',
        pet_code VARCHAR(64) COMMENT '宠物编号',
        pet_frozen_no VARCHAR(64) COMMENT '宠物冻结序列号',
        pet_name VARCHAR(64) COMMENT '宠物名称',
        pet_img VARCHAR(64) COMMENT '宠物图片',
        pet_generate_level INT COMMENT '宠物第几代',
        pet_property_detail VARCHAR(1024) COMMENT '宠物详情',
        pet_varity VARCHAR(45) COMMENT '稀有度',
        price DECIMAL(24,0) COMMENT '出售价格',
        status VARCHAR(20) COMMENT '状态',
        apply_time DATETIME COMMENT '出售时间',
        remark VARCHAR(200) COMMENT '备注',
        PRIMARY KEY (id),
        INDEX sale_no_idx (sale_no),
        INDEX user_id_idx (user_id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='出售订单表';

CREATE TABLE
    trd_settle_record
    (
        id bigint NOT NULL AUTO_INCREMENT COMMENT '交割单主键',
        source_id VARCHAR(64) COMMENT '来源id',
        source_type VARCHAR(64) COMMENT '来源类型',
        settle_no VARCHAR(64) COMMENT '交割单编号',
        settle_oder_type VARCHAR(20) COMMENT '交割单类型',
        from_user_id bigint COMMENT '出售者id',
        from_user_name VARCHAR(64) COMMENT '出售者名称',
        to_user_id bigint COMMENT '购买者id',
        to_user_name VARCHAR(64) COMMENT '购买者名称',
        price DECIMAL(24,0) COMMENT '交易价格',
        expecte_service_charge DECIMAL(24,0) COMMENT '预期手续费',
        actual_service_charge DECIMAL(24,0) COMMENT '实际手续费',
        payment_time DATETIME COMMENT '结单时间',
        status VARCHAR(20) COMMENT '状态',
        pet_id bigint COMMENT '宠物id',
        pet_code VARCHAR(64) COMMENT '宠物编号',
        pet_name VARCHAR(64) COMMENT '宠物名称',
        pet_img VARCHAR(64) COMMENT '宠物图片',
        pet_generate_level INT COMMENT '宠物第几代',
        pet_property_detail VARCHAR(1024) COMMENT '宠物详情',
        pet_varity VARCHAR(64) COMMENT '稀有度',
        from_frozen_no VARCHAR(64) COMMENT '出售冻结序列号',
        to_frozen_no VARCHAR(64) COMMENT '购买冻结序列号',
        remark VARCHAR(200) COMMENT '备注',
        PRIMARY KEY (id),
        CONSTRAINT settle_no_idx UNIQUE (settle_no),
        INDEX from_user_id_idx (from_user_id),
        INDEX to_user_id_idx (to_user_id),
        INDEX source_idx (source_id, source_type)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交割记录表';


CREATE TABLE
    transfer_request
    (
        id bigint NOT NULL AUTO_INCREMENT COMMENT '支付请求主键',
        req_no VARCHAR(64) COMMENT '请求序列号',
        source_id VARCHAR(64) COMMENT '来源id',
        source_type VARCHAR(64) COMMENT '来源类型',
        status VARCHAR(20) COMMENT '状态',
        appyle_time DATETIME COMMENT '请求时间',
        confirm_time DATETIME COMMENT '确认时间',
        PRIMARY KEY (id),
        CONSTRAINT tfs_req_no_idx UNIQUE (req_no)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付请求表';


CREATE TABLE
    transfer_reqeust_details
    (
        id bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
        transfer_request_id bigint COMMENT '请求表主键',
        from_user_id bigint COMMENT '出售者id',
        coin_num DECIMAL(26,2) COMMENT '交易积分',
        medium_id bigint COMMENT '媒介物id',
        to_user_id bigint COMMENT '购买者id',
        transaction_type VARCHAR(20) COMMENT '交易类型 平台赠送 用户交易',
        status VARCHAR(20) COMMENT '状态',
        PRIMARY KEY (id),
        INDEX tfs_dal_req_id_idx (transfer_request_id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付请求明细表';