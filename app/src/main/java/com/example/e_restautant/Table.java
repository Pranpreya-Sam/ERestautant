package com.example.e_restautant;

public class Table {
    private String Amount;
    private String Date;
    private String Time;
    private String Request;

    public  Table(){

    }

    public Table(String amount, String date, String time, String request) {
        this.Amount = amount;
        this.Date = date;
        this.Time = time;
        Request = request;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        this.Amount = amount;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        this.Time = time;
    }

    public String getRequest() {
        return Request;
    }

    public void setRequest(String request) {
        this.Request = request;
    }
}
