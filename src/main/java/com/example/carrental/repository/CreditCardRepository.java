package com.example.carrental.repository;

import com.example.carrental.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
	Optional<CreditCard> findByCardNo(String cardNo);
	


}
