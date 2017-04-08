package com.isp1004.ashley.common;

/**
 * Created by h06 on 2017-04-07.
 */

public class BasketVO {

    private int basketId;
    private String email;
    private String brandName;
    private String productId;
    private String productName;
    private int qty;
    private int price;
    private String isOrdered;
    private String isPaid;

    public BasketVO(int basketId, String email, String brandName, String productId, String productName, int qty, int price, String isOrdered, String isPaid) {
        this.basketId = basketId;
        this.email = email;
        this.brandName = brandName;
        this.productId = productId;
        this.productName = productName;
        this.qty = qty;
        this.price = price;
        this.isOrdered = isOrdered;
        this.isPaid = isPaid;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getBasketId() {
        return basketId;
    }

    public void setBasketId(int basketId) {
        this.basketId = basketId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getIsOrdered() {
        return isOrdered;
    }

    public void setIsOrdered(String isOrdered) {
        this.isOrdered = isOrdered;
    }

    public String getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
    }
}
