package com.vub.assessment.vendingmachine.domain.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<User, String> {
	Optional<User> findByUsername(String username);

	@Modifying
	@Query("update User u set u.deposit =:depositAmount where u.id =:id")
	void updateDeposit(long depositAmount,String id);
}
