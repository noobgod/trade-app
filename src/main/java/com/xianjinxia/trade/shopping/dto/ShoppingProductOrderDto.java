package com.xianjinxia.trade.shopping.dto;


public class ShoppingProductOrderDto {
	
	private Long id;

	private Long shoppingLoanOrderId;

	private String productId;

	private Integer productPrice;

	private String productName;

	private String productCategory;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		this.productId = productId;
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
		this.productName = productName;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
}
