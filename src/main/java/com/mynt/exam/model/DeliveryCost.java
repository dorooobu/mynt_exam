package com.mynt.exam.model;

public class DeliveryCost {
    private float cost;
    private String voucherStatus;

    public DeliveryCost() {}

    public float getCost() {
        return cost;
    }
    public void setCost(float cost) {
        this.cost = cost;
    }
    public String getVoucherStatus() {
        return voucherStatus;
    }
    public void setVoucherStatus(String voucherStatus) {
        this.voucherStatus = voucherStatus;
    }
}
