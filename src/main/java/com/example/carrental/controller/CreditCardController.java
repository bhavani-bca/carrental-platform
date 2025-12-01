package com.example.carrental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.carrental.model.CreditCard;
import com.example.carrental.service.CreditCardService;

@RestController
@RequestMapping("/credit-cards")
public class CreditCardController {

    @Autowired
    private CreditCardService creditCardService;

    @GetMapping("/{cardNo}")
    public ResponseEntity<CreditCard> getCardDetails(@PathVariable String cardNo) {
        CreditCard card = creditCardService.getCardByNumber(cardNo);
        return ResponseEntity.ok(card);
    }
}
