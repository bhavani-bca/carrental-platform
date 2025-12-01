package com.example.carrental.service;

import com.example.carrental.model.CreditCard;
import com.example.carrental.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CreditCardService {

    @Autowired
    private CreditCardRepository creditCardRepository;

    public boolean deductAmount(String cardNo, Long amount) {
        Optional<CreditCard> creditCardOptional = creditCardRepository.findByCardNo(cardNo);
        
        if (creditCardOptional.isPresent()) {
            CreditCard creditCard = creditCardOptional.get();
            
            if (creditCard.getBalance() >= amount) {
                creditCard.setBalance(creditCard.getBalance() - amount);
                creditCardRepository.save(creditCard);
                return true;
            }
        }
        return false; // Insufficient balance or card not found
    }

    public CreditCard getCardByNumber(String cardNo) {
        return creditCardRepository.findByCardNo(cardNo).orElse(null);
    }
    
    public Long getCardBalance(String cardNumber) {
        Optional<CreditCard> card = creditCardRepository.findByCardNo(cardNumber);
        return card.map(CreditCard::getBalance).orElse(null);
    }

    public void updateCardBalance(String cardNumber, Long newBalance) {
        Optional<CreditCard> cardOptional = creditCardRepository.findByCardNo(cardNumber);
        if (cardOptional.isPresent()) {
            CreditCard card = cardOptional.get();
            card.setBalance(newBalance);
            creditCardRepository.save(card);
        }
    }
    
 // New method to get Credit Card by card number
    public CreditCard getCreditCardByNumber(String cardNumber) {
        return creditCardRepository.findByCardNo(cardNumber).orElse(null);
    }

    // Update credit card balance
    public void updateCardBalance(String cardNumber, long newBalance) {
        Optional<CreditCard> optionalCreditCard = creditCardRepository.findByCardNo(cardNumber);
        if (optionalCreditCard.isPresent()) {
            CreditCard creditCard = optionalCreditCard.get();
            creditCard.setBalance(newBalance);
            creditCardRepository.save(creditCard);
        }
    }

}
