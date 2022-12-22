package com.vub.assessment.vendingmachine.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJpaRepository extends JpaRepository<Product,String> {
	@Modifying
	@Query(value = "update Product p set p.availableAmount = p.availableAmount  - :amount where p.id = :id")
	void updateAmount(String id, int amount);
}
