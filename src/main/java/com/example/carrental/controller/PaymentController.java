package com.example.carrental.controller;

import com.example.carrental.model.CreditCard;
import com.example.carrental.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private CreditCardService creditCardService;

    @PostMapping("/pay")
    public ResponseEntity<String> pay(@RequestParam String cardNo, @RequestParam Long amount) {
        boolean success = creditCardService.deductAmount(cardNo, amount);

        if (success) {
            return ResponseEntity.ok("Payment successful!");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient balance!");
    }

    @GetMapping("/balance")
    public ResponseEntity<?> getBalance(@RequestParam String cardNo) {
    	CreditCard card = creditCardService.getCardByNumber(cardNo);
        
        if (card == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Credit Card not found!");
        }
        
        return ResponseEntity.ok(card.getBalance());
    }

}
