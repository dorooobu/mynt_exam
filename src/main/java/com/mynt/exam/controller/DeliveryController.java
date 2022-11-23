package com.mynt.exam.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mynt.exam.model.Delivery;
import com.mynt.exam.model.DeliveryCost;

@RestController
@RequestMapping(path = "/delivery")
public class DeliveryController {

    @GetMapping(path = "/cost", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeliveryCost> calculateCost(
        @RequestParam float weight,
        @RequestParam float height,
        @RequestParam float width,
        @RequestParam float length,
        @RequestParam String voucherCode
    ) {
        Delivery delivery = new Delivery(weight, height * width * length, voucherCode);
        delivery.calculateCost();

        DeliveryCost deliveryCost = new DeliveryCost();
        deliveryCost.setCost(delivery.getCost());
        deliveryCost.setVoucherStatus(delivery.getVoucherStatus());

        return new ResponseEntity<>(deliveryCost, HttpStatus.OK);
    }
}
