package com.example.carrental.model;

import jakarta.persistence.*;

@Entity
@Table(name = "credit_cards")
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_no", nullable = false, unique = true)
    private String cardNo;

    @Column(name = "expire_month", nullable = false)
    private int expireMonth;

    @Column(name = "expire_year", nullable = false)
    private int expireYear;

    @Column(name = "cvv", nullable = false)
    private int cvv;

    @Column(name = "name_on_card", nullable = false)
    private String nameOnCard;

    @Column(name = "balance", nullable = false)
    private Long balance;

    // Constructors, Getters, and Setters
    public CreditCard() {}

    public CreditCard(String cardNo, int expireMonth, int expireYear, int cvv, String nameOnCard, Long balance) {
        this.cardNo = cardNo;
        this.expireMonth = expireMonth;
        this.expireYear = expireYear;
        this.cvv = cvv;
        this.nameOnCard = nameOnCard;
        this.balance = balance;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCardNo() { return cardNo; }
    public void setCardNo(String cardNo) { this.cardNo = cardNo; }

    public int getExpireMonth() { return expireMonth; }
    public void setExpireMonth(int expireMonth) { this.expireMonth = expireMonth; }

    public int getExpireYear() { return expireYear; }
    public void setExpireYear(int expireYear) { this.expireYear = expireYear; }

    public int getCvv() { return cvv; }
    public void setCvv(int cvv) { this.cvv = cvv; }

    public String getNameOnCard() { return nameOnCard; }
    public void setNameOnCard(String nameOnCard) { this.nameOnCard = nameOnCard; }

    public Long getBalance() { return balance; }
    public void setBalance(Long balance) { this.balance = balance; }
}