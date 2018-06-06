package com.xianjinxia.trade.shared.remote.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductInfoDto {

    private Long id;

    private String name;//名称

    private String code;//产品编号

    private String merchantCode;//商户编码

    private String merchantName;//商户名称

    private String status;//状态

    private BigDecimal minAmt;//最小额度

    private BigDecimal maxAmt;//最大额度

    private List<Integer> terms = new ArrayList<>();//期限(多个 7、14、21)

    private Integer termUnit;//期限单位

    private BigDecimal rate;//利率（已经*100）

    private Integer paybackType;//还款方式

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getMinAmt() {
        return minAmt;
    }

    public void setMinAmt(BigDecimal minAmt) {
        this.minAmt = minAmt;
    }

    public BigDecimal getMaxAmt() {
        return maxAmt;
    }

    public void setMaxAmt(BigDecimal maxAmt) {
        this.maxAmt = maxAmt;
    }

    public List<Integer> getTerms() {
        return terms;
    }

    public void setTerms(List<Integer> terms) {
        this.terms = terms;
    }

    public Integer getTermUnit() {
        return termUnit;
    }

    public void setTermUnit(Integer termUnit) {
        this.termUnit = termUnit;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Integer getPaybackType() {
        return paybackType;
    }

    public void setPaybackType(Integer paybackType) {
        this.paybackType = paybackType;
    }
}
