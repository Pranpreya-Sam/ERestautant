package com.example.e_restautant;

public class Order {

    private int OrderID;
    private String OrderName;
    private int OrderPrice;
    private String OrderType;
    private String OrderQuality;

    public Order() {

    }

    public Order(int orderID, String orderName, int orderPrice, String orderType, String orderQuality) {
        this.OrderID = orderID;
        this.OrderName = orderName;
        this.OrderPrice = orderPrice;
        this.OrderType = orderType;
        this.OrderQuality = orderQuality;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public String getOrderName() {
        return OrderName;
    }

    public void setOrderName(String orderName) {
        OrderName = orderName;
    }

    public int getOrderPrice() {
        return OrderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        OrderPrice = orderPrice;
    }

    public String getOrderType() {
        return OrderType;
    }

    public void setOrderType(String orderType) {
        OrderType = orderType;
    }

    public String getOrderQuality() {
        return OrderQuality;
    }

    public void setOrderQuality(String orderQuality) {
        OrderQuality = orderQuality;
    }
}
