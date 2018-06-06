package com.xianjinxia.trade.platform.request;

import com.xianjinxia.trade.app.request.BaseRequest;
import java.math.BigDecimal;

public class OpenApiConfirmLoanRequest extends BaseRequest{

    private String order_no;

    private BigDecimal loan_amount;

    private Integer loan_term;

    private Long bank_card_id;

    private Integer is_reloan;

    public OpenApiConfirmLoanRequest(String order_no, BigDecimal loan_amount, Integer loan_term, Long bank_card_id, Integer is_reloan) {
        this.order_no = order_no;
        this.loan_amount = loan_amount;
        this.loan_term = loan_term;
        this.bank_card_id = bank_card_id;
        this.is_reloan = is_reloan;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public BigDecimal getLoan_amount() {
        return loan_amount;
    }

    public void setLoan_amount(BigDecimal loan_amount) {
        this.loan_amount = loan_amount;
    }

    public Integer getLoan_term() {
        return loan_term;
    }

    public void setLoan_term(Integer loan_term) {
        this.loan_term = loan_term;
    }

    public Long getBank_card_id() {
        return bank_card_id;
    }

    public void setBank_card_id(Long bank_card_id) {
        this.bank_card_id = bank_card_id;
    }

    public Integer getIs_reloan() {
        return is_reloan;
    }

    public void setIs_reloan(Integer is_reloan) {
        this.is_reloan = is_reloan;
    }

}
