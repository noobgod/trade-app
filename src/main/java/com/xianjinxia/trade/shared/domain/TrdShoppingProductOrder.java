package com.xianjinxia.trade.shared.domain;

import java.util.Date;

public class TrdShoppingProductOrder {
    private Long id;

    private Long shoppingLoanOrderId;

    private String productId;

    private Integer productPrice;

    private String productName;

    private String productCategory;

    private String status;

    private Date createdAt;

    private String createdUser;

    private Date updatedAt;

    private String updatedUser;

    private Boolean dataValid;

    private Integer productNumber;

    private String thirdProductId;

    private Integer productUnitPrice;

    private String thirdPurchasePrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
	public TrdShoppingProductOrder() {

	}

    public TrdShoppingProductOrder(Long id, Long shoppingLoanOrderId,
                                   String productId, Integer productPrice, String productName,
                                   String productCategory, String status, Date createdAt,
                                   String createdUser, Date updatedAt, String updatedUser,
                                   Boolean dataValid, Integer productNumber, String thirdProductId,
                                   Integer productUnitPrice) {
        super();
        this.id = id;
        this.shoppingLoanOrderId = shoppingLoanOrderId;
        this.productId = productId;
        this.productPrice = productPrice;
        this.productName = productName;
        this.productCategory = productCategory;
        this.status = status;
        this.createdAt = createdAt;
        this.createdUser = createdUser;
        this.updatedAt = updatedAt;
        this.updatedUser = updatedUser;
        this.dataValid = dataValid;
        this.productNumber = productNumber;
        this.thirdProductId = thirdProductId;
        this.productUnitPrice = productUnitPrice;
    }

	public Long getShoppingLoanOrderId() {
        return shoppingLoanOrderId;
    }

    public void setShoppingLoanOrderId(Long shoppingLoanOrderId) {
        this.shoppingLoanOrderId = shoppingLoanOrderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory == null ? null : productCategory.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser == null ? null : createdUser.trim();
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser == null ? null : updatedUser.trim();
    }

    public Boolean getDataValid() {
        return dataValid;
    }

    public void setDataValid(Boolean dataValid) {
        this.dataValid = dataValid;
    }

    public Integer getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(Integer productNumber) {
        this.productNumber = productNumber;
    }

    public String getThirdProductId() {
        return thirdProductId;
    }

    public void setThirdProductId(String thirdProductId) {
        this.thirdProductId = thirdProductId;
    }

    public Integer getProductUnitPrice() {
        return productUnitPrice;
    }

    public void setProductUnitPrice(Integer productUnitPrice) {
        this.productUnitPrice = productUnitPrice;
    }

    public String getThirdPurchasePrice() {
        return thirdPurchasePrice;
    }

    public void setThirdPurchasePrice(String thirdPurchasePrice) {
        this.thirdPurchasePrice = thirdPurchasePrice;
    }
}