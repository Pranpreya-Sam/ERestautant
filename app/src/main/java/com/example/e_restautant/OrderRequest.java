package com.example.e_restautant;

import java.util.List;

public class OrderRequest {

    private int trackingNum;
    private String total;
    private List<Order> orderList;

    public OrderRequest() {

    }

    public OrderRequest(int trackingNum, String total, List<Order> orderList) {
        this.trackingNum = trackingNum;
        this.total = total;
        this.orderList = orderList;
    }



    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }


    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public int getTrackingNum() {
        return trackingNum;
    }

    public void setTrackingNum(int trackingNum) {
        this.trackingNum = trackingNum;
    }
}
