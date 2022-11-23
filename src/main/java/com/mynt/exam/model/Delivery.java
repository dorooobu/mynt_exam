package com.mynt.exam.model;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

public class Delivery {
    private float weight;
    private float volume;
    private String voucherCode;
    private float cost;
    private VoucherStatus voucherStatus;

    private static final float MINIMUM_COST = 0;

    public Delivery(float weight, float volume, String voucherCode) {
        this.weight = weight;
        this.volume = volume;
        this.voucherCode = voucherCode;
    }

    public void calculateCost() {
        if (this.weight > 50) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Weight exceeds 50kg");
        } else if (this.weight > 10) {
            this.cost = 20 * this.weight;
        } else if (this.volume < 1500) {
            this.cost = 0.03f * this.volume;
        } else if (this.volume < 2500) {
            this.cost = 0.04f * this.volume;
        } else {
            this.cost = 0.05f * this.volume;
        }

        calculateVoucherDiscount();
    }

    private void calculateVoucherDiscount() {
        String voucherURI = String.format("https://mynt-exam.mocklab.io/voucher/%s?key=%s", voucherCode, "apikey");
        RestTemplate restTemplate = new RestTemplate();

        try {
            Voucher result = restTemplate.getForObject(voucherURI, Voucher.class);

            if (result.getExpiry().before(new Date())) {
                voucherStatus = VoucherStatus.EXPIRED;
            } else {
                voucherStatus = VoucherStatus.USED;
                this.cost -= result.getDiscount();

                // logic if there is a minimum cost like 0php
                if (this.cost < MINIMUM_COST) {
                    this.cost = MINIMUM_COST;
                }
            }
        } catch (HttpClientErrorException ex) {
            voucherStatus = VoucherStatus.INVALID;
        }
    }

    public float getCost() {
        return cost;
    }

    public String getVoucherStatus() {
        return voucherStatus.name();
    }
}
