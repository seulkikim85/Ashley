package com.isp1004.ashley.myinfo;

/**
 * Created by Administrator on 2017-04-10.
 */

public class OrderVO {
    private String orderDate;

    private String brandName;
    private String productName;
    private int qty;
    private int price;

    public String getBrandName() {
        return brandName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
