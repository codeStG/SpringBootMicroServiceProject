package com.stgcodes.model;

public class Phone {
    enum PhoneType {
        MOBILE,
        HOME,
        BUSINESS,
        OTHER
    }

    private String phoneNumber;
    private PhoneType phoneType;

    public Phone(String phoneNumber, PhoneType phoneType) {
        this.phoneNumber = phoneNumber;
        this.phoneType = phoneType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PhoneType getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(PhoneType phoneType) {
        this.phoneType = phoneType;
    }
}
