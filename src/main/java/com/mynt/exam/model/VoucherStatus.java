package com.mynt.exam.model;

public enum VoucherStatus {
    USED("used"),
    EXPIRED("expired"),
    INVALID("invalid");

    public final String label;

    private VoucherStatus(String label) {
        this.label = label;
    }
}
