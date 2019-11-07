package com.example.e_restautant;

import android.graphics.Bitmap;

public class Menu {
    private int ID;
    private String Name;
    private int Price;
    private String Type;
    private byte[] Picture;

    public Menu(){

    }

    public Menu(int ID, String Name, int Price, String Type, byte[] Picture){
        this.ID = ID;
        this.Name = Name;
        this.Price = Price;
        this.Type = Type;
        this.Picture = Picture;
    }

    public byte[] getPicture() {
        return Picture;
    }

    public void setPicture(byte[] picture) {
        Picture = picture;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int Price) {
        this.Price = Price;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
