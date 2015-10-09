package com.tnw.entities;

import java.io.Serializable;

public class DeliveryNode implements Serializable{

    private static final long serialVersionUID = 4286742564550850550L;

    private String itemId, itemName, itemPhone, itemProvince, itemCity,
            itemArea, itemAddress, itemZipcode, itemIsDefault;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPhone() {
        return itemPhone;
    }

    public void setItemPhone(String itemPhone) {
        this.itemPhone = itemPhone;
    }

    public String getItemProvince() {
        return itemProvince;
    }

    public void setItemProvince(String itemProvince) {
        this.itemProvince = itemProvince;
    }

    public String getItemCity() {
        return itemCity;
    }

    public void setItemCity(String itemCity) {
        this.itemCity = itemCity;
    }

    public String getItemArea() {
        return itemArea;
    }

    public void setItemArea(String itemArea) {
        this.itemArea = itemArea;
    }

    public String getItemAddress() {
        return itemAddress;
    }

    public void setItemAddress(String itemAddress) {
        this.itemAddress = itemAddress;
    }

    public String getItemZipcode() {
        return itemZipcode;
    }

    public void setItemZipcode(String itemZipcode) {
        this.itemZipcode = itemZipcode;
    }

    public String getItemIsDefault() {
        return itemIsDefault;
    }

    public void setItemIsDefault(String itemIsDefault) {
        this.itemIsDefault = itemIsDefault;
    }
}
