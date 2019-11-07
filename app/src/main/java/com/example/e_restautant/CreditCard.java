package com.example.e_restautant;

import java.util.Date;
import java.util.List;

public class CreditCard {

    private String cardNo;
    private String cardCCV;
    private String dateExpire;

    public CreditCard() {

    }

    public CreditCard(String cardNo, String cardCCV, String dateExpire) {
        this.cardNo = cardNo;
        this.cardCCV = cardCCV;
        this.dateExpire = dateExpire;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardCCV() {
        return cardCCV;
    }

    public void setCardCCV(String cardCCV) {
        this.cardCCV = cardCCV;
    }

    public String getDateExpire() {
        return dateExpire;
    }

    public void setDateExpire(String dateExpire) {
        this.dateExpire = dateExpire;
    }
}
