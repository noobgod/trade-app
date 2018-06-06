package com.xianjinxia.trade.platform.status;

import org.apache.commons.lang3.StringUtils;
/**
 * Created by fanmaowen on 2017/11/29 0029.
 */
public enum BankCodeEnum {
    BANK_ICBC("ICBC","工商银行"),
    BANK_ABC("ABC","农业银行"),
    BANK_CMB("CMB","招商银行"),
    BANK_CCB("CCB","建设银行"),
    BANK_BCCB("BCCB","北京银行"),
    BANK_BJRCB("BJRCB","北京农业商业银行"),
    BANK_BOC("BOC","中国银行"),
    BANK_COMM("COMM","交通银行"),
    BANK_CMBC("CMBC","民生银行"),
    BANK_BOS("BOS","上海银行"),
    BANK_CBHB("CBHB","渤海银行"),
    BANK_CEB("CEB","光大银行"),
    BANK_CIB("CIB","兴业银行"),
    BANK_CITIC("CITIC","中信银行"),
    BANK_CZB("CZB","浙商银行"),
    BANK_GDB("GDB","广发银行"),
    BANK_HKBEA("HKBEA","东亚银行"),
    BANK_HXB("HXB","华夏银行"),
    BANK_HZCB("HZCB","杭州银行"),
    BANK_NJCB("NJCB","南京银行"),
    BANK_PINGAN("PINGAN","平安银行"),
    BANK_PSBC("PSBC","邮政储蓄银行"),
    BANK_SDB("SDB","深圳发展银行"),
    BANK_SPDB("SPDB","浦发银行"),
    BANK_SRCB("SRCB","上海农业商业银行");

    private String code ;
    private String msg ;

    BankCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static String getEnumByCode(String code) {
        BankCodeEnum[] values = BankCodeEnum.values();
        for(BankCodeEnum bankCodeEnum :values) {
            if(StringUtils.equals(code, bankCodeEnum.getCode())) {
                return bankCodeEnum.getMsg();
            }
        }
        return null;
    }

}
