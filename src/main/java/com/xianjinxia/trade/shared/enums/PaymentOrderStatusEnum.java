package com.xianjinxia.trade.shared.enums;

public enum PaymentOrderStatusEnum {
        DOING("01"),SUCCESS("02"),FAIL("03");

        PaymentOrderStatusEnum(String status){this.status=status;}

        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }